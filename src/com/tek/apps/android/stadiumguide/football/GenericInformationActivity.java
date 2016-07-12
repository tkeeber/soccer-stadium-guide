package com.tek.apps.android.stadiumguide.football;

import android.os.Bundle;
import android.widget.TextView;

import com.tek.apps.android.stadiumguide.R;

public class GenericInformationActivity extends AbstractActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.generic_information_screen);
		
		Bundle bundle =  getIntent().getExtras();
		
		TextView textInfoTitle = (TextView) findViewById(R.id.text_info_title);
		textInfoTitle.setText(bundle.getString("title"));
		
		TextView textInfoData = (TextView) findViewById(R.id.text_info_data);
		textInfoData.setText(bundle.getString("data"));
	}
}
