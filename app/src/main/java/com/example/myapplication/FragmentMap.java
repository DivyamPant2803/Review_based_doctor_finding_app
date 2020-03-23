package com.example.myapplication;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMap extends Fragment implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    String address,name;


    public FragmentMap() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_map, container, false);
        if (mapFragment == null) {

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map,mapFragment).commit();

        }
        mapFragment.getMapAsync(this);

        Bundle bundle = this.getArguments();
        if(bundle!=null)
            address = getArguments().getString("Address");
            name = getArguments().getString("Name");

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng loc = getLocationFromAddress(getContext(),address);
        //LatLng india = new LatLng(20.8767, 78.0987);
        mMap.addMarker(new MarkerOptions().position(loc).title(name));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        moveCamera(new LatLng(loc.latitude,loc.longitude),15f);
    }
    private void moveCamera(LatLng latLng,float zoom){
        //Log.d(TAG,"moveCamera: moving the camera to: lat "+latLng.latitude+", lng: "+latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
        /*if(!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions().position(latLng).title(title);
            mMap.addMarker(options);
        }*/
        //hideSoftKeyboard();
    }

    public LatLng getLocationFromAddress(Context context, String strAddress){
        Geocoder geocoder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = geocoder.getFromLocationName(strAddress,1);
            if(address==null)
                return null;
            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(),location.getLongitude());
        }catch (IOException e){
            e.printStackTrace();
        }
        return p1;

    }

}
