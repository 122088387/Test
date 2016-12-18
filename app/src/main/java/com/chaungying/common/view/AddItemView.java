package com.chaungying.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaungying.wuye3.R;

/**
 * @author 王晓赛 or 2016/7/18
 */
public class AddItemView extends FrameLayout {

    LinearLayout linearLayout;
    TextView title;
    ImageView delect;

    public AddItemView(Context context) {
        this(context, null);
    }

    public AddItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.add_view_item, this);
        delect = (ImageView) findViewById(R.id.delect_add_view_item);
        title = (TextView) findViewById(R.id.title_details);
    }

    public void setTitle(String titleName) {
        title.setText(titleName);
    }

    public ImageView getDelectTextView() {
        return delect;
    }

}
