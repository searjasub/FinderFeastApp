package lopez.s.finderfeast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class InfoActivity extends AppCompatActivity {

    private TextView name;
    private TextView distance;
    private TextView address;
    private ImageView thumb;
    private Button website;
    private Button favorite;
    private String rawWebsite;
    private MaterialRatingBar materialRatingBar;
    private FirebaseAuth fba;
    private FirebaseUser fbu;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        name = findViewById(R.id.name);
        distance = findViewById(R.id.distance);
        address = findViewById(R.id.address);
        thumb = findViewById(R.id.thumb);
        website = findViewById(R.id.website);
        favorite = findViewById(R.id.favorite);
        fba = FirebaseAuth.getInstance();
        fbu = fba.getCurrentUser();
        materialRatingBar = findViewById(R.id.ratingBar);

        intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        distance.setText(intent.getStringExtra("distance"));
        address.setText(intent.getStringExtra("address"));
        Glide.with(this).load(intent.getStringExtra("picture")).into(thumb);
        rawWebsite = intent.getStringExtra("url");

        materialRatingBar.setForegroundGravity(Gravity.TOP);
        materialRatingBar.setEnabled(false);
        materialRatingBar.setNumStars(5);
        materialRatingBar.setRating(Float.parseFloat(intent.getStringExtra("rating")));
        materialRatingBar.setPadding(0, 0, 0, 20);
    }

    public void goToWebsite(View view) {
        Uri uriUrl = Uri.parse(rawWebsite);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public void addToFavorites(View view){
        String email = fbu.getEmail();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> restaurant = new HashMap<>();
        String rName = intent.getStringExtra("name");
        System.out.println(rName);
        restaurant.put("name",intent.getStringExtra("name"));
        restaurant.put("distance",intent.getStringExtra("distance"));
        restaurant.put("address",intent.getStringExtra("address"));
        restaurant.put("picture",intent.getStringExtra("picture"));
        restaurant.put("website",rawWebsite);
        restaurant.put("rating",intent.getStringExtra("rating"));
        db.collection(email).document(rName).set(restaurant);
    }
}