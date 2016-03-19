package com.example.vaadin542;

import java.sql.SQLException;
import java.util.ArrayList;

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

        
        extOptionGrp1.addItems(new String[] { "year 1", "year 2", "year 3" });
        extOptionGrp2.addItems(new String[] { "genre1", "genre2", "genre3" });
        extOptionGrp3.addItems(
                new String[] { "director1", "director2", "director3" });
        extOptionGrp4.addItems(new String[] { "actor1", "actor2", "actor3" });

        win.setContent(windowContent);

        btnHome.addClickListener(this);
        linkYear.addClickListener(this);
        linkGenre.addClickListener(this);
        linkActor.addClickListener(this);
        linkDirector.addClickListener(this);

        this.parent = parent;

        try {
            gridResults.setContainerDataSource(new BeanItemContainer<>(
                    Movie.class, Model.search(query)));
            gridResults.setVisibleColumns("movieID", "movieTitle",
                    "releaseDate", "rating");
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
