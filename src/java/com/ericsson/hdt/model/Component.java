/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.model;

import java.io.Serializable;

/**
 *
 * @author eadrcle
 */
public class Component implements Serializable {
    
    private String name;
    private Integer units;
    private Boolean hasDependant;
    private ComponentType type;
    private APPNumber appNumber;
    private Integer height;
    private Integer width;
    private Integer x_pos;
    private Integer y_pos;
    private Integer rack_id = 0;
    private Integer startPosition;
    private Site site;

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

     public Component(Integer rackID,String name,APPNumber appNumber,Integer start_pos,Site site){
        
        this.rack_id = rackID;
        this.name = name;
        this.startPosition = start_pos;
        this.site = site;
        this.appNumber = appNumber;
    }
    
    public Component(Integer rackID,String name,Integer start_pos,Site site){
        
        this.rack_id = rackID;
        this.name = name;
        this.startPosition = start_pos;
        this.site = site;
    }

    public Component(String name) {
        this.name = name;
    }
    
    public Component(String name,Integer unit,Boolean hasDependant) {
        this.name = name;
        this.units = unit;
        this.hasDependant = hasDependant;
    }
    
    public Component(String name, APPNumber app, Integer units, ComponentType type, Boolean hasDependant){
        this.name = name;
        this.units = units;
        this.hasDependant = hasDependant;
        this.appNumber = app;
        this.type = type;
        
    }
    
    public Component() {
     
    }
    
    public Integer getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Integer startPosition) {
        this.startPosition = startPosition;
    }
    
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
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

    public Integer getRack_id() {
        return rack_id;
    }

    public void setRack_id(Integer rack_id) {
        this.rack_id = rack_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public Boolean getHasDependant() {
        return hasDependant;
    }

    public void setHasDependant(Boolean hasDependant) {
        this.hasDependant = hasDependant;
    }

   

    public APPNumber getAppNumber() {
        return appNumber;
    }

    public void setAppNumber(APPNumber appNumber) {
        this.appNumber = appNumber;
    }
    
    
    public ComponentType getType() {
        return type;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }
    
}
