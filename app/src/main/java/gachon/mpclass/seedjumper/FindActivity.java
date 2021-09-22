package gachon.mpclass.seedjumper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.auth.FirebaseAuth;

public class FindActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "FindActivity";
    private EditText editTextUserEmail;
    private Button buttonFind;
    private ImageView findBackArrow;
    private TextView findPassword;
    private ProgressDialog progressDialog;
    //define firebase object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        editTextUserEmail = (EditText) findViewById(R.id.editTextUserEmail);
        buttonFind = (Button) findViewById(R.id.buttonFind);
        findBackArrow = (ImageView) findViewById(R.id.findBackArrow);
        findPassword = (TextView) findViewById(R.id.findPassword);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        findPassword.setText("Find Password");
        buttonFind.setOnClickListener(this);
        findBackArrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonFind) {
            progressDialog.setMessage("Processing. Please wait...");
            progressDialog.show();
            /* ************Send password reset email************ */
            String emailAddress = editTextUserEmail.getText().toString().trim();

            if (TextUtils.isEmpty(emailAddress)) {
                Toast.makeText(this, "Please input E-mail.", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return;
            }

            firebaseAuth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(FindActivity.this, "Send Email.", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            } else {
                                Toast.makeText(FindActivity.this, "Failed send Email!", Toast.LENGTH_LONG).show();


                            }
                            progressDialog.dismiss();

                        }
                    });
            return;
        }

        if (view == findBackArrow) {
            finish();
        }
    }
}