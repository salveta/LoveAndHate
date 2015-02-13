package com.example.lovehate.loveandhate;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ToggleButton;


public class RunSound extends Activity {
    private SharedPreferences pref;
    private boolean audioAct;
    public static final String PRESS_NAME = "AmorOdioSettings";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_sound);

        pref = getSharedPreferences(PRESS_NAME, 0);
        audioAct = pref.getBoolean("audio", true);
        Log.d("AUDIO SET", String.valueOf(audioAct));


        // Take audio settings
        ToggleButton toggleButtonSound = (ToggleButton) findViewById(R.id.toggleButton);
        toggleButtonSound.setChecked(audioAct);

        toggleButtonSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isCheked) {
                SharedPreferences.Editor prefEd = pref.edit();
                if(isCheked){
                    prefEd.putBoolean("audio", true);
                    prefEd.commit();
                }else{
                    prefEd.putBoolean("audio", false);
                    prefEd.commit();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_run_sound, menu);
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
