package gachon.mpclass.seedjumper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String DUPLICATE_USER_MESSAGE = "User with this email already exist.";
    private static final String USERS_KEY = "users";
    private static final String REQUIRED_FIELDS_MESSAGE = "All fields are required";
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";
    private static final String PASSWORD_LENGTH_MESSAGE = "Password must be at least 6 chars long";

    private Button buttonSignup;
    private ImageView buttonBack;
    private TextView textviewMessage;
    private TextView yourAccount, create;

    private EditText editTextSignUpEmail;
    private EditText editTextSignUpPassword, editTextSignUpPasswordConfirm;
    private EditText editTextSignUpName;
    private EditText editTextSignUpNickname;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();


    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        editTextSignUpName = (EditText) findViewById(R.id.editTextSignUpName);
        editTextSignUpEmail = (EditText) findViewById(R.id.editTextSignUpEmail);
        editTextSignUpPassword = (EditText) findViewById(R.id.editTextSignUpPassword);
        editTextSignUpPasswordConfirm = (EditText) findViewById(R.id.editTextSignUpPasswordConfirm);
        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textviewMessage = (TextView) findViewById(R.id.textviewMessage);
        create = (TextView) findViewById(R.id.create);
        yourAccount = (TextView) findViewById(R.id.yourAccount);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        progressDialog = new ProgressDialog(this);

        create.setText("Create");
        yourAccount.setText(" your Account");
        buttonSignup.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    private void registerUser() {
        final String name = editTextSignUpName.getText().toString().trim();
        final String email = editTextSignUpEmail.getText().toString().trim();
        String password = editTextSignUpPassword.getText().toString().trim();
        String confirmPassword = editTextSignUpPasswordConfirm.getText().toString().trim();

        //Check whether the email and password are empty or not.
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please input Email.", Toast.LENGTH_SHORT).show();
            editTextSignUpEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please input Password.", Toast.LENGTH_SHORT).show();
            editTextSignUpPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Please input Confirm Password.", Toast.LENGTH_SHORT).show();
            editTextSignUpPasswordConfirm.requestFocus();
            return;
        }
        //Check whether email and password format is correct
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Not in email format.", Toast.LENGTH_SHORT).show();
            editTextSignUpEmail.requestFocus();
            return;
        }
        if (!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{6,20}$", password)) {
            Toast.makeText(this, "Please keep the password format.", Toast.LENGTH_SHORT).show();
            editTextSignUpPassword.requestFocus();
            return;
        }
        if (!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{6,20}$", confirmPassword)) {
            Toast.makeText(this, "Please keep the confirm password format.", Toast.LENGTH_SHORT).show();
            editTextSignUpPasswordConfirm.requestFocus();
            return;
        }

        //Check whether password equals password confirmed
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Please check password again.", Toast.LENGTH_SHORT).show();
            editTextSignUpPassword.setText("");
            editTextSignUpPasswordConfirm.setText("");
            editTextSignUpPassword.requestFocus();
            return;
        }

        //If the email and password are entered correctly, continue.
        progressDialog.setMessage("Registering. Please wait...");
        progressDialog.show();


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String uid = mAuth.getCurrentUser().getUid();
                            User newUser = new User(uid, email, name, password);
                            //newUser.setCreditCard(creditCard);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(uid)
                                    .setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        setInitialRecord();
                                        Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                    } else {
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });


                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                Toast.makeText(getApplicationContext(), "Email Already Exists!!", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    //총 운동시간 초기 데이터 구축
    public void setInitialRecord() {
        String uid = mAuth.getCurrentUser().getUid();
        Record record = new Record(0, 0,0);
        databaseReference.child("users").child(uid).child("record").child("total").setValue(record);

    }

    /* *******Register Button ******** */
    @Override
    public void onClick(View view) {
        if (view == buttonSignup) {
            registerUser();

        }

        /* *******go to Login page ******** */
        if (view == buttonBack) {
            finish();
        }

    }
}
