package adcowebsolutions.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
private static final String YEAR_FORMAT = "yyyy";
	
	/**
     * This method will format date
     * Input parameter date format: 2004-03-08 09:42:42.687001 (yyyy-mm-dd hh:mm:ss.ms)
     * @return java.lang.String in dd-mm-yyyy hh:mm:ss (or hh.mm.ss) format.
     */
    public static String getDateTime(java.lang.String date) {
        
		if(date == null)	return null;
		if(date.trim().equalsIgnoreCase(""))	return null;        
        //Get year
        String year = date.substring(0, 4);

        //Get day
        String month = date.substring(5, 7);

        //Get day
        String day = date.substring(8, 10);

        //Get Hour
        String hhmmss = date.substring(11, 19);
        String dateTime =day + "-" + month + "-" + year + " " +
                hhmmss;

        return dateTime;
    }

    /**
     * This method will format date
     * Input parameter date format: 2004-03-08 09:42:42.687001 (yyyy-mm-dd hh:mm:ss.ms)
     * @return java.lang.String in dd-mm-yyyy format.
     */
    public static java.lang.String getDate(String sDate) {
        
		if(sDate == null)	return null;
		if(sDate.trim().equalsIgnoreCase(""))	return null;        
        //Get year
        String year = sDate.substring(0, 4);

        //Get day
        String month = sDate.substring(5, 7);

        //Get day
        String day = sDate.substring(8, 10);
        String date = day + "-" + month + "-" + year;

        //return dbDate;
        return date;
    }

    /**
     * This method will format date from YYYYMMDD to MM/DD/YYYY
     * Input parameter date format: YYYYMMDD
     * @return java.lang.String in MM/DD/YYYY format.
     */
    public static java.lang.String getMACDate(String sDate) {
        
		if(sDate == null)	return null;
		if(sDate.trim().equalsIgnoreCase(""))	return null;        
        //Get year
        String year = sDate.substring(0, 4);

        //Get month
        String month = sDate.substring(4, 6);

        //Get day
        String day = sDate.substring(6, 8);

        String date = month + "/" + day + "/" + year;

        //return dbDate;
        return date;
    }
    
	/**
	 * This method will format date from YYYYMMDD to YYYY-MM-DD
	 * Input parameter date format: YYYYMMDD
	 * 
	 * @return java.lang.String in YYYY-MM-DD format.
	 */
	public static java.lang.String getDBDate(String sDate) {
        
		if(sDate == null)	return null;
		if(sDate.trim().equalsIgnoreCase(""))	return null;        
		
		//Get year
		String year = sDate.substring(0, 4);
		
		//Get month
		String month = sDate.substring(4, 6);

		//Get day
		String day = sDate.substring(6, 8);

		String date = year + "-" + month + "-" + day;
		//return dbDate;
		return date;
	}
	
	/**
	 * This method will format date from YYYYMMDD to DD-MM-YYYY
	 * Input parameter date format: YYYYMMDD
	 * 
	 * @return java.lang.String in DD-MM-YYYY format.
	 */
	public static java.lang.String convertFromMACDate(String sDate) {
       
		if(sDate == null)	return null;
		if(sDate.trim().equalsIgnoreCase(""))	return null;        
		
		//Get year
		String year = sDate.substring(0, 4);
		
		//Get month
		String month = sDate.substring(4, 6);

		//Get day
		String day = sDate.substring(6, 8);

		String date = day + "-" + month + "-" + year;
		//return dbDate;
		return date;
	}

    /**
     * This method will format date from YYYY-MM-DD to YYYYMMDD
     * Input parameter date format: YYYY-MM-DD
     * @return java.lang.String in YYYYMMDD format.
     */
    public static java.lang.String ConvertToMACDate(String sDate) {
		
		if(sDate == null)	return null;
		if(sDate.trim().equalsIgnoreCase(""))	return null;		
		//Get year
		String year = sDate.substring(0, 4);

        //Get month
        String month = sDate.substring(5, 7);

        //Get day
        String day = sDate.substring(8, 10);


        String date = year + month + day;
        
        //return dbDate;
        return date;
    }
	/**
	 * This method will format date from DD-MM-YYYY to YYYY-MM-DD
	 * Input parameter date format: DD-MM-YYYY
	 * @return java.lang.String in YYYY-MM-DD format.
	 */
	public static java.lang.String searchDate(String sDate) {
		
		if(sDate == null)	return null;
		if(sDate.trim().equalsIgnoreCase(""))	return null;

		//Get day
		String day = sDate.substring(0, 2);

		//Get month
		String month = sDate.substring(3, 5);

		//Get year
		String year = sDate.substring(6, 10);

		String date = year +"-"+ month +"-"+ day;
        
		//return dbDate;
		return date;
	}
	
	/**
	 * This method returns a Timestamp value for a given String. 
	 * 
	 * @param String dateTime in the format of dd-MM-yyyy hh:mm:ss
	 *
	 * @return Timestamp in the fomrat of dd-MM-yyyy HH:mm:ss (24 hour)
	 */
	public static java.sql.Timestamp getTimestampValue(java.lang.String dateTime) throws ParseException {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		java.util.Date date = sdf.parse(dateTime);
		return new java.sql.Timestamp(date.getTime());
	}
	
	/**
	 * This method will format input date and time in the format of (yyyymmddhhmmss) and
	 * return a Timestamp value in the format of (yyyy-dd-MM HH:mm:ss)
	 * 
	 * @return java.sql.Timestamp in yyyy-dd-MM HH:mm:ss (Time 24 hour) format.
	 * @author Axis WebView Team
	 */
	public static java.sql.Timestamp formatToTimestampValue(java.lang.String dateTime) throws ParseException {
		
		if(dateTime == null)	return null;
		if(dateTime.trim().equalsIgnoreCase(""))	return null;        
		//Get year
		String year = dateTime.substring(0, 4);

		//Get month
		String month = dateTime.substring(4, 6);

		//Get day
		String day = dateTime.substring(6, 8);

		//Get hours
		String hours = dateTime.substring(8, 10);
				
		//Get minutes
		String minutes = dateTime.substring(10, 12);	
				
		String newDateTime = day + "-" + month + "-" + year + " " + hours + ":" + minutes + ":00";

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		java.util.Date date = sdf.parse(newDateTime);
		return new java.sql.Timestamp(date.getTime());
	}
	
	/**
	 * This method will format input date (no time component, defaults time to midnight) in the format of (dd/MM/yyyy) and
	 * return a Timestamp value in the format of (yyyy-dd-MM HH:mm:ss)
	 * 
	 * @param java.lang.String in dd/MM/yyyy
	 * 
	 * @return java.sql.Timestamp in yyyy-dd-MM HH:mm:ss (Time 24 hour) format.
	 * 
	 * @author Axis WebView Team
	 */
	public static java.sql.Timestamp formatDateToTimestampValue(java.lang.String dateTime) throws ParseException {
		
		if(dateTime == null)	return null;
		if(dateTime.trim().equalsIgnoreCase(""))	return null;        
		//Get day
		String day = dateTime.substring(0, 2);
		
		//Get month
		String month = dateTime.substring(3, 5);
		
		//Get year
		String year = dateTime.substring(6, 10);
		
		String newDateTime = day + "-" + month + "-" + year + " " + "00:00:00";

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		java.util.Date date = sdf.parse(newDateTime);
		return new java.sql.Timestamp(date.getTime());
	}
	
	/**
	 * This method will format input date and time in the format of (dd-mm-yyyy HH:mm:ss) and
	 * return a Timestamp value in the format of (yyyy-dd-MM HH:mm:ss)
	 * 
	 * @return java.sql.Timestamp in yyyy-dd-MM HH:mm:ss (Time 24 hour) format.
	 *
	 */
	public static java.sql.Timestamp getTimestampYearFirst(java.lang.String dateTime) throws ParseException {
		
		if(dateTime == null)	return null;
		if(dateTime.trim().equalsIgnoreCase(""))	return null;        
		
		//Get day
		String day = dateTime.substring(0, 2);
		
		//Get month
		String month = dateTime.substring(3, 5);
		
		//Get year
		String year = dateTime.substring(6, 10);
		
		String hrsMinSec = dateTime.substring(11);

		String newDateTime = year + "-" + month + "-" + day + " " + hrsMinSec; 

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date = sdf.parse(newDateTime);
		java.sql.Timestamp newTimeStamp = new java.sql.Timestamp(date.getTime());
		return newTimeStamp;
	}
	
	
	/**
	 * This method will format input date and time in the format of (dd-mm-yyyy HH:mm:ss) and
	 * return a Timestamp value in the format of (yyyy-dd-MM HH:mm:ss)
	 * 
	 * @return java.sql.Timestamp in yyyy-dd-MM HH:mm:ss (Time 24 hour) format.
	 */
	public static java.sql.Timestamp getTimestampDayFirst(java.lang.String dateTime) throws ParseException {
		
		if(dateTime == null)	return null;
		if(dateTime.trim().equalsIgnoreCase(""))	return null;        
				
		//Get year		
		String year = dateTime.substring(0, 4);
			
		//Get month		
		String month = dateTime.substring(5, 7);
				
		//Get day		
		String day = dateTime.substring(8, 10);
			
		String hrsMinSec = dateTime.substring(11);

		String newDateTime = day + "-" + month + "-" +  year + " " + hrsMinSec; 

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("DD-MM-YYYY HH:mm:ss");
		java.util.Date date = sdf.parse(newDateTime);
		java.sql.Timestamp newTimeStamp = new java.sql.Timestamp(date.getTime());
		
		return newTimeStamp;		
	}
	
	/**
	 * This method will format input date and time in the format of (yyyymmddhhmmss) and
	 * return a Timestamp value in the format of (dd-MM-yyyy HH:mm:ss)
	 * 
	 * @return String in dd-MM-yyyy HH:mm:ss (Time 24 hour) format.
	 */
	public static String formatDateString(String dateTime) {
		
		if(dateTime == null)	return null;
		if(dateTime.trim().equalsIgnoreCase(""))	return null;  
		      
		//Get year
		String year = dateTime.substring(0, 4);

		//Get month
		String month = dateTime.substring(4, 6);

		//Get day
		String day = dateTime.substring(6, 8);

		//Get hours
		String hours = dateTime.substring(8, 10);
				
		//Get minutes
		String minutes = dateTime.substring(10, 12);	
				
		String newDateTime = day + "-" + month + "-" + year + " " + hours + ":" + minutes + ":00";
			
		return newDateTime;
	}
	
	/**
	 * This method will format input date and time String in the format of (yyyy-MM-dd HH:mm:ss) and
	 * return a String value in the format of (yyyymmddhhmmss)
	 * 
	 * @return java.lang.String in (yyyymmddhhmmss).
	 */
	public static java.lang.String formatTimestampToStringValue(java.lang.String dateTime) throws ParseException {
		
		if(dateTime == null)	return null;
		if(dateTime.trim().equalsIgnoreCase(""))	return null;        
		
		//Get year
		String year = dateTime.substring(0, 4);
		
		//Get month
		String month = dateTime.substring(5, 7);
		
		//Get day
		String day = dateTime.substring(8, 10);

		//Get hours
		String hours = dateTime.substring(11, 13);
				
		//Get minutes
		String minutes = dateTime.substring(14, 16);
				
		String newDateTime = year + month + day + hours + minutes +"00";
		
		return newDateTime;
	}
	
	/**
	 * This method will return a format string for use in java.text.SimpleDateFormat() based on the supplied date format.
	 * Input parameter date format as DMY, or MDY, YMD, etc.
	 * @return java.lang.String as argument for java.text.SimpleDateFormat().
	 */
	public static String SimpleDateFormat(String dateFormat) {
		if(dateFormat.equals("MDY")) return "MM-dd-yyyy";
		if(dateFormat.equals("MYD")) return "MM-yyyy-dd";
		if(dateFormat.equals("YDM")) return "yyyy-dd-MM";
		if(dateFormat.equals("YMD")) return "yyyy-MM-dd";
		if(dateFormat.equals("DYM")) return "dd-yyyy-MM";
		return "dd-MM-yyyy";
	}
	
	
	public static String now(String format) {
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(format);
	    return sdf.format(cal.getTime());
	}
	
	public static Timestamp currentDateTime() {
		long now = System.currentTimeMillis();
		return new Timestamp(now);
	}
	
	public static String getTimeDifferenceToCurrent(Date d) {
        return getTimeDifference(new Date(), d);
    }

    public static String getTimeDifference(Date a, Date b) {
        long time = a.getTime() > b.getTime() ? a.getTime() - b.getTime() : b.getTime() - a.getTime();
        int seconds = (int)((time/1000) % 60);
        int minutes = (int)((time/60000) % 60);
        int hours = (int)((time/3600000) % 24);
        String secondsStr = (seconds<10 ? "0" : "")+seconds;
        String minutesStr = (minutes<10 ? "0" : "")+minutes;
        String hoursStr = (hours<10 ? "0" : "")+hours;
        return hoursStr + "h:" + minutesStr + "m:" + secondsStr + "s";
    }

	public String getYear(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(YEAR_FORMAT);
		return sdf.format(date);	
	}

}
