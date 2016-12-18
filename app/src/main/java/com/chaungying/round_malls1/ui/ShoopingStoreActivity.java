package com.chaungying.round_malls1.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.common.utils.T;
import com.chaungying.common.view.DownPopWindowPerView;
import com.chaungying.ji_xiao.bean.JobHeader;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.round_malls1.adapter.ShoopingStoreListener;
import com.chaungying.round_malls1.adapter.ShoppingStoreAdapter;
import com.chaungying.round_malls1.bean.ShoppingStoreBean;
import com.chaungying.site_repairs.view.PullToRefreshLayout;
import com.chaungying.site_repairs.view.PullableListView1;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王晓赛 or 2016/9/23
 *         <p/>
 *         商店列表
 */
@ContentView(R.layout.activity_shopping_store)
public class ShoopingStoreActivity extends BaseActivity implements AdapterView.OnItemClickListener, DownPopWindowPerView.OnSendToPullListener {

    JobHeader bean;

    @ViewInject(R.id.down_popwindow_view)
    private DownPopWindowPerView down_popwindow_view;

    @ViewInject(R.id.lv_shopping_store)
    private PullableListView1 lv_shopping_store;

    private ShoppingStoreAdapter adapter;


    public static final int HANDLE_TAG = 0x0001;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLE_TAG:
                    ShoppingStoreBean bean = (ShoppingStoreBean) msg.getData().getSerializable("bean");
                    if (bean != null) {
                        adapter.setList(bean.getDatas());
                        adapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };

    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = new MyLocationListener();

    //刷新的页数
    public int refreshPage = 1;
    //刷新每次的个数
    public static int refreshPageNum = 20;

    @ViewInject(R.id.refresh_view)
    private PullToRefreshLayout refresh_view;

    private ShoopingStoreListener shoopingStoreListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBar("物业商城", R.drawable.nav_return, R.drawable.shopping_store_my);

        //设置定位的一些基本属性属性
        initLocation();
        //开启定位
        mLocationClient.start();


        adapter = new ShoppingStoreAdapter(this);

        initData();

        lv_shopping_store.setAdapter(adapter);
        lv_shopping_store.setOnItemClickListener(this);

        shoopingStoreListener = new ShoopingStoreListener(this);
        refresh_view.setOnRefreshListener(shoopingStoreListener);
        //注册加载数据之后的广播接收器
        registerBoradcastReceiver();

    }


    private void initData() {
        ProgressUtil.show(this, "加载中...");
        //下拉时间  先加载出来
        RequestParams params = new RequestParams(Const.WuYe.URL_SELLER_GET_SELLER_FILTRATE);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                bean = new Gson().fromJson(result, JobHeader.class);
                if (bean != null) {
                    initView();
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(bean.getItems().get(0).getFieldname(), -1 + "");
                    shoopingStoreListener.setMap(map);
                }
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

        //加载列表中的数据
        RequestParams params1 = new RequestParams(Const.WuYe.URL_SELLER_QUERY_SELLER);
        params1.addParameter("page", refreshPage);
        params1.addParameter("row", refreshPageNum);
        params1.addParameter("collectMemberId", SPUtils.get(this, Const.SPDate.ID, -1));
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ShoppingStoreBean bean = new Gson().fromJson(result, ShoppingStoreBean.class);
                if (bean != null) {
                    adapter.setList(bean.getDatas());
                    adapter.notifyDataSetChanged();
                }
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

    private void initView() {
        down_popwindow_view.setListener(this);
        down_popwindow_view.setItemsBeanList(bean.getItems());
        down_popwindow_view.setHandler(handler);
    }

    @Override
    public void sendToPullListener(Map<String, String> map) {
        shoopingStoreListener.setMap(map);
        shoopingStoreListener.refreshPage = 2;
    }

    //临时存储点击的哪个bean
    private ShoppingStoreBean.DatasBean storeBean;

    private static final int REQUEST_CODE = 0x0001;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        storeBean = adapter.getList().get(position);
        bundle.putSerializable("bean", storeBean);
        openActivtyForResult(this, ShoopingGoodsActivity.class, bundle, REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            int tag = data.getExtras().getInt("TAG");//得到新Activity 关闭后返回的数据
            storeBean.setIsCollect(tag);
        }
    }


    /**
     * 接收刷新数据的标志
     */
    private final String ACTION_NAME = "刷新数据";

    /**
     * 广播接收器
     */
    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ACTION_NAME);
        //注册广播
        registerReceiver(receiverRefresh, myIntentFilter);
    }

    /**
     * 定义接收 刷新数据的广播接收器
     */
    private BroadcastReceiver receiverRefresh = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_NAME)) {
                jsonToBean(intent.getStringExtra("json"));
            }
        }
    };

    private void jsonToBean(String json) {
//        json = "{\"datas\":[{\"createTime\":\"\",\"phone\":\"4124\",\"state\":0,\"introduce\":\"发顺丰\",\"evaluateLevel\":3,\"credential\":\"\",\"id\":1,\"address\":\"124124\",\"userId\":\"72297c8842604c059b05d28bfb11d10b\",\"name\":\"412412\",\"owner\":\"恶趣味\",\"sellNum\":114,\"buildDate\":\"2016-08-31\",\"longitude\":\"116.371067\",\"orders\":1111,\"latitude\":\"39.925338\",\"banner\":\"\"}],\"respCode\":1001,\"respMsg\":\"成功！\"}";
        ShoppingStoreBean bean = new Gson().fromJson(json, ShoppingStoreBean.class);
        if (bean != null) {
            if (bean.getDatas().size() != 0) {
                adapter.addDataToList(bean.getDatas());
            } else {
                T.showShort(ShoopingStoreActivity.this, "没有更多数据");
            }
            adapter.notifyDataSetChanged();
        }
    }


    /**
     * 设置定位参数
     */
    private void initLocation() {

        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 5 * 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }


    /**
     * 百度定位的监听接口
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            updataLocationView(location);

            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());
        }


    }

    /**
     * 获取经纬度  将本地的经纬度保存到sp文件中
     *
     * @param location
     */
    private void updataLocationView(BDLocation location) {

        SPUtils.put(this, Const.SPDate.LONGITHDE, location.getLongitude()+"");
        SPUtils.put(this, Const.SPDate.LATITUDE, location.getLatitude()+"");

        adapter.setBdLocation(location);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
        super.onDestroy();
    }

    /**
     * 点击我的
     */
    @Event(value = R.id.title_menu)
    private void shopping_store_my(View view) {
        openActivty(this, ShoppingStoreMyMsgActivity.class, null, null);
    }
}
