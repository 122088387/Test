package com.chaungying.gongzuotai.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.BaseActivity;
import com.chaungying.site_repairs.view.RepairsFragment;
import com.chaungying.wuye3.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * @author 王晓赛 or 2016/8/22
 */
@ContentView(R.layout.activity_deal_with_order)
public class DealWithOrderActivity extends BaseActivity {

    private int states = -1, userId = -1;

    public static int logicId = -1;

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
        states = getIntent().getExtras().getInt("states");
        userId = getIntent().getExtras().getInt("grabberId");
        logicId = getIntent().getExtras().getInt("logicId");
        title_back = (ImageView) findViewById(R.id.title_back);
        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        final int applicationId = getIntent().getExtras().getInt("applicationId");
//        RoleAppsBean bean = (RoleAppsBean) getIntent().getExtras().getSerializable("bean");
        RepairsFragment fragment = new RepairsFragment(states, userId);
        fragmentTransaction.add(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    /**
     * 让Fragment的界面获取到此TextView
     *
     * @return
     */
    public TextView getTi_jiao() {
        return title_order;
    }
}
