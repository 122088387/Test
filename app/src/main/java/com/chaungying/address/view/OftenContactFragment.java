package com.chaungying.address.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chaungying.address.adapter.PersonListAdapter;
import com.chaungying.address.bean.PersonListBean;
import com.chaungying.address.ui.PersonDetailsActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.common.http.HttpCallBack;
import com.chaungying.common.http.MyHttpRequest;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.common.utils.internet.NetworkUtils;
import com.chaungying.wuye3.R;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class OftenContactFragment extends Fragment implements HttpCallBack, AdapterView.OnItemClickListener {

    ListView lvOftenContact;
    //请求网络数据的类
    MyHttpRequest request;
    PersonListAdapter adapter;
    List<PersonListBean.DataBean> list = new ArrayList<PersonListBean.DataBean>();
    PersonListBean personListBean;

    public static OftenContactFragment getInstance() {
        OftenContactFragment sf = new OftenContactFragment();
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        request = MyHttpRequest.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_simple_card, null);
        lvOftenContact = (ListView) view.findViewById(R.id.lv_often_contact);
        adapter = new PersonListAdapter(getContext());
        adapter.setList(list);
        lvOftenContact.setAdapter(adapter);
        lvOftenContact.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //联网获取数据
        RequestParams params = new RequestParams(Const.WuYe.URL_SEARCH_OFTEN_CONTACT);
        params.addParameter("userId", SPUtils.get(getContext(), Const.SPDate.ID, -1));
        request.postRequse(params);
        request.setHttpCallBack(this);
    }

    @Override
    public void requestSuccess(String result) {
        personListBean = NetworkUtils.getGson().fromJson(result, PersonListBean.class);
        adapter.setList(personListBean.getData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void requestError(Throwable throwable) {

    }

    @Override
    public void requestFinish() {

    }

    @Override
    public void noData() {
        lvOftenContact.setEmptyView(getActivity().findViewById(R.id.empty_view));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PersonListBean.DataBean dataBean = personListBean.getData().get(position);
        Intent intent = new Intent(getContext(), PersonDetailsActivity.class);
        intent.putExtra("person_bean", dataBean);
        intent.putExtra("tag","often");
        startActivity(intent);
    }
}