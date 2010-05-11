package com.aillusions.cookbook.web.servlet;

public class DataSource {
	private String contentType, filename;
	private byte[] binary;

	/**
	 * Creates a new DataSource object.
	 * 
	 * @param contentType
	 *            The MIME type
	 * @param filename
	 *            The objects name. This includes any path information
	 * @param bin
	 *            The binary contents of the MIME object
	 */
	public DataSource(String contentType, String filename, byte[] bin) {
		this.contentType = contentType;
		this.filename = filename;
		this.binary = bin;
	}

	/**
	 * Sets this object's content type. The String should be in MIME format
	 * (i.e. image/jpeg)
	 * 
	 * @param type
	 *            The MIME type
	 */
	public void setContentType(String type) {
		this.contentType = type;
	}

	/**
	 * Sets this object's file name. This should include any path information.
	 * ex. /home/loser/images/you.jpg; c:\My Documents\My Pictures\you.gif
	 * 
	 * @param name
	 *            The file name
	 */
	public void setFilename(String name) {
		this.filename = name;
	}

	/**
	 * Sets this object's binary content
	 * 
	 * @param content
	 *            An array of bytes
	 */
	public void setBinaryContent(byte[] content) {
		this.binary = content;
	}

	/**
	 * @return The MIME type of this DataSource object
	 */
	public String getContentType() {
		return this.contentType;
	}

	/**
	 * @return The full file name including any path information
	 */
	public String getFilename() {
		return this.filename;
	}

	/**
	 * @return The object's name minus the path information or <code>null</code>
	 *         if <code>getFilename()</code> returns <code>null</code>
	 */
	public String getName() {
		String name = this.filename;
		if (null != name) {
			char separator = '/';
			int indx = name.indexOf(separator); // try unix first
			if (-1 == indx) {
				separator = '\\'; // try Windows next
				indx = name.indexOf(separator);
			}
			if (-1 != indx)
				name = name.substring(name.lastIndexOf(separator) + 1);
		}
		return name;
	}

	/**
	 * @return The binary content of <code>this</code> MIME object
	 */
	public byte[] getBinaryContent() {
		return this.binary;
	}
}