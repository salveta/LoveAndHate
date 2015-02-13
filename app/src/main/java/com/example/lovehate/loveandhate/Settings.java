package com.example.lovehate.loveandhate;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;


public class Settings extends Activity {

    private SharedPreferences pref;
    private boolean audioAct;
    private AudioManager mAudioManager;
    private MediaPlayer mPlayer;
    private boolean mCanPlayAudio;
    public static final String PREFS_NAME = "AmorOdioSettings";

    //Variables BBDD
    private DataBaseHelper mDbHelper;
    private SQLiteDatabase db;
    private SimpleCursorAdapter mAdapter;
    private Cursor c;

    private static final String TAG = "Datos";

    public static final String C_MODO  = "modo" ;
    public static final int C_VISUALIZAR = 551 ;
    public static final int C_CREAR = 552 ;
    public static final int C_EDITAR = 553 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Capturamos las preferencias del usuario
        pref = getSharedPreferences(PREFS_NAME,0);
        audioAct=pref.getBoolean("Audio",true);
        Log.d("AUDIO SET", String.valueOf(audioAct));


        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        mPlayer = MediaPlayer.create(this, R.raw.coldplay);

        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                 @Override
                 public void onPrepared (MediaPlayer mp){
                    Log.d("AUDIO", "Cargada la cancion");
                    if (audioAct) mPlayer.start();
                    }
                 }
        );

        //Create variable button
        final Button buttonLoveHate = (Button) findViewById(R.id.loveHate);
        final Button buttonClose = (Button) findViewById(R.id.close);
        final Button buttonSound = (Button) findViewById(R.id.sound);
        final Button love = (Button) findViewById(R.id.love);
        final Button hate = (Button) findViewById(R.id.hate);

        hate.setOnClickListener(new Button.OnClickListener(){
             @Override
             public void onClick(View v) {
             //creamos la intención para lanzar la segunda vista
             Intent launchHate = new Intent(
                Settings.this, Hate.class
             );
             startActivity(launchHate);
             }
        }
        );


        love.setOnClickListener(new Button.OnClickListener(){
              @Override
              public void onClick(View v) {
              //creamos la intención para lanzar la segunda vista
              Intent launchLove = new Intent(
                Settings.this, Love.class
              );
              startActivity(launchLove);
              }
        }
        );

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

        // Request audio focus
        int result = mAudioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        mCanPlayAudio = AudioManager.AUDIOFOCUS_REQUEST_GRANTED == result;


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

    // Listen for Audio focus changes
    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                mAudioManager.abandonAudioFocus(afChangeListener);
            }
        }
    };


    @Override
    protected void onResume() {
        Log.d("AUDIO", "VOLVIENDO A TOCAR");
        super.onResume();
        audioAct=pref.getBoolean("audio", true);
        mAudioManager.setSpeakerphoneOn(true);
        mAudioManager.loadSoundEffects();
        if(audioAct) mPlayer.start();
    }

    @Override
    protected void onPause() {
        Log.d("AUDIO", "EN PAUSA");
        mAudioManager.setSpeakerphoneOn(false);
        mAudioManager.unloadSoundEffects();
        audioAct = pref.getBoolean("audio", true);
        if(audioAct) mPlayer.pause();
        super.onPause();
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
