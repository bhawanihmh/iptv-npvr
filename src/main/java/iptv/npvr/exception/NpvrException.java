package iptv.npvr.exception;

import java.io.Serializable;

/**
 * 
 * @author bhawani.singh
 *
 */
public class NpvrException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;

	int code = 0;
	
	public NpvrException() {
		super();
	}

	public NpvrException(String msg) {
		super(msg);
	}
	
	public NpvrException(String msg,int code) {
		super(msg);
		this.code = code;
	}

	public NpvrException(String msg, Exception e) {
		super(msg, e);
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	
}
