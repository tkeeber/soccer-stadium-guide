package com.tek.apps.android.stadiumguide.football;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.tek.apps.android.stadiumguide.R;
import com.tek.apps.android.stadiumguide.database.AwayTeamDbAdapter;

public class ClubTelephoneNumberList extends AbstractListActivity {

	private AwayTeamDbAdapter mDbAdapter;

	private int mFootballClubId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.football_telephone_numbers_list);
		
		mDbAdapter = new AwayTeamDbAdapter(this);
		mDbAdapter.open();
		
		Bundle b = this.getIntent().getExtras();		
		mFootballClubId = b.getInt("football_club_id");
		
		Cursor c =  mDbAdapter.fetchClubTelephoneNumbers(mFootballClubId);
		startManagingCursor(c);
		
		// Create an array to specify the fields we want to display in the list (only TITLE)
		String[] from = new String[]{AwayTeamDbAdapter.TelephoneNumbers.TELE_DESCRIPTION_COL, AwayTeamDbAdapter.TelephoneNumbers.TELE_NO_COL};

		// and an array of the fields we want to bind those fields to (in this case just text1)
		int[] to = new int[]{R.id.telephone_description, R.id.telephone_number};
		
		SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, R.layout.telephone_number_row, c, from, to);
		setListAdapter(listAdapter);
		
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

				TextView telephoneView = (TextView) view.findViewById(R.id.telephone_number);
				call(telephoneView.getText().toString());

			}					
		});
	}

	private void call(String telephoneNumber) {
		
		if (telephoneNumber == null) {
			Log.d("Call", "Telephone number is null!");
			return;
		}

		Intent i = new Intent();
		i.setAction(Intent.ACTION_CALL);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setData(Uri.parse("tel:"+ telephoneNumber));
		startActivity(i);
			
	}
	
	
}
