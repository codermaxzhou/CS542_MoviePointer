package com.example.vaadin542;

import java.util.ArrayList;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class MainPage extends MainLayout implements ClickListener {
	Component[][] filters = new Component[3][4];
	boolean[] isVisible = new boolean[3];
	int lvl = 0;
	UI parent;
	
	public MainPage(UI parent) {
		filters[0][0] = andOr2;
		filters[1][0] = andOr3;
		filters[2][0] = andOr4;
		
		filters[0][1] = txtField2;
		filters[1][1] = txtField3;
		filters[2][1] = txtField4;
		
		filters[0][2] = cmbBox2;
		filters[1][2] = cmbBox3;
		filters[2][2] = cmbBox4;
		
		filters[0][3] = btnMinus2;
		filters[1][3] = btnMinus3;
		filters[2][3] = btnMinus4;
		
		btnAddSrch.addClickListener(this);
		btnMinus2.addClickListener(this);
		btnMinus3.addClickListener(this);
		btnMinus4.addClickListener(this);
		btnSearch.addClickListener(this);
		
		this.parent = parent;
	}
	
	public void init() {
		for(int i = 0; i < 3; ++i) {
			for(int x = 0; x < 4; ++x) {
				filters[i][x].setVisible(false);
			}
		}
	}

	@Override
	public void buttonClick(ClickEvent event) {
		Button origin = event.getButton();
		
		if(origin.equals(btnAddSrch)) {
			for(int i = 0; i < 3; ++i) {
				if(!isVisible[i]) {
					for(int x = 0; x < 4; ++x) {
						isVisible[i] = true;
						filters[i][x].setVisible(true);
					}
					
					break;
				}
			}
		} else if(origin.equals(btnMinus2) || origin.equals(btnMinus3) || origin.equals(btnMinus4)) {
			int index = 0;
			
			if(origin.equals(btnMinus3)) index = 1;
			else if(origin.equals(btnMinus4)) index = 2;
			
			if(isVisible[index]) {
				isVisible[index] = false;
				for(int x = 0; x < 4; ++x) {
					filters[index][x].setVisible(false);
				}
			}
		} else {
	        /** Code to get query list
	         *  the first element is search term 
	         *  the second element is type term
	         *  next elements are [operator] [search] [type]
		    */
            ArrayList<String> query = new ArrayList<>();
            
            query.add(txtField1.getValue());
            query.add((String)cmbBox1.getValue());
            
            for(int i = 0; i < 3; ++i) {
                if(isVisible[i]) {
                    query.add((String)(((ComboBox) filters[i][0]).getValue()));
                    query.add(((TextField) filters[i][1]).getValue());
                    query.add((String)(((ComboBox) filters[i][2]).getValue()));
                }
            }
		    
			parent.setContent(new ResultsPage(query, parent));
		}
	}
}
