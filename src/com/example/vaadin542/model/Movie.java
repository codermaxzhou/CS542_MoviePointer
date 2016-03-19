package com.example.vaadin542.model;

import java.io.Serializable;
import org.apache.commons.beanutils.BeanUtils;
import java.util.Date;

/**
 *
 * @author Yihao
 */
public class Movie implements Serializable, Cloneable {
    private int movieID;
    private String movieTitle;
    private Date releaseDate;
    private String overview;
    private int budget;
    private int revenue;
    private int runtime;
    private String posterPath;
    private float rating;
    private int numberOfRating;
    private int year;
    
    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the movieID
     */
    public int getMovieID() {
        return movieID;
    }

    /**
     * @param movieID the movieID to set
     */
    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    /**
     * @return the movieTitle
     */
    public String getMovieTitle() {
        return movieTitle;
    }

    /**
     * @param movieTitle the movieTitle to set
     */
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    /**
     * @return the releaseDate
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * @param releaseDate the releaseDate to set
     */
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * @return the overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     * @param overview the overview to set
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * @return the budget
     */
    public int getBudget() {
        return budget;
    }

    /**
     * @param budget the budget to set
     */
    public void setBudget(int budget) {
        this.budget = budget;
    }

    /**
     * @return the revenue
     */
    public int getRevenue() {
        return revenue;
    }

    /**
     * @param revenue the revenue to set
     */
    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    /**
     * @return the runtime
     */
    public int getRuntime() {
        return runtime;
    }

    /**
     * @param runtime the runtime to set
     */
    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    /**
     * @return the posterPath
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * @param posterPath the posterPath to set
     */
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    /**
     * @return the rating
     */
    public float getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(float rating) {
        this.rating = rating;
    }

    /**
     * @return the numberOfRating
     */
    public int getNumberOfRating() {
        return numberOfRating;
    }

    /**
     * @param numberOfRating the numberOfRating to set
     */
    public void setNumberOfRating(int numberOfRating) {
        this.numberOfRating = numberOfRating;
    }
    
    @Override
    public Movie clone() throws CloneNotSupportedException {
        try {
            return (Movie) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }

    @Override
    public String toString() {
        return "TODO";
    }
    
}


