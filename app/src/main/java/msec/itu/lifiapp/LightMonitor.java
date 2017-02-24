package msec.itu.lifiapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Created by martinosecchi on 03/01/17.
 */

public class LightMonitor implements SensorEventListener {
    public float value;
    private volatile Sensor sensor;

    public LightMonitor(Sensor sensor){
        this.sensor = sensor;
    }

    public Sensor getSensor(){
        return sensor;
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        value = event.values[0];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
