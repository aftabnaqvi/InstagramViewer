package com.codepath.syed.instagramviewer;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.text.SpannableString;
import android.text.format.DateUtils;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.syed.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
	// View lookup cache
    private static class ViewHolder {
    	//private CircularImageView imageProfile;
    	private ImageView imageProfile;
    	private TextView tvUsername;
    	private TextView tvLocation;
    	private TextView tvCaption;
    	private TextView tvlikes;
    	private ImageView imagePhoto;
    	private ImageView imageTime;
    	private TextView tvTime;
    	private TextView tvComments;
    	
    	private static Context context = null;
    	public void updateViewHolder(Context context, InstagramPhoto photo){
    		if(context != null){
    			ViewHolder.context = context;
    		}
    		
    		if(tvCaption!=null){
    			String userName = photo.username;

    			SpannableString result =  new SpannableString(userName);
    			result.setSpan(new StyleSpan(Typeface.BOLD), 0, result.length(), 0); 
    			tvCaption.setText("");
    			tvCaption.append(result);
    			if(photo.caption != null){
    				tvCaption.append(" -- " + photo.caption);
    			}
    		}

    		if(imageProfile != null){
    			Picasso.with(context).load(photo.imageProfileUrl).into(imageProfile);
    		}
    		
    		if(tvUsername != null){
    			tvUsername.setText(photo.username);
    		}
    		
    		if(tvLocation != null){
    			tvLocation.setText("Location comes here...");
    		}
    		
    		if(tvlikes != null/* && imageLike != null*/){
    			tvlikes.setText("");
    			if(photo.likesCount > 1){
    				tvlikes.setText(Html.fromHtml("&#9829").toString() + photo.likesCount + " likes");
    			}
    		}
    		
    		if(imageTime != null){
    			imageTime.setImageResource(R.drawable.clock_blue);
    		}
    		
    		if(tvTime != null){
    			tvTime.setText(DateUtils.getRelativeTimeSpanString(photo.timeCreated*1000, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS));
    		}
    		
    		// set the image height before loading
    		if(imagePhoto != null){
    			DisplayMetrics metrics = getDisplayMetrics();
    			imagePhoto.getLayoutParams().height = metrics.heightPixels/2 * photo.imageWidth/photo.imageHeight; // first set the height
    			imagePhoto.getLayoutParams().width = metrics.widthPixels; //  set the width
    			
    			// clear the image from the image view, it might be the recycled imageView...
    			// Reset the image from the recycle view
    			imagePhoto.setImageResource(0);
    			
    			// Ask for the photo to be added ot the imageView based on the photo url.
    			// Background work: send a network request to the url, download the image bytes, covert into bitmap, 
    			// resizing the image, insert bitmap into the imageView
    			//Picasso.with(getContext()).load(photo.imageUrl).into(imagePhoto);
    			//Picasso.with(getContext()).load(photo.imageUrl).resize(metrics.widthPixels,metrics.heightPixels/2).into(imagePhoto);
    			//Picasso.with(getContext()).load(photo.imageUrl).resize(metrics.widthPixels, metrics.heightPixels/2).centerInside().into(imagePhoto);
    			Picasso.with(context).load(photo.imageUrl).fit().centerInside().into(imagePhoto);
    		}
    		
    		if(tvComments != null){
    		;

    			SpannableString result =  new SpannableString("Comments:");
    			result.setSpan(new StyleSpan(Typeface.BOLD), 0, result.length(), 0); 
    			tvComments.setText("");
    			tvComments.append(result);
    			
    			// I know there might be a better sol. but at this time I came up with this approach at the middle of the night.
    			if(photo.comments.length == 2){
    				String recentCommentTime = (String) DateUtils.getRelativeTimeSpanString(photo.comments[0].createdTime*1000, 
    						System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);
    				String oldCommentTime = (String) DateUtils.getRelativeTimeSpanString(photo.comments[1].createdTime*1000, 
    						System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);
    				
    				tvComments.append("\n"+ recentCommentTime +": "+ photo.comments[0].comment + "\n" + oldCommentTime +": " + photo.comments[1].comment);
    			} else if(photo.comments.length == 1){
    				String recentCommentTime = (String) DateUtils.getRelativeTimeSpanString(photo.comments[0].createdTime*1000, 
    						System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);
    				tvComments.append("\n"+ recentCommentTime +": " + photo.comments[0].comment);
    			}
    		}
    	}
    	
    	// not sure where to put this method.. for now leave it here.
    	public DisplayMetrics getDisplayMetrics(){
    		DisplayMetrics metrics = new DisplayMetrics();
    		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    		wm.getDefaultDisplay().getMetrics(metrics);
    		return metrics;
    	}
    }
	
	
	private Context context;
	public InstagramPhotosAdapter(Context context, List<InstagramPhoto> photos){
		super(context, R.layout.item_photo, photos);
		this.context = context;
	}
	
	// Takes a data item at the position, converts it to a row in the listview.
	//by default it takes the model (IsntagramPhoto)
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Takes the data source at position.
  
		// Step-1 - get the data item.
		InstagramPhoto photo = getItem(position);
		ViewHolder viewHolder = null;
		
		// Step-2 - Check if we are using recycle view
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false); // false mean don't attach
			
			// Step-3 - Lookup the subview within the template
			//viewHolder.imageProfile = (CircularImageView)convertView.findViewById(R.id.imageProfile);
			viewHolder.imageProfile = (ImageView)convertView.findViewById(R.id.imageProfile);
			viewHolder.tvUsername = (TextView)convertView.findViewById(R.id.tvUserName);
			//viewHolder.tvLocation = (TextView)convertView.findViewById(R.id.tvLocation);
			viewHolder.tvCaption = (TextView)convertView.findViewById(R.id.tvCaption);
			viewHolder.tvlikes = (TextView)convertView.findViewById(R.id.tvLikes);
			viewHolder.imagePhoto = (ImageView)convertView.findViewById(R.id.imagePhoto);
			viewHolder.imageTime = (ImageView)convertView.findViewById(R.id.imageTime);
			viewHolder.tvTime = (TextView)convertView.findViewById(R.id.tvTime);
			viewHolder.tvComments = (TextView)convertView.findViewById(R.id.tvComments);

			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		// Step-4 - Populate the subview ( textfield, imageView with the correct data)
		viewHolder.updateViewHolder(context, photo);
		
		// return the view for that data item.
		return convertView;
	}

	public boolean isEnabled(int position) 
    { 
		return false; 
    } 
}
