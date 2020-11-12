package volcanicarts.ytscraper.worker;

import org.json.JSONArray;
import org.json.JSONObject;
import volcanicarts.ytscraper.util.TimeUtil;
import volcanicarts.ytscraper.ytvideo.InvalidVideoException;
import volcanicarts.ytscraper.ytvideo.VideoCategory;
import volcanicarts.ytscraper.ytvideo.YTVideo;
import volcanicarts.ytscraper.ytvideo.YTVideoImpl;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A worker designed for scraping a video's page
 *
 * @author VolcanicArts
 * @since 1.5.0
 */
public class YTVideoWorker extends GenericWorker {

	private static final Pattern PATTERN = Pattern.compile("\\{};ytplayer.config = (\\{.*?.*})");

	@Override
	public void run() {
		super.run();
		Matcher m = PATTERN.matcher(body);
		if (m.find()) {
			JSONObject data = new JSONObject(m.group(1));
			JSONObject playerData = new JSONObject(data.getJSONObject("args").getString("player_response"));
			JSONObject videoDetails = playerData.getJSONObject("videoDetails");
			JSONObject playerMFR = playerData.getJSONObject("microformat").getJSONObject("playerMicroformatRenderer");

			YTVideo video = createVideo(videoDetails, playerMFR);
			if (video != null) scraper.videoLoaded(this, video);
		} else {
			scraper.loadFailed(this, new InvalidVideoException(this.URL, "No data could be parsed for the provided video"));
		}
	}

	private YTVideo createVideo(JSONObject videoDetails, JSONObject playerMFR) {
		String videoID = videoDetails.getString("videoId");
		String title = videoDetails.getString("title");
		long duration = Long.parseUnsignedLong(videoDetails.getString("lengthSeconds")) * 1000L;
		String author = videoDetails.getString("author");
		long viewCount = Long.parseUnsignedLong(videoDetails.getString("viewCount"));
		String channelID = videoDetails.getString("channelId");

		JSONArray thumbnails = videoDetails.getJSONObject("thumbnail").getJSONArray("thumbnails");
		JSONObject thumbnail = thumbnails.getJSONObject(thumbnails.length() - 1);
		String thumbnailURL = thumbnail.getString("url").split("[?]")[0];

		String publishDate = playerMFR.getString("publishDate");
		long uploaded;
		try {
			uploaded = TimeUtil.parseUploaded(publishDate);
		} catch (ParseException e) {
			uploaded = 0L;
		}

		VideoCategory videoCategory = VideoCategory.NONE;
		if (playerMFR.has("category")) {
			String category = playerMFR.getString("category");
			videoCategory = VideoCategory.parseCategory(category);
		}

		String description = "";
		if (playerMFR.has("description")) {
			JSONObject descriptionObj = playerMFR.getJSONObject("description");
			description = descriptionObj.getString("simpleText");
		}

		return new YTVideoImpl()
				.setVideoID(videoID)
				.setTitle(title)
				.setDuration(duration)
				.setAuthor(author)
				.setViewCount(viewCount)
				.setChannelID(channelID)
				.setThumbnailURL(thumbnailURL)
				.setUploaded(uploaded)
				.setCategory(videoCategory)
				.setDescription(description);
	}
}
