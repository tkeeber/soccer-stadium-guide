package com.tek.apps.android.stadiumguide.football;


import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tek.apps.android.stadiumguide.R;
import com.tek.apps.android.stadiumguide.database.AwayTeamDbAdapter;

public class Startup extends Activity{
	
	protected boolean _active = true;
	protected int _splashTime = 2000; // time to display the splash screen in ms
		
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.startup);
	
	    // thread for displaying the SplashScreen
	    Thread splashTread = new Thread() {
	        @Override
	        public void run() {	        	
	            try {
	            	//open the database - make sure its loaded before starting the app.
	            	try {
	            		AwayTeamDbAdapter dbAdatper = new AwayTeamDbAdapter(getApplicationContext());
	            		dbAdatper.createDatabaseIfNotExists();
//						DbUtils.createDatabaseIfNotExists(getApplicationContext());
					} catch (IOException e) {
						e.printStackTrace();
						Log.d("TAG", "Exception checking database: " + e.getMessage());
					}
	            	
	                int waited = 0;
	                while(_active && (waited < _splashTime)) {
	                    sleep(100);
	                    if(_active) {
	                        waited += 100;
	                    }
	                }
	            } catch(InterruptedException e) {
	                // do nothing
	            } finally {

	                finish();
	                startActivity(new Intent(getApplicationContext(),FootballClubList.class));
	                //stop();
	            }
	        }
	    };
	    splashTread.start();
	}

	
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();				
	}
	
	
}
