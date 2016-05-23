package com.trsvax.jacquard.services;

import java.net.URL;

public interface WebService {
	
	String content(Class<?> pageClass);
	String content(URL url);
	String content(String url);

}
