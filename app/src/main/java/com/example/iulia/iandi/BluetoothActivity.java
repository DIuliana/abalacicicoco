
package com.example.iulia.iandi;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class BluetoothActivity extends AppCompatActivity {

    //Tipurile de mesaje trimise de BluetoothChatService Handler
    public static final int MESSAGE_STAGE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    //Key names from BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    //codurile pentru inent-uri
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    //legattura cu interfata
    private Toolbar toolbar;
    //Name of the connected device
    private String mConnectedDeviceName = null;
    //Array adapter for the conversation thread
    private ArrayAdapter<String> mConversationArrayAdapter;
    //String buffer for outgoing messages
    private StringBuffer mOutStringBuffer;
    //Local BluetoothAdapter
    private BluetoothAdapter mBluetoothAdapter = null;
    //Member object for the chat services
    private BluetoothService mChatService = null;

    boolean doubleBackToExitPressedOnce = false;

    public  final static String CREATE_BTN_CLICK_KEY="create";
    public  final static String JOIN_BTN_CLICK_KEY="join";

    private Button makeVisible;
    private Button createConnection;
    private Button galaxyModeBtn;
    Button wipModeBtn;

    String galaxyBtnText = "";
    String wipBtnText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        makeVisible = (Button) findViewById(R.id.visible);
        createConnection = (Button) findViewById(R.id.connection);
        galaxyModeBtn = (Button) findViewById(R.id.galaxyBtn);
        wipModeBtn = (Button) findViewById(R.id.wipBtn);
        Intent loginIntent = getIntent();
        Boolean isCreateIntent = loginIntent.getBooleanExtra(LoginActivity.CREATE_BTN_CLICKED_KEY, false);
        Boolean isJoinIntent = loginIntent.getBooleanExtra(LoginActivity.JOIN_BTN_CLICKED_KEY, false);

        if (isCreateIntent) {
            createConnection.setVisibility(View.VISIBLE);
        }
        if (isJoinIntent) {
            makeVisible.setVisibility(View.VISIBLE);
        }

        createConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent creatorClickedIntent=new Intent(BluetoothActivity.this, GalaxyActivity.class);
                creatorClickedIntent.putExtra(BluetoothActivity.CREATE_BTN_CLICK_KEY,true);
                Intent serverIntent = new Intent(BluetoothActivity.this, BluetoothDeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);


            }
        });
        makeVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ensureDiscoverable();
                galaxyBtnText = "JOIN GALAXY!";
                wipBtnText = "JOIN WIP";
                galaxyModeBtn.setText(galaxyBtnText);
                wipModeBtn.setText(wipBtnText);
            }
        });
        galaxyModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent probaIntent = new Intent(BluetoothActivity.this, GalaxyActivity.class);
                String message = "abalaciccicocoo";
                probaIntent.putExtra("msgProba", message);
                startActivity(probaIntent);
            }
        });
        wipModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void onStart() {
        super.onStart();

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, REQUEST_ENABLE_BT);
        } else {
            if (mChatService == null) {
                setupChat();
            }
        }
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
    @Override
    public synchronized void onResume() {
        super.onResume();
        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothService.STATE_NONE) {
                mChatService.start();
            }
        }
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
        //get random numbers from game activity

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Stop the Bluetooth chat service
        if (mChatService != null) {
            mChatService.stop();
        }
    }
    private void setupChat() {
        //Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothService(this, mHandler);
        //Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    /**
     * Sends a message.
     *
     * @param message A string of text to send.
     */

    private void sendMessage(String message) {
        //Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }
        //Check that there's actually something sent
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] sent = message.getBytes();
            mChatService.write(sent);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
            // mOutEditText.setText(mOutStringBuffer);
        }
    }

    //The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STAGE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            toolbar.setTitle(mConnectedDeviceName);
                            //make buttons visible after devices are connected
                            createConnection.setVisibility(View.INVISIBLE);
                            makeVisible.setVisibility(View.INVISIBLE);
                            galaxyModeBtn.setVisibility(View.VISIBLE);
                            wipModeBtn.setVisibility(View.VISIBLE);
                            //get the values from game activity when the deices are connected
                            LocalBroadcastManager.getInstance(BluetoothActivity.this).registerReceiver(mReceiver, new IntentFilter("INTENT_NAME"));
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            toolbar.setTitle(R.string.title_connecting);
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                            toolbar.setTitle(R.string.title_not_connected);
                            break;
                    }
                    break;

                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;

                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    //mConversationArrayAdapter.add( "Me : " + writeMessage);
                    Toast.makeText(BluetoothActivity.this, "Me:  " + writeMessage, Toast.LENGTH_LONG).show();
                    break;

                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;

                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    // mConversationArrayAdapter.add(mConnectedDeviceName + ": " + readMessage);
                    Toast.makeText(BluetoothActivity.this, mConnectedDeviceName + ": " + readMessage, Toast.LENGTH_SHORT).show();
                    break;

                case MESSAGE_DEVICE_NAME:
                    //save the connected devce's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;

                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    //brodcast receiver from Game
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String receivedGalaxyNumber = intent.getStringExtra("KEY");
            sendMessage(receivedGalaxyNumber);
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                //When DeviceListActivity returns a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    //Get the device MAC adress
                    String address = data.getExtras().getString(BluetoothDeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    //Get the BluetoothDevice object
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);

                    //Attempt to connect to the device
                    mChatService.connect(device);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupChat();
                } else {
                    // User did not enable Bluetooth or an error occured
                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }
}