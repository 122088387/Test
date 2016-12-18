package com.chaungying.set;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.chaungying.BaseActivity;
import com.chaungying.wuye3.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * @author 王晓赛 or 2016/8/20
 */
@ContentView(R.layout.activity_about_system)
public class AboutSystemActivity extends BaseActivity{


    @ViewInject(R.id.tv_system_code)
    private TextView tv_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBar("关于系统",R.drawable.nav_return,0);
        PackageManager manager = getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(getPackageName(), 0);
            tv_code.setText("版本号 "+info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
