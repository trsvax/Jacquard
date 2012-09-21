package com.trsvax.jacquard.services;

import java.util.Map;

import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.ioc.Messages;

public interface UserContentService<T> {
	
	public Object getProperty(String pageName, String propertyName);
	public BeanModel<T> beanModel(String pageName, Messages messages);
	public void setContent(String pageName, Map<String,Object> content);
	public Map<String,Object> getContent(String pageName);

}
