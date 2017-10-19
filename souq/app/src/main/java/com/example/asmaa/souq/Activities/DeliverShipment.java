package com.example.asmaa.souq.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asmaa.souq.R;
import com.example.asmaa.souq.Responses.ShipmentsResponse;
import com.example.asmaa.souq.utilities.APIs;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import static com.example.asmaa.souq.Activities.LoginPage.Token;

public class DeliverShipment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_shipment);
        Toast.makeText(DeliverShipment.this, Token, Toast.LENGTH_SHORT).show();
        shipments();
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
//                        double longt =ship.getEnd_point_longitude();
                        Toast.makeText(DeliverShipment.this,"رجعت الشحنات ",Toast.LENGTH_LONG).show();
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
                Toast.makeText(DeliverShipment.this, "مقدرتش ارجع داتا من السيرفر", Toast.LENGTH_SHORT).show();
                Log.w("response", "Response Erorr");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer" +" "+ Token);
                return headers;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
