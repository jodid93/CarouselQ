package com.example.notandi.carouselq;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Array;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {

    private Connection connection;
    private boolean isConnected = false;


    public DBConnector() throws URISyntaxException,SQLException {

        if(this.isConnected){
            return;
        }
        this.connection = null;


        try {

            /*this.connection = DriverManager.getConnection(
                    "jdbc:postgresql://ec2-54-228-226-93.eu-west-1.compute.amazonaws.com:5432/d7135l4obpt6ah", "niifhdogkmbvjv","0xPX5jsIs5W2Zu3wLvHds5inRr");
            */

            String dbUrl = System.getenv("JDBC_DATABASE_URL");
            this.connection = DriverManager.getConnection(dbUrl);

        } catch (SQLException e) {

            System.out.println("--------------loser thu failadir---------------");
           
            e.printStackTrace();
            return;

        }finally{
            System.out.println("----------vei thetta er kannski komid--------------");
        }

        //initTables();
        this.isConnected = true;


    }

    private void initTables() throws SQLException{
        Statement stmt = null;
        String user = "CREATE TABLE IF NOT EXISTS users " +
                "(id VARCHAR(255), " +
                " username VARCHAR(255), " +
                " password VARCHAR(255), " +
                " PRIMARY KEY ( id ))";

        try {

            stmt = this.connection.createStatement();
            stmt.executeUpdate(user);

        } catch (SQLException e ) {
            System.out.print("ohh o, eitthvad for urskedis");
        } finally {
            if (stmt != null) { stmt.close(); }
        }


        String gameState = "CREATE TABLE IF NOT EXISTS gameState " +
                "(id VARCHAR(255), " +
                " gameState VARCHAR(2000),"
                + "score INTEGER,"
                + "PRIMARY KEY ( id ))";

        try {

            stmt = this.connection.createStatement();
            stmt.executeUpdate(gameState);

        } catch (SQLException e ) {
            System.out.print("ohh o, eitthvad for urskedis");
        } finally {
            if (stmt != null) { stmt.close(); }
        }


        String friends = "CREATE TABLE IF NOT EXISTS friends " +
                "(id VARCHAR(255),  " +
                " friendID VARCHAR(255000),"
                + "PRIMARY KEY ( id ))";

        try {

            stmt = this.connection.createStatement();
            stmt.executeUpdate(friends);

        } catch (SQLException e ) {
            System.out.print("ohh o, eitthvad for urskedis");
        } finally {
            if (stmt != null) { stmt.close(); }
        }

    }

   /* public boolean isValidUser(String UN, String PW)throws SQLException {

        PreparedStatement pst;
        Statement stmt = null;

        String TableUN = null;
        String TablePW = null;

        try {
            pst = this.connection.prepareStatement("select username, password " +
                    "from users "+
                    "where username = ? AND password = ?");

            pst.setString(1, UN);
            pst.setString(2, this.security.hashPassword(PW));

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                TableUN = rs.getString("username");
                TablePW = rs.getString("password");
                System.out.println(" gogn fra toflu: "+TableUN+ " "+TablePW);
            }
        } catch (SQLException e ) {
            System.out.println("isValid user fail!");
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return (!(TableUN == null && TablePW == null));
    }*/

    public String getGameState(String UN) throws SQLException {
        PreparedStatement pst;
        Statement stmt = null;
        String gamestate = "nothing";


        try {
            pst = this.connection.prepareStatement("SELECT gamestate FROM gamestate WHERE id = ? ");
            pst.setString(1, UN);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                gamestate = rs.getString("gamestate");
            }
        } catch (SQLException e ) {
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return gamestate;
    }


    /*public boolean registrationSuccess(String UN, String PW) throws SQLException {
        PreparedStatement pst;
        Statement stmt = null;

        String TableUN = null;
        String TablePW = null;

        try {
            pst = this.connection.prepareStatement("select username, password " +
                    "from users "+
                    "where username = ? AND password = ?");

            pst.setString(1, UN);
            pst.setString(2, security.hashPassword(PW));

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                TableUN = rs.getString("username");
                TablePW = rs.getString("password");
                System.out.println(" gogn fra toflu: "+TableUN+ " "+TablePW);
            }
        } catch (SQLException e ) {
            System.out.println("isValid user fail!");
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return (TableUN == null && TablePW == null);
    }*/

    /*public void createNewUser(String UN, String PW)throws SQLException {

        PreparedStatement pst;
        Statement stmt = null;

        try {
            pst = this.connection.prepareStatement("insert into users (id, username, password) "
                    + "values(?,?,?)");

            pst.setString(1, UN);
            pst.setString(2, UN);
            pst.setString(3, security.hashPassword(PW));

            pst.executeUpdate();

        } catch (SQLException e ) {
            System.out.println("createNewUser failed!");
        } finally {
            if (stmt != null) { stmt.close(); }
        }

        try {
            pst = this.connection.prepareStatement("insert into gameState (id, gamestate, score) "
                    + "values(?,?,0)");

            pst.setString(1, UN);
            pst.setString(2, "{\"userName\": \""+UN+"\", \"upgrades1\": [[1,0,3],[0,3,3],[3,3,3]], \"upgrades2\": [[1,0,3],[0,3,3],[3,3,3]], \"currency\": 0, \"settings\": {\"audio-slider\": 20}, \"currFactor\": 0, \"treeFactor\": 1, \"timestamp\": "+ System.currentTimeMillis() +",\"score\":0 }");

            pst.executeUpdate();
            System.out.println("hallo");
        } catch (SQLException e ) {
            System.out.println("createNewUser failed!");
        } finally {
            if (stmt != null) { stmt.close(); }
        }

        try {
            pst = this.connection.prepareStatement("insert into friends (id, friendid) "
                    + "values(?,?)");

            pst.setString(1, UN);
            pst.setString(2, UN);

            pst.executeUpdate();

        } catch (SQLException e ) {
            System.out.println("createNewUser failed!");
            System.out.println(e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }

        return;
    }*/

    public void updateFriendsList(String User, String friend) throws SQLException {

        PreparedStatement pst = null;
        Statement stmt = null;

        String friendid = null;

        try {
            pst = this.connection.prepareStatement("select friendid from friends where id = ?");

            pst.setString(1, User);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                friendid = rs.getString(1);
            }
        } catch (SQLException e ) {
            System.out.println("updateFriendsList failed!");
            System.out.println(e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }


        friendid += ","+friend;


        try {
            pst = this.connection.prepareStatement("update friends set friendid = ? where id = ?");

            pst.setString(1, friendid);
            pst.setString(2, User);
            pst.executeUpdate();

        } catch (SQLException e ) {
            System.out.println("updateFriendsList failed 2!");
            System.out.println(e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }


    }

    public String findUser(String user) throws SQLException {

        PreparedStatement pst;
        Statement stmt = null;

        String TableUN = null;

        try {
            pst = this.connection.prepareStatement("Select username from users where username = ? ");

            pst.setString(1, user);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                TableUN = rs.getString("username");
            }

        } catch (SQLException e ) {
            System.out.println("find user failed");
            System.out.println(e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }

        return TableUN;
    }

    public String findFriendList(String user) throws SQLException {

        PreparedStatement pst;
        Statement stmt = null;

        String TableFL = null;

        try {
            pst = this.connection.prepareStatement("Select friendid from friends where id = ? ");

            pst.setString(1, user);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                TableFL = rs.getString("friendid");
            }

        } catch (SQLException e ) {
            System.out.println("find friendlist failed");
            System.out.println(e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }

        return TableFL;
    }

    public String findHighScoreForALL() throws SQLException {
        PreparedStatement pst;
        Statement stmt = null;

        String data = null;

        try {
            pst = this.connection.prepareStatement("Select id, score from gamestate order by score desc limit 10");

            ResultSet rs = pst.executeQuery();


            while (rs.next()) {
                data += ","+rs.getString("id");
                data += ","+Integer.toString(rs.getInt("score"));
            }

        } catch (SQLException e ) {
            System.out.println("find user failed");
            System.out.println(e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }

        return data;
    }

    public String findHighScoreForUser(String user, String[] friends) throws SQLException {
        PreparedStatement pst;
        Statement stmt = null;

        String data = null;
        Array listi = this.connection.createArrayOf("varchar", friends);

        try {
            pst = this.connection.prepareStatement("Select id, score from gamestate Where id = ANY(?) order by score desc limit 10");

            pst.setArray(1, listi);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                data += ","+rs.getString("id");
                data += ","+Integer.toString(rs.getInt("score"));
            }

        } catch (SQLException e ) {
            System.out.println("find user failed");
            System.out.println(e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }

        System.out.println(data);
        return data;
    }



}
