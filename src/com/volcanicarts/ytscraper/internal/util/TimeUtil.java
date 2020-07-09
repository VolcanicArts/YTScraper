package com.volcanicarts.ytscraper.internal.util;

import java.util.concurrent.TimeUnit;

public class TimeUtil {
	
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

}
