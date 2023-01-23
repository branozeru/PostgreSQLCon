package org.example;

public class MyConnection {



    private String host = "localhost";
    private String port = "5432";
    private String user = "temp_login";
    private String pass = "123";
    private String database = "postgres";

    public PostgreSQLConnection myCon(){
        return new PostgreSQLConnection(host, port, user, pass, database);
    }

}
