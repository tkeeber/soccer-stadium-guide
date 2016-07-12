package com.tek.apps.android.stadiumguide.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
import android.util.Log;

public class AwayTeamDbAdapter extends SQLiteOpenHelper implements DatabaseVersion{

	private Context mCtx;

	private SQLiteDatabase mDb;

	private SharedPreferences mSharedPreferences;
	
	private static final String PREFERENCE_DATABASE_VERSION_KEY = "database_version";
	
	public static final class FootballClubs implements BaseColumns {
		// This class cannot be instantiated
		private FootballClubs() {
		}
		
		public static final String TABLE_FOOTBALL_CLUBS = "football_clubs";		
		public static final String FOOTBALL_CLUB_NAME_COL = "football_club_name";
		public static final String FOOTBALL_CLUB_DIVISION_COL = "football_club_division";
		public static final String FOOTBALL_CLUB_STADIUM_ID_COL = "stadium_pk";
		public static final String FOOTBALL_CLUB_WEBSITE = "official_club_website";
		public static final String FOOTBALL_CLUB_EMAIL_ADDRESS = "club_email_address";

	}

	public static final class TelephoneNumbers implements BaseColumns {
		private TelephoneNumbers() {
		}
		private static final String TABLE_FOOTBALL_TELEPHONE = "football_telephone_no";
		public static final String TELE_NO_FOOTBALL_CLUB_COL = "tele_football_id";
		public static final String TELE_NO_COL = "tele_no";
		public static final String TELE_DESCRIPTION_COL = "tele_description";
	}
	
	public static final class Stadiums implements BaseColumns {
		private Stadiums() {
		}
		private static final String TABLE_FOOTBALL_CLUB_STADIUM_INFO = "football_club_stadiums";

		public static final String STADIUM_NAME_COL = "stadium_name";
		public static final String STADIUM_CAPACITY_COL = "stadium_capacity";
		public static final String STADIUM_ADDRESS_COL = "stadium_address";	
		public static final String STADIUM_LATITUDE = "stadium_latitude";		
		public static final String STADIUM_LONGITUDE = "stadium_longitude";
	}
	
	/**
	 * CREATE TABLE stadium_info (_id integer primary key autoincrement, stadium_id number, 
	 * general_info text, seating_text text, opening_times text, cash_machines text, 
	 * stadium_restrictions text, babies_children text, lost_property text, );
	 * @author tom
	 *
	 */
	public static final class StadiumInfo implements BaseColumns {
		private StadiumInfo() {}
		
		public static final String TABLE_STADIUM_INFO = "stadium_info";
		
		public static final String STADIUM_INFO_STADIUM_ID_COL = "stadium_id";
		public static final String STADIUM_INFO_GENERAL_TEXT_COL = "general_info";
		public static final String STADIUM_INFO_SEATING_TEXT_COL = "seating_text";
		public static final String STADIUM_INFO_OPENING_TIMES_COL = "opening_times";
		public static final String STADIUM_INFO_CASH_MACHINES_COL = "cash_machines";
		public static final String STADIUM_INFO_RESTRICTIONS_COL = "stadium_restrictions";
		public static final String STADIUM_INFO_BABIES_CHILDREN_COL = "babies_children";
		public static final String STADIUM_INFO_LOST_PROPERTY_COL = "lost_property";		
	}
	
	
	public static final class StadiumHistory implements BaseColumns {
		public static final String TABLE_STADIUM_HISTORY = "stadium_history";
		
		public static final String HISTORY_STADIUM_ID_COL = "stadium_id";
		public static final String HISTORY_STADIUM_BUILT_DATE = "build_date";
		public static final String HISTORY_STADIUM_FIRST_GAME_DATE = "first_game";
		public static final String HISTORY_STADIUM_OTHER_INFO = "other_info";
		
	}

	public static final class CommentsAndRatingsCache implements BaseColumns {
			private CommentsAndRatingsCache() {}
		
			public static final String COMMENTS_TEXT_COL = "comment_text";
			public static final String OVERALL_RATING_COL = "overall_rating";
			public static final String FOOD_RATING_COL = "food_rating";
			public static final String ACCESSABILITY_RATING_COL = "accessability";
			public static final String USERNAME_COL = "username";
			public static final String PARKING_COL = "";
	}
	
	public static final class StadiumDirections implements BaseColumns {
		private StadiumDirections() {}
	
		public static final String TABLE_STADIUM_DIRECTIONS = "stadium_directions";
		public static final String STADIUM_DIRECTIONS_STADIUM_ID = "stadium_id";
		public static final String STADIUM_DIRECTIONS_BUS_INFO = "bus_info";
		public static final String STADIUM_DIRECTIONS_TRAIN_INFO = "train_info";
	}
	
	/**
	 * CREATE TABLE stadium_parking (_id integer primary key autoincrement, stadium_id number, parking_info text);	
	 * @	
	private Context mCtx;
	

	private SQLiteDatabase mDb;author Tom
	 *
	 */
	public static final class StadiumParking implements BaseColumns {
		private StadiumParking(){}
		
		public static final String TABLE_STADIUM_PARKING = "stadium_parking";
		
		public static final String STADIUM_PARKING_STADIUM_ID = "stadium_id";
		public static final String STADIUM_PARKING_INFO_TEXT = "parking_info";
	}
	
	public static final class FoodAndDrinkVenues implements BaseColumns {
		private FoodAndDrinkVenues() {}
		
		public static final String TABLE_FOOD_VENUE = "food_and_drink_venues";
		public static final String VENUE_STADIUM_ID = "stadium_id";
		public static final String VENUE_NAME_COL = "venue_name";
		public static final String VENUE_LATITUDE_COL = "venue_latitude";
		public static final String VENUE_LONGITUDE_COL = "venue_longitude";
		public static final String VENUE_DESCRIPTION_COL = "venue_decription";
		public static final String VENUE_DISTANCE_FROM_STADIUM = "distance_from_stadium";
		public static final String VENUE_IS_DRINK_VENUE_COL = "is_drink_venue";
		public static final String VENUE_IS_FOOD_VENUE_COL = "is_food_venue";
		
	}
	

    /**doUpgrade
     * Sets the context as null.
     */
    public void nullContext() {
    	this.mCtx = null;
    }
   
	/**
	 * Constructor
	 * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
	 * @param context
	 */
	public AwayTeamDbAdapter(Context context) {

		super(context, DB_NAME, null, 1);
		this.mCtx = context;
		
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	}	

	

	public void open() throws SQLException{

		//Open the database
		String myPath = DB_PATH + DB_NAME;
		mDb = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

	}

	@Override
	public synchronized void close() {

		super.close();
		
		if(mDb != null) {
			mDb.close();
		}

		mCtx = null;

	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

    
    public Cursor fetchFootballTeam(long id)
    {
    	return mDb.query(FootballClubs.TABLE_FOOTBALL_CLUBS, new String[] {FootballClubs._ID, FootballClubs.FOOTBALL_CLUB_NAME_COL,FootballClubs.FOOTBALL_CLUB_DIVISION_COL, FootballClubs.FOOTBALL_CLUB_STADIUM_ID_COL, FootballClubs.FOOTBALL_CLUB_WEBSITE, FootballClubs.FOOTBALL_CLUB_EMAIL_ADDRESS}, 
    			FootballClubs._ID + "=" + id, null, null, null, null);	
    }
    
    public Cursor fetchStadiumDetails(long id)
    {
    	return mDb.query(Stadiums.TABLE_FOOTBALL_CLUB_STADIUM_INFO, 
    					 new String[] {Stadiums._ID, Stadiums.STADIUM_NAME_COL, Stadiums.STADIUM_CAPACITY_COL, Stadiums.STADIUM_ADDRESS_COL, Stadiums.STADIUM_LATITUDE, Stadiums.STADIUM_LONGITUDE}, 
    					 Stadiums._ID + "=" + id, null, null, null, null);	
    }
    
	public Cursor fetchAllClubs() {
		   return mDb.query(FootballClubs.TABLE_FOOTBALL_CLUBS, new String[] {FootballClubs._ID, FootballClubs.FOOTBALL_CLUB_NAME_COL}, 
	        		null, null, null, null, null);
	}
    
	public Cursor fetchClubsByDivision(int clubFilter) {
		   return mDb.query(FootballClubs.TABLE_FOOTBALL_CLUBS, new String[] {FootballClubs._ID, FootballClubs.FOOTBALL_CLUB_NAME_COL}, 
				   FootballClubs.FOOTBALL_CLUB_DIVISION_COL + " = " + clubFilter, null, null, null, null);
	}

	public String fetchDirections(int stadiumId, String trainOrBus) {
		   Cursor c = mDb.query(StadiumDirections.TABLE_STADIUM_DIRECTIONS, new String[] {StadiumDirections._ID, trainOrBus}, 
				   StadiumDirections.STADIUM_DIRECTIONS_STADIUM_ID + " = " + stadiumId, null, null, null, null);
		   
		   
		   try {
			   if (c.moveToFirst()) {
				   c.moveToFirst();
				   return c.getString(1);
			   }
		   } finally {
			   c.close();
			   c = null;
		   }
		   
		   return "";
	}
	

	public String fetchParking(int stadiumId) {
		 Cursor c = mDb.query(StadiumParking.TABLE_STADIUM_PARKING, new String[] {StadiumDirections._ID, StadiumParking.STADIUM_PARKING_INFO_TEXT}, 
				 StadiumParking.STADIUM_PARKING_STADIUM_ID + " = " + stadiumId, null, null, null, null);
		   
		   try {
			   if (c.moveToFirst()) {
				   c.moveToFirst();
				   return c.getString(1);
			   }
		   } finally {
			   c.close();
			   c = null;
		   }
		   
		   return "";
	}
	public Cursor fetchStadiumInfo(int stadiumId) {
		return mDb.query(StadiumInfo.TABLE_STADIUM_INFO, new String[] {StadiumInfo._ID, StadiumInfo.STADIUM_INFO_STADIUM_ID_COL, StadiumInfo.STADIUM_INFO_GENERAL_TEXT_COL, StadiumInfo.STADIUM_INFO_SEATING_TEXT_COL, StadiumInfo.STADIUM_INFO_OPENING_TIMES_COL, StadiumInfo.STADIUM_INFO_RESTRICTIONS_COL, StadiumInfo.STADIUM_INFO_BABIES_CHILDREN_COL, StadiumInfo.STADIUM_INFO_CASH_MACHINES_COL, StadiumInfo.STADIUM_INFO_LOST_PROPERTY_COL}, 
				StadiumInfo.STADIUM_INFO_STADIUM_ID_COL + " = " + stadiumId, null, null, null, null);
	
	}
	
	public Cursor fetchClubTelephoneNumbers(int footballClubId) {
		  return mDb.query(TelephoneNumbers.TABLE_FOOTBALL_TELEPHONE, 
				  new String[] {TelephoneNumbers._ID, TelephoneNumbers.TELE_NO_FOOTBALL_CLUB_COL, TelephoneNumbers.TELE_NO_COL, TelephoneNumbers.TELE_DESCRIPTION_COL}, 
				  TelephoneNumbers.TELE_NO_FOOTBALL_CLUB_COL  + "=" + footballClubId, null, null, null, null);
	}
	
	public Cursor fetchFoodAndDrinkVenues(int stadiumId) {
		  return mDb.query(FoodAndDrinkVenues.TABLE_FOOD_VENUE, 
				  new String[] {FoodAndDrinkVenues._ID, FoodAndDrinkVenues.VENUE_STADIUM_ID, FoodAndDrinkVenues.VENUE_NAME_COL, FoodAndDrinkVenues.VENUE_DESCRIPTION_COL, FoodAndDrinkVenues.VENUE_DISTANCE_FROM_STADIUM}, 
				  FoodAndDrinkVenues.VENUE_STADIUM_ID  + "=" + stadiumId, null, null, null, null);
	}
	
	  
	public Cursor fetchStadiumHistory(int stadiumId) {
		  return mDb.query(StadiumHistory.TABLE_STADIUM_HISTORY, 
				  new String[] {StadiumHistory._ID, StadiumHistory.HISTORY_STADIUM_ID_COL, StadiumHistory.HISTORY_STADIUM_BUILT_DATE, StadiumHistory.HISTORY_STADIUM_FIRST_GAME_DATE, StadiumHistory.HISTORY_STADIUM_OTHER_INFO}, 
				  FoodAndDrinkVenues.VENUE_STADIUM_ID  + "=" + stadiumId, null, null, null, null);
	}
	
	
	public Cursor fetchFoodAndDrinkVenuesForMap(int[] venueIds) {
		
		StringBuffer strVenueIds = new StringBuffer("(");
		boolean isAfterFirst = false;
		
		for (int i = 0; i < venueIds.length; i++) {
			if (isAfterFirst) {
				strVenueIds.append(",");
			}
			strVenueIds.append("" + venueIds[i]);
			isAfterFirst = true;
		}
		strVenueIds.append(")");
		
		Log.d("AwayTeam", strVenueIds.toString());
		
		  return mDb.query(FoodAndDrinkVenues.TABLE_FOOD_VENUE, 
				  new String[] {FoodAndDrinkVenues._ID, FoodAndDrinkVenues.VENUE_STADIUM_ID, FoodAndDrinkVenues.VENUE_NAME_COL, FoodAndDrinkVenues.VENUE_LATITUDE_COL, FoodAndDrinkVenues.VENUE_LONGITUDE_COL}, 
				  FoodAndDrinkVenues._ID  + " in " + strVenueIds.toString(), null, null, null, null);
	}
	
	public void deleteFoodAndDrinkVenue(int stadiumId) {
		mDb.delete(FoodAndDrinkVenues.TABLE_FOOD_VENUE, FoodAndDrinkVenues.VENUE_STADIUM_ID + "=" + stadiumId , null);
	}
	
	
	/**
	 * Check if the database already exist to avoid
	 *  re-copying the file each time you open the application.
	 * @return true if it exists, false if it doesn't
	 */
	private boolean doesDataBaseExist(){

		SQLiteDatabase checkDB = null;

		try{
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

		}catch(SQLiteException e){
			//database does't exist yet.
		}
		if(checkDB != null){
			checkDB.close();
		}
		
		return checkDB != null ? true : false;
	}
	
	public void createDatabaseIfNotExists() throws IOException {
        boolean createDb = false;

        File dbFile = new File(DB_PATH + DB_NAME);
        if (doesDataBaseExist() == false) {                        
        	createDb = true;
        }
        else if (!dbFile.exists()) {
            createDb = true;
        }
        else {
            // Check that we have the latest version of the db
            boolean doUpgrade = checkForUpgrade();
            
            // If we are doing an upgrade, basically we just delete the db then
            // flip the switch to create a new one
            if (doUpgrade) {
                dbFile.delete();
                createDb = true;
            }
        }

        if (createDb) {

        	//By calling this method and empty database will be created into the default system path
        	//of your application so we are gonna be able to overwrite that database with our database.
        	getReadableDatabase();

        	// Open your local db as the input stream
        	InputStream myInput = mCtx.getAssets().open(DB_NAME);
        	// Open the empty db as the output stream
        	OutputStream myOutput = new FileOutputStream(dbFile);
        	try {

        		// transfer bytes from the inputfile to the outputfile
        		byte[] buffer = new byte[1024];
        		int length;
        		while ((length = myInput.read(buffer)) > 0) {
        			myOutput.write(buffer, 0, length);
        		}
        	} finally {
        		// Close the streams
        		myOutput.flush();
        		myOutput.close();
        		myInput.close();
        	}
        	        	
        	//update the version file with the new version
        	updateVersion();
        	
        }
        
    	this.close();

    }

	private void updateVersion() {
		Editor editor = mSharedPreferences.edit();
		editor.putInt(PREFERENCE_DATABASE_VERSION_KEY, DATABASE_VERSION);
		editor.commit();
	}
	
	private boolean checkForUpgrade() throws IOException  {
		boolean doUpgrade = false;
		// Insert your own logic here on whether to upgrade the db; I personally
        // just store the db version # in a text file, but you can do whatever
        // you want.  I've tried MD5 hashing the db before, but that takes a while.
		
		int databaseVersion = mSharedPreferences.getInt(PREFERENCE_DATABASE_VERSION_KEY, 1);
		
	    doUpgrade = DATABASE_VERSION > databaseVersion; 
	
		Log.d("TAG", "Doupgrade is " + doUpgrade);
                
        return doUpgrade;
	}
}
