package com.example.vaadin542.model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Model {
    private static Connection conn = null;
    private static String JDBC_DRIVER= "com.mysql.jdbc.Driver";  
    private static String DB_URL = "jdbc:mysql://localhost/movie";
    private static String USER = "root";
    private static String PASS = "";
    private static String queryMovie = "";
    private static String queryYear = "";
    private static String queryGenre = "";
    private static String queryDirector = "";
    private static String queryActor = "";
    private static String queryFilter = "";
    
    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        if(conn == null) {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        }
        
        return conn;
    }
    
    public static void generateFilterQuery(List<Integer> y, List<String> g, List<String> d, List<String> a, double r) {
       queryFilter = queryMovie.replace(";", "");
       if (!y.isEmpty()) {
           queryFilter = queryFilter + " AND (year = " + y.remove(0);
           while(!y.isEmpty())
               queryFilter = queryFilter + " OR year = " + y.remove(0);
           queryFilter = queryFilter + ")";
       }
       
       if (!g.isEmpty()){
           queryFilter = queryFilter + " AND (movie_id IN (SELECT movie_id FROM movietype WHERE genre_id IN (SELECT genre_id FROM genre WHERE genre_name = '" + g.remove(0) + "')) ";
           while(!g.isEmpty())
               queryFilter = queryFilter + " OR movie_id IN (SELECT movie_id FROM movietype WHERE genre_id IN (SELECT genre_id FROM genre WHERE genre_name = '" + g.remove(0) + "')) ";
           queryFilter = queryFilter + ")";
       }
       
       if (!d.isEmpty()){
           queryFilter = queryFilter + " AND (movie_Id IN (SELECT movie_id FROM direct WHERE director_id IN (SELECT director_id FROM Director WHERE name LIKE '%" + d.remove(0) + "%'))";
           while(!d.isEmpty())
               queryFilter = queryFilter + " OR movie_Id IN (SELECT movie_id FROM direct WHERE director_id IN (SELECT director_id FROM Director WHERE name LIKE '%" + d.remove(0) + "%'))";
           queryFilter = queryFilter + ")";
       }
       
       if (!a.isEmpty()){
           queryFilter = queryFilter + " AND (movie_Id IN (SELECT movie_id FROM star_IN WHERE cast_id IN (SELECT cast_id FROM  cast WHERE name LIKE '%" + a.remove(0) + "%'))";
           while(!a.isEmpty())
               queryFilter = queryFilter + " OR movie_Id IN (SELECT movie_id FROM star_IN WHERE cast_id IN (SELECT cast_id FROM  cast WHERE name LIKE '%" + a.remove(0) + "%'))";
           queryFilter = queryFilter + ")";
       }
       queryFilter = queryFilter + " AND (rating >= '"+ r +"')";
       queryFilter = queryFilter + ";";
    }
    
    public static void generateQuery(ArrayList<String> arguments) {
        String term = "";
        String type = "";
        String operator = "";
        
        
        term = arguments.get(0);
        type = arguments.get(1);
        
        
        if (type.equals("Title")) queryMovie = "select * from movie where title like '%" + term + "%' "; 
        if (type.equals("Year")) queryMovie = "select * from movie where year = " + term + " ";
        if (type.equals("Genre")) queryMovie = "select * from movie where movie_id IN (SELECT movie_id FROM movietype WHERE genre_id IN (SELECT genre_id FROM genre WHERE genre_name = '" + term + "')) ";
        if (type.equals("Director")) queryMovie = "SELECT* FROM movie WHERE movie_Id IN (SELECT movie_id FROM direct WHERE director_id IN (SELECT director_id FROM Director WHERE name LIKE '%" + term + "%')) ";
        if (type.equals("Actor")) queryMovie = "SELECT * FROM movie WHERE movie_Id IN (SELECT movie_id FROM star_IN WHERE cast_id IN (SELECT cast_id FROM  cast WHERE name LIKE '%" + term + "%')) ";
        
        for (int i=2; i<arguments.size(); i++) {
            if ((i % 3) == 2) operator = arguments.get(i);
            if ((i % 3) == 0) term = arguments.get(i);
            if ((i % 3) == 1) {
                type = arguments.get(i);
                if (type.equals("Title")) queryMovie = queryMovie + operator + " title like '%" + term + "%' ";
                if (type.equals("Year")) queryMovie = queryMovie + operator + " year = " + term + " ";
                if (type.equals("Genre")) queryMovie = queryMovie + operator + " movie_id IN (SELECT movie_id FROM movietype WHERE genre_id IN (SELECT genre_id FROM genre WHERE genre_name = '" + term + "')) ";
                if (type.equals("Director")) queryMovie = queryMovie + operator + " movie_Id IN (SELECT movie_id FROM direct WHERE director_id IN (SELECT director_id FROM Director WHERE name LIKE '%" + term + "%') ) ";
                if (type.equals("Actor")) queryMovie = queryMovie + operator + " movie_Id IN (SELECT movie_id FROM star_IN WHERE cast_id IN (SELECT cast_id FROM  cast WHERE name LIKE '%" + term + "%') ) ";
            }
        }
        
        queryYear = "SELECT year, COUNT(year) AS count FROM (" + queryMovie + ") a group by year order by count desc;";
        queryGenre = "SELECT genre_name, COUNT(genre_name) AS count FROM genre joIN movietype ON genre.genre_id=movietype.genre_id joIN (" + queryMovie + ") a ON movietype.movie_id=a.movie_Id GROUP BY genre_name ORDER BY count DESC;";
        queryDirector = "SELECT name, COUNT(name) AS count FROM director joIN direct ON direct.director_id = director.director_id joIN ("+queryMovie+") a ON direct.movie_id=a.movie_Id GROUP BY name ORDER BY count DESC;";
        queryActor = "SELECT name, COUNT(name) AS count FROM cast joIN star_IN on cast.cast_id = star_IN.cast_id joIN ("+queryMovie+") a ON star_IN.movie_id=a.movie_Id GROUP BY name ORDER BY count DESC;";
        queryMovie = queryMovie + ";";
        

    }
    
    public static synchronized ResultSet dbAccess(String query) throws ClassNotFoundException, SQLException {
        Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return rs;
    }
    
    public static synchronized List<Movie> search() throws ClassNotFoundException, SQLException, MalformedURLException, IOException {        
        
        List<Movie> l = new ArrayList<>();
        ResultSet rs = dbAccess(queryMovie);
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
            m.setYear(rs.getString("year"));
            Blob blob = rs.getBlob("poster_img");
            InputStream in = null;
            if(blob != null) in = blob.getBinaryStream();
            m.setPosterImg(in);
            l.add(m);
        }
        
        return l;
    }
    
    public static synchronized List<Movie> filter() throws ClassNotFoundException, SQLException, MalformedURLException, IOException {
        
        List<Movie> l = new ArrayList<>();
        ResultSet rs = dbAccess(queryFilter);
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
            m.setYear(rs.getString("year"));
            Blob blob = rs.getBlob("poster_img");
            InputStream in = null;
            if(blob != null) in = blob.getBinaryStream();
            m.setPosterImg(in);
            l.add(m);
        }
        
        return l;
        
    }
    
    public static synchronized List<String> filterYear() throws ClassNotFoundException, SQLException {
        List<String> l = new ArrayList<>();
        ResultSet rs = dbAccess(queryYear);
        while(rs.next()) {
            l.add(rs.getString("year"));
        }
        return l;
    }
    
    public static synchronized List<String> filterGenre() throws ClassNotFoundException, SQLException {
        List<String> l = new ArrayList<>();
        ResultSet rs = dbAccess(queryGenre);
        while(rs.next()) {
            l.add(rs.getString("genre_name"));
        }
        return l;
    }
    
    public static synchronized List<String> filterDirector() throws ClassNotFoundException, SQLException {
        List<String> l = new ArrayList<>();
        ResultSet rs = dbAccess(queryDirector);
        while(rs.next()) {
            l.add(rs.getString("name"));
        }
        return l;
    }
    
    public static synchronized List<String> filterActor() throws ClassNotFoundException, SQLException {
        List<String> l = new ArrayList<>();
        ResultSet rs = dbAccess(queryActor);
        while(rs.next()) {
            l.add(rs.getString("name"));
        }
        return l;
    }
    
    public static synchronized String getDirector(int id) throws ClassNotFoundException, SQLException {
        ResultSet rs = dbAccess("SELECT name, COUNT(name) AS count FROM director joIN direct ON direct.director_id = director.director_id joIN (SELECT* FROM movie WHERE movie_id = " + id + ") a ON direct.movie_id=a.movie_Id GROUP BY name ORDER BY count DESC;");
        String s = "";
        while(rs.next()) {
            s = s + rs.getString("name") + "; ";
        }
        if (s != "") {
            s = s.substring(0, s.length()-2);
        }
        return s;
    }
    
    public static synchronized List<String> getGenre(int id) throws ClassNotFoundException, SQLException {
        ResultSet rs = dbAccess("SELECT genre_name, COUNT(genre_name) AS count FROM genre joIN movietype ON genre.genre_id=movietype.genre_id joIN (SELECT* FROM movie WHERE movie_id = " + id + ") a ON movietype.movie_id=a.movie_Id GROUP BY genre_name ORDER BY count DESC;");
        List<String> s = new ArrayList<String>();
        while(rs.next()) {
            s.add(rs.getString("genre_name"));
        }
        return s;
    }
    
    public static synchronized String getActor(int id) throws ClassNotFoundException, SQLException {
        ResultSet rs = dbAccess("SELECT name, COUNT(name) AS count FROM cast joIN star_IN on cast.cast_id = star_IN.cast_id joIN (SELECT* FROM movie WHERE movie_id = " + id + ") a ON star_IN.movie_id=a.movie_Id GROUP BY name ORDER BY count DESC;");
        String s = "";
        int count = 0;
        while(rs.next() && (count < 5)) {
            s = s + rs.getString("name") + "; ";
            count ++;
        }
        if (s != "") {
            s = s.substring(0, s.length()-2);
        }
        if (count == 5) {
            s = s + " ...";
        }
        return s;
    }
    
    public static BufferedImage resize(final URL url, int width, int height) throws IOException {
        Image image = ImageIO.read(url);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Increase quality if needed at the expense of speed
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        AffineTransform scaleTransform = AffineTransform.getScaleInstance(
                width / (double) image.getWidth(null), height / (double) image.getHeight(null));
        g.drawImage(image, scaleTransform, null);

        // Release resources
        g.dispose();

        return bufferedImage;
    }
    
    public synchronized static void saveImage(int mid, String path) throws ClassNotFoundException, SQLException, IOException {
        getConnection();
        PreparedStatement pst = null;
        conn.setAutoCommit(false);
        URL url = new URL(path);
        String tDir = System.getProperty("java.io.tmpdir"); 
        String p = tDir + "tmp" + ".jpg"; 
        File image = new File(p);
        ImageIO.write(resize(url, 27*3, 40*3), "jpg", image);
        FileInputStream fis = new FileInputStream (image);

        String sql="update movie set poster_img = ? where movie_id = ?;";
        pst = conn.prepareStatement(sql);

        pst.setBinaryStream(1, (InputStream)fis, (int) image.length());
        pst.setInt(2, mid);
        pst.executeUpdate();
        conn.commit();
        pst.close();
        fis.close();
        conn.setAutoCommit(true);
    }
    
}
