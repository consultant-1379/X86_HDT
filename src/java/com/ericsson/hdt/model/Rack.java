/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public class Rack implements Serializable{
    
    private Integer id;
    private String name;

   
    private int height =700;
    private int width = 225;
    private Site site;
    private List<Component> components = new ArrayList<Component>();
    private Enclosure enclosure;
    private Integer x_pos;
    
    
    public Rack(){
        
        
    }
    

    public Rack(Integer id){
        this.id = id;
        
    }

    public Integer getX_pos() {
        return x_pos;
    }

    public void setX_pos(Integer x_pos) {
        this.x_pos = x_pos;
    }

    public Integer getY_pos() {
        return y_pos;
    }

    public void setY_pos(Integer y_pos) {
        this.y_pos = y_pos;
    }
    private Integer y_pos;

    
    
    
    
    public Enclosure getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
    }
    
    
     public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }
    
    
    public void addComponent(Component c){
        
        components.add(c);
    }
    
    
    
   
    
}
