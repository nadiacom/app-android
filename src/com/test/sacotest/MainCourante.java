package com.test.sacotest;

import java.util.Locale;

import database.DBAdapter;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.test.sacotest.DonneesPrimaires;

public class MainCourante extends ActionBarActivity {
	 
    private String[] myCategories;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    
    //Database mode = local
    int DBMode = 0;

 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Open database
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
		editor.putInt("DBMode", DBMode );
		editor.commit();
		DBAdapter dba = new DBAdapter(getApplicationContext());
		dba.open();

 
        mTitle = "test";
 
        myCategories = getResources().getStringArray(R.array.categories_main_courante);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
 
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, myCategories));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
 
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {
 
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
            }
 
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mTitle);
            }
        };
 
        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        if (savedInstanceState == null) {
            selectItem(0);
        }
 
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
 
 
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
 
        return super.onOptionsItemSelected(item);
    }
 
    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int position) {
    	switch(position){
    	case 0:
    		//Toast.makeText(this, "coucou", Toast.LENGTH_SHORT).show();
    		Fragment fragment = new DonneesPrimaires();
    		// Insert the fragment by replacing any existing fragment
    	    FragmentManager fragmentManager = getSupportFragmentManager();
    	    fragmentManager.beginTransaction()
    	                   .replace(R.id.content_frame, fragment)
    	                   .commit();
    		break;
    	case 1:
    		Fragment fragment1 = new Identification();
    		// Insert the fragment by replacing any existing fragment
    	    FragmentManager fragmentManager1 = getSupportFragmentManager();
    	    fragmentManager1.beginTransaction()
    	                   .replace(R.id.content_frame, fragment1)
    	                   .commit();
//    		Intent main= new Intent(this, MainCourante.class);
//    		startActivity(main);
    		break;
    		
    	case 2:
    		Fragment fragment2 = new Circonstances();
    		// Insert the fragment by replacing any existing fragment
    	    FragmentManager fragmentManager2 = getSupportFragmentManager();
    	    fragmentManager2.beginTransaction()
    	                   .replace(R.id.content_frame, fragment2)
    	                   .commit();
//    		Intent main= new Intent(this, MainCourante.class);
//    		startActivity(main);
    		break;
    	
    	}
       // Toast.makeText(this, R.string.app_name, Toast.LENGTH_SHORT).show();
 
        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(myCategories[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
 
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
 
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }
 
}