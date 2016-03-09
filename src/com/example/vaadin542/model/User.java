package com.example.vaadin542.model;

import java.io.Serializable;
import org.apache.commons.beanutils.BeanUtils;

public class User implements Serializable, Cloneable {
    private int userID;
    private String userName;
    private String userType;
    
    public int getID() {
        return userID;
    }
    
    public void setID(int id) {
        userID = id;
    }
    
    public String getName() {
        return userName;
    }
    
    public void setName(String name) {
        userName = name;
    }
    
    public String getType() {
        return userType;
    }
    
    public void setType(String type) {
        userType = type;
    }
    
    @Override
    public User clone() throws CloneNotSupportedException {
        try {
            return (User) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }

    @Override
    public String toString() {
        return "User{" + "userid=" + userID + ", username=" + userName
                     + ", usertype=" + userType + '}';
    }
}
