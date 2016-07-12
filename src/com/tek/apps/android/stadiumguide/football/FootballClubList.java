package com.tek.apps.android.stadiumguide.football;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.tek.apps.android.stadiumguide.R;
import com.tek.apps.android.stadiumguide.database.AwayTeamDbAdapter;

public class FootballClubList extends AbstractListActivity {

	private AwayTeamDbAdapter mDbAdapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.football_clubs_list);

		mDbAdapter = new AwayTeamDbAdapter(getApplicationContext());
		mDbAdapter.open();		
		
//		Bundle b = this.getIntent().getExtras();		
//		int clubFilter = b.getInt("clubFilter");

		// Create an array to specify the fields we want to display in the list (only TITLE)
		String[] from = new String[]{AwayTeamDbAdapter.FootballClubs.FOOTBALL_CLUB_NAME_COL};

		// and an array of the fields we want to bind those fields to (in this case just text1)
		int[] to = new int[]{R.id.team_name};

		Cursor clubsCursor = retrieveClubs(1);
		startManagingCursor(clubsCursor);
		SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, R.layout.football_club_row, clubsCursor, from, to);

		setListAdapter(listAdapter);

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {		
		super.onListItemClick(l, v, position, id);
		
		Cursor cursor = (Cursor) getListView().getItemAtPosition(position);
		int keyId =  cursor.getInt(cursor.getColumnIndex(AwayTeamDbAdapter.FootballClubs._ID));

		startFootballClubHomeActivity(keyId);
		
	}
	
	private Cursor retrieveClubs(int clubFilter) {
		
		Cursor cursor = null;
		
		switch (clubFilter) {
			case FootballHelper.FILTER_PREMIERSHIP_CLUBS:
			case FootballHelper.FILTER_CHAMPIONSHIP_CLUBS:
			case FootballHelper.FILTER_LEAGUE_ONE_CLUBS:
			case FootballHelper.FILTER_LEAGUE_TWO_CLUBS: {
				cursor = mDbAdapter.fetchClubsByDivision(clubFilter);
				break;
			} 
			case FootballHelper.FILTER_ALL_CLUBS:
				cursor = mDbAdapter.fetchAllClubs();
				break;
		}
		startManagingCursor(cursor);
		return cursor; 
	}


	public void startFootballClubHomeActivity(int keyId) {

		Intent intent = new Intent(this, FootballClubOptionsList.class);
		intent.putExtra("football_club_id", keyId);
		startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mDbAdapter != null) {
			mDbAdapter.close();
			mDbAdapter.nullContext();
			mDbAdapter = null;
		}
	}

	
}


