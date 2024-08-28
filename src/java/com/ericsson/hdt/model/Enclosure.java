/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public class Enclosure implements Serializable{
    
    private String name;
    private List<Component> blades;
    private int height;
    private int width;
    private int x_pos;
    private int y_pos;
    
    public Enclosure(){
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Component> getBlades() {
        return blades;
    }

    public void setBlades(List<Component> blades) {
        this.blades = blades;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX_pos() {
        return x_pos;
    }

    public void setX_pos(int x_pos) {
        this.x_pos = x_pos;
    }

    public int getY_pos() {
        return y_pos;
    }

    public void setY_pos(int y_pos) {
        this.y_pos = y_pos;
    }
    
    
    @Override
   public String toString(){
       
       return this.name;
   }
    
    
    
    
    
}
