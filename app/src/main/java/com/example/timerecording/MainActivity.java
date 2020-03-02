package com.example.timerecording;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private Button startButton;
    private Button stopButton;
    private TextView counterTextView;

    private Timer timer;
    private MyService myService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        startButton = (Button) findViewById(R.id.buttonStart);
        stopButton = (Button) findViewById(R.id.buttonStop);
        counterTextView = (TextView) findViewById(R.id.textViewCounter);

        startButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (timer != null) {
                    timer.cancel();
                }

                timer = new Timer();
                myService = new MyService();
                timer.schedule(myService, 1000, 10000);
            }
        });

        stopButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
            }
        });
    }

    class MyService extends TimerTask {
        String newString = "";

        @Override
        public void run() {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "dd:MMMM:yyyy HH:mm:ss a", Locale.getDefault());
            final String strDate = simpleDateFormat.format(calendar.getTime());
            newString += strDate + "\n";

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    counterTextView.setText(newString);
                }
            });
        }
    }
}