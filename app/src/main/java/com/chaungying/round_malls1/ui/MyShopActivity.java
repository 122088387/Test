package com.chaungying.round_malls1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.round_malls1.adapter.OrderListAdapter;
import com.chaungying.round_malls1.bean.OrderListBean;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：  王晓赛
 * 时  间： 2016/10/19
 */
@ContentView(R.layout.activity_my_shop)
public class MyShopActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    public static final String ADAPTER_TAG = "商家";

    // 列表
    @ViewInject(R.id.my_shop_myorder_list)
    private ListView listView;

    // 列表适配器
    private OrderListAdapter adapter;

    // 数据
    private List<OrderListBean.DatasBean> datas = new ArrayList<>();

    //消费
    @ViewInject(R.id.title_menu)
    private TextView title_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void initView() {
        title_menu.setVisibility(View.GONE);
        setActionBarText(R.string.wo_de_shang_pu, R.drawable.nav_return, R.string.xiao_fei);
        // 初始化列表
        adapter = new OrderListAdapter(this);
        adapter.setDatas(datas);
        adapter.setTag(ADAPTER_TAG);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    private void refresh() {
        datas.clear();
        ProgressUtil.show(this, "加载中...");
        RequestParams params = new RequestParams(Const.WuYe.URL_SELLER_QUERY_ORDER);
//        page  页号  row 行数，sellerMemberId:商户会员id
        params.addParameter("sellerMemberId", SPUtils.get(this, Const.SPDate.ID, -1));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                OrderListBean bean = gson.fromJson(result, OrderListBean.class);
                datas.addAll(bean.getDatas());
                if (datas.size() > 0) {
                    if (datas.get(0).getIsDelivery() == 0) {
                        title_menu.setVisibility(View.GONE);
                    } else if (datas.get(0).getIsDelivery() == 1) {
                        title_menu.setVisibility(View.VISIBLE);
                    }
                }
                adapter.notifyDataSetChanged();
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


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, OrderListDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", datas.get(i));
        bundle.putString("tag", MyShopActivity.ADAPTER_TAG);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 点击消费
     *
     * @param view
     */
    @Event(value = R.id.title_menu)
    private void xiao_fei(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("sellerId", datas.get(0).getSellerId());
        openActivty(this, MyShopConsumpActivity.class, bundle, null);
    }
}
