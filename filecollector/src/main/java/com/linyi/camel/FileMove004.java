package com.linyi.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;


//<dependency>
//<groupId>org.apache.camel</groupId>
//<artifactId>camel-zipfile</artifactId>
//<version>3.3.0</version>
//<!-- use the same version as your Camel core version -->
//</dependency>

public class FileMove004 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		// Create a CamelContext object.
		CamelContext context = new DefaultCamelContext();
		
//		1、marshal方法：封存一个消息为另一种形式，比如封存java对象为XML, CSV, EDI, HL7等数据模型；即对象--->二进制 
//		2、unmarshal方法：进行一种反向操作，将某种格式的数据转换为消息，即二进制--->对象 

		// add our route to the CamelContext
		 context.addRoutes(new RouteBuilder() {
	            public void configure() {
	            	from("file:data/inbox?noop=true")
	            	.filter(header(Exchange.FILE_NAME).contains("zip"))
	            	.unmarshal().zipFile()
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
