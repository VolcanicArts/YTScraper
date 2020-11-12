package volcanicarts.ytscraper.worker;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import volcanicarts.ytscraper.YTScraper;
import volcanicarts.ytscraper.ytvideo.InvalidVideoException;

import java.io.IOException;
import java.util.Objects;

/**
 * A generic worker that makes a single request
 *
 * @author VolcanicArts
 * @since 1.5.0
 */
public class GenericWorker extends Thread {

	protected String URL;
	protected YTScraper scraper;
	protected String body = null;
	private OkHttpClient requestClient;

	public void assign(OkHttpClient requestClient, String URL, YTScraper scraper) {
		this.requestClient = requestClient;
		this.URL = URL;
		this.scraper = scraper;
	}

	@Override
	public void run() {
		Request request = new Request.Builder()
				.url(URL)
				.build();

		String body = null;
		try (Response response = requestClient.newCall(request).execute()) {
			body = Objects.requireNonNull(response.body()).string();
		} catch (IOException e) {
			scraper.loadFailed(this, new InvalidVideoException(this.URL, "The request could not be fulfilled"));
		}

		if (body == null || body.isEmpty())
			scraper.loadFailed(this, new InvalidVideoException(this.URL, "The request could not be fulfilled"));
		this.body = body;
	}

}
