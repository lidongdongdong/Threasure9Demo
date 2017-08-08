package com.zhuoxin.com.threasure9demo.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.zhuoxin.com.threasure9demo.R;
import com.zhuoxin.com.threasure9demo.commons.ActivityUtils;
import com.zhuoxin.com.threasure9demo.treasure.Area;
import com.zhuoxin.com.threasure9demo.treasure.Treasure;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by MACHENIKE on 2017/8/3.
 */

public class MapFragment extends Fragment implements MapFragmentView{
    private static final int REQUSET_CODE = 100;
    @BindView(R.id.iv_located)
    ImageView mIvLocated;
    @BindView(R.id.btn_HideHere)
    Button mBtnHideHere;
    @BindView(R.id.centerLayout)
    RelativeLayout mCenterLayout;
    @BindView(R.id.iv_scaleUp)
    ImageView mIvScaleUp;
    @BindView(R.id.iv_scaleDown)
    ImageView mIvScaleDown;
    @BindView(R.id.tv_located)
    TextView mTvLocated;
    @BindView(R.id.tv_satellite)
    TextView mTvSatellite;
    @BindView(R.id.tv_compass)
    TextView mTvCompass;
    @BindView(R.id.ll_locationBar)
    LinearLayout mLlLocationBar;
    @BindView(R.id.tv_currentLocation)
    TextView mTvCurrentLocation;
    @BindView(R.id.iv_toTreasureInfo)
    ImageView mIvToTreasureInfo;
    @BindView(R.id.et_treasureTitle)
    EditText mEtTreasureTitle;
    @BindView(R.id.cardView)
    CardView mCardView;
    @BindView(R.id.layout_bottom)
    FrameLayout mLayoutBottom;
    @BindView(R.id.map_frame)
    FrameLayout mMapFrame;
    Unbinder unbinder;
    private BaiduMap mBaiduMap;
    private LatLng mCurrentLocation;
    private boolean isFirst = true;
    private LocationClient mLocationClient;
    private LatLng mCurrentStatus;
    private ActivityUtils mActivityUtils;
    private BitmapDescriptor treasure_dot;
private Marker  mCurrentMarker;
    private BitmapDescriptor treasure_expand;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, null);

        if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},REQUSET_CODE);
        }

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        mActivityUtils = new ActivityUtils(this);

        initView();

        initLocation();
    }
    //初始化位置相关
    private void initLocation() {
        //定位功能
        mBaiduMap.setMyLocationEnabled(true);

        mLocationClient = new LocationClient(getContext().getApplicationContext());

        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        mLocationClient.setLocOption(option);


        mLocationClient.registerLocationListener(mBDLocationListener);

        mLocationClient.start();


    }

    private BDLocationListener mBDLocationListener = new BDLocationListener() {


        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            double latitude = bdLocation.getLatitude();//纬度
            double longitude = bdLocation.getLongitude();//经度

            mCurrentLocation = new LatLng(latitude, longitude);
            String addrStr = bdLocation.getAddrStr();//位置描述
            Log.e("TAG","当前位于:"+addrStr+"经纬度是"+longitude+":"+latitude);


            MyLocationData myLocationData = new MyLocationData.Builder()
                    .accuracy(100f)//精度
                    .latitude(latitude)
                    .longitude(longitude)
                    .build();
            mBaiduMap.setMyLocationData(myLocationData);

            if (isFirst){
                moveToLocation();
                isFirst = false;
            }

        }
    };

    private void initView() {
        treasure_dot = BitmapDescriptorFactory.fromResource(R.mipmap.treasure_dot);
treasure_expand=BitmapDescriptorFactory.fromResource(R.mipmap.treasure_expanded);
        MapStatus mapStatus = new MapStatus.Builder()
                .overlook(0f)
                .rotate(0f)
                .zoom(11)
                .build();

        BaiduMapOptions options = new BaiduMapOptions();
        options.mapStatus(mapStatus);
        options.scaleControlEnabled(false);
        options.zoomControlsEnabled(false);
        options.zoomGesturesEnabled(true);
        //创建MapView对象
        MapView mapView = new MapView(getContext(), options);

        mBaiduMap = mapView.getMap();


        mMapFrame.addView(mapView,0);


        mBaiduMap.setOnMapStatusChangeListener(mOnMapStatusChangeListener);
    mBaiduMap.setOnMarkerClickListener(mOnMarkerClickListener);
    }

    private BaiduMap.OnMarkerClickListener mOnMarkerClickListener=new BaiduMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            if (mCurrentMarker!=null){
                mCurrentMarker.setVisible(true);
            }
            mCurrentMarker=marker;
            mCurrentMarker.setVisible(false);
            InfoWindow infoWindow=new InfoWindow(treasure_expand, marker.getPosition(), 0, new InfoWindow.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick() {
                    mBaiduMap.hideInfoWindow();
                    mCurrentMarker.setVisible(true);
                }
            });
            mBaiduMap.showInfoWindow(infoWindow);
            return false;
        }
    };

    private BaiduMap.OnMapStatusChangeListener mOnMapStatusChangeListener = new BaiduMap.OnMapStatusChangeListener() {
        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChange(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChangeFinish(MapStatus mapStatus) {

            LatLng target = mapStatus.target;
            if (target!=mCurrentStatus){
                //此时认为地图状态真的改变了
                updateView(target);

                mCurrentStatus = target;

            }
        }
    };
    //拿到宝藏
    private void updateView(LatLng mapStatus) {

        double latitude = mapStatus.latitude;//纬度
        double longitude = mapStatus.longitude;//经度

        Area area = new Area();
        area.setMaxLat(Math.ceil(latitude));
        area.setMaxLng(Math.ceil(longitude));
        area.setMinLat(Math.floor(latitude));
        area.setMinLng(Math.floor(longitude));

        new MapFragmentPresenter(this).getTreasureInArea(area);

    }

    //--------------------------------给地图上的控件添加点击事件------------------------
    //点击定位
    @OnClick({R.id.tv_located})
    public void moveToLocation(){

        MapStatus mapStatus = new MapStatus.Builder()
                .zoom(19)
                .target(mCurrentLocation)
                .build();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mapStatus));
    }
    //切换地图类型
    @OnClick({R.id.tv_satellite})
    public void switchMapType(){

        int mapType = mBaiduMap.getMapType();

        mapType=mapType==BaiduMap.MAP_TYPE_NORMAL?BaiduMap.MAP_TYPE_SATELLITE:BaiduMap.MAP_TYPE_NORMAL;
        String msg = mapType==BaiduMap.MAP_TYPE_NORMAL?"卫星":"普通";

        mBaiduMap.setMapType(mapType);
        mTvSatellite.setText(msg);
    }
    //指南针
    @OnClick({R.id.tv_compass})
    public void compass(){
        boolean compassEnabled = mBaiduMap.getUiSettings().isCompassEnabled();
        mBaiduMap.getUiSettings().setCompassEnabled(!compassEnabled);
    }

    @OnClick({R.id.iv_scaleUp,R.id.iv_scaleDown})
    public void switchMapScale(View view){
        switch (view.getId()){
            case R.id.iv_scaleUp:
                //放大
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomIn());
                break;
            case R.id.iv_scaleDown:
                //缩小
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomOut());
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUSET_CODE:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    mLocationClient.requestLocation();
                } else {
                    Toast.makeText(getContext(),"权限不足",Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    //--------------------------实现自视图接口的方法------------------------
    @Override
    public void showMessage(String message) {
        mActivityUtils.showToast(message);
    }

    @Override
    public void setTreasureData(List<Treasure> treasureList) {

        mBaiduMap.clear();
        Log.e("TAG",treasureList.size()+"");

        for (Treasure mTreasure:
                treasureList) {
            Bundle bundle = new Bundle();
            bundle.putInt("treasure_id",mTreasure.getId());

            MarkerOptions markerOptions=new MarkerOptions();
            markerOptions.anchor(0.5f,0.5f);
            markerOptions.extraInfo(bundle);
            markerOptions.icon(treasure_dot);
            markerOptions.position(new LatLng(mTreasure.getLatitude(),mTreasure.getLongitude()));
        mBaiduMap.addOverlay(markerOptions);
        }

    }
}
