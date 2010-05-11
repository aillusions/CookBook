package com.aillusions.cookbook.web.servlet;


import java.io.*;
import java.util.*;
import javax.servlet.*;


public final class MultipartFormReader
{
    private final static short UNDEF = -1;
    private final static short READY = 0;
    private final static short FILENAME = 1;
    private final static short NAME = 2;
    private final static short BINARY = 3;
    private final static short TXTDATA = 4;
    private final static short CONTENT_TYPE = 5;
    private final static String CRLF = "\r\n";

    private MultipartFormReader(){}

    /**
     * Parses the ServletInputStream of encoding multipart/form-data and separates it into name value pairs.
     * The name-value pairs are stored in the <code>map</code> argument.  There are a couple of this to be aware of.
     * This class is not a replacement for the <code>javax.servlet.ServletRequest</code> interface but augments it.  It
     * should only be used in cases where the client is POSTing multipart/form-data encoded data.  This class will NOT
     * work under any other conditions.
     *
     * <p>
     * This class breaks the data into 4 groups of objects:<br>
     * 1 - Strings<br>
     * 2 - Arrays of Strings (String[])<br>
     * 3 - DataSource objects<br>
     * 4 - Arrays of DataSource objects (DataSource[])</p>
     *
     * <p>
     * A String object is returned for text fields, text-areas, etc.<br>
     * A String[] is returned for lists and check-boxes and etc.<br>
     * A DataSource object is returned for binary data specified by &lt;input type="file"&gt;<br>
     * A DataSource[] is returned for file fields which allow multiple selections.<br>
     * Just a note: I haven't found a browser yet that allows the multiple selection of filez but RFC 2388 and the W3C
     * HTML 4.01 describes the format for this type of functionality so I've put it here.
     *
     * Usage senarios:<p>
     * Naturally I'm assuming you (you the programmer that's using this class) already know the layout of the HTML
     * form(s) that your serlvet(s) may be processing, therefore it's your responsiblity to avoid
     * <code>NullPointerException</code>s that may be caused by trying to access a field that does not exist in the HTML
     * form.  Additionally, watch out for <code>ClassCastException</code>s that may be caused if you misjudge the type
     * of data that form field contains.</p>
     *
     * <pre>
     * // Get access to an image that the client uploaded to the servlet.
     * DataSource uploadedFile = (DataSource)map.get( "fieldname" );
     * byte[] binaryData = uploadedFile.getBinaryContent();
     * String contentType = uploadedFile.getContentType();
     * String fullFilename = uploadedFile.getFilename();// includes path info (i.e. /home/loser/images/smile.jpg)
     * String filename = uploadedFile.getName();// excludes path info (i.e. smile.jpg)
     * </pre>
     *
     * <pre>
     * // Access list data (i.e. checkboxes, multiple selection lists)
     * String[] typesOfMusic = (String[])map.get( "musicCheckBoxes" );
     * </pre>
     *
     * This is equivalent to the <code>javax.servlet.ServletRequest</code>'s
     * <code>public String[] getParameterValues( java.lang.String )</code> method.
     *
     * @param   request     A <code>ServletRequest</code> object.
     * @param   map         The <code>Map</code> will be populated with the name value pairs of the HTML/XHTML form's
     *                      content.
     */
    public static void read( ServletRequest request, Map map ) throws IOException
    {
        String filename = null;
        String fieldName = null;
        String contentType = null;
        StringBuffer fieldValue = null;

        //Get the separator for the form data.
        int pos = request.getContentType().indexOf( '=' );
        String boundary = "--" + request.getContentType().substring( pos + 1 ).trim();
        ByteArrayOutputStream binarybuffer = new ByteArrayOutputStream();
        byte[] bytes = new byte[ 1024 ];

        ServletInputStream sStream = request.getInputStream();
        int eof = sStream.readLine( bytes, 0, bytes.length );
        short state = UNDEF;

        while( -1 != eof )
        {
            String filter = new String( bytes, 0, eof );
            String caseInsensitiveFilter = filter.toLowerCase();
            if( filter.startsWith( boundary ) )
            {
                state = READY;

                if( null != fieldName )
                {
                    if( null != fieldValue )
                    {
                        Object o = map.get( fieldName ); // Find out if the field name already exists.
                        Object val = fieldValue.substring( 0, fieldValue.length() - 2 ); // Strip the CRLF
                        if( null != o ) // The field name already existed so assume we are dealing w/ a _list_ of values
                        {
                            if( o.getClass().isArray() )
                            {
                                int length = ((Object[])o).length;
                                String[] array = new String[ length + 1 ];
                                System.arraycopy( o, 0, array, 0, length );
                                array[ length ] = (String)val;
                                val = array;
                                array = null;
                            }
                            else
                                val = new String[]{ (String)o, (String)val };
                        }
                        map.put( fieldName, val );
                    }
                    else if( binarybuffer.size() > 0 )
                    {
                        byte[] bin = binarybuffer.toByteArray();
                        byte[] copy = new byte[ bin.length - 2 ]; //strip CRLF
                        System.arraycopy( bin, 0, copy, 0, copy.length );
                        map.put( fieldName, new DataSource( contentType, filename, copy ) );
                        binarybuffer.reset();
                        bin = copy = null;
                    }
                    fieldName = filename = contentType = null;
                    fieldValue = null;
                    binarybuffer.reset();
                }
            }
            else if( caseInsensitiveFilter.startsWith( "content-disposition: form-data" ) && READY == state )
            {
                for( StringTokenizer tokenizer = new StringTokenizer( filter, ";=\"" ); tokenizer.hasMoreTokens(); )
                {
                    String token = tokenizer.nextToken().trim();
                    if( token.startsWith( "name" ) )
                    {
                        fieldName = tokenizer.nextToken();
                        state = NAME;
                    }
                    else if( token.startsWith( "filename" ) )
                    {
                        state = FILENAME;
                        filename = tokenizer.nextToken();
                    }
                }
            }
            else if( caseInsensitiveFilter.startsWith( "content-type: multipart/mixed" ) && NAME == state )
            {
                String subpartBoundary = "--" + filter.substring( filter.indexOf( '=' ) + 1 ).trim();
                Object[] filez = handleSubpart( sStream, subpartBoundary );
                map.put( fieldName, filez );
                fieldName = null;
                fieldValue = null;
                filename = null;
                contentType = null;
                binarybuffer.reset();
                state = UNDEF;
            }
            else if( caseInsensitiveFilter.startsWith( "content-type: " ) && FILENAME == state )
                contentType = filter.substring( 14, filter.length() - 2 );// strip CRLF
            else if( filter.equals( CRLF ) && state == FILENAME )
                state = BINARY;
            else if( filter.equals( CRLF ) && state == NAME )
                state = TXTDATA;
            else if( state == TXTDATA )
                fieldValue = fieldValue == null ? new StringBuffer( filter ) : fieldValue.append( filter );
            else if( state == BINARY )
                binarybuffer.write( bytes, 0, eof );
            eof = sStream.readLine( bytes, 0, bytes.length );
        }// Parsing stops here. The Map should now contain all of the form's data.
        sStream.close();
    }

    /**
     * A utility method that saves you the trouble of having to create a Map object and passing it to the other read
     * method.
     *
     * @param   request     The ServletRequest object
     *
     * @return  A java.util.HashMap containing the name-value pairs of the HTTP POST's form data
     */
    public static HashMap read( ServletRequest request ) throws IOException
    {
        HashMap hash = new HashMap();
        read( request, hash );
        return hash;
    }

    /**
     * A state-machine similar to the read method except it only handles parsing mulipart/mixed encoded data
     *
     * @param   inStream    The ServletInputStream that it will get data from
     * @param   boundary    The component boundary
     *
     * @return  An array of DataSource objects containing the list of filez the user sent.
     */
    private static DataSource[] handleSubpart( ServletInputStream inStream, String startboundary ) throws IOException
    {
        String contentType = null;
        String filename = null;
        DataSource[] filez = null;
        String endboundary = startboundary + "--";
        byte[] binbucket = new byte[ 1024 ];
        ByteArrayOutputStream binbuffer = new ByteArrayOutputStream();
        short state = UNDEF;
        int eof = inStream.readLine( binbucket, 0, binbucket.length );
        while( -1 != eof )
        {
            String filter = new String( binbucket, 0, eof );
            String lowercaseFilter = filter.toLowerCase();
            if( filter.startsWith( startboundary ) )
            {
                state = READY;
                if( binbuffer.size() > 0 )
                {
                    byte[] bin = binbuffer.toByteArray();
                    byte[] bincopy = new byte[ bin.length - 2 ];// strip CRLF
                    System.arraycopy( bin, 0, bincopy, 0, bincopy.length );

                    if( null == filez )
                        filez = new DataSource[]{ new DataSource( contentType, filename, bincopy ) };
                    else
                    {
                        DataSource[] copy = new DataSource[ filez.length + 1 ];
                        System.arraycopy( filez, 0, copy, 0, filez.length );
                        copy[ filez.length ] = new DataSource( contentType, filename, bincopy );
                        filez = copy;
                        copy = null;
                    }
                    bin = bincopy = null;
                    binbuffer.reset();
                }
                if( filter.trim().equals( endboundary ) )
                    break;
            }
            else if( lowercaseFilter.startsWith( "content-disposition: " ) && READY == state )
            {
                int indx = filter.indexOf( "filename=" );
                filename = filter.substring( indx + 10, filter.length() - 3 );// strip CRLF && the closing "
                state = FILENAME;
            }
            else if( lowercaseFilter.startsWith( "content-type: " ) && FILENAME == state )
            {
                contentType = filter.substring( 14, filter.length() - 2 );
                state = CONTENT_TYPE;
            }
            else if( filter.equals( CRLF ) && CONTENT_TYPE == state )
                state = BINARY;
            else if( BINARY == state )
                binbuffer.write( binbucket, 0, eof );
            eof = inStream.readLine( binbucket, 0, binbucket.length );
        }
        return filez;
    }
}
