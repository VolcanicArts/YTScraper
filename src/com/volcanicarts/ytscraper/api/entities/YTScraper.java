package com.volcanicarts.ytscraper.api.entities;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.volcanicarts.ytscraper.internal.entities.VideoCategory;
import com.volcanicarts.ytscraper.internal.entities.VideoResultHandler;
import com.volcanicarts.ytscraper.internal.entities.YTVideoImpl;
import com.volcanicarts.ytscraper.internal.exceptions.InvalidVideoException;
import com.volcanicarts.ytscraper.internal.util.TimeUtil;

/**
 * The main class for scraping YouTube
 * @author VolcanicArts
 * @since 1.0.0
 */
public class YTScraper {
	
	private final Pattern CONFIG_PATTERN = Pattern.compile("\\{};ytplayer.config = (\\{.*?.*\\})");
	
	private final URI uri;
	private VideoResultHandler handler;
	
	/**
	 * Makes a new YTScraper from a video ID and the callback class
	 * @param videoID - The videoID of the video you want to get info on
	 * @param handler - The callback class
	 * @throws URISyntaxException
	 */
	public YTScraper(String videoID) throws URISyntaxException {
		this.uri = new URI("https://www.youtube.com/watch?v=" + videoID);
	}
	
	/**
	 * Makes a new YTScraper from a video ID and the callback class
	 * @param URI - The URI of the video you want to get info on
	 * @param handler - The callback class
	 * @throws URISyntaxException
	 */
	public YTScraper(URI uri) {
		this.uri = uri;
	}
	
	/**
	 * Begins loading the video
	 */
	public void load(VideoResultHandler handler) {
		this.handler = handler;
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
			.uri(this.uri)
			.GET()
	        .setHeader("Content-Type", "application/json")
	        .build();
		
		HttpResponse<String> res = null;
		try {
			res = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			handler.loadFailed(new InvalidVideoException("An exception has occured with the request"));
		}
		
		if (res == null) handler.loadFailed(new InvalidVideoException("No data could be found for the provided video"));
		String body = res.body();
		
		Matcher m = CONFIG_PATTERN.matcher(body);
		if (m.find()) {
			JSONObject data = new JSONObject(m.group(1));
			JSONObject playerData = new JSONObject(data.getJSONObject("args").getString("player_response"));
			
			JSONObject videoDetails = playerData.getJSONObject("videoDetails");
			JSONObject playerMFR = playerData.getJSONObject("microformat").getJSONObject("playerMicroformatRenderer");
			
			YTVideo video = createVideo(videoDetails, playerMFR);
			if (video != null) handler.videoLoaded(video);
		} else {
			handler.loadFailed(new InvalidVideoException("No data could be found for the provided video"));
		}
	}
	
	private YTVideo createVideo(JSONObject videoDetails, JSONObject playerMFR) {
		YTVideoImpl video = new YTVideoImpl();
		video.setID(videoDetails.getString("videoId"));
		video.setTitle(videoDetails.getString("title"));
		video.setDuration(Long.parseLong(videoDetails.getString("lengthSeconds")) * 1000);
		long uploaded;
		try {
			uploaded = TimeUtil.parseUploaded(playerMFR.getString("publishDate"));
		} catch (ParseException e) {
			e.printStackTrace();
			handler.loadFailed(new InvalidVideoException("Could not parse upload date correctly"));
			return null;
		}
		video.setUpload(uploaded);
		if (playerMFR.has("category")) video.setCategory(VideoCategory.valueOf(playerMFR.getString("category")));
		return video;
	}

}
