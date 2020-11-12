package volcanicarts.ytscraper.ytvideo;

import volcanicarts.ytscraper.YTScraper;

/**
 * The result handler for the YTScraper
 *
 * @author VolcanicArts
 * @see YTScraper
 * @since 1.1.0
 */
public abstract class ResultHandler {

	public abstract void videoLoaded(YTVideo video);

	public abstract void loadFailed(InvalidVideoException e);

}
