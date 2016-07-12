package com.tek.apps.android.stadiumguide.football;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.tek.apps.android.stadiumguide.R;
import com.tek.apps.android.stadiumguide.database.AwayTeamDbAdapter;

public class StadiumHistoryActivity extends AbstractActivity {

	private AwayTeamDbAdapter mDbAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stadium_history);
		
		int stadiumId = getIntent().getIntExtra("stadium_id", -1);
		
		mDbAdapter = new AwayTeamDbAdapter(this);
		mDbAdapter.open();
		
		Cursor c = mDbAdapter.fetchStadiumHistory(stadiumId);
		startManagingCursor(c);
		
		TextView stadiumBuiltText = (TextView) findViewById(R.id.stadium_build_date_data);
		TextView firstGameText = (TextView) findViewById(R.id.stadium_first_game_data);
		TextView otherHistoryText = (TextView) findViewById(R.id.stadium_other_history_data);
		
		if (c.moveToFirst()) {
			String stadiumBuiltDate = c.getString(2);			
			stadiumBuiltText.setText(stadiumBuiltDate);
			
			String firstGameData = c.getString(3);
			firstGameText.setText(firstGameData);
			
			String stadiumHistory = c.getString(4);
			otherHistoryText.setText(stadiumHistory);
		}
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mDbAdapter.close();
		mDbAdapter = null;
	}
	
	

	
}
