package iptv.npvr.pojo;

import java.io.Serializable;

/**
 * 
 * @author bhawani.singh
 *
 */
public class Programme implements Serializable {

	private String progId;
	private String progName;
	private Integer progDuration;
	
	private final static long serialVersionUID = -277195824410767091L;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Programme() {
	}

	/**
	 * 
	 * @param progId
	 * @param progDuration
	 * @param progName
	 */
	public Programme(String progId, String progName, Integer progDuration) {
		super();
		this.progId = progId;
		this.progName = progName;
		this.progDuration = progDuration;
	}

	public String getProgId() {
		return progId;
	}

	public void setProgId(String progId) {
		this.progId = progId;
	}

	public String getProgName() {
		return progName;
	}

	public void setProgName(String progName) {
		this.progName = progName;
	}

	public Integer getProgDuration() {
		return progDuration;
	}

	public void setProgDuration(Integer progDuration) {
		this.progDuration = progDuration;
	}

}
