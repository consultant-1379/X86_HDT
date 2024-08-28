/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.interfaces;

import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Component;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Version;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public interface IVisualViewCreator {
    
        public void positionComponent(List<Component> s,Product p, Version v, Network n, Bundle b) throws SQLException;
	public void positionBlade();
	public String createSVG();
    
}
