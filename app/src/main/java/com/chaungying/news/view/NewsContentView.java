package com.chaungying.news.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chaungying.wuye3.R;


/**
 * 新闻正文
 *
 * @author 种耀淮
 */
public class NewsContentView extends FrameLayout {

    public NewsContentView(Context context, String str) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_news_content, this);
        TextView textView = (TextView) findViewById(R.id.view_news_content_tv);
        textView.setText(str);
    }
}
