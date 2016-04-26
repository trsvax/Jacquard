package com.trsvax.jacquard.app.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
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

}
