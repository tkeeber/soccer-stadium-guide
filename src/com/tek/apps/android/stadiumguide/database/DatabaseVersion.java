package com.tek.apps.android.stadiumguide.database;

public interface DatabaseVersion {

	public static int DATABASE_VERSION = 2;

	public static final String CURRENT_DATABASE_VERSION_FILE = "dbversion";
	
	public static String DB_PATH = "/data/data/com.tek.apps.android.stadiumguide/databases/";

	public static String DB_NAME = "theawayteam.db";
}
