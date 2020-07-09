package test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.volcanicarts.ytscraper.api.entities.YTScraper;
import com.volcanicarts.ytscraper.api.entities.YTVideo;
import com.volcanicarts.ytscraper.internal.entities.VideoResultHandler;
import com.volcanicarts.ytscraper.internal.exceptions.InvalidVideoException;

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
