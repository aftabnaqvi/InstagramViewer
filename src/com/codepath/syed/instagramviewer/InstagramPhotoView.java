package com.codepath.syed.instagramviewer;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class InstagramPhotoView extends RelativeLayout {
	ImageView imageProfile;
	TextView tvUsername;
	TextView tvLocation;
	TextView tvCaption;
	TextView tvlikes;
	ImageView imagePhoto;
	ImageView imageLike;
	
	
	private Context context;
    public static InstagramPhotoView inflate(ViewGroup parent) {
        InstagramPhotoView itemView = (InstagramPhotoView)LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_view, parent, false);
        return itemView;
    }

    public InstagramPhotoView(Context c) {
        this(c, null);
    }

    public InstagramPhotoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InstagramPhotoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.item_photo, this, true);
        this.context = context;
        setupChildren();
    }
    public void setContext(Context context){
    	this.context = context;
    }
    private void setupChildren() {
//        mTitleTextView = (TextView) findViewById(R.id.item_titleTextView);
//        mDescriptionTextView = (TextView) findViewById(R.id.item_descriptionTextView);
//        mImageView = (ImageView) findViewById(R.id.item_imageView);
    	
    	imageProfile = (ImageView)findViewById(R.id.imageProfile);
		tvUsername = (TextView)findViewById(R.id.tvUserName);
		//tvLocation = (TextView)findViewById(R.id.tvLocation);
		tvCaption = (TextView)findViewById(R.id.tvCaption);
		tvlikes = (TextView)findViewById(R.id.tvLikes);
		imagePhoto = (ImageView)findViewById(R.id.imagePhoto);
		//imageLike = (ImageView)findViewById(R.id.imageLike);
    }

    public void setItem(InstagramPhoto photo) {
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
    }
    
    public DisplayMetrics getDisplayMetrics(){
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metrics);
		return metrics;
	}

	public ImageView getImageProfile() {
		return imageProfile;
	}

	public void setImageProfile(ImageView imageProfile) {
		this.imageProfile = imageProfile;
	}

	public TextView getTvUsername() {
		return tvUsername;
	}

	public void setTvUsername(TextView tvUsername) {
		this.tvUsername = tvUsername;
	}

	public TextView getTvLocation() {
		return tvLocation;
	}

	public void setTvLocation(TextView tvLocation) {
		this.tvLocation = tvLocation;
	}

	public TextView getTvCaption() {
		return tvCaption;
	}

	public void setTvCaption(TextView tvCaption) {
		this.tvCaption = tvCaption;
	}

	public TextView getTvlikes() {
		return tvlikes;
	}

	public void setTvlikes(TextView tvlikes) {
		this.tvlikes = tvlikes;
	}

	public ImageView getImagePhoto() {
		return imagePhoto;
	}

	public void setImagePhoto(ImageView imagePhoto) {
		this.imagePhoto = imagePhoto;
	}

	public ImageView getImageLike() {
		return imageLike;
	}

	public void setImageLike(ImageView imageLike) {
		this.imageLike = imageLike;
	}
}
