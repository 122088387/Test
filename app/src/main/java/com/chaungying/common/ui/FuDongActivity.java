package com.chaungying.common.ui;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chaungying.wuye3.R;

/**
 * @author 王晓赛 or 2016/9/2
 */
public class FuDongActivity extends Activity {

    private FrameLayout root;

    private ScrollView scrollView;

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 19) {
            //沉浸式状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_fudong);

        root = (FrameLayout) findViewById(R.id.fudong_root);
        scrollView = (ScrollView) findViewById(R.id.fudong_scroll);
        tv = (TextView) findViewById(R.id.fudong_tv);

        String title = getIntent().getStringExtra("data");
        tv.setText(title);

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                FuDongActivity.this.overridePendingTransition(R.anim.intent_alpha_in, R.anim.intent_alpha_out);
            }
        });
        scrollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                FuDongActivity.this.overridePendingTransition(R.anim.intent_alpha_in, R.anim.intent_alpha_out);
            }
        });
    }
}
