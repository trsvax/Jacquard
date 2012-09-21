package com.trsvax.jacquard.mixins;


import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.BindParameter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.beaneditor.PropertyModel;

import com.trsvax.jacquard.environment.GridEnvironment;

public class PropertyCell {
	@BindParameter
	private PropertyModel model;
	
	@Environmental
	private GridEnvironment environment;
	
	@BeginRender
	void beginRender(MarkupWriter writer) {
		if ( environment.includeCellDatatypes() ) {
			writer.getElement().addClassName(
					model.getDataType() == null ? model.getConduit().getPropertyType().getSimpleName() : model.getDataType());		
		}
	}

}
