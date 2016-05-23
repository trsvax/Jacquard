package com.trsvax.jacquard.pages.dev.page;

import java.util.Collection;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.trsvax.jacquard.Page;
import com.trsvax.jacquard.services.PageService;

public class PageIndex {
	
	@Inject
	PageService pageService;
	
	@Property
	Collection<Page> pages;
	
	@Property
	Page page;
	
	@SetupRender
	void setupRender() {
		pages = pageService.getPages();
	}

}
