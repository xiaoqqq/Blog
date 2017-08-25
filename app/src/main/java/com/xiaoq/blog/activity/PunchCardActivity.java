package com.xiaoq.blog.activity;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.xiaoq.base.utils.Logger;
import com.xiaoq.blog.R;

/**
 * Created by user on 2017/8/23.
 * packageName：com.xiaoq.blog.activity.
 * projectName: Blog.
 * email: xiao.qing@wonhigh.cn
 * desc: TODO
 */
public class PunchCardActivity extends AppCompatActivity implements AMap.OnMyLocationChangeListener, LocationSource, AMap.OnMapLoadedListener {

    private static final java.lang.String TAG = PunchCardActivity.class.getSimpleName();
    private MapView mMapView = null;
    private AMap aMap;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private float mBaseScalePx; // 设置最开始的缩放级别所对应的比例
    private float mChangeFinishScalePx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_card);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        init();


    }

    private void init() {
        if (aMap == null) {
            aMap = mMapView.getMap();
            setUpMap();
        }
    }

    private void setUpMap() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.showIndoorMap(true);
        // aMap.setLocationSource(this);
        //设置希望展示的地图缩放级别
        // CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(17);
        // aMap.moveCamera(cameraUpdate);
        aMap.setOnMyLocationChangeListener(this);
        aMap.setOnMapLoadedListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }


    @Override
    public void onMyLocationChange(Location location) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(17);
        aMap.moveCamera(cameraUpdate);
        float scalePerPixel = aMap.getScalePerPixel();
        Logger.i(TAG, location.getLatitude() + ">>>>" + scalePerPixel + "<<<<<" + location.getLongitude());
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                Logger.i(TAG, "onCameraChange");
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                mChangeFinishScalePx = aMap.getScalePerPixel();
                Logger.i(TAG, "onCameraChangeFinish" + "........" + mChangeFinishScalePx);

            }
        });

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        Logger.i(TAG, "caonima");
        CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(17);
        aMap.moveCamera(cameraUpdate);


    }

    @Override
    public void deactivate() {

    }

    /*地图加载完成的回调	AMap.OnMapLoadedListener*/
    @Override
    public void onMapLoaded() {
        //设置希望展示的地图缩放级别
        CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(17);
        aMap.moveCamera(cameraUpdate);
        mBaseScalePx = aMap.getScalePerPixel();
        float maxZoomLevel = aMap.getMaxZoomLevel();
        float minZoomLevel = aMap.getMinZoomLevel();
        Logger.i(TAG, "地图加载完成" + mBaseScalePx);
    }
}
