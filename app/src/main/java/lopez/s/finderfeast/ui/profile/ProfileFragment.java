package lopez.s.finderfeast.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import lopez.s.finderfeast.LoginActivity;
import lopez.s.finderfeast.R;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private ProfileViewModel profileViewModel;
    private ImageButton signOutBtn;
    private TextView nameET;
    private TextView lastNameET;
    private TextView emailET;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        nameET = view.findViewById(R.id.sign_up_name);
        lastNameET = view.findViewById(R.id.sign_up_lastName);
        emailET = view.findViewById(R.id.sign_up_email);

        if (user != null) {
            String[] pieces = Objects.requireNonNull(user.getDisplayName()).split(" ");
            nameET.setText(pieces[0]);
            lastNameET.setText(pieces[1]);
            emailET.setText(user.getEmail());
        }


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

}