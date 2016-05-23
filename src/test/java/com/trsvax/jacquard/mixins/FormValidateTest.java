package com.trsvax.jacquard.mixins;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.PropertyConduit;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.beaneditor.PropertyModel;
import org.apache.tapestry5.beaneditor.RelativePosition;
import org.apache.tapestry5.beanvalidator.modules.BeanValidatorModule;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.internal.services.MarkupWriterImpl;
import org.apache.tapestry5.modules.TapestryModule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.trsvax.jacquard.IOC;
import com.trsvax.jacquard.jsr303.DateRange;
import com.trsvax.jacquard.modules.TestModule;
import com.trsvax.jacquard.services.JacquardModule;

public class FormValidateTest {
	
	
	@Rule
	public IOC ioc = new IOC(TapestryModule.class,BeanValidatorModule.class,JacquardModule.class,TestModule.class);
	
	//private static Registry registry;

	private FormValidate formValidate;
	
	List<String> errors;

	@Before
	public void before() {
		formValidate = ioc.start().autobuild(FormValidate.class);
		errors = new ArrayList<>();
		formValidate.beanEditForm = new BeanEditForm() {
			public void recordError(String error) {
				errors.add(error);
			}			
		};
	}


	@Test
	public void validateError() {
		Bean bean = new Bean();
		bean.start = new Date();
		bean.end = new Date(bean.start.getTime()-1);
		formValidate.object = bean;
		
		formValidate.onValidate();
		assertTrue(errors.size() == 1);
	}
	
	@Test
	public void validatePass() {
		Bean bean = new Bean();
		bean.start = new Date();
		bean.end = new Date(bean.start.getTime()+1);
		formValidate.object = bean;
		formValidate.onValidate();
		assertTrue(errors.size() == 0);
	}
	
	@Test
	public void beginRender1() {
		Bean bean = new Bean();
		formValidate.object = bean;
		formValidate.model = model(null);
		MarkupWriter writer = new MarkupWriterImpl();
		formValidate.afterRender( writer );
	}
	
	@Test
	public void beginRender2() {
		Bean bean = new Bean();
		formValidate.object = bean;
		formValidate.model = model(Bean.class);
		MarkupWriter writer = new MarkupWriterImpl();
		formValidate.afterRender( writer );
	}
	
	BeanModel<Bean> model(final Class<Bean> type) {
		return new BeanModel<Bean>() {

			@Override
			public PropertyModel add(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public PropertyModel add(String arg0, PropertyConduit arg1) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public PropertyModel add(RelativePosition arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public PropertyModel add(RelativePosition arg0, String arg1, String arg2, PropertyConduit arg3) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public PropertyModel addEmpty(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public PropertyModel addExpression(String arg0, String arg1) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public BeanModel<Bean> exclude(String... arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public PropertyModel get(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Class<Bean> getBeanType() {
				return type;
			}

			@Override
			public PropertyModel getById(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<String> getPropertyNames() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public BeanModel<Bean> include(String... arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Bean newInstance() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public BeanModel<Bean> reorder(String... arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}


	@DateRange(start="start",end="end")
	public class Bean {
		@NotNull
		Date start;
		
		@NotNull
		Date end;

		public Date getStart() {
			return start;
		}

		public void setStart(Date start) {
			this.start = start;
		}

		public Date getEnd() {
			return end;
		}

		public void setEnd(Date end) {
			this.end = end;
		}

		
		
		
	}
}
