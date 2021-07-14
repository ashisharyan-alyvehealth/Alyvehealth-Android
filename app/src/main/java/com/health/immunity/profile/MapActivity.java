package com.health.immunity.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.health.immunity.BaseActivity;
import com.health.immunity.R;
import com.health.immunity.profile.view.PlaceAutoSuggestAdapter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends BaseActivity implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private PlacesClient placesClient;  //Search Suggestion
    private List<AutocompletePrediction> predictionList;
    private Location lastLocation;
    private LocationCallback locationCallback;  //Update the user request
    private View mapView;
    private final float DEFAULT_ZOOM = 18;
    private Button btnDone;
    private Context context;
    AutoCompleteTextView autoText;
    AutocompleteSessionToken token;
    private static LatLng fromPosition = null;
    private static LatLng toPosition = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        context = this;
        autoText = findViewById(R.id.autoText);
        btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(this);
        ImageView crss=findViewById(R.id.crss);
        crss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoText.setText("");
            }
        });
        //getmyDeviceLocation();

        getMapLocationData();

        autoText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                autoText.setText("");
                return false;
            }
        });
    }

    private void getMapLocationData() {

        autoText.setAdapter(new PlaceAutoSuggestAdapter(context, R.layout.list_item));
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        Places.initialize(context, getString(R.string.google_maps_key));
        placesClient = Places.createClient(context);
        token = AutocompleteSessionToken.newInstance();

        /*autoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/
    }

    private LatLng getLatLangFromAddress(String address) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocationName(address, 1);
            if (addressList != null)
            {
                if (addressList.size()>0)
                {
                    Address singleAddress = addressList.get(0);
                    LatLng latLng = new LatLng(singleAddress.getLatitude(), singleAddress.getLongitude());
                    return latLng;

                }
                else
                {
                    return  null;
                }


            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Address getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        try {
            String ss= String.format("%.3f", latLng.latitude);
            ss=ss.substring(ss.lastIndexOf(".") + 1);
            String ss1= String.format("%.3f", latLng.longitude);
            ss1=ss1.substring(ss1.lastIndexOf(".") + 1);
            System.out.println("##################asa"+ss);
            System.out.println("##################asa"+ss1);
            int a1=Integer.parseInt(ss)-3;
            int a2=Integer.parseInt(ss1)-2;
            System.out.println("##################asa"+a1);
            System.out.println("##################asa"+a2);
            String ssa1= String.valueOf(a1)+"5";
            String ssa2= String.valueOf(a2)+"5";
            System.out.println("##################asa"+ssa1);
            System.out.println("##################asa"+ssa2);
            String  ssla= String.valueOf(latLng.latitude);
            String   ss1lo= String.valueOf(latLng.longitude);
            System.out.println("##################asa"+ssla.substring(0,ssla.indexOf('.')));
            System.out.println("##################asa"+ss1lo.substring(0,ss1lo.indexOf('.')));



            addresses = geocoder.getFromLocation((latLng.latitude), (latLng.longitude), 1);
            //   addresses = geocoder.getFromLocation(Double.parseDouble(ssla.substring(0,ssla.indexOf('.'))+"."+ssa1), Double.parseDouble(ss1lo.substring(0,ss1lo.indexOf('.'))+"."+ssa2), 1);
            if (addresses != null) {
                Address address = addresses.get(0);
                String addres = String.valueOf(address);
                addres = addres.substring(addres.lastIndexOf(":") + 1);
                String result = addres.substring(addres.indexOf("[") + 2, addres.indexOf("]") - 1);

                autoText.setText(result);

                return address;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        this.mMap.setOnMarkerDragListener(this);
        this.mMap.setOnMarkerClickListener(this);

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 100, 100);
        }
        //Check GPS Enabled or not
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        SettingsClient settingsClient = LocationServices.getSettingsClient(context);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());
        task.addOnSuccessListener(locationSettingsResponse -> getDeviceLocation());
        task.addOnFailureListener(e -> {
            if (e instanceof ResolvableApiException) {
                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                try {
                    resolvableApiException.startResolutionForResult(activity, ADDRESS_CAPTURE);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void getDeviceLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    lastLocation = task.getResult();
                    if (lastLocation != null) {
                        System.out.println("#############################asasa"+getAddressFromLatLng(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude())));
                        mMap.addMarker(new MarkerOptions().position(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude())).draggable(true));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), DEFAULT_ZOOM));
                    } else {
                        final LocationRequest locationRequest = LocationRequest.create();
                        locationRequest.setInterval(10000);
                        locationRequest.setFastestInterval(5000);
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                if (locationResult == null) {
                                    return;
                                }
                                lastLocation = locationResult.getLastLocation();
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), DEFAULT_ZOOM));
                                fusedLocationProviderClient.removeLocationUpdates(locationCallback);
                            }
                        };
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    }
                } else {
                    Toast.makeText(context, "Unable to get last location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDRESS_CAPTURE) {
            if (resultCode == RESULT_OK) {
                getDeviceLocation();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDone:
                if (TextUtils.isEmpty(autoText.getText().toString()))
                {
                    showToast("Please enter location");
                }
                else
                {
                    Intent intent = new Intent();
                    LatLng latLng = getLatLangFromAddress(autoText.getText().toString());
                    System.out.println("##########################lat" + latLng);

                    if(latLng==null)
                    {
                        Toast.makeText(context, "Location not found", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        intent.putExtra("latitude", "" + latLng.latitude);
                        intent.putExtra("longitude", "" + latLng.longitude);
                        intent.putExtra("address", autoText.getText().toString());
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                }
                break;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        fromPosition = marker.getPosition();

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        toPosition = marker.getPosition();
        LatLng latLng = marker.getPosition();
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            android.location.Address address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
            String addres = String.valueOf(address);
            addres = addres.substring(addres.lastIndexOf(":") + 1);
            String result = addres.substring(addres.indexOf("[") + 2, addres.indexOf("]") - 1);

            autoText.setText(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  /*  private void getmyDeviceLocation() {
        new LocationTracker(context, location -> {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(context, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                String address = addresses.get(0).getAddressLine(0);
                try {
                    if (address != null)
                    {
                        autoText.setText(addresses.get(0).getAddressLine(0));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).onUpdateLocation();
    }*/
}
