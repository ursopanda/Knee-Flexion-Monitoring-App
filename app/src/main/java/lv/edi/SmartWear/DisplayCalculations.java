package lv.edi.SmartWear;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import lv.edi.Database.Databasehandler;
import lv.edi.Database.FlexionStats;


public class DisplayCalculations extends Activity {

    private SmartWearApplication application;
    private Vibrator myVib;
    private int thresholdCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_calculations);

        // Object to have a reference to Vibration functionality
        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        // Object to use for Database functionality
        final Databasehandler db = new Databasehandler(this);
        // Reference to SmartWearApplication class, where we can get Accelerometer's Data
        application = (SmartWearApplication)getApplication();

        //--------------------------------------------------------------------------------------------------------------------------------
        // Creating Alert Dialogs for Notifications, when certain amount of limits was reached
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Title");
        alertDialogBuilder.setMessage("Message")
                .setCancelable(false)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        // Initializing Alert Notifications for User
        // First Notification
        alertDialogBuilder.setTitle("Notification 1");
        alertDialogBuilder.setMessage("Relax! Do not hustle! Let's start once again :)");
        final AlertDialog alertDialog = alertDialogBuilder.create();
        // Second Notification
        alertDialogBuilder.setTitle("Notification 2");
        alertDialogBuilder.setMessage("Do not overload yourself. It is better to train slowly, then to overcome you limit! :)");
        final AlertDialog alertDialog2 = alertDialogBuilder.create();
        // Third Notification
        alertDialogBuilder.setTitle("Notification 3");
        alertDialogBuilder.setMessage("Let's have a rest and start again :)");
        final AlertDialog alertDialog3 = alertDialogBuilder.create();
        //--------------------------------------------------------------------------------------------------------------------------------
        // Get Target Device name from Intent
        Intent intent = getIntent();
        String message = intent.getStringExtra(DataSourceActivity.EXTRA_MESSAGE);
        // Show Target Device's name
        TextView textView = (TextView) findViewById(R.id.deviceNameTextView);
        textView.setText(message);

        // Thread is used to update TextView objects every second
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // -------------------------------------------------------------------------
                                // FIRST ACCELEROMETER RAW DATA
                                // Adding X Axis raw value
//                                TextView rawFirstAccXData = (TextView) findViewById(R.id.firstAccRawXValue);
//                                float rawFirstAccXValue = application.sensorArray[0].getAccRawX();
//                                String rawFirstAccXText = Float.toString(rawFirstAccXValue);
//                                rawFirstAccXData.setText(rawFirstAccXText);
                                // Adding X Axis norm value
                                TextView normFirstAccXData = (TextView) findViewById(R.id.firstAccNormXValue);
                                float normFirstAccXValue = application.sensorArray[0].getAccNormX();
                                String normFirstAccXText = Float.toString(normFirstAccXValue);
                                normFirstAccXData.setText(normFirstAccXText);
//                                // Adding Y axis raw value
//                                TextView rawFirstAccYData = (TextView) findViewById(R.id.firstAccRawYValue);
//                                float rawFirstAccYValue = application.sensorArray[0].getAccRawY();
//                                String rawFirstAccYText = Float.toString(rawFirstAccYValue);
//                                rawFirstAccYData.setText(rawFirstAccYText);
                                // Adding Y Axis norm value
                                TextView normFirstAccYData = (TextView) findViewById(R.id.firstAccNormYValue);
                                float normFirstAccYValue = application.sensorArray[0].getAccNormY();
                                String normFirstAccYText = Float.toString(normFirstAccYValue);
                                normFirstAccYData.setText(normFirstAccYText);
                                // Adding Z axis raw value
//                                TextView rawFirstAccZData = (TextView) findViewById(R.id.firstAccRawZValue);
//                                float rawFirstAccZValue = application.sensorArray[0].getAccRawZ();
//                                String rawFirstAccZText = Float.toString(rawFirstAccZValue);
//                                rawFirstAccZData.setText(rawFirstAccZText);
                                // Adding Z Axis norm value
                                TextView normFirstAccZData = (TextView) findViewById(R.id.firstAccNormZValue);
                                float normFirstAccZValue = application.sensorArray[0].getAccNormZ();
                                String normFirstAccZText = Float.toString(normFirstAccZValue);
                                normFirstAccZData.setText(normFirstAccZText);
                                // -------------------------------------------------------------------------
                                // SECOND ACCELEROMETER RAW DATA
                                // Adding X Axis raw value
//                                TextView rawSecondAccXData = (TextView) findViewById(R.id.secondAccRawXValue);
//                                float rawSecondAccXValue = application.sensorArray[1].getAccRawX();
//                                String rawSecondAccXText = Float.toString(rawSecondAccXValue);
//                                rawSecondAccXData.setText(rawSecondAccXText);
                                // Adding X Axis norm value
                                TextView normSecondAccXData = (TextView) findViewById(R.id.secondAccNormXValue);
                                float normSecondAccXValue = application.sensorArray[1].getAccNormX();
                                String normSecondAccXText = Float.toString(normSecondAccXValue);
                                normSecondAccXData.setText(normSecondAccXText);
                                // Adding Y axis raw value
//                                TextView rawSecondAccYData = (TextView) findViewById(R.id.secondAccRawYValue);
//                                float rawSecondAccYValue = application.sensorArray[1].getAccRawY();
//                                String rawSecondAccYText = Float.toString(rawSecondAccYValue);
//                                rawSecondAccYData.setText(rawSecondAccYText);
                                // Adding Y Axis norm value
                                TextView normSecondAccYData = (TextView) findViewById(R.id.secondAccNormYValue);
                                float normSecondAccYValue = application.sensorArray[1].getAccNormY();
                                String normSecondAccYText = Float.toString(normSecondAccYValue);
                                normSecondAccYData.setText(normSecondAccYText);
                                // Adding Z axis raw value
//                                TextView rawSecondAccZData = (TextView) findViewById(R.id.secondAccRawZValue);
//                                float rawSecondAccZValue = application.sensorArray[1].getAccRawZ();
//                                String rawSecondAccZText = Float.toString(rawSecondAccZValue);
//                                rawSecondAccZData.setText(rawSecondAccZText);
                                // Adding Z Axis norm value
                                TextView normSecondAccZData = (TextView) findViewById(R.id.secondAccNormZValue);
                                float normSecondAccZValue = application.sensorArray[1].getAccNormZ();
                                String normSecondAccZText = Float.toString(normSecondAccZValue);
                                normSecondAccZData.setText(normSecondAccZText);
                                // -----------------------------------------------------------------------------
                                // THIRD ACCELEROMETER NORM DATA
                                // Adding X Axis norm data
                                TextView normThirdAccXData = (TextView) findViewById(R.id.thirdAccNormXValue);
                                float normThirdAccXValue = application.sensorArray[2].getAccNormX();
                                String normThirdAccXText = Float.toString(normThirdAccXValue);
                                normThirdAccXData.setText(normThirdAccXText);
                                // Adding Y Axis norm data
                                TextView normThirdAccYData = (TextView) findViewById(R.id.thirdAccNormYValue);
                                float normThirdAccYValue = application.sensorArray[2].getAccNormY();
                                String normThirdAccYText = Float.toString(normThirdAccYValue);
                                normThirdAccYData.setText(normThirdAccYText);
                                // Adding Z Axis norm data
                                TextView normThirdAccZData = (TextView) findViewById(R.id.thirdAccNormZValue);
                                float normThirdAccZValue = application.sensorArray[2].getAccNormZ();
                                String normThirdAccZText = Float.toString(normThirdAccZValue);
                                normThirdAccZData.setText(normThirdAccZText);
                                // -----------------------------------------------------------------------------
                                // FOURTH ACCELEROMETER NORM DATA
                                // Adding X Axis norm data
                                TextView normFourthAccXData = (TextView) findViewById(R.id.fourthAccNormXValue);
                                float normFourthAccXValue = application.sensorArray[3].getAccNormX();
                                String normFourthAccXText = Float.toString(normFourthAccXValue);
                                normFourthAccXData.setText(normFourthAccXText);
                                // Adding Y Axis norm data
                                TextView normFourthAccYData = (TextView) findViewById(R.id.fourthAccNormYValue);
                                float normFourthAccYValue = application.sensorArray[3].getAccNormY();
                                String normFourthAccYText = Float.toString(normFourthAccYValue);
                                normFourthAccYData.setText(normFourthAccYText);
                                // Adding Z Axis norm data
                                TextView normFourthAccZData = (TextView) findViewById(R.id.fourthAccNormZValue);
                                float normFourthAccZValue = application.sensorArray[3].getAccNormZ();
                                String normFourthAccZText = Float.toString(normFourthAccZValue);
                                normFourthAccZData.setText(normFourthAccZText);
                                // ------------------------------------------------------------------------------------------------------------
                                // Calculating and adding tilt angle between accelerometers
                                // ALGORITHM NUMBER 1: ANGLE BETWEEN First and Third Accelerometer
                                float x = (normFirstAccXValue * normThirdAccXValue +
                                           normFirstAccYValue * normThirdAccYValue +
                                           normFirstAccZValue * normThirdAccZValue);
                                double gA = Math.sqrt(Math.pow(normFirstAccXValue,2) +
                                            Math.pow(normFirstAccYValue,2) +
                                            Math.pow(normFirstAccZValue,2));
                                double gB = Math.sqrt(Math.pow(normThirdAccXValue,2) +
                                            Math.pow(normThirdAccYValue,2) +
                                            Math.pow(normThirdAccZValue,2));
                                //double cos = (x/(gA * gB));
                                double cos = x;
                                double degrees = Math.toDegrees(Math.acos(cos));
                                degrees = Math.round(degrees);
                                // Showing flexion angle's value to user
                                String flexionAngleText = Double.toString(degrees);
                                TextView flexionAngle = (TextView) findViewById(R.id.flexionAngle);
                                flexionAngle.setText(flexionAngleText + "°");

                                // ALGORITHM NUMBER 2: USING FIRST AND FOURTH ACCELEROMETER TO DETERMINE KNEE JOINT FLEXION ANGLE
                                float x2 = (normFirstAccXValue * normFourthAccXValue +
                                        normFirstAccYValue * normFourthAccYValue +
                                        normFirstAccZValue * normFourthAccZValue);
                                double gA2 = Math.sqrt(Math.pow(normFirstAccXValue,2) +
                                        Math.pow(normFirstAccYValue,2) +
                                        Math.pow(normFirstAccZValue,2));
                                double gB2 = Math.sqrt(Math.pow(normFourthAccXValue,2) +
                                        Math.pow(normFourthAccYValue,2) +
                                        Math.pow(normFourthAccZValue,2));
                                //double cos2 = (x2/(gA2 * gB2));
                                double cos2 = x2;
                                double degrees2 = Math.toDegrees(Math.acos(cos2));
                                degrees2 = Math.round(degrees2);
                                // Showing flexion angle's value to user
                                String flexionAngleText2 = Double.toString(degrees2);
                                TextView flexionAngle2 = (TextView) findViewById(R.id.flexionAngle2);
                                flexionAngle2.setText(flexionAngleText2 + "°");

                                // ALGORITHM NUMBER 3: USING SECOND AND THIRD ACCELEROMETER TO DETERMINE KNEE JOINT FLEXION ANGLE
                                float x3 = (normSecondAccXValue * normThirdAccXValue +
                                        normSecondAccYValue * normThirdAccYValue +
                                        normSecondAccZValue * normThirdAccZValue);
                                double gA3 = Math.sqrt(Math.pow(normSecondAccXValue,2) +
                                        Math.pow(normSecondAccYValue,2) +
                                        Math.pow(normSecondAccZValue,2));
                                double gB3 = Math.sqrt(Math.pow(normThirdAccXValue,2) +
                                        Math.pow(normThirdAccYValue,2) +
                                        Math.pow(normThirdAccZValue,2));
                                //double cos3 = (x3/(gA3 * gB3);
                                double cos3 = x3;
                                double degrees3 = Math.toDegrees(Math.acos(cos3));
                                degrees3 = Math.round(degrees3);
                                // Showing flexion angle's value to user
                                String flexionAngleText3 = Double.toString(degrees3);
                                TextView flexionAngle3 = (TextView) findViewById(R.id.flexionAngle3);
                                flexionAngle3.setText(flexionAngleText3 + "°");

                                // ALGORITHM NUMBER 4: USING SECOND AND FOURTH ACCELEROMETER TO DETERMINE KNEE JOINT FLEXION ANGLE
                                float x4 = (normSecondAccXValue * normFourthAccXValue +
                                        normSecondAccYValue * normFourthAccYValue +
                                        normSecondAccZValue * normFourthAccZValue);
                                double gA4 = Math.sqrt(Math.sqrt(Math.pow(normSecondAccXValue,2) +
                                        Math.pow(normSecondAccYValue,2) +
                                        Math.pow(normSecondAccZValue,2)));
                                double gB4 = Math.sqrt(Math.pow(normFourthAccXValue,2) +
                                        Math.pow(normFourthAccYValue,2) +
                                        Math.pow(normFourthAccZValue,2));
                                //double cos4 = (x4/(gA4 * gB4));
                                double cos4 = x4;
                                double degrees4 = Math.toDegrees(Math.acos(cos4));
                                degrees4 = Math.round(degrees4);
                                // Showing flexion angle's value to user
                                String flexionAngleText4 = Double.toString(degrees4);
                                TextView flexionAngle4 = (TextView) findViewById(R.id.flexionAngle4);
                                flexionAngle4.setText(flexionAngleText4 +  "°");
                                // ALGORITHM NUMBER 5: USING 4 ACCELEROMETERS FOR ANGLE CALCULATION
                                // Coordinates of the first BIG vector
                                float firstVectorX = (normSecondAccXValue+normFirstAccXValue)/2;
                                float firstVectorY = (normSecondAccYValue+normFirstAccYValue)/2;
                                float firstVectorZ = (normFirstAccZValue+normSecondAccZValue)/2;
                                // Coordinates of the second BIG vector
                                float secondVectorX = (application.sensorArray[2].getAccNormX() + application.sensorArray[3].getAccNormX())/2;
                                float secondVectorY = (application.sensorArray[2].getAccNormY() + application.sensorArray[3].getAccNormY())/2;
                                float secondVectorZ = (application.sensorArray[2].getAccNormZ() + application.sensorArray[3].getAccNormZ())/2;
                                // Calculating flexion's angle
                                float x5 = (firstVectorX * secondVectorX +
                                           firstVectorY * secondVectorY +
                                           firstVectorZ * secondVectorZ);
                                double gA5 = Math.sqrt(Math.pow(firstVectorX,2) +
                                                       Math.pow(firstVectorY,2) +
                                                       Math.pow(firstVectorZ,2));
                                double gB5 = Math.sqrt(Math.pow(secondVectorX,2) +
                                                       Math.pow(secondVectorY,2) +
                                                       Math.pow(secondVectorZ,2));
                                //double cos2 = (x2/(gA2 * gB2));
                                double cos5 = x5;
                                double degrees5 = Math.toDegrees(Math.acos(cos5));
                                degrees5 = Math.round(degrees5);
                                // Showing flexion angle's value to user
                                String flexionAngleText5 = Double.toString(degrees5);
                                TextView flexionAngle5 = (TextView) findViewById(R.id.flexionAngle5);
                                flexionAngle5.setText(flexionAngleText5 + "°");
                                // ------------------------------------------------------------------------------------------------------------
                                // Database stuff: Putting calculated angle's value to DB FlexionAngles Table
                                db.addFlexionStats(new FlexionStats((int) degrees5));
                                // Comparing calculated flexion's angle to threshold value
                                if (degrees5 > DataSourceActivity.thresholdValue) {
                                    // TO-DO: Beep notification if threshold value has been reached
                                    // Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                    // Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                                    // r.play();
                                    flexionAngle5.setTextColor(Color.RED);
                                    thresholdCounter++;
                                } else {
                                    flexionAngle5.setTextColor(Color.WHITE);
                                }
                                // Showing Alert Dialogs, according to overcoming the threshold value of flexion's angle
                                if (thresholdCounter == 2) {
                                    alertDialog.show();
                                    thresholdCounter++;
                                } else if ((thresholdCounter == 4)&&(!alertDialog.isShowing())) {
                                    alertDialog2.show();
                                    thresholdCounter++;
                                } else if ((thresholdCounter == 5) && ((!alertDialog.isShowing()) && (!alertDialog2.isShowing()))) {
                                    thresholdCounter = 0;
                                    alertDialog3.show();
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {}
            }
        };
        t.start();
    }


    //callback that inflates option menu, when menu button is pressed
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_bluetooth_connection, menu);
        return true;

    }
    // callback that is called when item from option menu is selected
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) { //check selected items ID

            case R.id.settings: // in case of settings activity selected
                Intent intent3 = new Intent(this, PreferencesActivity.class);
                startActivity(intent3);
                return true;
            default:
                return false;
        }
    }
}
