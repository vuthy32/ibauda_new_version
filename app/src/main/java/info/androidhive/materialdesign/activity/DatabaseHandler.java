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
import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.JsonModel.ModelHomeFragment;
import info.androidhive.materialdesign.json_url.SchemaTable;
import info.androidhive.materialdesign.json_url.UrlJsonLink;

public class DatabaseHandler extends SQLiteOpenHelper {
         Context mContext;
			// All Static variables
			// Database Version
			private static final int DATABASE_VERSION = 1;
	        public String databasePath = "";
			// Database Name
			public DatabaseHandler(Context context) {
				super(context, Environment.getExternalStorageDirectory() + "/Android/data/"+context.getPackageName()+"/" + UrlJsonLink.DATABASE_NAME, null, DATABASE_VERSION);
				//SQLiteDatabase.openOrCreateDatabase("/sdcard/" + DATABASE_NAME,null);
				databasePath = context.getDatabasePath(UrlJsonLink.DATABASE_NAME).getPath();
			}
			// Creating Tables
			@Override
			public void onCreate(SQLiteDatabase db) {
			}
			public void Myqery(){

				String selectQuery = "select zc.*,zp.ZPHOTO_URL FROM ZCAR zc INNER JOIN ZPHOTOCAR  zp ON zc.ZCAR_ID=zp.ZCAR_ID GROUP BY zc.ZCAR_ID " ;
				SQLiteDatabase db = this.getWritableDatabase();
				Cursor cursor = db.rawQuery(selectQuery, null);
				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						Log.e("daaa",""+cursor.getString(6));
					} while (cursor.moveToNext());
				}
				db.close();
			}

	// Getting All Contacts
	public List<ModelHomeFragment> getAllCarData() {
		List<ModelHomeFragment> carData = new ArrayList<ModelHomeFragment>();
		// Select All Query

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(SchemaTable.SqlQueryDataListView, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
			//	Log.e("data",""+cursor.getColumnIndex("ZCITY"));
				ModelHomeFragment contact = new ModelHomeFragment();
				String ImageUrl = cursor.getString(cursor.getColumnIndex(SchemaTable.IMAGE_URL));
				contact.setImageUrl(ImageUrl);

				String CarNo = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_NO));
				contact.setCarNo(CarNo);

				String StringMake = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_MAKE));
				String StrngModel = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_MODEL));
				String StrngyYear = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_YEAR));

				String CarTitle = StringMake+" "+StrngModel+" "+StrngyYear;
				contact.setTitle(CarTitle);

				String IdexCar = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_INDEX));
				contact.setIdexID(IdexCar);

				String CarFOB = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_FOB));
				contact.setCarFob(CarFOB);

				String CarCooutry = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_COUTRY));
				String CarCity = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_CITY));
				contact.setCityCar(CarCooutry+" "+CarCity);

				contact.setStatusNew(cursor.getString(3));
				contact.setCarYear(cursor.getString(3));
				// Adding contact to list
				carData.add(contact);
			} while (cursor.moveToNext());
		}
		// return contact list
		return carData;
	}

			// Upgrading database
			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			}

}
