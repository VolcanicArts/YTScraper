package volcanicarts.ytscraper.ytvideo;

import volcanicarts.ytscraper.util.YouTubeUtil;

/**
 * Custom exception for when a video's data cannot be found
 *
 * @author VolcanicArts
 * @since 1.0.2
 */
@SuppressWarnings("serial")
public class InvalidVideoException extends Exception {

	private final String videoID;

	public InvalidVideoException(String URL, String error) {
		super(error);
		this.videoID = YouTubeUtil.videoLinkToID(URL);
	}

	/**
	 * @return The ID of the video that caused the exception
	 */
	public String getVideoID() {
		return videoID;
	}

	/**
	 * Prints the error reason
	 */
	public void printErrorReason() {
		String message = "YTScraper Error: " + String.format("[VideoID: %s] | ", getVideoID()) + getMessage();
		System.err.println(message);
	}

}
