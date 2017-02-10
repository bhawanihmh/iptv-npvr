package iptv.npvr.hystrix;

import java.net.URI;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

import iptv.npvr.exception.NpvrException;
import iptv.npvr.pojo.Channel;

/**
 * 
 * @author bhawani.singh
 *
 */

public class ChannelHystrixCommand extends HystrixCommand<Channel> {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(ChannelHystrixCommand.class.getName());

	private final String name;
	private final String channelId;
	private final boolean flag;

	/**
	 * Use Jmeter to send requests with 1 Thread(user) in 1 second and loop count 20000
	 * 
	 * @param name
	 * @param channelId
	 */
	public ChannelHystrixCommand(String name, String channelId,boolean flag) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("Channel"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("GetChannel"))
				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
						.withExecutionTimeoutInMilliseconds(1000)
						.withMetricsHealthSnapshotIntervalInMilliseconds(1000)
						.withMetricsRollingStatisticalWindowInMilliseconds(20000)
						.withCircuitBreakerErrorThresholdPercentage(100)
						.withCircuitBreakerEnabled(true)
						.withCircuitBreakerRequestVolumeThreshold(1)
						.withCircuitBreakerSleepWindowInMilliseconds(20000))
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("Channel")).andThreadPoolPropertiesDefaults(
						HystrixThreadPoolProperties.Setter().withCoreSize(4).withMaxQueueSize(10)));

		this.name = name;
		this.channelId = channelId;
		this.flag = flag;
	}

	@Override
	protected Channel run() throws Exception {
		Channel channel = callChannelService();
		LOGGER.info("Channel get successfully");		
		return channel;
	}
	
	@Override
	protected Channel getFallback() {
		LOGGER.info("########################");
		LOGGER.info("Channel Service Down !!!");
		LOGGER.info("########################");
		LOGGER.info("Fallback... return channel from cache ");
		Channel channel = channels.get(channelId);
		channel.setFallBack(true);
		return channel;
	}
	
	/**
	 * 
	 * @param record
	 * @return
	 */
	public Channel callChannelService()  throws NpvrException {
		LOGGER.info("@@@@@@@@@@@@  Call Channel Service    @@@@@@@@@@  ");
		//String url = "http://channel-wildflyswarm.apps.10.2.2.2.xip.io";
		String url = "http://10.131.126.158:8380";
		String path = "/channel/" + channelId + "/" + flag;		
		WebTarget channelService = ClientBuilder.newClient()
				.target(UriBuilder.fromUri(URI.create(url)).path(path).build());
		LOGGER.info("Uri = " + channelService.getUri().toString());
		Channel channel = channelService.request().accept(MediaType.APPLICATION_JSON).get(Channel.class);	
		return channel;
	}
	
	private static HashMap<String,Channel> channels = new HashMap<String,Channel>();	
	static {		
		for(int i=0; i<=10; i++){
			channels.put(""+i, new Channel(""+i, "CHAN"+i));
		}		
	}
}
