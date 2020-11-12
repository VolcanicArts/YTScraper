package volcanicarts.ytscraper;

import okhttp3.OkHttpClient;
import volcanicarts.ytscraper.util.YouTubeUtil;
import volcanicarts.ytscraper.worker.GenericWorker;
import volcanicarts.ytscraper.worker.YTVideoWorker;
import volcanicarts.ytscraper.ytvideo.InvalidVideoException;
import volcanicarts.ytscraper.ytvideo.ResultHandler;
import volcanicarts.ytscraper.ytvideo.YTVideo;

import java.util.ArrayList;
import java.util.List;

/**
 * The main class for scraping YouTube
 *
 * @author VolcanicArts
 * @since 1.0.0
 */
public class YTScraper {

	private final int MAX_WORKERS = 5;

	private final List<String> URLs = new ArrayList<String>();
	private final List<GenericWorker> workers = new ArrayList<GenericWorker>();
	private final OkHttpClient requestClient = new OkHttpClient();
	private ResultHandler handler;

	/**
	 * Makes a new YTScraper from a list of URLs
	 *
	 * @param URLs - A list of URLs to scrape
	 */
	public YTScraper(List<String> URLs) {
		this.URLs.addAll(URLs);
	}

	/**
	 * Makes a new YTScraper from a URL
	 *
	 * @param URL - A URL to scrape
	 */
	public YTScraper(String URL) {
		this.URLs.add(URL);
	}

	private GenericWorker createWorker(String URL) {
		if (URL == null) return null;
		GenericWorker worker = null;
		if (YouTubeUtil.isURLVideo(URL)) {
			worker = new YTVideoWorker();
		}
		this.workers.add(worker);
		return worker;
	}

	/**
	 * Starts the worker process
	 */
	public void load(ResultHandler handler) {
		this.handler = handler;
		checkForComplete();
	}

	private void checkForComplete() {
		if (this.URLs.size() != 0) {
			if (workers.size() != MAX_WORKERS) {
				String URL = this.URLs.remove(0);
				GenericWorker worker = createWorker(URL);
				if (URL != null && worker != null) {
					worker.assign(requestClient, URL, this);
					worker.run();
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				checkForComplete();
			}
		}
	}

	public void videoLoaded(GenericWorker worker, YTVideo video) {
		workers.remove(worker);
		this.handler.videoLoaded(video);
		checkForComplete();
	}

	public void loadFailed(GenericWorker worker, InvalidVideoException e) {
		workers.remove(worker);
		this.handler.loadFailed(e);
		checkForComplete();
	}

}
