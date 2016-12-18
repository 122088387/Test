package com.chaungying.zixunjieda.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chaungying.wuye3.R;
import com.chaungying.zixunjieda.bean.ShowDetailsMaterialBean;

/**
 * @author 王晓赛 or 2016/8/5
 */
public class TitleValueDetailsView extends FrameLayout {


    private Context mContext;

    private TextView title, value;

    private ShowDetailsMaterialBean.DatasBean bean;

    public void setBean(ShowDetailsMaterialBean.DatasBean bean) {
        this.bean = bean;
        initData();
    }


    public TitleValueDetailsView(Context context) {
        this(context, null);
    }

    public TitleValueDetailsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleValueDetailsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.title_value_view, this);
        title = (TextView) findViewById(R.id.title);
        value = (TextView) findViewById(R.id.value);
    }

    private void initData() {
        title.setText(bean.getTitle());
        value.setText(bean.getValue()==null?"":bean.getValue().toString());
    }
}
