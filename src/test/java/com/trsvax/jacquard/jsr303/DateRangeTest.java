package com.trsvax.jacquard.jsr303;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Set;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import com.trsvax.jacquard.modules.TestModule;

public class DateRangeTest {
	
	final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	
	@BeforeClass
	public static void setup() {
		RegistryBuilder builder = new RegistryBuilder();		 
		builder.add(TestModule.class);		 
		Registry registry = builder.build();		 
		registry.performRegistryStartup();
	}


	@SuppressWarnings("rawtypes")
	@Test
	public void test() {		
		TestRange testRange = new TestRange();
		Set constraintViolations = factory.getValidator().validate(testRange);
		assertTrue("", constraintViolations.size() == 0);
		
		testRange.start = new Date();
		constraintViolations = factory.getValidator().validate(testRange);
		assertTrue("", constraintViolations.size() == 0);
		
		testRange.start = null;
		testRange.end = new Date();
		constraintViolations = factory.getValidator().validate(testRange);
		assertTrue("", constraintViolations.size() == 0);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void test2() {
		TestRange testRange = new TestRange();
		testRange.start = new Date();
		testRange.end = new Date(testRange.start.getTime() + 1);
		Set constraintViolations = factory.getValidator().validate(testRange);
		assertTrue("", constraintViolations.size() == 0);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void test3() {
		TestRange testRange = new TestRange();
		testRange.start = new Date();
		testRange.end = new Date(testRange.start.getTime() - 1);
		Set constraintViolations = factory.getValidator().validate(testRange);
		assertTrue("", constraintViolations.size() == 1);
	}

	@DateRange(start="start",end="end")
	public class TestRange {
		Date start;
		Date end;
		
		public Date getStart() {
			return start;
		}
		
		public Date getEnd() {
			return end;
		}
	}
}
