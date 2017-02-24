package msec.itu.lifiapp;

import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.util.Log;

import static msec.itu.lifiapp.TransmissionUtil.getBinary;

/**
 * Created by martinosecchi on 31/12/16.
 */

public class LiFiRunnable implements Runnable {

    private CameraManager mCameraManager;
    private String mCameraId;
    private String input;
    private Boolean isTurnOn;
    private static RateData rateData;

    public LiFiRunnable(CameraManager cmanag, String cid, String _input){
        mCameraId = cid;
        mCameraManager = cmanag;
        input = _input;
        isTurnOn = false;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();

        String binary = getBinary(input);
        int length = binary.length();
        for (int i=0; i<binary.length(); i++){
            if(binary.charAt(i) == '0'){
                if(isTurnOn) {
                    turnOffFlashLight();
                    isTurnOn = false;
                }
            } else if (binary.charAt(i) == '1' ) {
                if(!isTurnOn) {
                    turnOnFlashLight();
                    isTurnOn = true;
                }
            } else { //else it's a space in the binary, ignore
                length += -1;
            }
        }
        turnOffFlashLight();

        float estimatedTime = System.currentTimeMillis() - startTime;
        estimatedTime = estimatedTime / 1000f;
        Log.i("LIFI Thread","It took " + estimatedTime + "s to flash " + length + " times. ["+length/estimatedTime+" Hz]");
        rateData = RateData.getShared();
        rateData.setTx(length/estimatedTime);
        /*
        TODO
        it's all good and fun for transmission, but in reality how to distinguish 0011100001 from 0101 in
        reception?
        I need a fixed frame rate otherwise it's impossible to read
        (or: decode differently, like pairs
        e.g.  0011100001 becomes 2-0  3-1  4-0  1-1, and have a syntax that can be easily read)
        */

    }

    public void turnOnFlashLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void turnOffFlashLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
