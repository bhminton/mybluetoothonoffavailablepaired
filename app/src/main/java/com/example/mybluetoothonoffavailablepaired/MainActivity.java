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
        if (mBlueAdapter.isEnabled());{
            
        }
    }
}