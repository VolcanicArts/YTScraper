package volcanicarts.ytscraper;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import volcanicarts.ytscraper.util.YouTubeUtil;
import volcanicarts.ytscraper.worker.GenericWorker;
import volcanicarts.ytscraper.worker.YTVideoWorker;
import volcanicarts.ytscraper.ytvideo.InvalidVideoException;
import volcanicarts.ytscraper.ytvideo.ResultHandler;
import volcanicarts.ytscraper.ytvideo.YTVideo;

/**
 * The main class for scraping YouTube
 * @author VolcanicArts
 * @since 1.0.0
 */
public class YTScraper {
	
	private final int MAX_WORKERS = 5;
	
	private final List<String> URLs = new ArrayList<String>();
	private final List<GenericWorker> workers = new ArrayList<GenericWorker>();
	private ResultHandler handler;
	private OkHttpClient requestClient = new OkHttpClient();
	
	/**
	 * Makes a new YTScraper from a list of URLs
	 * @param videoID - The videoID of the video you want to get info on
	 * @param handler - The callback class
	 * @throws URISyntaxException
	 */
	public YTScraper(List<String> URLs) throws URISyntaxException {
		this.URLs.addAll(URLs);
	}
	
	/**
	 * Makes a new YTScraper from a URL
	 * @param URI - The URI of the video you want to get info on
	 * @param handler - The callback class
	 * @throws URISyntaxException
	 */
	public YTScraper(String URLs) {
		this.URLs.add(URLs);
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
