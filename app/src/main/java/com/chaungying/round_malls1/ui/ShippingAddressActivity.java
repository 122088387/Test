package com.chaungying.round_malls1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Base;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.round_malls.recyclerView.FamiliarRecyclerView;
import com.chaungying.round_malls1.adapter.ShoppingAddressAdapter;
import com.chaungying.round_malls1.bean.ShoppingAddressBean;
import com.chaungying.wuye3.R;
import com.chaungying.zixunjieda.ui.ZiXunJieDaConfigActivity;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 创建者：  王晓赛
 * 时  间： 2016/10/10
 * <p>
 * 选择收货地址
 */
@ContentView(R.layout.activity_shipping_address)
public class ShippingAddressActivity extends BaseActivity implements FamiliarRecyclerView.OnItemClickListener {

    @ViewInject(R.id.lv_shipping_address)
    private FamiliarRecyclerView lv_shipping_address;

    private ShoppingAddressAdapter addressAdapter;
    private List<ShoppingAddressBean.DatasBean> addressList = new ArrayList<>();

    private String url = "";

    private boolean isRoundMalls;

    public static final int CASE_RESULT = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBarText(R.string.shipping_address, R.drawable.nav_return, R.string.xin_zeng);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            url = bundle.getString("url");
        }

        lv_shipping_address.setLayoutManager(new LinearLayoutManager(this));
        addressAdapter = new ShoppingAddressAdapter(this);

        addressAdapter.setList(addressList);
        lv_shipping_address.setAdapter(addressAdapter);

        lv_shipping_address.setOnItemClickListener(this);
    }

    private void initData() {
        ProgressUtil.show(this, "加载中...");
        if (url.equals("")) {
            //如果是周边商城进入这个页面
            url = Const.WuYe.URL_RECEIVER_RECEIVER_LIST;
            isRoundMalls = true;
            addressAdapter.setIsRoundMalls("是");
        }
        RequestParams params = new RequestParams(url);
        params.addParameter("memberId", SPUtils.get(this, Const.SPDate.ID, -1));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                addressList.clear();
                ShoppingAddressBean bean = new Gson().fromJson(result, ShoppingAddressBean.class);
                addressList.addAll(bean.getDatas());
                Collections.sort(addressList);
                addressAdapter.notifyDataSetChanged();
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

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Event(value = R.id.title_menu)
    private void addAddress(View view) {
        Bundle bundle = new Bundle();
        if(isRoundMalls){//是周边商城
            openActivty(this, AddShippingAddressActivity.class, bundle, null);
        }else{//不是周边商城
            bundle.putString("isRoundMalls","不是");
            openActivty(this, AddShippingAddressActivity.class, bundle, null);
        }
    }

    @Override
    public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
        if (isRoundMalls) {//是周边商城
            ProgressUtil.show(this, "");
            RequestParams params = new RequestParams(Const.WuYe.URL_RECEIVER_UPDASTE_RECEIVER_ENABLE);
//        receiverId：地址id；receiverEnable 收货人默认状态  0-默认 1-非默认；
            params.addParameter("receiverId", addressList.get(position).getReceiverId());
            params.addParameter("receiverEnable", 0);
            params.addParameter("memberId", SPUtils.get(this, Const.SPDate.ID, -1));
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    ProgressUtil.close();
                    Base base = new Gson().fromJson(result, Base.class);
                    if (base.respCode == 1001) {
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
        } else {
            ShoppingAddressBean.DatasBean bean = addressList.get(position);
            //不是周边商城
            Intent intent = new Intent();
            intent.setClass(ShippingAddressActivity.this, ZiXunJieDaConfigActivity.class);
            Bundle bunlde = new Bundle();
            bunlde.putString("name", bean.getReceiverName());
            bunlde.putString("phone", bean.getReceiverPhone());
            bunlde.putString("address", bean.getReceiverAddress() + " " + bean.getDoorNumber());
            intent.putExtras(bunlde);
            setResult(CASE_RESULT, intent);
            finish();
        }
    }
}
