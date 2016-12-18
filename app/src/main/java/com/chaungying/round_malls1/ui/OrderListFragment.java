package com.chaungying.round_malls1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单列表 Fragment
 */
@ContentView(R.layout.fragment_order_list)
public class OrderListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final int REQUEST_CODE = 0x0001;

    public static final String ADAPTER_TAG = "买家";

    // 列表
    @ViewInject(R.id.fragmentOrder_list)
    private ListView listView;

    // 列表适配器
    private OrderListAdapter adapter;

    // 数据
    private List<OrderListBean.DatasBean> datas = new ArrayList<>();


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    refresh();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = x.view().inject(this, inflater, container);
        createFragment();
        return view;
    }

    /**
     * 初始化
     */
    private void createFragment() {
        // 初始化列表
        adapter = new OrderListAdapter(getContext());
        adapter.setDatas(datas);
        adapter.setHandler(handler);
        adapter.setTag(ADAPTER_TAG);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        datas.clear();
        ProgressUtil.show(getContext(), "加载中...");
        RequestParams params = new RequestParams(Const.WuYe.URL_ORDER_QUERY_ORDER);
        params.addParameter("memberId", SPUtils.get(getContext(), Const.SPDate.ID, -1));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                OrderListBean bean = gson.fromJson(result, OrderListBean.class);
                datas.addAll(bean.getDatas());
                adapter.notifyDataSetChanged();
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getContext(), OrderListDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", datas.get(i));
        bundle.putString("tag", OrderListFragment.ADAPTER_TAG);
        intent.putExtras(bundle);
        getContext().startActivity(intent);

    }
}
