package volcanicarts.ytscraper.internal.exceptions;

import java.net.URI;

import volcanicarts.ytscraper.internal.util.YouTubeUtil;

/**
 * Custom exception for when a video's data cannot be found
 * @author VolcanicArts
 * @since 1.0.2
 */
@SuppressWarnings("serial")
public class InvalidVideoException extends Exception {
	
	private String videoID;
	
	public InvalidVideoException(URI videoID, String error) {
		super(error);
		this.videoID = YouTubeUtil.videoLinkToID(videoID);
	}

	/**
	 * @return The ID of the video that caused the exception
	 */
	public String getVideoID() {
		return videoID;
	}

}
