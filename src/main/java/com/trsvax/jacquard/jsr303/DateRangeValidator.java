package com.trsvax.jacquard.jsr303;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.tapestry5.ioc.services.PropertyAccess;

import com.trsvax.jacquard.services.AppGlobals;

public class DateRangeValidator implements ConstraintValidator<DateRange, Object>{
	
	private String start;
	private String end;

	@Override
	public void initialize(DateRange dateRange) {
		this.start = dateRange.start();
		this.end = dateRange.end();
		
	}

	@Override
	public boolean isValid(Object instance, ConstraintValidatorContext context) {
		PropertyAccess propertyAccess = AppGlobals.locator.getService(PropertyAccess.class);
		Date startDate = (Date) propertyAccess.get(instance, start);
		Date endDate = (Date) propertyAccess.get(instance, end);
		if ( startDate == null || endDate == null ) {
			return true;
		}
		return startDate.before(endDate);
	}
	


}
