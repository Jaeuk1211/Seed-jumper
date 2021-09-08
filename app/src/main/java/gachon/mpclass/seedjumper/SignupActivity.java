package gachon.mpclass.seedjumper;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";


    EditText editTextSignUpEmail;
    EditText editTextSignUpPassword, editTextSignUpPasswordConfirm;
    EditText editTextSignUpNickname;
    EditText editTextSignUpName;


    Button buttonSignup;
    ImageView buttonBack;
    TextView textviewMessage;
    private TextView yourAccount, create;
    ProgressDialog progressDialog;
//    FirebaseAuth firebaseAuth;
//    FirebaseFirestore fStore;
    String userID;

    SharedPreferences sh_Pref;
    SharedPreferences.Editor toEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //initializig firebase auth object
//        firebaseAuth = FirebaseAuth.getInstance();//get instance to firebaseAuth
//        fStore = FirebaseFirestore.getInstance();
//
//        /* ********if already logged in,finish this job********* */
//        if (firebaseAuth.getCurrentUser() != null) {
//            finish();
//            startActivity(new Intent(getApplicationContext(), LogoutActivity.class));
//
//        }
        //initializing views
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
    }


    //Register(Sign up)
    private void registerUser() {
        //Get the email & password that the user enters.
        String email = editTextSignUpEmail.getText().toString().trim();
        String password = editTextSignUpPassword.getText().toString().trim();
        String confirmPassword = editTextSignUpPasswordConfirm.getText().toString().trim();
        String name = editTextSignUpName.getText().toString();

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
        if (!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", password)) {
            Toast.makeText(this, "Please keep the password format.", Toast.LENGTH_SHORT).show();
            editTextSignUpPassword.requestFocus();
            return;
        }
        if (!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", confirmPassword)) {
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

//        //creating a new user
//        firebaseAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            finish();
//                            userID = firebaseAuth.getCurrentUser().getUid();
//                            User user = new User(email, name);
//                            //이메일, 이름, 패스워드 각각 firebase에 넣을 수 있도록 수정할 것 (참고 코드 그대로 복사한 것임)
//                            fStore.collection("users").add(user);
//
//                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        } else {
//                            //If error occured
//                            textviewMessage.setText("Error type\n - Email already registered\n -Password at least 6 digits \n - Server error");
//                            Toast.makeText(SignupActivity.this, "Register error", Toast.LENGTH_SHORT).show();
//                        }
//                        progressDialog.dismiss();
//                    }
//                });

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