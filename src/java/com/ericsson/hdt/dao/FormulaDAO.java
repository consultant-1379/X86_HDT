/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.dao;

import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Formula;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Role;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public interface FormulaDAO extends Serializable{
    
    public void add(Formula f,User user);
    public List<Formula> getFormula(Formula f);
    public List<Formula> getAllFormula();
    public int delete(Formula f, User user);
    public int updateFormulaCode(Formula f,User user);
    //public int updateProductReleaseFormulaName(Formula oldFormula,Formula newFormula,User user);
    //public int updateProductReleaseRoleFormulaName(Formula oldFormula, Formula newFormula, User user);
    public int renameFomulaName(Formula oldFormula, Formula newFormula,User user);
    public List<Formula> getAssignedFormulas();
    
    public Formula getIndividualFormula(String name);
    public Boolean isExist(Formula f);
    public Formula getFormulaPerRole(Product p,Version v, Network n, Bundle b,Role r);
    public Formula getProductReleaseTestScript(Product p,Version v, Network n, Bundle b);
    public Formula getProductSystemDetailsScript(Product p,Version v, Network n);
    
}
