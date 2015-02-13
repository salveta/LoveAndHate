package com.example.lovehate.loveandhate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DataBaseHelper extends SQLiteOpenHelper {

    //Datos de la tabla
    final private static String NAME = "lHate03_db"; //nombre DDBB
    final static String TABLE_LOVEHATE = "lovehateTable"; //Nombre de la tabla
    //Columnas
    final static String ID = "_id";
    final static String ITEM = "nombre";
    final static String CHECK = "checki";
    final static String IMG = "imagen";


    //comandos
    final static String[] columns = {ID, ITEM, IMG, CHECK};
    final private static String CREATE_CMD =

            "CREATE TABLE " + TABLE_LOVEHATE + " ("
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ITEM + " TEXT NOT NULL, "
                    + IMG + " STRING, "
                    + CHECK + " INTEGER DEFAULT 1) ";


    final private static Integer VERSION = 1;
    final private Context mContext;


    //Modos edicion
    public static final String C_MODO  = "modo" ;
    public static final int C_VISUALIZAR = 551 ;
    public static final int C_CREAR = 552 ;
    public static final int C_EDITAR = 553 ;


    //Constructor
    public DataBaseHelper(Context context) {
        super(context, NAME, null, VERSION);
        this.mContext = context;
    }

    //Creación de la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creamos la base de datos
        Log.i(this.getClass().toString(), "Tabla AMORODIO creada");
        db.execSQL(CREATE_CMD);
        //La rellenamos
        ContentValues values = new ContentValues();


        values.put(DataBaseHelper.ITEM, "Vetusta Morla");
        values.put(DataBaseHelper.CHECK, 0);
        values.put(DataBaseHelper.IMG, "");
        db.insert(DataBaseHelper.TABLE_LOVEHATE, null, values);
        values.clear();

        values.put(DataBaseHelper.ITEM, "ColdPlay");
        values.put(DataBaseHelper.CHECK, 0);
        values.put(DataBaseHelper.IMG, "");
        db.insert(DataBaseHelper.TABLE_LOVEHATE, null, values);
        values.clear();

        values.put(DataBaseHelper.ITEM, "All India Radio");
        values.put(DataBaseHelper.CHECK, 1);
        values.put(DataBaseHelper.IMG, "");
        db.insert(DataBaseHelper.TABLE_LOVEHATE, null, values);
        Log.i(this.getClass().toString(), "Datos insertados");
    }

    //Actualización de la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // N/A
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // N/A
    }

    //Borrando de la base de datos
    void deleteDatabase() {
        mContext.deleteDatabase(NAME);
    }

    //Lectura de la base de datos
    public Cursor readArtistas(SQLiteDatabase db, int love) {
        String consulta = "";
        if (love == 1)
        {
            consulta = " checki=1 ";
        }
        if (love == 0)
        {
            consulta = " checki=0 ";
        }
        return db.query(TABLE_LOVEHATE, columns, consulta, new String[] {}, null, null, null);
    }


    /**
     * Devuelve cursor con todos las columnas del registro
     */
    public Cursor getRegistro(long id) throws SQLException
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c = db.query( true, TABLE_LOVEHATE, columns, ID + "=" + id, null, null, null, null, null);

        //Nos movemos al primer registro de la consulta
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**
     * Inserta los valores en un registro de la tabla
     */
    public long insert(ContentValues reg)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.insert(TABLE_LOVEHATE, null, reg);
    }

    /**
     * Inserta los valores en un registro de la tabla
     */
    public long update(ContentValues reg)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        if (reg.containsKey(ID))
        {
            //
            // Obtenemos el id y lo borramos de los valores
            //
            long id = reg.getAsLong(ID);

            reg.remove(ID);

            //
            // Actualizamos el registro con el identificador que hemos extraido
            //
            return db.update(TABLE_LOVEHATE, reg, "_id=" + id, null);
        }
        return db.insert(TABLE_LOVEHATE, null, reg);
    }

}