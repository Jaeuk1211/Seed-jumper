package gachon.mpclass.seedjumper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ExerciseActivity extends AppCompatActivity {

    private int REQUEST_TEST = 1;
    String message="empty";
    int stop = 1;
    String response = "";
    int percent = 0;
    TextView combo;
    TextView progressPer;
    //private ProgressBar percentage;
    long start;
    long end;
    long exerciseTime;
    FirebaseUser loginUser;
    String uid;
    private FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference userReference = userDatabase.getReference();
    int level = 1;
    int genre = 0;
    String[] clearStage;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_endless);

        loginUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = loginUser != null ? loginUser.getUid() : null;

        DatabaseReference time = userReference.child("users").child(uid).child("record").child("total");
        time.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    long a = ds.getValue(Long.class);
                    long b = ds.getValue(Long.class);
                    exerciseTime = ds.getValue(Long.class);//????????? ?????? ????????? ???????????? ?????? ????????? ?????? ?????????
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        combo = findViewById(R.id.combo);
        //percentage = findViewById(R.id.exercise_progress);
        //progressPer = findViewById(R.id.percent);
        message = Integer.toString(level) + Integer.toString(genre);

        MyClientTask myClientTask = new MyClientTask("192.168.120.190", 7777, message);
        myClientTask.execute();

    }

    public class MyClientTask extends AsyncTask<Void, Void, Void> {
        String dstAddress;
        int dstPort;
        String myMessage = "";

        //constructor
        MyClientTask(String addr, int port, String message){
            dstAddress = addr;
            dstPort = port;
            myMessage = message;
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Socket socket = null;
            myMessage = myMessage.toString();
            start = System.currentTimeMillis();

            try {
                //??????
                while(percent > -1)
                {
                    socket = new Socket(dstAddress, dstPort);
                    //??????
                    OutputStream out = socket.getOutputStream();
                    out.write(myMessage.getBytes());
                    Log.d("connection_message", myMessage);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    InputStream inputStream = socket.getInputStream();

                    while ((bytesRead = inputStream.read(buffer)) != -1){
                        byteArrayOutputStream.write(buffer, 0, bytesRead);
                        response += byteArrayOutputStream.toString("UTF-8");
                    }
                    Log.d("connection_ing",  response);
                    String[] array = response.split("#");
                    percent = Integer.parseInt(array[0]);
                    response = "";
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            combo.setText(array[1]);
                            //percentage.setProgress(Integer.parseInt(array[0]));
                            //progressPer.setText(array[0]);
                        }
                    });
                    Thread.sleep(100);
                }

                socket.close();

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally{
                if(socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                end = System.currentTimeMillis();

                exerciseTime = exerciseTime + ((( end - start )/1000) % 60);
                Log.d("exerciseTime", Long.toString(exerciseTime));

                userReference.child("users").child(uid).child("record").child("total").child("time").setValue(exerciseTime);
                updateAvg((( end - start )/1000) % 60);

                goItemPage();
            }
            Log.d("connection_end",  response);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

    //?????? ?????? ???????????? ????????? ??????
    public void updateAvg(long unitExerciseTime)
    {
        userReference.child("users").child(uid).child("record").child("average").child("time").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long time = (long) dataSnapshot.getValue(Long.class);//????????? ?????? ????????? ????????????
                time = (time + unitExerciseTime) / 2; //????????? ?????????
                userReference.child("users").child(uid).child("record").child("average").child("time").setValue(time);//??????
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ExerciseActivity", String.valueOf(databaseError.toException())); // ????????? ??????
            }
        });
    }

    public void goItemPage(){

        if(exerciseTime > 3600)//?????? ??????
        {
            Intent intent = new Intent(getApplicationContext(), getNewItem.class);
            intent.putExtra("totalTime", Long.toString(exerciseTime));
            intent.putExtra("genre", genre);
            startActivity(intent);
            finish();
        }

    }
}