package com.trsvax.jacquard.services;


import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.DateField;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.corelib.components.GridCell;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;

import com.trsvax.jacquard.mixins.BeanEditDefaults;
import com.trsvax.jacquard.mixins.GridEnviromentMixin;
import com.trsvax.jacquard.mixins.PropertyCell;


public class AddMixinWorker implements ComponentClassTransformWorker2 {

	public void transform(PlasticClass plasticClass, TransformationSupport support, MutableComponentModel model) {
		if ( match(BeanEditForm.class, plasticClass) ) {
			model.addMixinClassName(BeanEditDefaults.class.getName());
		}
		if ( match(Grid.class, plasticClass) ) {
			model.addMixinClassName(GridEnviromentMixin.class.getName());
		}
		if ( match(GridCell.class,plasticClass)) {
			model.addMixinClassName(PropertyCell.class.getName());
		}
		
	}
	
	boolean match( Class clazz, PlasticClass plasticClass) {
		return clazz.getName().equals(plasticClass.getClassName());
	}

}
