package main;

public class YTVideo {
	
	private String ID;
	private String title;
	private long duration;
	private String upload;
	private String category;
	
	public void setID(String ID) {
		this.ID = ID;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public void setUpload(String upload) {
		this.upload = upload;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}

	public String getID() {
		return ID;
	}
	
	public String getURL() {
		return "https://www.youtube.com/watch?v=" + getID();
	}

	public String getTitle() {
		return title;
	}

	public long getDuration() {
		return duration;
	}

	public String getUpload() {
		return upload;
	}

	public String getCategory() {
		return category;
	}

}
