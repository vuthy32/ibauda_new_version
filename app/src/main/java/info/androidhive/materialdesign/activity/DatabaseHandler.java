package info.androidhive.materialdesign.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DatabaseHandler extends SQLiteOpenHelper {
         Context mContext;
			// All Static variables
			// Database Version
			private static final int DATABASE_VERSION = 1;
	        public String databasePath = "";
			// Database Name
			private static final String DATABASE_NAME = "downloadedfile.sqlite";


			public DatabaseHandler(Context context) {
				super(context, Environment.getExternalStorageDirectory() + "/" + DATABASE_NAME, null, DATABASE_VERSION);
				//SQLiteDatabase.openOrCreateDatabase("/sdcard/" + DATABASE_NAME,null);
				databasePath = context.getDatabasePath(DATABASE_NAME).getPath();
			}

			// Creating Tables
			@Override
			public void onCreate(SQLiteDatabase db) {
			}
			public void Myqery(){

				String selectQuery = "SELECT  * FROM ZCAR" ;
				SQLiteDatabase db = this.getWritableDatabase();
				Cursor cursor = db.rawQuery(selectQuery, null);
				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						Log.e("daaa",""+cursor.getString(0));
					} while (cursor.moveToNext());
				}

			}
			// Upgrading database
			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			}

}
