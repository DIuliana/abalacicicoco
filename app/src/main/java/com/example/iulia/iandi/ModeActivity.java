package com.example.iulia.iandi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Iulia on 07.05.2016.
 */
public class ModeActivity extends Activity {

    TextView usernameField;
    Button galaxyModeBtn;
    Button wipModeBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        usernameField = (TextView) findViewById(R.id.username);
        galaxyModeBtn=(Button)findViewById(R.id.galaxyMode);
        wipModeBtn=(Button)findViewById(R.id.wip);

        //show username from Login page
        usernameField.setText(getResources().getString(R.string.welcome)+" "+getUsernameFromLogin()+"!");

        galaxyModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galaxyIntent=new Intent(ModeActivity.this, GalaxyActivity.class);
                startActivity(galaxyIntent);
            }
        });
    }


    public String getUsernameFromLogin() {
        String username = null;
        Intent loginIntent = getIntent();
        username = loginIntent.getStringExtra(LoginActivity.USERNAME_KEY);
        return username;

    }

}
