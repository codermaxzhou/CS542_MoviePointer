package com.example.vaadin542.model;

import java.io.Serializable;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Yihao
 */
public class Director implements Serializable, Cloneable {
    private int directorID;
    private String name;
    private String profilePath;

    /**
     * @return the directorID
     */
    public int getDirectorID() {
        return directorID;
    }

    /**
     * @param directorID the directorID to set
     */
    public void setDirectorID(int directorID) {
        this.directorID = directorID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the profilePath
     */
    public String getProfilePath() {
        return profilePath;
    }

    /**
     * @param profilePath the profilePath to set
     */
    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
    
    @Override
    public Director clone() throws CloneNotSupportedException {
        try {
            return (Director) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }

    @Override
    public String toString() {
        return "TODO";
    }
}
