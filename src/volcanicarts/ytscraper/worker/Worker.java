package volcanicarts.ytscraper.worker;

import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import volcanicarts.ytscraper.YTScraper;
import volcanicarts.ytscraper.util.TimeUtil;
import volcanicarts.ytscraper.ytvideo.InvalidVideoException;
import volcanicarts.ytscraper.ytvideo.VideoCategory;
import volcanicarts.ytscraper.ytvideo.YTVideo;
import volcanicarts.ytscraper.ytvideo.YTVideoImpl;

/**
 * A worker that makes a single request and scrape
 * @author VolcanicArts
 * @since 1.2.0
 */
public class Worker {
	
	private final Pattern CONFIG_PATTERN = Pattern.compile("\\{};ytplayer.config = (\\{.*?.*\\})");
	
	private OkHttpClient requestClient;
	private String URL;
	private YTScraper scraper;
	
	public void assign(OkHttpClient requestClient, String URL, YTScraper scraper) {
		this.requestClient = requestClient;
		this.URL = URL;
		this.scraper = scraper;
	}
	
	public void run() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Request request = new Request.Builder()
				      .url(URL)
				      .build();

				String body = null;
				try (Response response = requestClient.newCall(request).execute()) {
					body = response.body().string();
				} catch (IOException e) {
					scraper.loadFailed(Worker.this, new InvalidVideoException(Worker.this.URL, "No data could be found for the provided video"));
				}
				
				if (body == null || body.isEmpty()) scraper.loadFailed(Worker.this, new InvalidVideoException(Worker.this.URL, "No data could be found for the provided video"));
				
				Matcher m = CONFIG_PATTERN.matcher(body);
				if (m.find()) {
					JSONObject data = new JSONObject(m.group(1));
					JSONObject playerData = new JSONObject(data.getJSONObject("args").getString("player_response"));
					JSONObject videoDetails = playerData.getJSONObject("videoDetails");
					JSONObject playerMFR = playerData.getJSONObject("microformat").getJSONObject("playerMicroformatRenderer");
					
					YTVideo video = createVideo(videoDetails, playerMFR);
					if (video != null) scraper.videoLoaded(Worker.this, video);
				} else {
					scraper.loadFailed(Worker.this, new InvalidVideoException(Worker.this.URL, "No data could be found for the provided video"));
				}
			}
			
		}).start();
		
	}
	
	private YTVideo createVideo(JSONObject videoDetails, JSONObject playerMFR) {
		YTVideoImpl video = new YTVideoImpl();
		video.setID(videoDetails.getString("videoId"));
		video.setTitle(videoDetails.getString("title"));
		video.setDuration(Long.parseLong(videoDetails.getString("lengthSeconds")) * 1000);
		try {
			long uploaded = TimeUtil.parseUploaded(playerMFR.getString("publishDate"));
			video.setUpload(uploaded);
		} catch (ParseException e) {
			scraper.loadFailed(this, new InvalidVideoException(this.URL, "Could not parse upload date correctly"));
			return null;
		}
		if (playerMFR.has("category")) video.setCategory(VideoCategory.valueOf(playerMFR.getString("category").replace(" ", "_").replace("&", "and")));
		video.setThumbnailURI(videoDetails.getJSONObject("thumbnail").getJSONArray("thumbnails").getJSONObject(0).getString("url").split("[?]")[0]);
		try {
			JSONObject description = playerMFR.getJSONObject("description");
			video.setDescription(description.getString("simpleText"));
		} catch (JSONException e) {
			video.setDescription("");
		}
		video.setAuthor(videoDetails.getString("author"));
		video.setViewCount(Long.parseUnsignedLong(videoDetails.getString("viewCount")));
		video.setChannelID(videoDetails.getString("channelId"));
		return video;
	}

}
