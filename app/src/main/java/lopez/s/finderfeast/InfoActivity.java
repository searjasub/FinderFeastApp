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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class InfoActivity extends AppCompatActivity {

    private TextView name;
    private TextView distance;
    private TextView address;
    private ImageView thumb;
    private Button website;
    private Button favorite;
    private String rawWebsite;
    private MaterialRatingBar materialRatingBar;
    private DatabaseReference mDatabase;


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

        materialRatingBar = findViewById(R.id.ratingBar);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        distance.setText(intent.getStringExtra("distance"));
        address.setText(intent.getStringExtra("address"));
        Glide.with(this).load(intent.getStringExtra("picture")).into(thumb);
        rawWebsite = intent.getStringExtra("url");

        mDatabase = FirebaseDatabase.getInstance().getReference();

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

    }
}