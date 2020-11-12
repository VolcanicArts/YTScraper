package volcanicarts.ytscraper.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class for utilities for YouTube
 * @author VolcanicArts
 * @since 1.2.0
 */
public class YouTubeUtil {
	
	private static final String ID_REGEX = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
	private static final Pattern ID_PATTERN = Pattern.compile(ID_REGEX);
	
	/**
	 * @param URI
	 * @return A video ID
	 */
	public static String videoLinkToID(String URL) {
		Matcher m = ID_PATTERN.matcher(URL);
		if (m.find()) {
			return m.group(0);
		} else {
			return null;
		}
	}

}
