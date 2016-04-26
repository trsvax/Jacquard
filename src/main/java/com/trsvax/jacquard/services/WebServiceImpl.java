package com.trsvax.jacquard.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import org.slf4j.Logger;

public class WebServiceImpl implements WebService {
	
	private final Logger logger;
	
	public WebServiceImpl(Logger logger) {
		this.logger = logger;
	}

	@Override
	public String content(URL url) {
		try {
			String content;
			Object o = url.getContent();
			logger.info("content type {}",o.getClass());
			if ( o instanceof InputStream ) {
				content = convertStreamToString((InputStream) o);
				((InputStream) o).close();
				return content;
			}
			return o.toString();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	String convertStreamToString(InputStream is) {
		Scanner scanner = new Scanner(is);
		try {	    
			java.util.Scanner s = scanner.useDelimiter("\\A");
			return s.hasNext() ? s.next() : "";
		} finally {
			scanner.close();
		}
	}

}

