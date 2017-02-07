package iptv.npvr.rest;

import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import iptv.npvr.exception.NpvrException;
import iptv.npvr.pojo.Channel;
import iptv.npvr.pojo.NPVR;
import iptv.npvr.pojo.Programme;
import iptv.npvr.pojo.Record;

/**
 * 
 * @author bhawani.singh
 *
 */
@Path("npvr")
public class NpvrREST {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(NpvrREST.class.getName());
	
	static final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

	@POST
	@Path("record")
	@Consumes("application/json")
    @Produces({"application/json"})
	//@Produces({MediaType.TEXT_PLAIN})
    public NPVR record(Record record) throws NpvrException {
		LOGGER.info("NpvrREST.record()");
		NPVR  npvr = new NPVR();
		npvr.setRecord(record);
		LOGGER.info("getChannelId = " + record.getChannelId());
		LOGGER.info("getProgramId = " + record.getProgramId());
		LOGGER.info("getStartTime = " + record.getStartTime());
		
		DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date startTime = null;
		try {
			startTime = df2.parse(record.getStartTime());
			LOGGER.info("Date: " + startTime);
			LOGGER.info("Date in dd-MM-yyyy HH:mm:ss format is: " + df2.format(startTime));			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Channel channel = callChannelService(record);
		if (null != channel) {			
			LOGGER.info("successfully");
			LOGGER.info("getChannelId = " + channel.getChannelId());
			LOGGER.info("getCallSign = " + channel.getCallSign());
			
			npvr.setChannel(channel);			
			Programme programme = callProgrammeService(record);
			
			if (null != programme) {			
				LOGGER.info("successfully");
				LOGGER.info("getProgId = " + programme.getProgId());
				LOGGER.info("getProgName = " + programme.getProgName());
				LOGGER.info("getProgDuration = " + programme.getProgDuration());
				
				long val = startTime.getTime();
		        
		        Date afterAddingTenMins = new Date(val 
		        		+ ((programme.getProgDuration().intValue()) * ONE_MINUTE_IN_MILLIS));
		        
		        LOGGER.info("Date in dd-MM-yyyy HH:mm:ss format is: " + df2.format(afterAddingTenMins));	
		        
		        record.setEndTime(df2.format(afterAddingTenMins));
				
				npvr.setProgramme(programme);
			} else {
				LOGGER.info("Programme Not found");
				throw new NpvrException("Programme Not found !!",104);				
			}
			
		} else {
			LOGGER.info("Channel Not found");
			throw new NpvrException("Channel Not found !!",103);			
		}		
		npvr.setStatus("Success");
		return npvr;
	}
	
	@GET
	@Path("hi")
    @Produces({"application/xml", "application/json"})
    public String hi() {
		LOGGER.info("NpvrREST.hi()");
		return "Hello Bhawani !!!";
	}

	/**
	 * 
	 * @param record
	 * @return
	 */
	public Channel callChannelService(Record record)  throws NpvrException {
		LOGGER.info("@@@@@@@@@@@@  NpvrREST.callChannelService()    @@@@@@@@@@  ");		
		
		String path = "channel/" + record.getChannelId();		
		WebTarget ChannelService = ClientBuilder.newClient()
				.target(UriBuilder.fromUri(URI.create("http://10.131.126.158:8180/")).path(path).build());
		Channel channel = null;
		try{
			channel = ChannelService.request().accept(MediaType.APPLICATION_JSON).get(Channel.class);
		} catch(Exception exception) {
			LOGGER.info("########################");
			LOGGER.info("Channel Service Down !!!");
			LOGGER.info("########################");
			return channels.get(record.getChannelId());		
			//throw new NpvrException("Channel Service Down !!",101);	
		}
		
		return channel;
	}
	
	/**
	 * 
	 * @param record
	 * @return
	 */
	public Programme callProgrammeService(Record record)  throws NpvrException {
		LOGGER.info("@@@@@@@@@@@@  NpvrREST.callProgrammeService()    @@@@@@@@@@  ");		
		
		String path = "programme/" + record.getProgramId();
		
		WebTarget ChannelService = ClientBuilder.newClient()
				.target(UriBuilder.fromUri(URI.create("http://10.131.126.158:8280/")).path(path).build());
		
		Programme programme = null;	
		
		try{
			programme = ChannelService.request().accept(MediaType.APPLICATION_JSON).get(Programme.class);
		} catch(Exception exception) {
			throw new NpvrException("Programme Service Down !!",102);	
		}
		
		return programme;
	}
	
	
	private static HashMap<String,Channel> channels = new HashMap<String,Channel>();	
	
	static {
		
		for(int i=0; i<=10; i++){
			channels.put(""+i, new Channel(""+i, "CHAN"+i));
		}
		
	}
	
}
