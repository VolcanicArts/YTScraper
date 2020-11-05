package volcanicarts.ytscraper.internal.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * A class for utilities for time
 * @author VolcanicArts
 * @since 1.0.5
 */
public class TimeUtil {
	
	/**
	 * Converts milliseconds into HH:MM:SS. HH will be left out if 0
	 * @param millis
	 * @return HH:MM:SS
	 */
	public static String convertMilliToHHMMSS(long millis) {
		if (millis < 3600000) {
			return String.format("%02d:%02d", 
				    TimeUnit.MILLISECONDS.toMinutes(millis) - 
				    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
				    TimeUnit.MILLISECONDS.toSeconds(millis) - 
				    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
		} else {
			return String.format("%02d:%02d:%02d", 
				    TimeUnit.MILLISECONDS.toHours(millis),
				    TimeUnit.MILLISECONDS.toMinutes(millis) - 
				    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
				    TimeUnit.MILLISECONDS.toSeconds(millis) - 
				    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
		}
	}
	
	/**
	 * Parses the uploaded date of a YouTube video
	 * @param uploaded
	 * @return TimeCode
	 * @throws ParseException
	 */
	public static long parseUploaded(String uploaded) throws ParseException {
		DateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sourceFormat.parse(uploaded);
		return date.getTime();
	}

}
