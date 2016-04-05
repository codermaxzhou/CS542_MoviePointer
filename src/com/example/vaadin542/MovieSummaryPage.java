package com.example.vaadin542;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.example.vaadin542.model.Model;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.themes.BaseTheme;

public class MovieSummaryPage extends MovieSummary {
	public MovieSummaryPage(int id, String title, String yr, String rating, String bdgt, String revenue, String synopsis, String posterURL) throws ClassNotFoundException, SQLException {
		lblTitle.setValue(title);
		lblYear.setValue(yr);
		lblRating.setValue(rating);
		lblBudget.setValue(bdgt);
		lblRevenue.setValue(revenue);
		txtAreaSynopsis.setValue(synopsis);
		lblDirector.setValue(Model.getDirector(id));
	    //lblGenre.setValue(Model.getGenre(id));
	    lblCast.setValue(Model.getActor(id));
		StreamResource res = new StreamResource(new ImageSource(posterURL), "poster");
		this.imgPoster.setSource(res);
		btnGenre.setVisible(false);
		btnGenre2.setVisible(false);
		btnGenre3.setVisible(false);
		btnGenre4.setVisible(false);
		btnGenre5.setVisible(false);
		this.setGenre(Model.getGenre(id));
	}
	
	private void setGenre(List<String> s) {
	    if(!s.isEmpty()) {
	        btnGenre.setCaption(s.remove(0));
	        btnGenre.setVisible(true);
	    }
        if(!s.isEmpty()) {
            btnGenre2.setCaption(s.remove(0));
            btnGenre2.setVisible(true);
        }
        if(!s.isEmpty()) {
            btnGenre3.setCaption(s.remove(0));
            btnGenre3.setVisible(true);
        }
        if(!s.isEmpty()) {
            btnGenre4.setCaption(s.remove(0));
            btnGenre4.setVisible(true);
        }
        if(!s.isEmpty()) {
            btnGenre5.setCaption(s.remove(0));
            btnGenre5.setVisible(true);
        }
	}
}
