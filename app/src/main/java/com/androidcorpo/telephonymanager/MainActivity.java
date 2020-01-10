package com.androidcorpo.telephonymanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView tvInfo;
    String info, phoneType;
    static final int PERMISSION_READ_STATE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getInfo(View view) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            myTelephonyManager();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE);
        }
    }

    private void myTelephonyManager() {

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        int phType = telephonyManager.getPhoneType();
        switch (phType) {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                phoneType = "CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                phoneType = "GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                phoneType = "NONE";
                break;
        }
        boolean isRoaming = telephonyManager.isNetworkRoaming();

            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            @SuppressLint("MissingPermission") String IMEINumber = telephonyManager.getDeviceId();
            @SuppressLint("MissingPermission") String subscriberID = telephonyManager.getDeviceId();
            @SuppressLint("MissingPermission") String simSerialNumber = telephonyManager.getSimSerialNumber();
            String networkCountryCode = telephonyManager.getNetworkCountryIso();
            String networkOperator = telephonyManager.getNetworkOperator();
            String networkOperatorName = telephonyManager.getNetworkOperatorName();
            String simCountryISO = telephonyManager.getSimCountryIso();
            @SuppressLint("MissingPermission") String softwareVersion = telephonyManager.getDeviceSoftwareVersion();
            @SuppressLint("MissingPermission") String voiceMailNumber = telephonyManager.getVoiceMailNumber();

            info = "Information detaillé sur le telephone : \n";
            info +="\n Network Operator : "+networkOperator;
            info +="\n Network Operator Name : "+networkOperatorName;
            info +="\n Network Country Code : "+networkCountryCode;
            info +="\n Sim Country ISO : "+simCountryISO;
            info +="\n Voice Mail Number : "+voiceMailNumber;
            info +="\n IMEI Number : "+IMEINumber;
            info +="\n Roaming Activated ? : "+isRoaming;
            info +="\n Phone Type: "+phoneType;
            info +="\n Sim Serial Number: "+simSerialNumber;
            info +="\n Subscriber ID : "+subscriberID;
            info +="\n Software Version : "+softwareVersion;

            btn = findViewById(R.id.btn_start);
            tvInfo = findViewById(R.id.text_info);
            tvInfo.setText(info);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_READ_STATE:
            {
                if(grantResults.length>=0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    myTelephonyManager();
                }else Toast.makeText(this, "YOu don't have permission !!!",Toast.LENGTH_LONG).show();
            }
        }
    }
}
