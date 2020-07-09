package test;

import java.net.URI;
import java.net.URISyntaxException;

import com.volcanicarts.ytscraper.api.entities.YTScraper;
import com.volcanicarts.ytscraper.api.entities.YTVideo;
import com.volcanicarts.ytscraper.internal.entities.VideoResultHandler;
import com.volcanicarts.ytscraper.internal.exceptions.InvalidVideoException;

public class Main {

	public static void main(String[] args) throws URISyntaxException {
		// A simple test case for using the YTScraper and YTVideo classes
		URI uri = new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
		new YTScraper(uri).load(new VideoResultHandler() {

			@Override
			public void videoLoaded(YTVideo video) {
				System.out.println("ID: " + video.getID());
				System.out.println("Title: " + video.getTitle());
				System.out.println("Duration: " + video.getDurationFormatted());
				System.out.println("Category: " + video.getCategory());
				System.out.println("Uploaded: " + video.getUploadFormatted());
			}

			@Override
			public void loadFailed(InvalidVideoException e) {
				e.printStackTrace();
			}
			
		});
	}

}
