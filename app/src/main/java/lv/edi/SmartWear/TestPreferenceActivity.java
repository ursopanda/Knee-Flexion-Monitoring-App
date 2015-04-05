package lv.edi.SmartWear;

import lv.edi.SmartWear.R;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;

public class TestPreferenceActivity extends PreferenceActivity {
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.testprefs); // not deprecitated for particular android version
		
		
	}
	
	 public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.activity_bluetooth_connection, menu);
	        return true;
	    }
		public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle item selection
	        switch (item.getItemId()) {
	            case R.id.bluetoth_connection_activity:
	                Intent intent = new Intent(this, DataSourceActivity.class);
	                startActivity(intent);
	                return true;
	            default:
	                return super.onOptionsItemSelected(item);
	        }
	    }
}
