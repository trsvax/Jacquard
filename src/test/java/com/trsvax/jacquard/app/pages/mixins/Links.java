package com.trsvax.jacquard.app.pages.mixins;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;

public class Links {
	
	void onPublic() {
		
	}
	
	@RequiresRoles("user")
	void onRole() {
		
	}
	
	@RequiresPermissions("access")
	void onPermission() {
		
	}

}
