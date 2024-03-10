package com.example.mybluetoothonoffavailablepaired;


import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BLUETOOTH = 0;
    private static final int REQUEST_DISCOVER_BLUETOOTH = 1;


    TextView mStatusBlueTv, mPairedTv;
    ImageView mBlueIv;
    Button mOnBtn, mOffBtn, mDiscoverBtn, mPairedBtn;

    BluetoothAdapter mBlueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mStatusBlueTv = findViewById(R.id.statusBluetoothTv);
        mPairedTv = findViewById(R.id.pairedTv);
        mBlueIv = findViewById(R.id.bluetoothIv);
        mOnBtn = findViewById(R.id.onBtn);
        mOffBtn = findViewById(R.id.offBtn);
        mDiscoverBtn = findViewById(R.id.discoverableBtn);
        mPairedBtn = findViewById(R.id.pairedBtn);

        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBlueAdapter == null) {
            Toast.makeText(this, "NOT ON", Toast.LENGTH_LONG).show();
            mStatusBlueTv.setText("BLUE TOOTH NOT AVAILABLE");

        } else {

            Toast.makeText(this, "BLUETOOTH IS ON", Toast.LENGTH_LONG).show();
            mStatusBlueTv.setText("BLUE TOOTH IS AVAILABLE");
        }
        //SET BLUE TOOTH IMAGE AS ON OR OFF
        if (mBlueAdapter.isEnabled()) {
            //  mStatusBlueTv.setText("Blue tooth is enabled");
            mBlueIv.setImageResource(R.drawable.ic_action_on);
        } else {
            // mStatusBlueTv.setText("Blue tooth NOT ENABLE");
            mBlueIv.setImageResource(R.drawable.ic_action_off);

        }
        //ON BUTTON CLICK
        mOnBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                if (!mBlueAdapter.isEnabled()) {
                    toastMsg("Turning on Blue Tooth");
                    //Intent to turn on blue tooth
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent,
                            REQUEST_ENABLE_BLUETOOTH);
                } else {

                    toastMsg("Bluetooth is already on");
                }
            }
        });
        //DISCOVER BUTTON
        mDiscoverBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                if (!mBlueAdapter.isDiscovering()) {
                    toastMsg("Making your device discoverable");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, REQUEST_DISCOVER_BLUETOOTH);

                }

            }
        });

        //OFF BUTTON
        mOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context applicationContext = getApplicationContext();
                if (mBlueAdapter.isEnabled()) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mBlueAdapter.disable();
                    toastMsg("Turning Blue tooth off");
                    mBlueIv.setImageResource(R.drawable.ic_action_off);
                } else {
                    toastMsg("Bluetooth is already off");
                }

                /// trying to figure out permissions
                /// ContextCompat.checkSelfPermissions()  method
                // returns PERMISSION_GRANTED or PERMISSION_DENIED
                /// depending on wether or not permissions has been granted
                /// See Below


            }
        });

        mPairedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBlueAdapter.isEnabled()) {
                    mPairedTv.setText("Paired devices ");
                     Context applicationContext = getApplicationContext();
                    if (ActivityCompat.checkSelfPermission(applicationContext, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    Set<BluetoothDevice> devices = mBlueAdapter.getBondedDevices();
                    for (BluetoothDevice device: devices) {
                        mPairedTv.append("\nDevice" + device.getName() + ","  + device);

                    }

                }
                else {

                    //bluetooth is not connected and cant get info
                    toastMsg("Turn on bletooth if you want shared devices ");
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode) {
            case REQUEST_ENABLE_BLUETOOTH:
                if (resultCode == RESULT_OK){
                  //Blue tooth is on
                    mBlueIv.setImageResource(R.drawable.ic_action_on);
                }
                else {
                  //user denied to turn on blue tooth
                  toastMsg("User declined to turn on blue tooth ");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // Set up toast
    private void  toastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }




}

//full question on chat gpt
//Certainly! If you want to check and request Bluetooth permissions at runtime, you can follow a similar pattern. Let's consider the Bluetooth permissions `BLUETOOTH` and `BLUETOOTH_ADMIN` as an example. These permissions are required for basic Bluetooth access and administrative operations, respectively.
//
//        1. **Declare Permissions in the Manifest:**
//        Add the following lines to your AndroidManifest.xml:
//
//        ```xml
//<uses-permission android:name="android.permission.BLUETOOTH" />
//<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
//        ```
//
//        2. **Check and Request Permissions at Runtime:**
//        In your code, check if the required permissions are granted before using Bluetooth features. If not, request them at runtime:
//
//        ```java
//private static final int BLUETOOTH_PERMISSION_REQUEST_CODE = 1;
//
//        // ...
//
//        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//
//        if (bluetoothAdapter == null) {
//        // Device does not support Bluetooth
//        } else {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||
//        ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
//
//        // Request Bluetooth permissions
//        ActivityCompat.requestPermissions(
//        this,
//        new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN},
//        BLUETOOTH_PERMISSION_REQUEST_CODE
//        );
//        } else {
//        // Bluetooth permissions are already granted, proceed with Bluetooth operations
//        }
//        }
//        ```
//
//        3. **Handle Permission Results:**
//        Override the `onRequestPermissionsResult` method to handle the result of the permission request:
//
//        ```java
//@Override
//public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == BLUETOOTH_PERMISSION_REQUEST_CODE) {
//        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
//        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//        // Bluetooth permissions granted, proceed with Bluetooth operations
//        } else {
//        // Permission denied, handle accordingly (e.g., show a message or disable Bluetooth functionality)
//        }
//        }
//        }
//        ```
//
//        Make sure to replace `this` with the actual context of your application where you are performing these checks.
//        This example assumes you're doing this in an `Activity`.
//          Adjust the code accordingly if you are using it in a different context.