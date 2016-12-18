package com.chaungying.common.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.wuye3.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/12.
 */
public class MoreSelectView extends FrameLayout {
    ImageView iv;
    TextView tv,tv_show;
    private String moreSelectUrl;

    public String getMoreSelectUrl() {
        return moreSelectUrl;
    }

    public void setMoreSelectUrl(String moreSelectUrl) {
        this.moreSelectUrl = moreSelectUrl;
    }

    //表明它的样式标记
    ArrayList<String> str = new ArrayList<String>();

    public void setSubtitles(ArrayList<String> list){
        str = list;
    }
    public ArrayList<String> getSubtitles(){
        return str;
    }

    public MoreSelectView(Context context) {
        this(context, null);
    }

    public MoreSelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoreSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.show_layout,this);
        iv = (ImageView) findViewById(R.id.iv_paigong);
        tv = (TextView) findViewById(R.id.tv_paigong);
        tv_show = (TextView) findViewById(R.id.tv_show_result);
    }
    public void setIvImage(Bitmap bitmap){
        iv.setImageBitmap(bitmap);
    }
    public void setTvText(String str){
        tv.setText(str);
    }
    public String getTvText(){
        return tv.getText().toString();
    }
    public void setTv_show(String string){
        tv_show.setText(string);
    }
}
