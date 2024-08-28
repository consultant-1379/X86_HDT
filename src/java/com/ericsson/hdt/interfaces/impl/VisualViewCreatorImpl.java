/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.interfaces.impl;

import com.ericsson.hdt.dao.RackDAO;
import com.ericsson.hdt.impl.RackDAOImpl;
import com.ericsson.hdt.interfaces.IEnclosure;
import com.ericsson.hdt.interfaces.IRack;
import com.ericsson.hdt.interfaces.IVisualViewCreator;
import com.ericsson.hdt.interfaces.IVisualViewGather;
import com.ericsson.hdt.model.APPNumber;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Component;
import com.ericsson.hdt.model.ComponentType;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Version;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author eadrcle
 */
public class VisualViewCreatorImpl  implements IVisualViewCreator,Serializable{
    
    protected final Log logger = LogFactory.getLog(getClass());
        
        private IVisualViewGather visualViewGather;
	private IRack[] rack;
	private IEnclosure [] enclosure;
	private List<Component> configuredBlades;
	private Boolean toggleFH= true, toggleHH = true;
	private String filename;
        String svgContent = "";
        int rackSlotSize =36;
        int [] rackSlot = new int [rackSlotSize];
        
        
        
        
        public VisualViewCreatorImpl(List<Component> com,Product p, Version v, Network n, Bundle b,String filename){
        try {            
            start(com,p,v,n,b);
            this.filename = filename;
        } catch (SQLException ex) {
            Logger.getLogger(VisualViewCreatorImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        }

	

        public void initilizeRackSlot(){
            
            for(int i=0;i<rackSlotSize; i++){
                if(i<10){
                    rackSlot[i]=1;
                    
                }
                else{
                    
                    rackSlot[i] = 0;
                    
                }
                 
                
            }
            
            
        }
        
	public void start(List<Component> c,Product p, Version v, Network n, Bundle b) throws SQLException{
		
		visualViewGather = new VisualViewGatherImpl(c);
		
		initilizeRackSlot();
		setupEnclosure();
		positionBlade();
		positionComponent(visualViewGather.getComponents(),p,v,n,b);
		
		
		
	}
    

    @Override
    public void positionComponent(List<Component> com, Product p, Version v, Network n, Bundle b) throws SQLException {
       
        int rowCounter = 1;
        List<Component> rackComponentLayout = null;
        Iterator<Component> ik;
        RackDAO rackDAO = new RackDAOImpl();
        rowCounter = rackDAO.getRowCount(p, v, n, b);
        
        
        if(rowCounter>0){
            
            // Return list of component
                rackComponentLayout = rackDAO.getRackLayoutForProductRelease(p, v, n, b);
                
                ik = rackComponentLayout.iterator();
                
                
                // Convert results set into 2 dimensional array.
                    String [][] table = new String[rowCounter][3];
                    
                    Integer i;
                    for(int j=0; j<rowCounter;j++){
                                if(ik.hasNext()){
                                    
                                        Component component = ik.next();
                                        
                                        //table[j][0] = component.getName();
                                        table[j][0] = component.getAppNumber().getId().toString();
                                        i = component.getRack_id();
                                        table[j][1] = i.toString();
                                        i = component.getStartPosition();
                                        table[j][2] = i.toString();
                                        
                                        
                                        
                                }
                                
                   }
                    
                    Iterator<Component> c = com.iterator(); // List of App's from the HDT Engine
                    while(c.hasNext()){
                            Component comp = c.next();
                            APPNumber app = comp.getAppNumber();
                            
                            for(int k=0;k<rowCounter;k++){
                                        // Components rack Id is set to 0 by default to show that this component isn't assigned to any rack yet. If it is assigned already skip this component. This
                                            // allows for duplicate component in a rack such as DAE's, NAS Head etc.
                                            
                                            if(comp.getAppNumber().getId().toString().equals(table[k][0]) && comp.getRack_id()==0){ 
                                                    
                                                    
                                                    comp.setRack_id(Integer.parseInt(table[k][1])); 
                                                    comp.setStartPosition(Integer.parseInt(table[k][2]));
                                                    //fillFreeRackSlot(comp.getStartPosition(),comp.getUnits());
                                                    table[k][0]="";
                                                    table[k][1]="";
                                                    table[k][2]="";
                                                    
                                                    
                                            }
                                            
                                    }
                                    
                            
                            
                    }
            
            
                    
                    
            setupRack(com); 	
             
             
             
             // This added each component to the rack(s) as defined in the database. 
             c = com.iterator();
             while(c.hasNext()){
                    
                    Component comp = c.next();
                    //System.out.println("Name:" + comp.getName() + " Rack Units:" + comp.getRackUnits() + " Rack id:" + comp.getRack_id() + " Rack Pos:" + comp.getRack_pos());
                    if(comp.getRack_id()!=0) // This component is not rack mounted so must be Blades that is housed inside the enclosure.
                    rack[comp.getRack_id()-1].addComponent(comp);
                                    
            } 
             
        }
            
        
        
        
        
        
        
    }

    @Override
    public void positionBlade() {
       
        List<Component> FH = visualViewGather.getFullHeights();
		List<Component> HH = visualViewGather.getHalfHeights();
                ComponentType FullHeight = new ComponentType("full_height");
                ComponentType HalfHeight = new ComponentType("half_height");
		
		Component blade;
		
		
			for(int l=0;l<enclosure.length;l++){
			
				configuredBlades =  new ArrayList<Component>();
				
				for(int k=0; k <4; k++){    // 4 Quadrants
					
					if((FH.size()>0) && toggleFH){

							for(int j=0;j<2;j++){    // 2 FH Per Quandrant
	
									if(FH.size()<1){
											blade = new Component();
											blade.setName("blank");
											blade.setType(FullHeight);
											configuredBlades.add(blade);
									}
									else{
											configuredBlades.add(FH.get(0));
											FH.remove(0);

									}
		
							}
	
							if(HH.size()>0){
								toggleHH = true;
								toggleFH = false;
							}
					
			
					}
					
					else if((HH.size()>0)&& toggleHH){

   							for(int i=0; i<4;i++){  // 4 HH per Quandrant
									
									if(HH.size()<1){
										blade = new Component();
										blade.setName("blank");
										blade.setType(HalfHeight);
										configuredBlades.add(blade);
			
									}
									else{
		
										configuredBlades.add(HH.get(0));
										HH.remove(0);

									}
		
							}
	
							
							if(FH.size()>0){
								toggleFH = true;  
								toggleHH = false;
							}
					
			    
					}
	
					if(FH.size()==0 && HH.size()==0)
							k=4;  // If all blades are configured break out of loop early

					

			}   // End Quadrants
			
				
				enclosure[l].addArrayBlade(configuredBlades);	
				
	    }  // End of Enclosure Configuration
		
	
        
    }

    @Override
    public String createSVG() {
        
                List<Component> blades;
		List<Component> components;
		String path= "resources/images/rack/";
                
		String blank="";
		String SVGHeader ="<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"no\"?>" 
				   + "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 20010904//EN\" \"http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd\">" 
						+ "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" ";
		

		int count = 0;
		int x_pos = 0,x_pos1= 0 ;
		int counter = 0; 
                int width = (rack.length * (375)); // Number racks by width of rack and text dimension.
		if(rack.length>0){
                    int txtdimension=0;
                	SVGHeader = SVGHeader + " height=\"800\" width=\"" + width + "\" >" ;
                        svgContent += SVGHeader;
				
				for(int i=0;i<rack.length;i++){
								// If more than one rack move txt over another 150px for alignment.
								
                                    if(i>0)
									
                                        txtdimension = 150;
								//pw.println("<rect x=\"10\" y=\"10\" width=\"" + ((rack.length * 225) + (15 * rack.length)) + "\" height=\"850\" />");
                                                                //svgContent +="<rect x=\"10\" y=\"10\" width=\"" + ((rack.length * 225) + (15 * rack.length)) + "\" height=\"850\" />";
					 			
                                    x_pos = (int) ((i* rack[i].getRack().getWidth())+(txtdimension * i));
					
					 			//pw.println("<image x=\"" + x_pos + "\"  y=\"10\" height=\"" + rack[0].getRack().getHeight() + "\" width=\"" + rack[i].getRack().getWidth() + "\" xlink:href=\"" + path + "BYB504.png\" />");
                                                                
                                    svgContent +="<image x=\"" + x_pos + "\"  y=\"10\" height=\"" + rack[0].getRack().getHeight() + "\" width=\"" + rack[i].getRack().getWidth() + "\" xlink:href=\"" + path + "BYB504.png\" />";
					
								
                                    if(i < enclosure.length) {
									
                                        counter = 0 ;
									
                                        blades = enclosure[i].getArrayBlade();
									
									
                                        Iterator<Component> b = blades.iterator();
									//	Prints Enclosure Frame
									
									
                                        x_pos1 = (x_pos + 20) ; // 20 Thick of Rack frame
                                        
                                        
                                                                        
                                        svgContent +="<image x=\"" + x_pos1 + "\" y=\"495\" width=\"184\" height=\"170\" xlink:href=\"" + path + "C7000.png\" />";
									
									
                                        while(b.hasNext()){
										
                                            Component blade = b.next();
										  
                                            if(blade.getType().getType().equalsIgnoreCase("BLANK"))
											  
                                                blank = "BLANK";
										  
                                            else 
											  
                                                blank="";
										  
										
                                            if(blade.getType().getType().equalsIgnoreCase("full_height")){
											      //pw.println("<image x=\"" + (((8 + x_pos1) + (counter*21))) + "\" y=\"500\" width=\"21\" height=\"138\" xlink:href=\"" + path + blade.getName() + ".png\" />");
                                                                                              
                                                svgContent +="<image x=\"" + (((8 + x_pos1) + (counter*21))) + "\" y=\"500\" width=\"21\" height=\"138\" xlink:href=\"" + path + blade.getName() + ".png\" />";
											      counter++;
										
                                            }
										
                                            else{
								
												
                                                if(count==0){
													
													//pw.println("<image x=\"" + (((8 + x_pos1) + (counter*21))) + "\" y=\"500\" width=\"21\" height=\"69\" xlink:href=\"" + path + blade.getName()  + ".png\" />");
                                                                                                        
                                                    svgContent +="<image x=\"" + (((8 + x_pos1) + (counter*21))) + "\" y=\"500\" width=\"21\" height=\"69\" xlink:href=\"" + path + blade.getName()  + ".png\" />";
													
												
                                                }
												
                                                else{ 
													
													//pw.println("<image x=\"" + (((8 + x_pos1) + (counter*21))) + "\" y=\"569\" width=\"21\" height=\"69\" xlink:href=\"" + path + blade.getName()  + ".png\" />");
                                                                                                        
                                                    svgContent +="<image x=\"" + (((8 + x_pos1) + (counter*21))) + "\" y=\"569\" width=\"21\" height=\"69\" xlink:href=\"" + path + blade.getName() + ".png\" />";
													
												
                                                }
												
                                                count ++ ;
												
                                                if(count ==2){
													
                                                    counter++;
													
                                                    count = 0;
												
                                                }
							
										
                                            }  // end else
						
						
									
                                        } // end while
									
/*******************************************************************************************************/
/*** Creates the text and draw the line for each component and blade in the rack.  					****/
/*******************************************************************************************************/									
				
					
									
									
                                        counter = 0 ;
									
                                        blades = enclosure[i].getArrayBlade();
									
                                        Iterator<Component> b1 = blades.iterator();
									
									
                                        while(b1.hasNext()){
										
										
										
                                            Component blade = b1.next();
										  
                                            if(blade.getName().equalsIgnoreCase("BLANK"))
											  
                                                blank = "BLANK";
										  
                                            else 
											  
                                                blank="";
										  
										
                                            if(blade.getType().getType().equalsIgnoreCase("full_height")){
											      
											      //pw.println("<text x=\"" + ((20 + rack[i].getRack().getWidth()) + x_pos) + "\" y=\"" + (510 + (10 * counter)) + "\" style=\"fill: #000000\">" + blade.getName() + "</text>" );
                                                                                              
                                                svgContent +="<text x=\"" + ((20 + rack[i].getRack().getWidth()) + x_pos) + "\" y=\"" + (510 + (11 * counter)) + "\" style=\"fill: #000000\">" + blade.getName() +  "</text>";
                                                                                              //pw.println("<line x1=\"" + ((x_pos1 + 20)+(counter*21)) +  "\" y1=\"" + (500 + (10 * counter)) +   "\" x2=\""  + ((23 + rack[i].getRack().getWidth())+ x_pos) + "\"  y2=\"" +  (505 + (10 * counter)) +  "\" style=\"stroke: #00ff00\"/>" );
                                                                                              
                                                svgContent +="<line x1=\"" + ((x_pos1 + 20)+(counter*21)) +  "\" y1=\"" + (500 + (10 * counter)) +   "\" x2=\""  + ((23 + rack[i].getRack().getWidth())+ x_pos) + "\"  y2=\"" +  (505 + (10 * counter)) +  "\" style=\"stroke: #00ff00\"/>";
						
                                                counter++;
						
                                            }
					
                                            else{
						
                                                if(count==0){
						
                                                    
						
                                                    //pw.println("<text x=\"" + ((20 + rack[i].getRack().getWidth()) + x_pos) + "\" y=\"" + (510 + (10 * counter)) + "\" style=\"fill: #000000\">" + blade.getName() + "</text>" );
                                                
                                                    svgContent +="<text x=\"" + ((20 + rack[i].getRack().getWidth()) + x_pos) + "\" y=\"" + (510 + (11 * counter)) + "\" style=\"fill: #000000\">" + blade.getName() + "</text>";
													  //pw.println("<line x1=\"" + ((x_pos1+ 20)+(counter*21)) +  "\" y1=\"" + (500 + (8 * counter)) +   "\" x2=\""  + ((23 + rack[i].getRack().getWidth())+ x_pos) + "\"  y2=\"" +  (505 + (10 * counter)) +  "\" style=\"stroke: #00ff00\"/>" );
						
                                                    svgContent +="<line x1=\"" + ((x_pos1+ 20)+(counter*21)) +  "\" y1=\"" + (500 + (8 * counter)) +   "\" x2=\""  + ((23 + rack[i].getRack().getWidth())+ x_pos) + "\"  y2=\"" +  (505 + (10 * counter)) +  "\" style=\"stroke: #00ff00\"/>";
						
                                                }
						
                                                else{ 
													
													
													//pw.println("<text x=\"" + ((20 + rack[i].getRack().getWidth()) + x_pos) + "\" y=\"" + (579 + (10 * counter)) + "\" style=\"fill: #000000\">" + blade.getName() + "</text>" );
                                                
                                                    svgContent +="<text x=\"" + ((20 + rack[i].getRack().getWidth()) + x_pos) + "\" y=\"" + (589 + (11 * counter)) + "\" style=\"fill: #000000\">" + blade.getName() + "</text>";
													//pw.println("<line x1=\"" + ((x_pos1+ 20)+(counter*21)) +  "\" y1=\"" + (569 + (8 * counter)) +   "\" x2=\""  + ((23 + rack[i].getRack().getWidth())+ x_pos) + "\"  y2=\"" +  (574 + (10 * counter)) +  "\" style=\"stroke: #00ff00\"/>" );
						
                                                    svgContent +="<line x1=\"" + ((x_pos1+ 20)+(counter*21)) +  "\" y1=\"" + (569 + (8 * counter)) +   "\" x2=\""  + ((23 + rack[i].getRack().getWidth())+ x_pos) + "\"  y2=\"" +  (584 + (10 * counter)) +  "\" style=\"stroke: #00ff00\"/>";
						
                                                }
						
                                                count ++ ;
						
                                                if(count ==2){
						
                                                    counter++;
						
                                                    count = 0;
						
                                                }
							
						
                                            }  // end else
		
										
					
                                        }  // end while
					
					
                                    }  // end if
								
					
				
                                    components = rack[i].getComponents();
				
                                    Iterator<Component> c = components.iterator();
									//x_pos = (int) (20 + (i* rack[i].getRack().getWidth()));
				
                                    x_pos = x_pos +20;
                                                                        
				
                                    while(c.hasNext()){
				
                                        Component component = c.next();
                                        
                                        int y_pos = 665 - (component.getStartPosition() * 17) - (component.getUnits()*17);
                                        
                                                                        
					
                                        if(!component.getName().equals("C7000")) {
											//pw.println("<image x=\"" + x_pos + "\" y=\"" + y_pos +  "\" width=\"184\" height=\"" + (component.getRackUnits()*17) +  "\" xlink:href=\"" + path + component.getName() +".png\" />");
                                        
                                            svgContent +="<image x=\"" + x_pos + "\" y=\"" + y_pos +  "\" width=\"184\" height=\"" + (component.getUnits()*17) +  "\" xlink:href=\"" + path + component.getName() +".png\" />";
											//pw.println("<text x=\"" + (x_pos + rack[i].getRack().getWidth()) + "\" y=\"" + (y_pos + 10) + "\" style=\"fill: #000000\">" + component.getName() + "(RU:" + component.getRackUnits() + ")" + "</text>" );
                                            
                                            svgContent +="<text x=\"" + (x_pos + rack[i].getRack().getWidth()) + "\" y=\"" + (y_pos + 10) + "\" style=\"fill: #000000\">" + component.getName() + "(RU:" + component.getUnits() + ")" + "</text>";
											//pw.println("<line x1=\"" + (rack[i].getRack().getWidth()+(x_pos -50)) +  "\" y1=\"" + (y_pos + 10) +   "\" x2=\""  + (x_pos + rack[i].getRack().getWidth()) + "\"  y2=\"" +  (y_pos + 10) +  "\" style=\"stroke: #ff0000\"/>" );
                                            
                                            svgContent +="<line x1=\"" + (rack[i].getRack().getWidth()+(x_pos -50)) +  "\" y1=\"" + (y_pos + 10) +   "\" x2=\""  + (x_pos + rack[i].getRack().getWidth()) + "\"  y2=\"" +  (y_pos + 10) +  "\" style=\"stroke: #ff0000\"/>";
                                                                                        
                                            
                                        }
					
                                    }
					
                                                                        
					
				}  // end for loop
					
				
				
						
			
                        //pw.println("</svg>");
                        svgContent +="</svg>";
		
                       //pw.close();
                        //fw.close();
				
                
                        //createImage(filename + ".svg", filename + ".jpg");
                
                        
                
                
		
                        return svgContent;
                
                } // END IF RACK > 1
                
                else{
                    
                    return "";
                    
                }
                
        
        
        
        
    }
    
    
    public void setupEnclosure() {
		
		int count = visualViewGather.getNumberEnclosures();
		enclosure = new EnclosureImpl[count];
		for(int i=0; i<count;i++){			
			enclosure[i] = new EnclosureImpl();
		}
		
		
	}
    
    
     // Uses the list of component to find the required numbers of racks. Even though we may only have 30 rack units used,
         // some component need to be in there own rack. eg two MSL4048 are in seperate racks. This causes the need for two rack not just one!!!!
         // Also check for the number of enclousure based on the number of blades. This may add another rack because each enclosure is housed in it own rack at present.
         
	public void setupRack(List<Component> c) {
		int count=0;
		Iterator<Component> comp = c.iterator();
                
                while(comp.hasNext()){
                    Component cc = comp.next();
                    if(cc.getRack_id()>count){
                        count = cc.getRack_id();
                    }
                }
                
                
                
                        // If number rack is less than the number of enclosure set number racks to number of enclosures - 
                        if(count<visualViewGather.getNumberEnclosures())
                            count = visualViewGather.getNumberEnclosures();
			rack = new RackImpl[count];
			for(int i=0;i<count;i++){
				rack[i] = new RackImpl();
						
			}
		
	}

	
    
    
    
}
