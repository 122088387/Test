package com.chaungying.patrols.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.site_repairs.view.RepairsFragment;
import com.chaungying.wuye3.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * @author 王晓赛 or 2016/7/14
 * 巡查任务
 */
@ContentView(R.layout.activity_patrols)
public class PatrolsActivity extends FragmentActivity{

    @ViewInject(R.id.title_back)
    private ImageView title_back;

    @ViewInject(R.id.ti_jiao)
    private TextView title_order;

    @ViewInject(R.id.frame_layout)
    private FrameLayout frame_layout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final int applicationId = getIntent().getExtras().getInt("applicationId");
        RepairsFragment fragment = new RepairsFragment(applicationId);
        fragmentTransaction.add(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    /**
     * 让Fragment的界面获取到此TextView
     * @return
     */
    public TextView getTi_jiao(){
        return title_order;
    }


    /**
     * 返回到九宫格主界面
     */
    @Event(value = R.id.title_back)
    private void back(View view){
        finish();
    }

}
