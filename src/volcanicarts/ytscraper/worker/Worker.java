package volcanicarts.ytscraper.worker;

import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
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
					scraper.loadFailed(Worker.this, new InvalidVideoException(Worker.this.URL, "The request could not be fulfilled"));
				}
				
				if (body == null || body.isEmpty()) scraper.loadFailed(Worker.this, new InvalidVideoException(Worker.this.URL, "The request could not be fulfilled"));
				
				Matcher m = CONFIG_PATTERN.matcher(body);
				if (m.find()) {
					JSONObject data = new JSONObject(m.group(1));
					JSONObject playerData = new JSONObject(data.getJSONObject("args").getString("player_response"));
					JSONObject videoDetails = playerData.getJSONObject("videoDetails");
					JSONObject playerMFR = playerData.getJSONObject("microformat").getJSONObject("playerMicroformatRenderer");
					
					YTVideo video = createVideo(videoDetails, playerMFR);
					if (video != null) scraper.videoLoaded(Worker.this, video);
				} else {
					scraper.loadFailed(Worker.this, new InvalidVideoException(Worker.this.URL, "No data could be parsed for the provided video"));
				}
			}
			
		}).start();
		
	}
	
	private YTVideo createVideo(JSONObject videoDetails, JSONObject playerMFR) {
		String videoID = videoDetails.getString("videoId");
		String title = videoDetails.getString("title");
		Long duration = Long.parseUnsignedLong(videoDetails.getString("lengthSeconds")) * 1000l;
		String author = videoDetails.getString("author");
		Long viewCount = Long.parseUnsignedLong(videoDetails.getString("viewCount"));
		String channelID = videoDetails.getString("channelId");
		
		JSONArray thumbnails = videoDetails.getJSONObject("thumbnail").getJSONArray("thumbnails");
		JSONObject thumbnail = thumbnails.getJSONObject(thumbnails.length() - 1);
		String thumbnailURL = thumbnail.getString("url").split("[?]")[0];
		
		String publishDate = playerMFR.getString("publishDate");
		Long uploaded = null;
		try {
			uploaded = TimeUtil.parseUploaded(publishDate);
		} catch (ParseException e) {
			uploaded = 0l;
		}
		
		VideoCategory videoCategory = VideoCategory.NONE;
		if (playerMFR.has("category")) {
			String category = playerMFR.getString("category");
			videoCategory = VideoCategory.parseCategory(category);
		}
		
		String description = "";
		if (playerMFR.has("description")) {
			JSONObject descriptionObj = playerMFR.getJSONObject("description");
			description = descriptionObj.getString("simpleText");
		}
		
		YTVideoImpl video = new YTVideoImpl()
				.setVideoID(videoID)
				.setTitle(title)
				.setDuration(duration)
				.setAuthor(author)
				.setViewCount(viewCount)
				.setChannelID(channelID)
				.setThumbnailURL(thumbnailURL)
				.setUploaded(uploaded)
				.setCategory(videoCategory)
				.setDescription(description);
		return video;
	}

}
