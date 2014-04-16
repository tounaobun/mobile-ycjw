/**
  * @Author Benson
  * @Time 2013-11-27
  */
package zjut.soft.finalwork.dbs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "mobile_yc.db";  
    private static final int DATABASE_VERSION = 1;  
    
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		 db.execSQL("CREATE TABLE IF NOT EXISTS username_table" +  
	                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR)"); 
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
