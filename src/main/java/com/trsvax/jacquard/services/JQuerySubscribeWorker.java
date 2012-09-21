package com.trsvax.jacquard.services;

import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import com.trsvax.jacquard.JQuerySubscribe;
import com.trsvax.jacquard.annotations.Subscribe;

public class JQuerySubscribeWorker extends AbstractSubscribeWorker implements JQuerySubscribe {
	
	
	public JQuerySubscribeWorker(JavaScriptSupport javaScriptSupport) {
		super(javaScriptSupport);
	}

	public String script(String name, String url, Subscribe subscribe) {
		String data = "";
		String sep = "";
		for ( String arg : subscribe.args() ) {
			data += sep + arg;
			sep = ", ";
		}
		return String.format("$(%s).bind('%s',function(event) {\n" +
				"$.ajax({\n" +
				"url: '%s',\n" +
				"data: {%s} \n" +
				"});\n " +
				"return %s;\n});",subscribe.selector(),name, url, data, subscribe.returnValue());
	}
	
	

}
