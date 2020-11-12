package volcanicarts.ytscraper.ytvideo;

import volcanicarts.ytscraper.util.TimeUtil;

import java.util.Date;

/**
 * The YTVideo implementation class
 *
 * @author VolcanicArts
 * @since 1.0.0
 */
public class YTVideoImpl implements YTVideo {

	private String videoID;
	private String title;
	private long duration;
	private long uploaded;
	private VideoCategory category;
	private String thumbnailURL;
	private String description;
	private String author;
	private long viewCount;
	private String channelID;

	@Override
	public String getVideoID() {
		return videoID;
	}

	public YTVideoImpl setVideoID(String videoID) {
		this.videoID = videoID;
		return this;
	}

	@Override
	public String getURL() {
		return String.format("https://www.youtube.com/watch?v=%s", getVideoID());
	}

	@Override
	public String getTitle() {
		return title;
	}

	public YTVideoImpl setTitle(String title) {
		this.title = title;
		return this;
	}

	@Override
	public long getDuration() {
		return duration;
	}

	public YTVideoImpl setDuration(long duration) {
		this.duration = duration;
		return this;
	}

	@Override
	public String getDurationFormatted() {
		return TimeUtil.convertMilliToHHMMSS(getDuration());
	}

	@Override
	public long getUploaded() {
		return uploaded;
	}

	public YTVideoImpl setUploaded(long uploaded) {
		this.uploaded = uploaded;
		return this;
	}

	@Override
	public String getUploadedFormatted() {
		return new Date(getUploaded()).toString();
	}

	@Override
	public VideoCategory getCategory() {
		return category;
	}

	public YTVideoImpl setCategory(VideoCategory category) {
		this.category = category;
		return this;
	}

	@Override
	public String getThumbnailURL() {
		return this.thumbnailURL;
	}

	public YTVideoImpl setThumbnailURL(String thumbnailURL) {
		this.thumbnailURL = thumbnailURL;
		return this;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	public YTVideoImpl setDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public String getAuthor() {
		return this.author;
	}

	public YTVideoImpl setAuthor(String author) {
		this.author = author;
		return this;
	}

	@Override
	public long getViewCount() {
		return this.viewCount;
	}

	public YTVideoImpl setViewCount(long viewCount) {
		this.viewCount = viewCount;
		return this;
	}

	@Override
	public String getViewCountFormatted() {
		return String.format("%,d", this.viewCount);
	}

	@Override
	public String getChannelID() {
		return this.channelID;
	}

	public YTVideoImpl setChannelID(String channelID) {
		this.channelID = channelID;
		return this;
	}

}
