package com.chaungying.metting.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.common.view.CustomDialog;
import com.chaungying.metting.adapter.MettingRoomRecordAdapter;
import com.chaungying.metting.bean.MettingRoomRecord;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.modues.login.bean.HttpRequestBase;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.litesuits.common.assist.Toastor;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 会议室已预定列表页面(取消操作用)
 * Created by Chooo on 2016/9/9 0009.
 */
@ContentView(R.layout.activity_metting_room_record)
public class MettingRoomRecordActivity extends BaseActivity {

    private Toastor toastor;

    @ViewInject(R.id.mettingRoomCancel_list)
    private ListView mListView;

    @ViewInject(R.id.mettingRoomCancel_nullData)
    private TextView mNullDataTv;

    private MettingRoomRecordAdapter mAdapter;

    private MettingRoomRecord mMettingRoomRecord;

    private int userId;

    private CustomDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        toastor = new Toastor(this);
        setActionBar("我的预定", R.drawable.nav_return, 0);
        userId = (int) SPUtils.get(this, Const.SPDate.ID, 0);

        getData();
    }

    private void getData() {
        mNullDataTv.setVisibility(View.GONE);
        ProgressUtil.show(this, "加载中");
        RequestParams params = new RequestParams(Const.WuYe.URL_METTING_ROOM_RECORD);
        params.addParameter("userId", userId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    mMettingRoomRecord = new Gson().fromJson(result, MettingRoomRecord.class);
                    if (mMettingRoomRecord.getRespCode() == 1001) {
                        createView();
                    } else {
                        toastor.showSingletonToast(mMettingRoomRecord.getRespMsg());
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                toastor.showSingletonToast("服务器异常");
                mMettingRoomRecord = new MettingRoomRecord();
                mMettingRoomRecord.setDatas(new ArrayList<MettingRoomRecord.DatasBean>());
                mNullDataTv.setVisibility(View.VISIBLE);
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

    /**
     * 初始化
     */
    private void createView() {
        // 初始化ListView
        mAdapter = new MettingRoomRecordAdapter(this, mMettingRoomRecord.getDatas());
        if (mMettingRoomRecord.getDatas() == null || mMettingRoomRecord.getDatas().size() == 0) {
            mNullDataTv.setVisibility(View.VISIBLE);
        }
        mListView.setAdapter(mAdapter);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showMyDialog(position);
                return true;
            }
        });
    }

    private void showMyDialog(final int position) {

        dialog = new CustomDialog.Builder(this);
        dialog.setTitle("提示");
        dialog.setMessage("是否取消预定");
        dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cancelReservation(mMettingRoomRecord.getDatas().get(position).getId());
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.create().show();


    }

    /**
     * 取消预订请求
     *
     * @param id
     */
    private void cancelReservation(int id) {
        ProgressUtil.show(this, "操作中");
        RequestParams params = new RequestParams(Const.WuYe.URL_METTING_ROOM_CANCEL);
        params.addParameter("id", id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    HttpRequestBase base = new Gson().fromJson(result, HttpRequestBase.class);
                    toastor.showSingletonToast(base.respMsg);
                    getData();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    toastor.showSingletonToast("网络错误");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                toastor.showSingletonToast("网络错误");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                ProgressUtil.close();
                dialog.dismiss();
            }
        });
    }
}
