package com.volcanicarts.ytscraper.api;

import com.volcanicarts.ytscraper.internal.VideoCategory;

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

}
