package com.trsvax.jacquard.environment;

public class GridEnvironmentImpl implements GridEnvironment {
	private Boolean lean;


	public Boolean getLean() {
		return lean;
	}

	public GridEnvironment withLean(Boolean lean) {
		this.lean = lean;
		return this;
	}

	@Override
	public Boolean includeCellDatatypes() {
		return !lean;
	}

}
