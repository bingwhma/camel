package com.linyi.camel.ext;

import java.io.File;

import org.apache.camel.Consumer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.component.file.FileComponent;
import org.apache.camel.support.DefaultEndpoint;
import org.apache.camel.support.DefaultExchange;

public class MyFileEndpoint extends DefaultEndpoint {
     
    public MyFileEndpoint(MyFileComponent component, String uri) {
    	super(uri, component);
    	System.out.println("======MyFileEndpoint. uri:" + uri);
    }
	 
    @Override
    public Producer createProducer() throws Exception {
    	System.out.println("======createProducer");
    	return new MyFileProducer(this);
    }
	 
    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
    	System.out.println("======createConsumer");
    	return new MyFileConsumer(this, processor);
    }
	 
    @Override
    public boolean isSingleton() {
    	System.out.println("======isSingleton");
    	return false;
    }
    
    public Exchange createExchange(File file) {
    	System.out.println("======createExchange");
    	
    	Exchange exchange = new DefaultExchange(getCamelContext());
    	exchange.setProperty(FileComponent.FILE_EXCHANGE_FILE, file);
    	exchange.getIn().setBody(file, File.class);
    	return exchange;
    }

}
