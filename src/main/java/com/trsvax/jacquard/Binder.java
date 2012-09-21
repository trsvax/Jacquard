package com.trsvax.jacquard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Binder {
	
	public Class implementation();
	public boolean eagerLoad() default false;
	public boolean preventDecoration() default false;
	public boolean preventReloading() default false;
	public String scope() default "";
	public String id() default "";
	//Use marker annotation instead
	//public Class marker() default Object.class;
	public boolean simpleId() default false;

}
