package com.chaungying.modues.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Base;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.common.utils.T;
import com.chaungying.modues.login.ui.LoginActivity;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * @author 王晓赛 or 2016/8/25
 */
@ContentView(R.layout.update_password)
public class UpdatePasswordActivity extends BaseActivity {

    @ViewInject(R.id.login_et_username)
    EditText etold;

    @ViewInject(R.id.login_et_password)
    EditText newpass1;

    @ViewInject(R.id.login_et_password2)
    EditText newpass2;

    @ViewInject(R.id.tv_ok_update)
    TextView tv_ok;

    String oldDbPasswork = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBar("修改密码", R.drawable.nav_return, 0);
        oldDbPasswork = (String) SPUtils.get(this, Const.SPDate.PASS_WORD, "-1");
    }

    @Event(value = R.id.tv_ok_update)
    private void tv_ok_btn(View view) {
        if (!oldDbPasswork.equals(etold.getText().toString())) {
            T.showLong(UpdatePasswordActivity.this, "旧密码输入不正确");
            return;
        }
        if (newpass1.getText().length() < 6) {
            T.showLong(UpdatePasswordActivity.this, "新密码不能小于6位");
            return;
        }
        if (!newpass1.getText().toString().equals(newpass2.getText().toString())) {
            T.showLong(UpdatePasswordActivity.this, "两次新密码输入不一致");
            return;
        }
        if (newpass1.getText().toString().equals(oldDbPasswork)) {
            T.showLong(UpdatePasswordActivity.this, "新密码不能和旧密码一致");
            return;
        }


//       ?loginNam=登陆账号&password=新密码
        RequestParams params = new RequestParams(Const.WuYe.URL_UPDATE_PASSWORD);
        params.addParameter("loginName", SPUtils.get(this, Const.SPDate.LONGING_NAME, "无名氏"));
        params.addParameter("password", newpass2.getText().toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Base base = gson.fromJson(result, Base.class);
                if (base.respCode == 1001) {
                    T.showLong(UpdatePasswordActivity.this, "修改成功");
                    Intent intent = new Intent(UpdatePasswordActivity.this, LoginActivity.class);
                    //用户是否点击退出登录按钮
                    intent.putExtra("click_exit", true);
                    startActivity(intent);
                    finish();
                } else {
                    T.showLong(UpdatePasswordActivity.this, "修改密码失败");
                }
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
}
