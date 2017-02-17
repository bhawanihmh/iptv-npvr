
package iptv.npvr.pojo;

import java.io.Serializable;

/**
 * 
 * @author bhawani.singh
 *
 */
public class Record implements Serializable {

	private String channelId;
	private String programId;
	private String startTime;
	private String endTime;

	private final static long serialVersionUID = 2063548155558844998L;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Record() {
	}

	/**
	 * 
	 * @param startTime
	 * @param programId
	 * @param channelId
	 * @param endTime
	 */
	public Record(String channelId, String programId, String startTime, String endTime) {
		super();
		this.channelId = channelId;
		this.programId = programId;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
