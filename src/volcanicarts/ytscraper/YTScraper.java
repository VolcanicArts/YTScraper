package volcanicarts.ytscraper;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import volcanicarts.ytscraper.worker.Worker;
import volcanicarts.ytscraper.ytvideo.InvalidVideoException;
import volcanicarts.ytscraper.ytvideo.VideoResultHandler;
import volcanicarts.ytscraper.ytvideo.YTVideo;

/**
 * The main class for scraping YouTube
 * @author VolcanicArts
 * @since 1.0.0
 */
public class YTScraper {
	
	private final int MAX_WORKERS = 5;
	
	private final List<String> URLs = new ArrayList<String>();
	private final List<Worker> workers = new ArrayList<Worker>();
	private VideoResultHandler handler;
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
	
	private Worker createWorker() {
		if (workers.size() == MAX_WORKERS) return null;
		Worker worker = new Worker();
		this.workers.add(worker);
		return worker;
	}
	
	/**
	 * Starts the worker process
	 */
	public void load(VideoResultHandler handler) {
		this.handler = handler;
		checkForComplete();
	}
	
	private void checkForComplete() {
		if (this.URLs.size() != 0) {
			Worker worker = createWorker();
			if (worker != null) {
				String URL = this.URLs.remove(0);
				if (URL != null) {
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
	
	public void videoLoaded(Worker worker, YTVideo video) {
		workers.remove(worker);
		this.handler.videoLoaded(video);
		checkForComplete();
	}
	
	public void loadFailed(Worker worker, InvalidVideoException e) {
		workers.remove(worker);
		this.handler.loadFailed(e);
		checkForComplete();
	}

}
