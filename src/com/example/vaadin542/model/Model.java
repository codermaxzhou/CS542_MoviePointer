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
    
    
    public static synchronized List<Movie> search(ArrayList<String> arguments) throws ClassNotFoundException, SQLException {
        String query = "";
        String term = "";
        String type = "";
        String operator = "";
        
        
        term = arguments.get(0);
        type = arguments.get(1);
        
        if (type.equals("Title")) query = "select * from movie where title like '%" + term + "%' "; 
        if (type.equals("Year")) query = "select * from movie where year = " + term + " ";
        if (type.equals("Genre")) query = "select * from movie where movie_id IN (SELECT movie_id FROM movietype WHERE genre_id IN (SELECT genre_id FROM genre WHERE genre_name = '" + term + "')) ";
        if (type.equals("Director")) query = "SELECT* FROM movie WHERE movie_Id IN (SELECT movie_id FROM direct WHERE director_id IN (SELECT director_id FROM Director WHERE name LIKE '%" + term + "%') ) ";
        if (type.equals("Actor")) query = "SELECT * FROM movie WHERE movie_Id IN (SELECT movie_id FROM star_IN WHERE cast_id IN (SELECT cast_id FROM  cast WHERE name LIKE '%" + term + "%') ) ";
        
        for (int i=2; i<arguments.size(); i++) {
            if ((i % 3) == 2) operator = arguments.get(i);
            if ((i % 3) == 0) term = arguments.get(i);
            if ((i % 3) == 1) {
                type = arguments.get(i);
                if (type.equals("Title")) query = query + operator + " title like '%" + term + "%' ";
                if (type.equals("Year")) query = query + operator + " year = " + term + " ";
                if (type.equals("Genre")) query = query + operator + " movie_id IN (SELECT movie_id FROM movietype WHERE genre_id IN (SELECT genre_id FROM genre WHERE genre_name = '" + term + "')) ";
                if (type.equals("Director")) query = query + operator + " movie_Id IN (SELECT movie_id FROM direct WHERE director_id IN (SELECT director_id FROM Director WHERE name LIKE '%" + term + "%') ) ";
                if (type.equals("Actor")) query = query + operator + " movie_Id IN (SELECT movie_id FROM star_IN WHERE cast_id IN (SELECT cast_id FROM  cast WHERE name LIKE '%" + term + "%') ) ";
            }
        }
        
        query = query + ";";
        
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
