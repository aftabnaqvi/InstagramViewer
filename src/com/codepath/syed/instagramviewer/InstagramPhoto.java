package com.codepath.syed.instagramviewer;

public class InstagramPhoto {
	// username, height, caption, image_url, likes_count;
	class Comments{
		String comment;
		long createdTime;
		String username;
	}
	public String username;
	public String caption;
	public String imageUrl;
	public String imageProfileUrl;
	public int imageHeight;
	public int imageWidth;
	public int likesCount;
	public long timeCreated;
	public Comments comments[]; //0-latest, 1- 2nd latest
	
	InstagramPhoto(){
		comments = new Comments[2];
		comments[0] = new Comments(); // we could use the for loop too
		comments[1] = new Comments();
	}
	
	public String toString(){
		return "image - "+ imageUrl;
	}
}
