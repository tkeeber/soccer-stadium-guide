package com.tek.apps.android.stadiumguide.football;

import android.content.Context;

import com.tek.apps.android.stadiumguide.R;

public class FootballHelper {

	public static final int FILTER_ALL_CLUBS = 0;
	public static final int FILTER_PREMIERSHIP_CLUBS = 1;
	public static final int FILTER_CHAMPIONSHIP_CLUBS = 2;
	public static final int FILTER_LEAGUE_ONE_CLUBS = 3;
	public static final int FILTER_LEAGUE_TWO_CLUBS = 4;
	
	public static final String getLeague(int league, Context context) {
		
		String[] englishLeague = context.getResources().getStringArray(R.array.english_leagues);
		
		return englishLeague[league - 1];
	}
}
