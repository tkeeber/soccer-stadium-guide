package com.tek.apps.android.stadiumguide.football;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.tek.apps.android.stadiumguide.R;
import com.tek.apps.android.stadiumguide.database.AwayTeamDbAdapter;

public class GeneralInformationActivity extends AbstractActivity {

	private AwayTeamDbAdapter mDbAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.stadium_general_info);
		
		int stadiumId = getIntent().getIntExtra("stadium_id", -1);
		
		mDbAdapter = new AwayTeamDbAdapter(this);
		mDbAdapter.open();
		
		Cursor c = mDbAdapter.fetchStadiumInfo(stadiumId);
		startManagingCursor(c);
		
		if (c.moveToFirst()) {

			TextView generalDetailsText = (TextView) findViewById(R.id.general_info_data);
			if (c.getString(2) != null && c.getString(2).length() > 0) {
				generalDetailsText.setText(c.getString(2));
			}

			TextView seatingText = (TextView) findViewById(R.id.seating_data);
			if (c.getString(3) != null && c.getString(3).length() > 0) {
				seatingText.setText(c.getString(3));
			}

			TextView openingText = (TextView) findViewById(R.id.opening_data);
			if (c.getString(4) != null && c.getString(4).length() > 0) {
				openingText.setText(c.getString(4));
			}

			TextView stadiumRestrictionsText = (TextView) findViewById(R.id.restriction_data);
			if (c.getString(5) != null && c.getString(5).length() > 0) {
				stadiumRestrictionsText.setText(c.getString(5));
			}

			if (c.getString(6) != null && c.getString(6).length() > 0) {
				TextView babiesAndChildrenText = (TextView) findViewById(R.id.babies_and_children_data);
				babiesAndChildrenText.setText(c.getString(6));
			}
			
			if (c.getString(7) != null && c.getString(7).length() > 0) {
				TextView lostProperty = (TextView) findViewById(R.id.cash_machine_data);
				lostProperty.setText(c.getString(7));
			}
			
			if (c.getString(8) != null && c.getString(8).length() > 0) {
				TextView lostProperty = (TextView) findViewById(R.id.lost_property_data);
				lostProperty.setText(c.getString(8));
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mDbAdapter.close();
		mDbAdapter = null;
	}

	
	
}
