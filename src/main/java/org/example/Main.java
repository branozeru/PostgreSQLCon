package org.example;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        PostgreSQLConnection psqlc = (new MyConnection()).myCon();
//      mission1

//      ------------------------------------------------------------------------

        List<Person> list = new ArrayList<Person>();
        list = psqlc.load_data();
//        for(int i = 0; i < list.size(); i++){
//            System.out.println(list.get(i).outPut());
//        }
//        psqlc.select("USER", "*", "no condition");

//        ----------------------------------------------------------------------

//      mission2
//        -----------------------------------------------------------------------
//        psqlc.select("USER", "*", "no condition");
//        list.get(0).updatePerson("last_name", "zeru");
//        System.out.println(list.get(0).outPut());
//        psqlc.select("USER", "*", "no condition");
        //        ----------------------------------------------------------------

//        mission3
        /*
        * Create new Item to the list
        * Insert his properties
        * Save him in the database
        * return his ID
        * */

//        for(int i = 0; i < list.size(); i++){
//            System.out.println(list.get(i).outPut());
//        }
//        psqlc.select("USER", "*", "no condition");
//
//        psqlc.insertPerson(list, "firstName", "lastName");
//
//        for(int i = 0; i < list.size(); i++){
//            System.out.println(list.get(i).outPut());
//        }
//        psqlc.select("USER", "*", "no condition");


    }



}