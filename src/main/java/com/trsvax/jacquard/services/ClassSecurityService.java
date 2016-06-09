package com.trsvax.jacquard.services;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.tynamo.security.services.SecurityService;

public class ClassSecurityService implements ObjectSecurityService<Class<?>> {
	
	private final SecurityService securityService;
	
	public ClassSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	@Override
	public boolean hasAccess(Class<?> o) {
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
