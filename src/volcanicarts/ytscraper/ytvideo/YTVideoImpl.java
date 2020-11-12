package volcanicarts.ytscraper.ytvideo;

import java.util.Date;

import volcanicarts.ytscraper.util.TimeUtil;

/**
 * The YTVideo implementation class 
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
	
	public YTVideoImpl setVideoID(String videoID) {
		this.videoID = videoID;
		return this;
	}
	
	public YTVideoImpl setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public YTVideoImpl setDuration(long duration) {
		this.duration = duration;
		return this;
	}
	
	public YTVideoImpl setUploaded(long uploaded) {
		this.uploaded = uploaded;
		return this;
	}
	
	public YTVideoImpl setCategory(VideoCategory category) {
		this.category = category;
		return this;
	}
	
	public YTVideoImpl setThumbnailURL(String thumbnailURL) {
		this.thumbnailURL = thumbnailURL;
		return this;
	}
	
	public YTVideoImpl setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public YTVideoImpl setAuthor(String author) {
		this.author = author;
		return this;
	}
	
	public YTVideoImpl setViewCount(long viewCount) {
		this.viewCount = viewCount;
		return this;
	}
	
	public YTVideoImpl setChannelID(String channelID) {
		this.channelID = channelID;
		return this;
	}

	@Override
	public String getVideoID() {
		return videoID;
	}
	
	@Override
	public String getURL() {
		return String.format("https://www.youtube.com/watch?v=%s", getVideoID());
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public long getDuration() {
		return duration;
	}
	
	@Override
	public String getDurationFormatted() {
		return TimeUtil.convertMilliToHHMMSS(getDuration());
	}

	@Override
	public long getUploaded() {
		return uploaded;
	}
	
	@Override
	public String getUploadedFormatted() {
		return new Date(getUploaded()).toString();
	}

	@Override
	public VideoCategory getCategory() {
		return category;
	}

	@Override
	public String getThumbnailURL() {
		return this.thumbnailURL;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public String getAuthor() {
		return this.author;
	}

	@Override
	public long getViewCount() {
		return this.viewCount;
	}
	
	@Override
	public String getViewCountFormatted() {
		return String.format("%,d", this.viewCount);
	}

	@Override
	public String getChannelID() {
		return this.channelID;
	}

}
