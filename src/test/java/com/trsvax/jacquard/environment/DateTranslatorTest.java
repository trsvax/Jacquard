package com.trsvax.jacquard.environment;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class DateTranslatorTest {

	@Test
	public void test() {
		DateTranslatorEnv env = new DateTranslatorImpl();
		Map<String,String> map = new HashMap<>();
		env.setAttributes(map);
		
		env.setFormatString("formatString");
		env.setStackName("stackName");
		env.setTypeOverride("typeOverride");
		
		
		assertTrue(env.getAttributes() == map);
		assertTrue("formatString".equals(env.getFormatString()));
		assertTrue("stackName".equals(env.getStackName()));
		assertTrue("typeOverride".equals(env.getTypeOverride()));
	}

}
