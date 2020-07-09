package com.volcanicarts.ytscraper.internal.exceptions;

/**
 * Custom exception for when a video's data cannot be found
 * @author VolcanicArts
 * @since 1.0.2
 */
@SuppressWarnings("serial")
public class InvalidVideoException extends Exception {
	
	public InvalidVideoException(String error) {
		super(error);
	}

}
