package com.linyi.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class FileMove001 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		// Create a CamelContext object.
		CamelContext context = new DefaultCamelContext();
		
		// add our route to the CamelContext
		 context.addRoutes(new RouteBuilder() {
	            public void configure() {
	                from("file:data/inbox?noop=true")
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
