package com.chaungying.qiandao.ui;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.common.utils.T;
import com.chaungying.common.utils.date.DateStyle;
import com.chaungying.common.utils.date.DateUtil;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.modues.login.bean.HttpRequestBase;
import com.chaungying.modues.main.view.SquareLayout;
import com.chaungying.qiandao.bean.SignInBean;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.litesuits.common.assist.Network;
import com.litesuits.common.assist.Toastor;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 签到
 *
 * @author 种耀淮 在2016年07月13日11:42创建
 */
@ContentView(R.layout.activity_sign_in)
public class SignInActivity extends BaseActivity {

    private Toastor toastor;

    public static final int UP_DATA_DATE = 0x0001;
    public static final int UP_DATA_WIFI = 0x0002;
    public static final int UP_DATA_BTN = 0x0003;
    public static final int UP_DATA_PROGRESS = 0x0004;


    //标题栏左边图片
    @ViewInject(R.id.title_back)
    private ImageView titleLeft;

    @ViewInject(R.id.title_menu)
    private TextView titleRight;

    // 日期
    @ViewInject(R.id.signIn_date)
    private TextView dateTv;
    // 日期
    @ViewInject(R.id.signIn_date2)
    private TextView dateTv2;

    // 时间
    @ViewInject(R.id.signIn_time)
    private TextView timeTv;
    // 时间
    @ViewInject(R.id.signIn_time2)
    private TextView timeTv2;

    // 秒
    @ViewInject(R.id.signIn_time_seconds)
    private TextView secondsTv;
    // 秒
    @ViewInject(R.id.signIn_time_seconds2)
    private TextView secondsTv2;

    // 签到时间布局
//    @ViewInject(R.id.signIn_signTimeLayout)
//    private LinearLayout signTimeLayout;

    // 签到时间说明
//    @ViewInject(R.id.signIn_signTimeTv)
//    private TextView signtimeStateTv;

    // 签到时间
//    @ViewInject(R.id.signIn_signTime)
//    private TextView signTimeTv;

    // 定位地址
    @ViewInject(R.id.signIn_location)
    private TextView locationTv;

    // 定位地址
    @ViewInject(R.id.signIn_location2)
    private TextView locationTv2;
    // 定位latitude
    @ViewInject(R.id.signIn_latitude)
    private TextView latitudeTv;
    // 定位longitude
    @ViewInject(R.id.signIn_longitude)
    private TextView longitudeTv;

    // wifi
    @ViewInject(R.id.signIn_wifi)
    private TextView wifiTv;
    // wifi
    @ViewInject(R.id.signIn_wifi2)
    private TextView wifiTv2;

//    // 进度条
//    @ViewInject(R.id.signIn_progress)
//    private ProgressWheel progressBar;

    // 圆圈点击按钮
    @ViewInject(R.id.signIn_btn_rootLayout)
    private SquareLayout btn;
    // 圆圈点击按钮
    @ViewInject(R.id.signIn_btn_rootLayout2)
    private SquareLayout btn2;

    // 圆圈灰色
    @ViewInject(R.id.signIn_btn_rootGrayLayout)
    private SquareLayout btnGray;

//    @ViewInject(R.id.signIn_btnUp)
//    private TextView btnUpTv;

    @ViewInject(R.id.signIn_btnTv)
    private TextView BtnTv;

    // Wifi管理器
    WifiManager wifiManager;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

//    private boolean isBtnClick = true;// 签到按钮可否点击

    private double latitude;
    private double longitude;

    private int userId;
    private int signInCount = 2;

    private SignInBean mSignInBean;

    private boolean isSignIn = true;

    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        toastor = new Toastor(this);
        userId = (int) SPUtils.get(this, Const.SPDate.ID, 0);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        createView();

        getData();
    }

    /**
     * 获取数据
     */
    private void getData() {
        ProgressUtil.show(this, "加载中");
        RequestParams params = new RequestParams(Const.WuYe.URL_SIGN_IN_COUNT);
        params.addParameter("date", DateUtil.DateToString(new Date(System.currentTimeMillis()), DateStyle.YYYY_MM_DD));
        params.addParameter("memberId", userId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    mSignInBean = new Gson().fromJson(result, SignInBean.class);
                    if (mSignInBean.getRespCode() == 1001) {
                        signInCount = mSignInBean.getTodayNum();
                        upDataView();
                    } else {
                        toastor.showSingletonToast(mSignInBean.getRespMsg());
                    }
                } catch (JsonSyntaxException e) {
                    toastor.showSingletonToast("服务器异常");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                toastor.showSingletonToast("服务器异常");
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
        // 初始化Title
        setActionBarText(R.string.signin, R.drawable.nav_return, R.string.statistical);
        // 初始化Content
        handler.sendEmptyMessage(UP_DATA_DATE);// 发送消息更改时间控件
        handler.sendEmptyMessage(UP_DATA_WIFI);// 更新wifi名称
        initLocation();
        mLocationClient.start();
    }

    /**
     * 更新UI
     */
    private void upDataView() {
        // 根据签到次数进行UI更新
        if (signInCount == 0) {
//            AnimationUtil.setAlphaVisibility(btn);
            btn.setVisibility(View.VISIBLE);
//            BtnTv.setText("签到");
//            btnUpTv.setText("点击进行签到");
        } else if (signInCount == 1) {
//            AnimationUtil.setAlphaVisibility(btn);
            btn2.setVisibility(View.VISIBLE);
//            BtnTv.setText("签退");
//            btnUpTv.setText("点击进行签退");
        } else if (signInCount >= 2) {
//            AnimationUtil.setAlphaGone(btn);
//            BtnTv.setText("签到");
//            btnUpTv.setText("已完成今日签到签退");
            btn.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化定位参数
     */
    private void initLocation() {
        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000 * 60;
        option.setScanSpan(span);
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    /**
     * 获取wifi信息
     *
     * @return wifi名称
     */
    private String getConnectWifiSsid() {
        switch (Network.getNetworkType(this)) {
            case Net2G:
                return "2G网络";
            case Net3G:
                return "3G网络";
            case Net4G:
                return "4G网络";
            case Wifi:// 无线网络
                WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                return wifiInfo.getSSID().replace("\"", "");
            case UnKnown:
                return "未连接到网络，无法签到。";
        }
        return "未知";
    }

    /**
     * 签到按钮点击事件
     *
     * @param v 视图
     */
    @Event(value = R.id.signIn_btn_rootLayout)
    private void onSignIn(View v) {
//        if (isBtnClick) {
        mLocationClient.start();
//            btnUpTv.setText("正在定位");
//            progressBar.setVisibility(View.VISIBLE);
//            progressBar.setProgress(1f);
//            progressBar.setCallback(new ProgressWheel.ProgressCallback() {
//                @Override
//                public void onProgressUpdate(float progress) {
//                    if (progress == 1f) {
//                        btnUpTv.setText("签到中");
//                        upDataBtnState();
//                        AnimationUtil.setAlphaVisibility(signTimeLayout);
//                        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
//                        signTimeTv.setText(formatterTime.format(new Date(System.currentTimeMillis())));
//                    }
//                }
//            });
        upDataBtnState();
//        }
    }

    @Event(value = R.id.signIn_btn_rootLayout2)
    private void onSignOut(View view) {
        mLocationClient.start();
        upDataBtnState();
    }

    /**
     * 更新按钮显示状态
     */
    private void upDataBtnState() {
//        Animation animation = AnimationUtils.loadAnimation(x.app(), R.anim.sign_in_progress);
//        progressBar.startAnimation(animation);
//        animation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                progressBar.setProgress(0f);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
        handler.sendEmptyMessageDelayed(UP_DATA_BTN, 0);
    }

    /**
     * Handler线程
     */
    private Handler handler = new Handler() {
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatterDatePoint = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
        SimpleDateFormat formatterSeconds = new SimpleDateFormat("ss");

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UP_DATA_DATE:// 更新时间
                    // 发送延时消息给自己
                    handler.sendEmptyMessageDelayed(UP_DATA_DATE, 1000);
                    // 获取时间
                    Date curDate = new Date(System.currentTimeMillis());
                    // 设置日期
                    dateTv.setText(formatterDatePoint.format(curDate));
                    dateTv2.setText(formatterDatePoint.format(curDate));
                    // 设置时间
                    timeTv.setText(formatterTime.format(curDate));
                    timeTv2.setText(formatterTime.format(curDate));
                    // 设置秒
                    secondsTv.setText(formatterSeconds.format(curDate));
                    secondsTv2.setText(formatterSeconds.format(curDate));
                    break;
                case UP_DATA_WIFI:// 更新Wifi
                    handler.sendEmptyMessageDelayed(UP_DATA_WIFI, 10000);
                    wifiTv.setText(getConnectWifiSsid().replaceAll("\"", ""));
                    wifiTv2.setText(getConnectWifiSsid().replaceAll("\"", ""));
                    break;
                case UP_DATA_BTN:// 点击按钮更新状态
                    upSignInDatas();
                    break;
            }
        }
    };

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            latitude = location.getLatitude();
            latitudeTv.setText(latitude+"");
            longitude = location.getLongitude();
            longitudeTv.setText(longitude+"");
//            String describe = location.getLocationDescribe();// 位置语义化信息
            String describe = location.getAddrStr();//
            if (describe == null || describe.length() == 0) {
                locationTv.setText("未知");
                locationTv2.setText("未知");
            } else {
                locationTv.setText(describe);
                locationTv2.setText(describe);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeMessages(UP_DATA_DATE);
        handler.removeMessages(UP_DATA_WIFI);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.removeMessages(UP_DATA_DATE);
        handler.removeMessages(UP_DATA_WIFI);
        handler.sendEmptyMessage(UP_DATA_DATE);// 发送消息更改时间控件
        handler.sendEmptyMessage(UP_DATA_WIFI);// 更新wifi名称
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(UP_DATA_DATE);
        handler.removeMessages(UP_DATA_WIFI);
    }

    //点击返回按钮时
    @Event(value = R.id.title_back)
    private void back(View view) {
        finish();
    }

    @Event(value = R.id.title_menu)
    private void titleMenu(View view) {
        openActivty(this, TongJiActivity.class, null, null);
    }

    /**
     * 提交签到的数据
     */
    private void upSignInDatas() {
//        if (signInCount >= 2) {
//            toastor.showSingletonToast("超出每日上限");
//        } else if (!isSignIn) {
//            LogUtil.e("刚刚签过");
//        } else if (isRunning) {
//            toastor.showSingletonToast("正在签到");
//        } else {
//            isRunning = true;
        RequestParams params = new RequestParams(Const.WuYe.URL_SIGN);
        params.setConnectTimeout(5 * 1000);
//        signInDate=签到日期&signInTime=签到时间&wifiName=wifi&signInAddress=签到地址&memberId=用户id
        String date = dateTv.getText().toString().replace(".", "-");
        params.addParameter("signInDate", date);
        params.addParameter("signInTime", timeTv.getText().toString());
        params.addParameter("wifiName", wifiTv.getText().toString());
        params.addParameter("signInAddress", locationTv.getText().toString());
        params.addParameter("memberId", SPUtils.get(this, Const.SPDate.ID, 4512));
        params.addParameter("signInLatitude", latitudeTv.getText().toString());
        params.addParameter("signInLongitude", longitudeTv.getText().toString());

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                    isBtnClick = true;
                try {
                    HttpRequestBase base = new Gson().fromJson(result, HttpRequestBase.class);
                    if (base.respCode == 1001) {
                        if (btn.getVisibility() == View.VISIBLE) {
                            btn.setVisibility(View.GONE);
                            btn2.setVisibility(View.VISIBLE);
                            toastor.showLongToast("签到成功");
                        } else {
                            btn2.setVisibility(View.GONE);
                            toastor.showLongToast("签退成功");
                        }
                        isSignIn = false;
//                            T.showLong(SignInActivity.this, base.respMsg);
//                            AnimationUtil.setAlphaGone(btn);
                        if (signInCount == 0) {
//                                btnUpTv.setText("签到成功");

                        } else if (signInCount == 1) {
//                                btnUpTv.setText("签退成功");
                        }
                    } else {
                        T.showLong(SignInActivity.this, base.respMsg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.showLong(SignInActivity.this, "签到失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                isRunning = false;
            }
        });
//        }
    }
}
