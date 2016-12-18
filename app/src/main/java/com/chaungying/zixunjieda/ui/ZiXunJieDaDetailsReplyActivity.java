package com.chaungying.zixunjieda.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Base;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.common.utils.T;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * @author 王晓赛 or 2016/9/12
 */
@ContentView(R.layout.activity_zi_xun_jie_da_details_reply)
public class ZiXunJieDaDetailsReplyActivity extends BaseActivity {

    @ViewInject(R.id.details_reply_text)
    EditText details_reply_text;

    @ViewInject(R.id.title_menu)
    TextView tv_tijiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBarText(R.string.hui_fu, R.drawable.nav_return, R.string.tijiao);
        tv_tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadContant();
            }
        });
    }

    private void uploadContant() {
        ProgressUtil.show(this, "");
        RequestParams params = new RequestParams(Const.WuYe.URL_ZI_XUN_JIE_DA_DETAILS_REPLY);
        params.addParameter("appId", getIntent().getExtras().getInt("appId"));
        params.addParameter("logicId", getIntent().getExtras().getInt("logicId"));
        params.addParameter("replyContent", details_reply_text.getText().toString());
        params.addParameter("replyUserId", SPUtils.get(this, Const.SPDate.ID, 0));
        params.addParameter("replyTime", "");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Base base = gson.fromJson(result, Base.class);
                if (base.respCode == 1001) {
                    T.showShort(ZiXunJieDaDetailsReplyActivity.this, "回复成功！");
                    finish();
                } else {
                    T.showShort(ZiXunJieDaDetailsReplyActivity.this, "回复失败！");
                }
                ProgressUtil.close();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ProgressUtil.close();
                T.showShort(ZiXunJieDaDetailsReplyActivity.this, "回复失败！");
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
