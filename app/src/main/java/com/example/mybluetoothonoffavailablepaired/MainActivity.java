package com.example.mybluetoothonoffavailablepaired;



import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
        mPairedTv     = findViewById(R.id.pairedTv);
        mBlueIv       = findViewById(R.id.bluetoothIv);
        mOnBtn        = findViewById(R.id.onBtn);
        mOffBtn       = findViewById(R.id.offBtn);
        mDiscoverBtn  = findViewById(R.id.discoverableBtn);
        mPairedBtn    = findViewById(R.id.pairedBtn);

        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBlueAdapter == null){
            Toast.makeText(this, "NOT ON", Toast.LENGTH_LONG).show();
            mStatusBlueTv.setText("BLUE TOOTH NOT AVAILABLE");

        }
        else {

            Toast.makeText(this, "BLUETOOTH IS ON", Toast.LENGTH_LONG).show();
            mStatusBlueTv.setText("BLUE TOOTH IS AVAILABLE");
        }
        //SET BLUE TOOTH IMAGE AS ON OR OFF
           if  (mBlueAdapter.isEnabled()){
          //  mStatusBlueTv.setText("Blue tooth is enabled");
            mBlueIv.setImageResource(R.drawable.ic_action_on);
        }
        else {
           // mStatusBlueTv.setText("Blue tooth NOT ENABLE");
               mBlueIv.setImageResource(R.drawable.ic_action_off);

        }
              //ON BUTTON CLICK
        mOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if ( !mBlueAdapter.isEnabled()){
                     toastMsg("Turning on Blue Tooth");
                     //Intent to turn on blue tooth
                     Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                      startActivityforResult (intent, REQUEST_ENABLE_BLUETOOTH);
                 }

                 else {

                     toastMsg("Bluetooth is already on");
                 }
            }
        });
             //DISCOVER BUTTON
        mDiscoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBlueAdapter.isDiscovering()) {
                    toastMsg("Making your device discoverable");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityforResult (intent, REQUEST_DISCOVER_BLUETOOTH);

                }

            }
        });

        //OFF BUTTON
        mOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBlueAdapter.isEnabled()){
                    mBlueAdapter.disable();
                    toastMsg( "Turning Blue tooth off");
                    mBlueIv.setImageResource(R.drawable.ic_action_off);
                }
                else {
                    toastMsg("Bluetooth is already off");
                }

            }
        });

        mPairedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBlueAdapter.isEnabled()) {
                    mPairedTv.setText("Paired devices ");
                    Set<BluetoothDevice> devices = mBlueAdapter.getBondedDevices();
                    for (BluetoothDevice: device: devices) {
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