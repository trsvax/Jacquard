package com.trsvax.jacquard.mixins;


import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.BindParameter;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.PropertyModel;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Environment;

import com.trsvax.jacquard.environment.GridCellEnviroment;
import com.trsvax.jacquard.environment.GridCellEnvironmentImpl;
import com.trsvax.jacquard.environment.GridEnvironment;

public class PropertyCell {
	@BindParameter
	private PropertyModel model;
	
	@BindParameter
	private Object object;
	
	@Inject
	private Environment environment;
	
	@BeginRender
	void beginRender(MarkupWriter writer) {
		environment.push(GridCellEnviroment.class, new GridCellEnvironmentImpl(object,model));
		GridEnvironment gridEnvironment = environment.peek(GridEnvironment.class);
		if ( gridEnvironment.includeCellDatatypes() && model.getConduit() != null ) {
			writer.getElement().addClassName(
					model.getDataType() == null ? 
							model.getConduit().getPropertyType().getSimpleName() 
							: model.getDataType());		
		}
	}
	
	@AfterRender
	void afterRender() {
		environment.pop(GridCellEnviroment.class);
	}

}
