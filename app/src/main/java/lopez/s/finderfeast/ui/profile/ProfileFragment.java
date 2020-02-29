package lopez.s.finderfeast.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import lopez.s.finderfeast.LoginActivity;
import lopez.s.finderfeast.R;

import static lopez.s.finderfeast.R.menu.menu;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private ProfileViewModel profileViewModel;
    private ImageButton signOutBtn;
    private TextView nameET;
    private TextView lastNameET;
    private TextView emailET;
    private TextView passwordET;
    private CheckBox checkBox;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
//        final TextView textView = view.findViewById(R.id.text_account);
//        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        nameET = view.findViewById(R.id.sing_up_name);
        lastNameET = view.findViewById(R.id.sing_up_lastName);
        emailET = view.findViewById(R.id.sing_up_email);
        passwordET = view.findViewById(R.id.sing_up_password);
        checkBox = view.findViewById(R.id.checkbox);

        if (user != null) {
            String[] pieces = Objects.requireNonNull(user.getDisplayName()).split(" ");
            nameET.setText(pieces[0]);
            lastNameET.setText(pieces[1]);
            emailET.setText(user.getEmail());
            passwordET.setText("Work in progress");
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    //show password
                    passwordET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //hide password
                    passwordET.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });

        signOutBtn = view.findViewById(R.id.signOutBtn);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == m){
//
//        }
//        return  super.onOptionsItemSelected(item);
//    }
}