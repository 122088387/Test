package com.chaungying.common.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chaungying.common.bean.ConfigBean;
import com.chaungying.common.bean.ConfigBean.ItemsBean;
import com.chaungying.common.bean.SubtitlesItem;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.T;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.site_repairs.view.PullBoxDialog;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/14.
 * <p/>
 * 动态加载生成的界面的核心类
 */
public class BuildView {


    LinearLayout linearLayout;
    Context mContext;
    //获取Activity传过来的handler消息
    private Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    String json = "";

    public void setJson(String json) {
        this.json = json;
    }

    public BuildView(LinearLayout linearLayout, Context context) {
        this.linearLayout = linearLayout;
        mContext = context;
    }

    static InputView inputView;
    static InputMoreView inputMoreView;

    //构建  通过json显示的布局

    /**
     * 根据json生成 布局
     *
     * @param json  传入的json
     * @param isAll 是完整的json还是整个json中一部分
     * @return
     */
    public LinearLayout buildView(String json, boolean isAll) {
        ////////////////////使用Gson对数据进行解析////////////////////////
        //获取ConfigBean实体对象
        Gson gson = new Gson();
        LinearLayout l = new LinearLayout(mContext);
        ArrayList<ItemsBean> listItem = null;
        if (isAll) {//
            ConfigBean configBean = gson.fromJson(json, ConfigBean.class);
            l.setOrientation(LinearLayout.VERTICAL);
            //根据itemType对每个定制样式
            listItem = (ArrayList<ConfigBean.ItemsBean>) configBean.getItems();
        } else {
            l.setOrientation(LinearLayout.VERTICAL);
            Type listType = new TypeToken<ArrayList<ItemsBean>>() {
            }.getType();
            listItem = gson.fromJson(json, listType);
        }
        //将获取的每行的信息发送到活动界面一次
        if (handler != null) {
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putSerializable("listitem", listItem);
            msg.setData(bundle);
            msg.what = 111;
            handler.sendMessage(msg);
        }
        if (listItem != null) {
            ItemsBean itemBean = null;
            for (int j = 0; j < listItem.size(); j++) {
                itemBean = listItem.get(j);
                int itemType = itemBean.getItemType();
                if (itemType == 1) {//1 单选
                    SingleSelectView singleSelectView = new SingleSelectView(mContext);
                    singleSelectView.setId(itemBean.getId());
                    singleSelectView.setIsRequired(itemBean.getIsRequired());
                    List<SubtitlesItem> list = itemBean.getSubtitles();
                    ArrayList<String> singleName = new ArrayList<String>();
                    for (int i = 0; i < list.size(); i++) {
                        singleName.add(itemBean.getSubtitles().get(i).getName());
                    }
                    singleSelectView.setData(singleName);
                    singleSelectView.init();
                    if (itemBean.getIsRequired() == 0) {
                        singleSelectView.setSexTv(itemBean.getTitle() + "*");
                    } else {
                        singleSelectView.setSexTv(itemBean.getTitle());
                    }
                    //默认将第一个对应的val赋值
                    singleSelectView.setSingle_tv(itemBean.getSubtitles().get(0).getVal() + "");
                    radioGroup = singleSelectView.getRadioGroup();
                    radioGroup.setOnCheckedChangeListener(new MyRadioGroupListener(singleSelectView, itemBean.getSubtitles()));
                    l.addView(singleSelectView);
                } else if (itemType == 12) {//2 多项选择
                    MoreSelectView moreselectView = new MoreSelectView(mContext);
                    moreselectView.setTvText(itemBean.getTitle());
                    moreselectView.setMoreSelectUrl((String) itemBean.getRequestmethod());
                    l.addView(moreselectView);
                } else if (itemType == 3) {//多行输入
                    inputMoreView = new InputMoreView(mContext);
                    inputMoreView.setId(itemBean.getId());
                    EditText editText = (EditText) inputMoreView.findViewById(R.id.et_input);
                    inputMoreView.setIsRequired(itemBean.getIsRequired());
                    if (itemBean.getIsRequired() == 0) {
                        editText.setHint(itemBean.getTitle() + "*");
                    } else {
                        editText.setHint(itemBean.getTitle());
                    }
//                    moreInput = inputMoreView.getInput();
                    l.addView(inputMoreView);
                } else if (itemType == 4) {//4 单行输入
                    inputView = new InputView(mContext);
                    //给 单行输入视图设置id
                    inputView.setId(itemBean.getId());
                    inputView.setEtPlaceholder("请输入" + itemBean.getTitle());
                    inputView.setIsRequired(itemBean.getIsRequired());
                    if (itemBean.getIsRequired() == 0) {
                        inputView.setTvText(itemBean.getTitle() + "*");
                    } else {
                        inputView.setTvText(itemBean.getTitle());
                    }
                    l.addView(inputView);
                } else if (itemType == 14) {//14 拍照
                    CameraView cameraView = new CameraView(mContext);
                    if (itemBean.getIsRequired() == 0) {
                        cameraView.setTitle("现场拍照*");
                    } else {
                        cameraView.setTitle("现场拍照");
                    }
                    cameraView.setFiledName(itemBean.getFieldname());
                    cameraView.setUpLoadeUrl((String) itemBean.getRequestmethod());
                    l.addView(cameraView);
                } else if (itemType == 16) {//16 时间
                    TimeView timeView = new TimeView(mContext);
                    timeView.setTvText(itemBean.getTitle() + " ");
                    l.addView(timeView);
                } else if (itemType == 17) {//17 涂鸦
                    HandDrawView handDrawView = new HandDrawView(mContext);
                    handDrawView.setId(itemBean.getId());
                    handDrawView.setOrders(itemBean.getOrders());
                    handDrawView.setIsRequired(itemBean.getIsRequired());
                    if (itemBean.getIsRequired() == 0) {
                        handDrawView.setTitle(itemBean.getTitle() + "*");
                    } else {
                        handDrawView.setTitle(itemBean.getTitle());
                    }
                    handDrawView.setHandUrl((String) itemBean.getFieldname());
                    l.addView(handDrawView);
                } else if (itemType == 18) {//18 小视频
                    LuXiangView luXiangView = new LuXiangView(mContext);
                    if (itemBean.getIsRequired() == 0) {
                        luXiangView.setTile("小视频*");
                    } else {
                        luXiangView.setTile("小视频");
                    }
                    l.addView(luXiangView);
                } else if (itemType == 19) {//19 录音
                    LuYinView luYinView = new LuYinView(mContext);
                    TextView textView = (TextView) luYinView.findViewById(R.id.tv_luyin);
                    if (itemBean.getIsRequired() == 0) {
                        textView.setText("现场录音*");
                    } else {
                        textView.setText("现场录音");
                    }
                    luYinView.setFieldName(itemBean.getFieldname());
                    l.addView(luYinView);
                } else if (itemType == 15) {
//                    //提交按钮的名字
//                    String submitTitle = itemBean.getTitle();
//                    this.submitTitle = submitTitle;
//                    //提交文本的接口
//                    String requestmethod = (String) itemBean.getRequestmethod();
//                    uploadUrl = requestmethod;
                } else if (itemType == 2) {//级联
                    JiLianView jiLianView = new JiLianView(mContext);
                    String requestmethod = (String) itemBean.getRequestmethod();//级联1的接口
                    jiLianView.setjiLianUrl(Const.WuYe.URL_BASE + requestmethod);
                    jiLianView.setOrders(itemBean.getOrders());
                    jiLianView.setId(itemBean.getId());
                    jiLianView.setDependency((Integer) itemBean.getDependency());
                    jiLianView.setFieldname(itemBean.getFieldname());
                    jiLianView.setIsRequired(itemBean.getIsRequired());
                    if (itemBean.getIsRequired() == 0) {
                        jiLianView.setTvText(itemBean.getTitle() + "*");
                    } else {
                        jiLianView.setTvText(itemBean.getTitle());
                    }
                    jiLianView.setTv_show_hint("请选择" + itemBean.getTitle());
                    l.addView(jiLianView);
                } else if (itemType == 152) {//级联（选择完成之后返回数据放到输入框）
                    JiLianEditView jiLianEditView = new JiLianEditView(mContext);
                    jiLianEditView.setUrl(Const.WuYe.URL_BASE + (String) itemBean.getRequestmethod());
                    jiLianEditView.setFieldname(itemBean.getFieldname());
                    jiLianEditView.setHint(itemBean.getPlaceholder());
                    jiLianEditView.setId(itemBean.getId());
                    jiLianEditView.setIsRequired(itemBean.getIsRequired());
                    if (itemBean.getIsRequired() == 0) {
                        jiLianEditView.setTitle(itemBean.getTitle() + "*");
                    } else {
                        jiLianEditView.setTitle(itemBean.getTitle());
                    }
                    l.addView(jiLianEditView);
                } else if (itemType == 20) {//文本显示
                    ShowTextView showTextView = new ShowTextView(mContext);
                    showTextView.setText(itemBean.getTitle());
                    showTextView.setTextViewType(Color.BLACK, 14, mContext.getResources().getColor(R.color.huise2));
                    //默认将第一个对应的val赋值
                    l.addView(showTextView);
                } else if (itemType == 127) {//二级标题
                    ShowTextNextView showTextNextView = new ShowTextNextView(mContext);
                    showTextNextView.setText(itemBean.getTitle());
                    showTextNextView.setTextViewType(Color.BLACK, 14, mContext.getResources().getColor(R.color.huise2));
                    //默认将第一个对应的val赋值
                    l.addView(showTextNextView);
                } else if (itemType == 31) {//手动添加视图
                    AddView addView = new AddView(mContext);
                    addView.setIsRequired(itemBean.getIsRequired());
                    addView.setItemsBean(itemBean);
                    l.addView(addView);
                } else if (itemType == 33) {//进入后选择时间，样式和级联相似
                    TimeSelectView timeSelectView = new TimeSelectView(mContext);
                    timeSelectView.setItemsBean(itemBean);
                    l.addView(timeSelectView);
                } else if (itemType == 36) {//日期对话框
                    DateDialogView dateDialogView = new DateDialogView(mContext);
                    dateDialogView.setId(itemBean.getId());
                    dateDialogView.setIsRequired(itemBean.getIsRequired());
                    if (itemBean.getIsRequired() == 0) {
                        dateDialogView.initView(itemBean.getTitle() + "*");
                    } else {
                        dateDialogView.initView(itemBean.getTitle());
                    }
                    dateDialogView.setHint("请选择" + itemBean.getTitle());
                    l.addView(dateDialogView);
                } else if (itemType == 37) {//时间对话框
                    TimeDialogView timeDialogView = new TimeDialogView(mContext);
                    timeDialogView.setId(itemBean.getId());
                    timeDialogView.setIsRequired(itemBean.getIsRequired());
                    if (itemBean.getIsRequired() == 0) {
                        timeDialogView.initView(itemBean.getTitle() + "*");
                    } else {
                        timeDialogView.initView(itemBean.getTitle());
                    }
                    timeDialogView.setHint("请选择" + itemBean.getTitle());
                    l.addView(timeDialogView);
                } else if (itemType == 155) {//时间日期对话框
                    DateTimeDialogView dateTimeDialogView = new DateTimeDialogView(mContext);
                    dateTimeDialogView.setId(itemBean.getId());
                    dateTimeDialogView.setIsRequired(itemBean.getIsRequired());
                    if (itemBean.getIsRequired() == 0) {
                        dateTimeDialogView.initView(itemBean.getTitle() + "*");
                    } else {
                        dateTimeDialogView.initView(itemBean.getTitle());
                    }
                    dateTimeDialogView.setHint("请选择" + itemBean.getTitle());
                    l.addView(dateTimeDialogView);
                } else if (itemType == 52 || itemType == 53) {//下拉样式的popWindow
                    itemsBeanList.add(itemBean);
                    continue;
                } else if (itemType == 54) {//选择框
                    PullSelectView pullSelectView = new PullSelectView(mContext);
                    l.addView(pullSelectView);
                } else if (itemType == 83) {//时间段
                    TimePeriod timePeriod = new TimePeriod(mContext);
                    l.addView(timePeriod);
                } else if (itemType == 108) {//分组的灰色线
                    GroupLineView groupLineView = new GroupLineView(mContext);
                    l.addView(groupLineView);
                } else if (itemType == 125) { //数量输入框
                    InputNumView inputNumView = new InputNumView(mContext);
                    inputNumView.setId(itemBean.getId());
                    inputNumView.setEtPlaceholder("请输入" + itemBean.getTitle());
                    inputNumView.setIsRequired(itemBean.getIsRequired());
                    if (itemBean.getIsRequired() == 0) {
                        inputNumView.setTvText(itemBean.getTitle() + "*");
                    } else {
                        inputNumView.setTvText(itemBean.getTitle());
                    }
                    l.addView(inputNumView);
                } else if (itemType == 126) { //价格输入框
                    InputPriceView inputPriceView = new InputPriceView(mContext);
                    inputPriceView.setId(itemBean.getId());
                    inputPriceView.setEtPlaceholder("请输入" + itemBean.getTitle());
                    inputPriceView.setIsRequired(itemBean.getIsRequired());
                    if (itemBean.getIsRequired() == 0) {
                        inputPriceView.setTvText(itemBean.getTitle() + "*");
                    } else {
                        inputPriceView.setTvText(itemBean.getTitle());
                    }
                    l.addView(inputPriceView);
                }else if(itemType == 162){//隐藏控件
                    HiddenView hiddenView = new HiddenView(mContext);
                    hiddenView.setFieldName(itemBean.getFieldname());
                    hiddenView.setHiddenKey(itemBean.getHiddenKey());
                    hiddenView.setId(itemBean.getId());
                    l.addView(hiddenView);
                }else if(itemType == 158){//订餐中的选择地址
                    SelectAddressView selectAddressView = new SelectAddressView(mContext);
                    selectAddressView.setFieldname(itemBean.getFieldname());
                    selectAddressView.setId(itemBean.getId());
                    selectAddressView.setUrl(Const.WuYe.URL_BASE + (String) itemBean.getRequestmethod());
                    selectAddressView.setIsRequired(itemBean.getIsRequired());
                    if (itemBean.getIsRequired() == 0) {
                        selectAddressView.setTitle(itemBean.getTitle() + "*");
                    } else {
                        selectAddressView.setTitle(itemBean.getTitle());
                    }
                    selectAddressView.setPlaceholder(itemBean.getPlaceholder());
                    l.addView(selectAddressView);
                }
                View tv = new View(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);

                if (itemType != 108 && itemType != 52 && itemType != 53 && itemType != 20 && itemType != 31) {
                    if (j < listItem.size() - 1) {
                        if (listItem.get(j + 1).getItemType() != 108) {
                            params.setMargins(14, 0, 0, 0);
                        }
                    }
                }
                tv.setLayoutParams(params);
                tv.setBackgroundResource(R.color.huise2);
                if(itemType != 162){
                    l.addView(tv);
                }
            }
            if (listItem.size() == 0) {
                DownPopWindowView downPopWindowView = new DownPopWindowView(mContext);
                downPopWindowView.setItemsBeanList(itemsBeanList);
                l.addView(downPopWindowView);
            }
        } else {
            T.showShort(mContext, "json解析异常");
        }
        //开始整理popWindow布局
        if (itemsBeanList.size() > 0) {
            DownPopWindowView downPopWindowView = new DownPopWindowView(mContext);
            downPopWindowView.setItemsBeanList(itemsBeanList);
            downPopWindowView.setOnClickPullBoxListener(onClickPullBoxListener);
            l.addView(downPopWindowView);
        }

        linearLayout.addView(l);
        //关闭加载对话框
        ProgressUtil.close();
        return linearLayout;
    }


    //收集下拉式的布局Item
    private List<ConfigBean.ItemsBean> itemsBeanList = new ArrayList<ItemsBean>();

    private String singInput;

    public String getSingInput() {
        return singInput;
    }

    //单选按钮组
    RadioGroup radioGroup;

    List<SubtitlesItem> mySubtitlesItemList;

    //监听单选的数据
    class MyRadioGroupListener implements RadioGroup.OnCheckedChangeListener {

        SingleSelectView mSingleSelectView;

        MyRadioGroupListener(SingleSelectView singleSelectView, List<SubtitlesItem> subtitlesItemList) {
            mSingleSelectView = singleSelectView;
            mySubtitlesItemList = subtitlesItemList;
        }


        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            for (int i = 0; i < group.getChildCount(); i++) {
                View view = group.getChildAt(i);
                if (view instanceof AppCompatRadioButton) {
                    AppCompatRadioButton button = (AppCompatRadioButton) view;
                    if (view.getId() == checkedId) {
                        String singSelect = button.getText().toString();
                        int val = mySubtitlesItemList.get(i).getVal();
                        //将单选的数据  隐藏放到控件当中
                        mSingleSelectView.setSingle_tv(val + "");
//                        Message msg = new Message();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("single", singSelect);
//                        msg.setData(bundle);
//                        msg.what = 1;//对单选的文字的传送
//                        handler.sendMessage(msg);
                    }
                }
            }
        }
    }

    ;

    //为了让现场报修 Activity得到链接和提交的名字
//    private String uploadUrl = "";
//
//    public String getUploadUrl() {
//        return uploadUrl;
//    }

//    public String submitTitle;


    //对侧拉框的回调时间处理,显示侧拉框
    private DownPopWindowView.OnClickPullBoxListener onClickPullBoxListener = new DownPopWindowView.OnClickPullBoxListener() {
        @Override
        public void onClickPullBox(String title, ItemsBean itemsBean) {
            PullBoxDialog dialog = PullBoxDialog.createDialog(mContext);
            dialog.initViewDatas(itemsBean);
            dialog.show();
        }
    };
}

