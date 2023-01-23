package org.example;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author brano
 * name: brano zeru
 * id: 328854989
 * date: 18/01/2023
 */

public class PostgreSQLConnection {

    public String host;
    public String port;
    public String username;
    public String password;
    public String database;
    private Connection connection;

    public PostgreSQLConnection(String host, String port, String username, String password, String database) {
        super();
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
    }
//    public void checkDemo(String table, String pkColumn) {
//        try {
//            this.connect();
//            Statement stmt = null;
//            String query = "SELECT * FROM " + table;
//
//            stmt = this.connection.createStatement();
//            ResultSet rs = stmt.executeQuery(query);
//            System.out.println("Column " + pkColumn);
//            while (rs.next()) {
//                String id = new String(rs.getBytes(pkColumn), StandardCharsets.UTF_8);
//                System.out.println("| Column " + id + " |");
//            }
//            this.disconnect();
//        } catch (Exception e) {
//            e.printStackTrace(System.out);
//        }
//    }

    private String getResults(String sqlQuery) {
            try {
                String result = "";
//            this.connect();
                Statement stmt = null;
                stmt = this.connection.createStatement();
                ResultSet rs = stmt.executeQuery(sqlQuery);
                ResultSetMetaData rsMeta = rs.getMetaData();
                //count -> number of columns in the table
                int count = rsMeta.getColumnCount();
                int i, j = 1;
                result += "\n| ";
                //Display Coloumns Name
                while (j <= count) {

//                String format = "%1$-" + rsMeta.getColumnDisplaySize(j) + "s";
                    String formatedValue = String.format("%s" ,rsMeta.getColumnLabel(j));
                    //Add current column name
                    result += formatedValue + " | ";
                    j++;
                }
                // "new String" is an empty list in the same length as "result"
                //This line print "---------------------------------------------"
                result += "\n" + new String(new char[result.length()]).replace("\0", "-");
                while (rs.next()) {
                    i = 1;
                    result += "\n| ";
                    while (i <= count) {
                        //                    String format = "%1$-" + rsMeta.getColumnDisplaySize(i) + "s";
                        //Adding data from the columns
                        String formatedValue = String.format("%s", new String(rs.getBytes(i), StandardCharsets.UTF_8));

                        String theLabel = String.format("%s", rsMeta.getColumnLabel(i));
                        int space_len = theLabel.length() - formatedValue.length();
                        if (space_len < 0) {
                            space_len *= -1;
                        }
                        String spaces = new String(new char[space_len]).replace("\0", " ");

                        result += formatedValue + spaces + " | ";
                        i++;
                    }
                }
                this.disconnect();
                return result;
            } catch (Exception e) {
                e.printStackTrace(System.out);
                return "";
            }
    }

    public String select(String table, String columns, String condition) {

        try {
            connect();
            PreparedStatement statement;
            //Empty query
            String query;
            if(condition != "no condition") {
//            "SELECT "ID", first_name, last_name FROM public."USER" WHERE "ID" = "ID"";
                //Preparing statement for injection
                query = "SELECT " + columns + " FROM \"" + table + "\" WHERE \"ID\" = ? ORDER BY \"ID\"  ";
                //Replacing (?) in values
                statement = this.connection.prepareStatement(query);
                statement.setString(1, condition);
            }else{
                query = "SELECT " + columns + " FROM \"" + table + "\"  ORDER BY \"ID\"  ";
                statement = this.connection.prepareStatement(query);
            }


            String res = getResults(statement.toString());
            System.out.println(res);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void insert(String table, String columns, String first_name, String last_name){

        try {
            connect();
            PreparedStatement statement;
            //Empty query
            String query = "INSERT INTO public.\""+table+"\"("+columns+") VALUES ( ?, ? )";
            //Preparing statement for injection
            statement = this.connection.prepareStatement(query);
            //Replacing (?, ?) in values
            statement.setString(1, first_name);
            statement.setString(2, last_name);

            String res = getResults(statement.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void update(String table, String column, String data, String column_condition, String data_condition){

        try {
            connect();
            String query = "UPDATE \""+table+"\" SET "+ column +" = ? WHERE  \"" + column_condition + "\" = ? ";
            PreparedStatement statement;
            statement = this.connection.prepareStatement(query);
            statement.setString(1,data);
            if(column_condition == "ID"){
                statement.setInt(2,Integer.parseInt(data_condition));
            }else{
                statement.setString(2,data_condition);
            }
            getResults(statement.toString());


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        //UPDATE public."USER" SET "ID"=?, first_name=?, last_name=? WHERE <condition>;

    }

    private void connect() throws Exception {
        Class.forName("org.postgresql.Driver");
        this.connection = null;
        this.connection = DriverManager.getConnection(
                "jdbc:postgresql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
    }

    private void disconnect() throws Exception {
        if (this.connection != null) {
            this.connection.close();
            this.connection = null;
        }
    }

    public List<Person> load_data(){

        try {
            connect();
//            //Get all rows from the table
            PreparedStatement statement;
            //Empty query
            String query;
            query = "SELECT * FROM \"USER\" ORDER BY \"ID\" ";
            statement = this.connection.prepareStatement(query);
            String txt = query.toString();
            Statement stmt = null;
            stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(txt);
            ResultSetMetaData rsMeta = rs.getMetaData();

            int count = rsMeta.getColumnCount();
            int ID = 0;
            String first_name = "";
            String last_name = "";


            List<Person> perObj = new ArrayList<Person>();

            int j = 0;
            while(rs.next()) {

                int i = 1;
                while (i <= count) {
                    //                    String format = "%1$-" + rsMeta.getColumnDisplaySize(i) + "s";
                    //Adding data from the columns
                    String formatedValue = String.format("%s", new String(rs.getBytes(i), StandardCharsets.UTF_8));
                    switch (i){

                        case 1:
                            ID = Integer.parseInt(formatedValue);
                            break;
                        case 2:
                            first_name = formatedValue;
                            break;
                        case 3:
                            last_name = formatedValue;
                            break;
                    }


                    i++;
                }
                Person perTemp = new Person(ID, first_name, last_name);
                perObj.add(perTemp);
                j++;
            }
            disconnect();
            return perObj;
        }catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    //        mission3
    /*
     * Create new Item to the list
     * Insert his properties
     * Save him in the database
     * return his ID
     * */
    public void insertPerson(List<Person> list, String first_name, String last_name){

        Person newPerson = new Person(0, first_name, last_name);
        list.add(newPerson);

        insert("USER", "first_name, last_name", newPerson.first_name, newPerson.last_name);
        list.get(list.size()-1).setID(maxID());

    }
    private int maxID(){

        try {

            String sqlQuery = "SELECT MAX(\"ID\") as max_id FROM \"USER\"";
            connect();
            Statement stmt = null;
            stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);

            rs.next();
            String formatedValue = String.format("%s", new String(rs.getBytes(1), StandardCharsets.UTF_8));

            disconnect();
            return Integer.parseInt(formatedValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }




}
