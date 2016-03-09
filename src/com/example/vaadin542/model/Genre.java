package com.example.vaadin542.model;

import java.io.Serializable;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Yihao
 */
public class Genre implements Serializable, Cloneable {
    private int genreID;
    private String genreName;

    /**
     * @return the genreID
     */
    public int getGenreID() {
        return genreID;
    }

    /**
     * @param genreID the genreID to set
     */
    public void setGenreID(int genreID) {
        this.genreID = genreID;
    }

    /**
     * @return the genreName
     */
    public String getGenreName() {
        return genreName;
    }

    /**
     * @param genreName the genreName to set
     */
    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
    
    @Override
    public Genre clone() throws CloneNotSupportedException {
        try {
            return (Genre) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }

    @Override
    public String toString() {
        return "TODO";
    }
    
}

