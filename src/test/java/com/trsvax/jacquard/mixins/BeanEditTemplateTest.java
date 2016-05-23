package com.trsvax.jacquard.mixins;

import static org.junit.Assert.assertTrue;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.internal.services.MarkupWriterImpl;
import org.junit.Test;

public class BeanEditTemplateTest {

	@Test
	public void beginRender() {
		BeanEditTemplate beanEditTemplate = new BeanEditTemplate();
		MarkupWriter writer = new MarkupWriterImpl();
		beanEditTemplate.beginRender(writer);
		assertTrue(writer.getElement() != null);
	}

	@Test
	public void afterRenderTemplate() {
		BeanEditTemplate beanEditTemplate = new BeanEditTemplate();
		Block template = new Block() {
			
		};
		beanEditTemplate.template = template;
		MarkupWriter writer = new MarkupWriterImpl();
		assertTrue(beanEditTemplate.afterRenderTemplate(writer) == template);
	}
	
	@Test
	public void afterRender() {
		BeanEditTemplate beanEditTemplate = new BeanEditTemplate();
		MarkupWriter writer = new MarkupWriterImpl();
		writer.element("test");
		beanEditTemplate.afterRender(writer);
		assertTrue(writer.getElement() == null);
	}
	
	@Test
	public void properties() {
		BeanEditTemplate beanEditTemplate = new BeanEditTemplate();
		MarkupWriter writer = new MarkupWriterImpl();
		beanEditTemplate.beginRender(writer);
		createForm(writer);

		addTemplate(writer);
		assertTrue(beanEditTemplate.properties.size() == 1);
	}
	
	@Test
	public void templates() {
		BeanEditTemplate beanEditTemplate = new BeanEditTemplate();
		MarkupWriter writer = new MarkupWriterImpl();
		beanEditTemplate.beginRender(writer);
		createForm(writer);
		
		addTemplate(writer);
		assertTrue(beanEditTemplate.templates.size() == 1 );
	}
	
	@Test
	public void firstFormGroup() {
		BeanEditTemplate beanEditTemplate = new BeanEditTemplate();
		MarkupWriter writer = new MarkupWriterImpl();
		beanEditTemplate.beginRender(writer);
		createForm(writer);
		
		addTemplate(writer);
		assertTrue(beanEditTemplate.firstFormGroup != null);
	}
	
	@Test
	public void formGroups() {
		BeanEditTemplate beanEditTemplate = new BeanEditTemplate();
		MarkupWriter writer = new MarkupWriterImpl();
		beanEditTemplate.beginRender(writer);
		createForm(writer);
		
		addTemplate(writer);
		assertTrue(beanEditTemplate.formGroups.size() > 0);
	}

	private void addTemplate(MarkupWriter writer) {
		writer.element("property");
		writer.end();
	}

	private void createForm(MarkupWriter writer) {
		writer.element("form");
		writer.element("div", "class","form-group");
		writer.element("input","id","name");
		writer.end();
		writer.end();
		writer.end();

	}
	
}
