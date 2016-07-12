package com.tek.apps.android.stadiumguide.football;

import com.tek.apps.android.stadiumguide.R;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;

public class AbstractListActivity extends ListActivity{

	private static final int ABOUT_ID = 0;
//	private static final int MY_TEAM = 1;
//	private static final int UPGRADE = 2;
//	private static final int PERFERENCES = 3;
	private static final int CONTACT_DEVELOPER = 4;
	private static final int DEVELOP_WEBSITE = 5;
	
	
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, ABOUT_ID, 0, R.string.about);
		menu.add(0, CONTACT_DEVELOPER, 0, R.string.contact_developer);
		menu.add(0, DEVELOP_WEBSITE, 0, R.string.developer_website);
		return result;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId())
		{
		case ABOUT_ID:
			startAboutAcitivity();
			return true;
		case CONTACT_DEVELOPER:
			startEmailDeveloper();
			return true;
		case DEVELOP_WEBSITE:
			startOpenDeveloperWebsite();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void startAboutAcitivity() {
		Intent i = new Intent(this, AboutActivity.class);
		startActivity(i);
	}
	
	public void startEmailDeveloper() {

		/* Create the Intent */  
		final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);  

		/* Fill it with Data */  
		emailIntent.setType("plain/text");  
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.developer_email)});  
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");  
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
}
