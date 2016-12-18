package com.chaungying.news.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chaungying.wuye3.R;

/**
 * @author 种耀淮
 */
public class NewsImageView extends FrameLayout {

    public NewsImageView(Context context, String url) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_news_image, this);
        ImageView imageView = (ImageView) findViewById(R.id.view_news_image_iv);
        Glide.with(context).load(url).error(R.mipmap.ic_launcher).into(imageView);
    }

}
