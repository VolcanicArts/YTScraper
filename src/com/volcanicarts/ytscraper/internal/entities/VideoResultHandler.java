package com.volcanicarts.ytscraper.internal.entities;

import com.volcanicarts.ytscraper.api.entities.YTVideo;
import com.volcanicarts.ytscraper.internal.exceptions.InvalidVideoException;

public abstract class VideoResultHandler {

	public abstract void videoLoaded(YTVideo video);
	public abstract void loadFailed(InvalidVideoException e);
	
}
