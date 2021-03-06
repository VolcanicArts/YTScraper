package volcanicarts.ytscraper.api.entities;

import volcanicarts.ytscraper.internal.entities.VideoCategory;

/**
 * The interface for any returned YouTube video
 * @author VolcanicArts
 * @since 1.0.0
 */
public interface YTVideo {

	/**
	 * @return The ID of the video
	 */
	String getID();

	/**
	 * @return The URL of the video
	 */
	String getURL();

	/**
	 * @return The title of the video
	 */
	String getTitle();

	/**
	 * @return The duration of the video in milliseconds
	 */
	long getDuration();
	
	/**
	 * @return The duration of the video in HH:MM:SS. Will exclude HH if 0
	 */
	String getDurationFormatted();

	/**
	 * @return The upload date of the video in timecode format
	 */
	long getUpload();
	
	/**
	 * @return The upload date of the video in String format
	 */
	String getUploadFormatted();

	/**
	 * This is null in cases where the video author has not set a category
	 * @return The category of the video
	 */
	VideoCategory getCategory();
	
	/**
	 * @return The URI of the thumbnail in the best quality
	 */
	String getThumbnailURI();
	
	/**
	 * @return The description of the video
	 */
	String getDescription();
	
	/**
	 * @return The author of the video
	 */
	String getAuthor();
	
	/**
	 * @return The view count of the video at the time of the request
	 */
	long getViewCount();
	
	/**
	 * @return The view count of the video at the time of the request, formatted
	 */
	String getViewCountFormatted();
	
	/**
	 * @return The ID of the channel
	 */
	String getChannelID();

}
