package com.example.vaadin542;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.vaadin542.model.Model;
import com.example.vaadin542.model.Movie;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.OptionGroup;

public class ResultsPage extends ResultsLayout implements ClickListener {
    UI parent;
    Window win = new Window();
    VerticalLayout windowContent = new VerticalLayout();
    OptionGroup extOptionGrp1 = new OptionGroup();
    OptionGroup extOptionGrp2 = new OptionGroup();
    OptionGroup extOptionGrp3 = new OptionGroup();
    OptionGroup extOptionGrp4 = new OptionGroup();

    public ResultsPage(ArrayList<String> query, UI parent) {
        optionGrp1.setMultiSelect(true);
        optionGrp2.setMultiSelect(true);
        optionGrp3.setMultiSelect(true);
        optionGrp4.setMultiSelect(true);

        extOptionGrp1.setMultiSelect(true);
        extOptionGrp2.setMultiSelect(true);
        extOptionGrp3.setMultiSelect(true);
        extOptionGrp4.setMultiSelect(true);

        extOptionGrp1.setStyleName("v-select-optiongroup-hor");
        extOptionGrp2.setStyleName("v-select-optiongroup-hor");
        extOptionGrp3.setStyleName("v-select-optiongroup-hor");
        extOptionGrp4.setStyleName("v-select-optiongroup-hor");

        win.setContent(windowContent);

        btnHome.addClickListener(this);
        linkYear.addClickListener(this);
        linkGenre.addClickListener(this);
        linkActor.addClickListener(this);
        linkDirector.addClickListener(this);
        btnFilter.addClickListener(this);

        this.parent = parent;

        try {
            Model.generateQuery(query);
            gridResults.setContainerDataSource(new BeanItemContainer<>(
                    Movie.class, Model.search()));
            gridResults.setVisibleColumns("movieID", "movieTitle",
                    "year", "rating");
            List<Integer> years = Model.filterYear();
            List<String> genres = Model.filterGenre();
            List<String> directors = Model.filterDirector();
            List<String> actors = Model.filterActor();
            extOptionGrp1.addItems(years);
            extOptionGrp2.addItems(genres);
            extOptionGrp3.addItems(directors);
            extOptionGrp4.addItems(actors);
            if (years.size() >= 3) optionGrp1.addItems(years.get(0), years.get(1), years.get(2));
            else optionGrp1.addItems(years);
            if (genres.size() >= 3) optionGrp2.addItems(genres.get(0), genres.get(1), genres.get(2));
            else optionGrp2.addItems(genres);
            if (directors.size() >= 3) optionGrp3.addItems(directors.get(0), directors.get(1), directors.get(2));
            else optionGrp3.addItems(directors);
            if (actors.size() >= 3) optionGrp4.addItems(actors.get(0), actors.get(1), actors.get(2));
            else optionGrp4.addItems(actors);
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
        
        else if (origin.equals(btnFilter)) {
            System.out.println(optionGrp1.getValue());
        }   

        else {
            windowContent.removeAllComponents();

            if (origin.equals(linkYear))
                windowContent.addComponent(extOptionGrp1);
            else if (origin.equals(linkGenre))
                windowContent.addComponent(extOptionGrp2);
            else if (origin.equals(linkDirector))
                windowContent.addComponent(extOptionGrp3);
            else
                windowContent.addComponent(extOptionGrp4);

            win.setPositionY(56);
            win.setResizable(false);
            win.setWidth(rightBar.getSplitPosition(), rightBar.getWidthUnits());
            win.setHeight(85f, Unit.PERCENTAGE);

            UI.getCurrent().removeWindow(win);
            UI.getCurrent().addWindow(win);
        }
    }
}
