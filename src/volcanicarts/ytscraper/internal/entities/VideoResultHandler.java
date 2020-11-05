package volcanicarts.ytscraper.internal.entities;

import volcanicarts.ytscraper.api.entities.YTVideo;
import volcanicarts.ytscraper.internal.exceptions.InvalidVideoException;

/**
 * The result handler for the YTScraper
 * @see YTScraper
 * @author VolcanicArts
 * @since 1.1.0
 */
public abstract class VideoResultHandler {

	public abstract void videoLoaded(YTVideo video);
	public abstract void loadFailed(InvalidVideoException e);
	
}
