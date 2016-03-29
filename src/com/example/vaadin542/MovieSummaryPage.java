package com.example.vaadin542;

import java.sql.SQLException;

import com.example.vaadin542.model.Model;
import com.vaadin.server.StreamResource;

public class MovieSummaryPage extends MovieSummary {
	public MovieSummaryPage(int id, String title, String yr, String rating, String bdgt, String revenue, String director,
			                String cast, String genre, String synopsis, String posterURL) throws ClassNotFoundException, SQLException {
		lblTitle.setValue(title);
		lblYear.setValue(yr);
		lblRating.setValue(rating);
		lblBudget.setValue(bdgt);
		lblRevenue.setValue(revenue);
		lblDirector.setValue(director);
		lblCast.setValue(cast);
		lblGenre.setValue(genre);
		txtAreaSynopsis.setValue(synopsis);
		lblDirector.setValue(Model.getDirector(id));
		
		StreamResource res = new StreamResource(new ImageSource(posterURL), "poster");
		this.imgPoster.setSource(res);
	}
	
	
}
