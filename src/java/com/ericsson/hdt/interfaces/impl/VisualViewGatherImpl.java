/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.interfaces.impl;

import com.ericsson.hdt.interfaces.IVisualViewGather;
import com.ericsson.hdt.model.Component;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public class VisualViewGatherImpl implements IVisualViewGather {
    
    private List<Component> componentList ;
	private List<Component> blades;
	private List<Component> fullHeights;
	private List<Component> halfHeights;
	private Component component;
	private String type;
	private String productName;
	private String tempVal;
	private int numberEnclosures;
	private int numberRacks = 1;
        private int rackSizeInUnits = 40;
        
        
        public VisualViewGatherImpl(List<Component> com){
		this.componentList = com;
                
	}
        

    @Override
    public List<Component> getComponents() {
        
        return componentList;
        
    }

    @Override
    public List<Component> getBlades() {
        
        Iterator<Component> i = componentList.iterator();
            while(i.hasNext()){
                Component com = i.next();
                if(com.getType().getType().equalsIgnoreCase("full_height") || com.getType().getType().equalsIgnoreCase("half_height")){
                    blades.add(com);
                    
                }
                
                
            }
        
            
            return blades;
    }

    @Override
    public List<Component> getFullHeights() {
        
        fullHeights = new ArrayList<Component>();
		Iterator<Component> i = componentList.iterator();
		while(i.hasNext()){
			Component b = i.next();
			if(b.getType().getType().equalsIgnoreCase("full_height")){
				fullHeights.add(b);
				
			}
		}
		
		return fullHeights;
        
    }

    @Override
    public List<Component> getHalfHeights() {
        
        halfHeights = new ArrayList<Component>();
		Iterator<Component> i = componentList.iterator();
		while(i.hasNext()){
			Component b = i.next();
			if(b.getType().getType().equalsIgnoreCase("half_height")){
				halfHeights.add(b);
				
			}
		}
		return halfHeights;
        
    }

    @Override
    public int getNumberEnclosures() {
        
        int totalNumberBlades = 0 ;		

		Iterator<Component> i = componentList.iterator();
		
		while(i.hasNext()){
                    Component c = i.next();
			
				if(c.getType().getType().equalsIgnoreCase("full_height")){
						totalNumberBlades+= 2;
				
				}	
                                else if(c.getType().getType().equalsIgnoreCase("half_height")) {
				
					totalNumberBlades+= 1;
				
				}
			
			
		}
		
		
		if((totalNumberBlades%16)!=0){
			numberEnclosures = (int)(totalNumberBlades/16) + 1;
  	
		}
		else{
			numberEnclosures = (int)(totalNumberBlades/16);
		}
		
		
		return numberEnclosures;
        
    }

    @Override
    public int getNumberRacks() throws SQLException {
        int count = 2;  // 2 rack units left for Ehternet Switch by Default
                Iterator<Component> i = componentList.iterator();
                while(i.hasNext()){
                    Component com = i.next();
                    count += com.getUnits();
                    
                    
                }
            
		
           
                
		
            return (count  /rackSizeInUnits) + 1;
        
    }

    @Override
    public int getProduct_id() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
