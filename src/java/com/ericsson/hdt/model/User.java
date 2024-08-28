/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public class User implements Serializable{
    
    private String sessionID;
    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
    private Integer role;
    private Boolean loggedOn;

    public Boolean getLoggedOn() {
        return loggedOn;
    }

    public void setLoggedOn(Boolean loggedOn) {
        this.loggedOn = loggedOn;
    }
    List<Parameter> parameterList;

    public List<Parameter> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<Parameter> parameterList) {
        this.parameterList = parameterList;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

   

    
   
    public User() {
        
    }
    
    public User(String email){
        this.email = email;
    }
    public User(String email, String password){
        this.email = email;
        this.password = password;
        
    }
    
    public User(String email,String password, Integer role){
        
        this.email = email;
        this.password = password;
        this.role = role;
        
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
    
    public User(String name, String surname, String email, String username, String password, Integer role) {
        
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
     
    }

    
    
    
    
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
     @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
            return false;
        }
        return true;
    }
    
    
         
    
}
