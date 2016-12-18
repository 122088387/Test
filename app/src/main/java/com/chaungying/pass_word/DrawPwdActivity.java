package com.chaungying.pass_word;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.chaungying.common.utils.SPUtils;
import com.chaungying.modues.login.ui.LoginActivity;
import com.chaungying.wuye3.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * @author 王晓赛 or 2016/8/9
 */
@ContentView(R.layout.draw_pwd)
public class DrawPwdActivity extends Activity {

    @ViewInject(R.id.mPassWordView)
    private LocusPassWordView mPwdView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mPwdView.setOnCompleteListener(new LocusPassWordView.OnCompleteListener() {
            @Override
            public void onComplete(String mPassword) {
//                SharedPreferencesHelper sph = SharedPreferencesHelper.getInstance(getApplicationContext());
//                String pwd = sph.getString("password", "");
//                Md5Utils md5 = new Md5Utils();
                boolean passed = false;
//                if (pwd.length() == 0) {
//                    sph.putString("password", md5.toMd5(mPassword, ""));
//                    passed = true;
//                } else {
//                    String encodedPwd = md5.toMd5(mPassword, "");
//                    if (encodedPwd.equals(pwd)) {
//                        passed = true;
//                    } else {
//                        mPwdView.markError();
//                    }
//                }
                String pwd = (String) SPUtils.get(DrawPwdActivity.this,"draw_password","");
                if (pwd.length() == 0) {
                    SPUtils.put(DrawPwdActivity.this,"draw_password",mPassword);
                    passed = true;
                } else {
                    if (mPassword.equals(pwd)) {
                        passed = true;
                    } else {
                        mPwdView.markError();
                    }
                }
                if (passed) {
                    Intent intent = new Intent(DrawPwdActivity.this,
                            LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

}