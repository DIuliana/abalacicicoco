package com.example.iulia.iandi;

import android.app.Activity;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Iulia on 07.05.2016.
 */
public class GalaxyActivity extends Activity {
    private TextView messageTxt;
    private Button butonel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.galaxy_mode);

        messageTxt = (TextView) findViewById(R.id.messagefrombluethoot);
        butonel = (Button) findViewById(R.id.butonel);

        Intent intent = getIntent();
        String msg = intent.getStringExtra("msgProba");
        messageTxt.setText(msg);

        butonel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSendToBluetooth = new Intent("INTENT_NAME").putExtra("KEY", generateRandom());
                LocalBroadcastManager.getInstance(GalaxyActivity.this).sendBroadcast(intentSendToBluetooth);
            }
        });
    }
    public String generateRandom() {
        double number = Math.random();
        number *= 100;
        return Integer.toString((int) number);
    }
}

