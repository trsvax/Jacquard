package jsr303;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Set;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.junit.Test;

import com.trsvax.jacquard.jsr303.DateRange;

public class DateRangeTest {
	
	final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();


	@Test
	public void test() {
		TestRange testRange = new TestRange();
		//Set constraintViolations = factory.getValidator().validate(testRange);
		//assertTrue("", constraintViolations.size() == 0);
	}

	@DateRange(start="start",end="end")
	class TestRange {
		Date start;
		Date end;
	}
}
