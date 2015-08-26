package all_action.iblaudas.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
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

import all_action.iblaudas.JsonModel.ModelCarGet;
import all_action.iblaudas.JsonModel.ModelHomeFragment;
import all_action.iblaudas.json_url.SchemaFieldTable;
import all_action.iblaudas.json_url.SchemaTable;
import all_action.iblaudas.json_url.UrlJsonLink;

public class DatabaseHandler extends SQLiteOpenHelper {
         Context mContext;
			// All Static variables
			// Database Version
			private static final int DATABASE_VERSION = 1;
	        public String databasePath = "";
			// Database Name
			public DatabaseHandler(Context context) {
				super(context, Environment.getExternalStorageDirectory() + "/Android/data/"+context.getPackageName()+"/" + UrlJsonLink.DATABASE_NAME, null, DATABASE_VERSION);
				//SQLiteDatabase.openOrCreateDatabase("/sdcard/Android/data/" + context.getPackageName()+"/" + UrlJsonLink.DATABASE_NAME, null);
				databasePath = context.getDatabasePath(UrlJsonLink.DATABASE_NAME).getPath();
			}
			// Creating Tables
			@Override
			public void onCreate(SQLiteDatabase db) {
			}
			public void Myqery(){

				String selectQuery = "SELECT *  FROM TBL_CAR" ;
				SQLiteDatabase db = this.getWritableDatabase();
				Cursor cursor = db.rawQuery(selectQuery, null);
				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						//Log.e("daaa",""+cursor.getString(6));
					} while (cursor.moveToNext());
				}
				db.close();
			}

	// Getting All Contacts
	public List<ModelHomeFragment> getAllCarData() {
		List<ModelHomeFragment> carData = new ArrayList<ModelHomeFragment>();
		// Select All Query
		SQLiteDatabase db = this.getWritableDatabase();
		//Cursor cursor=db.query("TBL_CAR", new String[] {"*"},"FROM TBL_CAR  WHERE CAR_ID = "+CarID,null, null, null, null,null);
		Cursor cursor = db.rawQuery(SchemaTable.SqlQueryDataListView, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ModelHomeFragment contact = new ModelHomeFragment();

				String IdexCar = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_INDEX));
				contact.setIdexID(IdexCar);

				String ImageUrl = cursor.getString(cursor.getColumnIndex(SchemaTable.IMAGE_URL_THUMB));
				contact.setImageUrl(ImageUrl);

				String CarNo = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_NO));
				contact.setCarNo(CarNo);

				String StringMake = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_MAKE));
				String StrngModel = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_MODEL));
				String StrngyYear = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_YEAR));
				String CarTitle = StringMake+" "+StrngModel+" "+StrngyYear;
				contact.setTitle(CarTitle);

				String CarFOB = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_FOB));
				contact.setCarFob(CarFOB);

				String CarFOBCurrency = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_FOB_CURRENCY));
				contact.setCarFobCurrency(CarFOBCurrency);

				String CarCooutry = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_COUTRY));
				String CarCity = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_CITY));
				contact.setCityCar(CarCooutry+" "+CarCity);

				String StatusNew = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_CREATE_STATUS));
				contact.setStatusNew(StatusNew);

				String StatusReverv = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_STATUS));
				contact.setStatusReserved(StatusReverv);
				// Adding contact to list
				carData.add(contact);
			} while (cursor.moveToNext());
		}
		db.close();
		return carData;
	}
	String TAG="Search data";


	public List<ModelHomeFragment> get_tag_Data(String KeyWords)
	{
		List<ModelHomeFragment> carDataSerach = new ArrayList<ModelHomeFragment>();
		SQLiteDatabase mDb = this.getWritableDatabase();
		String DATA_SEARCH_CAR ="SELECT * from TBL_CAR WHERE  CAR_STATUS!='releaseok' AND "+
				SchemaTable.CAR_MODEL + " LIKE '%"+KeyWords+"%' OR " +
				SchemaTable.CAR_CITY + " LIKE '%"+KeyWords+"%' OR " +
				SchemaTable.CAR_COUTRY + " LIKE '%"+KeyWords+"%' OR " +
				SchemaTable.CAR_FOB + " LIKE '%"+KeyWords+"%' OR " +
				SchemaTable.CAR_NO + " LIKE '%"+KeyWords+"%'" +
				" OR CAR_MAKE LIKE '%"+KeyWords+"%'" +
				" ORDER BY  CASE CAR_STATUS  WHEN  'sale' THEN 1 WHEN 'reserved' THEN 2  WHEN 'order' THEN 3 WHEN 'soldout' THEN 4 WHEN 'releaseeok' THEN 5 END";
		try

		{
			Cursor cursor = mDb.rawQuery(DATA_SEARCH_CAR, null);
			if (cursor!=null)
			{
				if (cursor.moveToFirst()) {
					do {
						ModelHomeFragment contact = new ModelHomeFragment();

						String IdexCar = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_INDEX));
						contact.setIdexID(IdexCar);

						String ImageUrl = cursor.getString(cursor.getColumnIndex(SchemaTable.IMAGE_URL_THUMB));
						contact.setImageUrl(ImageUrl);

						String CarNo = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_NO));
						contact.setCarNo(CarNo);

						String StringMake = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_MAKE));
						String StrngModel = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_MODEL));
						String StrngyYear = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_YEAR));
						String CarTitle = StringMake+" "+StrngModel+" "+StrngyYear;
						contact.setTitle(CarTitle);

						String CarFOB = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_FOB));
						contact.setCarFob(CarFOB);

						String CarFOBCurrency = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_FOB_CURRENCY));
						contact.setCarFobCurrency(CarFOBCurrency);

						String CarCooutry = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_COUTRY));
						String CarCity = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_CITY));
						contact.setCityCar(CarCooutry+" "+CarCity);

						String StatusNew = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_CREATE_STATUS));
						contact.setStatusNew(StatusNew);

						String StatusReverv = cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_STATUS));
						contact.setStatusReserved(StatusReverv);
						// Adding contact to list
						carDataSerach.add(contact);
						Log.e(TAG, "getTestData >>" + cursor.getString(cursor.getColumnIndex(SchemaTable.CAR_STATUS)));
					} while (cursor.moveToNext());
				}

			}
			mDb.close();
			return carDataSerach;

		}
		catch (SQLException mSQLException)
		{
			Log.e(TAG, "getTestData >>"+ mSQLException.toString());
			throw mSQLException;
		}

	}
//*************************car Gallery **************************8
public List<ModelHomeFragment> getGalleryPhoto(String CarID)
{
	List<ModelHomeFragment> carDataPhoto = new ArrayList<ModelHomeFragment>();
	SQLiteDatabase mDb = this.getWritableDatabase();
	String DATA_PHOTO_CAR ="SELECT * FROM "+SchemaFieldTable.TABLE_CAR_PHOTO+ " WHERE "+ SchemaFieldTable.CAR_ID +"="+CarID;
	try

	{
		Cursor cursor = mDb.rawQuery(DATA_PHOTO_CAR, null);
		if (cursor!=null)
		{
			if (cursor.moveToFirst()) {
				do {
					ModelHomeFragment contact = new ModelHomeFragment();
					String ThumbUrl = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.PHOTO_THUMB_URL));

					contact.setImageUrl(ThumbUrl);
					String PhotoURL = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.PHOTO_URL));
					contact.setPhotoUrl(PhotoURL);
					String PhotoSort = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.SORT_PHOTO));
					contact.setSortPhoto(PhotoSort);
						Log.e("cursor", "" + ThumbUrl);
					carDataPhoto.add(contact);

				} while (cursor.moveToNext());
			}

		}
		mDb.close();
		return carDataPhoto;
	}
	catch (SQLException mSQLException)
	{
		Log.e(TAG, "getTestData >>"+ mSQLException.toString());
		throw mSQLException;
	}
}


	//*********get data detail *************************
	public List<ModelCarGet> getAllCarDataDatil(String CarIDStr) {
		List<ModelCarGet> carDataDetail = new ArrayList<ModelCarGet>();
		// Select All Query
		SQLiteDatabase db = this.getWritableDatabase();
		//Cursor cursor=db.query("TBL_CAR", new String[] {"*"},"FROM TBL_CAR  WHERE CAR_ID = "+CarID,null, null, null, null,null);
		Cursor cursor = db.rawQuery(SchemaTable.SqlQueryDataDetail+CarIDStr, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ModelCarGet contact = new ModelCarGet();

				String IdexCar = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.CAR_ID));
				contact.setCarID(IdexCar);

				String carMaks = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.CAR_MAKE));

				String carmodel = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.CAR_MODEL));
				String carYears = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.CAR_YEAR));
				String carStatYears = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.CAR_YEAR_START));
				String carTransmissionss = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.CAR_TRANSMISSION));
				String carCountryzz = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.CAR_COUNTRY));
				String carCityssd = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.CAR_CITY));
				String carStockNosd = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.CAR_STOCK_NO));
				String carChassisNo = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.CAR_CHASSIS_NO));
				Log.e("carStockNosd",""+carStockNosd);
				String carStatussd = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.CAR_STATUS));
				String carGrsdade = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.CAR_GRADE));
				//String carCC = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.CAR_CC));

				String firstRegisterMonthsd = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.FRIST_REGISTER_MONTH));
				String carMileagesd = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.CAR_MILEAGE));
				String carFuelsd = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.CAR_FUEL));
				String carColorsd = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.CAR_COLOR));

				String carSeatsd = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.CAR_SEAT));
				String carBodyTypesd = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.CAR_BODY_TYPE));
				String carDriverTypesd = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.CAR_DRIVER_TYPE));
				String carFobCostsd = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.CAR_FOB_COST));
				String carFobCursdrent = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.CAR_FOB_CURRENT));
				String carCreateStatussd = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.CAR_CREATE_STATUS));
				String carCreateDatesd = cursor.getString(cursor.getColumnIndex(SchemaFieldTable.CAR_CREATE_DATE));
				//Log.e("carCC",""+carCC);

				contact.setCarMake(carMaks);
				contact.setCarModel(carmodel);
				contact.setCarYear(carYears);
				contact.setCarStartYear(carStatYears);
				contact.setCarTransmission(carTransmissionss);


				contact.setCarCountry(carCountryzz);
				contact.setCarCity(carCityssd);
				contact.setCarStockNo(carStockNosd);
				contact.setCarChassNo(carChassisNo);
				contact.setCarStatus(carStatussd);
				contact.setCarGrade(carGrsdade);
				//contact.setCarCCStr(carCC);

				contact.setFirstRegisterMonth(firstRegisterMonthsd);
				contact.setCarMileage(carMileagesd);
				contact.setCarFuel(carFuelsd);
				contact.setCarColor(carColorsd);
				contact.setCarSeat(carSeatsd);
				contact.setCarBodyType(carBodyTypesd);

				contact.setCarDriverType(carDriverTypesd);
				contact.setCarFobCost(carFobCostsd);
				contact.setCarFobCurrent(carFobCursdrent);
				contact.setCarCreateStatus(carCreateStatussd);
				contact.setCarCreateDate(carCreateDatesd);
				// Adding contact to list
				carDataDetail.add(contact);
			} while (cursor.moveToNext());
		}
		db.close();
		return carDataDetail;
	}




			// Upgrading database
			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			}

}
