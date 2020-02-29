package lopez.s.finderfeast.ui.nearme;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
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

import lopez.s.finderfeast.R;
//import lopez.s.finderfeast.RestaurantConnection;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class NearMeFragment extends Fragment {

    private NearMeViewModel nearMeViewModel;
    private Button button;
    private Button button2;
//    private FusedLocationProviderClient client;
    private double lat = 40;
    private double lon = -111;

    private ListView listview;
    String list[] = {"Jelly", "Peanut", "Butter", "Jam", "Bread", "Ham", "Turkey", "Sausage", "Mustard", "Mayonnaise", "Lettuce", "Tomato"};

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

//        requestPermission();
//        client = LocationServices.getFusedLocationProviderClient(getActivity());
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);

        listview.setAdapter(arrayAdapter);
        return root;
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION}, 1);
    }

}