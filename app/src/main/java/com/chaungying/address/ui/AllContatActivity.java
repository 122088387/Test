package com.chaungying.address.ui;

import android.os.Bundle;
import android.widget.ListView;

import com.chaungying.BaseActivity;
import com.chaungying.wuye3.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * @author 王晓赛 or 2016/8/31
 */
@ContentView(R.layout.activity_all_contact)
public class AllContatActivity extends BaseActivity {


    @ViewInject(R.id.lv_activity_all_contact)
    private ListView lv_activity_all_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
