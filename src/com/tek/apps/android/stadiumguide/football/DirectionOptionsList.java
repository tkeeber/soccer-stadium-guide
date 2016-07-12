package com.tek.apps.android.stadiumguide.football;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.tek.apps.android.stadiumguide.R;
import com.tek.apps.android.stadiumguide.database.AwayTeamDbAdapter;

public class DirectionOptionsList extends AbstractListActivity{

	private AwayTeamDbAdapter mDbAdapter;

	private int mStadiumId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.directions_options_list);

		setListAdapter(new DirectionsListAdapter(getLayoutInflater()));
		
		mDbAdapter = new AwayTeamDbAdapter(this);
		mDbAdapter.open();
		
		
		mStadiumId = getIntent().getIntExtra("stadium_id", -1);

		Log.d("","Stadium id is: " + mStadiumId);
		 ListView lv = getListView();
			lv.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

					/**
					 * "General Information", "Getting there", "Parking", 
					 * "Food And Drink", "Comment/Ratings", "History",   "Show on map", "Call Club", "Visit Website", "Send Email"
					 */
					
					switch (position) {
						case 0 : startDrivingDirectionsActivity(); break;
						case 1 : startByBusDirectionActivity(); break;
						case 2 : startByTrainDirectionActivity(); break;
					}
				}						
			});
	}
	
	private void startByBusDirectionActivity() {
		 startGenericDirectionsActivity("By Bus", mDbAdapter.fetchDirections(mStadiumId, AwayTeamDbAdapter.StadiumDirections.STADIUM_DIRECTIONS_BUS_INFO));
	}
	
	private void startByTrainDirectionActivity() {
		 startGenericDirectionsActivity("By Train", mDbAdapter.fetchDirections(mStadiumId, AwayTeamDbAdapter.StadiumDirections.STADIUM_DIRECTIONS_TRAIN_INFO));
	}
	
	private void startGenericDirectionsActivity(String title, String directions) {
		Intent i = new Intent(this, GenericInformationActivity.class);
		i.putExtra("title", title);
		i.putExtra("data", directions);
		startActivity(i);
	}
			
	private void startDrivingDirectionsActivity() {
		Cursor c = mDbAdapter.fetchStadiumDetails(mStadiumId);
		startManagingCursor(c);
		double latitude = 0;
		double longitude = 0;
		 try {
			   if (c.moveToFirst()) {
				   latitude = c.getDouble(4);
				   longitude = c.getDouble(5);
			   }
		 } finally {
			 c.close();
		 }
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
		Uri.parse("http://maps.google.com/maps?&daddr=" + latitude + "," + longitude));
		startActivity(intent);
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mDbAdapter != null) {
			mDbAdapter.close();		
			mDbAdapter = null;
		}
	}


	private class DirectionsListAdapter extends BaseAdapter  {

		private LayoutInflater mLayoutFlater;

		private String[] directionTitles = {"By Car", "By Bus", "By Train"};

		public DirectionsListAdapter(LayoutInflater layoutFlater) {
			super();
			this.mLayoutFlater = layoutFlater;
		}


		public int getCount() {
			return directionTitles.length;
		}


		public Object getItem(int position) {
			return directionTitles[position];
		}


		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = null;
			if (convertView == null)
			{
				rowView  = (View) mLayoutFlater.inflate(R.layout.directions_options_row, null);				
				TextView rowTitle = (TextView) rowView.findViewById(R.id.text_option_title);
				rowTitle.setText(directionTitles[position]);
			}
			else
			{
				rowView = (View) convertView;
				TextView rowTitle = (TextView) rowView.findViewById(R.id.text_option_title);
				rowTitle.setText(directionTitles[position]);
			}
			return rowView;
		}	
	}
	
	
}