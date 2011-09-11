package adcowebsolutions.utils;

import java.util.Date;

public class ApplicationDate {
	
	private String yearCreated;

	private String fromAndTo;

	public String getYearCreated() {
		return yearCreated;
	}

	public void setYearCreated(String yearCreated) {
		this.yearCreated = yearCreated;
	}

	public String getFromAndTo() {

		Date now = new Date();
	
		String currentYear = new DateUtils().getYear(now);
	
		if (yearCreated.equals(currentYear)) {
			fromAndTo = currentYear;
		} else {
			fromAndTo = yearCreated + " - " + currentYear;
		}
	
		return fromAndTo;
	}
}
