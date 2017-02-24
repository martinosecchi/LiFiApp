package msec.itu.lifiapp;

import android.util.Log;

import static msec.itu.lifiapp.TransmissionUtil.ETX;
import static msec.itu.lifiapp.TransmissionUtil.STX;

/**
 * Created by martinosecchi on 03/01/17.
 */

public class LightRunnable implements Runnable {
    private LightMonitor lightMonitor;
    private int sleepTime;
    private int duration;

    public LightRunnable(LightMonitor lightMtr){
        this.lightMonitor = lightMtr;
        this.sleepTime = 1; //ms
        duration = 5000;
    }
    @Override
    public void run() {
        MyQueue buff = new MyQueue<String>(24);
        MyQueue read = new MyQueue();
        float curr, prev;
        if (lightMonitor.getSensor() == null){
            Log.e("Light Thread", "no sensor attached.");
            return;
        }
        int i = 0;
        boolean started = false;
        String value;
        long startTime = System.currentTimeMillis();
        while(i < duration ){//|| buff.joinToString().equals(ETX)){
//            Log.i("LIGHT", "value: " + lightMonitor.value);
//            Log.i("LIGHT", "buf.joinToString(): " + buff.joinToString());
//            if (!started && buff.joinToString().equals(STX)){
//                started = true;
//            }
//            prev = curr;
//            curr = lightMonitor.value;
//            if (curr <= prev){
//                value = "0";
//            } else {
//                value = "1";
//            }
//            buff.addLast(value);
//            if (!started)
//                read.addLast(value);
            try {
                Thread.sleep(sleepTime);
            } catch( InterruptedException exn){}
            i++;
        }

        float estimatedTime = System.currentTimeMillis() - startTime;
        estimatedTime = estimatedTime / 1000f;
//        Log.i("LIFI Rx","It took " + estimatedTime + "s to read " + duration + " times. ["+duration/estimatedTime+" Hz]");

        RateData rateData = RateData.getShared();
        rateData.setHzRx(duration/estimatedTime);
//        rateData.setRxValue(buff.joinToString());
    }
}
