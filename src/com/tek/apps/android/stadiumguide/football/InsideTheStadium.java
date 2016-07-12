package com.tek.apps.android.stadiumguide.football;

import android.os.Bundle;

import com.tek.apps.android.stadiumguide.R;
import com.tek.apps.android.stadiumguide.database.AwayTeamDbAdapter;

public class InsideTheStadium extends AbstractActivity {

	private AwayTeamDbAdapter mDbAdapter; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.football_club_home);

		mDbAdapter.open();		
		
		Bundle b = this.getIntent().getExtras();		
		int stadiumId = b.getInt("stadium_id");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mDbAdapter.close();
		mDbAdapter = null;
	}
}
