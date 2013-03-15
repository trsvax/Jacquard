package com.trsvax.jacquard.mixins;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library={"markitup/jquery.markitup.js","markitup/sets/html/set.js"},
	stylesheet={"markitup/skins/markitup/style.css","markitup/sets/html/style.css"})
public class MarkItUp {
	
	@Inject
	private JavaScriptSupport support;
	
	@Inject
	private ComponentResources resources;
	
	@AfterRender
	void afterRender() {
		String id = ((ClientElement) resources.getContainer()).getClientId();
		support.addScript("$('#%s').markItUp(%s);", id,"mySettings");
		
	}

}
