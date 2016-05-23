package com.trsvax.jacquard.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class Log4jWatcher implements LogWatcher {
	
	private final Logger logger;
	
	Marker marker = MarkerFactory.getMarker("Watcher");
	Marker marker1 ;


	
	public Log4jWatcher(Logger logger) {
		this.logger = logger;
		marker1 = MarkerFactory.getMarker("Watcher1");
		marker1.add(marker);
	}

	@Override
	public void start(String name) {
		MDC.put("job", name);
		logger.info(marker, "Watch {}",name);
	}

	@Override
	public List<String> logs() {
		// TODO Auto-generated method stub
		return null;
	}

}
