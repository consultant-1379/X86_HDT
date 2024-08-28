/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.interfaces.impl;

import com.ericsson.hdt.interfaces.IRack;
import com.ericsson.hdt.model.Component;
import com.ericsson.hdt.model.Enclosure;
import com.ericsson.hdt.model.Rack;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public class RackImpl implements IRack {
    
    private Rack rack  = new Rack();

    @Override
    public void addComponent(Component c) {
        rack.addComponent(c);
    }

    @Override
    public void configureRackDimension(ArrayList<Component> c, Enclosure e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addEnclosure(Enclosure e) {
        rack.setEnclosure(e);
    }

    @Override
    public List<Component> getComponents() {
      return rack.getComponents();
        
    }

    @Override
    public Rack getRack() {
        
        
        return this.rack;
    }
    
}
