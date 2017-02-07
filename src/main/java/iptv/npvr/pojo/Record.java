
package iptv.npvr.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * 
 * @author bhawani.singh
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "channelId", "programId", "startTime", "endTime" })
public class Record implements Serializable {

	@JsonProperty("channelId")
	private String channelId;
	@JsonProperty("programId")
	private String programId;
	@JsonProperty("startTime")
	private String startTime;
	@JsonProperty("endTime")
	private String endTime;
	@JsonIgnore
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

	@JsonProperty("channelId")
	public String getChannelId() {
		return channelId;
	}

	@JsonProperty("channelId")
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@JsonProperty("programId")
	public String getProgramId() {
		return programId;
	}

	@JsonProperty("programId")
	public void setProgramId(String programId) {
		this.programId = programId;
	}

	@JsonProperty("startTime")
	public String getStartTime() {
		return startTime;
	}

	@JsonProperty("startTime")
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@JsonProperty("endTime")
	public String getEndTime() {
		return endTime;
	}

	@JsonProperty("endTime")
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}