package com.codepath.syed.instagramviewer;

public class InstagramPhoto {
	// username, height, caption, image_url, likes_count;

	public String username;
	public String caption;
	public String imageUrl;
	public int imageHeight;
	public int likesCount;
	
	public String toString(){
		return "image - "+ imageUrl;
	}
}
