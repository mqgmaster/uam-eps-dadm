package es.uam.eps.dadm.mqg.minesweeper.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAdapter {

	private static final String DATABASE_NAME = "minesweeper.db";
	private static final int DATABASE_VERSION = 2;
	
	private DatabaseHelper helper;
	private SQLiteDatabase db;
	
	public DatabaseAdapter(Context context) {
		helper = new DatabaseHelper(context);
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper { 

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		public void onCreate(SQLiteDatabase db) {
			createTable(db);
		}
	
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + Match.TABLE_NAME);
			createTable(db);
		}
	
		private void createTable(SQLiteDatabase db) {
			String str1 = "CREATE TABLE " + Match.TABLE_NAME + " ("
					+ Match.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ Match.DATE + " TEXT, "
					+ Match.PLAYER1_NAME + " TEXT, "
					+ Match.PLAYER1_TIME_SPENT + " INTEGER,"
					+ Match.PLAYER1_SCORE + " INTEGER,"
					+ Match.PLAYER2_NAME + " TEXT,"
					+ Match.PLAYER2_TIME_SPENT + " INTEGER,"
					+ Match.PLAYER2_SCORE + " INTEGER);";
			try {
				db.execSQL(str1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
			
	public DatabaseAdapter open() throws SQLException {
		db = helper.getWritableDatabase();
		return this;
	}
		
	public void close() {
		db.close();
	}

	public long insertMatch(Match match) {
		ContentValues values = new ContentValues();
		values.put(Match.DATE, match.getDate());
		values.put(Match.PLAYER1_NAME, match.getPlayer1_name());
		values.put(Match.PLAYER1_SCORE, match.getPlayer1_score());
		values.put(Match.PLAYER1_TIME_SPENT, match.getPlayer1_time_spent());
		values.put(Match.PLAYER2_NAME, match.getPlayer2_name());
		values.put(Match.PLAYER2_SCORE, match.getPlayer2_score());
		values.put(Match.PLAYER2_TIME_SPENT, match.getPlayer2_time_spent());
		return db.insert(Match.TABLE_NAME, null, values);
	}

	public boolean deleteMatch(long id) {
		return db.delete(Match.TABLE_NAME, Match.ID + "=" + id, null) > 0;
	}

	public Cursor getAll() {
		return db.query(Match.TABLE_NAME, new String[] { Match.ID, Match.PLAYER1_NAME, 
				Match.PLAYER1_SCORE, Match.PLAYER1_TIME_SPENT, Match.PLAYER2_NAME, 
				Match.PLAYER2_SCORE, Match.PLAYER2_TIME_SPENT },
				null, null, null, null, null);
	}

	public ArrayList<String> getAllMatches() {
		ArrayList<String> list = new ArrayList<String>();
		Cursor cursor = db.rawQuery("SELECT " 
				+ Match.PLAYER1_NAME + "," 
				+ Match.PLAYER1_SCORE + ","
				+ Match.PLAYER2_NAME + "," 
				+ Match.PLAYER2_SCORE 
				+ " "
				+ "FROM " + Match.TABLE_NAME, null); 
		if (cursor.moveToFirst()) {
			int p1Name = cursor.getColumnIndex(Match.PLAYER1_NAME);
			int p1Score = cursor.getColumnIndex(Match.PLAYER1_SCORE);
			int p2Name = cursor.getColumnIndex(Match.PLAYER2_NAME);
			int p2Score = cursor.getColumnIndex(Match.PLAYER2_SCORE);
		do {
			String str = cursor.getString(p1Name) + " "
					+ cursor.getInt(p1Score) + " "
					+ cursor.getString(p2Name) + " "
					+ cursor.getInt(p2Score);
			list.add(str);
		} while (cursor.moveToNext());
		}
		return list;
	}
}
