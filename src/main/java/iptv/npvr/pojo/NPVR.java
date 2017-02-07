package iptv.npvr.pojo;

/**
 * 
 * @author bhawani.singh
 *
 */
public class NPVR {

	private Record record;
	private Channel channel;
	private Programme programme;
	private String status;
	
	/**
	 * @return the record
	 */
	public Record getRecord() {
		return record;
	}
	/**
	 * @param record the record to set
	 */
	public void setRecord(Record record) {
		this.record = record;
	}
	/**
	 * @return the channel
	 */
	public Channel getChannel() {
		return channel;
	}
	/**
	 * @param channel the channel to set
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	/**
	 * @return the programme
	 */
	public Programme getProgramme() {
		return programme;
	}
	/**
	 * @param programme the programme to set
	 */
	public void setProgramme(Programme programme) {
		this.programme = programme;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
