package gachon.mpclass.seedjumper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//개인정보 수정 dialog
public class DialogFragmentExample extends DialogFragment {

    FirebaseAuth firebaseAuth;
    private String uid;
    DatabaseReference user;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();//get instance to firebaseAuth
        uid = firebaseAuth.getCurrentUser().getUid();
        user = FirebaseDatabase.getInstance().getReference("users");

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_modify, null);
        builder.setView(view);

        final Button submit = (Button) view.findViewById(R.id.buttonSubmit);
        final EditText name = (EditText) view.findViewById(R.id.editTextModifyName);
        final EditText password = (EditText) view.findViewById(R.id.editTextModifyPassword);
        final EditText weight = (EditText) view.findViewById(R.id.editTextModifyWeight);

        //사용자의 이름 불러오기
        user.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                String uName, uPassword;
                int uWeight;

                uName = user.getName();
                uPassword = user.getPwd();
                uWeight = user.getWeight();

                name.setText(uName);
                password.setText(uPassword);
                weight.setText(String.valueOf(uWeight));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DialogFragment", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = name.getText().toString();
                String strPassword = password.getText().toString();
                int strWeight = Integer.parseInt(weight.getText().toString());

                Intent data = new Intent();
                data.putExtra("name",strName);
                data.putExtra("password",strPassword);
                data.putExtra("weight",strWeight);

                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,data);
                saveInfo(strName, strPassword,strWeight);

                dismiss(); //대화상자를 닫는 함수
            }
        });
        return builder.create();

    }

    //변경된 사용자 정보 데이터 파이어베이스에 업데이트
    @SuppressLint("WrongConstant")
    public void saveInfo(String rName, String rPassword, int rWeight) {
        user.child(uid).child("name").setValue(rName);
        user.child(uid).child("pwd").setValue(rPassword);
        user.child(uid).child("weight").setValue(rWeight);
    }
}
