package com.parse.buddyfinder;

import java.util.ArrayList;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.parse.integratingfacebooktutorial.R;

public class HomePageActivity extends FragmentActivity {

	
		private DrawerLayout mDrawerLayout;
	    private ListView mDrawerList;
	    private ActionBarDrawerToggle mDrawerToggle;
	 
	    public static ArrayList<Boolean> checked;
	    
	    // nav drawer title
	    private CharSequence mDrawerTitle;
	 
	    // used to store app title
	    private CharSequence mTitle;
	 
	    // slide menu items
	    private String[] navMenuTitles;
	    private TypedArray navMenuIcons;
	 
	    private ArrayList<DrawerItemClass> navDrawerItems;
	    private DrawerAdapter adapter;
	    
	    private LinearLayout linearLayout;
	    
	    
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		
		linearLayout = (LinearLayout) findViewById(R.id.myLinearLayout);
		
		mTitle = mDrawerTitle = getTitle();
		checked = new ArrayList<Boolean>();
		for(int i=0;i<4;i++)
		{
			checked.add(true);
		}
		 
        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
 
        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);
 
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
 
        navDrawerItems = new ArrayList<DrawerItemClass>();
 
        navDrawerItems.add(new DrawerItemClass(navMenuTitles[0], R.drawable.ic_home));
        navDrawerItems.add(new DrawerItemClass(navMenuTitles[1], R.drawable.ic_pages));
        navDrawerItems.add(new DrawerItemClass(navMenuTitles[2], R.drawable.ic_people));
        navDrawerItems.add(new DrawerItemClass(navMenuTitles[3], R.drawable.ic_communities));
         
 
        // Recycle the typed array
        navMenuIcons.recycle();
 
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
 
        // setting the nav drawer list adapter
        adapter = new DrawerAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);
 
        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
 
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }
 
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
 
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
		 
		Fragment fragment = new HomePageContent(getApplicationContext());
		Bundle args = new Bundle();
		fragment.setArguments(args);

		// Insert the fragment by replacing any existing fragment
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();

		
	}
	
	public void initialize()
	{
		 // setting the nav drawer list adapter
        adapter = new DrawerAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);
        
	}
	
	public void drawerInit()
	{
		// load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
 
        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_items);
 
        
 
        navDrawerItems = new ArrayList<DrawerItemClass>();
 
        navDrawerItems.add(new DrawerItemClass(navMenuTitles[0], R.drawable.ic_home));
        navDrawerItems.add(new DrawerItemClass(navMenuTitles[1], R.drawable.ic_pages));
        navDrawerItems.add(new DrawerItemClass(navMenuTitles[2], R.drawable.ic_people));
        navDrawerItems.add(new DrawerItemClass(navMenuTitles[3], R.drawable.ic_communities));
         
 
        // Recycle the typed array
        navMenuIcons.recycle();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_page, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Fragment fragment2 = new SettingsPage(getApplicationContext());
			Bundle args2 = new Bundle();
			fragment2.setArguments(args2);

			// Insert the fragment by replacing any existing fragment
			FragmentManager fragmentManager2 = getSupportFragmentManager();
			fragmentManager2.beginTransaction()
			.replace(R.id.content_frame, fragment2)
			.commit();
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void showUserDetailsActivity() {
		Intent intent = new Intent(this, UserDetailsActivity.class);
		startActivity(intent);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.settings_page,
					container, false);
			return rootView;
		}
	}
	
	private class SlideMenuClickListener implements
	ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
		
		
		 
}
	
	private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
        case 0:
        	fragment = new HomePageContent();
            break;
        case 1:
        	fragment = new Events();
            break;
        case 2:
            fragment = new UserDetailsActivity();
            break;
        case 3:
        	fragment = new SettingsPage();
            break;
        case 4:
            break;
        case 5:
            break;
 
        default:
            break;
        }
 
        if (fragment != null) {
        	
    		Bundle args = new Bundle();
    		fragment.setArguments(args);

    		// Insert the fragment by replacing any existing fragment
    		FragmentManager fragmentManager = getSupportFragmentManager();
    		fragmentManager.beginTransaction()
    		.replace(R.id.content_frame, fragment)
    		.commit();
 
            // update selected item and title, then close the drawer
    		
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(linearLayout);
            
            
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }


}
