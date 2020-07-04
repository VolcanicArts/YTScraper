package main;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

public class YTScraper {
	
	public static YTVideo getYouTubeVideoInfo(String videoID) throws URISyntaxException {
		return getYouTubeVideoInfo(new URI("https://www.youtube.com/watch?v=" + videoID));
	}
	
	public static YTVideo getYouTubeVideoInfo(URI URL) {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URL)
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
		//System.out.println(res.body());
		String body = res.body();
		Pattern p = Pattern.compile("\\{};ytplayer.config = (\\{.*?.*\\})");
		Matcher m = p.matcher(body);
		if (m.find()) {
			String found = m.group(1);
			//System.out.println(found);
			JSONObject data = new JSONObject(found);
			JSONObject playerData = new JSONObject(data.getJSONObject("args").getString("player_response"));
			
			JSONObject videoDetails = playerData.getJSONObject("videoDetails");
			JSONObject miniPlayer = playerData.getJSONObject("microformat").getJSONObject("playerMicroformatRenderer");
			
			YTVideo video = new YTVideo();
			video.setID(videoDetails.getString("videoId"));
			video.setTitle(videoDetails.getString("title"));
			video.setDuration(Long.parseLong(videoDetails.getString("lengthSeconds")) * 1000);
			video.setUpload(miniPlayer.getString("publishDate"));
			video.setCategory(miniPlayer.getString("category"));
			return video;
		} else {
			return null;
		}
	}

}
