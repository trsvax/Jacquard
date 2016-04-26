package com.trsvax.jacquard.services;

import org.apache.tapestry5.ioc.ObjectLocator;

public class AppGlobals  implements Runnable {
	
	public static ObjectLocator locator;

	public AppGlobals(ObjectLocator locator) {
		AppGlobals.locator = locator;
	}

	@Override
	public void run() {
		AppGlobals.locator = null;
	}

}
