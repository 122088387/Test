package com.chaungying.round_malls1.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Base;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.T;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 创建者：  王晓赛
 * 时  间： 2016/10/21
 * <p>
 * 消费 界面（验证密码）
 */

@ContentView(R.layout.activity_my_shop_consump)
public class MyShopConsumpActivity extends BaseActivity {

    @ViewInject(R.id.et_verification_code)
    private EditText et_verification_code;

    private int sellerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBar("消费", R.drawable.nav_return, 0);

        sellerId = getIntent().getExtras().getInt("sellerId");

    }

    @Event(value = R.id.tv_validation)
    private void yan_zheng(View view) {
        RequestParams params = new RequestParams(Const.WuYe.URL_ORDER_MEMEBER_OK);
        params.addParameter("sellerId", sellerId);

        String orderCode = et_verification_code.getText().toString();
        if (orderCode == null || orderCode.length() == 0) {
            T.showShort(this, "验证码不能为空");
            return;
        }
        ProgressUtil.show(this, "验证中...");
        params.addParameter("orderCode", orderCode);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Base base = new Gson().fromJson(result, Base.class);
                if (base.respCode == 1001) {
                    T.showShort(MyShopConsumpActivity.this, "验证成功");
                    finish();
                } else {
                    T.showLong(MyShopConsumpActivity.this, "验证失败,验证码输入错误或已消费");
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
                ProgressUtil.close();
            }
        });

    }
}
