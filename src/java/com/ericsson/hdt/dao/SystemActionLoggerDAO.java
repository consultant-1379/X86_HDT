/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.dao;

import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.UserAction;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public interface SystemActionLoggerDAO {
    
    public void logUserAction(User user,String table,String sql);
    
    public List<UserAction> viewUpdateActions();
    public List<UserAction> viewDeleteActions();
    public List<UserAction> viewAllActions();
    public List<UserAction> viewInsertActions();
    public List<UserAction> viewUserUpdateActions();
    public List<UserAction> viewUserDeleteActions();
    public List<UserAction> viewAllUserActions();
    
}
