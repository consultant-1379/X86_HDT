/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.interfaces;

import com.ericsson.hdt.model.Component;
import com.ericsson.hdt.model.Enclosure;
import com.ericsson.hdt.model.Rack;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public interface IRack {
    
    public void addComponent(Component s);
	public void configureRackDimension(ArrayList<Component> c, Enclosure e);
	public void addEnclosure(Enclosure e);
	public List<Component> getComponents();
	public Rack getRack();
    
}
