package com.trsvax.jacquard.mixins;

import java.lang.reflect.Method;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.BindParameter;
import org.apache.tapestry5.annotations.MixinAfter;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.slf4j.Logger;

import com.trsvax.jacquard.services.ObjectSecurityService;

@MixinAfter
public class PermissionedEventLink {
	
	@BindParameter
	String event;
	
	@Inject
	ComponentResources componentResources;
	
	@Inject
	ObjectSecurityService<Method> objectSecurityService;
	
	boolean beforeRenderBody() throws ClassNotFoundException
    {
		Method method = null;

				
		Class<?> pageClass = componentResources.getPage().getClass();
		for ( Method m : pageClass.getDeclaredMethods()) {
			if ( m.getName().toLowerCase().equals("on" + event.toLowerCase())) {
				method = m;
				break;
			}
			OnEvent event = m.getAnnotation(OnEvent.class);
			if ( event != null ) {
				if ( event.value().equals(event)) {
					method = m;
					break;
				}
			}
			
		}
		if ( method == null ) {
			return true;
		}
		
        return objectSecurityService.hasAccess(method);
    }

}
