package com.chaungying.use_car.ui;

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
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.modues.login.bean.HttpRequestBase;
import com.chaungying.use_car.adapter.UseCarRecordAdapter;
import com.chaungying.use_car.bean.UseCarRecord;
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
 * 用车已预订列表
 *
 * @author 种耀淮 在2016年09月09日11:03创建
 */
@ContentView(R.layout.activity_metting_room_record)
public class UseCarRecordActivity extends BaseActivity {

    /**
     * 优化有的Toast
     */
    private Toastor toastor;

    @ViewInject(R.id.mettingRoomCancel_list)
    private ListView mListView;

    /**
     * 空数据显示文本控件
     */
    @ViewInject(R.id.mettingRoomCancel_nullData)
    private TextView mNullDataTv;

    private UseCarRecordAdapter mAdapter;

    private UseCarRecord mUseCarRecord;

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
        RequestParams params = new RequestParams(Const.WuYe.URL_USER_CAR_MY_RECORD);
        params.addParameter("userId", userId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    mUseCarRecord = new Gson().fromJson(result, UseCarRecord.class);
                    if (mUseCarRecord.getRespCode() == 1001) {
                        createView();
                    } else {
                        toastor.showSingletonToast(mUseCarRecord.getRespMsg());
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                toastor.showSingletonToast("服务器异常");
                mUseCarRecord = new UseCarRecord();
                mUseCarRecord.setDatas(new ArrayList<UseCarRecord.DatasBean>());
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
        mAdapter = new UseCarRecordAdapter(this, mUseCarRecord.getDatas());
        if (mUseCarRecord.getDatas() == null || mUseCarRecord.getDatas().size() == 0) {
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
                cancelReservation(mUseCarRecord.getDatas().get(position).getId());
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
        RequestParams params = new RequestParams(Const.WuYe.URL_USER_CAR_CANCEL);
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
