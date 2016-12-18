package com.chaungying.zixunjieda.ui;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.LinearLayout;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.wuye3.R;
import com.chaungying.zixunjieda.bean.ShowDetailsBean;
import com.chaungying.zixunjieda.bean.ShowDetailsMaterialBean;
import com.chaungying.zixunjieda.view.ZiXunBuildView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * @author 王晓赛 or 2016/8/5
 */
@ContentView(R.layout.activity_zi_xun_jie_da_details)
public class ZiXunJieDaDetailsActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.ll_add_view)
    private LinearLayout ll_add_view;

    @ViewInject(R.id.zi_xun_details_btn)
    private AppCompatButton zi_xun_details_btn;


    ZiXunBuildView build = null;

    private int appId;
    private int logicId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        zi_xun_details_btn.setOnClickListener(this);

        appId = getIntent().getIntExtra("appId", 0);
        logicId = getIntent().getIntExtra("logicId", 0);

        String name = ZiXunJieDaListActivity.detailsName;
        if (name != null) {
            setActionBar("详请", R.drawable.nav_return, 0);
        }
        if (appId != 111 && appId != 113) {//咨询解答，投诉管理
            initData();
        }
    }

    private void initData() {
        ll_add_view.removeAllViews();
        zi_xun_details_btn.setVisibility(View.GONE);
        ProgressUtil.show(this, "加载中...");
        RequestParams params = new RequestParams(Const.WuYe.URL_ZI_XUN_DETAILS);
        params.setConnectTimeout(50 * 1000);
//                ?appId=103（菜单id）&logicId=1（数据id ）
        params.addParameter("appId", appId);
        params.addParameter("logicId", logicId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                build = new ZiXunBuildView(ZiXunJieDaDetailsActivity.this, ll_add_view);
                Gson gson = new Gson();
                ShowDetailsBean bean = gson.fromJson(result, ShowDetailsBean.class);
//                int userId = bean.getUserId();
                if (appId == 111 || appId == 113) {//  咨询解答  投诉管理
                    int state = bean.getStates();
                    if (state == 1) {
                        zi_xun_details_btn.setText("回复");
                        zi_xun_details_btn.setVisibility(View.VISIBLE);
                    } else {
                        zi_xun_details_btn.setVisibility(View.GONE);
                    }
                }
                final List<ShowDetailsBean.DatasBean> beanList = bean.getDatas();
                //循环判断，获取需要得到明细的url
                for (int i = 0; i < beanList.size(); i++) {
                    if (beanList.get(i).getItemtype() == 59) {//详情展示明细的控件DetailsValueView
                        final String detailsUrl = (String) beanList.get(i).getLinkUrl();
                        final int finalI = i;
                        getDetailsData(appId, detailsUrl, beanList.get(finalI).getId(), result);
                        break;
                    } else if (beanList.get(i).getItemtype() == 58) {//展示文本控件TitleValueView
                        build.buildView(result, false);
                        ProgressUtil.close();
                        break;
                    }
                }
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

    @Override
    protected void onResume() {
        super.onResume();
        if (appId == 111 || appId == 113) {
            initData();
        }
    }

    /**
     * 获取点击明细的详细信息
     */
    private void getDetailsData(int appId, String url, int logicId, final String upResult) {
//        RequestParams params = new RequestParams("http://192.168.1.122/propertyInterface/applicationList/getApplicationDetailByIdForList.action?entityName=ApplicationDetailList&appId=104&logicId=353");
        RequestParams params = new RequestParams(url + "&appId=" + appId + "&logicId=" + logicId);
        params.setConnectTimeout(30 * 1000);
        params.addParameter("appId", appId);
        params.addParameter("logicId", logicId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ShowDetailsMaterialBean bean = gson.fromJson(result, ShowDetailsMaterialBean.class);
                build.buildView(upResult, false);
                build.setDatas(bean.getDatas());
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

    @Override
    public void onClick(View v) {//回复按钮
        switch (v.getId()) {
            case R.id.zi_xun_details_btn:
                Bundle bundle = new Bundle();
                bundle.putInt("appId", appId);
                bundle.putInt("logicId", logicId);
                openActivty(this, ZiXunJieDaDetailsReplyActivity.class, bundle, null);
//                ProgressUtil.show(this,"");
////                ?appId=菜单id&logicId=数据id
//                RequestParams params = new RequestParams(Const.WuYe.URL_FINISH_OREDER);
//                params.addParameter("appId",appId);
//                params.addParameter("logicId",logicId);
//                x.http().post(params, new Callback.CommonCallback<String>() {
//                    @Override
//                    public void onSuccess(String result) {
//                        Gson gson = new Gson();
//                        Base base = gson.fromJson(result,Base.class);
//                        if(base.respCode == 1001){
//                            T.showShort(ZiXunJieDaDetailsActivity.this,"确认成功！");
//                            finish();
//                        }else{
//                            T.showShort(ZiXunJieDaDetailsActivity.this,"确认失败！");
//                        }
//                        ProgressUtil.close();
//                    }
//
//                    @Override
//                    public void onError(Throwable ex, boolean isOnCallback) {
//                        ProgressUtil.close();
//                        T.showShort(ZiXunJieDaDetailsActivity.this,"确认失败！");
//                    }
//
//                    @Override
//                    public void onCancelled(CancelledException cex) {
//
//                    }
//
//                    @Override
//                    public void onFinished() {
//
//                    }
//                });

                break;
        }
    }
}
