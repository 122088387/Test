package com.chaungying.common.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.wuye3.R;

/**
 * @author 王晓赛 or 2016/7/18
 */
public class ShowImageView extends FrameLayout {
    private TextView textView;
    private ImageView imageView;
    public ShowImageView(Context context) {
        this(context, null);
    }

    public ShowImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShowImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.show_image_view, this);
        textView = (TextView) findViewById(R.id.show_text);
        imageView = (ImageView) findViewById(R.id.iv_add);
    }

    public void setText(String showStr) {
        textView.setText(showStr);
    }

    public void setImage(int resourceId){
        imageView.setImageResource(resourceId);
    }
    public void setImage(Drawable drawable){
        imageView.setImageDrawable(drawable);
    }
}