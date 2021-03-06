package lopez.s.finderfeast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class InfoActivity extends AppCompatActivity {

    private TextView name;
    private TextView distance;
    private TextView address;
    private ImageView picture;
    private String rawWebsite;
    private MaterialRatingBar materialRatingBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        name = findViewById(R.id.name);
        distance = findViewById(R.id.distance);
        address = findViewById(R.id.address);
        picture = findViewById(R.id.thumb);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        materialRatingBar = findViewById(R.id.ratingBar);

        intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        distance.setText(intent.getStringExtra("distance"));
        address.setText(intent.getStringExtra("address"));
        Glide.with(this).load(intent.getStringExtra("picture")).into(picture);
        rawWebsite = intent.getStringExtra("url");

        materialRatingBar.setForegroundGravity(Gravity.TOP);
        materialRatingBar.setEnabled(false);
        materialRatingBar.setNumStars(5);
        materialRatingBar.setRating(Float.parseFloat(intent.getStringExtra("rating")));
        materialRatingBar.setPadding(0, 0, 0, 20);
    }

    public void  goToWebsite(View view){
        Uri uriUrl = Uri.parse(rawWebsite);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public void goToFavorites(View view) {
        String email = firebaseUser.getEmail();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> restaurant = new HashMap<>();
        String rName = intent.getStringExtra("name");
        System.out.println(rName);
        restaurant.put("name",intent.getStringExtra("name"));
        restaurant.put("distance",intent.getStringExtra("distance"));
        restaurant.put("rawDistance", intent.getStringExtra("rawDistance"));
        restaurant.put("address",intent.getStringExtra("address"));
        restaurant.put("picture",intent.getStringExtra("picture"));
        restaurant.put("website",rawWebsite);
        restaurant.put("rating",intent.getStringExtra("rating"));
        db.collection(email).document(rName).set(restaurant);

        Intent intent = new Intent(InfoActivity.this, MainActivity.class);
        startActivity(intent);

        Toast.makeText(this, "Restaurant: " + rName+ " has been added", Toast.LENGTH_SHORT).show();
    }}
