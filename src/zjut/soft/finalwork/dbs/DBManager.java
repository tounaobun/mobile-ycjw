/**
  * @Author Benson
  * @Time 2013-11-27
  */
package zjut.soft.finalwork.dbs;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

	private DBHelper helper;
	private SQLiteDatabase db;
	
	public DBManager(Context context) {
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
	}
	
	public void addUsername(String username) {
		db.beginTransaction();
		Cursor c;
		try {
			c = db.rawQuery("select * from username_table", null);
			while(c.moveToNext()) {
				if(username.equals(c.getString(c.getColumnIndex("username")))) {
					return;
				} 
			}
			c.close();
			db.execSQL("INSERT INTO username_table VALUES(null, ?)", new Object[]{username});
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	
	public List<String> query() {
		List<String> usernames = new ArrayList<String>();
		Cursor c = db.rawQuery("select * from username_table", null);
		while(c.moveToNext()) {
			usernames.add(c.getString(c.getColumnIndex("username")));
		}
		c.close();
		return usernames;
	}
}
