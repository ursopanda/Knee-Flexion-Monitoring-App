package lv.edi.SmartWear;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import lv.edi.Graph.Animation;


public class FlexionAnimationActivity extends Activity {

    private SmartWearApplication application;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Reference to SmartWearApplication class, where we can get Accelerometer's Data
        application = (SmartWearApplication)getApplication();
        super.onCreate(savedInstanceState);
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        while (!isInterrupted()) {
                            Thread.sleep(200);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setContentView(new Animation(FlexionAnimationActivity.this));
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                    }
                };
            };
            t.start();
        }

//    @Override
//    protected void onDraw


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_flexion_animation, menu);
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
