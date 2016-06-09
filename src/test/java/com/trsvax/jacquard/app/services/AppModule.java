package com.trsvax.jacquard.app.services;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.ImportModule;

import com.trsvax.jacquard.services.JacquardModule;
import com.trsvax.webjars.services.WebJarsModule;

@ImportModule({JacquardModule.class, WebJarsModule.class})
public class AppModule {
	
	public static void contributeApplicationDefaults(MappedConfiguration<String, Object> configuration) {

        configuration.add(SymbolConstants.PRODUCTION_MODE, false);
        configuration.add(SymbolConstants.HMAC_PASSPHRASE, "jacquard");    
       configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");
        configuration.add(SymbolConstants.ENABLE_PAGELOADING_MASK, "false");

    }
	
	@Contribute(WebSecurityManager.class)
	public static void addRealms(Configuration<Realm> configuration) {
		
		configuration.add( new Realm() {
			
			@Override
			public boolean supports(AuthenticationToken arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public AuthenticationInfo getAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}

}
