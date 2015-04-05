package lv.edi.SmartWear;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import lv.edi.Database.Databasehandler;


public class DisplayStatistics extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_statistics);
        // Creating an object to reffer to an app DB
        Databasehandler db = new Databasehandler(this);
        // Getting Flexion's Maximum value to the table
        TextView maxFlexionValueText = (TextView) findViewById(R.id.maxFlexionValue);
        maxFlexionValueText.setText(db.getMaxFlexionValue());
        // Getting Flexion's Average value to the table
        TextView avgFlexionValueText = (TextView) findViewById(R.id.avgFlexionValue);
        // Parsing from double to String to put it into the TextView
        String avgValue = Double.toString(db.getAverageFlexionValue());
        avgFlexionValueText.setText(avgValue);
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
