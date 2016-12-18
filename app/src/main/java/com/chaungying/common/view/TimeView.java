package com.chaungying.common.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.wuye3.R;

/**
 * @author 王晓赛 or 2016/6/24
 */
public class TimeView  extends FrameLayout {
    ImageView iv;
    TextView tv,tv_show;
    public TimeView(Context context) {
        this(context, null);
    }

    public TimeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeView(Context context, AttributeSet attrs, int defStyleAttr) {
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
