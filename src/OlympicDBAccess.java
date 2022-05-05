import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class OlympicDBAccess {

    final String URL = "jdbc:mysql://seitux2.adfa.unsw.edu.au/z5364371";
    final String user = "z5364371";
    final String pass = "mysqlpass";

    public OlympicDBAccess(){ //opens connection to the database
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }
        catch (Exception ex)
        {
            System.err.println("Unable to load MySQL driver.");
            ex.printStackTrace();
        }
    }

    public void createTables() {
        Statement stmt = null;
        try {
            Connection con = DriverManager.getConnection(URL,user,pass);
            stmt = con.createStatement();
            stmt.executeUpdate("CREATE TABLE OLYMPICS ("
                    + "ID INT NOT NULL AUTO_INCREMENT,"
                    + "year INT,"
                    + "season VARCHAR(7),"
                    + "city VARCHAR(23),"
                    + "PRIMARY KEY (ID));");
            stmt.executeUpdate("CREATE TABLE EVENTS ("
                    + "ID INT NOT NULL AUTO_INCREMENT,"
                    + "sport VARCHAR(26),"
                    + "event VARCHAR(86),"
                    + "PRIMARY KEY (ID));");
            stmt.executeUpdate("CREATE TABLE ATHLETES ("
                    + "ID INT NOT NULL AUTO_INCREMENT,"
                    + "name VARCHAR(94),"
                    + "noc CHAR(3),"
                    + "gender CHAR(1),"
                    + "PRIMARY KEY (ID));");
            stmt.executeUpdate("CREATE TABLE MEDALS ("
                    + "ID INT NOT NULL AUTO_INCREMENT,"
                    + "olympicID INT,"
                    + "eventID INT,"
                    + "athleteID INT,"
                    + "PRIMARY KEY (ID),"
                    + "FOREIGN KEY (olympicID) REFERENCES OLYMPICS(ID),"
                    + "FOREIGN KEY (eventID) REFERENCES EVENTS(ID),"
                    + "FOREIGN KEY (athleteID) REFERENCES ATHLETES(ID));");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTables() {
        Statement stmt = null;
        try {
            Connection con = DriverManager.getConnection(URL,user,pass);
            stmt = con.createStatement();
            stmt.executeUpdate("DROP TABLE MEDALS;"); //drops medals first to avoid foreign key problems
            stmt.executeUpdate("DROP TABLE ATHLETES;");
            stmt.executeUpdate("DROP TABLE EVENTS;");
            stmt.executeUpdate("DROP TABLE OLYMPICS;");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void populateTables() {
        //this should be the first line in this method.
        long time = System.currentTimeMillis();
        populateOlympics();
        populateEvents();
        populateAthletes();

        //this should be the last line in this method
        System.out.println("Time to populate: " + (System.currentTimeMillis() - time) + "ms");
    }

    public void populateOlympics (){
        try {
            Connection con = DriverManager.getConnection(URL,user,pass);
            Statement stmt = con.createStatement();
            Scanner sc = new Scanner(new FileReader("olympics.csv"));
            while(sc.hasNextLine()){
                String lineText = sc.nextLine();
                String[] lineData = lineText.split(",");
                stmt.executeUpdate("INSERT INTO OLYMPICS(year, season, city) VALUES(" + lineData[0] + ", " + lineData[1] + ", " + lineData[2] + ");");
            }
            con.close();
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void populateEvents() {
        try {
            Connection con = DriverManager.getConnection(URL,user,pass);
            Statement stmt = con.createStatement();
            Scanner sc = new Scanner(new FileReader("events.csv"));
            while(sc.hasNextLine()){
                String lineText = sc.nextLine();
                String[] lineData = lineText.split(",");
                stmt.executeUpdate("INSERT INTO EVENTS(sport, event) VALUES(" + lineData[0] + ", " + lineData[1] + ");");
            }
            con.close();
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void populateAthletes() {
        try {
            Connection con = DriverManager.getConnection(URL,user,pass);
            Statement stmt = con.createStatement();
            Scanner sc = new Scanner(new FileReader("athletes.csv"));
            while(sc.hasNextLine()){
                String lineText = sc.nextLine();
                String[] lineData = lineText.split(",");
                stmt.executeUpdate("INSERT INTO ATHLETES(name, noc, gender) VALUES(" + lineData[0] + ", " + lineData[1] + ", " + lineData[2] + ");");
            }
            con.close();
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void populateMedals() {


    }


        public void runQueries() {

    }


}

