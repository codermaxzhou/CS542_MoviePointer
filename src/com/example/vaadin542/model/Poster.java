package com.example.vaadin542.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Poster {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        int count = 0;
        ResultSet rs = Model.dbAccess("select * from movie;");
        while(rs.next()) {
            try {
                Model.saveImage(rs.getInt("movie_id"), rs.getString("poster_path"));
                count ++;
                System.out.println(count);
            } catch (ClassNotFoundException | FileNotFoundException
                    | SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}
