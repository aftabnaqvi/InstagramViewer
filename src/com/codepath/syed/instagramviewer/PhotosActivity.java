package com.codepath.syed.instagramviewer;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;


public class PhotosActivity extends Activity {
	public static String CLIENT_ID = "7ab161dcddf04d97905d5778c0875ab0";
	private ArrayList<InstagramPhoto> photos;
	private InstagramPhotosAdapter aPhotos;
	private SwipeRefreshLayout swipeContainer;
	
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        
        // Step - 1
    	photos = new ArrayList<InstagramPhoto>(); // initialize arraylist.
    	
    	// Step - 2
    	//Create adapter, bind it to the data in arraylist.
    	aPhotos = new InstagramPhotosAdapter(this, photos);
    	
    	// Step - 3
    	// populate the data into the listview
    	ListView lvPhotos = (ListView)findViewById(R.id.lvPhotos);
    	
    	// Step - 4
    	// Set the adapter to the listView (population of items)
    	lvPhotos.setAdapter(aPhotos);
    	
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        
     // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
            	fetchPopularPhotos();
            } 
        });
        
        fetchPopularPhotos();
        
        // Configure the refreshing colors
        swipeContainer.setColorScheme(android.R.color.holo_blue_bright, 
                android.R.color.holo_green_light, 
                android.R.color.holo_orange_light, 
                android.R.color.holo_red_light);
    }
    private void fetchPopularPhotos(){
    	//https://api.instagram.com/v1/media/popular?client_id=7ab161dcddf04d97905d5778c0875ab0
    	//https://api.instagram.com/v1/media/popular?client_id=<client_id>
    	// {"data" => [x] => "images" => "standard_resolution" => "url"}
    	
    	// setup popular url endpoint
    	String popularUrl = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
    	
    	// create the network client
    	AsyncHttpClient client = new AsyncHttpClient();
    	
    	// trigger the network request
    	client.get(popularUrl, new JsonHttpResponseHandler(){
    		@Override
    		public void onFailure(int statusCode, Header[] headers,
    				String responseString, Throwable throwable) {
    			
    			Log.e("ERROR", responseString);
    		}
    		
    		@Override
    		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
    			// TODO Auto-generated method stub
    			//super.onSuccess(statusCode, headers, response);
    			
    			// response object = popular photos json.
    			// url, height, username, caption likes-count- thats we need to extract
    			// {"data" => [x] => "images" => "standard_resolution" => "url"}
    			// {"data" => [x] => "images" => "standard_resolution" => "height"}
    			// {"data" => [x] => "user" => "username"}
    			// {"data" => [x] => "caption" => "text"}
    			// {"data" => [x] => "likes" => "count"}
    			//Log.i("INFO", response.toString());
    			
    			JSONArray photosJSON = null;
    			try{
    				photos.clear();
    				// getting array of "data"
    				photosJSON = response.getJSONArray("data");	
    				int size = photosJSON.length();
    				for(int i=0; i<size; i++){
    					JSONObject photoJSON = photosJSON.getJSONObject(i);//1,2,3
    					InstagramPhoto photo = new InstagramPhoto();
    					photo.username = photoJSON.getJSONObject("user").getString("username");
    					photo.imageProfileUrl = photoJSON.getJSONObject("user").getString("profile_picture");
    					
    					// its a work around... 
    					if (!photoJSON.isNull("caption")) {
    						photo.caption = photoJSON.getJSONObject("caption").getString("text");
    					}
    					
    					photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
    					photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
    					photo.imageWidth = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("width");
    					photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
    					photo.timeCreated = photoJSON.getInt("created_time");
    					
    					JSONArray commentsJSON = photoJSON.getJSONObject("comments").getJSONArray("data");
    					//Log.i("INFO:", commentsJSON.toString());
    					if(commentsJSON.length() >= 2){
    						JSONObject commentJSON = (JSONObject) commentsJSON.get(commentsJSON.length() - 1);
    						//Log.i("INFO:", commentJSON.toString());
    						if(commentJSON != null){
    							photo.comments[0].comment = commentJSON.getString("text");
    							photo.comments[0].createdTime = commentJSON.getInt("created_time");
    							photo.comments[0].username = commentJSON.getJSONObject("from").getString("username");
    						}
    						
    						commentJSON = (JSONObject) commentsJSON.get(commentsJSON.length() - 2);
    						//Log.i("INFO:", commentJSON.toString());
    						if(commentJSON != null){
    							photo.comments[1].comment = commentJSON.getString("text");
    							photo.comments[1].createdTime = commentJSON.getInt("created_time");
    							photo.comments[1].username = commentJSON.getJSONObject("from").getString("username");
    						}
    					} else if(commentsJSON.length() == 1) { // if we have only one comment then get that one.
    						JSONObject commentJSON = (JSONObject) commentsJSON.get(commentsJSON.length() - 1);
    						//Log.i("INFO:", commentJSON.toString());
    						if(commentJSON != null){
    							photo.comments[0].comment = commentJSON.getString("text");
    							photo.comments[0].createdTime = commentJSON.getInt("created_time");
    							photo.comments[0].username = commentJSON.getJSONObject("from").getString("username");
    						}
    					}
    					
    					photos.add(photo);
    				}
    				// notify the adapter that it should populate new changes into the listview.
    				aPhotos.notifyDataSetChanged();
    				swipeContainer.setRefreshing(false);
    			} catch(JSONException e){
    				// in case of error...
    				e.printStackTrace();
    			}
    		}
    	});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
