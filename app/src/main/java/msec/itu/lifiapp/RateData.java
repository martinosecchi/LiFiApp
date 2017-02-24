package msec.itu.lifiapp;

import android.content.Context;

/**
 * Created by martinosecchi on 07/01/17.
 */

public class RateData {
    private static RateData shared;
    public static RateData getShared(){
        if (shared == null){
            shared = new RateData();
        }
        return shared;
    }
    public RateData(){}

    private float hzTx, hzRx;
    private String rxValue;
    public synchronized void setTx(float tx){hzTx = tx;}
    public synchronized float getTx(){return hzTx;}
    public synchronized float getHzRx() {return hzRx;}
    public synchronized void setHzRx(float hzRx) {this.hzRx = hzRx;}
    public synchronized String getRxValue() {return rxValue;}
    public synchronized void setRxValue(String rxValue) {this.rxValue = rxValue;}
}
