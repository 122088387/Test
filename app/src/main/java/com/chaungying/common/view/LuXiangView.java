package com.chaungying.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.chaungying.wuye3.R;

/**
 * Created by Administrator on 2016/6/19.
 */
public class LuXiangView extends FrameLayout {

    private ImageView iv_luxiang,iv_show_luxiang;
    private VideoView first_videoView;
    private TextView tv_luxiang;
    public LuXiangView(Context context) {
        this(context,null);
    }

    public LuXiangView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LuXiangView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.luxiang_layout,this);
        iv_luxiang = (ImageView) view.findViewById(R.id.iv_luxiang);
        tv_luxiang = (TextView) view.findViewById(R.id.tv_luxiang);
        iv_show_luxiang = (ImageView) view.findViewById(R.id.iv_show_luxiang);
        first_videoView = (VideoView) view.findViewById(R.id.first_videoView);
    }
    public void setTile(String string){
        tv_luxiang.setText(string);
    }
    public VideoView getFirst_videoView() {
        return first_videoView;
    }

    public ImageView getIv_show_luxiang() {
        return iv_show_luxiang;
    }

    /**
     * 给录像中的控件设置监听
     * @param listener
     */
    public void setOnLuXiangListener(OnClickListener listener){
        iv_luxiang.setOnClickListener(listener);
        iv_show_luxiang.setOnClickListener(listener);
    }

    @SuppressLint("NewApi")
    public void setSuoLueTu(Bitmap bitmap){
//        iv_show_luxiang.setImageBitmap(bitmap);
        iv_show_luxiang.setBackground(new BitmapDrawable(bitmap));
    }
    /**
     * 设置播放视频的View是否显示
     * @param tag
     */
    public void setVideoViewVisible(int tag){
        first_videoView.setVisibility(tag);
    }

    /**
     * 设置缩略图是否显示
     * @param tag
     */
    public void setSuoLueTuVisible(int tag){
        iv_show_luxiang.setVisibility(tag);
    }
}
