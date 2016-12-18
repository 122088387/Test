package com.chaungying.news.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.news.adapter.NoticeListAdapter;
import com.chaungying.news.bean.NoticeItem;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.litesuits.common.assist.Toastor;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 公告列表碎片
 *
 * @author 种耀淮 在2016年08月04日11:14创建
 */
@ContentView(R.layout.fragment_notice)
public class NoticeListFragment extends Fragment {

    private Toastor toastor;

    // 列表
    @ViewInject(R.id.fragmentNotice_list)
    private ListView listView;

    // 列表适配器
    private NoticeListAdapter adapter;

    // 数据
    private ArrayList<NoticeItem> datas = new ArrayList<>();

    // 网络控制器
//    private RefreshLoadPresenter presenter;


    //未读消息的数量
    private int num = 0;

    private NewsListFragment.ReturnUnreadNumListener unreadNumListener;

    public void setUnreadNumListener(NewsListFragment.ReturnUnreadNumListener unreadNumListener) {
        this.unreadNumListener = unreadNumListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = x.view().inject(this, inflater, container);
//        presenter = new RefreshLoadPresenter(this);
        toastor = new Toastor(getContext());
        createFragment();
        return view;
    }

    private void createFragment() {
        // 初始化列表
        adapter = new NoticeListAdapter(getContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (unreadNumListener != null) {
                    if (datas.get(i).getState() == 1) {
                        if (num > 0) {
                            num--;
                            unreadNumListener.returnNum(num);
                        }
                        datas.get(i).setState(0);
                        adapter.notifyDataSetChanged();
                    }

                }

                Intent intent = new Intent(getContext(), NoticeDetailsActivity.class);
                intent.putExtra("noticeId", datas.get(i).getId());
                startActivity(intent);
            }
        });

        refresh();
    }

    private void refresh() {
//        listView.dismiss();
        datas.clear();
        RequestParams params = new RequestParams(Const.WuYe.URL_NOTICE_LIST);
        params.addParameter("activityType", 1);
        params.addParameter("memberId", SPUtils.get(getContext(), Const.SPDate.ID, -1));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Type type = new TypeToken<ArrayList<NoticeItem>>() {
                }.getType();
                Gson gson = new Gson();
                datas = gson.fromJson(result, type);
                adapter.setDatas(datas);
                adapter.notifyDataSetChanged();

                for (int i = 0; i < datas.size(); i++) {
                    if (datas.get(i).getState() == 1) {
                        num++;
                    }
                }
                if (unreadNumListener != null && num > 0) {
                    unreadNumListener.returnNum(num);
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
                ProgressUtil.close();
            }
        });
//        presenter.refresh(params);// 执行刷新操作
//        listView.showLoadingView();// 显示加载中
    }

    /**
     * 分割
     * *********************************************************************************************
     * 网络请求回调
     */
//    @Override
//    public void onRefreshSuccess(String result) {
//        Type type = new TypeToken<ArrayList<NoticeItem>>() {
//        }.getType();
//        datas = App.gson.fromJson(result, type);
//        adapter.setDatas(datas);
//    }
//
//    @Override
//    public void onRefreshFinished() {
//
//    }
//
//    @Override
//    public void onRefreshNullData() {
//        listView.showEmptyView();
//    }
//
//    @Override
//    public void onLoadSuccess(String result) {
//        Type type = new TypeToken<ArrayList<NoticeItem>>() {
//        }.getType();
//        ArrayList<NoticeItem> temp = App.gson.fromJson(result, type);
//        datas.addAll(temp);
//        adapter.setDatas(datas);
//    }
//
//    @Override
//    public void onLoadFinished() {
//
//    }
//
//    @Override
//    public void onLoadNullData() {
//        toastor.showSingletonToast("到底了");
//    }
//
//    @Override
//    public void onError(String ex, boolean isRefresh) {
//        if (isRefresh) {
//            TextView errorView = (TextView) listView.getErrorView();
//            errorView.setText(ex);
//            listView.showErrorView();
//        } else {
//            toastor.showSingletonToast(ex);
//        }
//    }
}
