package adcowebsolutions.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

public class DateUtilsTest {
	
	private DateUtils dateUtils;

	@Before
	public void setUp() {
		dateUtils = new DateUtils();
	}

	@Test
	public void testGetYear() {

		assertNotNull(dateUtils);
	
		Calendar cal = Calendar.getInstance();
		cal.set(2011, 6, 2);
		String year = dateUtils.getYear(cal.getTime());
		assertEquals("2011", year);
	
		cal = Calendar.getInstance();
		cal.set(2009, 6, 2);
		year = dateUtils.getYear(cal.getTime());
		assertNotSame("2011", year);
	}

}
