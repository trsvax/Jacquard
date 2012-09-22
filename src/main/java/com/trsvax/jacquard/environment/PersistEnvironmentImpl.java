package com.trsvax.jacquard.environment;

import java.util.ArrayList;
import java.util.List;

public class PersistEnvironmentImpl implements PersistEnvironment {
	private final List<String> include = new ArrayList<String>();
	
	public PersistEnvironmentImpl(String include) {
		if ( include != null && include.length() > 0 ) {
			for ( String v : include.split(",")) {
				this.include.add(v.trim());
			}
		}
	}

	@Override
	public List<String> include() {
		return include;
	}
	

}
