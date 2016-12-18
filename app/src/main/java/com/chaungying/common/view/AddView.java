package com.chaungying.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaungying.common.bean.ConfigBean;
import com.chaungying.wuye3.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 王晓赛 or 2016/7/15
 *         <p/>
 *         添加明细布局
 */
public class AddView extends FrameLayout implements View.OnClickListener {


    private LinearLayout ll_add_parent;
    private int isRequired;
    private ConfigBean.ItemsBean itemsBean;
    private List<ConfigBean.ItemsBean.DatasBean> datasBeanList = new ArrayList<ConfigBean.ItemsBean.DatasBean>();
    //    private TextView tv_add;
    ShowImageView imageView;
    private Context mContext;

    public int getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(int isRequired) {
        this.isRequired = isRequired;
    }
    //假设数据
//    List<Integer> list = new ArrayList<Integer>();

    public AddView(Context context) {
        this(context, null);
    }

    public AddView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.add_view, this);
        ll_add_parent = (LinearLayout) findViewById(R.id.ll_view_parent);
        imageView = (ShowImageView) findViewById(R.id.show_image_view);
    }

    private void initView() {
        if (itemsBean.getIsRequired() == 0) {
            imageView.setText(itemsBean.getTitle() + "*");
        } else {
            imageView.setText(itemsBean.getTitle());
        }
        imageView.setImage(getResources().getDrawable(R.drawable.add_contactor));
        datasBeanList = itemsBean.getDatas();

//        ll_add_parent.addView(imageView);
        imageView.setOnClickListener(this);
    }

    public ConfigBean.ItemsBean getItemsBean() {
        return itemsBean;
    }

    public void setItemsBean(ConfigBean.ItemsBean itemsBean) {
        this.itemsBean = itemsBean;
        initView();
    }

    /**
     * 点击添加明细按钮
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        final LinearLayout ll_add = new LinearLayout(mContext);
        ll_add.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < datasBeanList.size(); i++) {
            ConfigBean.ItemsBean.DatasBean bean = datasBeanList.get(i);
            int itemType = bean.getItemType();
            if (itemType == 4) {//单行输入
                InputView inputView = new InputView(mContext);
                inputView.setTvText(bean.getTitle());
                inputView.setFieldname((String) bean.getFieldname());
                ll_add.addView(inputView);
            } else if (itemType == 32) {//灰色的组标题
                AddItemView itemView = new AddItemView(mContext);
                itemView.setTitle(bean.getTitle());
                ImageView delect = itemView.getDelectTextView();
                delect.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        T.showShort(mContext, "删除");
                        ll_add_parent.removeView(ll_add);
                    }
                });
                ll_add.addView(itemView);
            }
            ll_add.addView(getLineView(itemType));
        }
        ll_add_parent.addView(ll_add);
        postInvalidate();

    }

    List<HashMap<String, String>> mapList = new ArrayList<HashMap<String, String>>();

    public List<HashMap<String, String>> getUpLoadMapList() {
        mapList.clear();
        int count = ll_add_parent.getChildCount();
        for (int i = 0; i < count; i++) {
            LinearLayout llAdd = (LinearLayout) ll_add_parent.getChildAt(i);
            HashMap<String, String> map = new HashMap<String, String>();
            for (int j = 0; j < llAdd.getChildCount(); j++) {
                if (llAdd.getChildAt(j) instanceof InputView) {
                    InputView inputView = (InputView) llAdd.getChildAt(j);
                    String value = inputView.getInput();
                    map.put(inputView.getFieldname(), value);
                }
            }
            mapList.add(map);
        }
        return mapList;
    }


    public TextView getLineView(int itemType) {
        TextView tv = new TextView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
        if (itemType == 4) {
            params.setMargins(14, 0, 0, 0);
        }
        tv.setLayoutParams(params);
        tv.setBackgroundResource(R.color.huise2);
        return tv;
    }


}
