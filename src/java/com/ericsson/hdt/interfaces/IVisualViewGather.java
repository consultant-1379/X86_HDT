/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.interfaces;

import com.ericsson.hdt.model.Component;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public interface IVisualViewGather {
    
    public List<Component> getComponents();
	public List<Component> getBlades();
	public List<Component> getFullHeights();
	public List<Component> getHalfHeights();
	public int getNumberEnclosures();
	public int getNumberRacks() throws SQLException;
	public int getProduct_id();
}
