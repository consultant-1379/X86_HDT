/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.dao;

import com.ericsson.hdt.model.APPNumber;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Component;
import com.ericsson.hdt.model.ComponentType;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Rack;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public interface ComponentDAO  extends Serializable {
    
    
    public int add(Component c,User user);
    public int delete(Component c,User user);
    public int updateUnits(Component newComponent,User user);
    public int updateName(Component oldComponent,Component newComponent,User user);
    public int updateType(Component newComponent,User user);
    public int addComponentType(ComponentType c, User user);
    public List<ComponentType> getComponentTypes();
    public List<Component> getComponents();
    public List<Component> getComponentDependants(Component component,Product p, Version v, Network n, Bundle b);
    public List<Component> getRackMountableComponents();
    public List<Component> getRackComponent(Product p, Version v, Network n, Bundle b,Rack r);
    public Component getComponent(Component c);
    public Boolean isAppNumberUsed(APPNumber app);
    public Component findComponentByAPPNumber(APPNumber app);
    public int addComponentDependant(Component component, Component dependant,User user);
    public int updateComponentDependantStatus(Component component,User user);
    
    
    
}
