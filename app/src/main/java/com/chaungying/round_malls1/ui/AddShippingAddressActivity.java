package com.chaungying.round_malls1.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Base;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.common.utils.T;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.round_malls1.bean.ShoppingAddressBean;
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
 * 时  间： 2016/10/10
 * <p>
 * 新增收货地址
 */
@ContentView(R.layout.activity_add_shipping_address)
public class AddShippingAddressActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.et_add_shipping_address_name)
    private EditText name;

    @ViewInject(R.id.add_address_man)
    private ImageView manSex;

    @ViewInject(R.id.add_address_woman)
    private ImageView woManSex;

    @ViewInject(R.id.et_add_shipping_address_phone)
    private EditText phone;

    @ViewInject(R.id.et_add_shipping_address_address)
    private EditText receiverAddress;

    @ViewInject(R.id.et_add_shipping_address_details)
    private EditText doorNumber;

    private boolean isMan = true;

    private ShoppingAddressBean.DatasBean datasBean;

    private boolean isUpDataState;

    //是否是周边商城标志
    private String isRoundMalls = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBar("新增地址", R.drawable.nav_return, 0);

        isRoundMalls = getIntent().getExtras().getString("isRoundMalls");
        datasBean = (ShoppingAddressBean.DatasBean) getIntent().getExtras().getSerializable("bean");

        initView();

    }

    private void initView() {
        manSex.setOnClickListener(this);
        woManSex.setOnClickListener(this);
        //更新状态，进行判断
        if (datasBean != null) {

            isUpDataState = true;

            name.setText(datasBean.getReceiverName());
            isMan = datasBean.getSex() == 1 ? true : false;
            if (isMan) {
                manSex.setImageResource(R.drawable.select);
                woManSex.setImageResource(R.drawable.select_no);
            } else {
                manSex.setImageResource(R.drawable.select_no);
                woManSex.setImageResource(R.drawable.select);
            }
            phone.setText(datasBean.getReceiverPhone());
            receiverAddress.setText(datasBean.getReceiverAddress());
            doorNumber.setText(datasBean.getDoorNumber() + "");
        }
    }

    @Event(value = R.id.save_add_address)
    private void addAddress(View view) {
        String url = "";
        if (isUpDataState) {
            url = Const.WuYe.URL_RECEIVER_UPDATE_RECEIVER;//更新
        } else {
            url = Const.WuYe.URL_RECEIVER_ADD_RECEIVER;//添加
        }
        ProgressUtil.show(this, "保存中...");
        RequestParams params = new RequestParams(url);
//        memberId 会员ID；receiverName 收货人姓名；receiverPhone 收货人电话；receiverAddress 收货人街道地址；
//        receiverEnable 收货人默认状态  0-默认 1-非默认； sex  性别 1男 2 女；doorNumber  门牌号；
        params.addParameter("memberId", SPUtils.get(this, Const.SPDate.ID, -1));
        params.addParameter("receiverName", name.getText().toString());
        params.addParameter("receiverPhone", phone.getText().toString());
        params.addParameter("receiverAddress", receiverAddress.getText().toString());
        params.addParameter("receiverEnable", 0);
        params.addParameter("sex", isMan ? "1" : "2");
        params.addParameter("doorNumber", doorNumber.getText().toString());
        if (isUpDataState) {
            params.addParameter("receiverId", datasBean.getReceiverId());
        }
        if (isRoundMalls.equals("不是")) {
            params.addParameter("type", 2);
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ProgressUtil.close();
                Base base = new Gson().fromJson(result, Base.class);
                if (base.respCode == 1001) {
                    T.showShort(AddShippingAddressActivity.this, "保存成功");
                    finish();
                }
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

    /**
     * 删除该地址
     *
     * @param view
     */
//    @Event(value = R.id.save_delete_address)
//    private void save_delete_address(View view) {
//        ProgressUtil.show(this, "删除中...");
//        RequestParams params = new RequestParams(Const.WuYe.URL_RECEIVER_DEL_RECEIVER);
//        params.addParameter("receiverId", datasBean.getReceiverId());
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                ProgressUtil.close();
//                Base base = new Gson().fromJson(result, Base.class);
//                if (base.respCode == 1001) {
//                    finish();
//                } else {
//                    T.showShort(AddShippingAddressActivity.this, "服务器出错");
//                }
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                ProgressUtil.close();
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
//    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_address_man:
                if (!isMan) {
                    manSex.setImageResource(R.drawable.select);
                    woManSex.setImageResource(R.drawable.select_no);
                    isMan = !isMan;
                }
                break;
            case R.id.add_address_woman:
                if (isMan) {
                    woManSex.setImageResource(R.drawable.select);
                    manSex.setImageResource(R.drawable.select_no);
                    isMan = !isMan;
                }
                break;
        }
    }
}
