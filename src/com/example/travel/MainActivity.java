package com.example.travel;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat; 
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;

public class MainActivity extends Activity {
	private DrawerLayout MenuList;
    private ListView MLDrawer;

    private ActionBarDrawerToggle drawerToggle;
    private CharSequence DrawerTitle;
    private CharSequence Title;
    
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
		
		initActionBar();
        initDrawer();
        initDrawerList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		menu.findItem(R.id.action_search).getActionView();
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		// click app icon and show drawer
		if (drawerToggle.onOptionsItemSelected(item)) {
	        return true;
	    }
		
		int id = item.getItemId();
		switch(id){
			//case R.id.action_settings:
				//return true;
			//case R.id.item1:
				//Toast.makeText(this, "Option1", Toast.LENGTH_SHORT).show();
				//return true;
			case R.id.action_search:
				openSearch();
				return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	/** Swaps fragments in the main content view */
	private void selectItem(int position){
		String[] drawer_menu = this.getResources().getStringArray(R.array.drawer_menu);
		// Create a new fragment and specify the planet to show based on position
		Fragment fragment = null;
		
		switch(position){
			case 0:
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, HomeMapActivity.class);
				startActivity(intent); 
				//fragment = new FragmentHome();
				break;
			case 1:
				fragment = new FragmentProfile();
				break;
			default:
				return;
		}
		FragmentManager fragmentManager = getFragmentManager();
	    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit(); 
		
		// Highlight the selected item, update the title, and close the drawer
		MLDrawer.setItemChecked(position, true);
	    setTitle(drawer_menu[position]);
	    MenuList.closeDrawer(MLDrawer);
	}
	
	@Override
	public void setTitle(CharSequence title) {
	    Title = title;
	    getActionBar().setTitle(Title);
	}
	
	private void openSearch() {
	    Toast.makeText(this, "Search button pressed", Toast.LENGTH_SHORT).show();
	}
	
	private void initActionBar(){
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
	}
	
	private void initDrawer(){
		setContentView(R.layout.drawer);

        MenuList = (DrawerLayout) findViewById(R.id.drawer_layout);
        MLDrawer = (ListView) findViewById(R.id.left_drawer);

        MenuList.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        Title = DrawerTitle = getTitle();
        drawerToggle = new ActionBarDrawerToggle(
        		this, 
                MenuList,
                R.drawable.ic_drawer, 
                R.string.drawer_open,
                R.string.drawer_close) {

            		@Override
            		public void onDrawerClosed(View view) {
            			super.onDrawerClosed(view);
            			getActionBar().setTitle(Title);
            		}

            		@Override
            		public void onDrawerOpened(View drawerView) {
            			super.onDrawerOpened(drawerView);
            			getActionBar().setTitle(DrawerTitle);
            		}
        		};
        drawerToggle.syncState();

        MenuList.setDrawerListener(drawerToggle);
	}
	
	private void initDrawerList(){
		String[] drawer_menu = this.getResources().getStringArray(R.array.drawer_menu);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, drawer_menu);
        MLDrawer.setAdapter(adapter);
        
        //側選單點選偵聽器
        MLDrawer.setOnItemClickListener(new DrawerItemClickListener());
    
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
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        selectItem(position);
	    }
	}

}
