package lv.edi.SmartWear;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import lv.edi.Database.Databasehandler;


public class DisplayStatistics extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_statistics);
        // Creating an object to reffer to an app DB
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
                                // Getting Flexion's Maximum value to the table
                                TextView maxFlexionValueText = (TextView) findViewById(R.id.maxFlexionValueText);
                                maxFlexionValueText.setText(String.valueOf(db.getMaxFlexionValue()));
                                // Getting Flexion's Average value to the table
                                TextView avgFlexionValueText = (TextView) findViewById(R.id.avgFlexionValue);
                                // Parsing from double to String to put it into the TextView
                                avgFlexionValueText.setText(String.valueOf(db.getAverageFlexionValue()));
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            };
        };
        t.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_statistics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
