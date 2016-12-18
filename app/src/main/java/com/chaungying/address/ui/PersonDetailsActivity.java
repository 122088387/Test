package com.chaungying.address.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.BaseActivity;
import com.chaungying.address.bean.PersonListBean;
import com.chaungying.common.constant.Base;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.common.utils.T;
import com.chaungying.common.view.CustomDialog;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * @author 王晓赛 or 2016/8/31
 */

@ContentView(R.layout.activity_person_details)
public class PersonDetailsActivity extends BaseActivity {

    private PersonListBean.DataBean dataBean;

    @ViewInject(R.id.person_details_image)
    private ImageView person_details_image;

    @ViewInject(R.id.person_details_name)
    private TextView person_details_name;

    @ViewInject(R.id.person_details_position)
    private TextView person_details_position;

    @ViewInject(R.id.person_details_phone)
    private TextView person_details_phone;

    @ViewInject(R.id.person_details_department)
    private TextView person_details_department;

    @ViewInject(R.id.title_menu)
    private ImageView addPerson;

    private String tag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        tag = getIntent().getStringExtra("tag");
        dataBean = (PersonListBean.DataBean) getIntent().getSerializableExtra("person_bean");
        if ("often".equals(tag)) {
            setActionBar("个人信息", R.drawable.nav_return, 0);
        } else if ("person_list".equals(tag)) {
            setActionBar("个人信息", R.drawable.nav_return, R.drawable.add_image);
        }
        initView();
    }

    private void initView() {
        person_details_name.setText(dataBean.getUserName());
        person_details_position.setText(dataBean.getPosition());
        person_details_phone.setText(dataBean.getLoginName());
        person_details_department.setText(dataBean.getDepartmentName());
    }

    @Event(value = R.id.title_menu)
    private void addPerson(View view) {
        final CustomDialog.Builder dialog = new CustomDialog.Builder(this);
        dialog.setTitle("提示");
        dialog.setMessage("是否添加到常用联系人?");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                upLoadaddPerson();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.create().show();


    }

    /**
     * 添加联系人
     */
    private void upLoadaddPerson() {
        ProgressUtil.show(this, "添加中...");
        RequestParams params = new RequestParams(Const.WuYe.URL_ADDRESS_ADD_FRIEND);
        params.addParameter("userId", SPUtils.get(this, Const.SPDate.ID, -1));
        params.addParameter("friendId", dataBean.getId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Base base = gson.fromJson(result, Base.class);
                T.showLong(PersonDetailsActivity.this, base.respMsg);
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
}
