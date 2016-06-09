package com.trsvax.jacquard.app.pages.mixins;

import org.apache.shiro.authz.annotation.RequiresRoles;

@RequiresRoles("user")
public class RequiresRole {

}
