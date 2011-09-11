package adcowebsolutions.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

public class ApplicationDateTest {

	private ApplicationDate applicationDate;

	@Before
	public void setUp() {
		applicationDate = new ApplicationDate();
	}

	@Test
	public void testOneYear() {

		assertNotNull(applicationDate);

		applicationDate.setYearCreated("2011");
		String fromToYearString = applicationDate.getFromAndTo();
		assertEquals("2011", fromToYearString);
	}

	@Test
	public void testMultipleYears() {

		assertNotNull(applicationDate);
	
		Calendar cal = Calendar.getInstance();
		cal.set(2011, 6, 2);
		String currentYear = new DateUtils().getYear(cal.getTime());
	
		applicationDate.setYearCreated("2008");
		String fromToYearString = applicationDate.getFromAndTo();
		assertEquals("2008 - "+currentYear, fromToYearString);
	}
}
