package com.trsvax.jacquard;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.spi.LoggingEvent;

public class Page {
	
	private final Integer sequence;
	private final List<LoggingEvent> logs = new CopyOnWriteArrayList<>();
	private final String url;
	
	private final long start = System.currentTimeMillis();
	private long end;

	public Page(Integer sequence, String url) {
		this.sequence = sequence;
		this.url = url;
	}
	
	public Long getDuration() {
		if ( end == 0 ) {
			return null;
		}
		return end - start;
	}
	
	public Integer getSequence() {
		return sequence;
	}
	
	public void addLog(LoggingEvent event) {
		logs.add(event);
	}
	
	public List<LoggingEvent> getLogs() {
		return logs;
	}

	public String getUrl() {
		return url;
	}

	public long getStart() {
		return start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}
}
