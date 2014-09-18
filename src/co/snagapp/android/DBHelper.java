package co.snagapp.android;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper extends SQLiteAssetHelper {

	private static final String DATABASE_NAME = "snag.db";
	private static final String TABLE_WORDS = "words";
	private static final String TABLE_LIKELY_SPAM = "likely_spam";
	private static final int DATABASE_VERSION = 1;
	
  
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	/*
	 * Messages
	 * 
	 */
	public Cursor getLikelySpamMessages(){
	    String selectQuery = "SELECT "+Constants.KEY_ID+" as _id, "+Constants.KEY_FROM+", "+Constants.KEY_BODY+", "
	    		+ ""+Constants.KEY_DATE+" FROM "+TABLE_LIKELY_SPAM+" order by date desc";	    
	    SQLiteDatabase db = this.getWritableDatabase();
	    return db.rawQuery(selectQuery, null);
	}
	
	public void addSpamSMS(String from, String sms){
		
		Log.e("DEBUG", "Add spam sms: "+from+" "+sms);
	    SQLiteDatabase db = this.getWritableDatabase();
	    
	    ContentValues values = new ContentValues();
	    values.put(Constants.KEY_FROM, from);
	    values.put(Constants.KEY_BODY, sms);
	 
	    db.insert(TABLE_LIKELY_SPAM, null, values);
	    db.close();
	}
	
	/**
	 * 
	 * Counts
	 * 
	 */

	// This word in category
	public int thisWordCount(String word, int category) {
		int count = 0;
        String countQuery = "select "+Constants.CATEGORIES[category]+" from "+TABLE_WORDS+" where word='"+word+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
        }
        cursor.close();
 
        return count;
	}

	// All words in category
	public int allWordCount(int category) {
		int count = 0;
        String countQuery = "select sum("+Constants.CATEGORIES[category]+") from "+TABLE_WORDS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
        }
        cursor.close();
 
        return count;
	}

	// This word in  both categories
	public int wordCount(String word) {
		int count = 0;
        String countQuery = "select "+Constants.CATEGORIES[0]+" + "+Constants.CATEGORIES[1]+""
        		+ " from "+TABLE_WORDS+" where word='"+word+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
        }
        cursor.close();
 
        return count;
	}

}
