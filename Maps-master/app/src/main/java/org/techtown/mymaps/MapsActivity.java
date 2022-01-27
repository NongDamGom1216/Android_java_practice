package org.techtown.mymaps;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
//https://console.developers.google.com/apis/dashboard?project=my-maps-296409&supportedpurview=project
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    //각 지역의 위도, 경도 좌표를 담은 객체 변수
    private static final LatLng SEOUL = new LatLng(37.566535, 126.977969);
    private static final LatLng DAEJEON = new LatLng(36.350412, 127.384548);
    private static final LatLng BUSAN = new LatLng(35.179554, 129.075642);

    //각 지역에 표시할 마커 변수
    private Marker mSeoul;
    private Marker mDaejeon;
    private Marker mBusan;

    private GoogleMap mMap; //구글맵 변수 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        //SupprotMapFragment는 레이아웃에 만든 프래그먼트의 id를 참조 후 연결
        mapFragment.getMapAsync(this); //getMapAsync는 프래그먼트에 콜백을 설정한다
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    // 지도가 준비될 때 호출
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add some markers to the map, and add a data object to each marker.

        //addMarker를 통해 각 지역에 마커를 생성한다
        mSeoul = mMap.addMarker(new MarkerOptions().position(SEOUL).title("SEOUL"));
        mSeoul.setTag(0);

        mDaejeon = mMap.addMarker(new MarkerOptions()
                .position(DAEJEON).title("Daejeon")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.androidimage)));
        //androidimage.png를 불러와 마커로 사용한다.
        mDaejeon.setTag(0);

        mBusan = mMap.addMarker(new MarkerOptions().position(BUSAN).title("Busan"));
        mBusan.setTag(0);

        mMap.setOnMarkerClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL)); //카메라를 SEOUL 위치로
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Integer clickCount = (Integer) marker.getTag();

        if (clickCount != null) {
            //클릭했을 때 해당 마커의 이름과 카운트를 보여준다.
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this, marker.getTitle() + " 가 클릭되었음, 클릭횟수: " + clickCount, Toast.LENGTH_SHORT).show();
        }

        return false;
    }
}