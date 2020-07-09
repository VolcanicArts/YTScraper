package com.volcanicarts.ytscraper.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.volcanicarts.ytscraper.internal.InvalidVideoException;
import com.volcanicarts.ytscraper.internal.VideoCategory;
import com.volcanicarts.ytscraper.internal.YTVideoImpl;

/**
 * The main class for scraping YouTube
 * @author VolcanicArts
 * @since 1.0.0
 */
public class YTScraper {
	
	private static final Pattern CONFIG_PATTERN = Pattern.compile("\\{};ytplayer.config = (\\{.*?.*\\})");
	
	/**
	 * Retrieve YouTube video information from a video ID
	 * @param videoID - The videoID of the video you want to get info on
	 * @return new YTVideo
	 * @see YTVideo
	 * @throws URISyntaxException
	 * @throws InvalidVideoException 
	 */
	public static YTVideo getYouTubeVideoInfo(String videoID) throws URISyntaxException, InvalidVideoException {
		return getYouTubeVideoInfo(new URI("https://www.youtube.com/watch?v=" + videoID));
	}
	
	/**
	 * Retrieve YouTube video information
	 * @param URI - The URL of the video you want to get info on
	 * @return new YTVideo
	 * @throws InvalidVideoException
	 * @see YTVideo
	 */
	public static YTVideo getYouTubeVideoInfo(URI URI) throws InvalidVideoException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI)
			.GET()
	        .setHeader("Content-Type", "application/json")
	        .build();
		
		HttpResponse<String> res = null;
		try {
			res = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException("An exception has occured with the request", e);
		}
		
		if (res == null) throw new InvalidVideoException("No data could be found for the provided video: " + URI);
		String body = res.body();
		
		Matcher m = CONFIG_PATTERN.matcher(body);
		if (m.find()) {
			JSONObject data = new JSONObject(m.group(1));
			JSONObject playerData = new JSONObject(data.getJSONObject("args").getString("player_response"));
			
			JSONObject videoDetails = playerData.getJSONObject("videoDetails");
			JSONObject playerMFR = playerData.getJSONObject("microformat").getJSONObject("playerMicroformatRenderer");
			
			return createVideo(videoDetails, playerMFR);
		} else {
			throw new InvalidVideoException("No data could be found for the provided video: " + URI);
		}
	}
	
	private static YTVideo createVideo(JSONObject videoDetails, JSONObject playerMFR) {
		YTVideoImpl video = new YTVideoImpl();
		video.setID(videoDetails.getString("videoId"));
		video.setTitle(videoDetails.getString("title"));
		video.setDuration(Long.parseLong(videoDetails.getString("lengthSeconds")) * 1000);
		long uploaded;
		try {
			uploaded = parseUploaded(playerMFR.getString("publishDate"));
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not parse upload date correctly", e);
		}
		video.setUpload(uploaded);
		if (playerMFR.has("category")) video.setCategory(VideoCategory.valueOf(playerMFR.getString("category")));
		return video;
	}
	
	private static long parseUploaded(String uploaded) throws ParseException {
		DateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sourceFormat.parse(uploaded);
		return date.getTime();
	}

}
