package gachon.mpclass.seedjumper;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_dumy);

        combo = findViewById(R.id.combo);

        message = "ok";

        MyClientTask myClientTask = new MyClientTask("172.30.1.15", 7777, message);
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
            try {
                //수신
                while(percent < 100)
                {
                    socket = new Socket(dstAddress, dstPort);
                    //송신
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
            }
            Log.d("connection_end",  response);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }
}