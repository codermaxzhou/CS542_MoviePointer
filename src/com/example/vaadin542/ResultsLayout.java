package com.example.vaadin542;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Slider;
import com.vaadin.ui.Table;
import com.vaadin.ui.declarative.Design;

/** 
 * !! DO NOT EDIT THIS FILE !!
 * 
 * This class is generated by Vaadin Designer and will be overwritten.
 * 
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { … }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class ResultsLayout extends CssLayout {
    protected Button btnHome;
    protected HorizontalSplitPanel rightBar;
    protected OptionGroup optionGrp1;
    protected Button linkYear;
    protected Label lblYear;
    protected OptionGroup optionGrp2;
    protected Button linkGenre;
    protected OptionGroup optionGrp3;
    protected Button linkDirector;
    protected OptionGroup optionGrp4;
    protected Button linkActor;
    protected Slider sldrRating;
    protected Button btnFilter;
    protected Table gridResults;

    public ResultsLayout() {
        Design.read(this);
        sldrRating.setMax(10);
        sldrRating.setResolution(1);
    }
}
