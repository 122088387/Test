package com.chaungying.round_malls.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.chaungying.BaseActivity;
import com.chaungying.round_malls.adapter.RoundMallsAdapter;
import com.chaungying.round_malls.bean.RoundMallsBean;
import com.chaungying.round_malls.interface_.ReturnShoppingTotalPirceListener;
import com.chaungying.round_malls.recyclerView.FamiliarRecyclerView;
import com.chaungying.round_malls.utils.BigDecimalUtils;
import com.chaungying.wuye3.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/9/22
 *         <p/>
 *         周边商城
 */
@ContentView(R.layout.activity_round_malls)
public class RoundMallsActivity extends BaseActivity implements View.OnClickListener, ReturnShoppingTotalPirceListener {

    @ViewInject(R.id.lv_round_malls)
    private FamiliarRecyclerView lv_round_malls;

    private List<RoundMallsBean> list = new ArrayList<>();

    private RoundMallsAdapter adapter;

    @ViewInject(R.id.confirm_order)
    private TextView confirm_order;

    @ViewInject(R.id.round_malls_total_price)
    private TextView round_malls_total_price;

    private String totalStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBar("周边商城", R.drawable.nav_return, 0);
        initData();
        initView();

        lv_round_malls.setAdapter(adapter);
        confirm_order.setOnClickListener(this);
    }

    private void initData() {
        totalStr = round_malls_total_price.getText().toString();
        for (int i = 0; i < 50; i++) {
            RoundMallsBean bean = new RoundMallsBean();
            bean.setName("鼠标" + i);
            bean.setPrice(1.11);
            bean.setShoppingNum(0);
            list.add(bean);
        }
        round_malls_total_price.setText(totalStr + "0.00");
    }

    private void initView() {
        lv_round_malls.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RoundMallsAdapter(this);
        adapter.setReturnPirceListener(this);
        adapter.setList(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_order://确认下单
                openActivty(this, ShoppingCartActivity.class, null, null);
                break;
        }
    }

    @Override
    public void returnTotalPirce(String price) {
        round_malls_total_price.setText(totalStr + BigDecimalUtils.rounding(price));
    }
}
