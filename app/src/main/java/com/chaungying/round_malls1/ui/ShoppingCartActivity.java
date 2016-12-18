package com.chaungying.round_malls1.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.BaseActivity;
import com.chaungying.round_malls.interface_.ReturnShoppingTotalPirceListener;
import com.chaungying.round_malls.recyclerView.FamiliarRecyclerView;
import com.chaungying.round_malls1.adapter.ShoppingCartAdapter;
import com.chaungying.round_malls1.bean.ShoppingGoodsBean;
import com.chaungying.round_malls1.listener.SelectListener;
import com.chaungying.wuye3.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * @author 王晓赛 or 2016/9/22
 */
@ContentView(R.layout.activity_shopping_cart)

public class ShoppingCartActivity extends BaseActivity implements ReturnShoppingTotalPirceListener, SelectListener {

    //右边商品的列表
    private ArrayList<ShoppingGoodsBean.ProductsBean> goodsRightBeanList = new ArrayList<>();

    //存储临时列表的集合
    private ArrayList<ShoppingGoodsBean.ProductsBean> tempList = new ArrayList<>();

    //经过筛选之后进入支付界面的集合
    private ArrayList<ShoppingGoodsBean.ProductsBean> payMoneyTempList = new ArrayList<>();


    @ViewInject(R.id.lv_shopping_cart)
    private FamiliarRecyclerView lv_shopping_cart;

    @ViewInject(R.id.shopping_cart_total_price1)
    private TextView shopping_cart_total_price;

    //去下单
    @ViewInject(R.id.shopping_cart_Place_oreder)
    private TextView shopping_cart_Place_oreder;

    //全选
    @ViewInject(R.id.full_select_iv)
    private ImageView full_select_iv;

    //全选按钮的开关
    private boolean fullTag = true;

    private ShoppingCartAdapter shoppingCartAdapter;

    private String totalPrice;//100

    private int storeId;
    private String storeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBar(R.string.gou_wu_che, R.drawable.nav_return, 0);
        goodsRightBeanList = (ArrayList<ShoppingGoodsBean.ProductsBean>) getIntent().getExtras().getSerializable("list");
        totalPrice = getIntent().getExtras().getString("totalPrice");
        storeId = getIntent().getExtras().getInt("storeId");
        storeName = getIntent().getExtras().getString("storeName");
        initView();
    }

    private void initView() {
        shopping_cart_total_price.setText("￥" + totalPrice);
        lv_shopping_cart.setLayoutManager(new LinearLayoutManager(this));
        shoppingCartAdapter = new ShoppingCartAdapter(this);
        shoppingCartAdapter.setToolPrice(totalPrice);
        shoppingCartAdapter.setReturnPirceListener(this);
        shoppingCartAdapter.setListener(this);
        for (int i = 0; i < goodsRightBeanList.size(); i++) {
            if (goodsRightBeanList.get(i).getShoppingNum() != 0) {
                tempList.add(goodsRightBeanList.get(i));
            }
        }
        shoppingCartAdapter.setList(tempList);
        lv_shopping_cart.setAdapter(shoppingCartAdapter);
    }


    @Override
    public void returnTotalPirce(String price) {
        shopping_cart_total_price.setText("￥" + price);
        placeOrder(price);
    }

    /**
     * 去下单按钮是否可以点击
     */
    public void placeOrder(String price) {
        if ("0.00".equals(price)) {
            shopping_cart_Place_oreder.setBackgroundColor(getResources().getColor(R.color.color_999999));
            shopping_cart_Place_oreder.setEnabled(false);
        } else {
            shopping_cart_Place_oreder.setBackgroundColor(getResources().getColor(R.color.color_fe7634));
            shopping_cart_Place_oreder.setEnabled(true);
        }
    }

    /**
     * 去下单
     *
     * @param view
     */
    @Event(value = R.id.shopping_cart_Place_oreder)
    private void shopping_cart_Place_oreder(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("storeId", storeId);
        bundle.putString("totalPrice", shopping_cart_total_price.getText().toString().substring(1));
        bundle.putString("storeName", storeName);
        payMoneyTempList.clear();
        for (ShoppingGoodsBean.ProductsBean bean : tempList) {
            if (bean.isCheck()) {
                payMoneyTempList.add(bean);
            }
        }
        bundle.putSerializable("list", payMoneyTempList);
        openActivty(this, PayMoneyActivity.class, bundle, null);
    }

    /**
     * \全选按钮
     *
     * @param view
     */
    @Event(value = R.id.full_select_iv)
    private void full_select_iv(View view) {
        if (fullTag) {
            full_select_iv.setImageResource(R.drawable.select_no);
            for (int i = 0; i < shoppingCartAdapter.getList().size(); i++) {
                shoppingCartAdapter.getList().get(i).setCheck(false);
            }
        } else {
            full_select_iv.setImageResource(R.drawable.select);
            for (int i = 0; i < shoppingCartAdapter.getList().size(); i++) {
                shoppingCartAdapter.getList().get(i).setCheck(true);
            }
        }
        String price = shoppingCartAdapter.calculatePrice();
        shopping_cart_total_price.setText("￥" + price);
        placeOrder(price);
        shoppingCartAdapter.notifyDataSetChanged();
        fullTag = !fullTag;
    }

    @Override
    public void selectListener(String price) {
        shopping_cart_total_price.setText("￥" + price);
        placeOrder(price);
        for (int i = 0; i < shoppingCartAdapter.getList().size(); i++) {
            if (shoppingCartAdapter.getList().get(i).isCheck()) {
                if (i == shoppingCartAdapter.getList().size() - 1) {
                    fullTag = true;
                }
            } else {
                fullTag = false;
                break;
            }
        }
        if (fullTag) {
            full_select_iv.setImageResource(R.drawable.select);
        } else {
            full_select_iv.setImageResource(R.drawable.select_no);
        }
    }
}
