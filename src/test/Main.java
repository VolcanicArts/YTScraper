package test;

import java.net.URI;
import java.net.URISyntaxException;

import main.YTScraper;
import main.YTVideo;

public class Main {

	public static void main(String[] args) throws URISyntaxException {
		YTVideo video = YTScraper.getYouTubeVideoInfo(new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
		System.out.println("Title: " + video.getTitle());
		System.out.println("Duration: " + video.getDuration());
		System.out.println("Category: " + video.getCategory());
	}

}
