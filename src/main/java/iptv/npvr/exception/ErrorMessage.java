package iptv.npvr.exception;

/**
 * 
 * @author bhawani.singh
 *
 */
public class ErrorMessage {
	int status;
	int code;
	String message;
	/**
	 * @param status
	 * @param code
	 * @param message
	 */
	public ErrorMessage(int status, int code, String message) {
		super();
		this.status = status;
		this.code = code;
		this.message = message;
	}
	
	/**
	 * 
	 */
	public ErrorMessage() {
		super();
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
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
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
