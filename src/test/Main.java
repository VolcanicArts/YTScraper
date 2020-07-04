package test;

import java.net.URI;
import java.net.URISyntaxException;

import main.YTScraper;

public class Main {

	public static void main(String[] args) throws URISyntaxException {
		YTScraper.getYouTubeVideoInfo(new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
	}

}
