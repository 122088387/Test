package com.chaungying.round_malls1.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.common.utils.T;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.round_malls.utils.BigDecimalUtils;
import com.chaungying.round_malls1.adapter.PayMoneyGoodsAdapter;
import com.chaungying.round_malls1.bean.OrderBean;
import com.chaungying.round_malls1.bean.OrderReturnBean;
import com.chaungying.round_malls1.bean.Pay;
import com.chaungying.round_malls1.bean.PayMoneyGoodsBean;
import com.chaungying.round_malls1.bean.ShoppingAddressBean;
import com.chaungying.round_malls1.bean.ShoppingGoodsBean;
import com.chaungying.round_malls1.utils.SpannableUtils;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 创建者：  王晓赛
 * 时  间： 2016/9/30
 * 支付页面
 */
@ContentView(R.layout.activity_pay_money)
public class PayMoneyActivity extends BaseActivity {

//    @ViewInject(R.id.pay_money_btn)
//    private Button pay_money_btn;

    @ViewInject(R.id.scrollView)
    private ScrollView scrollView;
    private Handler mHandler = new Handler();

    @ViewInject(R.id.lv_pay_money_store)
    private ListView lv_pay_money_store;

    @ViewInject(R.id.shopping_cart_total_price)
    private TextView shopping_cart_total_price;
    @ViewInject(R.id.pay_money_store_bei_zhu)
    private EditText pay_money_store_bei_zhu;

    @ViewInject(R.id.pay_money_adress)
    private TextView pay_money_adress;

    @ViewInject(R.id.pay_money_name)
    private TextView pay_money_name;

    @ViewInject(R.id.pay_money_phone)
    private TextView pay_money_phone;

    @ViewInject(R.id.pay_money_store_name)
    private TextView pay_money_store_name;

    //总计
    @ViewInject(R.id.pay_money_price)
    private TextView pay_money_price;

    private PayMoneyGoodsAdapter adapter;

    private String totalPrice;//100
    private int storeId;
    private String storeName;
    //需要付款的商品的列表
    private ArrayList<PayMoneyGoodsBean> payMoneyGoodsBeanArrayList = new ArrayList<>();
    private ArrayList<ShoppingGoodsBean.ProductsBean> tempList = new ArrayList<>();


    private String json = "";

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBar("支付", R.drawable.nav_return, 0);
        initData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDefaultShippingAddress();
    }

    /**
     * 获取默认的收货地址
     */
    private void getDefaultShippingAddress() {
        ProgressUtil.show(this, "获取收货地址中...");
        RequestParams params = new RequestParams(Const.WuYe.URL_RECEIVER_RECEIVER_LIST);
        params.addParameter("memberId", SPUtils.get(this, Const.SPDate.ID, -1));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ShoppingAddressBean bean = new Gson().fromJson(result, ShoppingAddressBean.class);
                for (int i = 0; i < bean.getDatas().size(); i++) {
                    if ("0".equals(bean.getDatas().get(i).getReceiverEnable())) {
                        ShoppingAddressBean.DatasBean tempBean = bean.getDatas().get(i);
                        pay_money_adress.setText(tempBean.getReceiverAddress() + " " + tempBean.getDoorNumber());
                        if (tempBean.getSex() == 1) {
                            pay_money_name.setText(tempBean.getReceiverName() + " 先生");
                        } else {
                            pay_money_name.setText(tempBean.getReceiverName() + " 女士");
                        }
                        pay_money_phone.setText(tempBean.getReceiverPhone());
                        break;
                    }
                }
                ProgressUtil.close();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.showShort(PayMoneyActivity.this, "获取收货地址失败");
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
        String temp = "￥"+totalPrice;
        pay_money_price.setText(SpannableUtils.setTextSize(temp,0,1,24));
        shopping_cart_total_price.setText(temp);
        pay_money_store_name.setText(storeName);

        adapter = new PayMoneyGoodsAdapter(this);
        adapter.setPayMoneyGoodsBeanList(payMoneyGoodsBeanArrayList);
        lv_pay_money_store.setAdapter(adapter);
    }


    private void initData() {
        tempList = (ArrayList<ShoppingGoodsBean.ProductsBean>) getIntent().getExtras().getSerializable("list");
        totalPrice = getIntent().getExtras().getString("totalPrice");
        storeId = getIntent().getExtras().getInt("storeId");
        storeName = getIntent().getExtras().getString("storeName");
        for (int i = 0; i < tempList.size(); i++) {
            PayMoneyGoodsBean payBean = new PayMoneyGoodsBean();
            payBean.setName(tempList.get(i).getProName());
            payBean.setNum(tempList.get(i).getShoppingNum());
            payBean.setPrice(BigDecimalUtils.rounding(BigDecimalUtils.cheng(tempList.get(i).getPrice(), tempList.get(i).getShoppingNum() + "")));
            payMoneyGoodsBeanArrayList.add(payBean);
        }

        api = WXAPIFactory.createWXAPI(this, Const.WXAPPID.ID, true);
        api.registerApp(Const.WXAPPID.ID);

    }


//    @Event(value = R.id.pay_money_btn)
//    private void pay_money_btn(View view) {
//        if (isCanPay()) {
//            getOrderPay();
//        } else {
//            T.showTShort(PayMoneyActivity.this, "请您打开或者升级微信", 1000);
//        }
//    }

    private void getOrderPay() {

        // TODO: 2016/10/14  微信返回json数据 要给json赋值

        OrderReturnBean bean = new Gson().fromJson(json, OrderReturnBean.class);
        OrderReturnBean.RespMsgBean respMsgBean = bean.getRespMsg();
        try {
            if (bean.getRespCode() == 1043) {
                T.showTShort(PayMoneyActivity.this, "订单不存在或已支付", 1000);
            } else if (bean.getRespCode() == 1044) {
                T.showTShort(PayMoneyActivity.this, "调取微信失败", 1000);
            } else if (bean.getRespCode() == 1047) {
                T.showTShort(PayMoneyActivity.this, "没有优惠券，或优惠券已使用", 1000);
            } else {
                PayReq req = new PayReq();
                //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
//                req.appId = respMsgBean.getAppid();
                req.appId = Const.WXAPPID.ID;

                req.partnerId = respMsgBean.getPartnerid();
                req.prepayId = respMsgBean.getPrepayid();
                req.nonceStr = respMsgBean.getNoncestr();
                req.timeStamp = respMsgBean.getTimestamp() + "";
                req.packageValue = "Sign=WXPay";
                req.sign = respMsgBean.getSign();

//                req.extData			= "app data"; // optional

                api.sendReq(req);
//                              api.handleIntent(getIntent(), iwxapiEventHandler);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 微信是否可以支付
     *
     * @return ++
     */
    private boolean isCanPay() {
        boolean isPaySupported = api.getWXAppSupportAPI() >= com.tencent.mm.sdk.constants.Build.PAY_SUPPORTED_SDK_INT;
        return isPaySupported;
    }

    /**
     * 点击最上部的地址
     *
     * @param view
     */
    @Event(value = R.id.rl_pay_money_store_address)
    private void rl_pay_money_store_address(View view) {
        openActivty(this, ShippingAddressActivity.class, null, null);
    }

    @Event(value = R.id.shopping_cart_total_oreder1)
    private void shopping_cart_total_oreder1(View view) {
//        sellerId：商户id；orderNote：订单备注；memberId：用户id：customerName：购买人姓名；
//        customerPhone：手机；customerAddress：地址；goodsDetail：商品json串（goodsId，商品id，buyCount，数量）
        ProgressUtil.show(this, "订单提交中...");
        RequestParams params = new RequestParams(Const.WuYe.URL_SELLER_ADD_ORDER);

        params.addParameter("sellerId", storeId);
        params.addParameter("orderNote", pay_money_store_bei_zhu.getText().toString());
        params.addParameter("memberId", SPUtils.get(this, Const.SPDate.ID, -1));
        params.addParameter("customerName", SPUtils.get(this, Const.SPDate.USER_NAME, ""));
        params.addParameter("customerPhone", SPUtils.get(this, Const.SPDate.LONGING_NAME, ""));
        params.addParameter("customerAddress", pay_money_adress.getText().toString());

        final ArrayList<OrderBean> list = new ArrayList<>();

        for (int i = 0; i < tempList.size(); i++) {
            OrderBean bean = new OrderBean();
            bean.setGoodsId(tempList.get(i).getProId());
            bean.setBuyCount(tempList.get(i).getShoppingNum());
            list.add(bean);
        }
        params.addParameter("goodsDetail", new Gson().toJson(list));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("result", result);
                Pay base = new Gson().fromJson(result, Pay.class);
                if (base.respCode == 1001) {
                    T.showShort(PayMoneyActivity.this, "成功");
                }
                // TODO: 2016/10/14 将返回提交订单后数据  根据该数据调起微信支付

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

//    @Event(value = R.id.pay_money_store_bei_zhu)
//    private void pay_money_store_bei_zhu(View view) {
//        //这里必须要给一个延迟，如果不加延迟则没有效果。我现在还没想明白为什么
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //将ScrollView滚动到底
//                scrollView.fullScroll(View.FOCUS_DOWN);
//            }
//        }, 100);
//    }
}
