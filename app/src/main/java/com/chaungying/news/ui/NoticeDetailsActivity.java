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
 * @author 种耀淮 在2016年08月04日17:04创建
 */
@ContentView(R.layout.activity_notice_details)
public class NoticeDetailsActivity extends BaseActivity {

    // 正文布局
    @ViewInject(R.id.noticeDetails_content)
    private LinearLayout contentLayout;

    private long noticeId;

    private List<NewsDetails.DatasBean> datas = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        ProgressUtil.show(this, "加载中...");
        noticeId = getIntent().getLongExtra("noticeId", 0);
        createView();
    }

    /**
     * 初始化
     */
    private void createView() {
        // 初始化Title
        setActionBar("公告详情", R.drawable.nav_return, 0);
        // 初始化Content
        refresh();
    }

    private void refresh() {
        RequestParams params = new RequestParams(Const.WuYe.URL_NEWS_LIST_DETAILS);
        params.setConnectTimeout(5 * 1000);
        params.addParameter("id", noticeId);
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
                            NewsTitleView titleView = new NewsTitleView(NoticeDetailsActivity.this, datas.get(i).getContent());
                            contentLayout.addView(titleView);
                            break;
                        case 2:// 图片
                            NewsImageView imageView = new NewsImageView(NoticeDetailsActivity.this, datas.get(i).getContent());
                            contentLayout.addView(imageView);
                            break;
                        case 3:// 正文
                            NewsContentView contentView = new NewsContentView(NoticeDetailsActivity.this, datas.get(i).getContent());
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
