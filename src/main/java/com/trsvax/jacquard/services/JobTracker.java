package com.trsvax.jacquard.services;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.RequestGlobals;


public class JobTracker implements ComponentRequestFilter {
	
	private final JobRunner jobRunner;
	private final RequestGlobals requestGlobals;
	private Method getStatus;
	
	public JobTracker(JobRunner jobRunner, RequestGlobals requestGlobals) {
		this.jobRunner = jobRunner;
		this.requestGlobals = requestGlobals;
		try {
			this.getStatus = HttpServletResponse.class.getMethod("getStatus");
		} catch (NoSuchMethodException | SecurityException e) {
			this.getStatus = null;
		}
	}

	@Override
	public void handleComponentEvent(ComponentEventRequestParameters parameters, ComponentRequestHandler handler)
			throws IOException {
		handler.handleComponentEvent(parameters);
		
	}

	@Override
	public void handlePageRender(PageRenderRequestParameters parameters, ComponentRequestHandler handler)
			throws IOException {
		jobRunner.logRun(parameters.getLogicalPageName());
		handler.handlePageRender(parameters);	
		if ( ! ok() ) {
			jobRunner.logFail(parameters.getLogicalPageName());
		}
	}

	/* only for containers >= 3 */
	boolean ok() {
		if ( getStatus != null ) {
			try {
				HttpServletResponse httpServletResponse = requestGlobals.getHTTPServletResponse();
				Integer status = (Integer) getStatus.invoke(httpServletResponse);
				if ( status == HttpServletResponse.SC_OK) {
					return true;
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// don't care
			}
		}
		return false;
	}
}
