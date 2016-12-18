package com.chaungying.round_malls1.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Base;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.date.DateStyle;
import com.chaungying.common.utils.date.DateUtil;
import com.chaungying.common.view.CustomDialog;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.modues.main.view.FillListView;
import com.chaungying.round_malls1.adapter.OrderListDetailsAdapter;
import com.chaungying.round_malls1.bean.OrderListBean;
import com.chaungying.round_malls1.bean.OrderListDetailsBean;
import com.chaungying.round_malls1.utils.TelePhoneUtils;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 创建者：  王晓赛
 * 时  间： 2016/10/14
 * <p>
 * 我的订单详情
 */

@ContentView(R.layout.activity_order_list_details)
public class OrderListDetailsActivity extends BaseActivity {


    @ViewInject(R.id.lv_order_list_dateils)
    private FillListView lv_order_list_dateils;

    //店家名称
    @ViewInject(R.id.order_list_details_name)
    private TextView order_list_details_name;

    @ViewInject(R.id.time_1)
    private TextView time_1;

    //配送方
    @ViewInject(R.id.order_list_details_distribution)
    private TextView order_list_details_distribution;

    //订单号
    @ViewInject(R.id.order_list_details_number)
    private TextView order_list_details_number;

    //配送时间
    @ViewInject(R.id.order_list_details_time)
    private TextView order_list_details_time;

    //收货信息
    @ViewInject(R.id.order_list_details_msg)
    private TextView order_list_details_msg;


    //状态
    @ViewInject(R.id.item_order_list_detail_state)
    private TextView item_order_list_detail_state;

    //左边按钮
    @ViewInject(R.id.item_order_list_details_left)
    private TextView item_order_list_details_left;
    //右边按钮
    @ViewInject(R.id.item_order_list_details_right)
    private TextView item_order_list_details_right;

    //包含两个按钮的线性布局
    @ViewInject(R.id.ll_details_double_btn)
    private LinearLayout ll_details_double_btn;


    private OrderListBean.DatasBean bean;

    private List<OrderListDetailsBean.DatasBean> datasBeanList = new ArrayList<>();

    private OrderListDetailsAdapter detailsAdapter;

    //区别商家和买家
    private String tagShopSell;

    //购物券
    @ViewInject(R.id.ll_order_list_details_vouchers)
    private LinearLayout ll_order_list_details_vouchers;

    //退款原因
    @ViewInject(R.id.ll_order_list_details_refund)
    private LinearLayout ll_order_list_details_refund;

    //退款原因说明
    @ViewInject(R.id.tv_refund)
    private TextView tv_refund;

    //订单备注
    @ViewInject(R.id.order_list_details_note)
    private TextView order_list_details_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initView();
        initData();
    }

    private void initData() {
        datasBeanList.clear();
        ProgressUtil.show(this, "加载中...");
        RequestParams params = new RequestParams(Const.WuYe.URL_SELLER_QUERY_ORDER_DETAIL);
        params.addParameter("orderNo", bean.getOrderNo());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                OrderListDetailsBean bean = new Gson().fromJson(result, OrderListDetailsBean.class);
                datasBeanList.addAll(bean.getDatas());
                detailsAdapter.notifyDataSetChanged();

                ProgressUtil.close();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ProgressUtil.close();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void initView() {
        bean = (OrderListBean.DatasBean) getIntent().getExtras().get("bean");
        tagShopSell = getIntent().getExtras().getString("tag");

        if (bean.getIsDelivery() == 0) {  //商家是否派送 0是 1否
            ll_order_list_details_vouchers.setVisibility(View.GONE);
        } else if (bean.getIsDelivery() == 1) {
            if (tagShopSell != null && tagShopSell.equals(OrderListFragment.ADAPTER_TAG)) {//买家
                ll_order_list_details_vouchers.setVisibility(View.VISIBLE);
            } else if (tagShopSell != null && tagShopSell.equals(MyShopActivity.ADAPTER_TAG)) {//卖家
                ll_order_list_details_vouchers.setVisibility(View.GONE);
            }
        }
        setActionBar(bean.getSellerName(), R.drawable.nav_return, 0);
        order_list_details_name.setText(bean.getSellerName());
        order_list_details_distribution.setText(bean.getSellerName());
        order_list_details_number.setText(bean.getOrderNo());
        Date date = new Date();
        date.setTime(bean.getOrderDate() * 1000L);
        order_list_details_time.setText(DateUtil.DateToString(date, DateStyle.YYYY_MM_DD_HH_MM));
        time_1.setText(DateUtil.DateToString(date, DateStyle.YYYY_MM_DD_HH_MM));
        order_list_details_msg.setText(bean.getCustomerName() + "  " + bean.getCustomerPhone() + "\n" + bean.getCustomerAddress());
        order_list_details_note.setText(bean.getOrderNote());
        initDoubleBtn();

        detailsAdapter = new OrderListDetailsAdapter(this);
        detailsAdapter.setPayMoneyGoodsBeanList(datasBeanList);
        lv_order_list_dateils.setAdapter(detailsAdapter);

    }

    private void initDoubleBtn() {

//        0	待支付
//        1	已经付
//        2	商户确认
//        3	用户确认订单（完成订单）
//        4	用户取消
//        5	商户取消
//        6	退款中
//        7	退款完成
//        8	退款失败

        if ("0".equals(bean.getPaymentStatus())) {
            if (bean.getIsDelivery() == 0) {
                item_order_list_detail_state.setText("待支付");
                if (tagShopSell != null && tagShopSell.equals(OrderListFragment.ADAPTER_TAG)) {//买家
                    item_order_list_details_left.setText("去支付");
                    item_order_list_details_right.setText("取消订单");
                } else if (tagShopSell != null && tagShopSell.equals(MyShopActivity.ADAPTER_TAG)) {//卖家
                    item_order_list_details_left.setText("取消订单");
                    item_order_list_details_right.setText("联系买家");
                }
                ll_details_double_btn.setVisibility(View.VISIBLE);
                item_order_list_details_left.setVisibility(View.VISIBLE);
                item_order_list_details_right.setVisibility(View.VISIBLE);
            } else if (bean.getIsDelivery() == 1) {
                item_order_list_detail_state.setText("待支付");
                if (tagShopSell != null && tagShopSell.equals(OrderListFragment.ADAPTER_TAG)) {//买家
                    item_order_list_details_left.setText("去支付");
                    item_order_list_details_right.setText("取消订单");
                    ll_details_double_btn.setVisibility(View.VISIBLE);
                    item_order_list_details_left.setVisibility(View.VISIBLE);
                    item_order_list_details_right.setVisibility(View.VISIBLE);
                } else if (tagShopSell != null && tagShopSell.equals(MyShopActivity.ADAPTER_TAG)) {//卖家
                    ll_details_double_btn.setVisibility(View.GONE);
                }
            }
        } else if ("1".equals(bean.getPaymentStatus())) { //支付完成的状态
            if (bean.getIsDelivery() == 0) {
                item_order_list_details_left.setText("退款");
                if (tagShopSell != null && tagShopSell.equals(OrderListFragment.ADAPTER_TAG)) {//买家
                    item_order_list_details_right.setText("联系卖家");
                    item_order_list_detail_state.setText("已支付");
                } else if (tagShopSell != null && tagShopSell.equals(MyShopActivity.ADAPTER_TAG)) {//卖家
                    item_order_list_details_right.setText("确认订单");
                    item_order_list_detail_state.setText("待确认");
                }

                ll_details_double_btn.setVisibility(View.VISIBLE);
                item_order_list_details_left.setVisibility(View.VISIBLE);
                item_order_list_details_right.setVisibility(View.VISIBLE);
            } else if (bean.getIsDelivery() == 1) {
                item_order_list_details_left.setText("退款");
                if (tagShopSell != null && tagShopSell.equals(OrderListFragment.ADAPTER_TAG)) {//买家
                    item_order_list_details_right.setText("联系卖家");
                    item_order_list_detail_state.setText("待销费");
                    ll_details_double_btn.setVisibility(View.VISIBLE);
                    item_order_list_details_left.setVisibility(View.VISIBLE);
                    item_order_list_details_right.setVisibility(View.VISIBLE);
                } else if (tagShopSell != null && tagShopSell.equals(MyShopActivity.ADAPTER_TAG)) {//卖家
                    item_order_list_detail_state.setText("待消费");
                    ll_details_double_btn.setVisibility(View.GONE);
                }
            }
        } else if ("2".equals(bean.getPaymentStatus())) {
            if (bean.getIsDelivery() == 0) {
                if (tagShopSell != null && tagShopSell.equals(OrderListFragment.ADAPTER_TAG)) {//买家
                    item_order_list_details_left.setText("联系卖家");
                    item_order_list_details_right.setText("确认完成");
                    item_order_list_detail_state.setText("卖家已确认");
                    item_order_list_details_left.setVisibility(View.VISIBLE);
                    item_order_list_details_right.setVisibility(View.VISIBLE);
                    ll_details_double_btn.setVisibility(View.VISIBLE);
                } else if (tagShopSell != null && tagShopSell.equals(MyShopActivity.ADAPTER_TAG)) {//卖家
                    item_order_list_detail_state.setText("已确认");
                    ll_details_double_btn.setVisibility(View.GONE);
                }
            } else if (bean.getIsDelivery() == 1) {
                item_order_list_detail_state.setText("待消费");
                ll_details_double_btn.setVisibility(View.GONE);
            }
        } else if ("3".equals(bean.getPaymentStatus())) {
            if (tagShopSell != null && tagShopSell.equals(OrderListFragment.ADAPTER_TAG)) {//买家
                if (bean.getEvaluteStatus() == 0) {//0未评价 1已评价
                    item_order_list_details_left.setText("评价");
                } else if (bean.getEvaluteStatus() == 1) {
                    item_order_list_details_left.setText("查看评价");
                }
                ll_details_double_btn.setVisibility(View.VISIBLE);
            } else if (tagShopSell != null && tagShopSell.equals(MyShopActivity.ADAPTER_TAG)) {//卖家
                if (bean.getEvaluteStatus() == 0) {//0未评价 1已评价
                    ll_details_double_btn.setVisibility(View.GONE);
                } else if (bean.getEvaluteStatus() == 1) {
                    item_order_list_details_left.setText("查看评价");
                    ll_details_double_btn.setVisibility(View.VISIBLE);
                }
            }
            item_order_list_detail_state.setText("已完成");
            item_order_list_details_left.setVisibility(View.VISIBLE);
            item_order_list_details_right.setVisibility(View.GONE);
        } else if ("4".equals(bean.getPaymentStatus())) {
            ll_details_double_btn.setVisibility(View.GONE);
            item_order_list_detail_state.setText("买家已取消订单");
        } else if ("5".equals(bean.getPaymentStatus())) {
            ll_details_double_btn.setVisibility(View.GONE);
            item_order_list_detail_state.setText("卖家已取消订单");
        } else if ("6".equals(bean.getPaymentStatus())) {//退款中
            ll_details_double_btn.setVisibility(View.GONE);
            item_order_list_detail_state.setText("退款中");

            ll_order_list_details_refund.setVisibility(View.VISIBLE);
            tv_refund.setText(bean.getRefundNote());

        } else if ("7".equals(bean.getPaymentStatus())) {//退款完成
            ll_details_double_btn.setVisibility(View.GONE);
            item_order_list_detail_state.setText("退款完成");
        } else if ("8".equals(bean.getPaymentStatus())) {//退款失败
            ll_details_double_btn.setVisibility(View.GONE);
            item_order_list_detail_state.setText("退款失败");
        }

        item_order_list_details_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int state = 0;
                switch (bean.getPaymentStatus()) {
                    case "0":
                        if (tagShopSell != null && tagShopSell.equals(OrderListFragment.ADAPTER_TAG)) {
                            upDateOrderState(1);//买家去支付
                        } else if (tagShopSell != null && tagShopSell.equals(MyShopActivity.ADAPTER_TAG)) {
                            upDateOrderState(5);//商户取消
                        }
                        break;
                    case "1":
                        if (tagShopSell != null && tagShopSell.equals(OrderListFragment.ADAPTER_TAG)) {
                            showDialogBuy();//买家退款
                        } else if (tagShopSell != null && tagShopSell.equals(MyShopActivity.ADAPTER_TAG)) {
                            showDialog();//卖家退款
                        }
                        break;
                    case "2":
                        TelePhoneUtils.telePhone(OrderListDetailsActivity.this, bean.getSellerPhone());
                        break;
                    case "3":
                        if (tagShopSell != null && tagShopSell.equals(OrderListFragment.ADAPTER_TAG)) {
                            if (bean.getEvaluteStatus() == 0) {//0未评价 1已评价
                                startNextActivity();
                            } else if (bean.getEvaluteStatus() == 1) {
                                startNextActivity();
                            }
                        } else if (tagShopSell != null && tagShopSell.equals(MyShopActivity.ADAPTER_TAG)) {
                            startNextActivity();
                        }
                        break;

                }
            }
        });

        item_order_list_details_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int state = 0;
                switch (bean.getPaymentStatus()) {
                    case "0":
                        if (tagShopSell != null && tagShopSell.equals(OrderListFragment.ADAPTER_TAG)) {
                            upDateOrderState(4);//用户取消
                        } else if (tagShopSell != null && tagShopSell.equals(MyShopActivity.ADAPTER_TAG)) {
                            TelePhoneUtils.telePhone(OrderListDetailsActivity.this, bean.getCustomerPhone());
                        }
                        break;
                    case "1":
                        if (tagShopSell != null && tagShopSell.equals(OrderListFragment.ADAPTER_TAG)) {
                            TelePhoneUtils.telePhone(OrderListDetailsActivity.this, bean.getSellerPhone());
                        } else if (tagShopSell != null && tagShopSell.equals(MyShopActivity.ADAPTER_TAG)) {
                            upDateOrderState(2);
                        }
                        break;
                    case "2":
                        upDateOrderState(3); //买家完成订单
                        break;
                }
            }
        });
    }

    private void startNextActivity() {
        Intent intent = new Intent(OrderListDetailsActivity.this, OrderListEvaluationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 卖家退款
     */
    private void showDialog() {
        final CustomDialog.Builder dialog = new CustomDialog.Builder(this);
        dialog.setTitle("退款");
        View view = LayoutInflater.from(this).inflate(R.layout.my_edit_text, null);
        final EditText editText = (EditText) view.findViewById(R.id.et_input_result);
        dialog.setContentView(view);
        dialog.setPositiveButton("确认退款", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                refund(editText.getText().toString());
            }
        });
        dialog.setNegativeButton("联系买家", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TelePhoneUtils.telePhone(OrderListDetailsActivity.this, bean.getCustomerPhone());
                dialog.dismiss();
            }
        });
        dialog.create().show();
    }

    /**
     * 买家退款
     */
    private void showDialogBuy() {
        final CustomDialog.Builder dialog = new CustomDialog.Builder(this);
        dialog.setTitle("退款");
        View view = LayoutInflater.from(this).inflate(R.layout.my_edit_text, null);
        final EditText editText = (EditText) view.findViewById(R.id.et_input_result);
        dialog.setContentView(view);
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                refund(editText.getText().toString());
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.create().show();
    }


    /**
     * 改变订单状态
     *
     * @param state 点击item的状态
     */
    private void upDateOrderState(final int state) {
        ProgressUtil.show(this, "");
        RequestParams params = new RequestParams(Const.WuYe.URL_ORDER_UPDATE_ORDER_STATE);
//        orderNo：订单号；paymentStatus ：订单状态
        params.addParameter("orderNo", bean.getOrderNo());
        params.addParameter("paymentStatus", state);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Log.d("订单状态改变", result);
                Base base = new Gson().fromJson(result, Base.class);
                if (base.respCode == 1001) {
                    bean.setPaymentStatus(state + "");
                    initDoubleBtn();
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

    private void refund(final String refundReason) {
        ProgressUtil.show(this, "");
        RequestParams params = new RequestParams(Const.WuYe.URL_ORDER_REFUND);
//        orderNo：订单号；refundReason 退款原因
        params.addParameter("orderNo", bean.getOrderNo());
        params.addParameter("refundReason", refundReason);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("订单状态改变", result);
                Base base = new Gson().fromJson(result, Base.class);
                if (base.respCode == 1001) {
                    bean.setPaymentStatus(6 + "");//状态6 为退款中
                    bean.setRefundNote(refundReason);
                    initDoubleBtn();
                }
                ProgressUtil.close();
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
