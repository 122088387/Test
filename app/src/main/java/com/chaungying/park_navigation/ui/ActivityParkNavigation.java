package com.chaungying.park_navigation.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.park_navigation.bean.ParkListBean;
import com.chaungying.park_navigation.bean.ParkMapBean;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/7/29
 *         <p/>
 *         园区导航
 */

@ContentView(R.layout.activity_park_navigation)
public class ActivityParkNavigation extends BaseActivity {


    //    114.569229,38.028577   谈固南大街槐安东路
    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;

    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = new MyLocationListener();
    private BDLocation MyLocation = null;

    //是否第一次定位
    private boolean isFirstLocation = true;


    //存储小区的信息
    ParkListBean.DataBean parkBean = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
//        ImageView view = new ImageView(this);
//        view.setLayoutParams(new ViewGroup.LayoutParams(30,30));
//        view.setImageResource(R.drawable.supermarket_white);

        parkBean = (ParkListBean.DataBean) getIntent().getExtras().getSerializable("park_bean");
        setActionBar(parkBean.getName(), R.drawable.nav_return, 0);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bdmapView);
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        mLocationClient = new LocationClient(this);     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        //缩放级别
        mBaiduMap.setMaxAndMinZoomLevel(3.0f, 21f);
        //设置定位的一些基本属性属性
        initLocation();
//        开启定位
        mLocationClient.start();

        //添加覆盖物
        getDatas();
    }

    /**
     * 添加覆盖物
     */
    private void getDatas() {
        if (parkBean == null) {
            return;
        }

        RequestParams params = new RequestParams(Const.WuYe.URL_PARK_LIST_DETAILS);
        params.addParameter("districtId", parkBean.getDistrictId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<ParkMapBean>>() {
                }.getType();
                ArrayList<ParkMapBean> parkMapBean = gson.fromJson(result, listType);
                for (int i = 0; i < parkMapBean.size(); i++) {
                    addOverlay(parkMapBean.get(i));
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

    //第一个覆盖物为地图中心
    private boolean isFirstPosition = true;

    private final int MARKER_BUILD = 137;
    private final int MARKER_RESTAURANT = 138;
    private final int MARKER_SUPERMARKET = 139;

    /**
     * 根据bean添加覆盖物
     *
     * @param parkMapBean
     */
    public void addOverlay(final ParkMapBean parkMapBean) {
        String latitude = parkMapBean.getLatitude();
        String longitude = parkMapBean.getLongitude();
        LatLng llD = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        View view = LayoutInflater.from(this).inflate(R.layout.map_marker_view, null);
        ImageView iv_marker_type = (ImageView) view.findViewById(R.id.iv_marker_type);
        switch (parkMapBean.getType()) {
            case MARKER_BUILD://建筑
                iv_marker_type.setImageResource(R.drawable.building);
                break;
            case MARKER_RESTAURANT://餐厅
                iv_marker_type.setImageResource(R.drawable.restaurant);
                break;
            case MARKER_SUPERMARKET://超市
                iv_marker_type.setImageResource(R.drawable.supermarket);
                break;
        }
        BitmapDescriptor bdGround = BitmapDescriptorFactory.fromView(view);
        MarkerOptions ooB = new MarkerOptions().position(llD).icon(bdGround);
        ooB.perspective(true);
        final Marker markerB = (Marker) mBaiduMap.addOverlay(ooB);
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                View view = LayoutInflater.from(ActivityParkNavigation.this).inflate(R.layout.map_marker_pop_view, null);
                ImageView iv_map_marker_pop = (ImageView) view.findViewById(R.id.iv_map_marker_pop);
                TextView tv = (TextView) view.findViewById(R.id.tv_pop);
                if (marker == markerB) {
                    tv.setText(parkMapBean.getName());
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mBaiduMap.hideInfoWindow();
                        }
                    });
                    switch (parkMapBean.getType()) {
                        case MARKER_BUILD://建筑
                            iv_map_marker_pop.setImageResource(R.drawable.building_white);
                            break;
                        case MARKER_RESTAURANT://餐厅
                            iv_map_marker_pop.setImageResource(R.drawable.restaurant_white);
                            break;
                        case MARKER_SUPERMARKET://超市
                            iv_map_marker_pop.setImageResource(R.drawable.supermarket_white);
                            break;
                    }
                    LatLng ll = marker.getPosition();
                    InfoWindow mInfoWindow = new InfoWindow(view, ll, -60);
                    mBaiduMap.showInfoWindow(mInfoWindow);
                }
                return true;
            }
        });


        if (isFirstPosition) {
            isFirstPosition = !isFirstPosition;

//            MyLocationData locData = new MyLocationData.Builder()
//                    .latitude(Double.parseDouble(latitude))
//                    .longitude( Double.parseDouble(longitude)).build();
//            //设置定位的数据
//            mBaiduMap.setMyLocationData(locData);

            //将第一个覆盖物的位置显示为地图中心
            LatLng ll = new LatLng(Double.parseDouble(latitude),
                    Double.parseDouble(longitude));
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(18.0f);
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

//            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//            设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
//            MyLocationConfiguration config = new MyLocationConfiguration(
//                    MyLocationConfiguration.LocationMode.NORMAL, true, null);
//            mBaiduMap.setMyLocationConfigeration(config);
        }
    }

    /**
     * 更新地图定位信息
     */
    private void updataLocationView(BDLocation location) {
        // map view 销毁后不在处理新接收的位置
        if (location == null || mMapView == null) {
            return;
        }

        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);

        //第一次定位
        if (isFirstLocation) {
            isFirstLocation = false;
            // 开启定位图层
            mBaiduMap.setMyLocationEnabled(true);
            // 构造定位数据
//            LatLng ll = new LatLng(location.getLatitude(),
//                    location.getLongitude());
//            MapStatus.Builder builder = new MapStatus.Builder();
//            builder.target(ll).zoom(18.0f);
//            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//            设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
            MyLocationConfiguration config = new MyLocationConfiguration(
                    MyLocationConfiguration.LocationMode.NORMAL, true, null);
            mBaiduMap.setMyLocationConfigeration(config);

            //添加覆盖物
            getDatas();
        }
    }


    public void initLocation() {
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

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            MyLocation = location;
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


    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
        if (mBaiduMap != null) {
            // 关闭定位图层
            mBaiduMap.setMyLocationEnabled(false);
        }
        if (mMapView != null) {
            //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
            mMapView.onDestroy();
        }
        mMapView = null;
        super.onDestroy();
    }


}
