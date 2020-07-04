package main;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
		
		HttpResponse res = null;
		try {
			res = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		if (res == null) return null;
		
		System.out.println(res.body());
		return null;
	}

}
