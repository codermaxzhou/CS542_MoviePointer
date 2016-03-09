package com.example.vaadin542.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
//import org.vaadin.addons.lazyquerycontainer.Query;
//import org.vaadin.addons.lazyquerycontainer.QueryDefinition;
//import org.vaadin.addons.lazyquerycontainer.QueryFactory;

public class Model {
    private static Connection conn = null;
    private static String JDBC_DRIVER= "com.mysql.jdbc.Driver";  
    private static String DB_URL = "jdbc:mysql://localhost/movie";
    private static String USER = "root";
    private static String PASS = "";
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if(conn == null) {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        }
        
        return conn;
    }
    
    public static synchronized List<User> filter(String filter) throws ClassNotFoundException, SQLException {
        String query = "";
        
        if(filter == null || filter.equals("")) {
            query = "SELECT * FROM users;";
        } else {
            query = "SELECT * FROM users WHERE username ='" + filter + "';"; 
        }
        
        Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        List<User> l = new ArrayList<>();
        
        while(rs.next()) {
            User u = new User();
            
            u.setID(rs.getInt("userid"));
            u.setName(rs.getString("username"));
            u.setType(rs.getString("usertype"));
            
            l.add(u);
        }
        
        return l;
    }
    
    public static synchronized List<Movie> search(String argument) throws ClassNotFoundException, SQLException {
        String query = "SELECT * FROM movie;";
        
        if(argument == null || argument.equals("")) {
            query = "";
        } else {
            query = "select * from movie where title like '%" + argument + "%';"; 
        }
        
        
        Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        List<Movie> l = new ArrayList<>();
        
        while(rs.next()) {
            Movie m = new Movie();
            
            m.setMovieID(rs.getInt("movie_id"));
            m.setMovieTitle(rs.getString("title"));
            m.setOverview(rs.getString("overview"));
            m.setReleaseDate(rs.getDate("rel_date"));
            m.setBudget(rs.getInt("budget"));
            m.setRevenue(rs.getInt("revenue"));
            m.setRuntime(rs.getInt("runtime"));
            m.setPosterPath(rs.getString("poster_path"));
            m.setRating(rs.getFloat("rating"));
            
            l.add(m);
        }
        
        return l;
    }
}
