package com.chaungying.avery_menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chaungying.wuye3.R;

/**
 * @author 王晓赛 or 2016/7/18
 */
public class ShowTextLineView extends FrameLayout{
    private TextView textView;
    private ImageView iv_menu;
    private Context mContext;
    public ShowTextLineView(Context context) {
        this(context,null);
    }

    public ShowTextLineView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShowTextLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.show_text_line_view,this);
        textView = (TextView) findViewById(R.id.show_text);
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
    }
    public void setUrl(String url){
        Glide.with(mContext).load(url).into(iv_menu);
    }
    public void setText(String showStr){
        textView.setText(showStr);
    }

    /**
     * 设置展示文本的样式
     * @param color
     *          颜色
     * @param size
     *          字体大小
     * @param backgroundColor
     *          字体背景颜色
     */
    public void setTextViewType(int color,int size,int backgroundColor){
        textView.setTextSize(size);
        textView.setTextColor(color);
        textView.setBackgroundColor(backgroundColor);
    }
}
