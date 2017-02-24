package msec.itu.lifiapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static msec.itu.lifiapp.TransmissionUtil.getBinary;

public class TransmitterActivity extends AppCompatActivity {

    private CameraManager mCameraManager;
    private String mCameraId;
    private Button mTorchOnOffButton;
    private Button goToRecvActivityBtn;
    private EditText mEditText;
    private TextView mBinaryText, mRateText;
    private static RateData rateData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rateData = RateData.getShared();

        mTorchOnOffButton = (Button) findViewById(R.id.button_on_off);
        mEditText = (EditText) findViewById(R.id.inputText);
        mBinaryText = (TextView) findViewById(R.id.binaryText);
        mRateText = (TextView) findViewById(R.id.rateTx);
        goToRecvActivityBtn = (Button) findViewById(R.id.goToRecBtn);


        Boolean isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!isFlashAvailable) {
            AlertDialog alert = new AlertDialog.Builder(TransmitterActivity.this)
                    .create();
            alert.setTitle("Error !!");
            alert.setMessage("Your device doesn't support flash light!");
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // closing the application
                    finish();
                    System.exit(0);
                }
            });
            alert.show();
            return;
        }
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
//        try {
//            mCameraId = mCameraManager.getCameraIdList()[0]; //0 for main camera
//            Log.i("Camera ID", mCameraId);
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
        mCameraId = "0"; //main camera
        mTorchOnOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread lifiThread = new Thread(new LiFiRunnable(mCameraManager, mCameraId, mEditText.getText().toString()));
                lifiThread.start();
                mBinaryText.setText(getBinary(mEditText.getText().toString()));
                while (lifiThread.isAlive()){}
                mRateText.setText("" + rateData.getTx() + " Hz");
            }
        });

        final Intent recvIntent = new Intent(this, ReceiverActivity.class);
        goToRecvActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(recvIntent);
            }
        });
    }

}
