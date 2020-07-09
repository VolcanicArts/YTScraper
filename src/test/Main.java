package test;

import java.net.URI;
import java.net.URISyntaxException;

import com.volcanicarts.ytscraper.api.YTScraper;
import com.volcanicarts.ytscraper.api.YTVideo;
import com.volcanicarts.ytscraper.internal.InvalidVideoException;

public class Main {

	public static void main(String[] args) throws URISyntaxException {
		// A simple test case for using the YTScraper and YTVideo classes
		URI uri = new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
		YTVideo video = null;
		try {
			video = YTScraper.getYouTubeVideoInfo(uri);
		} catch (InvalidVideoException e) {
			e.printStackTrace();
		}
		if (video != null) {
			System.out.println("ID: " + video.getID());
			System.out.println("Title: " + video.getTitle());
			System.out.println("Duration: " + video.getDurationFormatted());
			System.out.println("Category: " + video.getCategory());
			System.out.println("Uploaded: " + video.getUploadFormatted());
		} else {
			System.out.println("No data could be found");
		}
	}

}
