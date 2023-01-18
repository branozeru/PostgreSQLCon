package org.example;
import java.sql.*;

public class Main {
    public static void main(String[] args) {

        String host = "localhost";
        String port = "5432";
        String user = "temp_login";
        String pass = "123";
        String database = "postgres";
        PostgreSQLConnection psqlc = new PostgreSQLConnection(host, port, user, pass, database);


//        psqlc.update("USER", "last_name", "LAST N", "last_name", "newLastName");
        psqlc.update("USER", "last_name", "yismah", "first_name", "orian");

//        psqlc.insert("USER", "first_name, last_name", "orian", "yismahMoshe");
        psqlc.select("USER", "*", "no condition");

    }
}