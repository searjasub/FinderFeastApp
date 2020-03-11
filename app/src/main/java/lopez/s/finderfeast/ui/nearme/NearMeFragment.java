package lopez.s.finderfeast.ui.nearme;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import cz.msebera.android.httpclient.Header;
import lopez.s.finderfeast.InfoActivity;
import lopez.s.finderfeast.R;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.loopj.android.http.RequestParams;
//import lopez.s.finderfeast.RestaurantConnection;

public class NearMeFragment extends Fragment {

    private NearMeViewModel nearMeViewModel;
    private double lat = 40.7661;
    private double lon = -111.8906;
    private static final String BASE_URL = "https://developers.zomato.com/api/v2.1/";
    private static String keyCode = "d3b965d17f5d9cdd0c08e4d1d6ed47e2";
    private static AsyncHttpClient client = new AsyncHttpClient();
    private LinearLayout linearLayout;
    private ScrollView scrollView;

    @SuppressLint("FragmentLiveDataObserve")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        nearMeViewModel =
                ViewModelProviders.of(this).get(NearMeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_near_me, container, false);
        nearMeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        requestPermission();
        getUserLocation();
        requestPermission();

        linearLayout = root.findViewById(R.id.near_me_linear);
        scrollView = root.findViewById(R.id.scrollView);

        getNearBy();
        return root;
    }

    private void getNearBy() {
        String url = "search";
        int radius = 1000;
        client.addHeader("user-key", keyCode);
        RequestParams rp = new RequestParams();

        rp.add("count", "20");
        rp.add("lat", Double.toString(lat));
        rp.add("lon", Double.toString(lon));
        rp.add("radius", Double.toString(radius));
        Log.d("asd", "" + client.isLoggingEnabled());
        Log.d("asd", "" + getAbsoluteUrl(url));
        client.get(getAbsoluteUrl(url), rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] header, final JSONObject response) {
                Log.d("asd", "---------------- this is response : " + response);
                try {
                    response.getJSONArray("restaurants");
                    System.out.println(response.getJSONArray("restaurants").length());
                    for (int i = 0; i < response.getJSONArray("restaurants").length(); i++) {

                        if (!response.getJSONArray("restaurants").getJSONObject(i).getJSONObject("restaurant").getString("featured_image").isEmpty()) {

                            double resLat = response.getJSONArray("restaurants").getJSONObject(i).getJSONObject("restaurant").getJSONObject("location").getDouble("latitude");
                            double resLon = response.getJSONArray("restaurants").getJSONObject(i).getJSONObject("restaurant").getJSONObject("location").getDouble("longitude");
                            double distance = getDistance("K", 0, 0, resLon, resLat);
                            System.out.println(response.getJSONArray("restaurants").getJSONObject(i).getJSONObject("restaurant").getString("name"));
                            System.out.println(response.getJSONArray("restaurants").getJSONObject(i).getJSONObject("restaurant").getString("featured_image"));
                            System.out.println(distance);

                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


                            MaterialCardView cardView = new MaterialCardView(getContext());
                            cardView.setPreventCornerOverlap(true);
//                            cardView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            cardView.setMinimumHeight(500);
                            cardView.setLayoutParams(params);

                            if (i == response.getJSONArray("restaurants").length()) {
                                params.setMargins(40, 40, 40, 120);
                            } else {
                                params.setMargins(40, 40, 40, 40);
                            }
                            cardView.setLayoutParams(params);

                            LinearLayout childLinear = new LinearLayout(getContext());
                            childLinear.setOrientation(LinearLayout.VERTICAL);
                            childLinear.setGravity(Gravity.CENTER_HORIZONTAL);

                            ImageView thumbImageView = new ImageView(getContext());
                            thumbImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
                            thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            Glide.with(getContext()).load(response.getJSONArray("restaurants").getJSONObject(i).getJSONObject("restaurant").getString("featured_image")).into(thumbImageView);

                            LinearLayout l2 = new LinearLayout(getContext());
                            l2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            l2.setOrientation(LinearLayout.VERTICAL);

                            TextView restaurantNameTV = new TextView(getContext());
                            restaurantNameTV.setGravity(Gravity.CENTER);
                            restaurantNameTV.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            restaurantNameTV.setPadding(0, 20, 0, 20);
                            restaurantNameTV.setTextSize(20);
                            restaurantNameTV.setTypeface(Typeface.DEFAULT_BOLD);
                            restaurantNameTV.setText(response.getJSONArray("restaurants").getJSONObject(i).getJSONObject("restaurant").getString("name"));

//                            TextView distanceTV = new TextView(getContext());
//                            distanceTV.setGravity(Gravity.CENTER);
//                            distanceTV.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                            distanceTV.setPadding(0, 0, 0, 20);
//                            distanceTV.setText(distance + "");
                            MaterialRatingBar materialRatingBar = new MaterialRatingBar(getContext());
                            materialRatingBar.setForegroundGravity(Gravity.TOP);
                            materialRatingBar.setEnabled(false);
                            materialRatingBar.setNumStars(5);
                            materialRatingBar.setRating(Float.parseFloat(response.getJSONArray("restaurants").getJSONObject(i).getJSONObject("restaurant").getJSONObject("user_rating").getString("aggregate_rating")));
                            materialRatingBar.setPadding(0, 0, 0, 20);

                            childLinear.addView(thumbImageView);
                            childLinear.addView(l2);
                            l2.addView(restaurantNameTV);
                            l2.addView(materialRatingBar);

                            cardView.addView(childLinear);
                            linearLayout.addView(cardView);

                            final double distance2 = distance;

                            final int finalI = i;
                            cardView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), InfoActivity.class);
                                    try {
                                        intent.putExtra("name", response.getJSONArray("restaurants").getJSONObject(finalI).getJSONObject("restaurant").getString("name"));
                                        intent.putExtra("url", response.getJSONArray("restaurants").getJSONObject(finalI).getJSONObject("restaurant").getString("url"));
                                        intent.putExtra("rating", response.getJSONArray("restaurants").getJSONObject(finalI).getJSONObject("restaurant").getJSONObject("user_rating").getString("aggregate_rating"));
                                        intent.putExtra("distance", distance2 + "");
                                        intent.putExtra("address",response.getJSONArray("restaurants").getJSONObject(finalI).getJSONObject("restaurant").getJSONObject("location").getString("address"));
                                        intent.putExtra("picture", response.getJSONArray("restaurants").getJSONObject(finalI).getJSONObject("restaurant").getString("featured_image"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    startActivity(intent);
                                }
                            });
                        } else {
                            System.out.println("No picture for " + response.getJSONArray("restaurants").getJSONObject(i).getJSONObject("restaurant").getString("name"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] header, String message, Throwable exception) {
                Log.d("asd", "failure: " + statusCode + " " + exception + " " + message + " ");
                Toast.makeText(getContext(), "Failure to get restaurants", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private double getDistance(String unit, double lon1, double lat1, double lon2, double lat2) {
        double radLat1 = Math.PI * lat1 / 180;
        double radLat2 = Math.PI * lat2 / 180;
        double theta = lon1 - lon2;
        double radTheta = Math.PI * theta / 180;
        double dist = Math.sin(radLat1) * Math.sin(radLat2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radTheta);
        if (dist > 1) {
            dist = 1;
        }
        dist = Math.acos(dist);
        dist = dist * 180 / Math.PI;
        dist = dist * 60 * 1.1515;
        if (unit.equals("K")) {
            dist = dist * 1.609344;
        }
        if (unit.equals("N")) {
            dist = dist * 0.8684;
        }
        return dist;
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    private void getUserLocation() {
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("There are no permissions granted.");
        } else {
            LocationManager locationmanager = (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(Context.LOCATION_SERVICE);
            Location location = Objects.requireNonNull(locationmanager).getLastKnownLocation(LocationManager.GPS_PROVIDER);
            lat = Objects.requireNonNull(location).getLatitude();
            lon = location.getLongitude();
        }
    }

}