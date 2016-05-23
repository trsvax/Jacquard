package com.trsvax.jacquard.services;

import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentEventCallback;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.beanvalidator.modules.BeanValidatorModule;
import org.apache.tapestry5.ioc.AnnotationProvider;
import org.apache.tapestry5.ioc.Location;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.model.ComponentModel;
import org.apache.tapestry5.modules.TapestryModule;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.runtime.PageLifecycleCallbackHub;
import org.apache.tapestry5.runtime.PageLifecycleListener;
import org.apache.tapestry5.services.pageload.ComponentResourceSelector;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;

import com.trsvax.jacquard.modules.TestModule;

public class PageBindingTest {
	
	private static Registry registry;

	@BeforeClass
	public static void beforeClass() {
		RegistryBuilder builder = new RegistryBuilder();		 
		builder.add(TapestryModule.class,BeanValidatorModule.class,JacquardModule.class,TestModule.class);		 
		registry = builder.build();		 
		registry.performRegistryStartup();
	}

	@Test
	public void test() {
		PageBinding pageBinding = new PageBinding(location(), "View", "description", resources());
		System.out.println(pageBinding.get());
		assertTrue("/testTest/testTestView".equals(pageBinding.get()));
	}

	private ComponentResources resources() {
		// TODO Auto-generated method stub
		return new ComponentResources() {
			
			@Override
			public Location getLocation() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@SuppressWarnings("rawtypes")
			@Override
			public boolean triggerEvent(String eventType, Object[] contextValues, ComponentEventCallback callback) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@SuppressWarnings("rawtypes")
			@Override
			public boolean triggerContextEvent(String eventType, EventContext context, ComponentEventCallback callback) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isRendering() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean hasBody() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public ComponentResourceSelector getResourceSelector() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getPageName() {
				return "/test/IndexTest";
			}
			
			@Override
			public String getNestedId() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Logger getLogger() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Locale getLocale() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getId() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getElementName(String defaultElementName) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getCompleteId() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Block getBody() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Block getBlock(String blockId) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Block findBlock(String blockId) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Link createFormEventLink(String eventType, Object... context) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Link createEventLink(String eventType, Object... context) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void storeRenderVariable(String name, Object value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void renderInformalParameters(MarkupWriter writer) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void removePageLifecycleListener(PageLifecycleListener listener) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isMixin() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isBound(String parameterName) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Object getRenderVariable(String name) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <T extends Annotation> T getParameterAnnotation(String parameterName, Class<T> annotationType) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PageLifecycleCallbackHub getPageLifecycleCallbackHub() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Component getPage() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Messages getMessages() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<String> getInformalParameterNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <T> T getInformalParameter(String name, Class<T> type) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Component getEmbeddedComponent(String embeddedId) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getElementName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ComponentResources getContainerResources() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Messages getContainerMessages() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Component getContainer() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ComponentModel getComponentModel() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Component getComponent() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Class getBoundType(String parameterName) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Type getBoundGenericType(String parameterName) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Block getBlockParameter(String parameterName) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Resource getBaseResource() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public AnnotationProvider getAnnotationProvider(String parameterName) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void discardPersistentFieldChanges() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void addPageLifecycleListener(PageLifecycleListener listener) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	private Location location() {
		return new Location() {
			
			@Override
			public Resource getResource() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getLine() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getColumn() {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}

}
