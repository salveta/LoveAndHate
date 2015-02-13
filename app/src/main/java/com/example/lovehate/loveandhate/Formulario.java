package com.example.lovehate.loveandhate;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Formulario extends Activity {

    private TextView labelId;
    private EditText nombre;

    //Identificador de la base de datos
    private DataBaseHelper mDbHelper;
    private SQLiteDatabase db;
    private long id;
    private String ruta = "";
    private ImageView imagenMostrar;
    private Bitmap bitmap;

    //
    // Modo del formulario
    //
    private int modo ;
    private int tipo;

    //Botones
    private Button boton_guardar;
    private Button boton_cancelar;
    private Button boton_camara;
    private Button boton_ver;


    //camara
    //Fichero de guardado
    private Uri fileUri;

    //Tipos definidos
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        //CApturamos los datos enviados
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();

        if (extra == null) return;

        // Consultamos la base de datos
        mDbHelper = new DataBaseHelper(this);
        db = mDbHelper.getWritableDatabase();

        //
        // Obtenemos los elementos de la vista
        //
        labelId = (TextView) findViewById(R.id.label_id);
        nombre = (EditText) findViewById(R.id.nombre);
        imagenMostrar = (ImageView) findViewById(R.id.image);

        //
        // Obtenemos el identificador del registro si viene indicado
        //
        if (extra.containsKey(DataBaseHelper.ID)) {
            id = extra.getLong(DataBaseHelper.ID);
            consultar(id);
        }
        //Botones de guardado y cancelar
        boton_guardar = (Button) findViewById(R.id.boton_guardar);
        boton_cancelar = (Button) findViewById(R.id.boton_cancelar);
        boton_camara = (Button) findViewById(R.id.camara);
        boton_ver = (Button) findViewById(R.id.botonVer);



        //
        // Establecemos el modo del formulario
        //
        establecerModo(extra.getInt(mDbHelper.C_MODO));


        //
        // Definimos las acciones para los dos botones
        //

        boton_ver.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent camara = new Intent();
                camara.setAction(Intent.ACTION_VIEW);
                camara.setDataAndType(Uri.parse("file://" + ruta), "image/*");
                startActivity(camara);

            }
        });

        boton_camara.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent camara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
                camara.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

                startActivityForResult(camara, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

                ruta = fileUri.getPath();

            }
        });

        boton_guardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                guardar();
            }
        });

        boton_cancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                cancelar();
            }
        });
    }




    private void consultar(long id)
    {
        //
        // Consultamos el centro por el identificador
        //
        Cursor cursor = mDbHelper.getRegistro(id);
        labelId.setText(labelId.getText()+cursor.getString(cursor.getColumnIndex(DataBaseHelper.ID)));
        nombre.setText(cursor.getString(cursor.getColumnIndex(DataBaseHelper.ITEM)));
        ruta = cursor.getString(cursor.getColumnIndex(DataBaseHelper.IMG));

        bitmap = BitmapFactory.decodeFile(ruta);
        imagenMostrar.setImageBitmap(bitmap);

    }

    private void establecerModo(int m)
    {
        this.modo = m ;

        if (modo == mDbHelper.C_VISUALIZAR)
        {
            this.nombre.setEnabled(false);
            this.boton_guardar.setEnabled(false);
        }else if ((modo == mDbHelper.C_CREAR)||(modo == mDbHelper.C_EDITAR)){
            this.setTitle(R.string.hipoteca_crear_titulo);
            this.nombre.setEnabled(true);
            this.boton_guardar.setEnabled(true);
        }
    }

    private void guardar()
    {
        int odio = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        odio=extras.getInt("tipo");
        }
        //
        // Obtenemos los datos del formulario
        //
        ContentValues reg = new ContentValues();
        if (modo == mDbHelper.C_EDITAR) reg.put(mDbHelper.ID, id);
        reg.put(mDbHelper.ITEM, nombre.getText().toString());
        reg.put(mDbHelper.IMG, ruta);
        reg.put(mDbHelper.CHECK, odio);

        if (modo == mDbHelper.C_CREAR)
        {
            mDbHelper.insert(reg);
            Toast.makeText(Formulario.this, "Item creado", Toast.LENGTH_SHORT).show();
        }   else if (modo == mDbHelper.C_EDITAR){
            Toast.makeText(Formulario.this, "Item modificado", Toast.LENGTH_SHORT).show();
            mDbHelper.update(reg);
        }

        //
        // Devolvemos el control
        //
        setResult(RESULT_OK);
        finish();
    }

    private void cancelar()
    {
        setResult(RESULT_CANCELED, null);
        finish();
    }

    //Metodos camara
    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Proyecto06");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("Proycto06", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
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