package com.trsvax.jacquard.services;

import java.lang.reflect.Method;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.tynamo.security.services.SecurityService;

public class MethodSecurityService implements ObjectSecurityService<Method>{
	
	private final SecurityService securityService;
	
	public MethodSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	@Override
	public boolean hasAccess(Method o) {
		RequiresRoles requiresRoles = o.getAnnotation(RequiresRoles.class);
		if ( requiresRoles != null ) {
			for ( String role : requiresRoles.value() ) {
				if ( securityService.isLacksRole(role)) {
					return false;
				}
			}
		}
		
		RequiresPermissions requiresPermissions = o.getAnnotation(RequiresPermissions.class);
		if ( requiresPermissions != null ) {
			for ( String permission : requiresPermissions.value() ) {
				if ( securityService.isLacksRole(permission)) {
					return false;
				}
			}
		}

		return true;
	}

}
