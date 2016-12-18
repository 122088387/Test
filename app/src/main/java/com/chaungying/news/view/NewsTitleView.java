package com.chaungying.news.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chaungying.wuye3.R;


/**
 * 新闻标题
 *
 * @author 种耀淮
 */
public class NewsTitleView extends FrameLayout {

    public NewsTitleView(Context context, String str) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_news_title, this);
        TextView textView = (TextView) findViewById(R.id.view_news_title_tv);
        textView.setText(str);
    }
}
