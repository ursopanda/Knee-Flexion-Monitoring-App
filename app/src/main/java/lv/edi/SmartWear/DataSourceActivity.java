package lv.edi.SmartWear;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import lv.edi.Database.Databasehandler;
import lv.edi.Database.FlexionStats;

public class DataSourceActivity extends Activity implements OnGestureListener{
	// constants
	//request codes for start activity for result
    public final static String EXTRA_MESSAGE = "lv.edi.SmartWear.MESSAGE";
	private static final int REQUEST_ENABLE_BT = 1;
	private static final int REQUEST_SELECT_TARGET_DEVICE = 2;
	private static final int REQUEST_SELECT_SOURCE_FILE = 3;
	
	// message types for handlers
	public static final int UPDATE_CONNECTION_STATUS = 1; // to update statysView
	public static final int UPDATE_CONNECTED = 1; // update activity that connected
	public static final int UPDATE_DISCONNECTED = 2; //update activity that bluetooth connection has been lost
	public static final int UPDATE_TARGET_BATTERY_STATUS = 101;// update TARGET

	public static int thresholdValue = 0;  // Threshold Value for Knee Joint Extension - global variable for the whole application
	private SmartWearApplication application; // refference to application instance stores global data
	private Button connectButton; // button that starts and stops bluetooth connection
	private TextView targetDeviceView; // textview that shows bluetooth target device view
	private TextView connectionStatusView; //textview that shows connection status
	private GestureDetector gDetector; //Gesture detector that detects touch input on screan
	private ReadingFromFileService readingFromFileService = null;
	// bluetooth objects
	BluetoothDevice mDevice; // target bluetooth device
	BluetoothAdapter mBluetoothAdapter; // phones bluetooth adapter
	@SuppressLint("HandlerLeak")
	private final Handler handler = new Handler(){ // handler that handles messages from other threads
		public void handleMessage(Message message){
			switch (message.what){
			case UPDATE_CONNECTION_STATUS: // in case of message type update conection status
				if(message.arg1 == UPDATE_CONNECTED){ // if connection created
					connectionStatusView.setText("connected!"); // update status view
					connectButton.setText("Disconnect");		//update connect button with text
				} else {									// if connection lost message received
					connectButton.setText("Connect"); 	//
					connectionStatusView.setText("not connected");
					Toast.makeText(getApplicationContext(), "Not connected", Toast.LENGTH_SHORT).show();
				}
				break;
			case UPDATE_TARGET_BATTERY_STATUS:
			//	targetBatteryLevelView.setText(""+application.getBatteryLevel()+" %");
				Log.d("Battery message", "got message");
				break;
			default:
				break;
			}
		}
    };
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // call for super class method
        setContentView(R.layout.activity_select_data_source); // set content view from xml file
        //get application object
        application = (SmartWearApplication)getApplicationContext(); // instantiate application object
        //instantiate views from activity screen
        connectButton = (Button) findViewById(R.id.connect_button);
        targetDeviceView = (TextView) findViewById(R.id.target_device_view);
        connectionStatusView = (TextView) findViewById(R.id.connection_status_view);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); // getting bluetooth adapter
        // checking if adapter is created, if not, then application closes
        if(mBluetoothAdapter == null){
        	Toast.makeText(this, "Sorry, but device does not support bluetooth connection \n Application will now close", Toast.LENGTH_SHORT).show();
        	finish();
        	return;
        }
        // check if bluetooth is turned on
        if(!mBluetoothAdapter.isEnabled()){
        	// intent to open activity, to turn on bluetooth if bluetooth no turned on
        	Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        	startActivityForResult(enableIntent, REQUEST_ENABLE_BT); //start activity for result
        }
        
        gDetector = new GestureDetector(getApplicationContext(), this); // instantiate gesture detector

        // Getting a refference to a DB to get Flexion's values to the table
        final Databasehandler db = new Databasehandler(this);
        // Thread is used to update table data every second
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Getting current Knee's Flexion Value to the table
                                TextView currentFlexionValueText = (TextView) findViewById(R.id.currentFlexionValueText);
                                TextView maxFlexionValueText = (TextView) findViewById(R.id.maxFlexionValueText);
                                TextView avgFlexionValueText = (TextView) findViewById(R.id.avgFlexionValue);
                                // checking if there is running bluetooth connection
                                if(application.bluetoothService.isConnected()) {
                                    currentFlexionValueText.setText(String.valueOf(DisplayCalculations.flexionsAngleValue));
                                    maxFlexionValueText.setText(String.valueOf(db.getMaxFlexionValue()));
                                    avgFlexionValueText.setText(String.valueOf(db.getAverageFlexionValue()));
                                }
                                else
                                    currentFlexionValueText.setText("-");
                                // Getting Flexion's Maximum value to the table
 //                               TextView maxFlexionValueText = (TextView) findViewById(R.id.maxFlexionValueText);
                            //    maxFlexionValueText.setText(String.valueOf(db.getMaxFlexionValue()));
                                // Getting Flexion's Average value to the table
    //                            TextView avgFlexionValueText = (TextView) findViewById(R.id.avgFlexionValue);
                           //     // Parsing from double to String to put it into the TextView
                                //avgFlexionValueText.setText(String.valueOf(db.getAverageFlexionValue()));
                                TextView realTimeKneeFlexionValueText = (TextView) findViewById(R.id.realTimeKneeFlexionValue);
                                realTimeKneeFlexionValueText.setText(String.valueOf(DisplayCalculations.flexionsAngleValue));
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            };
        };
        t.start();
    }
    // on start callback
    public void onStart(){
    	super.onStart();
    	// if bluetooth service has target device object
    	if(application.bluetoothService.getBluetoothDevice()!=null){
    		mDevice=application.bluetoothService.getBluetoothDevice();// get reference to that device
    		targetDeviceView.setText(application.bluetoothService.getBluetoothDevice().getName()); // update target device view
    	}
    	if(application.bluetoothService.isConnected()){ // if there is running bluetooth connection
    		connectionStatusView.setText("Connected!"); //update connection status view
    		connectButton.setText("Disconnect");// update buttons text
    	}
    }
    // on activity destroy callback
    	public void onResume(){
    		super.onResume();
    		if(application.bluetoothService.isConnected()){
    			connectionStatusView.setText("connected!");
    			connectButton.setText("Disconnect");
    		} else{
    			connectionStatusView.setText("not connected");
    			connectButton.setText("Connect");
    		}
    	}
    	public void onDestroy(){
    	super.onDestroy();
    	if(application.bluetoothService.isConnected()==true){ // if there is ongoing connection close the connection
    		application.bluetoothService.disconnectDevice(); // calling function that disconnects target bluetooth device	
    	}	
    }
     
    @Override
    public boolean onTouchEvent(MotionEvent me) { // on touch event callback
        return gDetector.onTouchEvent(me); 
    }
    //callback that inflates option menu, when menu button is pressed
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
    
    // button callbacks
    //on click button Select Target Device
    public void onClickSlectTarget(View v){
    	Intent serverIntent = new Intent(this, DeviceListActivity.class); // intent to start activity that allows to select bluetooth target device
        startActivityForResult(serverIntent, REQUEST_SELECT_TARGET_DEVICE); // start activity for result
    }
    // on click connect button
    public void onClickConnect(View v){
    	if(!application.isReadingFromFile()){
	    	if(mDevice!=null){ // if bluetooth connection target device is selected
	    		if(application.bluetoothService.isConnected()==false){ //if connection is not already open
	    			Log.d("connect button","connecting to device");
	    			connectionStatusView.setText("connecting..."); // update connection status view
	    			application.bluetoothService.connectDevice(mDevice, handler); // connect target device in bluetooth service class
	    		} else{
	    			application.bluetoothService.disconnectDevice(); // if bluetooth connection on going and connect button pressed stop bluetooth connection and deisconect device
	    			connectionStatusView.setText("not connected"); //update status view
	    			connectButton.setText("Connect"); // update connect button label
	    		}
	    		
	    	} else{
	    		Toast.makeText(getApplicationContext(),"Select Target Device First",Toast.LENGTH_SHORT).show();// warn user, that target is not selected
	    	}
    	} else{
    		Toast.makeText(this, "Stop reading from file first", Toast.LENGTH_SHORT).show();
    	}
    }

    // onClick function, that will transfer us to length calculation's view
    public void onClickCalculations(View v) {
        if(application.bluetoothService.isConnected() == true) {    // We will proceed to the next view, only if Bluetooth target device is connected
            Intent intent = new Intent(this, DisplayCalculations.class);
            TextView deviceName = (TextView) findViewById(R.id.target_device_view);
            String message = deviceName.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Nothing to show there!", Toast.LENGTH_SHORT).show();
        }
    }

    // onClick functions, that will transfer us to statistics view
    public void onClickStatistics(View v) {
//        Intent intent = new Intent(this, DisplayStatistics.class);
//        startActivity(intent);
        Intent intent = new Intent(this, DynamicGraphActivity.class);
        startActivity(intent);
    }

    //onClick function, that will set the Threshold Value for Knee Joint extension's angle
    public void setThresholdValue(View v) {
        EditText thresholdValueTextView = (EditText) findViewById(R.id.thresholdValue);
        String thresholdValueText = thresholdValueTextView.getText().toString();
        if (isInteger(thresholdValueText))
        thresholdValue = Integer.parseInt(thresholdValueText);
        else {
            thresholdValue = 90;
            thresholdValueTextView.setText("90");
        }
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	switch(requestCode){
    	case REQUEST_ENABLE_BT: // result from bluetooth enable activity
    		if(resultCode==RESULT_CANCELED){ // if user did not turn on bluetooth adapter
    			Toast.makeText(this, "In order to use this application you must turn on bluetooth",Toast.LENGTH_SHORT).show();// warn user
    			finish(); //finish activity
    		}
    		break;
    	case REQUEST_SELECT_TARGET_DEVICE: // if
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                // Get the device MAC address
                String address = data.getExtras()
                                     .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                // Get the BLuetoothDevice object
                 mDevice = mBluetoothAdapter.getRemoteDevice(address); // instantate bluetooth target device
                 targetDeviceView.setText(mDevice.getName()); //update target device view
                 Log.d("REQUEST_SELECT_TARGET_DEVICE", "RESULT_OK");
            }
            break;
    	case REQUEST_SELECT_SOURCE_FILE:
    		if(resultCode == Activity.RESULT_OK){
    		Log.d("ACTIVITYRESULT"," RESULT OK");
			Log.d("ACTIVITYRESULT","FILE "+application.getDataSourceFile().getName());
			//sourceFileView.setText(application.getDataSourceFile().getName()); // show selected file
			
    		}
    		if (resultCode == Activity.RESULT_CANCELED){
    			Log.d("ACTIVITYRESULT","SELECT SOURCE FILE  ACTIVITY CANCELED");
    		}
    		break;
    	default:
    		break;
    	}
    }
    // callback for gesture detector events
    // in case of fling gesture
    public boolean onFling(MotionEvent start, MotionEvent finish, float xVelocity, float yVelocity) {
		if (start.getRawX() > finish.getRawX()) { // if finger slided from right to left
			
			Intent intent = new Intent(getApplicationContext(), DisplayCalculations.class);// intent to start processing activity
            if (application.bluetoothService.isConnected() == true) {
                startActivity(intent); // start angle calculation activity
            } else {
                Toast.makeText(getApplicationContext(), "Nothing to show there!", Toast.LENGTH_SHORT).show();
            }
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);// override animation for activity transition
		} 
		return true;
	}
    // firther there are gesture callbacks that are not implemented in currents case
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
}
