/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.interfaces;

import com.ericsson.hdt.model.Blade;
import com.ericsson.hdt.model.Component;
import com.ericsson.hdt.model.Enclosure;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public interface IEnclosure {
    
   public void addBlade(Blade b);
   public void addArrayBlade(List<Component> b);
   public List<Component> getArrayBlade();
   public Enclosure getEnclosure();
    
    
}
