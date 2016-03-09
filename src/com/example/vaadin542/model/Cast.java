package com.example.vaadin542.model;

import java.io.Serializable;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Yihao
 */
public class Cast implements Serializable, Cloneable {
    private int castID;
    private String name;
    private String profilePath;

    /**
     * @return the castID
     */
    public int getCastID() {
        return castID;
    }

    /**
     * @param castID the castID to set
     */
    public void setCastID(int castID) {
        this.castID = castID;
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
    public Cast clone() throws CloneNotSupportedException {
        try {
            return (Cast) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }

    @Override
    public String toString() {
        return "TODO";
    }
    
}
