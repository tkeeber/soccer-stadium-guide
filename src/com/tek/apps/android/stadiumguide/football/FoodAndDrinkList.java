package com.tek.apps.android.stadiumguide.football;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;

import com.tek.apps.android.stadiumguide.R;
import com.tek.apps.android.stadiumguide.database.AwayTeamDbAdapter;
import com.tek.apps.android.stadiumguide.database.AwayTeamDbAdapter.FoodAndDrinkVenues;

public class FoodAndDrinkList extends ListActivity{

	private Integer mStadiumId;
	private String mStadiumName = null;
	private AwayTeamDbAdapter mAwayTeamDbAdapter; 

	private static final int CONTACT_US_ABOUT_VENUE = 0;
//	private static final int MY_TEAM = 1;
//	private static final int UPGRADE = 2;
//	private static final int PERFERENCES = 3;
	//private static final int CONTACT_DEVELOPER = 4;
	//private static final int DEVELOP_WEBSITE = 5;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.food_and_drink_list);

		Bundle b = this.getIntent().getExtras();		
		mStadiumId = b.getInt("stadium_id");
		mStadiumName = b.getString("stadium_name");

		mAwayTeamDbAdapter = new AwayTeamDbAdapter(this);
		mAwayTeamDbAdapter.open();
		
		Cursor cursor = mAwayTeamDbAdapter.fetchFoodAndDrinkVenues(mStadiumId);
		startManagingCursor(cursor);

		loadData(cursor);
		
		Button buttonViewAll = (Button) findViewById(R.id.button_view_all_on_map);
		
		buttonViewAll.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
			
					showAllOnMap();
			}
		});
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, CONTACT_US_ABOUT_VENUE, 0, R.string.contact_us_venue);
		return result;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId())
		{
		case CONTACT_US_ABOUT_VENUE:
			startEmailDeveloper();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void startEmailDeveloper() {

		/* Create the Intent */  
		final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);  

		/* Fill it with Data */  
		emailIntent.setType("plain/text");  
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.developer_email)});  
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Pub near " + mStadiumName);  
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");  

		/* Send it off to the Activity-Chooser */  
		startActivity(Intent.createChooser(emailIntent, "Send mail...")); 
	}
	
	
	public void startOpenDeveloperWebsite() {

		Intent i = new Intent();
		i.setAction(Intent.ACTION_VIEW);
		i.setData(Uri.parse(getResources().getString(R.string.developer_website_url)));

		startActivity(i);

	}
	
//	private void addFooter() {	
//			
//		View v = getLayoutInflater().inflate(R.layout.food_and_drink_footer, null);
//		
//		Button buttonViewAll = (Button)v.findViewById(R.id.button_view_all_on_map);
//		
//		buttonViewAll.setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View v) {
//			
//					showAllOnMap();
//			}
//		});
//
//		
//		getListView().addFooterView(v);
//	}
	
	private void showAllOnMap() {
		Cursor cursor = mAwayTeamDbAdapter.fetchFoodAndDrinkVenues(mStadiumId);
		startManagingCursor(cursor);
		
		int[] venuesToShow = new int[cursor.getCount()];
		cursor.moveToFirst();
		 for (int i=0; i<cursor.getCount(); i++) { 
			int id = cursor.getInt(0);
			venuesToShow[i] = id;
			cursor.moveToNext();
		}
		
		Intent i = new Intent(this, FoodAndDrinkMap.class);
		i.putExtra("venuesToShow", venuesToShow);
		i.putExtra("stadiumId", mStadiumId);
		startActivity(i);
	}
	
	private void loadData(Cursor cursor) {
		
		String[] from = {FoodAndDrinkVenues.VENUE_NAME_COL, FoodAndDrinkVenues.VENUE_DISTANCE_FROM_STADIUM, FoodAndDrinkVenues.VENUE_DESCRIPTION_COL};
		int [] to = {R.id.text_venue_name, R.id.text_distance_from_stadium, R.id.text_description};
		SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, 
				R.layout.food_and_drink_row, cursor, from, to);

		setListAdapter(listAdapter);
		
	}
	
//	protected void retrieveFoodAndDrinkVenues() {
//
//		// Fire off a thread to do some work that we shouldn't do directly in the UI thread
//		Thread t = new Thread() {
//			public void run() {
//				mVenues = mDataManager.getFoodAndDrinkVenues(mStadiumId);
//				mHandler.post(mUpdateResults);
//			}
//		};
//		t.start();
//	}

//	private void saveResultsToCache() {
//		if (mVenues != null) {
//			if (mAwayTeamDbAdapter.isOpen()) {
//				mAwayTeamDbAdapter.saveFoodAndDrinkToCache(mStadiumId, mVenues);
//				Cursor cursor = mAwayTeamDbAdapter.fetchFoodAndDrinkVenues(mStadiumId);
//				startManagingCursor(cursor);
//				loadData(cursor);
//			}
//		} else {
//			Log.d("", "no results!");
//		}
//	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		mAwayTeamDbAdapter.close();
		mAwayTeamDbAdapter = null;
	}

}
