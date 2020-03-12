package lopez.s.finderfeast.ui.favorites;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

import lopez.s.finderfeast.InfoActivity;
import lopez.s.finderfeast.InfoActivity2;
import lopez.s.finderfeast.R;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class FavoritesFragment extends Fragment {


    private FavoritesViewModel favoritesViewModel;
    private LinearLayout linearLayout;
    private ScrollView scrollView;

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
        FirebaseAuth fba = FirebaseAuth.getInstance();
        FirebaseUser fbu = fba.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String email = Objects.requireNonNull(fbu).getEmail();

        db.collection(Objects.requireNonNull(email)).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                params.setMargins(40, 40, 40, 60);


                                MaterialCardView cardView = new MaterialCardView(getContext());
                                cardView.setPreventCornerOverlap(true);
                                cardView.setMinimumHeight(500);
                                cardView.setLayoutParams(params);

                                final String name = document.getString("name");
                                final String address = document.getString("address");
                                final String picture = document.getString("picture");
                                final String rating = document.getString("rating");
                                final String website = document.getString("website");
                                String distance = document.getString("distance");
                                String rawDistance = document.getString("rawDistance");

                                LinearLayout childLinear = new LinearLayout(getContext());
                                childLinear.setOrientation(LinearLayout.VERTICAL);
                                childLinear.setGravity(Gravity.CENTER_HORIZONTAL);

                                ImageView thumbImageView = new ImageView(getContext());
                                thumbImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
                                thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                Glide.with(getContext()).load(picture).into(thumbImageView);

                                LinearLayout l2 = new LinearLayout(getContext());
                                l2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                l2.setOrientation(LinearLayout.VERTICAL);

                                TextView restaurantNameTV = new TextView(getContext());
                                restaurantNameTV.setGravity(Gravity.CENTER);
                                restaurantNameTV.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                restaurantNameTV.setPadding(0, 20, 0, 20);
                                restaurantNameTV.setTextSize(20);
                                restaurantNameTV.setTypeface(Typeface.DEFAULT_BOLD);
                                restaurantNameTV.setText(name);

                                MaterialRatingBar materialRatingBar = new MaterialRatingBar(getContext());
                                materialRatingBar.setForegroundGravity(Gravity.TOP);
                                materialRatingBar.setEnabled(false);
                                materialRatingBar.setNumStars(5);
                                materialRatingBar.setRating(Float.parseFloat(Objects.requireNonNull(rating)));
                                materialRatingBar.setPadding(0, 0, 0, 20);

                                final double distance2 = Math.round(Float.parseFloat(Objects.requireNonNull(rawDistance)));

                                cardView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getActivity(), InfoActivity2.class);
                                        intent.putExtra("name", name);
                                        intent.putExtra("url", website);
                                        intent.putExtra("rating", rating);
                                        intent.putExtra("distance", "About " + distance2 + " miles away");
                                        intent.putExtra("address", address);
                                        intent.putExtra("picture", picture);

                                        startActivity(intent);
                                    }
                                });

                                childLinear.addView(thumbImageView);
                                childLinear.addView(l2);
                                l2.addView(restaurantNameTV);
                                l2.addView(materialRatingBar);

                                cardView.addView(childLinear);
                                linearLayout.addView(cardView);
                            }
                        }
                    }
                });
    }
}