/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.interfaces;

import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Parameter;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Role;
import com.ericsson.hdt.model.Version;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author eadrcle
 */
public interface IDimensioner extends Serializable{
    
    public Map<String,Object> calculateResult(Product p, Version v, Network n, Bundle b,HttpServletRequest request, HttpSession session);
    public Map<String,Object> calculateIndividualHardware(Product p, Version v, Network n, Bundle b,Map<String,String> parameters,HttpSession session, Role r);
    public Map<String,Object> changeHardwareGeneration(Product p, Version v, Network n, Bundle b,Map<String,String> parameters,HttpSession session);
    public Map<String,Object> reCalculateAllRoles(Product p, Version v, Network n, Bundle b,Map<String,String> parameters,HttpSession session);
    public void updateHardwareAppQty(Map<String,String> parameters,HttpSession session);
    public Map<String,Object> validateProductTestScript(Product p, Version v, Network n, Bundle b,Map<String,String> parameters,HttpServletRequest request, HttpSession session);
    public Map<String,Object> validateProductTestScript(Product p, Version v, Network n, Bundle b,HttpServletRequest request, HttpSession session);
    
    
}
