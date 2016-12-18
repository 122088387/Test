package com.chaungying.site_repairs.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.VideoView;

import com.chaungying.BaseFragment;
import com.chaungying.common.bean.ConfigBean;
import com.chaungying.common.bean.MoreBean;
import com.chaungying.common.bean.MoreSelectBean;
import com.chaungying.common.bean.SubtitlesItem;
import com.chaungying.common.constant.Base;
import com.chaungying.common.constant.Const;
import com.chaungying.common.ui.JiLianActivity1;
import com.chaungying.common.ui.JiLianEditActivity;
import com.chaungying.common.ui.MoreSelectActivity;
import com.chaungying.common.ui.PhotoActivity;
import com.chaungying.common.ui.VideoActivity;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.common.utils.T;
import com.chaungying.common.utils.file.AsyncTaskForUpLoadFiles;
import com.chaungying.common.utils.file.Bimp;
import com.chaungying.common.view.AddView;
import com.chaungying.common.view.BuildView;
import com.chaungying.common.view.CameraView;
import com.chaungying.common.view.DateDialogPopWindow;
import com.chaungying.common.view.DateDialogView;
import com.chaungying.common.view.DateTimeDialogPopWindow;
import com.chaungying.common.view.DateTimeDialogView;
import com.chaungying.common.view.HandDrawView;
import com.chaungying.common.view.HiddenView;
import com.chaungying.common.view.InputMoreView;
import com.chaungying.common.view.InputNumView;
import com.chaungying.common.view.InputPriceView;
import com.chaungying.common.view.InputView;
import com.chaungying.common.view.JiLianEditView;
import com.chaungying.common.view.JiLianView;
import com.chaungying.common.view.LuXiangView;
import com.chaungying.common.view.LuYinView;
import com.chaungying.common.view.MoreSelectView;
import com.chaungying.common.view.SelectAddressView;
import com.chaungying.common.view.SelectTimePopupWindow;
import com.chaungying.common.view.SingleSelectView;
import com.chaungying.common.view.TimeDialogPopWindow;
import com.chaungying.common.view.TimeDialogView;
import com.chaungying.common.view.TimePeriod;
import com.chaungying.common.view.TimeSelectView;
import com.chaungying.common.view.TimeView;
import com.chaungying.gongzuotai.ui.DealWithOrderActivity;
import com.chaungying.jinjitonggao.ui.TimeSelectActivity;
import com.chaungying.jinjitonggao.ui.TongGaoActivity;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.patrols.ui.PatrolsActivity;
import com.chaungying.round_malls1.ui.ShippingAddressActivity;
import com.chaungying.site_repairs.bean.HandDrawSaveBean;
import com.chaungying.wuye3.R;
import com.chaungying.zixunjieda.ui.ZiXunJieDaConfigActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.chaungying.common.ui.JiLianEditActivity.CASE_RESULT;
import static com.chaungying.common.utils.SPUtils.get;

/**
 * @author 王晓赛 or 2016/6/27
 */
@ContentView(R.layout.fragment_repairs)
public class RepairsFragment extends BaseFragment implements View.OnClickListener {

    private Activity mContext;

    @ViewInject(R.id.linear_layout)
    private LinearLayout linearLayout;
    //点击的哪个appId
    private int appId;

    private int states;
    private int grabberId;

    public RepairsFragment() {
    }

    /**
     * 为了报修处理添加的构造方法
     *
     * @param states
     * @param grabberId
     */
    @SuppressLint("ValidFragment")
    public RepairsFragment(int states, int grabberId) {
        this.states = states;
        this.grabberId = grabberId;
    }

    @SuppressLint("ValidFragment")
    public RepairsFragment(int appId) {
        this.appId = appId;
    }

    private TextView ti_jiao;

    private TextView ti_jiao_deal_with;

    //    private String singSelect;//单选过来的文字
    private boolean isHaveSelectAddressView;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CameraView.CAMERA_PIC:
                    //打开相机，并处理图片
                    // 利用系统自带的相机应用:拍照
                    bmp = msg.getData().getParcelableArrayList("bmp");
                    photo();
                    break;
//                case 1:
//                    //获取单选按钮选择了哪一个
//                    singSelect = msg.getData().getString("single");
//                    break;
                case 111:
                    //获取 配置界面的ItemBean集合
                    itemBeanList = (ArrayList<ConfigBean.ItemsBean>) msg.getData().getSerializable("listitem");
                    //判断是否有选择地址这种控件
                    for (int i = 0; i < itemBeanList.size(); i++) {
                        if (itemBeanList.get(i).getItemType() == 158) {
                            isHaveSelectAddressView = true;
                            break;
                        }
                    }
                    break;
                case UPLOAD_ADD_DATA:
                    //对材料明细的上传
                    upLoadAddData();
                    break;
                case UPLOAD_HAND_DRAW:
                    //对涂鸦的上传
                    upLoadSignature();
                    break;
                case UPLOAD_VOICE:
                    //对录音的上传
                    upLoadVoice();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = x.view().inject(this, inflater, container);
        if (getActivity() instanceof PatrolsActivity) {//巡查记录界面
            mContext = (PatrolsActivity) getActivity();
            ti_jiao = ((PatrolsActivity) mContext).getTi_jiao();
        } else if (getActivity() instanceof TongGaoActivity) {
            mContext = (TongGaoActivity) getActivity();
            ti_jiao = ((TongGaoActivity) mContext).getTi_jiao();
        } else if (getActivity() instanceof ZiXunJieDaConfigActivity) {
            mContext = (ZiXunJieDaConfigActivity) getActivity();
            ti_jiao = ((ZiXunJieDaConfigActivity) mContext).getTi_jiao();
        } else if (getActivity() instanceof DealWithOrderActivity) {
            mContext = (DealWithOrderActivity) getActivity();
            ti_jiao_deal_with = ((DealWithOrderActivity) mContext).getTi_jiao();
        }
        if (getActivity() instanceof DealWithOrderActivity) {
            ti_jiao_deal_with.setOnClickListener(upLoadDealWith);
        } else {
            ti_jiao.setOnClickListener(this);
        }
        getData();
        return view;
    }

    //配置界面的json数据
    private String jsonConfig;
    private ArrayList<ConfigBean.ItemsBean> itemBeanList = new ArrayList<>();

    private void getData() {
        final int appId = this.appId;
        ProgressUtil.show(mContext, "加载中...");
        RequestParams params = new RequestParams(Const.WuYe.URL_ON_SITE_REPAIR);
        params.setConnectTimeout(30 * 1000);
        params.addParameter("type", 1);
        if (getActivity() instanceof DealWithOrderActivity) {
//            if (states == 1) {
//                params.addParameter("applicationId", 149);
//            } else if (states == 2) {
//                params.addParameter("applicationId", 134);
//                params.addParameter("grabberId", grabberId);
//            }
            if (grabberId == 0) {//派工回执
                params.addParameter("applicationId", 149);
            } else {//维修回执
                params.addParameter("applicationId", 134);
                params.addParameter("grabberId", grabberId);
            }

            params.addParameter("states", states);
        } else {
            params.addParameter("applicationId", appId);
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                jsonConfig = result;
//                T.showShort(mContext, "请求配置数据成功");
                //请求成功之后配置界面
                initView();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                ProgressUtil.close();
                T.showShort(mContext, "网络较慢，请重试");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    LinearLayout llView = null;
    private MoreSelectView moreSelectView;
    //单行输入的样式集合
    private List<InputView> inputViewList = new ArrayList<InputView>();
    private List<InputPriceView> inputPriceViews = new ArrayList<InputPriceView>();
    private List<InputNumView> inputNumViews = new ArrayList<InputNumView>();


    //多行的集合
    private List<InputMoreView> inputMoreViewList = new ArrayList<InputMoreView>();
    //级联的集合
    ArrayList<JiLianView> jiLianList = new ArrayList<JiLianView>();
    //级联选择的集合
    ArrayList<JiLianEditView> jiLianEditList = new ArrayList<JiLianEditView>();
    //级联选择的集合
    ArrayList<HiddenView> hiddenViewList = new ArrayList<HiddenView>();
    //单选的集合
    ArrayList<SingleSelectView> singleSelectViewList = new ArrayList<SingleSelectView>();
    //时间选择集合
    ArrayList<TimeSelectView> timeSelectViewList = new ArrayList<TimeSelectView>();
    //日期
    ArrayList<DateDialogView> dateDialogViewList = new ArrayList<DateDialogView>();
    //时间
    ArrayList<TimeDialogView> timeDialogViewList = new ArrayList<TimeDialogView>();
    //日期和时间
    ArrayList<DateTimeDialogView> dateTimeDialogViewList = new ArrayList<DateTimeDialogView>();
    //涂鸦
    ArrayList<HandDrawView> handDrawViewList = new ArrayList<HandDrawView>();

    SelectAddressView selectAddressView;

    //手动添加视图
    AddView addView;
    //时间段选择
    TimePeriod timePeriod;

    private void initView() {
        if (jsonConfig != null) {
            BuildView view = new BuildView(linearLayout, mContext);
            //根据json配置界面
            view.setHandler(handler);
            llView = view.buildView(jsonConfig, true);
//            ti_jiao.setText(view.submitTitle);
        }
        //向根据不同的类型强转对应的View ，并且对需要整个点击处理的进行监听
        for (int i = 0; i < llView.getChildCount(); i++) {
            LinearLayout v = (LinearLayout) llView.getChildAt(i);
//            Log.e("geshu", v.getChildCount() + "");//包括灰色的线
            for (int j = 0; j < v.getChildCount(); j++) {
                if (v.getChildAt(j) instanceof InputMoreView) {
                    InputMoreView inputMoreView = ((InputMoreView) v.getChildAt(j));
                    inputMoreViewList.add(inputMoreView);
                } else if (v.getChildAt(j) instanceof InputView) {
                    InputView inputView = ((InputView) v.getChildAt(j));
                    inputViewList.add(inputView);
                } else if (v.getChildAt(j) instanceof InputPriceView) {
                    InputPriceView inputPriceView = ((InputPriceView) v.getChildAt(j));
                    inputPriceViews.add(inputPriceView);
                } else if (v.getChildAt(j) instanceof InputNumView) {
                    InputNumView inputNumView = ((InputNumView) v.getChildAt(j));
                    inputNumViews.add(inputNumView);
                } else if (v.getChildAt(j) instanceof MoreSelectView) {
                    moreSelectView = (MoreSelectView) v.getChildAt(j);
                    moreSelectView.setOnClickListener(this);
                } else if (v.getChildAt(j) instanceof HandDrawView) {
                    HandDrawView handDrawView = (HandDrawView) v.getChildAt(j);
                    handDrawViewList.add(handDrawView);
                    handDrawView.setOnSendHandDrawPathListener(onSendHandDrawPathListener);
                } else if (v.getChildAt(j) instanceof CameraView) {
                    CameraView cameraView = (CameraView) v.getChildAt(j);
                    cameraView.setHandler(handler);
                    cameraViewVar = cameraView;
                } else if (v.getChildAt(j) instanceof LuXiangView) {
                    LuXiangView luxiangView = (LuXiangView) v.getChildAt(j);
                    this.luXiangView = luxiangView;
                    wVideoView = luxiangView.getFirst_videoView();
                    this.luXiangView.setOnLuXiangListener(luxiangClickListener);
                } else if (v.getChildAt(j) instanceof LuYinView) {
                    LuYinView luyinView = (LuYinView) v.getChildAt(j);
                    this.luyinView = luyinView;
                } else if (v.getChildAt(j) instanceof TimeView) {
                    TimeView timeView = (TimeView) v.getChildAt(j);
                    timeView.setOnClickListener(this);
                } else if (v.getChildAt(j) instanceof JiLianView) {
                    JiLianView jiLianView = (JiLianView) v.getChildAt(j);
                    jiLianView.setOnClickListener(this);
                    jiLianList.add(jiLianView);
                } else if (v.getChildAt(j) instanceof JiLianEditView) {
                    JiLianEditView jiLianEditView = (JiLianEditView) v.getChildAt(j);
                    jiLianEditView.setOnClickListener(this);
                    jiLianEditList.add(jiLianEditView);
                } else if (v.getChildAt(j) instanceof SingleSelectView) {
                    SingleSelectView singleSelectView = (SingleSelectView) v.getChildAt(j);
                    singleSelectView.setOnClickListener(this);
                    singleSelectViewList.add(singleSelectView);
                } else if (v.getChildAt(j) instanceof TimeSelectView) {
                    TimeSelectView timeSelectView = (TimeSelectView) v.getChildAt(j);
                    timeSelectView.setOnClickListener(this);
                    timeSelectViewList.add(timeSelectView);
                } else if (v.getChildAt(j) instanceof DateDialogView) {
                    DateDialogView dateDialogView = (DateDialogView) v.getChildAt(j);
                    dateDialogView.setOnClickListener(this);
                    dateDialogViewList.add(dateDialogView);
                } else if (v.getChildAt(j) instanceof TimeDialogView) {
                    TimeDialogView timeDialogView = (TimeDialogView) v.getChildAt(j);
                    timeDialogView.setOnClickListener(this);
                    timeDialogViewList.add(timeDialogView);
                } else if (v.getChildAt(j) instanceof DateTimeDialogView) {
                    DateTimeDialogView dateTimeDialogView = (DateTimeDialogView) v.getChildAt(j);
                    dateTimeDialogView.setOnClickListener(this);
                    dateTimeDialogViewList.add(dateTimeDialogView);
                } else if (v.getChildAt(j) instanceof AddView) {
                    addView = (AddView) v.getChildAt(j);
                } else if (v.getChildAt(j) instanceof TimePeriod) {
                    timePeriod = (TimePeriod) v.getChildAt(j);
                } else if (v.getChildAt(j) instanceof HiddenView) {
                    HiddenView hiddenView = (HiddenView) v.getChildAt(j);
                    hiddenViewList.add(hiddenView);
                } else if (v.getChildAt(j) instanceof SelectAddressView) {
                    selectAddressView = (SelectAddressView) v.getChildAt(j);
                    selectAddressView.setOnClickListener(this);
                }
            }
        }
    }

    /////////////////////////对录音的功能实现////////////////////////////

    LuYinView luyinView;//录音的控件
    File recAudioFile = null;//录音文件的路径

    /////////////////////////对录像的功能实现////////////////////////////
    /**
     * 录像的整个View视图
     */
    private LuXiangView luXiangView;
    /**
     * 录像之后保存的地址
     */
    private String vedioPath;
    private static final int CASE_VIDEO = 10;
    /**
     * 录像的返回码
     */
    private final int REQUSTCODE = 1;

    private VideoView wVideoView;
    View.OnClickListener luxiangClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_luxiang:
                    Intent intentVideo = new Intent(mContext, VideoActivity.class);
                    startActivityForResult(intentVideo, REQUSTCODE);
                    break;
                case R.id.iv_show_luxiang:
                    // 视频播放
                    palyVideo(vedioPath);
                    luXiangView.setSuoLueTuVisible(View.GONE);
                    luXiangView.setVideoViewVisible(View.VISIBLE);
                    break;
            }
        }
    };

    // 视频播放
    private void palyVideo(String path) {
        if (path != null && path.trim().length() != 0) {
//            wVideoView.setVisibility(View.VISIBLE);
            wVideoView.setVideoPath(path);
            wVideoView.start();
            wVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    mp.setLooping(false);//不重复播放
                }
            });
            //对视频播放完毕之后的监听
            wVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //设置为缩略图形式
                    luXiangView.setVideoViewVisible(View.GONE);
                    luXiangView.setSuoLueTuVisible(View.VISIBLE);
                }
            });
        }
    }

    /////////////////////////对相机界面的控制////////////////////////////
    CameraView cameraViewVar;
    //    //存储所有图片的数组
    private ArrayList<Bitmap> bitmapList = new ArrayList<Bitmap>();
    //
    public List<String> drr = new ArrayList<String>();
    public List<Bitmap> bmp = new ArrayList<Bitmap>();
    private static final int TAKE_PICTURE = 0;
    private Uri photoUri;

    /**
     * 相机拍照
     */
    public void photo() {
        try {
            //设置启动相机意图
            Intent openCameraIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);

            String sdcardState = Environment.getExternalStorageState();
            String sdcardPathDir = Const.SAVE_MEDAR.PIC_PATH;
            File file = null;
            if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
                // 有sd卡，是否有myImage文件夹
                File fileDir = new File(sdcardPathDir);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                // 是否有headImg文件
                file = new File(sdcardPathDir, System.currentTimeMillis() + ".JPG");
            }
            if (file != null) {
                photoUri = Uri.fromFile(file);
                if (!file.exists()) {
                    file.createNewFile();
                }
                drr.add(photoUri.getPath());
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(openCameraIntent, TAKE_PICTURE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //////////////////////////////////////////////对涂鸦界面的控制/////////////////////////////////
    // 存放所有涂鸦文件的路径
    private List<HandDrawSaveBean> handDrawableList = new ArrayList<HandDrawSaveBean>();
    // 使用map存储回调过来的路径，当key值一样，value自动覆盖
    private Map<Integer, HandDrawSaveBean> handDrawableMap = new HashMap<Integer, HandDrawSaveBean>();

    //涂鸦的回调
    HandDrawView.OnSendHandDrawPathListener onSendHandDrawPathListener = new HandDrawView.OnSendHandDrawPathListener() {
        @Override
        public void sendHandDrawPathListener(int orders, String handDrawPath, String handUrl) {
            HandDrawSaveBean bean = new HandDrawSaveBean();
            bean.setKey(orders);
            bean.setPath(handDrawPath);
            bean.setFieldName(handUrl);
            handDrawableMap.put(orders, bean);
        }

    };
    //////////////////////////////////////////////对时间的控制/////////////////////////////////
    //自定义的时间弹出框
    SelectTimePopupWindow menuWindow;

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mContext.getWindow().setAttributes(lp);
    }


    /**
     * 在本Activity中点击自定义View中按钮
     */
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_ok:
//                    ToastShow.toastShow(BaoXiuDanActivity.this,"能监听到");
                    break;
//                case R.id.btn_pick_photo:
//                    break;
                default:
                    break;
            }
        }

    };


    /////////////////////对多选控制的变量///////////////////////////
    //多项选择的activity result值
    private static final int MORE_SELECT = 100;

    ArrayList<MoreSelectBean> selectList = new ArrayList<MoreSelectBean>();
    private boolean isFirst = true;
    String districtIdVar = "";
    JiLianView jiLianView = null;
    JiLianEditView jiLianEditView = null;

    @Override
    public void onClick(View v) {
        if (v instanceof MoreSelectView) {
//            moreSelectView1
            final Bundle bundle = new Bundle();
            if (isFirst) {//第一次进入多选界面
                String url = moreSelectView.getMoreSelectUrl();
                RequestParams params = new RequestParams(url);
                params.setConnectTimeout(30 * 1000);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        //对多选中获取的json进行工作
                        Type type = new TypeToken<ArrayList<MoreBean>>() {
                        }.getType();
                        Gson gson = new Gson();
                        ArrayList<MoreBean> moreBeanList = gson.fromJson(result, type);
                        for (int i = 0; i < moreBeanList.size(); i++) {
                            MoreSelectBean bean = new MoreSelectBean();
                            bean.setInfo(moreBeanList.get(i).getName());
                            bean.setChecked(false);
                            selectList.add(bean);
                        }
                        bundle.putSerializable("subtitles", selectList);
                        openActivtyForResult(getActivity(), MoreSelectActivity.class, bundle, null, MORE_SELECT);
                        isFirst = !isFirst;
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
            } else {
                bundle.putSerializable("subtitles", selectList);
                openActivtyForResult(mContext, MoreSelectActivity.class, bundle, null, MORE_SELECT);
            }
        } else if (v instanceof TimeView) {//自定义的时间弹出框（目前没有用到，不予考虑）
            final TimeView timeView = ((TimeView) v);
            String time = timeView.getTvText().toString();
            Log.e("time11", time);
            timeView.setTvText(time.substring(0, time.indexOf(" ") + 1));
//                ToastShow.toastShow(this, "得到了id");
            //自定义的弹出框类
            menuWindow = new SelectTimePopupWindow(mContext, itemsOnClick);
            menuWindow.setListnerSenStr(new SelectTimePopupWindow.SendStrListner() {
                @Override
                public void sendToActivityStr(String string) {
//                    T.showShort(mContext, string);
                    timeView.setTvText(timeView.getTvText() + " " + string);
                    //将时间字符串给了全局变量
                    timeString = string;
                }
            });
            backgroundAlpha(0.5f);
            //显示窗口
            menuWindow.showAtLocation(mContext.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
            menuWindow.setOnDismissListener(new poponDismissListener());
        } else if (v instanceof JiLianView) {
            jiLianView = ((JiLianView) v);
            for (int i = 0; i < jiLianList.size(); i++) {
                if (jiLianView == jiLianList.get(i)) {
                    String louyuUrl = "";
                    String fidleName = "";
                    //选择小区的时候分情况  用依赖上一级
                    if (jiLianView.getDependency() != null && !jiLianView.getDependency().equals("")) {
                        Integer integer = jiLianView.getDependency();
                        View view = ((LinearLayout) llView.getChildAt(0)).findViewById(integer);
                        if (view instanceof HiddenView) {
                            fidleName = ((HiddenView) view).getHiddenKey();
                            districtIdVar = (String) SPUtils.get(mContext, fidleName, "");
                        } else if (view instanceof JiLianView) {
                            fidleName = ((JiLianView) view).getFieldname();
                            //当没有选择上一级的时候，下边是不让选择的
                            if (((JiLianView) view).getTv_show() == null || ((JiLianView) view).getTv_show().getText().toString().equals("")) {
                                T.showShort(mContext, "请选择：" + ((JiLianView) view).getTvText());
                                return;
                            }
                        }
                        //将接口和参数提交到服务器
                        louyuUrl = jiLianView.getjiLianUrl() + "?" + fidleName + "=" + districtIdVar;
                    } else {//选择小区的时候分情况  不用依赖上一级
                        if (jiLianView.getjiLianUrl() != null && !"".equals(jiLianView.getjiLianUrl())) {
                            louyuUrl = jiLianView.getjiLianUrl();
                        }
                    }
                    // 点击本级联时 它的下级全部置空
                    for (int j = i + 1; j < jiLianList.size(); j++) {
                        //判断是否有依赖项 有依赖项的情况下 置空
                        if (jiLianList.get(j).getDependency() != null && !jiLianList.get(j).getDependency().equals("")) {
                            jiLianList.get(j).setTv_show("");
                            jiLianList.get(j).setTv_show_id("");
                        }
                    }
                    Bundle bundle = new Bundle();
                    //将展示的文字传入到下一个界面
                    if (!"".equals(jiLianView.getTv_show().getText())) {
                        bundle.putString("show", jiLianView.getTv_show().getText().toString());
                    }
                    bundle.putString("title", jiLianView.getTvText());
                    bundle.putString("louyu", louyuUrl);
                    bundle.putInt("appId", appId);
                    openActivtyForResult(getActivity(), JiLianActivity1.class, bundle, JI_LIAN_RESULT);
                    break;
                }
            }
        } else if (v instanceof JiLianEditView) {//选择性的级联，选择后返回数据到EditText中
            jiLianEditView = (JiLianEditView) v;
            Bundle bundle = new Bundle();
            //将展示的文字传入到下一个界面
            if (!"".equals(jiLianEditView.getEt_input())) {
                bundle.putString("show", jiLianEditView.getEt_input());
            }
            bundle.putString("title", jiLianEditView.getTitle());
            bundle.putString("url", jiLianEditView.getUrl());
            openActivtyForResult(getActivity(), JiLianEditActivity.class, bundle, JI_LIAN_EDIT_RESULT);
        } else if (v instanceof TimeSelectView) {//时间选择
            TimeSelectView timeSelectView = (TimeSelectView) v;
            ArrayList<SubtitlesItem> list = (ArrayList<SubtitlesItem>) timeSelectView.getList();
            Bundle bundle = new Bundle();
            bundle.putString("louyu", "时间的url");
            bundle.putSerializable("time_list", list);
            openActivtyForResult(getActivity(), TimeSelectActivity.class, bundle, TIME_RESULT);
        } else if (v instanceof DateDialogView) {//日期对话框
            final DateDialogView dateDialogView = (DateDialogView) v;
            DateDialogPopWindow timePop = new DateDialogPopWindow(mContext);
            timePop.showAtLocation(mContext.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            timePop.sendStrListner = new DateDialogPopWindow.SendStrListner() {
                @Override
                public void sendToActivityStr(String string) {
                    dateDialogView.setTimeShow(string);
                }
            };
        } else if (v instanceof TimeDialogView) {//时间对话框
            final TimeDialogView timeDialogView = (TimeDialogView) v;
            //创建时间弹出框
            TimeDialogPopWindow timePop = new TimeDialogPopWindow(mContext);
            //设置分钟的间隔
            timePop.setMinuts_interval(1);
            //初始化时间选择器的属性，设置初始化的时间，是否循环，能否取消
            timePop.initTimePicker();
            timePop.showAtLocation(mContext.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            timePop.sendStrListner = new TimeDialogPopWindow.SendStrListner() {
                @Override
                public void sendToActivityStr(String string) {
                    timeDialogView.setTimeShow(string);
                }
            };
        } else if (v instanceof DateTimeDialogView) {//日期时间对话框
            final DateTimeDialogView dateTimeDialogView = (DateTimeDialogView) v;
            DateTimeDialogPopWindow timePop = new DateTimeDialogPopWindow(mContext);
            timePop.showAtLocation(mContext.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            timePop.sendStrListner = new DateTimeDialogPopWindow.SendStrListner() {
                @Override
                public void sendToActivityStr(String string) {
                    dateTimeDialogView.setTimeShow(string);
                }
            };
        } else if (v instanceof SelectAddressView) {//订餐中选择地址
            Bundle bundle = new Bundle();
            bundle.putString("url", selectAddressView.getUrl());
            openActivtyForResult(mContext, ShippingAddressActivity.class, bundle, SELECT_ADDRESS_RETURN);
        } else if (v.getId() == R.id.ti_jiao) {//提交按钮
            String url = Const.WuYe.URL_SAVE_APPLICATION_PAGE;
            uploadBaseText(url);
        }
    }

    /**
     * 时间的弹出框监听事件（目前不用考虑，没用到）
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }

    }

    /**
     * 提交按钮点击事件
     */
    private View.OnClickListener upLoadDealWith = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            uploadBaseText(Const.WuYe.URL_OREDER_DEAL_WITH_UPLOAD);
        }
    };

    //级联
    private static final int JI_LIAN_RESULT = 5;

    //选择性级联
    private static final int JI_LIAN_EDIT_RESULT = 7;

    //选择地址之后回调处理
    private static final int SELECT_ADDRESS_RETURN = 8;


    //时间
    private static final int TIME_RESULT = 6;

    /**
     * 对返回到改界面时，根据不同的返回值，做相应的处理
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MORE_SELECT://多项选择的返回
                if (data != null && resultCode == 10) {
                    selectList = (ArrayList<MoreSelectBean>) data.getExtras().getSerializable("subtitles1");
                    int checkedNum = 0;
                    List<String> listInfo = new ArrayList<String>();
                    for (int i = 0; i < selectList.size(); i++) {
                        if (selectList.get(i).isChecked()) {
                            checkedNum++;
                            listInfo.add(selectList.get(i).getInfo());
                        }
                    }
                    String info = listInfo.toString();
                    moreSelectView.setTv_show(info.substring(1, info.indexOf("]")));
                }
                break;
            case TAKE_PICTURE:// 拍照
//                if (drr.size() < 6 && resultCode == -1) {// 拍照 对图片的限制为五张，目前无限制
                if (resultCode == -1) {
                    //对图片处理之后放入到内存卡中
                    Bitmap bitmap1 = Bimp.revitionImageSize(drr.get(drr.size() - 1));
                    PhotoActivity.bitmap.add(bitmap1);
                    //将图片切为圆角的
                    Bitmap bitmap2 = Bimp.createFramedPhoto(480, 480, bitmap1, 0);
                    bmp.add(bitmap2);
                    cameraViewVar.gridviewInit(drr);
                }
                break;
            case REQUSTCODE://录像
                if (resultCode == -1) {
                    vedioPath = data.getStringExtra("path");
                    //获取视频的第一帧缩略图
                    Bitmap bitmapFirst = ThumbnailUtils.createVideoThumbnail(vedioPath, MediaStore.Video.Thumbnails.MICRO_KIND);
                    bitmapFirst = ThumbnailUtils.extractThumbnail(bitmapFirst, 96, 96);
                    luXiangView.setSuoLueTuVisible(View.VISIBLE);
                    luXiangView.setSuoLueTu(bitmapFirst);
                }
                break;
            case JI_LIAN_RESULT://级联
                if (resultCode == 5) {
                    String select = data.getExtras().getString("select");
                    int districtId = data.getExtras().getInt("val");
                    districtIdVar = districtId + "";
                    jiLianView.setTv_show(select);
                    jiLianView.setTv_show_id(districtId + "");
                }
                break;
            case JI_LIAN_EDIT_RESULT://选择返回数据级联
                if (resultCode == CASE_RESULT) {
                    String select = data.getExtras().getString("select");
                    jiLianEditView.setEt_input(select);
                }
                break;
            case SELECT_ADDRESS_RETURN://订餐中选择地址后返回的数据
                if (resultCode == ShippingAddressActivity.CASE_RESULT) {
                    selectAddressName = data.getExtras().getString("name");
                    selectAddressPhone = data.getExtras().getString("phone");
                    selectAddress_ = data.getExtras().getString("address");
                    selectAddressView.setShow(selectAddress_);
                }
                break;
        }
    }

    private String selectAddressName;
    private String selectAddressPhone;
    private String selectAddress_;

    ///////////////////////////////////////////////////对基础数据进行上传//////////////////////////////
    private String timeString;
    private int logicId = 0;
    List<HashMap<String, String>> mapList = new ArrayList<HashMap<String, String>>();

    private void uploadBaseText(String tiJiaoUrl) {//提交的接口
        //////////////////目前不用考虑的/////////////////////
        //对多选的选项的值
//        String moreSelect = selectList.toString();
//        params.addBodyParameter("type","多项选择");
//        params.addBodyParameter("repairTime", timeString);//时间
        ProgressUtil.show(mContext, "提交中...");
        RequestParams params = new RequestParams(tiJiaoUrl);
        params.setConnectTimeout(30 * 1000);
        if (mContext instanceof DealWithOrderActivity) {
//            if (states == 1) {//派工回执
//                params.addParameter("appId", 149);
//            } else if (states == 2) {//维修回执
//                params.addParameter("appId", 134);
//            }
            if (grabberId == 0) {//派工回执
                params.addParameter("appId", 149);
            } else {//维修回执
                params.addParameter("appId", 134);
            }
            params.addParameter("logicId", DealWithOrderActivity.logicId);
        } else {
            params.addParameter("appId", itemBeanList.get(0).getApplicationId());
        }
        for (int i = 0; i < itemBeanList.size(); i++) {//判断每个item所属哪个类型
            ConfigBean.ItemsBean item = itemBeanList.get(i);
            //根据数据的类型不同 ，从而根据View的样式提交数据
            switch (item.getItemType()) {
                case 162://隐藏控件 中的内容
                    Log.e("aa", item.getItemType() + "");
                    for (int j = 0; j < hiddenViewList.size(); j++) {
                        HiddenView hiddenView = hiddenViewList.get(j);
                        if (hiddenView.getId() == item.getId()) {
                            if (hiddenView.getFieldName().equals("memberName")) {
                                params.addParameter("memberName", selectAddressName);
                                break;
                            } else if (hiddenView.getFieldName().equals("phone")) {
                                if (isHaveSelectAddressView) {
                                    params.addParameter("phone", selectAddressPhone);
                                    break;
                                }
                            } else if (hiddenView.getFieldName().equals("sendAddress")) {
                                params.addParameter("sendAddress", selectAddress_);
                                break;
                            }
                            params.addParameter(hiddenView.getFieldName(), SPUtils.get(mContext, hiddenView.getHiddenKey(), ""));
                        }
                    }
                    break;
                case 4://单行数入
                    for (int j = 0; j < inputViewList.size(); j++) {
                        InputView inputView = inputViewList.get(j);
                        if (inputView.getId() == item.getId()) {
                            if (inputView.getIsRequired() == 0 && inputView.getInput().equals("")) {//如果等于0
                                T.showShort(mContext, item.getTitle() + "：为必填项");
                                ProgressUtil.close();
                                return;
                            }
                            params.addBodyParameter(item.getFieldname(), inputView.getInput());//上传服务器的key

                        }
                    }
                    break;
                case 125://单行数入(数量)
                    for (int j = 0; j < inputNumViews.size(); j++) {
                        InputNumView inputNumView = inputNumViews.get(j);
                        if (inputNumView.getId() == item.getId()) {
                            if (inputNumView.getIsRequired() == 0 && inputNumView.getInput().equals("")) {//如果等于0
                                T.showShort(mContext, item.getTitle() + "：为必填项");
                                ProgressUtil.close();
                                return;
                            }
                            params.addBodyParameter(item.getFieldname(), inputNumView.getInput());//上传服务器的key
                        }
                    }
                    break;
                case 126://单行数入(价格)
                    for (int j = 0; j < inputPriceViews.size(); j++) {
                        InputPriceView inputPriceView = inputPriceViews.get(j);
                        if (inputPriceView.getId() == item.getId()) {
                            if (inputPriceView.getIsRequired() == 0 && inputPriceView.getInput().equals("")) {//如果等于0
                                T.showShort(mContext, item.getTitle() + "：为必填项");
                                ProgressUtil.close();
                                return;
                            }
                            params.addBodyParameter(item.getFieldname(), inputPriceView.getInput());//上传服务器的key
                        }
                    }
                    break;
                case 3://多行输入
                    for (int j = 0; j < inputMoreViewList.size(); j++) {
                        InputMoreView inputMoreView = inputMoreViewList.get(j);
                        if (inputMoreView.getId() == item.getId()) {
                            if (inputMoreView.getIsRequired() == 0 && inputMoreView.getInput().equals("")) {//如果等于0
                                T.showShort(mContext, item.getTitle() + "：为必填项");
                                ProgressUtil.close();
                                return;
                            }
                            params.addBodyParameter(item.getFieldname(), inputMoreView.getInput());//多行
                        }
                    }
                    break;
                case 2://级联数据的提交
                    for (int j = 0; j < jiLianList.size(); j++) {
                        JiLianView jiLianView = jiLianList.get(j);
                        if (jiLianView.getId() == item.getId()) {
                            if (jiLianView.getIsRequired() == 0 && jiLianView.getTv_show().getText().toString().equals("")) {//如果等于空
                                T.showShort(mContext, item.getTitle() + "：为必填项");
                                ProgressUtil.close();
                                return;
                            }
                            params.addParameter(item.getFieldname(), jiLianView.getTv_show_id());//多行
                        }
                    }
                    break;
                case 152://选择级联数据（选择完成之后，将内容放到输入框中）
                    for (int j = 0; j < jiLianEditList.size(); j++) {
                        JiLianEditView jiLianEditView1 = jiLianEditList.get(j);
                        if (jiLianEditView1.getId() == item.getId()) {
                            if (jiLianEditView1.getIsRequired() == 0 && jiLianEditView1.getEt_input().equals("")) {//如果等于空
                                T.showShort(mContext, item.getTitle() + "：为必填项");
                                ProgressUtil.close();
                                return;
                            }
                            params.addParameter(item.getFieldname(), jiLianEditView1.getEt_input());//多行
                        }
                    }
                    break;
                case 1://单选
                    for (int j = 0; j < singleSelectViewList.size(); j++) {
                        SingleSelectView singleSelectView = singleSelectViewList.get(j);
                        if (singleSelectView.getId() == item.getId()) {
                            if (singleSelectView.getIsRequired() == 0 && singleSelectView.getSingle_tv().equals("")) {//如果等于空
                                T.showShort(mContext, item.getTitle() + "：为必填项");
                                ProgressUtil.close();
                                return;
                            }
                            params.addBodyParameter(item.getFieldname(), singleSelectView.getSingle_tv());
                        }
                    }
                    break;
                case 36://日期对话框
                    for (int j = 0; j < dateDialogViewList.size(); j++) {
                        DateDialogView dateDialogView = dateDialogViewList.get(j);
                        if (dateDialogView.getId() == item.getId()) {
                            if (dateDialogView.getIsRequired() == 0 && dateDialogView.getTimeShow().equals("")) {//如果等于空
                                T.showShort(mContext, item.getTitle() + "：为必填项");
                                ProgressUtil.close();
                                return;
                            }
                            params.addBodyParameter(item.getFieldname(), dateDialogView.getTimeShow());
                        }
                    }
                    break;
                case 37://时间对话框
                    for (int j = 0; j < timeDialogViewList.size(); j++) {
                        TimeDialogView timeDialogView = timeDialogViewList.get(j);
                        if (timeDialogView.getId() == item.getId()) {
                            if (timeDialogView.getIsRequired() == 0 && timeDialogView.getTimeShow().equals("")) {//如果等于空
                                T.showShort(mContext, item.getTitle() + "：为必填项");
                                ProgressUtil.close();
                                return;
                            }
                            params.addBodyParameter(item.getFieldname(), timeDialogView.getTimeShow());
                        }
                    }
                    break;
                case 155://日期时间对话框
                    for (int j = 0; j < dateTimeDialogViewList.size(); j++) {
                        DateTimeDialogView dateTimeDialogView = dateTimeDialogViewList.get(j);
                        if (dateTimeDialogView.getId() == item.getId()) {
                            if (dateTimeDialogView.getIsRequired() == 0 && dateTimeDialogView.getTimeShow().equals("")) {//如果等于空
                                T.showShort(mContext, item.getTitle() + "：为必填项");
                                ProgressUtil.close();
                                return;
                            }
                            params.addBodyParameter(item.getFieldname(), dateTimeDialogView.getTimeShow());
                        }
                    }
                    break;
                case 31:  //添加明细
                    mapList = addView.getUpLoadMapList();
                    if (addView.getIsRequired() == 0 && mapList.size() == 0) {//如果等于0
                        T.showShort(mContext, item.getTitle() + "：为必填项");
                        ProgressUtil.close();
                        return;
                    }
                    break;
                case 83://时间段
                    if (timePeriod.checkTime()) {
                        String time = timePeriod.getTime();
                        params.addBodyParameter(item.getFieldname(), time);
                    } else {
                        T.showShort(mContext, "时间格式不正确");
                        ProgressUtil.close();
                        return;
                    }
                    break;
                case 17://签字
                    for (int j = 0; j < handDrawViewList.size(); j++) {
                        HandDrawView handDrawView = handDrawViewList.get(j);
                        if (handDrawView.getId() == item.getId()) {
                            if (handDrawView.getIsRequired() == 0 && !handDrawView.isSign()) {//如果等于空
                                T.showShort(mContext, "带 * 的为必签字项");
                                ProgressUtil.close();
                                return;
                            }
                        }
                    }
                    break;
                case 158://选择地址控件
                    if (selectAddressView.getIsRequired() == 0 && selectAddressView.getShow().equals("")) {
                        T.showShort(mContext, item.getTitle() + "：为必填项");
                        params.addBodyParameter(item.getFieldname(), selectAddressView.getShow());
                    }
                    break;
            }
        }

        params.addParameter("userId", get(mContext, Const.SPDate.ID, -1));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Base base = gson.fromJson(result, Base.class);
                if (base.respCode == 1001) {
                    logicId = base.logicId;
                    //对明细的提交
                    if (mapList.size() > 0) {
                        handler.sendEmptyMessage(UPLOAD_ADD_DATA);
                    }
                    //对涂鸦的提交
                    if (handDrawableMap.size() > 0) {
                        Set<Integer> set = handDrawableMap.keySet();
                        Iterator iterator = set.iterator();
                        while (iterator.hasNext()) {
                            int key = (int) iterator.next();
                            HandDrawSaveBean bean = handDrawableMap.get(key);
                            handDrawableList.add(bean);
                        }
                        Collections.sort(handDrawableList);
                        handler.sendEmptyMessage(UPLOAD_HAND_DRAW);
                    }
                    //对图片的提交
                    if (drr.size() > 0) {
                        setData(drr, logicId);
                    }
                    //获取录音文件
                    if (luyinView != null) {
                        recAudioFile = luyinView.getRecAudioFile();
                        if (recAudioFile != null) {
                            handler.sendEmptyMessage(UPLOAD_VOICE);
                        }
                    }
                    T.showLong(mContext, "数据提交：" + base.respMsg);
//                    关闭此界面，返回上一个界面
                    mContext.finish();
                    ProgressUtil.close();
                } else {
                    T.showLong(mContext, base.respMsg);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.showLong(mContext, "提交数据失败！");
                ex.printStackTrace();
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
     * 明细上传
     */
    private static final int UPLOAD_ADD_DATA = 0x11111111;
    /**
     * 签字上传
     */
    private static final int UPLOAD_HAND_DRAW = 0x11111112;
    /**
     * 声音上传
     */
    private static final int UPLOAD_VOICE = 0x11111113;
    /**
     * 照片上传
     */
    private static final int PULODATE_HEAD = 11;

    //控制提交材料明细第几次
    private int upLoadAddNum = 0;

    /**
     * 对材料明细添加提交
     */
    private void upLoadAddData() {
        if (upLoadAddNum < mapList.size()) {
            HashMap<String, String> map = mapList.get(upLoadAddNum);
            RequestParams params = new RequestParams(Const.WuYe.URL_CAI_LIAO_DETAILS);
            params.addParameter("logicId", logicId);
            params.addParameter("appId", itemBeanList.get(0).getApplicationId());

            Set<String> set = map.keySet();
            Iterator<String> iterator = set.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                params.addParameter(key, map.get(key));
            }
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.e("明细提交", result);
                    Gson gson = new Gson();
                    Base base = gson.fromJson(result, Base.class);
                    if (base.respCode == 1001) {
                        upLoadAddNum++;
                        handler.sendEmptyMessage(UPLOAD_ADD_DATA);
                    } else {
                        handler.sendEmptyMessage(UPLOAD_ADD_DATA);
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
        } else {
            //重置为0
            upLoadAddNum = 0;
        }
    }

    /**
     * 保存方法
     */
    public String saveBitmap(Bitmap bm, String picName) {
        //保存到临时文件夹
        File f = new File(Const.SAVE_MEDAR.PIC_PATH + "cache", picName + ".JPG");
        f.mkdirs();
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f.getPath();
    }

    /**
     * 照片图片的上传数据设置
     *
     * @param path
     */
    private void setData(List<String> path, int logicId) {
        Map<String, File> files = new HashMap<String, File>();
        Map<String, File> files1 = new HashMap<String, File>();
        for (int i = 0; i < path.size(); i++) {

            Bitmap bitmap1 = Bimp.revitionImageSize(path.get(i));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHssmm");
            String pathSD = saveBitmap(bitmap1, sdf.format(new Date()));
            files1.put(path.get(i), new File(pathSD));
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("logicId", logicId + "");
        params.put("appId", itemBeanList.get(0).getApplicationId() + "");
        params.put("fieldName", cameraViewVar.getFiledName());

        new AsyncTaskForUpLoadFiles(
                Const.WuYe.URL_UP_LOAD_IMAGES
                , params, files1, "images", handler.obtainMessage(PULODATE_HEAD)).execute();
    }


    //上传签字图片个数的 控制变量
    private int upLoadDrawNum = 0;

    /**
     * 上传签字  涂鸦
     */
    private void upLoadSignature() {
        if (upLoadDrawNum < handDrawableList.size()) {
            RequestParams params = new RequestParams(Const.WuYe.URL_UPLOAD_SINGNATURE);
            params.addBodyParameter("signature", new File(handDrawableList.get(upLoadDrawNum).getPath()));
            params.addParameter("logicId", logicId);
            params.addParameter("appId", itemBeanList.get(0).getApplicationId());
            params.addParameter("fieldName", handDrawableList.get(upLoadDrawNum).getFieldName());
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Gson gson = new Gson();
                    Base base = gson.fromJson(result, Base.class);
                    if (base.respCode == 1001) {
                        handler.sendEmptyMessage(UPLOAD_HAND_DRAW);
                        upLoadDrawNum++;
                    } else {
                        handler.sendEmptyMessage(UPLOAD_HAND_DRAW);
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
        } else {
            upLoadDrawNum = 0;
        }
        //上传签字的第二种方式
//        if (upLoadDrawNum < handDrawableList.size()) {
//            Map<String, String> params = new HashMap<String, String>();
//            params.put("logicId", logicId+"");
//            params.put("appId", itemBeanList.get(0).getApplicationId()+"");
//            params.put("fieldName", "signature");
//
//            Map<String, File> files = new HashMap<String, File>();
//            files.put(handDrawableList.get(upLoadDrawNum),new File(handDrawableList.get(upLoadDrawNum)));
//
//            new AsyncTaskForUpLoadFiles("http://192.168.1.122:80/propertyInterface/repair/uploadSignature.action",
//                    params,
//                    files,
//                   "signature",
//                    handler.obtainMessage(PULODATE_HEAD)).execute();
//        }

    }

    /**
     * 上传录音
     */
    public void upLoadVoice() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("logicId", logicId + "");
        params.put("appId", itemBeanList.get(0).getApplicationId() + "");
        params.put("fieldName", luyinView.getFieldName());

        Map<String, File> files = new HashMap<String, File>();
        files.put(recAudioFile.getPath(), recAudioFile);

        new AsyncTaskForUpLoadFiles(Const.WuYe.URL_UPLOAD_VOICE,
                params,
                files,
                "voices",
                handler.obtainMessage(PULODATE_HEAD)).execute();

        //上传录音的第二种方式
//        RequestParams params = new RequestParams(Const.WuYe.URL_UPLOAD_VOICE);
//        params.addBodyParameter("voices", recAudioFile);
//        params.addParameter("logicId", logicId);
//        params.addParameter("appId", itemBeanList.get(0).getApplicationId());
//        params.addParameter("fieldName", "voices");
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                Gson gson = new Gson();
//                Base base = gson.fromJson(result, Base.class);
//                if (base.respCode == 1001) {
//
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
    }

    /**
     * 退出该界面是需要的操作
     */
    public void onDestroy() {
        // 清理图片缓存
        for (int i = 0; i < bmp.size(); i++) {
            bmp.get(i).recycle();
        }
        for (int i = 0; i < PhotoActivity.bitmap.size(); i++) {
            PhotoActivity.bitmap.get(i).recycle();
        }
        PhotoActivity.bitmap.clear();
        bmp.clear();
        drr.clear();
        super.onDestroy();
    }
}
