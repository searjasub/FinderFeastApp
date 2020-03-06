package lopez.s.finderfeast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class InfoActivity extends AppCompatActivity {

    private TextView name;
    private TextView distance;
    private TextView address;
    private ImageView thumb;
    private Button website;
    private String rawWebsite;
    private MaterialRatingBar materialRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        name = findViewById(R.id.name);
        distance = findViewById(R.id.distance);
        address = findViewById(R.id.address);
        thumb = findViewById(R.id.thumb);
        website = findViewById(R.id.website);
        materialRatingBar = findViewById(R.id.ratingBar);

        Intent intent = getIntent();
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
}
