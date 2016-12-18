package com.chaungying.zixunjieda.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaungying.gongzuotai.ui.MsgListDetailsActivity;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.wuye3.R;
import com.chaungying.zixunjieda.bean.ShowDetailsBean;
import com.chaungying.zixunjieda.bean.ShowDetailsMaterialBean;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/8/5
 */
public class DetailsValueView extends FrameLayout implements View.OnClickListener {


    private Context mContext;

    private TextView title;
    private ImageView value;
    private LinearLayout add_details_material;

    private List<List<ShowDetailsMaterialBean.DatasBean>> datas = new ArrayList<List<ShowDetailsMaterialBean.DatasBean>>();

    private ShowDetailsBean.DatasBean bean;

    private OnRemoveDetailsListener onRemoveDetailsListener;

    public void setOnRemoveDetailsListener(OnRemoveDetailsListener onRemoveDetailsListener) {
        this.onRemoveDetailsListener = onRemoveDetailsListener;
    }

    public void setDatas(List<List<ShowDetailsMaterialBean.DatasBean>> datas) {
        this.datas = datas;
        creatDetailsView();
    }

    /**
     * 创建详细界面
     */
    private void creatDetailsView() {
        for (int i = 0; i < datas.size(); i++) {
            List<ShowDetailsMaterialBean.DatasBean> list = datas.get(i);
            for (int j = 0; j < list.size(); j++) {
                TitleValueDetailsView titleView = new TitleValueDetailsView(mContext);
                ShowDetailsMaterialBean.DatasBean bean = list.get(j);
                titleView.setBean(bean);
                add_details_material.addView(titleView);
            }
        }
    }

    public void setBean(ShowDetailsBean.DatasBean bean) {
        this.bean = bean;
        initData();
    }

    public DetailsValueView(Context context) {
        this(context, null);
    }

    private String mingxi_url = "";

    public void setMingxi_url(String mingxi_url) {
        this.mingxi_url = mingxi_url;
        getMingXi();
    }

    private void getMingXi() {
        RequestParams params = new RequestParams(mingxi_url);
        params.addParameter("logicId", MsgListDetailsActivity.logicId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ShowDetailsMaterialBean bean = gson.fromJson(result, ShowDetailsMaterialBean.class);
                if (bean.getDatas().size() > 0) {
                    setDatas(bean.getDatas());
                } else {
                    //回调  将材料明细控件移除掉
                    if (onRemoveDetailsListener != null) {
                        onRemoveDetailsListener.removeDetailsListener();
                    }
                }
                ProgressUtil.close();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 移除材料明细控件的回调
     */
    public interface OnRemoveDetailsListener {
        void removeDetailsListener();
    }


    public DetailsValueView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DetailsValueView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.details_value_view, this);
        title = (TextView) findViewById(R.id.title);
        value = (ImageView) findViewById(R.id.value);
        add_details_material = (LinearLayout) findViewById(R.id.add_details_material);
        this.setOnClickListener(this);
    }

    private void initData() {
        title.setText(bean.getTitle());
        value.setImageResource(R.drawable.icon_arrow_downward);
//        192.168.1.122/propertyInterface/applicationList/getApplicationDetailByIdForList.action?entityName=ApplicationDetailList&appId=134
//        String linkUrl = bean.getLinkUrl();

    }

    private boolean isShow;

    @Override
    public void onClick(View v) {
        if (!isShow) {
            add_details_material.setVisibility(View.VISIBLE);
            value.setImageResource(R.drawable.icon_arrow_upward);
        } else {
            add_details_material.setVisibility(View.GONE);
            value.setImageResource(R.drawable.icon_arrow_downward);
        }
        isShow = !isShow;
    }
}
