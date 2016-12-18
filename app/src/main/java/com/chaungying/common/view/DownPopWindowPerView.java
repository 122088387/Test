package com.chaungying.common.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.ji_xiao.bean.JobHeader;
import com.chaungying.ji_xiao.bean.JobPer;
import com.chaungying.ji_xiao.bean.PerCarBean;
import com.chaungying.ji_xiao.bean.PerRepairDispatchBean;
import com.chaungying.ji_xiao.ui.PerRepairDispatchActivity;
import com.chaungying.ji_xiao.ui.PerUserCarActivity;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.round_malls1.bean.ShoppingStoreBean;
import com.chaungying.round_malls1.ui.ShoopingStoreActivity;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王晓赛 or 2016/7/27
 *         <p/>
 *         选中之后有下拉的自定义控件
 */

public class DownPopWindowPerView extends FrameLayout implements View.OnClickListener, AdapterView.OnItemClickListener {

    //下拉筛选头部的list集合
    private List<JobHeader.ItemsBean> itemsBeanList = new ArrayList<>();
    //筛选的头标题对应的线性布局
    private LinearLayout llPopAdd;
    private Activity mContext;
    //半透明的遮罩层
    private View translucentView;

    //展示某个报修单记录的列表
//    private ListView pullListView;
    //某个报修单记录的列表的适配器
//    private FillListAdapter fillListAdapter;

    //头部下拉的列表
    private ListView myListView;//类型1、类型2等

    JobPer bean = null;
    //哪个应用Id
    private int appId = 0;

    //对调处理（将数据源返回到）
    Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setItemsBeanList(List<JobHeader.ItemsBean> itemsBeanList) {
        this.itemsBeanList = itemsBeanList;
        addView();
    }

    public DownPopWindowPerView(Context context) {
        this(context, null);
    }

    public DownPopWindowPerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownPopWindowPerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = (Activity) context;
        LayoutInflater.from(context).inflate(R.layout.down_pop_window_per_view, this);
        llPopAdd = (LinearLayout) findViewById(R.id.ll_pop_add);


        //对表单列表的设置
//        pullListView = (ListView) findViewById(R.id.content_view);
//        fillListAdapter = new FillListAdapter(mContext);
//        pullListView.setAdapter(fillListAdapter);
//        pullListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                int logicId = bean.getData().get(position).get(0).getLogicId();
//                Intent intent = new Intent(mContext, ZiXunJieDaDetailsActivity.class);
//                intent.putExtra("appId", appId);
//                intent.putExtra("logicId", logicId);
//                mContext.startActivity(intent);
//            }
//        });

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
//        //更新适配器数据刷新回调
//        if (mContext instanceof ZiXunJieDaListActivity) {
//            ((ZiXunJieDaListActivity) mContext).setPullRecordListListener(pullRecordListListener);
//        }
    }

    List<DownPopWindowPerItemView> itemViewList = new ArrayList<DownPopWindowPerItemView>();

    private void addView() {
        for (int i = 0; i < itemsBeanList.size(); i++) { //标题头部的列表
            JobHeader.ItemsBean itemsBean = itemsBeanList.get(i);
            DownPopWindowPerItemView itemView = new DownPopWindowPerItemView(mContext);
            itemView.setVal(-1);
            itemView.setItemsBean(itemsBean);
            itemView.setFieldname(itemsBean.getFieldname());
            itemView.setOnClickListener(this);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f));
            if (itemsBean.getSubtitles() != null && itemsBean.getSubtitles().size() > 0) {
                itemView.setText(itemsBean.getSubtitles().get(0).getName());
            }
            if (llPopAdd != null) {
                llPopAdd.addView(itemView);
            }
            itemViewList.add(itemView);
        }
//        getListData();
    }

    /**
     * 获取列表的数据
     */
//    private void getListData() {
//        ProgressUtil.show(mContext, "加载中...");
//        RequestParams params = new RequestParams(Const.WuYe.URL_LIST);//报修记录list接口
//        params.setConnectTimeout(5 * 1000);
//        //具体是哪个应用要请求列表数据
//        if (mContext instanceof ZiXunJieDaListActivity) {
//            appId = ((ZiXunJieDaListActivity) mContext).appId;
//        }
//        params.addParameter("appId", appId);
//        params.addParameter("userId", SPUtils.get(mContext, Const.SPDate.ID, -1));
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                Gson gson = new Gson();
//                bean = gson.fromJson(result, FillListBean.class);
//                fillListAdapter.setFillListBeen(bean);
//                fillListAdapter.notifyDataSetChanged();
//                ProgressUtil.close();
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                ProgressUtil.close();
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
//    }


    DownPopWindowPerView.PullRecordListListener pullRecordListListener = new DownPopWindowPerView.PullRecordListListener() {
        @Override
        public void onPullRecordListLIstener() {
//            getListData();
        }
    };

    /**
     * 刷新记录列表数据的回调接口
     */
    public interface PullRecordListListener {
        void onPullRecordListLIstener();
    }


    List<JobHeader.ItemsBean.SubtitlesBean> subtitles = new ArrayList<JobHeader.ItemsBean.SubtitlesBean>();
    DownPopWindowPerItemView itemView;

    String filedName = "";

    @Override
    public void onClick(final View v) {
        itemView = ((DownPopWindowPerItemView) v);
        JobHeader.ItemsBean itemsBean = itemView.getItemsBeanList();
        subtitles = itemsBean.getSubtitles();
        if (subtitles != null) {
            myListView.setAdapter(new MyAdapter(itemView.getTitle()));
        }
        show();
//        switch (itemsBean.getItemType()) {
//            case 52:
//                subList = itemsBean.getSubtitles();
//                if (subList != null) {
//                    myListView.setAdapter(new MyAdapter(itemView.getTitle()));
//                }
//                show();
//                break;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ProgressUtil.show(mContext, "加载中...");
        TextView tv = (TextView) view.findViewById(R.id.tv_list_popup_window_item);
        String msg = tv.getText().toString();
        itemView.setText(msg);
        for (int i = 0; i < subtitles.size(); i++) {
            if (subtitles.get(i).getName().equals(msg)) {
                itemView.setVal(subtitles.get(i).getVal());
            }
        }
        dismiss();
        //维修派工时使用
        if (mContext instanceof PerRepairDispatchActivity) {
            RequestParams params = new RequestParams(Const.WuYe.URL_JOP_PER);
            for (int i = 0; i < itemsBeanList.size(); i++) {
                int val = itemViewList.get(i).getVal();
                if (i == 0) {
                    if (val == -1) {
                        params.addParameter(itemsBeanList.get(i).getFieldname(), "");
                    } else {
                        params.addParameter(itemsBeanList.get(i).getFieldname(), val);
                    }
                } else if (i == 1) {
                    if (val == -1) {
                        params.addParameter(itemsBeanList.get(i).getFieldname(), "");
                    } else {
                        params.addParameter(itemsBeanList.get(i).getFieldname(), val);
                    }
                }
            }
            params.addParameter("appId", 110);
            params.addParameter("userId", SPUtils.get(mContext, Const.SPDate.ID, -1));
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Gson gson = new Gson();
                    bean = gson.fromJson(result, JobPer.class);
                    if (handler != null) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("bean", bean);
                        Message msg = new Message();
                        msg.what = 2;
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
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

            RequestParams params1 = new RequestParams(Const.WuYe.URL_SIGNIN_WORK_REPAIRCHARTS_LIST);
            for (int i = 0; i < itemsBeanList.size(); i++) {
                int val = itemViewList.get(i).getVal();
                if (i == 0) {
                    if (val == -1) {
                        params1.addParameter(itemsBeanList.get(i).getFieldname(), "");
                    } else {
                        params1.addParameter(itemsBeanList.get(i).getFieldname(), val);
                    }
                } else if (i == 1) {
                    if (val == -1) {
                        params1.addParameter(itemsBeanList.get(i).getFieldname(), "");
                    } else {
                        params1.addParameter(itemsBeanList.get(i).getFieldname(), val);
                    }
                }
            }
            x.http().post(params1, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Gson gson = new Gson();
                    PerRepairDispatchBean bean = gson.fromJson(result, PerRepairDispatchBean.class);
                    if (handler != null) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("bean", bean);
                        Message msg = new Message();
                        msg.what = 3;
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
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
            //用车情况使用
        } else if (mContext instanceof PerUserCarActivity) {
            RequestParams params1 = new RequestParams(Const.WuYe.URL_SIGNIN_WORK_CAR_WORK_PERFORMANCE_LIST);
            for (int i = 0; i < itemsBeanList.size(); i++) {
                int val = itemViewList.get(i).getVal();
                if (i == 0) {
                    if (val == -1) {
                        params1.addParameter(itemsBeanList.get(i).getFieldname(), "");
                    } else {
                        params1.addParameter(itemsBeanList.get(i).getFieldname(), val);
                    }
                } else if (i == 1) {
                    if (val == -1) {
                        params1.addParameter(itemsBeanList.get(i).getFieldname(), "");
                    } else {
                        params1.addParameter(itemsBeanList.get(i).getFieldname(), val);
                    }
                }
            }
            x.http().post(params1, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Gson gson = new Gson();
                    PerCarBean bean = gson.fromJson(result, PerCarBean.class);
                    if (handler != null) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("bean", bean);
                        Message msg = new Message();
                        msg.what = 100;
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
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
        } else if (mContext instanceof ShoopingStoreActivity) { // ShoopingStoreActivity使用
            Map<String, String> map = new HashMap<>();
            RequestParams params1 = new RequestParams(Const.WuYe.URL_SELLER_QUERY_SELLER);
            params1.addParameter("page", 1);
            params1.addParameter("row", 20);
            for (int i = 0; i < itemsBeanList.size(); i++) {
                int val = itemViewList.get(i).getVal();
                if (i == 0) {
                    if (val == -1) {
                        params1.addParameter(itemsBeanList.get(i).getFieldname(), "");
                        map.put(itemsBeanList.get(i).getFieldname(), "");
                    } else {
                        params1.addParameter(itemsBeanList.get(i).getFieldname(), val);
                        map.put(itemsBeanList.get(i).getFieldname(), val + "");
                    }
                } else if (i == 1) {
                    if (val == -1) {
                        params1.addParameter(itemsBeanList.get(i).getFieldname(), "");
                        map.put(itemsBeanList.get(i).getFieldname(), "");
                    } else {
                        params1.addParameter(itemsBeanList.get(i).getFieldname(), val);
                        map.put(itemsBeanList.get(i).getFieldname(), val + "");
                    }
                }
            }
            if (listener != null) {
                listener.sendToPullListener(map);
            }
            x.http().post(params1, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Gson gson = new Gson();
                    ShoppingStoreBean bean = gson.fromJson(result, ShoppingStoreBean.class);
                    if (handler != null) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("bean", bean);
                        Message msg = new Message();
                        msg.what = ShoopingStoreActivity.HANDLE_TAG;
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
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

    }

    private OnSendToPullListener listener;

    public void setListener(OnSendToPullListener listener) {
        this.listener = listener;
    }

    /**
     * 将下拉的头的数据传送到下拉刷新的的界面
     */
    public interface OnSendToPullListener {
        void sendToPullListener(Map<String, String> map);
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
            return subtitles.size();
        }

        @Override
        public Object getItem(int position) {
            return subtitles.get(position);
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
            holder.tv.setText(subtitles.get(position).getName());
            for (int i = 0; i < subtitles.size(); i++) {
                if (subtitles.get(position).getName().equals(TAG)) {
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
