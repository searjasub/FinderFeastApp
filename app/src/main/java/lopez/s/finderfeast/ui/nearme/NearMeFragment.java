package lopez.s.finderfeast.ui.nearme;

import android.annotation.SuppressLint;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.loopj.android.http.RequestParams;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.Objects;

import lopez.s.finderfeast.R;
//import lopez.s.finderfeast.RestaurantConnection;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class NearMeFragment extends Fragment {

    private NearMeViewModel nearMeViewModel;
    private Button button;
    private Button button2;
//    private FusedLocationProviderClient client;
    private double lat = 40.7661;
    private double lon = -111.8906;
    private static final String BASE_URL = "https://developers.zomato.com/api/v2.1/";
    private static String keyCode = "d3b965d17f5d9cdd0c08e4d1d6ed47e2";
    private static AsyncHttpClient client = new AsyncHttpClient();
    TextView textbox;

    private ListView listview;
    @SuppressLint("FragmentLiveDataObserve")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        nearMeViewModel =
                ViewModelProviders.of(this).get(NearMeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_near_me, container, false);
        final TextView textView = root.findViewById(R.id.text_near_me);
        nearMeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        requestPermission();
        getUserLocation();
//        button2 = root.findViewById(R.id.getCategoriesBtn);
//        button2.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                if(ActivityCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//                RequestParams rp = new RequestParams();
//                TextView textbox = root.findViewById(R.id.categories);
//                textbox.setText("Please wait....");
//                textbox.setText((CharSequence)RestaurantConnection.getNearby(lat, lon, 0));
//
//            }
//        });
        requestPermission();
//        client = LocationServices.getFusedLocationProviderClient(getActivity());
        button2 = root.findViewById(R.id.getCategoriesBtn);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(ActivityCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                textbox = root.findViewById(R.id.categories);
                textbox.setText("Please wait....");
                getNearBy(null);
            }
        });
//
//
//        button = root.findViewById(R.id.getLocationBtn);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(ActivityCompat.checkSelfPermission(root.getContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//
//                client.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        if(location!= null) {
//                            TextView textView = root.findViewById(R.id.location);
//                            textView.setText("Latitude: " + location.getLatitude() + " \nLongitude:" + location.getLongitude());
//                            lat = location.getLatitude();
//                            lon = location.getLongitude();
//                        }
//                    }
//                });
//            }
//        });
        listview = root.findViewById(R.id.listview);

        return root;
    }

    public void getNearBy(RequestParams params){
        String url = "search";
        int radius = 0;
        client.addHeader("user-key", keyCode);
        RequestParams rp = new RequestParams();
        if (radius == 0) {
            radius = 1000;
        }
        rp.add("count", "10");
        rp.add("lat", Double.toString(lat));
        rp.add("lon", Double.toString(lon));
        rp.add("radius", Double.toString(radius));
        Log.d("asd", "" + client.isLoggingEnabled());
        Log.d("asd", "" + getAbsoluteUrl(url));
        client.get(getAbsoluteUrl(url), rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] header, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("asd", "---------------- this is response : " + response);
                try {
                    response.getJSONArray("restaurants");
                    double resLat = response.getJSONArray("restaurants").getJSONObject(0).getJSONObject("restaurant").getJSONObject("location").getDouble("longitude");
                    double resLon = response.getJSONArray("restaurants").getJSONObject(0).getJSONObject("restaurant").getJSONObject("location").getDouble("latitude");
                    double distance = getDistance("m", 0, 0, resLon, resLat);
                    textbox.setText(response.getJSONArray("restaurants").getJSONObject(0).getJSONObject("restaurant").getString("name") + distance);
                    String[] list = new String[response.getJSONArray("restaurants").length()];
                    for(int i = 0; i < response.getJSONArray("restaurants").length(); i++) {
                        list[i] = response.getJSONArray("restaurants").getJSONObject(i).getJSONObject("restaurant").getString("name") + distance;
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
                    listview.setAdapter(arrayAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*try {
                    JSONObject serverResp = new JSONObject(response.toString());
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }*/
            }
            @Override
            public void onFailure(int statusCode, Header[] header, String message, Throwable exception) {
                Log.d("asd", "failure: " + statusCode + " " + exception + " " + message + " ");
                textbox.setText("Failure to get restaurants");
            }
        });
    }

    private double getDistance(String unit, double lon1, double lat1, double lon2, double lat2) {
        double radlat1 = Math.PI * lat1/180;
        double radlat2 = Math.PI * lat2/180;
        double theta = lon1-lon2;
        double radtheta = Math.PI * theta/180;
        double dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta);
        if (dist > 1) {
            dist = 1;
        }
        dist = Math.acos(dist);
        dist = dist * 180/Math.PI;
        dist = dist * 60 * 1.1515;
        if (unit=="K") { dist = dist * 1.609344; }
        if (unit=="N") { dist = dist * 0.8684; }
        return dist;
    }
    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    private void getUserLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("There are no permissions granted.");
        } else {
            LocationManager locationmanager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Location location = locationmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            lat = location.getLatitude();
            lon = location.getLongitude();
        }
    }

}