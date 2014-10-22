package database;

import android.content.Context;

public class DBInitialization {


	void InitializeDB(Context context){

		DBAdapter dba = new DBAdapter(context);
		dba.open();
		// DELETE DATA FROM DATABASE 
		dba.deletePrimaryData();
		
	}

}
