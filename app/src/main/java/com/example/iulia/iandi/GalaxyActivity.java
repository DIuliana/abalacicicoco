package com.example.iulia.iandi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Iulia on 07.05.2016.
 */
public class GalaxyActivity extends Activity {


    private Button turnBtn;
    private RelativeLayout background_support;
    private ImageView background;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.galaxy_mode);

        turnBtn=(Button)findViewById(R.id.go);
        Intent creatorClickedIntent=getIntent();
        if(creatorClickedIntent.getBooleanExtra(BluetoothActivity.CREATE_BTN_CLICK_KEY,true)){
            turnBtn.setVisibility(View.VISIBLE);
        }

        int buttonSpace=(int)Math.round(LoginActivity.getHeight()*0.15);
        turnBtn.getLayoutParams().height=buttonSpace;

        background_support = (RelativeLayout) findViewById(R.id.backg_support);
        // background = (ImageView) findViewById(R.id.background);

        Intent intent = getIntent();
        String msg = intent.getStringExtra("msgProba");


        turnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSendToBluetooth = new Intent("INTENT_NAME").putExtra("KEY", generateRandom());
                LocalBroadcastManager.getInstance(GalaxyActivity.this).sendBroadcast(intentSendToBluetooth);
            }
        });
        Draw draw = new Draw(this);
        background_support.addView(draw);
    }

    public String generateRandom() {
        double number = Math.random();
        number *= 100;
        return Integer.toString((int) number);
    }



}
