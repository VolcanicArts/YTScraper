package com.volcanicarts.ytscraper.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.volcanicarts.ytscraper.internal.VideoCategory;
import com.volcanicarts.ytscraper.internal.YTVideoImpl;

/**
 * The main class for scraping YouTube
 * @author VolcanicArts
 * @since 1.0.0
 */
public class YTScraper {
	
	/**
	 * Retrieve YouTube video information from a video ID
	 * @param videoID - The videoID of the video you want to get info on
	 * @return new YTVideo
	 * @see YTVideo
	 * @throws URISyntaxException
	 */
	public static YTVideo getYouTubeVideoInfo(String videoID) throws URISyntaxException {
		return getYouTubeVideoInfo(new URI("https://www.youtube.com/watch?v=" + videoID));
	}
	
	/**
	 * Retrieve YouTube video information
	 * @param URI - The URL of the video you want to get info on
	 * @return new YTVideo
	 * @see YTVideo
	 */
	public static YTVideo getYouTubeVideoInfo(URI URI) {
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
		}
		
		if (res == null) return null;
		String body = res.body();
		
		Pattern p = Pattern.compile("\\{};ytplayer.config = (\\{.*?.*\\})");
		Matcher m = p.matcher(body);
		if (m.find()) {
			JSONObject data = new JSONObject(m.group(1));
			JSONObject playerData = new JSONObject(data.getJSONObject("args").getString("player_response"));
			
			JSONObject videoDetails = playerData.getJSONObject("videoDetails");
			JSONObject playerMFR = playerData.getJSONObject("microformat").getJSONObject("playerMicroformatRenderer");
			
			YTVideoImpl video = new YTVideoImpl();
			video.setID(videoDetails.getString("videoId"));
			video.setTitle(videoDetails.getString("title"));
			video.setDuration(Long.parseLong(videoDetails.getString("lengthSeconds")) * 1000);
			video.setUpload(playerMFR.getString("publishDate"));
			if (playerMFR.has("category")) {
				video.setCategory(VideoCategory.valueOf(playerMFR.getString("category")));
			}
			return video;
		} else {
			return null;
		}
	}

}
