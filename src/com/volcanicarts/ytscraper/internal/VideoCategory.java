package com.volcanicarts.ytscraper.internal;

/**
 * The category of a returned video
 * @author VolcanicArts
 * @since 1.0.0
 */
public enum VideoCategory {
	
	Film_and_Animation("Film & Animation", 1),
	Autos_and_Vehicles("Autos & Vehicles", 2),
	Music("Music", 10),
	Pets_and_Animals("Pets & Animals", 15),
	Sports("Sports", 17),
	Short_Movies("Short Movies", 18),
	Travel_and_Events("Travel & Events", 19),
	Gaming("Gaming", 20),
	Videoblogging("Videoblogging", 21),
	People_and_Blogs("People & Blogs", 22),
	Comedy("Comedy", 23),
	Entertainment("Entertainment", 24),
	News_and_Politics("News & Politics", 25),
	Howto_and_Style("Howto & Style", 26),
	Education("Education", 27),
	Science_and_Technology("Science & Technology", 28),
	Nonprofits_and_Activism("Nonprofits & Activism", 29),
	Movies("Movies", 30),
	Anime_or_Animation("Anime/Animation", 31),
	Action_or_Adventure("Action/Adventure", 32),
	Classics("Classics", 33),
	//Comedy("Comedy", 34),
	Documentary("Documentary", 35),
	Drama("Drama", 36),
	Family("Family", 37),
	Foreign("Foreign", 38),
	Horror("Horror", 39),
	SciFi_or_Fantasy("Sci-Fi/Fantasy", 40),
	Thriller("Thriller", 41),
	Shorts("Shorts", 42),
	Shows("Shows", 43),
	Trailers("Trailers", 44);
	
	private String actualName;
	private int ID;
	
	VideoCategory(String actualName, int ID) {
		this.actualName = actualName;
		this.ID = ID;
	}

	/**
	 * @return The name provided by YouTube
	 */
	public String getActualName() {
		return actualName;
	}

	/**
	 * @return The ID provided by YouTube
	 */
	public int getID() {
		return ID;
	}

}
