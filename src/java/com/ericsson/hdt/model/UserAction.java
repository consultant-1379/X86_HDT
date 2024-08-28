/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.model;

import java.sql.Date;

/**
 *
 * @author eadrcle
 */
public class UserAction  extends User{
    
    private String action;
    private String table;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
    

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    
    public UserAction(String email,String table,String action, Date date){
        super(email);
        this.action = action;
        this.table = table;
        this.date = date;
        
    }
    
    
    
}
