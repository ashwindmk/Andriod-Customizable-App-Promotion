package com.example.ashwin.customizableapppromotion;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by ashwin on 12/9/16.
 */

public class AppViewHolder extends RecyclerView.ViewHolder {

    View mView;

    public AppViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setTitle(String name) {
        TextView textview_title = (TextView) mView.findViewById(R.id.title);
        if(name != null)
        {
            if( !name.equals("") )
            {
                textview_title.setVisibility(View.VISIBLE);
                textview_title.setText(name);
            }
            else
            {
                textview_title.setVisibility(View.GONE);
            }
        }
        else
        {
            textview_title.setVisibility(View.GONE);
        }

    }

    public void setSubTitle(String address) {
        TextView textview_subtitle = (TextView) mView.findViewById(R.id.subtitle);
        if(address != null)
        {
            if( !address.equals("") )
            {
                textview_subtitle.setVisibility(View.VISIBLE);
                textview_subtitle.setText(address);
            }
            else
            {
                textview_subtitle.setVisibility(View.GONE);
            }
        }
        else
        {
            textview_subtitle.setVisibility(View.GONE);
        }

    }

    public void setAppLogo(Context context, String url) {
        ImageView imageViewAppLogo = (ImageView) mView.findViewById(R.id.app_logo);
        if( !url.equals("") ) {
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();

            imageLoader.displayImage(url, imageViewAppLogo, options);
            imageViewAppLogo.setVisibility(View.VISIBLE);
        }
        else
        {
            imageViewAppLogo.setVisibility(View.GONE);
        }
    }

    public void setAppImage(Context context, String url) {
        ImageView imageViewAppImage = (ImageView) mView.findViewById(R.id.app_image);
        if( !url.equals("") )
        {
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .showImageOnLoading(R.drawable.loading)
                    .build();

            imageLoader.displayImage(url, imageViewAppImage, options);
            imageViewAppImage.setVisibility(View.VISIBLE);
        }
        else
        {
            imageViewAppImage.setVisibility(View.GONE);
        }

    }

    public void setCallToActionText(String callToAction) {
        Button buttonCallToActionText = (Button) mView.findViewById(R.id.call_to_action);
        if( (callToAction != null))
        {
            if( !callToAction.equals("") )
            {
                buttonCallToActionText.setVisibility(View.VISIBLE);
                buttonCallToActionText.setText(callToAction);
            }
            else
            {
                buttonCallToActionText.setVisibility(View.GONE);
            }
        }
        else
        {
            buttonCallToActionText.setVisibility(View.GONE);
        }
    }

}

