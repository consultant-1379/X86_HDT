/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.dao;

import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Parameter;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public interface UserDAO extends Serializable{
    
    public User login(User u);
    public void delete(User u);
    public List<User> registeredUsers();
    public Boolean changePassword(User u);
   
    public int saveParameters(User user ,Product p, Version v, Network n, Bundle b, Parameter parameter);
    public Boolean logout(User u);
    //public List<Parameter> getSavedParameters(User user ,Product p, Version v, Network n, Bundle b, String timeStamp);
    public List<Parameter> getUniqueSavedParameter(User user ,Product p, Version v, Network n, Bundle b, String timeStamp);
    public List<Parameter> getUserSelectedParameterSet(User user ,Product p, Version v, Network n, Bundle b);
    public List<Parameter> getUserSavedParameterSet(User user);
    public Boolean register(User u);
    public Boolean deletedUserSavedParameters(User user, String timeStamp);
    public Boolean validEmail(String email);
    public Boolean loggedOn(String email);
    public Boolean updateUserRole(User u);
    
    
    
    
    
    
}
