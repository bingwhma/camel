package com.linyi.camel.ext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ExtComponentTest {

	public static void main(String[] args) throws Exception {
		CamelContext camelContext = new DefaultCamelContext();
		camelContext.addComponent("xfile", new MyFileComponent());
		
		camelContext.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				this.from("xfile:c:/gitsrc/filecollector/data/inbox")
				.process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						File file = exchange.getIn().getBody(File.class);
						PrintStream ps = new PrintStream(System.out);
						BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
						String line = null;
						while((line = br.readLine()) != null) {
							ps.println(line);
						}
						
						ps.close();
						br.close();
						System.out.println("Processor end.");
//						exchange.getOut().setBody(file, File.class);
					}
				})
//				.to("file:data/outbox")
				.to("xfile:c:/gitsrc/filecollector/data/outbox")
				.end();
				
			}
		});
		
		camelContext.start();
		
		Object object = new Object();
		synchronized (object) {
			object.wait();
		}
	}
		
}
