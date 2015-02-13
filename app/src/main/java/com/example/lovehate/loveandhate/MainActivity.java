package com.example.lovehate.loveandhate;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    //Initialize the class of the fragments
    static final Down fragmentDown = new Down();
    static final Up fragmentUp = new Up();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create the up and down fragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.down, fragmentDown);
        fragmentTransaction.add(R.id.up, fragmentUp);
        fragmentTransaction.commit();
    }

    //create method to get info of fragment up
    protected void itemSelected(String itemSelectedRecived){
        fragmentDown.setItemSelected(itemSelectedRecived);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}