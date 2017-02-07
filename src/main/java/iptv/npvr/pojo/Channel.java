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
@JsonPropertyOrder({ "channelId", "callSign" })
public class Channel implements Serializable {

	@JsonProperty("channelId")
	private String channelId;
	@JsonProperty("callSign")
	private String callSign;
	@JsonIgnore
	private final static long serialVersionUID = 1117074953613238204L;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Channel() {
	}

	/**
	 * 
	 * @param channelId
	 * @param callSign
	 */
	public Channel(String channelId, String callSign) {
		super();
		this.channelId = channelId;
		this.callSign = callSign;
	}

	@JsonProperty("channelId")
	public String getChannelId() {
		return channelId;
	}

	@JsonProperty("channelId")
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@JsonProperty("callSign")
	public String getCallSign() {
		return callSign;
	}

	@JsonProperty("callSign")
	public void setCallSign(String callSign) {
		this.callSign = callSign;
	}

}


