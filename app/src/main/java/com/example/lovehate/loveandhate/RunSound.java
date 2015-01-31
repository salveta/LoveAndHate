package com.example.lovehate.loveandhate;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;


public class RunSound extends Activity {
    private int sonando=0;
    private MediaPlayer mPlayer;
    private int mSoundId;
    private AudioManager mAudioManager;
    private boolean mCanPlayAudio;
    private SoundPool mSoundPool;
    private SharedPreferences pref;
    private boolean audioAct;
    public static final String PRESS_NAME = "AmorOdioSettings";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_sound);

        //Capturamos preferencias
        pref = getSharedPreferences(PRESS_NAME, 0);
        audioAct = pref.getBoolean("Audio", true);
        Log.d("AUDIO SET", String.valueOf(audioAct));

        // Capturamos el servicio que nos proporciona manejar Sonidos
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);



        // Desactivamos el boton del play
        final ToggleButton toggleButtonSound = (ToggleButton) findViewById(R.id.toggleButton);
        toggleButtonSound.setEnabled(false);



        // Cargamos la cancion
        mPlayer = MediaPlayer.create(this, R.raw.coldplay);


        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
               @Override
               public void onPrepared (MediaPlayer mp){
                 Log.d("AUDIO", "Cargada la cancion");
                  toggleButtonSound.setEnabled(true);
                 }
               }
        );

        // Suena la cancion
        toggleButtonSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sonando==0){
                    toggleButtonSound.setText("On");
                    sonando=1;

                    mPlayer.start();

                }else if(sonando==1){
                    toggleButtonSound.setText("Off");
                    sonando=2;

                    mPlayer.pause();
                }else{
                    toggleButtonSound.setText("On");
                    sonando=1;

                    mPlayer.start();
                }
            }
        });

        // Request audio focus
        int result = mAudioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        mCanPlayAudio = AudioManager.AUDIOFOCUS_REQUEST_GRANTED == result;
    }

    // Get ready to play sound effects
    @Override
    protected void onResume() {
        Log.d("AUDIO", "VOLVIENDO A TOCAR");
        super.onResume();
        mAudioManager.setSpeakerphoneOn(true);
        mAudioManager.loadSoundEffects();
    }

    // Release resources & clean up
    @Override
    protected void onPause() {
        Log.d("AUDIO", "EN PAUSA");
        if (null != mAudioManager) {
            mPlayer.release();

        }
        mAudioManager.setSpeakerphoneOn(false);
        mAudioManager.unloadSoundEffects();
        super.onPause();
    }

    // Listen for Audio focus changes
    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                mAudioManager.abandonAudioFocus(afChangeListener);
                mCanPlayAudio = false;
            }
        }
    };



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
