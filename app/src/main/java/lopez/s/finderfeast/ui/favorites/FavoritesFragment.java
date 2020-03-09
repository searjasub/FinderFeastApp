package lopez.s.finderfeast.ui.favorites;

import android.content.Intent;
import android.graphics.Typeface;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import lopez.s.finderfeast.InfoActivity;
import lopez.s.finderfeast.R;
import lopez.s.finderfeast.ui.nearme.NearMeFragment;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel favoritesViewModel;
    private static AsyncHttpClient client = new AsyncHttpClient();
    private LinearLayout linearLayout;
    private ScrollView scrollView;
    private List<String> favoritesList = new ArrayList<String>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritesViewModel =
                ViewModelProviders.of(this).get(FavoritesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);

        linearLayout = root.findViewById(R.id.favorites_linear);
        scrollView = root.findViewById(R.id.favorites_scrollView);

        getFavoritesList();

        return root;
    }

    private void getFavoritesList() {
        //above this get the favorites list from the database.

//        for (int i=0; i < favoritesList.size(); i++) {
//
//            if(!favoritesList.isEmpty()) {
//
//                double resLat = response.getJSONArray().getDouble("latitude");
//                double resLon = response.getJSONArray().getDouble("longitude");
//                double distance = getDistance("K", 0, 0, resLon, resLat);
//
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//                MaterialCardView cardView = new MaterialCardView(getContext());
//                cardView.setPreventCornerOverlap(true);
//                cardView.setMinimumHeight(500);
//                cardView.setLayoutParams(params);
//
//                if(i == response.getJSONArray().length) {
//                    params.setMargins(40, 40, 40, 120);
//                }
//                else {
//                    params.setMargins(40, 40, 40, 40);
//                }
//                cardView.setLayoutParams(params);
//
//                LinearLayout childLinear = new LinearLayout(getContext());
//                childLinear.setOrientation(LinearLayout.VERTICAL);
//                childLinear.setGravity(Gravity.CENTER_HORIZONTAL);
//
//                ImageView thumbImageView = new ImageView(getContext());
//                thumbImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
//                thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                Glide.with(getContext()).load(response.getJSONArray().getJSONObejct(i).getString("featured_image")).into(thumbImageView);
//
//                LinearLayout insideCardLayout = new LinearLayout(getContext());
//                insideCardLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                insideCardLayout.setOrientation(LinearLayout.VERTICAL);
//
//                TextView restaurantNameTV = new TextView(getContext());
//                restaurantNameTV.setGravity(Gravity.CENTER);
//                restaurantNameTV.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                restaurantNameTV.setPadding(0, 20, 0, 20);
//                restaurantNameTV.setTextSize(20);
//                restaurantNameTV.setTypeface(Typeface.DEFAULT_BOLD);
//                restaurantNameTV.setText(response.getJSONArray().getJSONObject(i).getString("name"));
//
//                MaterialRatingBar materialRatingBar = new MaterialRatingBar(getContext());
//                materialRatingBar.setForegroundGravity(Gravity.TOP);
//                materialRatingBar.setEnabled(false);
//                materialRatingBar.setNumStars(5);
//                materialRatingBar.setRating(Float.parseFloat(response.getJSONArray().getJSONObject(i).getJSONObject("user_rating").getString("aggregate_rating")));
//                materialRatingBar.setPadding(0, 0, 0, 20);
//
//                childLinear.addView(thumbImageView);
//                childLinear.addView(insideCardLayout);
//                insideCardLayout.addView(restaurantNameTV);
//                insideCardLayout.addView(materialRatingBar);
//
//                cardView.addView(childLinear);
//                linearLayout.addView(cardView);
//
//                final int finalI = i;
//                cardView.setOnClickListener(new View.OnClickListener() {
//                   @Override
//                   public void onClick(View v) {
//                       Intent intent = new Intent(getActivity(), InfoActivity.class);
//                       try {
//                           intent.putExtra("name", response.getJSONArray().getJSONObject(finalI).getString("name"));
//                           intent.putExtra("url", response.getJSONArray().getJSONObject(finalI).getString("url"));
//                           intent.putExtra("rating", response.getJSONArray().getJSONObject(finalI).getString("user_rating"));
//                           intent.putExtra("distance", distance);
//                           intent.putExtra("address", response.getJSONArray().getJSONObject(finalI).getString("address"));
//                           intent.putExtra("picture", response.getJSONArray().getJSONObject(finalI).getString("featured_image"));
//                       } catch (JSONException e) {
//                           e.printStackTrace();
//                       }
//                       startActivity(intent);
//                   }
//                });
//            } else {
//                System.out.println("No picture.");
//            }

//        }

    }

//    @Override
//    public void onFailure(int statusCode, Header[] header, String message, Throwable exception) {
//        Log.d("asd", "failure: " + statusCode + " " + exception + " " + message + " ");
//        Toast.makeText(getContext(), "Failure to get restaurants", Toast.LENGTH_SHORT).show();
//    }

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
}