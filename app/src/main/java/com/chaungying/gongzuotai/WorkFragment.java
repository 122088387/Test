package com.chaungying.gongzuotai;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.common.utils.date.DateUtil;
import com.chaungying.common.utils.file.MusicPlayer;
import com.chaungying.common.utils.file.VibrationPlayer;
import com.chaungying.gongzuotai.bean.AllMsgBean;
import com.chaungying.gongzuotai.bean.MyRepairBean;
import com.chaungying.gongzuotai.dbbean.OrderMealBean;
import com.chaungying.gongzuotai.dbbean.RepairBean;
import com.chaungying.gongzuotai.dbbean.UserIdTimeBean;
import com.chaungying.gongzuotai.dbbean.UserIdTimeRepairBean;
import com.chaungying.gongzuotai.ui.MsgListActivity;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 工作台碎片
 *
 * @author 种耀淮 在2016年07月09日12:52创建
 */
@ContentView(R.layout.fragment_work)
public class WorkFragment extends Fragment implements AdapterView.OnItemClickListener {

    // 在主页面的位置
    public static final int NUMBER = 0;


    // 列表
    @ViewInject(R.id.work_list)
    private ListView listView;

    // 列表适配器
    private WorkAdapter adapter;

    // 列表数据
    private ArrayList<WorkItem> datas = new ArrayList<>();

    //未读消息的总数
    private int unReadMsgNum = 0;
    //所有数据的列表
    List<AllMsgBean.DataBean> datasBeanList = new ArrayList<>();
    List<List<MyRepairBean.DataBean.DatasBean>> datasBeanRepairList = new ArrayList<>();


    //第一次根据数据对列表进行展示
    private boolean isFirstShow = true;
    private boolean isFirstShowRepair = true;


    @Override
//    public void onAttach(Activity activity) {
//        if (activity instanceof MainActivity) {
//            ((MainActivity) activity).setWorkListener();
//        }
//        super.onAttach(activity);
//    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        if(savedInstanceState != null){
//            sendMsgNumberListener = (WorkFragment.sendMsgNumberListener) savedInstanceState.getSerializable("interface");
//        }
        View view = x.view().inject(this, inflater, container);

        createFragment();

        registerBoradcastReceiver();

        return view;
    }

    //注册广播
    public void registerBoradcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getActivity().getResources().getString(R.string.send_work_task));
        getActivity().registerReceiver(myBroadCastReceive, intentFilter);
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        outState.putSerializable("interface", sendMsgNumberListener);
//        super.onSaveInstanceState(outState);
//    }

    UserIdTimeBean userIdTimeBean;

    /**
     * 从数据库中查找userID和dateTIme
     *
     * @return
     */
    private String getUserId() {
//        userId=4513&dateTime=
        int userId = (int) SPUtils.get(getContext(), Const.SPDate.ID, 4512);
        String dateTime = "";
        try {
//            UserIdTimeBean temp = new UserIdTimeBean();
            List<UserIdTimeBean> userIdTimeList = DataSupport.order("userID").find(UserIdTimeBean.class);
            if (userIdTimeList.size() > 0) {
                for (int i = 0; i < userIdTimeList.size(); i++) {
                    if (userIdTimeList.get(i).getUserID().equals(userId + "")) {
                        UserIdTimeBean userIdTimeBean = userIdTimeList.get(i);
                        dateTime = userIdTimeBean.getDateTime();
                        break;
                    } else if (i == userIdTimeList.size() - 1) {
                        UserIdTimeBean userIdTimeBean = new UserIdTimeBean();
                        userIdTimeBean.setUserID(userId + "");
                        userIdTimeBean.setDateTime(dateTime);
                        userIdTimeBean.save();
                    }
                }
            } else {
                UserIdTimeBean userIdTimeBean = new UserIdTimeBean();
                userIdTimeBean.setUserID(userId + "");
                userIdTimeBean.setDateTime(dateTime);
                userIdTimeBean.save();
            }
        } catch (Exception e) {
            e.printStackTrace();
            UserIdTimeBean userIdTimeBean = new UserIdTimeBean();
            userIdTimeBean.setUserID(userId + "");
            userIdTimeBean.setDateTime(dateTime);
            userIdTimeBean.save();
        }
        return "userId=" + userId + "&dateTime=" + dateTime.replaceAll(" ", "%20");
    }

    UserIdTimeRepairBean userIdTimeRepairBean;

    /**
     * 获取我的报修中的数据
     *
     * @return
     */
    private String getRepairUserId() {
        int userId = (int) SPUtils.get(getContext(), Const.SPDate.ID, 4512);
        String dateTime = "";
        try {
            List<UserIdTimeRepairBean> userIdTimeRepairBeanList = DataSupport.order("userID").find(UserIdTimeRepairBean.class);
            if (userIdTimeRepairBeanList.size() > 0) {
                for (int i = 0; i < userIdTimeRepairBeanList.size(); i++) {
                    if (userIdTimeRepairBeanList.get(i).getUserID().equals(userId + "")) {
                        UserIdTimeRepairBean userIdTimeRepairBean = userIdTimeRepairBeanList.get(i);
                        dateTime = userIdTimeRepairBean.getDateTime();
                        break;
                    } else if (i == userIdTimeRepairBeanList.size() - 1) {
                        UserIdTimeRepairBean userIdTimeRepairBean = new UserIdTimeRepairBean();
                        userIdTimeRepairBean.setUserID(userId + "");
                        userIdTimeRepairBean.setDateTime(dateTime);
                        userIdTimeRepairBean.save();
                    }
                }
            } else {
                UserIdTimeRepairBean userIdTimeRepairBean = new UserIdTimeRepairBean();
                userIdTimeRepairBean.setUserID(userId + "");
                userIdTimeRepairBean.setDateTime(dateTime);
                userIdTimeRepairBean.save();
            }

        } catch (Exception e) {
            e.printStackTrace();
            UserIdTimeRepairBean userIdTimeRepairBean = new UserIdTimeRepairBean();
            userIdTimeRepairBean.setUserID(userId + "");
            userIdTimeRepairBean.setDateTime(dateTime);
            userIdTimeRepairBean.save();
        }
        return "userId=" + userId + "&dateTime=" + dateTime.replaceAll(" ", "%20");
    }


    /**
     * 我的报修
     */
    private void getMyRepair() {
        RequestParams params = new RequestParams(Const.WuYe.URL_WORK_MY_REPAIR + "?" + getRepairUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result1) {
                resultRepair = result1;
                handler.sendEmptyMessage(1);
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

    //用于传到下一个界面的我的报修的url
    private String orderUrl = "";

    /**
     * 请求网络 订餐提醒
     */
    private void getData() {
        orderUrl = Const.WuYe.URL_WORK_BEANCE + "?" + getUserId();
        RequestParams params = new RequestParams(orderUrl);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result1) {
                result = result1;
                handler.sendEmptyMessage(0);
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

    String result = "";
    String resultRepair = "";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    saveDatas(result);
                    result = "";
                    break;
                case 1://我的报修
                    saveRepairDatas(resultRepair);
                    resultRepair = "";
                    break;
            }

        }
    };

    private void saveDatas(String result) {
        Gson gson = new Gson();
        AllMsgBean bean = gson.fromJson(result, AllMsgBean.class);
        String initTime = bean.getInitTime();
        UserIdTimeBean userIdTimeBean = new UserIdTimeBean();
        userIdTimeBean.setDateTime(initTime);
        String userID = (int) SPUtils.get(getContext(), Const.SPDate.ID, 4512) + "";
        userIdTimeBean.updateAll("userID=" + userID);

        List<AllMsgBean.DataBean> list = bean.getData();
        datasBeanList.clear();
        datasBeanList = list;
        for (int i = 0; i < list.size(); i++) {
            AllMsgBean.DataBean datasBean = list.get(i);
            int type = list.get(i).getType();
            if (type == 1) {//订餐提醒
                List<List<AllMsgBean.DataBean.DatasBean>> databeanList = datasBean.getDatas();
                for (int j = 0; j < databeanList.size(); j++) {
                    List<AllMsgBean.DataBean.DatasBean> databeanList1 = databeanList.get(j);
                    OrderMealBean mealBean = new OrderMealBean();
                    mealBean.setLayoutid(57);
                    mealBean.setApplicationId(datasBean.getApplicationId());
                    mealBean.setType(datasBean.getType());
                    mealBean.setTitle(databeanList1.get(0).getTitle() + ":" + databeanList1.get(0).getValue());
                    mealBean.setTitle1(databeanList1.get(1).getTitle() + ":" + databeanList1.get(1).getValue());
                    mealBean.setTitle2(databeanList1.get(2).getValue());
                    mealBean.setTitle3(databeanList1.get(3).getTitle() + ":" + databeanList1.get(3).getValue());
                    mealBean.setLogicId(databeanList1.get(0).getLogicId());
                    mealBean.setRead(false);
                    mealBean.setUserId((int) SPUtils.get(getContext(), Const.SPDate.ID, 4512));
                    mealBean.setItemtype(databeanList1.get(0).getItemtype());
                    mealBean.save();
                }
            } else if (type == 2) {//抢单中心
                //如果有抢单中心  再加载 我的报修 的数据
                getMyRepair();
//                List<List<AllMsgBean.DataBean.DatasBean>> databeanList = datasBean.getDatas();
//                for (int j = 0; j < databeanList.size(); j++) {
//                    List<AllMsgBean.DataBean.DatasBean> databeanList1 = databeanList.get(j);
//                    RepairBean repairBean = new RepairBean();
//                    repairBean.setLayoutid(datasBean.getLayoutid());
//                    repairBean.setApplicationId(datasBean.getApplicationId());
//                    repairBean.setType(datasBean.getType());
//                    repairBean.setTitle(databeanList1.get(0).getTitle() + ":" + databeanList1.get(0).getValue());
//                    repairBean.setTitle1(databeanList1.get(1).getTitle() + ":" + databeanList1.get(1).getValue());
//                    repairBean.setTitle2(databeanList1.get(2).getTitle() + ":" + databeanList1.get(2).getValue());
//                    repairBean.setTitle3(databeanList1.get(3).getTitle() + ":" + databeanList1.get(3).getValue());
//                    repairBean.setLogicId(databeanList1.get(0).getLogicId());
//                    repairBean.setRead(false);
//                    repairBean.setUserId((int) SPUtils.get(getContext(), Const.SPDate.ID, 4512));
//                    repairBean.setItemtype(databeanList1.get(0).getItemtype());
//                    repairBean.save();
//                }
            }
        }
        if (isFirstShow) {
            isFirstShow = !isFirstShow;
            upData();
        }
        //查询数据库
        searchData();
    }

    MyRepairBean bean = null;

    /**
     * 存储我的报修数据
     *
     * @param resultRepair
     */
    private void saveRepairDatas(String resultRepair) {
        Gson gson = new Gson();

        bean = gson.fromJson(resultRepair, MyRepairBean.class);
        List<List<MyRepairBean.DataBean.DatasBean>> list = new ArrayList<>();
        if (bean != null) {
            String initTime = bean.getInitTime();
            UserIdTimeRepairBean userIdTimeRepairBean = new UserIdTimeRepairBean();
            userIdTimeRepairBean.setDateTime(initTime);
            String userID = (int) SPUtils.get(getContext(), Const.SPDate.ID, 4512) + "";
            userIdTimeRepairBean.updateAll("userID=" + userID);
            if (bean.getData().size() > 0) {
                for (int i = 0; i < bean.getData().size(); i++) {
                    for (int j = 0; j < bean.getData().get(i).getDatas().size(); j++) {
                        for (int k = 0; k < bean.getData().get(i).getDatas().get(j).size(); k++) {
                            bean.getData().get(i).getDatas().get(j).get(k).setApplicationId(bean.getData().get(i).getApplicationId());
                        }
                    }
                    list.addAll(bean.getData().get(i).getDatas());
                }
            }
        }
        datasBeanRepairList.clear();
        datasBeanRepairList = list;

        for (int i = 0; i < list.size(); i++) {
            List<MyRepairBean.DataBean.DatasBean> datasBean = list.get(i);
            //我的报修
            RepairBean repairBean = new RepairBean();
            repairBean.setLayoutid(56);
            repairBean.setApplicationId(datasBean.get(0).getApplicationId());
            repairBean.setType(bean.getType());
            for (int j = 0; j < datasBean.size(); j++) {
                MyRepairBean.DataBean.DatasBean dataBean = datasBean.get(j);
                switch (j) {
                    case 0:
                        repairBean.setTitle(dataBean.getTitle() + dataBean.getValue());
                        break;
                    case 1:
                        repairBean.setTitle1(dataBean.getTitle() + ":" + dataBean.getValue());
                        break;
                    case 2:
                        repairBean.setTitle2(dataBean.getTitle() + ":" + dataBean.getValue());
                        break;
                    case 3:
                        repairBean.setTitle3("");
                        break;
                }
            }
            repairBean.setLogicId(datasBean.get(0).getLogicId());
            repairBean.setDataType(datasBean.get(0).getDataType());
            if(datasBean.get(0).getCountersignTime() == null || datasBean.get(0).getCountersignTime().equals("null")){
                repairBean.setCountersignTime("");
            }else{
                repairBean.setCountersignTime(datasBean.get(0).getCountersignTime());
            }
            repairBean.setRead(false);
            repairBean.setUserId((int) SPUtils.get(getContext(), Const.SPDate.ID, 4512));
            repairBean.setItemtype(datasBean.get(0).getItemtype());
            repairBean.save();
        }
        if (isFirstShowRepair) {
            isFirstShowRepair = !isFirstShowRepair;
            upDataRepair();
        }
        //查询我的报修的数据库
        searchRepairData();
    }

    //广播接收器
    private BroadcastReceiver myBroadCastReceive = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //得到广播中得到的数据，并显示出来
            String message = intent.getStringExtra("data");
            Log.i("接收到广播msg", message);
            getData();
//            getMyRepair();
            playMedia();
        }
    };

    /**
     * 播放声音和震动
     */
    private void playMedia() {
        boolean isVoiceOn = (boolean) SPUtils.get(getContext(), Const.SPDate.AUTO_VOICE, true);
        if (isVoiceOn) {
            //声音
            MusicPlayer musicPlayer = new MusicPlayer(getContext());
            if (musicPlayer.isPlay() == false) {
                musicPlayer.playRaw(R.raw.sound);
            }
        }
        boolean isVibrationOn = (boolean) SPUtils.get(getContext(), Const.SPDate.AUTO_VIBRATION, true);
        if (isVibrationOn) {
            //震动
            VibrationPlayer vibrationPlayer = new VibrationPlayer(getContext());
            vibrationPlayer.play();
        }
    }

    private int orderAndRepairNum = 0;

    /**
     * 对数据库查询,为了显示红点
     */
    private void searchData() {
        int orderNum = 0;
        int repairNum = 0;
//        for (int k = 0; k < datasBeanList.size(); k++) {
//            if (datasBeanList.get(k).getType() == 1) {
        List<OrderMealBean> beanList = DataSupport.where("userId=?", (int) SPUtils.get(getContext(), Const.SPDate.ID, 4512) + "").find(OrderMealBean.class);
        if (beanList != null) {
            for (int i = 0; i < beanList.size(); i++) {
                if (beanList.get(i).isRead() == false) {
                    orderNum++;
                }
            }
            for (int i = 0; i < datas.size(); i++) {
                if (datas.get(i).getType() == 1) {
                    datas.get(i).setMsgCount(orderNum);
                    datas.get(i).setTime(DateUtil.DateToString(new Date(), "HH:mm"));
                    if (orderNum == 0) {
                        datas.get(i).setContent("");
                    } else {
                        datas.get(i).setContent("你有新的消息");
                    }
                }
            }
//                }
        }
        for (int k = 0; k < datasBeanList.size(); k++) {
            if (datasBeanList.get(k).getType() == 2) {
//                List<RepairBean> repairBeanList = DataSupport/*.where("id>?", "1")*/.order("logicId").find(RepairBean.class);
                repairNum = datasBeanList.get(k).getDatas().size();
//                if (repairBeanList != null) {
//                    for (int i = 0; i < repairBeanList.size(); i++) {
//                        if (repairBeanList.get(i).isRead() == false) {
//                            repairNum++;
//                        }
//                    }
                for (int i = 0; i < datas.size(); i++) {
                    if (datas.get(i).getType() == 2) {
                        datas.get(i).setMsgCount(repairNum);
                        datas.get(i).setTime(DateUtil.DateToString(new Date(), "HH:mm"));
                        if (repairNum == 0) {
                            datas.get(i).setContent("");
                        } else {
                            datas.get(i).setContent("你有新的消息");
                        }
                    }
                }
//                }
            }
        }
//        }


        adapter.setDatas(datas);
        adapter.notifyDataSetChanged();
        orderAndRepairNum = orderNum + repairNum;
        unReadMsgNum = orderAndRepairNum + andMyRepairNum;
        if (sendMsgNumberListener != null) {
            //将数据库中查找到的未读信息数量传送到Main 的Tab上
            sendMsgNumberListener.sendMsgNumber(unReadMsgNum);
        }
    }

    private int andMyRepairNum = 0;

    private void searchRepairData() {
        int myRepairNum = 0;
//        for (int k = 0; k < datasBeanRepairList.size(); k++) {
        List<RepairBean> beanList = DataSupport.where("userId=?", (int) SPUtils.get(getContext(), Const.SPDate.ID, 4512) + "").find(RepairBean.class);
        if (beanList != null) {
            for (int i = 0; i < beanList.size(); i++) {
                if (beanList.get(i).isRead() == false) {
                    myRepairNum++;
                }
            }
            for (int i = 0; i < datas.size(); i++) {
                if (datas.get(i).getType() == 3) {
                    datas.get(i).setMsgCount(myRepairNum);
                    datas.get(i).setTime(DateUtil.DateToString(new Date(), "HH:mm"));
                    if (myRepairNum == 0) {
                        datas.get(i).setContent("");
                    } else {
                        datas.get(i).setContent("你有新的消息");
                    }
                }
            }
        }
//        }

        adapter.setDatas(datas);
        adapter.notifyDataSetChanged();
        andMyRepairNum = myRepairNum;
        unReadMsgNum = myRepairNum + orderAndRepairNum;
        if (sendMsgNumberListener != null) {
            //将数据库中查找到的未读信息数量传送到Main 的Tab上
            sendMsgNumberListener.sendMsgNumber(unReadMsgNum);
        }
    }


    private sendMsgNumberListener sendMsgNumberListener;

    public void setSendMsgNumberListener(WorkFragment.sendMsgNumberListener sendMsgNumberListener) {
        this.sendMsgNumberListener = sendMsgNumberListener;
    }


    public interface sendMsgNumberListener extends Serializable {
        void sendMsgNumber(int num);
    }

    /**
     * 初始化
     */
    private void createFragment() {
        // 初始化Content
        adapter = new WorkAdapter(getContext());
        adapter.setDatas(datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
//        upData();
    }

    private void upData() {
//        datas.clear();
//        adapter.notifyDataSetChanged();
        WorkItem temp = null;
        for (int i = 0; i < datasBeanList.size(); i++) {
            if (datasBeanList.get(i).getType() == 1) {
                temp = new WorkItem();
                temp.setTitle("订餐提醒");
                temp.setContent("你有新的消息");
                temp.setTime(DateUtil.DateToString(new Date(), "HH:mm"));
                temp.setType(1);
            } else if (datasBeanList.get(i).getType() == 2) {
                temp = new WorkItem();
                temp.setTitle("抢单中心");
                temp.setContent("你有新的消息");
                temp.setTime(DateUtil.DateToString(new Date(), "HH:mm"));
                temp.setType(2);
            }
            if (temp != null) {
                datas.add(temp);
            }
        }
//        adapter.notifyDataSetChanged();
    }

    private void upDataRepair() {
        WorkItem temp = null;
        if (bean != null) {
            temp = new WorkItem();
            temp.setTitle("我的报修");
            temp.setContent("你有新的消息");
            temp.setTime(DateUtil.DateToString(new Date(), "HH:mm"));
            temp.setType(3);
            if (temp != null) {
                datas.add(temp);
            }
        }
//        adapter.notifyDataSetInvalidated();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //请求网络 查询数据库，存储数据
        getData();
        //我的报修重新定义接口 逻辑不变|
//        getMyRepair();
//        searchData();
//        searchRepairData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        MsgView msgView = (MsgView) view.findViewById(R.id.item_workList_msgView);
//        msgView.setVisibility(View.GONE);
//        datas.get(position).setMsgCount(0);
//        unReadMsgNum = 0;
//        for (int i = 0; i < datas.size(); i++) {
//            unReadMsgNum += datas.get(i).getMsgCount();
//        }
//        adapter.notifyDataSetInvalidated();


        Intent intent = new Intent(getActivity(), MsgListActivity.class);
        intent.putExtra("title", datas.get(position).getTitle());
        intent.putExtra("type", datas.get(position).getType());
        intent.putExtra("url", orderUrl);
        getContext().startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(myBroadCastReceive);
    }
}
