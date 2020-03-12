package lopez.s.finderfeast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class InfoActivity2 extends AppCompatActivity {

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
        setContentView(R.layout.activity_info2);

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

}