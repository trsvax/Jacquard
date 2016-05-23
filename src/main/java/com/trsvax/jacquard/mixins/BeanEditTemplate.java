package com.trsvax.jacquard.mixins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.MarkupWriterListener;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.AfterRenderTemplate;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.CleanupRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.dom.Attribute;
import org.apache.tapestry5.dom.Element;

public class BeanEditTemplate {
	
	@Parameter
	Block template;
	
	MarkupWriterListener listener;	
	Element container;
	Element form;	
	List<Element> properties;
	List<Element> templates;
	Element firstFormGroup;
	Map<String,Element> formGroups;
	
	@BeginRender
	void beginRender(MarkupWriter writer) {
		form = null;
		properties = new ArrayList<Element>();
		templates = new ArrayList<Element>();
		formGroups = new HashMap<String, Element>();
		listener = new TemplateListener();
		writer.addListener( listener);
		container = writer.element("mixin");
	}
	
	@AfterRenderTemplate
	Object afterRenderTemplate(MarkupWriter writer) {
		return template;	
	}
	
	@AfterRender
	void afterRender(MarkupWriter writer) {
		writer.end();
	}
	
	@CleanupRender
	void cleanupRender(MarkupWriter writer) {
		writer.removeListener(listener);
		
		for ( Element e : templates) {
			e.moveBefore(firstFormGroup);
		}
						
		for ( Element property : properties ) {
			String name = property.getAttribute("name");
			if ( formGroups.containsKey(name)) {
				Element formGroup = formGroups.get(name);
				formGroup.moveAfter(property);
				formGroup.attribute("class", property.getAttribute("class"));
				property.pop();
			}
		}
		
		if ( form != null ) {
			for ( Attribute a : container.getAttributes() ) {
				form.attribute(a.getName(), a.getValue());
			}
		}
		container.pop();	
	}
	
	private String keyFor(Element element) {
		String key = element.getAttribute("id");
		int i = key.indexOf("_");
		if ( i > 0 ) {
			key = key.substring(0,i);
		}
		return key;
	}
	
	private boolean isCheckBox(Element element) {
		String type = element.getAttribute("type");
		if ( type != null && type.equals("checkbox")) {
			return true;
		}
		return false;
	}
	
	private boolean isInput(Element element) {
		String name = element.getName();
		
		if ( name.equals("input")) {
			return true;
		}
		if ( name.equals("select")) {
			return true;
		}
		if ( name.equals("textarea")) {
			return true;
		}
		return false;
	}
	
	private boolean isFormGroup(Element element) {
		if ( element.getAttribute("class") == null ) {
			return false;
		}
		if ( element.getAttribute("class").equals("form-group") ) {
			return true;
		}
		return false;
	}
	
	private class TemplateListener implements MarkupWriterListener {
		@Override
		public void elementDidStart(Element e) {

			if ( e.getName().equals("property")) {
				properties.add(e);
			}
			if ( isInput(e) && isFormGroup(e.getContainer()) ) {
				if ( firstFormGroup == null ) {
					firstFormGroup = e.getContainer();
				}
				formGroups.put(keyFor(e), e.getContainer());											
			}
			if ( isCheckBox(e) ) {
				formGroups.put(keyFor(e), e.getContainer());											
			}
			if ( form != null ) {
				if ( e.getContainer().equals(container)) {
					templates.add(e);
				}
			}
		}
		
		@Override
		public void elementDidEnd(Element e) {
			if ( e.getName().equals("form")) {
				form = e;
			}
		}
	}

}
