package com.example.vaadin542;

public class MovieSummaryPage extends MovieSummary {
	public MovieSummaryPage(String title, String yr, String rating, String bdgt, String revenue, String director,
			                String cast, String genre, String synopsis, String posterURL) {
		lblTitle.setValue(title);
		lblYear.setValue(yr);
		lblRating.setValue(rating);
		lblBudget.setValue(bdgt);
		lblRevenue.setValue(revenue);
		lblDirector.setValue(director);
		lblCast.setValue(cast);
		lblGenre.setValue(genre);
		txtAreaSynopsis.setValue(synopsis);
	}
}
