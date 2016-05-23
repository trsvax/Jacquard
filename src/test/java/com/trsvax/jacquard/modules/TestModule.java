package com.trsvax.jacquard.modules;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.apache.tapestry5.ioc.services.RegistryShutdownHub;

import com.trsvax.jacquard.services.AppGlobals;

public class TestModule {
	
	@Startup
	  public static void initMyApplication(RegistryShutdownHub hub, ObjectLocator locator)
	  {
		hub.addRegistryShutdownListener(new AppGlobals(locator));
	  }
	
	 public static void contributeApplicationDefaults(MappedConfiguration<String,String> configuration)
	  {
	    configuration.add(SymbolConstants.APPLICATION_CATALOG, "com.trsvax.jacquard");
	    configuration.add("tapestry.app-package", "com.trsvax.jacquard");
	  }

}
