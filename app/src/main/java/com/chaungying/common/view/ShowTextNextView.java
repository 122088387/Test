package com.chaungying.common.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chaungying.common.ui.FuDongActivity;
import com.chaungying.wuye3.R;

/**
 * @author 王晓赛 or 2016/7/18
 */
public class ShowTextNextView extends FrameLayout {
    private TextView textView;

    private String title;

    public ShowTextNextView(Context context) {
        this(context, null);
    }

    public ShowTextNextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShowTextNextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.show_text_next_view, this);
        textView = (TextView) findViewById(R.id.show_text);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                intentActivity();
            }
        });
    }

    private void intentActivity() {
        Intent intent = new Intent(getContext(), FuDongActivity.class);
        intent.putExtra("data", title);
        getContext().startActivity(intent);
        ((Activity) getContext()).overridePendingTransition(R.anim.intent_alpha_in, R.anim.intent_alpha_out);
    }

    public void setText(String showStr) {
        textView.setText(showStr);
        title = showStr;
    }

    /**
     * 设置展示文本的样式
     *
     * @param color           颜色
     * @param size            字体大小
     * @param backgroundColor 字体背景颜色
     */
    public void setTextViewType(int color, int size, int backgroundColor) {
        textView.setTextSize(size);
        textView.setTextColor(color);
        textView.setBackgroundColor(backgroundColor);
    }
}
