package org.example;

import java.util.List;

public class Person {



    int ID;
    String first_name;
    String last_name;
    Person(int m_id, String m_first_name, String m_last_name){

        ID = m_id;
        first_name = m_first_name;
        last_name = m_last_name;

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void updatePerson(String property, String toSet){


        PostgreSQLConnection psqlc = (new MyConnection()).myCon();

        switch (property){
            case "ID":
                this.ID = Integer.parseInt(toSet);
                break;
            case "first_name":
                this.first_name = toSet;
                break;
            case "last_name":
                this.last_name = toSet;
                break;
        }

        psqlc.update("USER", property, toSet, "ID", String.valueOf(this.ID));

    }

    public String outPut() {

        return "\nID: " + getID() + "\nfirst_name: " + getFirst_name() + "\nlast_name: " + getLast_name();

    }

}
