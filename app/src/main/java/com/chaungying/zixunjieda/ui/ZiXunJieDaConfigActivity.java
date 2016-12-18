package com.chaungying.zixunjieda.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.modues.main.bean.RoleAppsBean;
import com.chaungying.site_repairs.view.RepairsFragment;
import com.chaungying.wuye3.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * @author 王晓赛 or 2016/7/14
 *         //咨询解答、投诉管理、订餐管理、现场报修、能源管理
 */
@ContentView(R.layout.activity_patrols)
public class ZiXunJieDaConfigActivity extends FragmentActivity {

    @ViewInject(R.id.title_back)
    private ImageView title_back;

    @ViewInject(R.id.ti_jiao)
    private TextView title_order;

    @ViewInject(R.id.frame_layout)
    private FrameLayout frame_layout;

    @ViewInject(R.id.title)
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        if (Build.VERSION.SDK_INT >= 19) {
            //沉浸式状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RoleAppsBean bean = (RoleAppsBean) getIntent().getExtras().getSerializable("bean");
        RepairsFragment fragment = new RepairsFragment(bean.getApplicationId());
        fragmentTransaction.add(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
        title.setText(bean.getName());
    }

    /**
     * 让Fragment的界面获取到此TextView
     *
     * @return
     */
    public TextView getTi_jiao() {
        return title_order;
    }


    /**
     * 返回到九宫格主界面
     */
    @Event(value = R.id.title_back)
    private void back(View view) {
        finish();
    }

}
