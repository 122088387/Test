package com.chaungying.zixunjieda.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.common.view.BuildView;
import com.chaungying.common.view.DownPopWindowView;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.modues.main.bean.RoleAppsBean;
import com.chaungying.wuye3.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * @author 王晓赛 or 2016/7/14
 *         //咨询解答、投诉管理、订餐管理、现场报修、能源管理
 *         <p/>
 *         点击九宫格中的
 */
@ContentView(R.layout.activity_patrols1)
public class ZiXunJieDaListActivity extends BaseActivity {

//    @ViewInject(R.id.title_back)
//    private ImageView title_back;
//
//    @ViewInject(R.id.title_menu)
//    private TextView title_menu;

    //添加下拉的线性布局
    @ViewInject(R.id.ll_add_view)
    private LinearLayout llAddView;

    //工作单列表中的实体
    private RoleAppsBean bean;

    public static String detailsName;

    public static int appId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        bean = (RoleAppsBean) getIntent().getExtras().getSerializable("bean");
        if (bean.getApplicationId() == 111 || bean.getApplicationId() == 113) {//咨询解答  投诉管理
            setActionBar(bean.getName(), R.drawable.nav_return, 0);
        } else {
            setActionBar(bean.getName(), R.drawable.nav_return, R.drawable.add_image);
        }
        //为了让列表详情界面得到这个String
        detailsName = bean.getName();
        appId = bean.getApplicationId();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ProgressUtil.show(this, "加载中...");
        RequestParams params = new RequestParams(Const.WuYe.URL_ON_SITE_REPAIR);
        params.setConnectTimeout(30 * 1000);
        params.addParameter("applicationId", bean.getApplicationId());
        params.addParameter("type", 2);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                llAddView.removeAllViews();
                BuildView buildView = new BuildView(llAddView, ZiXunJieDaListActivity.this);
                buildView.buildView(result, true);
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

    DownPopWindowView.PullRecordListListener pullRecordListListener;

    public void setPullRecordListListener(DownPopWindowView.PullRecordListListener pullRecordListListener) {
        this.pullRecordListListener = pullRecordListListener;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //通过回调接口  通知DownPopWindowView中获取列表数据的适配器更新数据
        if (pullRecordListListener != null) {
            pullRecordListListener.onPullRecordListLIstener();
        }
    }

    /**
     * 点击右上角加号
     */
    @Event(value = R.id.title_menu)
    private void clickAdd(View view) {

        Bundle bundle = new Bundle();
        //根据id进行配置表单
        bundle.putSerializable("bean", bean);
        openActivty(this, ZiXunJieDaConfigActivity.class, bundle, null);


    }

    /**
     * 返回到九宫格主界面
     */
    @Event(value = R.id.title_back)
    private void back(View view) {
        finish();
    }

}
