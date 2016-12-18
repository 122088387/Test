package com.chaungying.modues.main.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.wuye3.R;

/**
 * 主界面下边的自定义按钮
 * @anthor 王晓赛 or 2016/6/22
 */
public class MenuButton extends FrameLayout {
    //按钮图片
    ImageView imageView;
    //按钮文字
    TextView textView;
    public MenuButton(Context context) {
        this(context, null);
    }

    public MenuButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.main_menu_item, this);
        imageView = (ImageView) view.findViewById(R.id.iv_menu);
        textView = (TextView) view.findViewById(R.id.tv_menu);
    }
    public void setMemuType(int imageRes,String text,int textColor){
        imageView.setImageResource(imageRes);
        textView.setText(text);
        textView.setTextColor(textColor);
    }
}
