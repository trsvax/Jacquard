package com.trsvax.jacquard.jsr303;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;

@Target( { ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DateRangeValidator.class} )
@Documented
public @interface DateRange {
	
	String message() default "{end} should be later than {start}";
    String start();
    String end();
    @SuppressWarnings("rawtypes")
	Class[] groups() default {};
    @SuppressWarnings("rawtypes")
	Class[] payload() default {};

}
