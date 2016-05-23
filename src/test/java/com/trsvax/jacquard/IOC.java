package com.trsvax.jacquard;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class IOC implements TestRule {
	
	public IOC() {	
	}
	
	public IOC(Class<?> ... modules) {
		key = key(modules);
		//startup(modules);
	}
	
	Map<String,Registry> registries;
	String key;
	
	public Registry start(Class<?> ... modules) {
		key = key(modules);
		if ( registries.containsKey(key) ) {
			return registries.get(key);
		}
		return startup(modules);
	}
	
	public Registry start() {
		System.out.println(key);
		if ( registries.containsKey(key) ) {
			return registries.get(key);
		}
		return startup((Class<?>[]) null);
	}

	@Override
	public Statement apply(final Statement base, final Description description) {
		return new Statement() {

			
			@Override
			public void evaluate() throws Throwable {
				Modules modules = description.getAnnotation(Modules.class);
				if ( modules != null ) {
					key = key(modules.value());
				} 
				try {
					base.evaluate();
				} finally {
					
				}
			}
		};
	}
	
	Registry startup(Class<?> ... modules) {
		RegistryBuilder builder = new RegistryBuilder();		 
		//builder.add(modules);		 
		Registry registry = builder.build();		 
		//registry.performRegistryStartup();
		key = key(modules);
		registries.put(key, registry);
		return registry;
	}

	String key(Class<?> ... modules ){
		return Arrays.stream(modules).map(c -> c.getName()).sorted().collect(Collectors.joining());
	}
}
