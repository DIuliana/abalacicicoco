package com.example.iulia.iandi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Iulia on 03.04.2016.
 */
public class LoginActivity extends AppCompatActivity {

    final static String USERNAME_KEY="username";
    final static String CREATE_BTN_CLICKED_KEY="create";
    final static String JOIN_BTN_CLICKED_KEY="join";

    Button aboutAppBtn;
    TextView aboutAppText;
    ImageView aboutAppImage;
    EditText usernameField;
    Button play;
    Button connect;
    Button join;
    String username = null;
    int click = 0;


    Boolean createBtnClicked=false;
    Boolean joinBtnClicked=false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        aboutAppBtn = (Button) findViewById(R.id.aboutApp);
        aboutAppText = (TextView) findViewById(R.id.aboutAppText);
        aboutAppImage = (ImageView) findViewById(R.id.aboutAppImage);
        usernameField = (EditText) findViewById(R.id.username);
        play = (Button) findViewById(R.id.playBtn);
        play.setVisibility(View.INVISIBLE);
        connect=(Button)findViewById(R.id.createGame);
        join=(Button)findViewById(R.id.joinGame);
        aboutAppBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                click++;
                if (isClickedOnce()) {
                    showAboutAppInfo();
                } else {
                    hideAboutAppInfo();
                }

            }

        });
        usernameField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play.setEnabled(false);
                //usernameField.setText("");
            }
        });
        usernameField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                play.setEnabled(false);

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    username = usernameField.getText().toString();
                    if (!username.equals("")) {
                        play.setEnabled(true);
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.toast_messge, Toast.LENGTH_LONG).show();
                    }
                }

                return false;
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseGameMode();
            }
        });

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createBtnClicked=true;
                Intent connectionIntent=new Intent(LoginActivity.this, BluetoothActivity.class);
                connectionIntent.putExtra(CREATE_BTN_CLICKED_KEY, createBtnClicked);
                startActivity(connectionIntent);
                //connect.setEnabled(false);

            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinBtnClicked=true;
                Intent connectionIntent=new Intent(LoginActivity.this, BluetoothActivity.class);
                connectionIntent.putExtra(JOIN_BTN_CLICKED_KEY, joinBtnClicked);
                startActivity(connectionIntent);
              //  join.setEnabled(true);
            }
        });
    }


    public boolean isClickedOnce() {
        if (click % 2 == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void showAboutAppInfo() {
        aboutAppImage.setVisibility(View.VISIBLE);
        aboutAppText.setVisibility(View.VISIBLE);
    }

    public void hideAboutAppInfo() {
        aboutAppImage.setVisibility(View.INVISIBLE);
        aboutAppText.setVisibility(View.INVISIBLE);
    }

    public void chooseGameMode() {
        Intent modeIntent = new Intent(this, ModeActivity.class);
        modeIntent.putExtra(USERNAME_KEY, username);
        startActivity(modeIntent);
    }


    public void createConnection(View view ){
        createBtnClicked=true;
        Intent connectionIntent=new Intent(this, BluetoothActivity.class);
        connectionIntent.putExtra(CREATE_BTN_CLICKED_KEY, createBtnClicked);
        startActivity(connectionIntent);
    }
//    public void joinConnection(View view ){
//        Intent connectionIntent=new Intent(this, BluetoothActivity.class);
//        connectionIntent.putExtra(JOIN_BTN_CLICKED_KEY, joinBtnClicked);
//        startActivity(connectionIntent);
//    }
}
