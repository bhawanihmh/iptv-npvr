package iptv.npvr.rest;

import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import iptv.npvr.exception.NpvrException;
import iptv.npvr.hystrix.ChannelHystrixCommand;
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
	
	static{
		loadCache();
	}  
	
	static final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

	@POST
	@Path("record/{flag}")
	@Consumes("application/json")
    @Produces({"application/json"})
	//@Produces({MediaType.TEXT_PLAIN})
    public NPVR record(Record record, @PathParam("flag") boolean flag) throws NpvrException {
		LOGGER.info("NpvrREST.record()");
		NPVR npvr = new NPVR();
		npvr.setRecord(record);
		npvr.setStatus("Success");
		LOGGER.info("getChannelId = " + record.getChannelId());
		LOGGER.info("getProgramId = " + record.getProgramId());
		LOGGER.info("getStartTime = " + record.getStartTime());
		LOGGER.info("flag = " + flag);
		
		DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date startTime = null;
		try {
			startTime = df2.parse(record.getStartTime());
			LOGGER.info("Date: " + startTime);
			LOGGER.info("Date in dd-MM-yyyy HH:mm:ss format is: " + df2.format(startTime));			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Channel channel = null;		
		HystrixRequestContext context = HystrixRequestContext.initializeContext();
		try {
			ConfigurationManager.getConfigInstance().setProperty("primarySecondary.usePrimary", true);
			channel = new ChannelHystrixCommand("Channel",record.getChannelId(),flag).execute();			
			if (null != channel) {				
				LOGGER.info("getChannelId = " + channel.getChannelId());
				LOGGER.info("getCallSign = " + channel.getCallSign());
				if(channel.isFallBack()){
					npvr.setStatus("Success: Returned from Circuit Breaker");
					LOGGER.info("Success: Returned from Circuit Breaker");
				} else {
					LOGGER.info("Success");
				}
				npvr.setChannel(channel);			
				Programme programme = callProgrammeService(record);
				
				if (null != programme) {			
					LOGGER.info("Programme get successfully");
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
		} finally {
			context.shutdown();
			ConfigurationManager.getConfigInstance().clear();
		}
		
		return npvr;
	}
	
	@GET
	@Path("hi")
    //@Produces({"application/xml", "application/json"})
	@Produces({MediaType.TEXT_PLAIN})
    public String hi() {
		LOGGER.info("NpvrREST.hi()");
		return "Hello World !!!";
	}

	
	
	/**
	 * 
	 * @param record
	 * @return
	 */
	public Programme callProgrammeService(Record record)  throws NpvrException {
		LOGGER.info("@@@@@@@@@@@@  Call Programme Service    @@@@@@@@@@  ");		
		String url = "http://programme-wildflyswarm.apps.10.2.2.2.xip.io";
		//String url = "http://10.131.126.158:8480";
		String path = "/programme/" + record.getProgramId();
		
		WebTarget programmeService = ClientBuilder.newClient()
				.target(UriBuilder.fromUri(URI.create(url)).path(path).build());
		
		LOGGER.info("Uri = " + programmeService.getUri().toString());
		
		Programme programme = null;	
		
		try{
			programme = programmeService.request().accept(MediaType.APPLICATION_JSON).get(Programme.class);
		} catch(Exception exception) {
			throw new NpvrException("Programme Service Down !!",102);	
		}
		
		return programme;
	}	
	
	private static void loadCache(){
		System.out.println("NpvrREST.loadCache()");
		String url = "http://channel-wildflyswarm.apps.10.2.2.2.xip.io";
		//String url = "http://10.131.126.158:8380";
		String path = "/channel/hi";		
		WebTarget channelService = ClientBuilder.newClient()
				.target(UriBuilder.fromUri(URI.create(url)).path(path).build());
		LOGGER.info("Uri = " + channelService.getUri().toString());
		String val = channelService.request().accept(MediaType.TEXT_PLAIN).get(String.class);	
			
		
		url = "http://programme-wildflyswarm.apps.10.2.2.2.xip.io";
		//url = "http://10.131.126.158:8480";
		path = "/programme/hi";		
		WebTarget programmeService = ClientBuilder.newClient()
				.target(UriBuilder.fromUri(URI.create(url)).path(path).build());		
		LOGGER.info("Uri = " + programmeService.getUri().toString());
		val = programmeService.request().accept(MediaType.TEXT_PLAIN).get(String.class);		
	}	
}
