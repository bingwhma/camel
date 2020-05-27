package com.linyi.camel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;


//<dependency>
//<groupId>org.apache.camel</groupId>
//<artifactId>camel-zipfile</artifactId>
//<version>3.3.0</version>
//<!-- use the same version as your Camel core version -->
//</dependency>

public class FileMove005 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		// Create a CamelContext object.
		CamelContext context = new DefaultCamelContext();
		
//		1、marshal方法：封存一个消息为另一种形式，比如封存java对象为XML, CSV, EDI, HL7等数据模型；即对象--->二进制 
//		2、unmarshal方法：进行一种反向操作，将某种格式的数据转换为消息，即二进制--->对象 

		// add our route to the CamelContext
		 context.addRoutes(new RouteBuilder() {
	            public void configure() {
	            	from("file:data/inbox")
	            	.filter(header(Exchange.FILE_NAME).contains("zip"))
	            	.unmarshal().zipFile()
	            	 .process(new Processor() {
						@Override
						public void process(Exchange exchange) throws Exception {
							// TODO Auto-generated method stub
							InputStream body = exchange.getIn().getBody(InputStream.class);
							BufferedReader br = new BufferedReader(new InputStreamReader(body));
							StringBuffer sb = new StringBuffer();
							
							String str = null;
							System.out.println("Headers:" + exchange.getIn().getHeaders());
							while((str = br.readLine()) != null) {
								System.out.println("line:" + str);
								sb.append(str);
							}
						}
	            		 
	            		 
	            	 })
	            	.end();
	            }
	        });
		 
        // start the route and let it do its work
        context.start();
        
        Thread.sleep(10000);

        // stop the CamelContext
        context.stop();

	}

}
