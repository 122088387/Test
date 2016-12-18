package com.chaungying.common.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chaungying.common.adapter.FillListAdapter;
import com.chaungying.common.bean.ConfigBean;
import com.chaungying.common.bean.FillListBean;
import com.chaungying.common.bean.SubtitlesItem;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.wuye3.R;
import com.chaungying.zixunjieda.ui.ZiXunJieDaDetailsActivity;
import com.chaungying.zixunjieda.ui.ZiXunJieDaListActivity;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/7/27
 *         <p/>
 *         选中之后有下拉的自定义控件
 */

public class DownPopWindowView extends FrameLayout implements View.OnClickListener, AdapterView.OnItemClickListener {


    private List<List<FillListBean.DataBean>> dataBeanList = new ArrayList<>();

    //下拉筛选头部的list集合
    private List<ConfigBean.ItemsBean> itemsBeanList = new ArrayList<ConfigBean.ItemsBean>();
    //筛选的头标题对应的线性布局
    private LinearLayout llPopAdd;
    private Activity mContext;
    //半透明的遮罩层
    private View translucentView;

    //展示某个报修单记录的列表
    private ListView pullListView;
    //某个报修单记录的列表的适配器
    private FillListAdapter fillListAdapter;

    //头部下拉的列表
    private ListView myListView;//类型1、类型2等

    FillListBean bean = null;
    //哪个应用Id
    private int appId = 0;

    public void setItemsBeanList(List<ConfigBean.ItemsBean> itemsBeanList) {
        this.itemsBeanList = itemsBeanList;
        addView();
    }

    public DownPopWindowView(Context context) {
        this(context, null);
    }

    public DownPopWindowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownPopWindowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = (Activity) context;
        LayoutInflater.from(context).inflate(R.layout.down_pop_window_view, this);
        llPopAdd = (LinearLayout) findViewById(R.id.ll_pop_add);
        //对表单列表的设置
        pullListView = (ListView) findViewById(R.id.content_view);
        fillListAdapter = new FillListAdapter(mContext);
        fillListAdapter.setList(dataBeanList);
        pullListView.setAdapter(fillListAdapter);
        pullListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int logicId = bean.getData().get(position).get(0).getLogicId();
                Intent intent = new Intent(mContext, ZiXunJieDaDetailsActivity.class);
                intent.putExtra("appId", appId);
                intent.putExtra("logicId", logicId);
                mContext.startActivity(intent);
            }
        });

        //对下拉选择列表的设置
        myListView = (ListView) findViewById(R.id.my_list_view);
        myListView.setOnItemClickListener(this);
        translucentView = findViewById(R.id.zhe_zhao_ceng);
        //点击遮罩层也消失
        translucentView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
//        myListView.setListViewHeight(0);
        dismiss();
        //更新适配器数据刷新回调
        if (mContext instanceof ZiXunJieDaListActivity) {
            ((ZiXunJieDaListActivity) mContext).setPullRecordListListener(pullRecordListListener);
        }
    }

    List<DownPopWindowItemView> itemViewList = new ArrayList<DownPopWindowItemView>();

    private void addView() {
        for (int i = 0; i < itemsBeanList.size(); i++) {
            ConfigBean.ItemsBean bean = itemsBeanList.get(i);
            DownPopWindowItemView itemView = new DownPopWindowItemView(mContext);
            itemView.setVal(-1);
            itemView.setItemsBean(bean);
            itemView.setOnClickListener(this);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f));
            if (bean.getItemType() == 53) {
                itemView.setText(bean.getTitle());
            } else {
                if (bean.getSubtitles() != null) {
                    itemView.setText(bean.getSubtitles().get(0).getName());
                }
            }
            if (llPopAdd != null) {
                llPopAdd.addView(itemView);
            }
            itemViewList.add(itemView);
        }

        getListData();
    }

    /**
     * 获取列表的数据
     */
    private void getListData() {
        dataBeanList.clear();
        ProgressUtil.show(mContext, "加载中...");
        RequestParams params = new RequestParams(Const.WuYe.URL_LIST);//报修记录list接口
        params.setConnectTimeout(5 * 1000);
        //具体是哪个应用要请求列表数据
        if (mContext instanceof ZiXunJieDaListActivity) {
            appId = ((ZiXunJieDaListActivity) mContext).appId;
        }
        params.addParameter("appId", appId);
        params.addParameter("userId", SPUtils.get(mContext, Const.SPDate.ID, -1));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                bean = gson.fromJson(result, FillListBean.class);
                fillListAdapter.setLayoutId(bean.getLayoutid());
                dataBeanList.addAll(bean.getData());
                fillListAdapter.notifyDataSetChanged();
                ProgressUtil.close();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ProgressUtil.close();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    DownPopWindowView.PullRecordListListener pullRecordListListener = new DownPopWindowView.PullRecordListListener() {
        @Override
        public void onPullRecordListLIstener() {
            getListData();
        }
    };

    /**
     * 刷新记录列表数据的回调接口
     */
    public interface PullRecordListListener {
        void onPullRecordListLIstener();
    }


    List<SubtitlesItem> subList = new ArrayList<SubtitlesItem>();
    DownPopWindowItemView itemView;

    String filedName = "";

    @Override
    public void onClick(final View v) {
        ConfigBean.ItemsBean itemsBean = ((DownPopWindowItemView) v).getItemsBean();
        filedName = itemsBean.getFieldname();
        itemView = ((DownPopWindowItemView) v);
        switch (itemsBean.getItemType()) {
            case 52:
                subList = itemsBean.getSubtitles();
                if (subList != null) {
                    myListView.setAdapter(new MyAdapter(itemView.getTitle()));
                }
                show();
                break;
            case 53:
                dismiss();
                //显示侧拉框
                onClickPullBoxListener.onClickPullBox(itemsBean.getTitle(), itemsBean);
                break;

        }
    }

    private OnClickPullBoxListener onClickPullBoxListener;

    public void setOnClickPullBoxListener(OnClickPullBoxListener onClickPullBoxListener) {
        this.onClickPullBoxListener = onClickPullBoxListener;
    }

    /**
     * 选择筛选按钮之后回调接口
     */
    public interface OnClickPullBoxListener {
        void onClickPullBox(String title, ConfigBean.ItemsBean itemsBean);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dataBeanList.clear();
        ProgressUtil.show(mContext, "加载中...");
        TextView tv = (TextView) view.findViewById(R.id.tv_list_popup_window_item);
        String msg = tv.getText().toString();
        itemView.setText(msg);
        for (int i = 0; i < subList.size(); i++) {
            if (subList.get(i).getName().equals(msg)) {
                itemView.setVal(subList.get(i).getVal());
            }
        }
        dismiss();

        RequestParams params = new RequestParams(Const.WuYe.URL_LIST);
        for (int i = 0; i < itemsBeanList.size(); i++) {
            String fieldName = itemsBeanList.get(i).getFieldname();
            int val = itemViewList.get(i).getVal();
            if (val == -1) {
                params.addParameter(fieldName, "");
            } else {
                params.addParameter(fieldName, val);
            }
        }
        params.addParameter("appId", appId);
        params.addParameter("userId", SPUtils.get(mContext, Const.SPDate.ID, -1));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                bean = gson.fromJson(result, FillListBean.class);
                fillListAdapter.setLayoutId(bean.getLayoutid());
                dataBeanList.addAll(bean.getData());
                fillListAdapter.notifyDataSetChanged();
                ProgressUtil.close();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ProgressUtil.close();
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
     * 遮罩层和view都显示
     */
    private void show() {
        translucentView.setVisibility(View.VISIBLE);
        myListView.setVisibility(View.VISIBLE);
    }

    /**
     * 遮罩层和view都消失
     */
    private void dismiss() {
        myListView.setVisibility(View.GONE);
        translucentView.setVisibility(View.GONE);
    }

    class MyAdapter extends BaseAdapter {

        private String TAG;

        public MyAdapter(String title) {
            TAG = title;
        }

        @Override
        public int getCount() {
            return subList.size();
        }

        @Override
        public Object getItem(int position) {
            return subList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.list_popup_window_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv.setText(subList.get(position).getName());
            for (int i = 0; i < subList.size(); i++) {
                if (subList.get(position).getName().equals(TAG)) {
                    holder.tv.setTextColor(getResources().getColor(R.color.include_title));
                } else {
                    holder.tv.setTextColor(getResources().getColor(R.color.menu_item));
                }
            }
            return convertView;
        }

        class ViewHolder {
            TextView tv;

            ViewHolder(View view) {
                tv = (TextView) view.findViewById(R.id.tv_list_popup_window_item);
            }
        }
    }
}
