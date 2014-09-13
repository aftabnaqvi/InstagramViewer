package com.codepath.syed.instagramviewer;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
	
	public InstagramPhotosAdapter(Context context, List<InstagramPhoto> photos){
		super(context, R.layout.item_photo, photos);
	}
	
	// Takes a data item at the position, converts it to a row in the listview.
	//by default it takes the model (IsntagramPhoto)
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Takes the data source at position.
		
		// Step-1 - get the data item.
		InstagramPhoto photo = getItem(position);
		
		// Step-2 - Check if we are using recycle view
		if(convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false); // false mean don't attach
		}
		
		// Step-3 - Lookup the subview within the template
		ImageView imageProfile = (ImageView)convertView.findViewById(R.id.imageProfile);
		TextView tvUsername = (TextView)convertView.findViewById(R.id.tvUserName);
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
			// resizing the image, insert bitmap into the imageView
			Picasso.with(getContext()).load(photo.imageProfileUrl).into(imageProfile);
		}
		
		if(tvUsername != null){
			tvUsername.setText(photo.username);
		}
		
		if(tvlikes != null && imageLike != null){
			imageLike.setImageResource(0);
			tvlikes.setText("");
			if(photo.likesCount > 1){
				imageLike.setImageResource(R.drawable.heart);
				tvlikes.setText(" " + photo.likesCount + " likes");
			}
		}
		
		// set the image height before loading
		if(imagePhoto != null){
			imagePhoto.getLayoutParams().height = photo.imageHeight; // first set the height
		}
		
		// clear the image from the image view, it might be the recycled imageView...
		// Reset the image from the recycle view
		imagePhoto.setImageResource(0);
		
		// Ask for the photo to be added ot the imageView based on the photo url.
		// Background work: send a network request to the url, download the image bytes, covert into bitmap, 
		// resizing the image, insert bitmap into the imageView
		Picasso.with(getContext()).load(photo.imageUrl).into(imagePhoto);
		
		// return the view for that data item.
		return convertView;
	}

}
