package test;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import volcanicarts.ytscraper.YTScraper;
import volcanicarts.ytscraper.ytvideo.InvalidVideoException;
import volcanicarts.ytscraper.ytvideo.VideoResultHandler;
import volcanicarts.ytscraper.ytvideo.YTVideo;

public class Main {

	public static void main(String[] args) throws URISyntaxException {
		// A simple test case for using the YTScraper and YTVideo classes
		String URL = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
		List<String> URLs = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			URLs.add(URL);
		}
		new YTScraper(URLs).load(new VideoResultHandler() {

			@Override
			public void videoLoaded(YTVideo video) {
				System.out.println("Video ID: " + video.getVideoID());
				System.out.println("URL: " + video.getURL());
				System.out.println("Title: " + video.getTitle());
				System.out.println("Duration: " + video.getDurationFormatted());
				System.out.println("Category: " + video.getCategory());
				System.out.println("Uploaded: " + video.getUploaded());
				System.out.println("Uploaded Formatted: " + video.getUploadedFormatted());
				System.out.println("Thumbnail URI: " + video.getThumbnailURL());
				System.out.println("Description: " + video.getDescription());
				System.out.println("Author: " + video.getAuthor());
				System.out.println("View Count: " + video.getViewCount());
				System.out.println("View Count Formatted: " + video.getViewCountFormatted());
				System.out.println("Channel ID: " + video.getChannelID());
			}

			@Override
			public void loadFailed(InvalidVideoException e) {
				e.printStackTrace();
			}
			
		});
	}

}
