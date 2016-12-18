package com.chaungying.modues.main.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaungying.common.bean.VersionBean;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.L;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.common.view.CustomDialog;
import com.chaungying.modues.login.ui.LoginActivity;
import com.chaungying.modues.main.ui.MainActivity;
import com.chaungying.modues.main.ui.UpdatePasswordActivity;
import com.chaungying.modues.main.ui.VoiceVibrationActivity;
import com.chaungying.set.AboutSystemActivity;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;
import com.zcw.togglebutton.ToggleButton;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 设置界面
 *
 * @anthor 王晓赛 or 2016/6/22
 */
public class SetFragment extends Fragment implements ToggleButton.OnToggleChanged, View.OnClickListener {

    private Context mContext;

    private ImageView iv_red;

    //版本更新
    private RelativeLayout rl_version;
    //关于系统
    private RelativeLayout rl_about;
    //修改密码
    private RelativeLayout rl_update_password;
    //声音和震动
    private RelativeLayout rl_voice_vibration;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View view = inflater.inflate(R.layout.fragment_set, null);
        TextView loginName = (TextView) view.findViewById(R.id.tv_name);
        loginName.setText((CharSequence) SPUtils.get(mContext, Const.SPDate.USER_NAME, "未登录"));
        TextView title = (TextView) view.findViewById(R.id.title);
        iv_red = (ImageView) view.findViewById(R.id.iv_red);
        rl_version = (RelativeLayout) view.findViewById(R.id.rl_version);
        rl_version.setOnClickListener(this);
        rl_about = (RelativeLayout) view.findViewById(R.id.rl_about);
        rl_about.setOnClickListener(this);
        rl_update_password = (RelativeLayout) view.findViewById(R.id.rl_update_password);
        rl_update_password.setOnClickListener(this);

        rl_voice_vibration = (RelativeLayout) view.findViewById(R.id.rl_voice_vibration);
        rl_voice_vibration.setOnClickListener(this);

        //退出按钮
        TextView tvExit = (TextView) view.findViewById(R.id.btn_exit);
        tvExit.setOnClickListener(this);
        //对自动登录开关的设置
        final ToggleButton autoBtn = (ToggleButton) view.findViewById(R.id.auto_btn);
        boolean isAuto = (boolean) SPUtils.get(mContext, Const.SPDate.LOGIN_AUTO, true);
        if (isAuto) {
            autoBtn.setToggleOn();
        } else {
            autoBtn.setToggleOff();
        }
        autoBtn.setOnToggleChanged(this);
        title.setText("设置");
        getLocaVersion();
        getNetVersion();
        //
        return view;
    }

    String locVersion = "";

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getLocaVersion() {
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            locVersion = info.versionName;
//            this.getString(R.string.version_name) +
            return locVersion;
        } catch (Exception e) {
            e.printStackTrace();
//            this.getString(R.string.can_not_find_version_name)
            return "";
        }
    }

    String netVersion = "";

    private void getNetVersion() {
        RequestParams params = new RequestParams(Const.WuYe.URL_VERSION_CHECK);
//        ?ostype=android&optype=version
        params.addParameter("ostype", "android");
        params.addParameter("optype", "version");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                L.e(result);
//                {"version":"1.0"}
                Gson gson = new Gson();
                VersionBean bean = gson.fromJson(result, VersionBean.class);
                netVersion = bean.getVersion();
//                1.0.1
                if (!locVersion.equals(netVersion)) {
                    iv_red.setVisibility(View.VISIBLE);
                    //通过回调给设置按钮增加红色提示
                    if (setSystemUpdateListener != null) {
                        setSystemUpdateListener.systemUpdate(true);
                    }
                } else {
                    iv_red.setVisibility(View.GONE);
                    if (setSystemUpdateListener != null) {
                        setSystemUpdateListener.systemUpdate(false);
                    }
                }
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

    private SetSystemUpdateListener setSystemUpdateListener;

    public void setSetSystemUpdateListener(SetSystemUpdateListener setSystemUpdateListener) {
        this.setSystemUpdateListener = setSystemUpdateListener;
    }

    public interface SetSystemUpdateListener {
        void systemUpdate(boolean isShow);
    }

    /**
     * 自动登录按钮开关的监听
     *
     * @param on
     */
    @Override
    public void onToggle(boolean on) {
        //将点击后的登录按钮状态存入SP文件
        SPUtils.put(mContext, Const.SPDate.LOGIN_AUTO, on);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit:
                final CustomDialog.Builder dialog = new CustomDialog.Builder(mContext);
                dialog.setTitle("退出");
                dialog.setMessage("您确定要退出吗?");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        //用户是否点击退出登录按钮
                        intent.putExtra("click_exit", true);
                        startActivity(intent);
                        ((MainActivity) mContext).finish();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();
                    }
                });
                dialog.create().show();
                break;
            case R.id.rl_version:
//                if (locVersion.equals(netVersion)) {
//                    T.showShort(mContext, "已经是最新版本");
//                } else {
////                    http://139.129.10.71:31005/App.aspx，
//                    Intent intent1 = new Intent();
//                    intent1.setAction(Intent.ACTION_VIEW);
//                    intent1.setData(Uri.parse(Const.WuYe.URL_VERSION_SHOW_PAGE));
//                    startActivity(intent1);
//                }
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse(Const.WuYe.URL_VERSION_SHOW_PAGE));
                startActivity(intent1);
                break;
            case R.id.rl_about:
                Intent intentSys = new Intent(mContext, AboutSystemActivity.class);
                startActivity(intentSys);
                break;
            case R.id.rl_update_password:
                Intent intentUpData = new Intent(mContext, UpdatePasswordActivity.class);
                startActivity(intentUpData);
                break;
            case R.id.rl_voice_vibration:
                Intent intentVoiceVibration = new Intent(mContext, VoiceVibrationActivity.class);
                startActivity(intentVoiceVibration);
                break;
        }
    }
}
