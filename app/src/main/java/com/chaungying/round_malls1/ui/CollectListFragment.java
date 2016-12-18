package com.chaungying.round_malls1.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.round_malls1.adapter.ShoppingStoreAdapter;
import com.chaungying.round_malls1.bean.ShoppingStoreBean;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 创建者：  王晓赛
 * 时  间： 2016/10/11
 * <p>
 * 收藏列表 的适配器
 */

@ContentView(R.layout.fragment_collect_list)
public class CollectListFragment extends Fragment {

    // 列表
    @ViewInject(R.id.fragmentOrder_list)
    private ListView listView;

    // 列表适配器
    private ShoppingStoreAdapter adapter;

    // 数据
    private ArrayList<ShoppingStoreBean.DatasBean> datas = new ArrayList<>();


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
        adapter = new ShoppingStoreAdapter(getContext());
        adapter.setList(datas);
        listView.setAdapter(adapter);
        refresh();
    }

    private void refresh() {
        datas.clear();
        ProgressUtil.show(getContext(), "加载中...");
        RequestParams params = new RequestParams(Const.WuYe.URL_SELLER_QUERY_SELLER_COLLECT);
        params.addParameter("memberId", SPUtils.get(getContext(), Const.SPDate.ID, -1));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ShoppingStoreBean bean = gson.fromJson(result, ShoppingStoreBean.class);
                adapter.setList(bean.getDatas());
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

}
