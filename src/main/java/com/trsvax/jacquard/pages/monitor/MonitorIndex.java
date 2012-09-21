package com.trsvax.jacquard.pages.monitor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;

import com.trsvax.jacquard.services.Monitor;



public class MonitorIndex {
	
	
	@Inject
	private Monitor monitor;
	
	Object onException(Throwable cause) {
	    return response(cause.getMessage());
	 }
	
	Object onActivate() throws Exception {
		return response(monitor.status());
	}
	
	StreamResponse response(final String status) {
			return new StreamResponse() {
			
			@Override
			public void prepareResponse(Response response) {							
			}
			
			@Override
			public InputStream getStream() throws IOException {
				return new ByteArrayInputStream(status.getBytes());
			}
			
			@Override
			public String getContentType() {
				return "text/plain";
			}
		};
	}
	
	

}
