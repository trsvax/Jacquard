package com.trsvax.jacquard.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.trsvax.jacquard.JQuerySubscribe;
import com.trsvax.jacquard.SubscribeWorker;

@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {
	public Class<? extends SubscribeWorker> type() default JQuerySubscribe.class;
	public String selector() default "document";
	public String event() default "";
	public String[] args() default {};
	public boolean returnValue() default false;
}
