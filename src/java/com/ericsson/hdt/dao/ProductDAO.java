/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.dao;

import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Formula;
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
public interface ProductDAO extends Serializable {
    
    //Usual CRUD interface
    
    public void add(Product p,User user);
    public void addCombinedProduct(Product p,User user);
    public void updateName(Product p,User user);
    public void updateWeight(Product p,User user);
    public void setGA(Product p,User user);
    public void removeGA(Product p,User user);
    public List<Product> getAllProducts();
    public List<Product> getAllVisibileProducts();
    public void delete(Product p,User user);
    
    public int deleteProduct(Product p,User user);
    public Product getCombinedProduct(Product p);
    public Product getIndividualProduct(String name);
    public Product getIndividualProduct(Integer weight);
    
    
    
    
    
    
    
}
