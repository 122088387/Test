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
import com.chaungying.news.adapter.NewsListAdapter;
import com.chaungying.news.bean.NewsItem;
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
 * 新闻列表碎片
 *
 * @author 种耀淮 在2016年07月26日19:31创建
 */
@ContentView(R.layout.fragment_news)
public class NewsListFragment extends Fragment {

    private Toastor toastor;

    // 列表
    @ViewInject(R.id.fragmentNews_list)
    private ListView listView;

    // 列表适配器
    private NewsListAdapter adapter;

    // 数据
    private ArrayList<NewsItem> datas = new ArrayList<>();

    private ReturnUnreadNumListener unreadNumListener;

    public void setUnreadNumListener(ReturnUnreadNumListener unreadNumListener) {
        this.unreadNumListener = unreadNumListener;
    }


    //未读消息的数量
    private int num = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = x.view().inject(this, inflater, container);
        toastor = new Toastor(getContext());

        createFragment();
        return view;
    }


    /**
     * 初始化
     */
    private void createFragment() {
        // 初始化列表
        adapter = new NewsListAdapter(getContext());
        listView.setAdapter(adapter);
        adapter.setDatas(datas);
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

                Intent intent = new Intent(getContext(), NewsDetailsActivity.class);
                intent.putExtra("newsId", datas.get(i).getId());
                startActivity(intent);
            }
        });
        refresh();
    }

    private void refresh() {
        datas.clear();
        RequestParams params = new RequestParams(Const.WuYe.URL_NEWS_LIST);
        params.addParameter("activityType", 0);
        params.addParameter("memberId", SPUtils.get(getContext(), Const.SPDate.ID, -1));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Type type = new TypeToken<ArrayList<NewsItem>>() {
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
    }

    public interface ReturnUnreadNumListener {
        void returnNum(int unReadNum);
    }
}
