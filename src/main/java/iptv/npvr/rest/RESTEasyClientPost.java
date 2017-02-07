package iptv.npvr.rest;

import java.io.StringWriter;
import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import iptv.npvr.pojo.Channel;

/**
 * Please ignore this file
 * 
 * @author bhawani.singh
 *
 */

public class RESTEasyClientPost {
	static final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

	public static void main(String[] args) {

		String testDateString2 = "02/04/2014 23:37:50";
		DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		 Date d2;
		try {
			d2 = df2.parse(testDateString2);
			System.out.println("Date: " + d2);
	        System.out.println("Date in dd-MM-yyyy HH:mm:ss format is: "+df2.format(d2));
	        
	        long val = d2.getTime();
	        
	        Date afterAddingTenMins=new Date(val + (30 * ONE_MINUTE_IN_MILLIS));
	        
	        System.out.println("Date: " + afterAddingTenMins);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
		
		
         
		/* String myTime = "14:10";
		 SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		 Date d = df.parse(myTime); 
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(d);
		 cal.add(Calendar.MINUTE, 10);
		 String newTime = df.format(cal.getTime());*/
		
         
		//restCallNpvr();
		//callChannel();
		

		
		
		
	}

	private static void callChannel() {
		WebTarget programmeService = ClientBuilder.newClient()
				.target(UriBuilder.fromUri(URI.create("http://10.131.126.158:8080/channel")).path("/8").build());

		
		Channel channel = programmeService.request().accept(MediaType.APPLICATION_JSON).get(Channel.class);
		
		
		//Response response = programmeService.request().post(Entity.json(w.toString()));
		//Channel channel = (Channel)programmeService.request().get(Channel.class);

		if (null != channel) {			
			System.out.println("RESTEasyClientPost.main()  successfully");
			System.out.println("RESTEasyClientPost.main() getChannelId = " + channel.getChannelId());
			System.out.println("RESTEasyClientPost.main() getCallSign = " + channel.getCallSign());
			
		} else {
			System.out.println("RESTEasyClientPost.main()  Failur");
		}
	}

	private static void restCallNpvr() {
		JsonObject jsonObject = Json.createObjectBuilder().add("channelId", "9").add("programId", "11")
				.add("startTime", "10:10:10").add("endTime", "10:10:10").build();
		StringWriter w = new StringWriter();
		try (JsonWriter writer = Json.createWriter(w)) {
			writer.write(jsonObject);
		}

		/*WebTarget programmeService = ClientBuilder.newClient()
				.target(UriBuilder.fromUri(URI.create("http://10.131.126.158:8080/npvr")).path("/record").build());*/
		
		WebTarget programmeService = ClientBuilder.newClient()
				.target(UriBuilder.fromUri(URI.create("http://10.131.126.158:8080/channel")).path("/8").build());

		System.out.println("RESTEasyClientPost.main() w = " + w.toString());
		
		//Response response = programmeService.request().post(Entity.json(w.toString()));
		Response response = programmeService.request().get();

		/*
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(response.getEntity().getBytes())));*/
		
		
		
		Response.StatusType statusInfo = response.getStatusInfo();

		if (statusInfo.getFamily() == Response.Status.Family.SUCCESSFUL) {			
			System.out.println("RESTEasyClientPost.main()  successfully");
			
			System.out.println(response.readEntity(Channel.class));
			
			Channel channel = response.readEntity(Channel.class);
			
			System.out.println("RESTEasyClientPost.main() getChannelId = " + channel.getChannelId());
			System.out.println("RESTEasyClientPost.main() getCallSign = " + channel.getCallSign());

			/*JsonObject jsonResponse = Json.createReader(new StringReader(response.readEntity(NPVR.class))).readObject();
			System.out.println("Record added successfully," +
                    " login: " + jsonResponse.get("login") +
                    " password: " + jsonResponse.get("password"));*/
			
			
		} else {
			System.out.println("RESTEasyClientPost.main()  Failur");
		}
	}

}