package com.trsvax.jacquard.pages.dev.page;

import java.util.List;

import org.apache.log4j.spi.LoggingEvent;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.trsvax.jacquard.Page;
import com.trsvax.jacquard.services.PageService;

public class PageView {
	
	@PageActivationContext
	String pageSequence;
	
	@Inject
	PageService pageService;
	
	@Property
	List<LoggingEvent> logs;
	
	@SetupRender
	void setupRender() {
		Page page = pageService.getPage(pageSequence);
		if ( page != null ) {
			logs = page.getLogs();
		}
	}

}
