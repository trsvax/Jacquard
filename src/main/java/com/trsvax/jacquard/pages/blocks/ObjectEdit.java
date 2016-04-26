package com.trsvax.jacquard.pages.blocks;

import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.PropertyEditContext;

public class ObjectEdit {
	@Property
	@Environmental
	private PropertyEditContext context;
	
	@Inject
	private BeanModelSource beanModelSource;
	
	@SuppressWarnings("unchecked")
	public BeanModel<?> getModel() {
		return beanModelSource.createEditModel(context.getPropertyType(), context.getContainerMessages());
	}
}
