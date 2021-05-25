package com.example.weplay.Database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.weplay.ContactData.Contact;
import com.example.weplay.R;
import com.example.weplay.Util.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating user table
        String CREATE_USER_TABLE = "CREATE TABLE " + Util.Users.TABLE_NAME + "(" + Util.Users.KEY_id + " INTEGER PRIMARY KEY," + Util.Users.KEY_username + " VARCHAR(50),"
                + Util.Users.KEY_email + " VARCHAR(50)," + Util.Users.KEY_password + " VARCHAR(50)" + ")";
        String CREATE_SCORES_TABLE = "CREATE TABLE " + Util.Scores.TABLE_NAME + "(" + Util.Scores.KEY_id + " INTEGER PRIMARY KEY," + Util.Scores.KEY_username + " VARCHAR(50),"
                +Util.Scores.KEY_GAME_TITLE +" VARCHAR(20), " +Util.Scores.KEY_score + " INTEGER" + ")";
        //creating games table
        //executing sql queries for all the tables above
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_SCORES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = String.valueOf(R.string.db_drop);
        db.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});
        onCreate(db);
    }

    //User Part
    //----------
    //Adding Users
    public void addUser(Contact.Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        //creating a ContentValues object to store all the data before putting it into the database
        ContentValues values = new ContentValues();
        //putting username,email and password into 'values' variable
        values.put(Util.Users.KEY_username, user.getUsername());
        values.put(Util.Users.KEY_email, user.getEmail());
        values.put(Util.Users.KEY_password, user.getPassword());
        //insert row
        db.insert(Util.Users.TABLE_NAME, null, values);
        Log.d("DBHandler", "addContact: " + "item added");
    }

    public int CheckIfUserExists(String username, String email, String password) {
        if (password == null) {
            String countQuery = "SELECT * FROM " + Util.Users.TABLE_NAME + " WHERE " + Util.Users.KEY_username + " = '" + username +
                    "' OR " + Util.Users.KEY_email + " = '" + email + "'";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            return cursor.getCount();
        } else {
            String countQuery = "SELECT * FROM " + Util.Users.TABLE_NAME + " WHERE " + Util.Users.KEY_email + " = '" + email +
                    "' AND " + Util.Users.KEY_password + " = '" + password + "'";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            return cursor.getCount();
        }
    }

    public int CheckIfUsernameExists(String username) {
        String countQuery = "SELECT * FROM " + Util.Users.TABLE_NAME + " WHERE " + Util.Users.KEY_username +
                " = '" + username + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

    public void ChangeUsername(String old_username, String new_username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE Users SET " + Util.Users.KEY_username + " = '" + new_username +
                "' WHERE " + Util.Users.KEY_username + "= '" + old_username + "'";
        String query2 = "UPDATE Scores SET " + Util.Scores.KEY_username + " = '" + new_username +
                "' WHERE " + Util.Scores.KEY_username + "= '" + old_username + "'";
        db.execSQL(query);
        db.execSQL(query2);
    }

    public Contact.Users getUser(String email) {
        SQLiteDatabase db = getReadableDatabase();
        String getUsername = "SELECT " + Util.Users.KEY_username + "FROM " + Util.Users.TABLE_NAME + " WHERE " +
                Util.Users.KEY_email + " = '" + email + "'";
        //------------------------
        Cursor cursor = db.query(Util.Users.TABLE_NAME,
                new String[]{Util.Users.KEY_username},
                Util.Users.KEY_email + "=?", new String[]{String.valueOf(email)},
                null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        //-----------------------
        Contact contact = new Contact();
        Contact.Users userContact = contact.new Users();
        userContact.setUsername(cursor.getString(0));
        return userContact;
    }

    public void addDefaultScore(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        String TRIVIA_QUERRY = "insert into Scores (id_username,game_title,score) values ('"+username+"','TRIVIA',0)";
        String SECOND_GAME_QUERRY = "insert into Scores (id_username,game_title,score) values ('"+username+"','PROGRESS',0)";
        db.execSQL(TRIVIA_QUERRY);
        db.execSQL(SECOND_GAME_QUERRY);
    }
    public void addScore(Contact.Scores scores) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Util.Scores.KEY_username, scores.getUsername());
        values.put(Util.Scores.KEY_score, scores.getScore());
        values.put(Util.Scores.KEY_GAME_TITLE,scores.getGame_title());

        db.insert(Util.Scores.TABLE_NAME, null, values);
        Log.d("DBHandler", "addScore: " + "item added");
    }

    public String getScore(String username,String game_title) {
        String result;
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "SELECT MAX(" + Util.Scores.KEY_score + ") FROM " + Util.Scores.TABLE_NAME
                + " WHERE " + Util.Scores.KEY_username + " = '" + username + "' AND " + Util.Scores.KEY_GAME_TITLE +" = '"
                + game_title + "'";
        //String Query = "select max(score) from Scores where id_username = '"+username+"'";
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.moveToFirst()) {
            int col = cursor.getColumnIndex("score");
            result = cursor.getString(0);
            return result;
        }
            result = "0";
            return result;
    }
    public List<Contact.Scores> getUserScores(String username,String Game_title){
        SQLiteDatabase db = this.getReadableDatabase();
        Contact contact = new Contact();
        List<Contact.Scores> result = new ArrayList<>();
        /*
        String query = "SELECT " + Util.Scores.KEY_score + " FROM " + Util.Scores.TABLE_NAME
                +" WHERE " + Util.Scores.KEY_username + " = '" + username + "'";
         */
        String query = "SELECT * FROM " + Util.Scores.TABLE_NAME + " WHERE " + Util.Scores.KEY_username +
                " = '" + username + "' AND " + Util.Scores.KEY_GAME_TITLE + " = '"+ Game_title + "' ORDER BY " +
                Util.Scores.KEY_score +" DESC";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                String user = cursor.getString(cursor.getColumnIndex("id_username"));
                String game_title = cursor.getString(cursor.getColumnIndex("game_title"));
                int score = cursor.getInt(cursor.getColumnIndex("score"));
                Contact.Scores scores = contact.new Scores(score,user,game_title);
                result.add(scores);
            }while(cursor.moveToNext());
        }
        return result;
    }
    public List<Contact.Scores> getAllScores(String Game_title){
        SQLiteDatabase db = this.getReadableDatabase();
        Contact contact = new Contact();
        List<Contact.Scores> result = new ArrayList<>();
        /*
        String query = "SELECT " + Util.Scores.KEY_score + " FROM " + Util.Scores.TABLE_NAME
                +" WHERE " + Util.Scores.KEY_username + " = '" + username + "'";
         */
        String query = "SELECT * FROM " + Util.Scores.TABLE_NAME + " WHERE " + Util.Scores.KEY_GAME_TITLE + " = '" +
                Game_title + "' ORDER BY " + Util.Scores.KEY_score + " DESC";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                String user = cursor.getString(cursor.getColumnIndex("id_username"));
                String game_title = cursor.getString(cursor.getColumnIndex("game_title"));
                int score = cursor.getInt(cursor.getColumnIndex("score"));
                Contact.Scores scores = contact.new Scores(score,user,game_title);
                result.add(scores);
            }while(cursor.moveToNext());
        }
        return result;
    }
}

