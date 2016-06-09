package com.trsvax.jacquard.mixins;

import org.apache.tapestry5.annotations.BindParameter;
import org.apache.tapestry5.annotations.MixinAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ComponentClassResolver;

import com.trsvax.jacquard.services.ObjectSecurityService;

@MixinAfter
public class PermissionedPageLink {
	
	@BindParameter
	String page;
	
	@Inject
	ComponentClassResolver resolver;
	
	@Inject
	ObjectSecurityService<Class<?>> objectSecurityService;
	
	boolean beforeRenderBody() throws ClassNotFoundException
    {
		String className = resolver.resolvePageNameToClassName(page);
		Class<?> pageClass = Class.forName(className);		
        return objectSecurityService.hasAccess(pageClass);
    }
	

}
