package test;

import java.net.URI;
import java.net.URISyntaxException;

import api.YTScraper;
import api.YTVideo;

public class Main {

	public static void main(String[] args) throws URISyntaxException {
		// A simple test case for using the YTScraper and YTVideo classes
		URI uri = new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
		YTVideo video = YTScraper.getYouTubeVideoInfo(uri);
		System.out.println("ID: " + video.getID());
		System.out.println("Title: " + video.getTitle());
		System.out.println("Duration: " + video.getDuration());
		System.out.println("Category: " + video.getCategory());
		System.out.println("Uploaded: " + video.getUpload());
	}

}
