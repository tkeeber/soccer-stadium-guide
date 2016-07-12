package com.tek.apps.android.stadiumguide.football;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.tek.apps.android.stadiumguide.R;

public class AboutActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		Button closeButton = (Button) findViewById(R.id.closeButton);
		closeButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				finishAbout();
			}
		});
	}

	private void finishAbout() {
		this.finish();
	}
	
}
