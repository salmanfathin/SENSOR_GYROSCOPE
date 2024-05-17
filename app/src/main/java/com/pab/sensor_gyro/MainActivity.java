package com.pab.sensor_gyro;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.widget.Toast;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private SensorEventListener gyroscopeEventListener;
    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayer2;
    private TextView voiceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.ehe);
        mediaPlayer2 = MediaPlayer.create(MainActivity.this, R.raw.ehetenandayo);
        voiceText = findViewById(R.id.voiceText);

        if (gyroscopeSensor == null) {
            Toast.makeText(this, "Perangkat Tidak Mempunyai Gyroscope!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gyroscopeSensor != null) {
            sensorManager.registerListener(this, gyroscopeSensor,
                    sensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gyroscopeSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

        @Override
        public void onSensorChanged (SensorEvent event){
            if (event.values[2] > 0.5f) {
                getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                voiceText.setText("VENTI : EHE");
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                }
            } else if (event.values[2] < -0.5f) {
                getWindow().getDecorView().setBackgroundColor(Color.RED);
                voiceText.setText("PAIMON : EHE TE NANDAYO!");
                if (mediaPlayer2 != null) {
                    mediaPlayer2.start();
                }
            }
        }

        @Override
        public void onAccuracyChanged (Sensor sensor,int accuracy){

        }
    }

