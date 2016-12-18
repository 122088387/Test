package com.chaungying.common.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.chaungying.BaseActivity;
import com.chaungying.common.bean.TDistrict;
import com.chaungying.common.utils.T;
import com.chaungying.wuye3.R;
import com.chaungying.zixunjieda.ui.ZiXunJieDaConfigActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * @author 王晓赛 or 2016/6/25
 *         进入级联界面
 */

@ContentView(R.layout.activity_jilian)
public class JiLianActivity extends BaseActivity {

    @ViewInject(R.id.radio_group_ji_lian)
    private RadioGroup radioGroup;
    @ViewInject(R.id.btn_ok)
    private Button btn_ok;

    //    private ArrayList<String> strList = new ArrayList<String>();
    ArrayList<TDistrict> moreBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        //设置title Bar
        setActionBar(R.string.title, 0, 0);
        String url = getIntent().getExtras().getString("louyu");
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(30 * 1000);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //对多选中获取的json进行工作
                T.showShort(JiLianActivity.this, "能够返回多项选择中的数据");
                Type type = new TypeToken<ArrayList<TDistrict>>() {
                }.getType();
                Gson gson = new Gson();
                moreBeanList = gson.fromJson(result, type);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                //联网获取小区，楼宇等完成之后  绘制界面
                for (int i = 0; i < moreBeanList.size(); i++) {
                    AppCompatRadioButton radioBtn = new AppCompatRadioButton(JiLianActivity.this);
                    radioBtn.setText(moreBeanList.get(i).getName());
                    radioBtn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    radioBtn.setPadding(10, 10, 5, 10);
                    radioGroup.addView(radioBtn);
                    //默认高度是1
//                    GrayLineView grayLineView = new GrayLineView(JiLianActivity.this);
//                    grayLineView.setLineHeight(50);
//                    radioGroup.addView(grayLineView);
                    //对单选组进行监听
                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            for (int i = 0; i < group.getChildCount(); i++) {
                                View view = group.getChildAt(i);
                                if (view instanceof AppCompatRadioButton) {
                                    AppCompatRadioButton button = (AppCompatRadioButton) view;
                                    if (view.getId() == checkedId) {
                                        singSelect = button.getText().toString();
                                        id = moreBeanList.get(i).getVal();
                                    }
                                }
                            }

                        }
                    });
                }
            }
        });
    }

    private String singSelect = "";
    private static final int CASE_RESULT = 5;
    private int id = 0;

    /**
     * 选择确定按钮，将数据传回到现场报修界面
     *
     * @param view
     */
    @Event(value = R.id.btn_ok)
    private void btnOk(View view) {
        Intent intent = new Intent();
        intent.setClass(JiLianActivity.this, ZiXunJieDaConfigActivity.class);
        Bundle bunlde = new Bundle();
        bunlde.putSerializable("select", singSelect);
        bunlde.putInt("id", id);
        intent.putExtras(bunlde);
        setResult(CASE_RESULT, intent);
        finish();
    }

}
