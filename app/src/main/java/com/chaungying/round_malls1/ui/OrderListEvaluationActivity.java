package com.chaungying.round_malls1.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Base;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.T;
import com.chaungying.round_malls1.bean.OrderListBean;
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
 * 时  间： 2016/10/20
 * <p>
 * 评价界面
 */
@ContentView(R.layout.activity_order_list_evaluation)
public class OrderListEvaluationActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.order_list_evaluation_time)
    private TextView order_list_evaluation_time;

    @ViewInject(R.id.order_list_evaluation_content)
    private EditText order_list_evaluation_content;

    @ViewInject(R.id.order_list_evaluation_tijiao)
    private TextView order_list_evaluation_tijiao;

    @ViewInject(R.id.order_list_evaluation_level)
    private TextView order_list_evaluation_level;

    @ViewInject(R.id.iv_start1)
    private ImageView iv_start1;
    @ViewInject(R.id.iv_start2)
    private ImageView iv_start2;
    @ViewInject(R.id.iv_start3)
    private ImageView iv_start3;
    @ViewInject(R.id.iv_start4)
    private ImageView iv_start4;
    @ViewInject(R.id.iv_start5)
    private ImageView iv_start5;

    private ImageView[] imageViews;

    private OrderListBean.DatasBean bean;

    private int eveLevel;

    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        bean = (OrderListBean.DatasBean) getIntent().getExtras().getSerializable("bean");
        imageViews = new ImageView[]{iv_start1, iv_start2, iv_start3, iv_start4, iv_start5};

        if (bean.getEvaluteStatus() == 0) {//未评价
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[i].setOnClickListener(this);
            }
            order_list_evaluation_content.setEnabled(true);
            order_list_evaluation_tijiao.setVisibility(View.VISIBLE);
        } else if (bean.getEvaluteStatus() == 1) {//已评价

            for (int i = 0; i < imageViews.length; i++) {
                if (bean.getEvaluate().getProLevel()-1 == i) {
                    initStart(i);
                    break;
                }
            }

            order_list_evaluation_content.setText("评价内容：" + bean.getEvaluate().getEvaluate_note());
            order_list_evaluation_content.setEnabled(false);
            order_list_evaluation_tijiao.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[i].setImageResource(R.drawable.star_no);
        }
        for (int i = 0; i < imageViews.length; i++) {
            if (view == imageViews[i]) {
                eveLevel = i;
                initStart(i);
                break;
            }
        }
    }

    private void initStart(int i) {
        for (int j = 0; j <= i; j++) {
            imageViews[j].setImageResource(R.drawable.star_full);
        }
        switch (i) {
            case 0:
                order_list_evaluation_level.setText("很差");
                break;
            case 1:
                order_list_evaluation_level.setText("差");
                break;
            case 2:
                order_list_evaluation_level.setText("一般");
                break;
            case 3:
                order_list_evaluation_level.setText("好");
                break;
            case 4:
                order_list_evaluation_level.setText("很好");
                break;
        }
    }

    /**
     * 提交评价
     *
     * @param view
     */
    @Event(value = R.id.order_list_evaluation_tijiao)
    private void order_list_evaluation_tijiao(View view) {

        RequestParams params = new RequestParams(Const.WuYe.URL_ORDER_ADD_EVALUATE);
//        orderNo：订单号；proLevel：评论等级；evaluate_note：评论内容；
        params.addParameter("orderNo", bean.getOrderNo());
        params.addParameter("proLevel", eveLevel + 1);
        params.addParameter("evaluate_note", order_list_evaluation_content.getText().toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("评价", result);
                Base base = new Gson().fromJson(result, Base.class);
                if (base.respCode == 1001) {
                    finish();
                } else {
                    T.showShort(OrderListEvaluationActivity.this, "服务器异常");
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
