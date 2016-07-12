package com.tek.apps.android.stadiumguide.football;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.tek.apps.android.stadiumguide.R;
import com.tek.apps.android.stadiumguide.database.AwayTeamDbAdapter;

public class FootballClubOptionsList extends AbstractListActivity{

	private AwayTeamDbAdapter mDbAdapter;
	
	private String mTeamName;
	private String mTeamWebsite;
	private String mTeamTelephone;
	private int mStadiumId = -999;
	private String mStadiumName;
	private String mStadiumCapity;
	private String mStadiumAddress;
	private String mEmailAddress; 
	private double mLatitude;
	private double mLongitude;
	
	
	private final static int numberOfOptions = 9;
	private static String[] mClubOptionsDetail = new String[numberOfOptions];
	
	private int mFootballClubId;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			setContentView(R.layout.football_club_options_list);
			
			mDbAdapter = new AwayTeamDbAdapter(this);
			mDbAdapter.open();
			
	
			Bundle b = this.getIntent().getExtras();		
			mFootballClubId = b.getInt("football_club_id");
			        
			//"General Information", "Getting there", "Parking", 
			// "Food And Drink", "Comment/Ratings", "History",   "Show on map", 
			//"Call Club", "Visit Website", "Send Email"
			initaliseTeam(mFootballClubId);
//			addHeader();
			//general information
			mClubOptionsDetail[0] = "";
			//"Getting there
			mClubOptionsDetail[1] = "Drive/Train/Bus";
			//Parking
			mClubOptionsDetail[2] = "";
			//Food And Drink
			mClubOptionsDetail[3] = "";
			//"Comment/Ratings"
		//	mClubOptionsDetail[4] = "";
			//History
			mClubOptionsDetail[4] = mStadiumName;
			//Show on map
			mClubOptionsDetail[5] = mTeamTelephone;
			//Call Club
			mClubOptionsDetail[6] = "";
			//Visit Website
			mClubOptionsDetail[7] = mTeamWebsite;
			//Send Email
			mClubOptionsDetail[8] = mEmailAddress;
			
			
			Drawable[] rowDrawables = new Drawable[numberOfOptions];
			//general information
			rowDrawables[0] = getResources().getDrawable(R.drawable.question_mark_orb_175);
			//"Getting therestadium_name
			rowDrawables[1] = getResources().getDrawable(R.drawable.treasure_map_icon_32);
			//Parking
			rowDrawables[2] = getResources().getDrawable(R.drawable.car_32x32);
			//Food And Drink
			rowDrawables[3] = getResources().getDrawable(R.drawable.food_32);
			//"Comment/Ratings"
		//	rowDrawables[4] = getResources().getDrawable(R.drawable.star_32);
			//History
			rowDrawables[4] = getResources().getDrawable(R.drawable.history_32);
			//Show on map
			rowDrawables[5] = getResources().getDrawable(R.drawable.treasure_map_icon_32);
			//Call Club
			rowDrawables[6] = getResources().getDrawable(R.drawable.phone_32x32);
			//Visit Website
			rowDrawables[7] = getResources().getDrawable(R.drawable.earth_icon_32);
			//Send Email
			rowDrawables[8] = getResources().getDrawable(R.drawable.email_32);
			
	        FootballClubListAdapter adapter = new FootballClubListAdapter(getLayoutInflater(), mClubOptionsDetail, rowDrawables);
	        setListAdapter(adapter);
	        
	        ListView lv = getListView();
			lv.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

					/**
					 * "General Information", "Getting there", "Parking", 
					 * "Food And Drink", "Comment/Ratings", "History",   "Show on map", "Call Club", "Visit Website", "Send Email"
					 */
					
					switch (position) {
					case 0 : startGeneralInformationActivity(); break;
					case 1 : startDirectionsOptionsActivity(); break;
					case 2 : startParkingAcivity(); break;
					case 3 : startFoodAndDrinkActivity(); break;
//					case 4 : startCommentRatingsActivity(); break;
					case 4 : startHistoryActivity(); break;
					case 5 : startShowOnMap(); break;
					case 6 : startFootballClubTelephoneList(); break;
					case 7 : startOpenWebsite(); break;
					case 8 : startEmailActivity(); break; 
					}    

				}						
			});
	        
		//			 ListView lv = getListView();
//			 lv.setTextFilterEnabled(true);
//			 lv.setOnItemClickListener(new OnItemClickListener() {
//
//				public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//				
//					Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
//				    int keyId =  cursor.getInt(cursor.getColumnIndex(AwayTeamDbAdapter.FOOTBALL_CLUB_ID_COL));
//					 
//					Toast.makeText(getApplicationContext(), "id is " + keyId, Toast.LENGTH_SHORT).show();
//					
//					startFootballClubHomeActivity(keyId);
//					
//				}					
//			});
			
		//retrieve the latest club ratings.
		//ConnectivityUtils connectivityUtils = new ConnectivityUtils(this);
		//if (connectivityUtils.isConnected()) {
//				new InitAsyncTask().execute();
		//}		
	}
	
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (this.mDbAdapter != null) {
			this.mDbAdapter.close();
			this.mDbAdapter.nullContext();
			this.mDbAdapter = null;
		}		
		this.mFootballClubId = -1;
		this.mStadiumId = -1;
		this.mStadiumName = null;
		this.mTeamName = null;
		this.mTeamTelephone = null;
		this.mTeamWebsite = null;
	}



	private void initaliseTeam(int id) {
		Cursor cursor = mDbAdapter.fetchFootballTeam(id);
		startManagingCursor(cursor);

		try {
			if (cursor.moveToFirst()) {

				mTeamName = cursor.getString(1);
				mStadiumId = cursor.getInt(3);
								
				TextView txtTeamName = (TextView) findViewById(R.id.text_club_name);
				txtTeamName.setText(mTeamName);
							
				mTeamWebsite = cursor.getString(4);
				
				mEmailAddress = cursor.getString(5);
			}
		}
		finally {
			cursor.close();
			cursor = null;
		}
		
		if (mStadiumId != -999) {
			try { 
				cursor = mDbAdapter.fetchStadiumDetails(mStadiumId);
				if (cursor.moveToFirst()) {
									
					mStadiumName = cursor.getString(1);
					mStadiumCapity = cursor.getString(2);
					mStadiumAddress = cursor.getString(3);
					mLatitude = cursor.getDouble(4);
					mLongitude = cursor.getDouble(5);
					TextView txtStadiumName = (TextView) findViewById(R.id.text_stadium_name);
					txtStadiumName.setText(cursor.getString(1));
					TextView txtCapacity = (TextView) findViewById(R.id.text_stadium_capacity);
					txtCapacity.setText(cursor.getString(2));
					TextView txtAddress = (TextView) findViewById(R.id.text_stadium_address);
					txtAddress.setText(cursor.getString(3));
				}
			} finally {
				cursor.close();
				cursor = null;
			}
		}
	}
	

    protected void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
    	
    	switch (position) {
    		case 1 : startFootballClubTelephoneList(); break;
    		case 2 : break;
    		case 3 : break;
    	}    	
    }
    
	private void startParkingAcivity() {
		 startGenericDirectionsActivity("Parking", mDbAdapter.fetchParking(mStadiumId));
	}
	
	private void startGenericDirectionsActivity(String title, String directions) {
		Intent i = new Intent(this, GenericInformationActivity.class);
		i.putExtra("title", title);
		i.putExtra("data", directions);
		startActivity(i);
	}
	
	private void startHistoryActivity() {
		Intent i = new Intent(this, StadiumHistoryActivity.class);
		i.putExtra("stadium_id", mStadiumId);
		startActivity(i);	
	}
	
	private void startEmailActivity() {

		if (mEmailAddress == null || mEmailAddress.length() == 0) {
			Toast.makeText(this, mTeamName + " do not have an email address. Sorry...", 2000).show();
		}
		else{
		
			/* Create the Intent */  
			final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);  
	
			/* Fill it with Data */  
			emailIntent.setType("plain/text");  
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{mEmailAddress});  
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");  
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");  
	
			/* Send it off to the Activity-Chooser */  
			startActivity(Intent.createChooser(emailIntent, "Send mail..."));  
		}
	}
	
	private void startDirectionsOptionsActivity() {
		
		Intent i = new Intent(this, DirectionOptionsList.class);
		i.putExtra("stadium_id", mStadiumId);
		startActivity(i);	
	}
	
	private void startFoodAndDrinkActivity() {
			
		Intent i = new Intent(this, FoodAndDrinkList.class);
		i.putExtra("stadium_id", mStadiumId);
		i.putExtra("stadium_name", mStadiumName);
		startActivity(i);
	}
	
	private void startInsideTheStadium() {		
		Intent i = new Intent(this, InsideTheStadium.class);
		i.putExtra("stadium_id", mStadiumId);
		startActivity(i);
	}
	
	private void startGeneralInformationActivity() {
		Intent i = new Intent(this, GeneralInformationActivity.class);
		i.putExtra("stadium_id", mStadiumId);
		startActivity(i);
	}	
	
	private void startShowOnMap() {
		
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + mStadiumAddress));
		intent.putExtra("stadium_id", mStadiumId);
		startActivity(intent);
	}
	
	
    public void startOpenWebsite() {
    	
    	if (mTeamWebsite == null) {    		
    		Toast.makeText(getApplicationContext(), "No Website for this club", Toast.LENGTH_SHORT).show();
    	} else {
//    		Toast.makeText(getApplicationContext(), "Opening (" + mTeamWebsite + ")", Toast.LENGTH_SHORT).show();
    		Intent i = new Intent();
    		i.setAction(Intent.ACTION_VIEW);
    		i.setData(Uri.parse(mTeamWebsite));
    		
    		startActivity(i);
    	}
    }
    
    public void startFootballClubTelephoneList() {
    	
    	Intent intent = new Intent(this, ClubTelephoneNumberList.class);
		intent.putExtra("football_club_id", mFootballClubId);
    	startActivity(intent);
    }
    
    

	private class FootballClubListAdapter extends BaseAdapter  {

		private LayoutInflater mLayoutFlater;
		
		//"General Information", "Getting there", "Parking", "Food And Drink", "Comment/Ratings", "History",   "Show on map", "Call Club", "Visit Website", "Send Email"
		private String[] forwardOptions = {"General Information", "Getting there", "Parking", "Food And Drink", "History",   "Show on map", "Call Club", "Visit Website", "Send Email"};
		private Drawable[] rowDrawables;
		private String[] clubOptionsDetail;
		
		public FootballClubListAdapter(LayoutInflater layoutFlater, 
									   String[] clubOptionsDetail, Drawable[] rowDrawables) {
			super();
			this.mLayoutFlater = layoutFlater;
			this.clubOptionsDetail = clubOptionsDetail;
			this.rowDrawables = rowDrawables;
		}

		
		public int getCount() {
			return forwardOptions.length;
		}

		
		public Object getItem(int position) {
			return forwardOptions[position];
		}

		
		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = null;
			if (convertView == null)
			{
				rowView  = (View) mLayoutFlater.inflate(R.layout.football_club_options_row, null);			
				//inflate from the quote row source
//				textView = new TextView(mContext);
//				textView.setPadding(5, 1, 1, 1);
//				textView.setTextSize(20);
				//set the values in the text.
				ImageView image = (ImageView) rowView.findViewById(R.id.image_football_row);
				TextView rowTitle = (TextView) rowView.findViewById(R.id.text_row_title);
				TextView rowSubTitle = (TextView) rowView.findViewById(R.id.text_row_sub);
				rowTitle.setText(forwardOptions[position]);
				rowSubTitle.setText(clubOptionsDetail[position]);
				if (rowDrawables[position] != null) {
					image.setImageDrawable(rowDrawables[position]);
				}
			}
			else
			{
				rowView = (View) convertView;
				ImageView image = (ImageView) rowView.findViewById(R.id.image_football_row);
				TextView rowTitle = (TextView) rowView.findViewById(R.id.text_row_title);
				TextView rowSubTitle = (TextView) rowView.findViewById(R.id.text_row_sub);
				rowTitle.setText(forwardOptions[position]);
				rowSubTitle.setText(clubOptionsDetail[position]);
				if (rowDrawables[position] != null) {
					image.setImageDrawable(rowDrawables[position]);
				}
			}
			return rowView;
		}	
	}

}
