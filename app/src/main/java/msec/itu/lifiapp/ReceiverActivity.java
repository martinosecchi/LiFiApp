package msec.itu.lifiapp;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static msec.itu.lifiapp.TransmissionUtil.fromBinary;

public class ReceiverActivity extends AppCompatActivity {

    private Button receiveBtn;
    private Button goToTransmitter;
    private String TAG = "RecvActivity";
    private TextView mResultTxt, mRateTxt;
    private RateData rateData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        rateData = RateData.getShared();
        mResultTxt = (TextView) findViewById(R.id.rxResult);
        mRateTxt = (TextView) findViewById(R.id.rxrate);
        receiveBtn = (Button) findViewById(R.id.button_receive);
        goToTransmitter = (Button) findViewById(R.id.goToTransmBtn);

        final Intent intent = new Intent(this, TransmitterActivity.class);
        goToTransmitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        final SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        final Sensor lightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        final LightMonitor lm = new LightMonitor(lightSensor);
        mSensorManager.registerListener(lm, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);

        if(lightSensor == null){
            Log.e(TAG, "No light sensor.");
        }

        receiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread lightThread = new Thread(new LightRunnable(lm));
                lightThread.start();
                while (lightThread.isAlive()){}
                mRateTxt.setText("[" + rateData.getHzRx() + "Hz]");
//                mResultTxt.setText(fromBinary(rateData.getRxValue()));
            }
        });
    }
}
