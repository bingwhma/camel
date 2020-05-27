package com.linyi.camel.ext;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultProducer;

public class MyFileProducer extends DefaultProducer {

	private File outputDir;

	public MyFileProducer(Endpoint endpoint) {
		super(endpoint);
		System.out.println("MyFileProducer:" + endpoint.getEndpointUri());
		this.outputDir = new File(endpoint.getEndpointUri().substring(endpoint.getEndpointUri().indexOf(":")+1));
	}
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		File file = exchange.getIn().getBody(File.class);
		System.out.println("MyFileProducer process. input file:" + file.getAbsolutePath());
		if(file!=null) {
			String name = file.getName();
			Files.copy(file.toPath(), 
					new File(outputDir.getAbsoluteFile() + "/" + name).toPath(),
					StandardCopyOption.REPLACE_EXISTING);
//			FileUtils.moveFileToDirectory(file, outputDir, true);
		} else {
			System.out.println("input file is null.");
		}
	}

}
