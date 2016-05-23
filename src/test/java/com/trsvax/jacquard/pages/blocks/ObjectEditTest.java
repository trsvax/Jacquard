package com.trsvax.jacquard.pages.blocks;

import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.apache.tapestry5.Field;
import org.apache.tapestry5.FieldTranslator;
import org.apache.tapestry5.FieldValidator;
import org.apache.tapestry5.beanvalidator.modules.BeanValidatorModule;
import org.apache.tapestry5.ioc.MessageFormatter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;
import org.apache.tapestry5.modules.TapestryModule;
import org.apache.tapestry5.services.PropertyEditContext;
import org.junit.BeforeClass;
import org.junit.Test;

import com.trsvax.jacquard.modules.TestModule;
import com.trsvax.jacquard.services.JacquardModule;

public class ObjectEditTest {
	
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
		ObjectEdit page = registry.autobuild(ObjectEdit.class);
		page.context = context(Bean.class);
		assertTrue(page.getModel().getPropertyNames().size() == 1);
	}
	
	public class Bean {
		String field;

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}
		
		
	}
	
	@SuppressWarnings("rawtypes")
	PropertyEditContext context(final Class clazz) {
		return new PropertyEditContext() {
			
			@Override
			public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void setPropertyValue(Object value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public FieldValidator getValidator(Field field) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public FieldTranslator getTranslator(Field field) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getPropertyValue() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Class getPropertyType() {
				return clazz;
			}
			
			@Override
			public String getPropertyId() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getLabel() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Messages getContainerMessages() {
				return messages();
			}
		};
	}
	
	Messages messages() {
		return new Messages() {
			
			@Override
			public Set<String> getKeys() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public MessageFormatter getFormatter(String key) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String get(String key) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String format(String key, Object... args) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean contains(String key) {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}

}
