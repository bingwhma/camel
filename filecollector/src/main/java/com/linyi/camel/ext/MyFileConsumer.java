package com.linyi.camel.ext;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.support.DefaultConsumer;

public class MyFileConsumer extends DefaultConsumer implements Runnable {

	private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
	private File pollDir;
	
	public MyFileConsumer(Endpoint endpoint, Processor processor) {
		super(endpoint, processor);

		String pollDir = endpoint.getEndpointUri().substring(endpoint.getEndpointUri().indexOf(":")+1);
		this.pollDir = new File(pollDir);

	}

	@Override
	protected void doStart() throws Exception {
		super.doStart();
		executorService.scheduleAtFixedRate(this, 1000L, 1000L, TimeUnit.MILLISECONDS);	
	}
	
	@Override
	public void run() {
		System.out.println("void run." + pollDir.toPath().toString());
		File[] files = pollDir.listFiles();
		System.out.println("files:" + files.length);
		for(File file : files) {
			System.out.println("file:" + file.getAbsolutePath());
			MyFileEndpoint endpoint = (MyFileEndpoint) getEndpoint();
			Exchange exchange = endpoint.createExchange(file);
			try {
				processExchange(exchange);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
 
 
	private void processExchange(Exchange exchange) throws Exception {
		this.getProcessor().process(exchange);
	}

}
