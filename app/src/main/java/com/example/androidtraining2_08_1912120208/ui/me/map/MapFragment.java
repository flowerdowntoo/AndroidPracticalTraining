package com.example.androidtraining2_08_1912120208.ui.me.map;

import static cn.bmob.v3.Bmob.getApplicationContext;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.androidtraining2_08_1912120208.R;

public class MapFragment extends Fragment {

    private static final int REQUEST_CODE = 0x1001;
    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private PoiSearch mPoiSearch;
    private LatLng latLng;

    public class MyPoiOverlay extends PoiOverlay{

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int i) {
            if (getPoiResult().getAllPoi() != null) {
                PoiInfo poiInfo = getPoiResult().getAllPoi().get(i);
                if (poiInfo != null) {
                    Toast.makeText(getContext(),
                            "?????????"+poiInfo.name
                                    +"\n?????????"+poiInfo.address
                                    +"\n?????????"+poiInfo.phoneNum, Toast.LENGTH_LONG).show();
                }
            }
            return super.onPoiClick(i);
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView ???????????????????????????????????????
            if (location == null || mMapView == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // ?????????????????????????????????????????????????????????0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            latLng=new LatLng(location.getLatitude(),location.getLongitude());
            mBaiduMap.setMyLocationData(locData);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_map, container, false);
        mMapView=root.findViewById(R.id.bmapView);
        mBaiduMap=mMapView.getMap();
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18.0f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        mBaiduMap.setMyLocationEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                &&root.getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE);
        }
        mLocationClient.setAgreePrivacy(true);//?????????????????????
        //???????????????
        try {
            mLocationClient = new LocationClient(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //??????LocationClientOption??????LocationClient????????????
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // ??????gps
        option.setCoorType("bd09ll"); // ??????????????????
        option.setScanSpan(1000);

        //??????locationClientOption
        mLocationClient.setLocOption(option);

        //??????LocationListener?????????
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        //????????????????????????
        mLocationClient.start();

        MyLocationConfiguration mLocationConfiguration=new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.COMPASS,true,null);
        mBaiduMap.setMyLocationConfiguration(mLocationConfiguration);

        mPoiSearch = PoiSearch.newInstance();
        OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    mBaiduMap.clear();

                    //??????PoiOverlay??????
                    MyPoiOverlay poiOverlay = new MyPoiOverlay(mBaiduMap);

                    //??????Poi????????????
                    poiOverlay.setData(poiResult);

                    //???poiOverlay???????????????????????????????????????
                    poiOverlay.addToMap();
                    poiOverlay.zoomToSpan();
                    mBaiduMap.setOnMarkerClickListener(poiOverlay);
                }
            }
            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

            }
            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
            //??????
            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }
        };
        mPoiSearch.setOnGetPoiSearchResultListener(listener);
        EditText editText=root.findViewById(R.id.editText);
        Button button = root.findViewById(R.id.button);
        button.setOnClickListener(v -> {
            String keyword = editText.getText().toString();
            mPoiSearch.searchNearby(new PoiNearbySearchOption()
                    .location(latLng)
                    .radius(10000)
                    .keyword(keyword));
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        //???activity??????onResume?????????mMapView. onResume ()?????????????????????????????????
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //???activity??????onPause?????????mMapView. onPause ()?????????????????????????????????
        mMapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //???activity??????onDestroy?????????mMapView.onDestroy()?????????????????????????????????
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_CODE){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                mLocationClient.restart();
            }else{
                Toast.makeText(getContext(), "????????????GPS???????????????????????????",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}