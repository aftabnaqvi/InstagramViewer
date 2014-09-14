package com.codepath.syed.instagramviewer;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
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
		
		
		InstagramPhotoView itemView = (InstagramPhotoView)convertView;
        if (null == itemView)
            itemView = InstagramPhotoView.inflate(parent);
        
        itemView.setContext(context);
        itemView.setItem(getItem(position));
        return itemView;
        
        /*
        
		// Step-1 - get the data item.
		InstagramPhoto photo = getItem(position);
		
		// Step-2 - Check if we are using recycle view
		if(convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false); // false mean don't attach
		}
		
		// Step-3 - Lookup the subview within the template
		ImageView imageProfile = (ImageView)convertView.findViewById(R.id.imageProfile);
		TextView tvUsername = (TextView)convertView.findViewById(R.id.tvUserName);
		TextView tvLocation = (TextView)convertView.findViewById(R.id.tvLocation);
		TextView tvCaption = (TextView)convertView.findViewById(R.id.tvCaption);
		TextView tvlikes = (TextView)convertView.findViewById(R.id.tvLikes);
		ImageView imagePhoto = (ImageView)convertView.findViewById(R.id.imagePhoto);
		ImageView imageLike = (ImageView)convertView.findViewById(R.id.imageLike);
		
		// Step-4 - Populate the subview ( textfield, imageView with the correct data)
		if(tvCaption!=null){
			String userName = photo.username;

			SpannableString result =  new SpannableString(userName);
			result.setSpan(new StyleSpan(Typeface.BOLD), 0, result.length(), 0); 
			tvCaption.setText("");
			tvCaption.append(result);
			tvCaption.append(" -- " + photo.caption);
		}

		if(imageProfile != null){
			//imageProfile.getLayoutParams().height = 130; // first set the height
			//imageProfile.getLayoutParams().width = 130; //  set the width
			Picasso.with(getContext()).load(photo.imageProfileUrl).into(imageProfile);
		}
		
		if(tvUsername != null){
			tvUsername.setText(photo.username);
		}
		
		if(tvLocation != null){
			tvLocation.setText("New York....");
		}
		if(tvlikes != null && imageLike != null){
			//imageLike.setImageResource(0);
			tvlikes.setText("");
			if(photo.likesCount > 1){
				imageLike.setImageResource(R.drawable.heart);
				tvlikes.setText("💚" + photo.likesCount + " 💚 likes");
			}
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
			Picasso.with(getContext()).load(photo.imageUrl).fit().centerInside().into(imagePhoto);
		}
		// return the view for that data item.
		return convertView; */
	}

	public boolean isEnabled(int position) 
    { 
		return false; 
    } 
	
	public DisplayMetrics getDisplayMetrics(){
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metrics);
		return metrics;
	}
}
