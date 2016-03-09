package com.example.vaadin542;

import java.sql.SQLException;


import com.example.vaadin542.model.Model;
import com.example.vaadin542.model.Movie;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ResultsPage extends ResultsLayout implements ClickListener {
    UI parent;
    
	public ResultsPage(String searchTerm, UI parent) {
		optionGrp1.setMultiSelect(true);
		optionGrp2.setMultiSelect(true);
		optionGrp3.setMultiSelect(true);
		optionGrp4.setMultiSelect(true);
		btnHome.addClickListener(this);
		this.parent = parent;
		
		try {
            gridResults.setContainerDataSource(new BeanItemContainer<>(Movie.class, Model.search(searchTerm)));
            gridResults.setVisibleColumns("movieID", "movieTitle","releaseDate","rating");
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
    @Override
    public void buttonClick(ClickEvent event) {
        Button origin = event.getButton();
        
        if (origin.equals(btnHome)) {
            MainPage layout = new MainPage(parent);
            parent.setContent(layout);
            layout.init();
        }
    }
}
