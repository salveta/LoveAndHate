package com.example.lovehate.loveandhate;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class Settings extends Activity {

    private SharedPreferences preferencias;
    private boolean audioAct;
    public static final String PREFS_NAME = "AmorOdioSettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Capturamos las preferencias del usuario
        preferencias = getSharedPreferences(PREFS_NAME,0);
        audioAct=preferencias.getBoolean("Audio",true);

        //Create variable button
        final Button buttonLoveHate = (Button) findViewById(R.id.loveHate);
        final Button buttonClose = (Button) findViewById(R.id.close);
        final Button buttonSound = (Button) findViewById(R.id.sound);

        buttonLoveHate.setOnClickListener(new Button.OnClickListener(){
             @Override
             public void onClick(View v) {
             //creamos la intención para lanzar la segunda vista
             Intent launchLoveHate = new Intent(
                Settings.this, MainActivity.class
             );
             startActivity(launchLoveHate);
             }
        }
        );

        buttonClose.setOnClickListener(new Button.OnClickListener(){
             @Override
             public void onClick(View v) {

                 finish();
              }
        }
        );

        buttonSound.setOnClickListener(new Button.OnClickListener(){
             @Override
             public void onClick(View v) {
             Intent launchRunSound = new Intent(
                 Settings.this, RunSound.class
             );
             startActivity(launchRunSound);
             }
        }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
