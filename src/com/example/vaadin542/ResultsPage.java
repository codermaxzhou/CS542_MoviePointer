package com.example.vaadin542;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;

import com.example.vaadin542.model.Model;
import com.example.vaadin542.model.Movie;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Slider;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.OptionGroup;

public class ResultsPage extends ResultsLayout implements ClickListener, ItemClickListener {
    UI parent;
    Window win = new Window();
    Window summaryWindow = new Window();
    VerticalLayout windowContent = new VerticalLayout();
    OptionGroup extOptionGrp1 = new OptionGroup();
    OptionGroup extOptionGrp2 = new OptionGroup();
    OptionGroup extOptionGrp3 = new OptionGroup();
    OptionGroup extOptionGrp4 = new OptionGroup();
    double rating;
    
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
            
            List<String> years = Model.filterYear();
            List<String> genres = Model.filterGenre();
            List<String> directors = Model.filterDirector();
            List<String> actors = Model.filterActor();
            
            if (years.size() > 3) {
                optionGrp1.addItems(years.get(0), years.get(1), years.get(2));
                years.remove(0);
                years.remove(0);
                years.remove(0);
                extOptionGrp1.addItems(years);
            }
            else {
                optionGrp1.addItems(years);
            }
            if (genres.size() > 3) {
                optionGrp2.addItems(genres.get(0), genres.get(1), genres.get(2));
                genres.remove(0);
                genres.remove(0);
                genres.remove(0);
                extOptionGrp2.addItems(genres);
            }
            else {
                optionGrp2.addItems(genres);
            }
            if (directors.size() > 3) {
                optionGrp3.addItems(directors.get(0), directors.get(1), directors.get(2));
                directors.remove(0);
                directors.remove(0);
                directors.remove(0);
                extOptionGrp3.addItems(directors);
            }
            else {
                optionGrp3.addItems(directors);
            }
            if (actors.size() > 3) {
                optionGrp4.addItems(actors.get(0), actors.get(1), actors.get(2));
                actors.remove(0);
                actors.remove(0);
                actors.remove(0);
                extOptionGrp4.addItems(actors);
            }
            else {
                optionGrp4.addItems(actors);
            }
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        gridResults.addItemClickListener(this);
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
            List<Integer> selectedYear = new ArrayList<Integer>();
            selectedYear.addAll((Collection<? extends Integer>) optionGrp1.getValue());
            selectedYear.addAll((Collection<? extends Integer>) extOptionGrp1.getValue());
            
            List<String> selectedGenre = new ArrayList<String>();
            selectedGenre.addAll((Collection<? extends String>) optionGrp2.getValue());
            selectedGenre.addAll((Collection<? extends String>) extOptionGrp2.getValue());
            
            List<String> selectedDirector = new ArrayList<String>();
            selectedDirector.addAll((Collection<? extends String>) optionGrp3.getValue());
            selectedDirector.addAll((Collection<? extends String>) extOptionGrp3.getValue());
            
            List<String> selectedActor = new ArrayList<String>();
            selectedActor.addAll((Collection<? extends String>) optionGrp4.getValue());
            selectedActor.addAll((Collection<? extends String>) extOptionGrp4.getValue());
            rating = sldrRating.getValue();
            
            try {
            Model.generateFilterQuery(selectedYear, selectedGenre, selectedDirector, selectedActor, rating);
            
            gridResults.setContainerDataSource(new BeanItemContainer<>(
                    Movie.class, Model.filter()));
            gridResults.setVisibleColumns("movieID", "movieTitle",
                    "year", "rating");
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
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

    @Override
    public void itemClick(ItemClickEvent event) {
        if(event.isDoubleClick()) {
            BeanItem<Movie> bean = (BeanItem<Movie>)gridResults.getItem(event.getItemId());
            Movie s = (Movie)bean.getBean();
            
            try {
                summaryWindow.setContent(new MovieSummaryPage(s.getMovieID(), s.getMovieTitle(), s.getYear() + "", s.getRating() + "", "$" + s.getBudget(), "$" + s.getRevenue(), "directors", "casts", "genres", s.getOverview(), s.getPosterPath()));
            } catch (ClassNotFoundException | SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            summaryWindow.setPosition(180, 56);
            summaryWindow.setResizable(false);
            summaryWindow.setWidth(760, Unit.PIXELS);
            summaryWindow.setHeight(450, Unit.PIXELS);
            summaryWindow.setModal(true);
            
            UI.getCurrent().removeWindow(summaryWindow);
            UI.getCurrent().addWindow(summaryWindow);
        }
    }
}
