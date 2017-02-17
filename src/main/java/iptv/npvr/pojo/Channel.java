package iptv.npvr.pojo;

import java.io.Serializable;

/**
 * 
 * @author bhawani.singh
 *
 */
public class Channel implements Serializable {

	private String channelId;
	private String callSign;
	private boolean isFallBack;
	
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

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getCallSign() {
		return callSign;
	}

	public void setCallSign(String callSign) {
		this.callSign = callSign;
	}

	public boolean isFallBack() {
		return isFallBack;
	}

	public void setFallBack(boolean isFallBack) {
		this.isFallBack = isFallBack;
	}

}


