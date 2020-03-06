package lopez.s.finderfeast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText name;
    private EditText email;
    private EditText lastName;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = findViewById(R.id.sing_up_name);
        email = findViewById(R.id.sing_up_email);
        lastName = findViewById(R.id.sing_up_lastName);
        password = findViewById(R.id.sing_up_password);

        mAuth = FirebaseAuth.getInstance();

    }

    public void submit(View view) {
        signUp(email.getText().toString(), password.getText().toString());
    }

    private void signUp(final String email, String password) {
        if (email.isEmpty()) {
            this.email.setError("Please provide an email.");
        } else if (password.isEmpty()) {
            this.password.setError("Please provide a password.");
        } else if (password.length() < 6) {
            this.password.setError("Password needs to be longer than 6 characters.");
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Check sign up successful and change the screen to be logged in.

                                final FirebaseUser user = mAuth.getCurrentUser();

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name.getText().toString().trim() + " " + lastName.getText().toString().trim())
                                        .build();

                                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignUpActivity.this.getApplicationContext(), "Account for " + user.getDisplayName() + " has been created.", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignUpActivity.this.getApplicationContext(), "Sign-up unsuccessful", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    //This method will change the screen from the sign up page back to the main login page
    public void back(View view) {

    }
}
