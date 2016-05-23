package com.trsvax.jacquard.environment;

import java.util.Map;

/**
 * 
 * 
 * @author bfb
 *
 */
public interface DateTranslatorEnv {

	public void setFormatString(String formatString);
	public void setStackName(String stackName);
	public void setTypeOverride(String typeOverride);
	public void setAttributes(Map<String,String> attributes);
	
	public String getFormatString();
	public String getStackName();
	public String getTypeOverride();
	public Map<String,String> getAttributes();
}
