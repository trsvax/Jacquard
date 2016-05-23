package com.trsvax.jacquard.services.translators;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.beanvalidator.modules.BeanValidatorModule;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;
import org.apache.tapestry5.modules.TapestryModule;
import org.junit.BeforeClass;
import org.junit.Test;

import com.trsvax.jacquard.modules.TestModule;
import com.trsvax.jacquard.services.JacquardModule;

public class DateTranslatorTest {
	
	private static Registry registry;

	@BeforeClass
	public static void beforeClass() {
		RegistryBuilder builder = new RegistryBuilder();		 
		builder.add(TapestryModule.class,BeanValidatorModule.class,JacquardModule.class,TestModule.class);		 
		registry = builder.build();		 
		registry.performRegistryStartup();
	}

	@SuppressWarnings("unused")
	@Test
	public void test() {
		try {
			DateTranslator dateTranslator = registry.autobuild(DateTranslator.Builder.class).build();
		} catch (ConstraintViolationException e) {
			assertTrue(e.getConstraintViolations().size() > 0);

		}
	}
	
	@Test
	public void test1() {
		DateTranslator dateTranslator = registry.autobuild(DateTranslator.Builder.class)
					.setName("Date")
					.setType(Date.class)
					.build();
		assertTrue(dateTranslator != null);
	}
	
	@Test
	public void test2() {
		DateTranslator dateTranslator = registry.autobuild(DateTranslator.Builder.class)
					.setName("Date")
					.setType(Date.class)
					.build();
		String date = dateTranslator.toClient(new Date());
		assertTrue(date != null);
	}
	
	@Test
	public void test3() throws ValidationException {
		DateTranslator dateTranslator = registry.autobuild(DateTranslator.Builder.class)
					.setName("Date")
					.setType(Date.class)
					.build();
		Date date = dateTranslator.parseClient(null, "01/01/2000", null);
		assertTrue(date != null);
	}

}
