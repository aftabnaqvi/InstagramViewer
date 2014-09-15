package com.codepath.syed.instagramviewer;

public class InstagramPhoto {
	// username, height, caption, image_url, likes_count;

	public String username;
	public String caption;
	public String imageUrl;
	public String imageProfileUrl;
	public int imageHeight;
	public int imageWidth;
	public int likesCount;
	public long timeCreated;
	
	public String toString(){
		return "image - "+ imageUrl;
	}
}
