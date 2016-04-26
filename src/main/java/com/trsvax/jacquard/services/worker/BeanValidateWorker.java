package com.trsvax.jacquard.services.worker;

import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;

import com.trsvax.jacquard.mixins.BeanEditTemplate;
import com.trsvax.jacquard.mixins.FormValidate;

public class BeanValidateWorker implements ComponentClassTransformWorker2 {

	@Override
	public void transform(PlasticClass plasticClass, TransformationSupport support, MutableComponentModel model) {
		if ( BeanEditForm.class.getName().equals(plasticClass.getClassName()) ) {
			model.addMixinClassName(FormValidate.class.getName());
			model.addMixinClassName(BeanEditTemplate.class.getName());
		}
	}

}
