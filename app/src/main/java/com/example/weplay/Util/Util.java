package com.example.weplay.Util;

public class Util {
    //variables for creating the database
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WePlay";

    //creating separate class for columns from table
    public class Users {
        public static final String TABLE_NAME = "Users";
        public static final String KEY_id = "id_user";
        public static final String KEY_username = "username";
        public static final String KEY_email = "email";
        public static final String KEY_password = "password";
    }
    public class Scores {
        public static final String TABLE_NAME = "Scores";
        public static final String KEY_id = "id_score";
        public static final String KEY_username = "id_username";
        public static final String KEY_GAME_TITLE = "game_title";
        public static final String KEY_score = "score";
    }
}