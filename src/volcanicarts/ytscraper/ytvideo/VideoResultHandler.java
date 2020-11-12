package volcanicarts.ytscraper.ytvideo;

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
