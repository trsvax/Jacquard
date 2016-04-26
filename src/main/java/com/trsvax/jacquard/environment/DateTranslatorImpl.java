package com.trsvax.jacquard.environment;

import java.util.Map;

public class DateTranslatorImpl implements DateTranslatorEnv { 
	
	private String moduleName; 
	private String formatString;
	private String typeOverride;
	private Map<String,String> attributes;
	
	@Override
	public void setFormatString(String formatString) {
		this.formatString = formatString;
	}

	@Override
	public void setStackName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	@Override
	public void setTypeOverride(String typeOverride) {
		this.typeOverride = typeOverride;;
	}

	@Override
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	
	public String getFormatString() {
		return formatString;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	
	public String getTypeOverride() {
		return typeOverride;
	}
	
	public Map<String,String> getAttributes() {
		return attributes;
	}

}
