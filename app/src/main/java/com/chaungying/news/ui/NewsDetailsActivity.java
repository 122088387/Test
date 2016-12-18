package com.chaungying.news.ui;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.news.bean.NewsDetails;
import com.chaungying.news.view.NewsContentView;
import com.chaungying.news.view.NewsImageView;
import com.chaungying.news.view.NewsTitleView;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻信息详情
 *
 * @author 种耀淮 在2016年08月04日14:14创建
 */
@ContentView(R.layout.activity_news_details)
public class NewsDetailsActivity extends BaseActivity {


    // 动态加载布局
    @ViewInject(R.id.newsDetails_content)
    private LinearLayout contentLayout;

    private List<NewsDetails.DatasBean> datas = new ArrayList<>();

    private long newsId;

    // 网络请求控制器
//    private BasePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
//        presenter = new BasePresenter(this);
        newsId = getIntent().getLongExtra("newsId", 0);
        ProgressUtil.show(this, "加载中...");
        createView();
    }

    /**
     * 初始化
     */
    private void createView() {
        // 初始化Title
        setActionBar("新闻通知", R.drawable.nav_return, 0);
        // 初始化Content
        upData();
    }

    private void upData() {
        RequestParams params = new RequestParams(Const.WuYe.URL_NEWS_LIST_DETAILS);
        params.setConnectTimeout(5 * 1000);
        params.addParameter("id", newsId);
        params.addParameter("memberId", SPUtils.get(this, Const.SPDate.ID, -1));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                contentLayout.removeAllViews();// 加载成功后先删除布局中所有View
                // 解析数据
                Gson gson = new Gson();
                NewsDetails newsDetails = gson.fromJson(result, NewsDetails.class);
                datas = newsDetails.getDatas();
                // 加载视图
                for (int i = 0; i < datas.size(); i++) {
                    switch (datas.get(i).getType()) {
                        case 1:// 标题
                            NewsTitleView titleView = new NewsTitleView(NewsDetailsActivity.this, datas.get(i).getContent());
                            contentLayout.addView(titleView);
                            break;
                        case 2:// 图片
                            NewsImageView imageView = new NewsImageView(NewsDetailsActivity.this, datas.get(i).getContent());
                            contentLayout.addView(imageView);
                            break;
                        case 3:// 正文
                            NewsContentView contentView = new NewsContentView(NewsDetailsActivity.this, datas.get(i).getContent());
                            contentLayout.addView(contentView);
                            break;
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
                ProgressUtil.close();
            }
        });
    }

    /**
     * 分割
     * *********************************************************************************************
     * 网络请求回调
     */
//    @Override
//    public void onSuccess(String result) {
//        contentLayout.removeAllViews();// 加载成功后先删除布局中所有View
//        // 解析数据
//        Type type = new TypeToken<ArrayList<NewsDetails>>() {
//        }.getType();
//        datas = App.gson.fromJson(result, type);
//        // 加载视图
//        for (int i = 0; i < datas.size(); i++) {
//            switch (datas.get(i).getType()) {
//                case 1:// 标题
//                    NewsTitleView titleView = new NewsTitleView(this, datas.get(i).getContent());
//                    contentLayout.addView(titleView);
//                    break;
//                case 2:// 图片
//                    NewsImageView imageView = new NewsImageView(this, datas.get(i).getContent());
//                    contentLayout.addView(imageView);
//                    break;
//                case 3:// 正文
//                    NewsContentView contentView = new NewsContentView(this, datas.get(i).getContent());
//                    contentLayout.addView(contentView);
//                    break;
//            }
//        }
//    }
//
//    @Override
//    public void onError(String ex) {
//        toastor.showSingletonToast(ex);
//    }
//
//    @Override
//    public void onFinished() {
//
//    }
}
