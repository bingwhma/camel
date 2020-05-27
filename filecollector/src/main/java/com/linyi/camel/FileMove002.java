package com.linyi.camel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class FileMove002 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		// Create a CamelContext object.
		CamelContext context = new DefaultCamelContext();
		
		// add our route to the CamelContext
		 context.addRoutes(new RouteBuilder() {
	            public void configure() {
	                from("file:data/inbox?noop=true")
	                .process(new Processor() {

						@Override
						public void process(Exchange exchange) throws Exception {
							// TODO Auto-generated method stub
							InputStream body = exchange.getIn().getBody(InputStream.class);
							BufferedReader br = new BufferedReader(new InputStreamReader(body));
							StringBuffer sb = new StringBuffer();
							
							String str = null;
							while((str = br.readLine()) != null) {
								System.out.println("line:" + str);
								sb.append(str);
							}
							exchange.getOut().setHeader(Exchange.FILE_NAME, "test.txt");
							exchange.getOut().setBody(sb.toString());
						}
	                	
	                })
	                .to("file:data/outbox");
	            }
	        });
		 
        // start the route and let it do its work
        context.start();
        
        Thread.sleep(10000);

        // stop the CamelContext
        context.stop();

	}

}
