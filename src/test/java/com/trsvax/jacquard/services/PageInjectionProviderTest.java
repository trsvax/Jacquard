package com.trsvax.jacquard.services;

import org.apache.tapestry5.beanvalidator.modules.BeanValidatorModule;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;
import org.apache.tapestry5.modules.TapestryModule;
import org.junit.BeforeClass;

import com.trsvax.jacquard.modules.TestModule;

public class PageInjectionProviderTest {
	
	private static Registry registry;

	@BeforeClass
	public static void beforeClass() {
		RegistryBuilder builder = new RegistryBuilder();		 
		builder.add(TapestryModule.class,BeanValidatorModule.class,JacquardModule.class,TestModule.class);		 
		registry = builder.build();		 
		registry.performRegistryStartup();
	}

	//@Test
	public void test() {
		//Page page = registry.autobuild(Page.class);
		//assertTrue(page.objectEdit != null);
	}

}
