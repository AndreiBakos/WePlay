package com.example.weplay.ContactData;

public class Contact {

    public Contact(){}

    public class Users {
        private int id;
        private String username,email,password;

        public Users(int id, String username, String email, String password) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.password = password;
        }

        public Users(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }

        public Users() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public class Scores {
        private int id,score;
        private String username,game_title;

        public Scores(){}

        public Scores(int id, int score, String username,String game_title) {
            this.id = id;
            this.score = score;
            this.username = username;
            this.game_title = game_title;
        }

        public Scores(int score, String username,String game_title) {
            this.score = score;
            this.username = username;
            this.game_title = game_title;
        }

        public int getId() { return id; }

        public void setId(int id) { this.id = id; }

        public int getScore() { return score; }

        public void setScore(int score) { this.score = score; }

        public String getUsername() { return username; }

        public void setUsername(String username) { this.username = username; }

        public String getGame_title() { return game_title; }

        public void setGame_title(String game_title) { this.game_title = game_title; }
    }

}
