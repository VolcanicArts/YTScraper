package test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import volcanicarts.ytscraper.api.entities.YTScraper;
import volcanicarts.ytscraper.api.entities.YTVideo;
import volcanicarts.ytscraper.internal.entities.VideoResultHandler;
import volcanicarts.ytscraper.internal.exceptions.InvalidVideoException;

public class Main {

	public static void main(String[] args) throws URISyntaxException {
		// A simple test case for using the YTScraper and YTVideo classes
		URI uri = new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
		List<URI> uris = new ArrayList<URI>();
		for (int i = 0; i < 10; i++) {
			uris.add(uri);
		}
		new YTScraper(uris).load(new VideoResultHandler() {

			@Override
			public void videoLoaded(YTVideo video) {
				System.out.println("Video ID: " + video.getID());
				System.out.println("URL: " + video.getURL());
				System.out.println("Title: " + video.getTitle());
				System.out.println("Duration: " + video.getDurationFormatted());
				System.out.println("Category: " + video.getCategory());
				System.out.println("Uploaded: " + video.getUpload());
				System.out.println("Uploaded Formatted: " + video.getUploadFormatted());
				System.out.println("Thumbnail URI: " + video.getThumbnailURI());
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
