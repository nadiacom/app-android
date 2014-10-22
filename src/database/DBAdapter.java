package database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBAdapter {

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "reports";

	// User table name 
	private static final String TABLE_PRIMARY_DATA = "primary_data";

	

	DatabaseHelper DBHelper;
	Context context;
	SQLiteDatabase db;
	SharedPreferences prefs;
	private int DBMode;

	public DBAdapter(Context context){

		this.context = context;
		DBHelper = new DatabaseHelper(context); 
	}

	public class DatabaseHelper extends SQLiteOpenHelper {

		Context context;


		public DatabaseHelper(Context context) {

			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			this.context = context;
		}



		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(" PRAGMA foreign_keys = ON ");
			String CREATE_PRIMARY_DATA_TABLE = "CREATE TABLE " + TABLE_PRIMARY_DATA + "(id_intervention integer primary key autoincrement," + "date_intervention text not null,vps text not null,poste text not null, adress_intervention" + ");";
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Toast.makeText(context, "Mise à jour de la base de données de la version" + oldVersion + "vers la version" + newVersion, Toast.LENGTH_SHORT).show();
			db.execSQL("DROP TABLE IF EXISTS" + TABLE_PRIMARY_DATA);
			onCreate(db);
		}
	}

	public  DBAdapter open(){
		db = DBHelper.getWritableDatabase();
		return this;
	}

	public void close(){
		db.close();
	}

	public long insertPrimaryData(String date_intervention, String vps, String poste, String adress_intervention){
		ContentValues values = new ContentValues();
		values.put("date_intervention", date_intervention);
		values.put("vps", vps);
		values.put("poste", poste);
		values.put("adress_intervention", adress_intervention);
		return db.insert(TABLE_PRIMARY_DATA, null, values);

	}

	public ArrayList<ArrayList<String>> getPrimaryData(Context context){

		prefs = context.getSharedPreferences( "database", Context.MODE_PRIVATE);
		final int dbMode = prefs.getInt("DBMode", DBMode);
		if (dbMode == Constants.DBMode_LOCAL) {
			ArrayList<ArrayList<String>> PrimaryDataList = new ArrayList<ArrayList<String>>();
			String[] columns = {"id_intervention","date_intervention","vps","poste","adress_intervention"};
			Cursor dCursor = db.query(TABLE_PRIMARY_DATA,columns , null, null, null, null, null);
			if (dCursor != null ) {
				if  (dCursor.moveToFirst()) {
					do {
						ArrayList<String> PrimaryData = new ArrayList<String>();
						int Id_intervention = dCursor.getInt(dCursor.getColumnIndex("id_intervention"));
						PrimaryData.add(String.valueOf(Id_intervention));
						String Date_intervention = dCursor.getString(dCursor.getColumnIndex("date_intervention"));
						PrimaryData.add(Date_intervention);
						String Vps = dCursor.getString(dCursor.getColumnIndex("vps"));
						PrimaryData.add(Vps);
						String Poste = dCursor.getString(dCursor.getColumnIndex("poste"));
						PrimaryData.add(Poste);
						String Intervention_adress = dCursor.getString(dCursor.getColumnIndex("adress_intervention"));
						PrimaryData.add(Intervention_adress);
						PrimaryDataList.add(PrimaryData);
					} while (dCursor.moveToNext());
				}
			}
			dCursor.close();
			return PrimaryDataList;
		}else 
			return null;
	}

	
	public void deletePrimaryData(){

		db.delete(TABLE_PRIMARY_DATA, null, null);
	}

}