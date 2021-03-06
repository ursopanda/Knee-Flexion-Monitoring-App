package lv.edi.SmartWear;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class PreferencesActivity extends PreferenceActivity{
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs); // not deprecated for particular android version

		Preference button = (Preference)findPreference("open_test_preference");
		button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
		                @Override
		                public boolean onPreferenceClick(Preference arg0) { 
		                    //code for what you want it to do 
		                	   Intent intent = new Intent(getApplicationContext(),TestPreferenceActivity.class);
		                	   startActivity(intent);
		                    return true;
		                }
		            });
		Preference buttonRestore = (Preference)findPreference("restore_defaults");
		buttonRestore.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			
			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
				SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				SharedPreferences.Editor editor = sharedPrefs.edit();
				editor.clear();
				editor.commit();
				PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.prefs, true);
				PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.testprefs, true);
				overridePendingTransition(0, 0);
				Intent intent = getIntent();
			    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				finish();
				overridePendingTransition(0, 0);
				startActivity(intent);
				Toast.makeText(getApplicationContext(),"Settings restored to default", Toast.LENGTH_SHORT).show();
				return true;
			}
		});
	}
	
	 public boolean onCreateOptionsMenu(Menu menu) {
	        return false;
	    }

}
