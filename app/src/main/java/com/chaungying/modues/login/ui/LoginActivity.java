package com.chaungying.modues.login.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.MyApplication;
import com.chaungying.WuYeData;
import com.chaungying.common.constant.Const;
import com.chaungying.common.constant.ExtraTag;
import com.chaungying.common.utils.KeyBoardUtils;
import com.chaungying.common.utils.L;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.common.utils.T;
import com.chaungying.modues.login.bean.HttpRequestBase;
import com.chaungying.modues.main.bean.RoleAppsBean;
import com.chaungying.modues.main.ui.MainActivity;
import com.chaungying.qiandao.AnimationUtil;
import com.chaungying.sever.MyServiceZHY;
import com.chaungying.wuye3.R;
import com.vmeet.netsocket.tool.Constants;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 登陆页面
 *
 * @author 种耀淮 2016/6/29创建
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    public static final int FINISH = 0x0004;

    private final String TAG = "LoginActivity";

    //上下文
    private Activity context;

    // 欢迎画
    @ViewInject(R.id.login_splash)
    private ImageView splashIv;

    // Logo
    @ViewInject(R.id.login_image_logo)
    private ImageView logoIv;

    // 输入框父布局
    @ViewInject(R.id.login_ll)
    private FrameLayout inputLL;

    // 用户名输入框
    @ViewInject(R.id.login_et_username)
    private EditText userET;

    // 用户名删除
    @ViewInject(R.id.login_username_clear)
    private ImageView userClearIv;

    // 密码输入框
    @ViewInject(R.id.login_et_password)
    private EditText passwordET;

    // 密码删除
    @ViewInject(R.id.login_password_clear)
    private ImageView passwordClearIv;

    // 登陆按钮
    @ViewInject(R.id.login_btn_go)
    private Button goBtn;

    //使用教程
    @ViewInject(R.id.use_course)
    TextView use_course;

    // 是否可以点击
    private boolean isGoClick = true;

    // 解析器
//    private LoginPresenter presenter;

    private boolean isWelcome;

    //默认是自动登录
//    private boolean autoLogin = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        isWelcome = (boolean) SPUtils.get(this, Const.SPDate.IS_FIRST, true);
        context = this;
        use_course.setVisibility(View.GONE);
//        autoLogin = (boolean) SPUtils.get(this, Const.SPDate.LOGIN_AUTO, false);

        // 实例化解析器
//        presenter = new LoginPresenter(this, httpBase);

        executeAmin();
        createEditListener();
    }

    /**
     * 点击父布局取消软键盘
     */
    @Event(value = R.id.login_root)
    private void onRoot(View v) {
        KeyBoardUtils.closeKeybord(userET, this);
    }

    /**
     * 用户名删除按钮监听
     */
    @Event(value = R.id.login_username_clear)
    private void onUserClear(View v) {
        userET.setText("");
    }

    /**
     * 密码删除按钮监听
     */
    @Event(value = R.id.login_password_clear)
    private void onPasswordClear(View v) {
        passwordET.setText("");
    }

    /**
     * 登陆按钮点击事件
     *
     * @param v
     */
    @Event(value = R.id.login_btn_go)
    private void go(View v) {
        KeyBoardUtils.closeKeybord(userET, this);
        if (isGoClick) {
            isGoClick = false;
            String userName = userET.getText().toString();
            String password = passwordET.getText().toString();
            // 登陆操作
            login(userName, password);
        }
    }

    private void login(String userName, String userPassWord) {
        if (userName.length() == 11) {
            if (!userPassWord.isEmpty()) {
                try {
                    //正在登录时不让做任何操作
                    goBtn.setEnabled(false);
                    userET.setEnabled(false);
                    passwordET.setEnabled(false);
                    goBtn.setText(getResources().getText(R.string.is_login));//正在登陆
//                     获取手机token
//                    TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//                    String szImei = TelephonyMgr.getDeviceId();
                    RequestParams params = new RequestParams(Const.WuYe.URL_LOGIN);
//                    params.setConnectTimeout(30 * 1000);
                    params.addParameter("loginName", userName);
                    params.addParameter("password", userPassWord);
                    params.addParameter("token", "11117");
                    //获取手机token的值
//                    TelephoneUtil.getIMEI(this);
                    params.addParameter("systemType", "android");
                    TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    String DEVICE_ID = tm.getDeviceId();
                    params.addParameter("phoneId", DEVICE_ID);
                    Log.e("DEVICE_ID", DEVICE_ID);
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            HttpRequestBase requestBase = MyApplication.gson.fromJson(result, HttpRequestBase.class);
                            if (requestBase.respCode == 1001) {
                                // 用户id存入全局
                                WuYeData.Initialize().setUserId(requestBase.user.id);
                                // 账号密码存入数据库
                                spStore(requestBase);
                                L.i(TAG, requestBase.respMsg + "  " + requestBase.user);
                                ArrayList<RoleAppsBean> list = (ArrayList<RoleAppsBean>) requestBase.roleApps;

                                String macId = (String) SPUtils.get(LoginActivity.this, Const.SPDate.MAC_ID_ORIGINAL, "");
                                if (!macId.equals("")) {
                                    MyApplication.socketObj.setMac(macId);//macID在正常情况下，该MAcid需要后台返回，此处需要测试，因此写了一个死的
                                }
                                MyApplication.socketObj.setIp(Constants.SERVER_IP);
                                MyApplication.socketObj.setPort(Constants.SERVER_PORT);
                                Intent serviceIntent = new Intent(LoginActivity.this, MyServiceZHY.class);
                                startService(serviceIntent);//启动长连接服务
                                //跳转到主界面
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("bundle", list);
                                intent.putExtra(ExtraTag.APP_JSON, bundle);
                                startActivity(intent);
                                finish();
                            } else {
//                                T.showShort(this, requestBase.respMsg);
                                goBtn.setEnabled(true);
                                userET.setEnabled(true);
                                passwordET.setEnabled(true);
                                isGoClick = true;
                                goBtn.setText(getResources().getText(R.string.login));
                                T.showShort(LoginActivity.this, requestBase.respMsg);
                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            goBtn.setEnabled(true);
                            userET.setEnabled(true);
                            passwordET.setEnabled(true);
                            isGoClick = true;
                            goBtn.setText("登陆");
                            T.showShort(LoginActivity.this, "获取数据失败");
                            ex.printStackTrace();
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });
                } catch (Exception e) {
                    T.showShort(this, "请开启手机应用的权限");
                    goBtn.setEnabled(true);
                    userET.setEnabled(true);
                    passwordET.setEnabled(true);
                    isGoClick = true;
                    goBtn.setText("登陆");
                }
            } else {
                T.showShort(this, "密码不能为空");
                isGoClick = true;
            }
        } else {
            T.showShort(this, "请输入正确的手机号");
            isGoClick = true;
        }
    }

    /**
     * 网络回调
     */
//    private HttpBase httpBase = new HttpBase() {
//        @Override
//        public void onSuccess(Object result) {
//            goBtn.setText("登陆成功");
//            Intent intent = new Intent(context, MainActivity.class);
//            startActivity(intent);
//        }
//
//        @Override
//        public void onError(Throwable ex, boolean isOnCallback) {
//            isGoClick = true;
//            goBtn.setText("登陆");
//            if (ex instanceof HttpException) { // 网络错误
//                HttpException httpEx = (HttpException) ex;
//                int responseCode = httpEx.getCode();
//                String responseMsg = httpEx.getMessage();
//                String errorResult = httpEx.getResult();
//                // ...
//            } else { // 其他错误
//                // ...
//            }
//        }
//
//        @Override
//        public void onFinished() {
//
//        }
//    };

    /**
     * 初始化输入框监听
     */
    private void createEditListener() {
        if (userET.getText().length() != 0) {
            userClearIv.setVisibility(View.VISIBLE);
        }
        if (passwordET.getText().length() != 0) {
            passwordClearIv.setVisibility(View.VISIBLE);
        }
        userET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (userET.getText().length() != 0) {
                    AnimationUtil.setAlphaVisibility(userClearIv);
                } else {
                    AnimationUtil.setAlphaGone(userClearIv);
                }
                passwordET.setText("");
                AnimationUtil.setAlphaGone(passwordClearIv);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        passwordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (userET.getText().length() == 0) {
                    KeyBoardUtils.openKeybord(userET, context);
                }
                if (passwordET.getText().length() != 0) {
                    AnimationUtil.setAlphaVisibility(passwordClearIv);
                } else {
                    AnimationUtil.setAlphaGone(passwordClearIv);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 执行动画
     */
    private void executeAmin() {
        if (isWelcome) {
            // 欢迎画淡出
            Animation splashAnimation = new AlphaAnimation(1f, 0f);
            splashAnimation.setDuration(2000);
            splashAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
//                    splashIv.setVisibility(View.GONE);
//                    boolean  isClickExit = getIntent().getBooleanExtra("click_exit",false);
//                    if(isClickExit){
//                        String un = (String) SPUtils.get(LoginActivity.this, "WuYeLoginName", "");
//                        userET.setText(un);
//                        return;
//                    }
//                    if (spGet()) {//自动登录
//                        go(goBtn);
//                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            splashIv.startAnimation(splashAnimation);
            // Logo弹出
//            handler.sendEmptyMessageDelayed(0x0001, 1700);
            // 输入框弹出
            handler.sendEmptyMessageDelayed(0x0002, 1800);
            // 登陆按钮弹出
            handler.sendEmptyMessageDelayed(0x0003, 1900);
        } else {
            splashIv.setVisibility(View.GONE);
            // Logo弹出
            handler.sendEmptyMessageDelayed(0x0001, 300);
            // 输入框弹出
            handler.sendEmptyMessageDelayed(0x0002, 400);
            // 登陆按钮弹出
            handler.sendEmptyMessageDelayed(0x0003, 500);
        }

    }

    // 此handler主要用于动画
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.login_logo);
            switch (msg.what) {
                case 0x0001:
                    logoIv.setVisibility(View.VISIBLE);
                    logoIv.startAnimation(animation);
                    break;
                case 0x0002:
                    inputLL.setVisibility(View.VISIBLE);
                    inputLL.startAnimation(animation);
                    break;
                case 0x0003:
                    goBtn.setVisibility(View.VISIBLE);
                    goBtn.startAnimation(animation);
                    use_course.setVisibility(View.VISIBLE);
                    break;
                case FINISH:
                    finish();
                    break;
            }
        }
    };

    // 存入数据库账号密码信息
    private void spStore(HttpRequestBase requestBase) {
        SPUtils.remove(this, Const.SPDate.ID);
        SPUtils.remove(this, Const.SPDate.LONGING_NAME);
        SPUtils.remove(this, Const.SPDate.PASS_WORD);
        SPUtils.remove(this, Const.SPDate.USER_NAME);
        SPUtils.remove(this, Const.SPDate.MAC_ID);
        SPUtils.remove(this, Const.SPDate.MAC_ID_ORIGINAL);//本来的MacId
        SPUtils.remove(this, Const.SPDate.USER_COMPANY);
        SPUtils.remove(this, Const.SPDate.USER_DISTRICT_ID);
        SPUtils.remove(LoginActivity.this, Const.SPDate.LOGIN_AUTO);


        SPUtils.put(this, Const.SPDate.MAC_ID_ORIGINAL, requestBase.user.macId);

        String macId = requestBase.user.macId.replaceAll("-", "");

        SPUtils.put(this, Const.SPDate.MAC_ID, macId);
        //极光推送
        JPushInterface.setAlias(this, macId, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                if (i == 0) {
                    L.i("别名设置成功！");
                } else {
                    L.i("别名设置失败！");
                }
            }
        });
        SPUtils.put(this, Const.SPDate.ID, requestBase.user.id);
        SPUtils.put(this, Const.SPDate.LONGING_NAME, userET.getText().toString());
        SPUtils.put(this, Const.SPDate.PASS_WORD, passwordET.getText().toString());
        SPUtils.put(this, Const.SPDate.USER_NAME, requestBase.user.userName);
        SPUtils.put(this, Const.SPDate.USER_COMPANY, requestBase.user.company);
        SPUtils.put(this, Const.SPDate.USER_DISTRICT_ID, requestBase.user.districtId+"");

//        if (autoLogin) {
        SPUtils.put(LoginActivity.this, Const.SPDate.LOGIN_AUTO, true);
//        } else {
//            SPUtils.put(LoginActivity.this, Const.SPDate.LOGIN_AUTO, false);
//        }
    }

    private boolean spGet() {
        String un = (String) SPUtils.get(this, Const.SPDate.LONGING_NAME, "");
        String pw = (String) SPUtils.get(this, "WuYePassword", "");
        boolean isAuto = (boolean) SPUtils.get(this, "WuYeAutoLogin", false);
        userET.setText(un);
        passwordET.setText(pw);
        if (un != null && pw != null && isAuto) {
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
        splashIv.setVisibility(View.GONE);
        boolean isClickExit = getIntent().getBooleanExtra("click_exit", false);
        if (isClickExit) {
            String un = (String) SPUtils.get(LoginActivity.this, Const.SPDate.LONGING_NAME, "");
            userET.setText(un);
            return;
        }
        if (spGet()) {//自动登录
            go(goBtn);
        }
    }

    /**
     * 使用教程操作
     *
     * @param view
     */
    @Event(value = R.id.use_course)
    private void use_course(View view) {
        Intent intent1 = new Intent();
        intent1.setAction(Intent.ACTION_VIEW);
        intent1.setData(Uri.parse(Const.WuYe.URL_APP_USE_COURSE));
        startActivity(intent1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
}