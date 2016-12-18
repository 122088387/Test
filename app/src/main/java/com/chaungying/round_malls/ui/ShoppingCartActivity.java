package com.chaungying.round_malls.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.chaungying.BaseActivity;
import com.chaungying.round_malls.recyclerView.FamiliarRecyclerView;
import com.chaungying.round_malls1.bean.ShoppingGoodsBean;
import com.chaungying.wuye3.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * @author 王晓赛 or 2016/9/22
 */
@ContentView(R.layout.activity_shopping_cart)
public class ShoppingCartActivity extends BaseActivity {

    //右边商品的列表
    private ArrayList<ShoppingGoodsBean.ProductsBean> goodsRightBeanList = new ArrayList<>();

    @ViewInject(R.id.lv_shopping_cart)
    private FamiliarRecyclerView lv_shopping_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBarText(R.string.gou_wu_che, R.drawable.nav_return, R.string.bian_ji);
        goodsRightBeanList = (ArrayList<ShoppingGoodsBean.ProductsBean>) getIntent().getExtras().getSerializable("list");
        initView();
    }

    private void initView() {
        lv_shopping_cart.setLayoutManager(new LinearLayoutManager(this));


    }
}
