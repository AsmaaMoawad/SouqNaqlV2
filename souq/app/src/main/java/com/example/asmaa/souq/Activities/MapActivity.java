package com.example.asmaa.souq.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asmaa.souq.Responses.ShipmentsResponse;
import com.example.asmaa.souq.R;
import com.example.asmaa.souq.utilities.APIs;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import static com.example.asmaa.souq.Activities.LoginPage.Token;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    void shipments() {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = APIs.Shipments_URL;

        final ProgressDialog loading = ProgressDialog.show(this, null, "جارى تحميل البيانات ......", false, false);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ShipmentsResponse ship = new Gson().fromJson(response, ShipmentsResponse.class);
                        double longt =ship.getEnd_point_longitude();
                        Toast.makeText(MapActivity.this,String.valueOf(longt),Toast.LENGTH_LONG).show();
//                        for (int i=0 ; i<= ship.size();i++) {
//                            mMap.addMarker(new MarkerOptions()
//                                    .position(new LatLng(ship.end_point_latitude,ship.end_point_longitude))
//                                    .title(ship.)
//                                    .snippet(type).icon(BitmapDescriptorFactory.fromResource(R.drawable.activestar)));
//
//                        }


                        loading.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(MapActivity.this, "خطا فى الاستجابه", Toast.LENGTH_SHORT).show();
                Log.w("response", "Response Erorr");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer" + "" + Token);
                return headers;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}

