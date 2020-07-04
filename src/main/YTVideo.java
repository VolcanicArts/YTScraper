package main;

public class YTVideo {
	
	private String ID;
	private String title;
	private long duration;
	private long upload;
	private int category;
	
	public void setID(String iD) {
		ID = iD;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public void setUpload(long upload) {
		this.upload = upload;
	}
	
	public void setCategory(int category) {
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

	public long getUpload() {
		return upload;
	}

	public int getCategory() {
		return category;
	}

}
