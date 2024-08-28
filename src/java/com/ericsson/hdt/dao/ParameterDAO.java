/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.dao;

import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Formula;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Parameter;
import com.ericsson.hdt.model.ParameterType;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public interface ParameterDAO extends Serializable{
    
    public void add(Parameter p,User user);
    public List<Parameter> getListParameter();
    public void addParameterType(String type,User user);
   
    public List<ParameterType> getParameterTypes();
    public List<Parameter> getProductReleaseSubParameter(Product p,Version v,Network net, Bundle b,Parameter par);
    public void update(Parameter p,User user);
    public void delete(Parameter p,User user);
    public Boolean parameterExist(String name);
    public int updateParameterType(Parameter p,User user);
    public Parameter getParameter(int id);
    public Parameter getParameter(String name);
    public int updateParameterSubParameterStatus(Product p, Version v, Network net,Bundle b, Parameter mainParameter,User user);
    public List<Parameter> getProductReleaseParameterList(Product p, Version v, Network net,Bundle b);
    public int updateProductReleaseParameterSubParameterValue(Product p, Version v, Network net,Bundle b, Parameter mainParameter, Parameter subParameter,User user);
    public int updateProductReleaseParameterSubParameterVisibilty(Product p, Version v, Network net,Bundle b, Parameter mainParameter, Parameter subParameter,User user);
    public int updateProductReleaseParameterSubParameterEditability(Product p, Version v, Network net,Bundle b, Parameter mainParameter, Parameter subParameter,User user);
    public int updateProductReleaseParameterValue(Product p, Version v, Network net,Bundle b, Parameter par,User user);
    public int updateProductReleaseParameterVisibilty(Product p, Version v, Network net,Bundle b, Parameter par,User user);
    public int updateProductReleaseParameterEditability(Product p, Version v, Network net,Bundle b, Parameter par,User user);
    public int updateParameterDescription(Parameter p,User user);
    public int addProductParameters(Product p,Version v, Network n, Bundle b,Parameter par,User user);
    public int addProductDetailedVariable(Product p,Version v, Network n, Bundle b, Formula f,String variable,User user);
    public int addProductSubParameter(Product p,Version v, Network n, Bundle b,Parameter mainParameter,Parameter subParameter,User user);
    // Used to check for Deleting a Parameter.
    public Boolean isParameterUsed(Parameter p);
    public Boolean isProductReleaseParameterDefined(Product p,Version v, Network n, Bundle b,Parameter par);
    
    public int deleteProductReleaseParameter(Product p, Version v, Network net,Bundle b, Parameter par,User user);
    public int deleteProductReleaseSubParameter(Product p, Version v, Network net,Bundle b, Parameter mainParameter,Parameter subParameter,User user);
    
    public int updateProductReleaseParameterDisplayOrder(Product p, Version v, Network net,Bundle b, Parameter parameter,User user);
    
    
    
    
}
