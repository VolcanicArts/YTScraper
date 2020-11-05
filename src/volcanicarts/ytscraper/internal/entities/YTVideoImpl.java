package volcanicarts.ytscraper.internal.entities;

import java.util.Date;

import volcanicarts.ytscraper.api.entities.YTVideo;
import volcanicarts.ytscraper.internal.util.TimeUtil;

/**
 * The YTVideo implementation class 
 * @author VolcanicArts
 * @since 1.0.0
 */
public class YTVideoImpl implements YTVideo {
	
	private String ID;
	private String title;
	private long duration;
	private long upload;
	private VideoCategory category;
	private String thumbnailURI;
	private String description;
	private String author;
	private long viewCount;
	private String channelID;
	
	public YTVideoImpl setID(String ID) {
		this.ID = ID;
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
	
	public YTVideoImpl setUpload(long upload) {
		this.upload = upload;
		return this;
	}
	
	public YTVideoImpl setCategory(VideoCategory category) {
		this.category = category;
		return this;
	}
	
	public YTVideoImpl setThumbnailURI(String thumbnailURI) {
		this.thumbnailURI = thumbnailURI;
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
	public String getID() {
		return ID;
	}
	
	@Override
	public String getURL() {
		return String.format("https://www.youtube.com/watch?v=%s", getID());
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
	public long getUpload() {
		return upload;
	}
	
	@Override
	public String getUploadFormatted() {
		return new Date(getUpload()).toString();
	}

	@Override
	public VideoCategory getCategory() {
		return category;
	}

	@Override
	public String getThumbnailURI() {
		return this.thumbnailURI;
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
