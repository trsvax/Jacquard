package com.trsvax.jacquard.services;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.ioc.annotations.EagerLoad;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.LinkCreationHub;
import org.apache.tapestry5.services.LinkCreationListener2;
import org.apache.tapestry5.services.PageRenderRequestParameters;

@EagerLoad
public class MultiLinkHub implements LinkCreationListener2 {
	private final MultiStatePersistenceStrategy strategy;
	
	public MultiLinkHub(MultiStatePersistenceStrategy strategy, LinkCreationHub hub) {
		this.strategy = strategy;
		hub.addListener(this);
	}
		
	@Override
	public void createdComponentEventLink(Link link, ComponentEventRequestParameters parameters) {
		strategy.updateEvent(link);
	}

	@Override
	public void createdPageRenderLink(Link link, PageRenderRequestParameters parameters) {
		strategy.updatePage(link);
	}

}
