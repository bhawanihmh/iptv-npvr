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
@JsonPropertyOrder({ "progId", "progName", "progDuration" })
public class Programme implements Serializable {

	@JsonProperty("progId")
	private String progId;
	@JsonProperty("progName")
	private String progName;
	@JsonProperty("progDuration")
	private Integer progDuration;
	@JsonIgnore
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

	@JsonProperty("progId")
	public String getProgId() {
		return progId;
	}

	@JsonProperty("progId")
	public void setProgId(String progId) {
		this.progId = progId;
	}

	@JsonProperty("progName")
	public String getProgName() {
		return progName;
	}

	@JsonProperty("progName")
	public void setProgName(String progName) {
		this.progName = progName;
	}

	@JsonProperty("progDuration")
	public Integer getProgDuration() {
		return progDuration;
	}

	@JsonProperty("progDuration")
	public void setProgDuration(Integer progDuration) {
		this.progDuration = progDuration;
	}

}