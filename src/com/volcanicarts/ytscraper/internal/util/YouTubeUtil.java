package com.volcanicarts.ytscraper.internal.util;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouTubeUtil {
	
	public static String videoLinkToID(URI uri) {
		String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(uri.toString());
		if (m.find()) {
			return m.group(0);
		} else {
			return null;
		}
	}

}
