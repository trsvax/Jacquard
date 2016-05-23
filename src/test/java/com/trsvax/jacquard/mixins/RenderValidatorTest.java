package com.trsvax.jacquard.mixins;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;

import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.AfterRenderBody;
import org.apache.tapestry5.annotations.AfterRenderTemplate;
import org.apache.tapestry5.annotations.BeforeRenderTemplate;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.CleanupRender;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.beanvalidator.BeanValidatorSource;
import org.junit.Before;
import org.junit.Test;

public class RenderValidatorTest {

	final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	RenderValidator renderValidator;
	TestPage page;

	@Before
	public void before() {
		renderValidator = new RenderValidator();
		page = new TestPage();
		renderValidator.beanValidatorSource = new BeanValidatorSource() {
			
			@Override
			public ValidatorFactory getValidatorFactory() {
				return factory;
			}
			
			@Override
			public Validator create() {
				return null;
			}
		};
		renderValidator.container = page;
	}
	
	@Test
	public void setupRender() {
		try {
			renderValidator.setupRender();
			fail("No ConstraintViolationException");
		} catch ( ConstraintViolationException e ) {
			assertTrue(e.getConstraintViolations().size() == 1);
		}
		page.setupRender = "setupRender";
		renderValidator.setupRender();
		assertTrue(renderValidator.constraintViolations.size() == 0);		
	}
	
	@Test
	public void beginRender() {
		try {
			renderValidator.beginRender();
			fail("No ConstraintViolationException");
		} catch ( ConstraintViolationException e ) {
			assertTrue(e.getConstraintViolations().size() == 1);
		}
		page.beginRender = "beginRender";
		renderValidator.beginRender();
		assertTrue(renderValidator.constraintViolations.size() == 0);		
	}
	
	@Test
	public void beforeRenderTemplate() {
		try {
			renderValidator.beforeRenderTemplate();
			fail("No ConstraintViolationException");
		} catch ( ConstraintViolationException e ) {
			assertTrue(e.getConstraintViolations().size() == 1);
		}
		page.beforeRenderTemplate = "beforeRenderTemplate";
		renderValidator.beforeRenderTemplate();
		assertTrue(renderValidator.constraintViolations.size() == 0);		
	}
	
	@Test
	public void afterRenderBody() {
		try {
			renderValidator.afterRenderBody();
			fail("No ConstraintViolationException");
		} catch ( ConstraintViolationException e ) {
			assertTrue(e.getConstraintViolations().size() == 1);
		}
		page.afterRenderBody = "afterRenderBody";
		renderValidator.afterRenderBody();
		assertTrue(renderValidator.constraintViolations.size() == 0);		
	}
	
	@Test
	public void afterRenderTemplate() {
		try {
			renderValidator.afterRenderTemplate();
			fail("No ConstraintViolationException");
		} catch ( ConstraintViolationException e ) {
			assertTrue(e.getConstraintViolations().size() == 1);
		}
		page.afterRenderTemplate = "afterRenderTemplate";
		renderValidator.afterRenderTemplate();
		assertTrue(renderValidator.constraintViolations.size() == 0);		
	}
	
	@Test
	public void afterRender() {
		try {
			renderValidator.afterRender();
			fail("No ConstraintViolationException");
		} catch ( ConstraintViolationException e ) {
			assertTrue(e.getConstraintViolations().size() == 1);
		}
		page.afterRender = "afterRender";
		renderValidator.afterRender();
		assertTrue(renderValidator.constraintViolations.size() == 0);		
	}
	
	@Test
	public void cleanupRender() {
		try {
			renderValidator.cleanupRender();
			fail("No ConstraintViolationException");
		} catch ( ConstraintViolationException e ) {
			assertTrue(e.getConstraintViolations().size() == 1);
		}
		page.cleanupRender = "cleanupRender";
		renderValidator.cleanupRender();
		assertTrue(renderValidator.constraintViolations.size() == 0);		
	}
	
	public class TestPage {
		@NotNull(groups={SetupRender.class})
		String setupRender;
		
		@NotNull(groups={BeginRender.class})
		String beginRender;
		
		@NotNull(groups={BeforeRenderTemplate.class})
		String beforeRenderTemplate;
		
		@NotNull(groups={AfterRenderBody.class})
		String afterRenderBody;
		
		@NotNull(groups={AfterRenderTemplate.class})
		String afterRenderTemplate;
		
		@NotNull(groups={AfterRender.class})
		String afterRender;
		
		@NotNull(groups={CleanupRender.class})
		String cleanupRender;
	}

}
