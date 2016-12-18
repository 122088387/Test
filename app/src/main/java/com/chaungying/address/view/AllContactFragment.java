package com.chaungying.address.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chaungying.address.adapter.ParkListAdapter;
import com.chaungying.address.bean.GardenContactBean;
import com.chaungying.address.ui.DepartmentActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/7/29
 */
public class AllContactFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView allContactListView;
    List<GardenContactBean.DataBean> list = new ArrayList<GardenContactBean.DataBean>();
    ParkListAdapter adapter;
    //获取园区的bean
    GardenContactBean gardenContactBean;

    public static AllContactFragment getInstance() {
        AllContactFragment sf = new AllContactFragment();

        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getNetContact("contacts/showDistricts.action");
    }

    /**
     * 从服务器获取园区列表
     */
    private void getNetContact(String url) {
        RequestParams params = new RequestParams(Const.WuYe.URL_ADDRESS_PARK_LIST + url);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                gardenContactBean = gson.fromJson(result, GardenContactBean.class);
                adapter.setList(gardenContactBean.getData());
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

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.all_contact_fragment, null);
        allContactListView = (ListView) v.findViewById(R.id.lv_all_contact_fragment);

        adapter = new ParkListAdapter(getContext());
        adapter.setList(list);
        allContactListView.setAdapter(adapter);
        allContactListView.setOnItemClickListener(this);
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String isShowMembers = gardenContactBean.getIsShowMembers();
        String isNextUrlParam = gardenContactBean.getIsNextUrlParam();
        String requestUrl = gardenContactBean.getRequestUrl();
        String isShowMembersParam = gardenContactBean.getIsShowMembersParam();
        String paramKey = gardenContactBean.getParamKey();
        int val = gardenContactBean.getData().get(position).getVal();
        if (isShowMembers.equals("1")) {//不是人员列表
            if (isNextUrlParam.equals("1")) {//不拼接
                Intent intent = new Intent(getActivity(), DepartmentActivity.class);
                intent.putExtra("requestUrl", requestUrl);
                startActivity(intent);
            } else if (isNextUrlParam.equals("0")) {//拼接

            }
        } else if (isShowMembers.equals("0")) {//是人员列表

        }
        //首先移除掉所有存的数据
        SPUtils.remove(getContext(), Const.SpAddress.ADDRESS_KEY);

        if (isShowMembersParam.equals("0") || isShowMembers.equals("0")) {//存起来
            SPUtils.put(getContext(), Const.SpAddress.ADDRESS_KEY, paramKey + ":" + val);
        } else if (isShowMembersParam.equals("1")) {//不存

        }
    }
}
