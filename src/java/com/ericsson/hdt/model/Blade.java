/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.model;

/**
 *
 * @author eadrcle
 */
public class Blade {
    
    private int type; 
    
    public Blade(){
		//System.out.println("New Blade");
	}
	
	
	public int getType() {
		
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
		//System.out.println("Blade set...");
	}
	
	
	@Override
	public String toString(){
		if(getType()==1){
			return "Full Height Blade";
                }
                
                else{
			return "Half Height Blade";
                }
	}
    
}
