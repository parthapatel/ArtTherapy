package edu.csulb.android.arttherapy;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private ShakeActivity mShakeActivity;

    private BackgroundSound backgroundSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ReceiverActivity receiver= new ReceiverActivity();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_USER_PRESENT);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(receiver, filter);

        View v;

        
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeActivity = new ShakeActivity();
        mShakeActivity.setOnShakeListener(new ShakeActivity.OnShakeListener() {

            @Override
            public void onShake(int count) {
				/*
				 * The following method, "handleShakeEvent(count):" is a stub //
				 * method you would use to setup whatever you want done once the
				 * device has been shook.
				 */
                new BackgroundSound().execute();
//                 Intent intent = getIntent();
//                 finish();
//                 startActivity(intent);
		try{
                    setContentView(R.layout.activity_main);
                }catch (Exception e){
                    Log.e("this", e.toString());
                }
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeActivity, mSensor,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        mSensorManager.unregisterListener(mShakeActivity);
        super.onPause();
    }

    public class BackgroundSound extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            MediaPlayer player = MediaPlayer.create(MainActivity.this, R.raw.eraser);
            player.setLooping(false);
            player.setVolume(100,100);
            player.start();

            return null;
        }

    }

}
