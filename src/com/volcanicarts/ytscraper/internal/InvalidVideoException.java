package com.volcanicarts.ytscraper.internal;

/**
 * Custom exception for when a video's data cannot be found
 * @author VolcanicArts
 */
@SuppressWarnings("serial")
public class InvalidVideoException extends Exception {
	
	public InvalidVideoException(String error) {
		super(error);
	}

}
