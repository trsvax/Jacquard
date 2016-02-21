package com.trsvax.jacquard.base;

import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.corelib.base.AbstractField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.FormSupport;

//import com.trsvax.jacquard.services.CompositeService;


public abstract class AbstractCompositeField <T> extends AbstractField {
	
	@Environmental
	private FormSupport formSupport;
	
	//@Inject
	//private CompositeService compositeService;

	@Override
	protected void processSubmission(String controlName) {
		final T value = process(controlName);
		formSupport.defer( new Runnable() {			
			@Override
			public void run() {
				validate(value);
			}
		});
	}
	
	public T autoBuild(Class<T> clazz, T value) {
		if ( value != null ) {
			return value;
		}
		//return compositeService.getLocator().autobuild(clazz);
		return null;
	}
	
	protected abstract T process(String controlName);
	protected abstract void validate(T value);

}
