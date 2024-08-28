/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.interfaces.impl;

import com.ericsson.hdt.interfaces.IEnclosure;
import com.ericsson.hdt.model.Blade;
import com.ericsson.hdt.model.Component;
import com.ericsson.hdt.model.Enclosure;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public class EnclosureImpl implements IEnclosure {
    
    private Enclosure enclosure = new Enclosure();

    @Override
    public void addBlade(Blade b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addArrayBlade(List<Component> b) {
        
        enclosure.setBlades(b);
    }

    @Override
    public List<Component> getArrayBlade() {
        return enclosure.getBlades();
    }

    @Override
    public Enclosure getEnclosure() {
        return this.enclosure;
    }
    
}
