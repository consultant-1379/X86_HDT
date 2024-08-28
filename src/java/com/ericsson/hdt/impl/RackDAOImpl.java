/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.impl;

import com.ericsson.hdt.dao.RackDAO;
import com.ericsson.hdt.dao.SystemActionLoggerDAO;
import com.ericsson.hdt.model.APPNumber;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Component;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Rack;
import com.ericsson.hdt.model.Site;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


/**
 *
 * @author eadrcle
 */

@Repository
public class RackDAOImpl extends BaseDAOImpl implements RackDAO{
    
    private final JdbcTemplate jdbcTemplate;
    private SystemActionLoggerDAO userActionLogger = new SystemActionLoggerDAOImpl();
    
    public RackDAOImpl(){
        super();
        jdbcTemplate = new JdbcTemplate(dataSource);
        
    }

    @Override
    public List<Rack> getRackDetails(Product p, Version v, Network n, Bundle b, Rack r) {
       
        
        return null;
    }

    @Override
    public int setRackDetail(Product p, Version v, Network n, Bundle b,Rack r, Component c,User user) {
       
        int rows = this.jdbcTemplate.update("insert into rack values(?,?,?,?,?,?,?,?,?)",r.getId(),c.getStartPosition(),
                v.getName(),b.getID(),n.getNetworkWeight(),p.getWeighting(),r.getSite().getId(),c.getName(),c.getAppNumber().getId());
        
        String sqlQuery = "insert into rack values(" + r.getId() + "," + c.getStartPosition() + "," + v.getName() + "," + b.getID() + "," + n.getNetworkWeight()
                + "," + p.getWeighting() + "," +r.getSite().getId() + "," + c.getName()+ "," + c.getAppNumber().getId()  +")";
        userActionLogger.logUserAction(user, "rack", sqlQuery);
        
        
        return rows;
        
        
    }

    @Override
    public List<Integer> getNumberRacks(Product p, Version v, Network n, Bundle b, Site site) {
        
        List<Integer> rackID;
        rackID =  this.jdbcTemplate.query("select Distinct id from rack where product_weight=? and combined_network_weight=?"
                + " and bundle_id=? and version_name=? and site_id=?",new Object[]{p.getWeighting(),n.getNetworkWeight(),b.getID(),v.getName(),site.getId()},new IntegerMapper());
                
                
       return rackID;
    }

    @Override
    public int updateRackComponentPosition(Product p, Version v, Network n, Bundle b,Component oldComponent,Component newComponent,Site site,User user) {
        
        int updatedRows;
        
       
        
           updatedRows = this.jdbcTemplate.update("update rack set start_pos=?  where product_weight=? and combined_network_weight=? "
                    + " and bundle_id=? and version_name=? and component_name=? and start_pos=? and id=? and site_id=?",
                    newComponent.getStartPosition(),p.getWeighting(),n.getNetworkWeight(),b.getID(),v.getName(),oldComponent.getName(),
                    oldComponent.getStartPosition(),oldComponent.getRack_id(),site.getId());
            
            
         String sqlQuery = "update rack set start_pos=" + newComponent.getStartPosition()+ "  where product_weight=" + p.getWeighting() + " and combined_network_weight= " + n.getNetworkWeight()
                    + " and bundle_id=" +b.getID() + " and version_name=" + v.getName() + " and component_name=" + oldComponent.getName() + " and start_pos=" +  oldComponent.getStartPosition()
                 + " and id=" +oldComponent.getRack_id() + " and site_id=" + site.getId();
        
         userActionLogger.logUserAction(user, "rack", sqlQuery);
        
        
        return updatedRows;
    }

    @Override
    public int updateComponentRackID(Product p, Version v, Network n, Bundle b, Component oldComponent, Component newComponent, Site site,User user) {
        int updatedRows;
        
       
           updatedRows = this.jdbcTemplate.update("update rack set id=?  where product_weight=? and combined_network_weight=? "
                    + " and bundle_id=? and version_name=? and component_name=? and start_pos=? and id=? and site_id=?",
                    newComponent.getRack_id(),p.getWeighting(),n.getNetworkWeight(),b.getID(),v.getName(),oldComponent.getName(),
                    oldComponent.getStartPosition(),oldComponent.getRack_id(),site.getId());
            
            String sqlQuery = "update rack set id=" +  newComponent.getRack_id() + "  where product_weight=" + p.getWeighting() + " and combined_network_weight= " + n.getNetworkWeight()
                    + " and bundle_id=" +b.getID() + " and version_name=" + v.getName() + " and component_name=" + oldComponent.getName() + " and start_pos=" +  oldComponent.getStartPosition()
                 + " and id=" +oldComponent.getRack_id() + " and site_id=" + site.getId();
           userActionLogger.logUserAction(user, "rack", sqlQuery);
        
        
        return updatedRows;
    }

    @Override
    public List<Component> getValidRackApps() {
        
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getRowCount(Product p, Version v, Network n, Bundle b) {
        return this.jdbcTemplate.queryForInt("select count(*) from rack where product_weight=? and combined_network_weight=? "
                    + " and bundle_id=? and version_name=?", p.getWeighting(),n.getNetworkWeight(),b.getID(),v.getName());
    }

    @Override
    public int deleteProductRackComponent(Product p, Version v, Network n, Bundle b, Component component, Rack rack,User user) {
        
        String sqlQuery = "delete from rack where version_name="  +  v.getName() + " and bundle_id=" + b.getID() + " and combined_network_weight= " + n.getNetworkWeight() 
               + " and product_weight=" + p.getWeighting() + " and component_name= " + component.getName() + " and id=" + rack.getId() + "  and start_pos=" + component.getStartPosition() + " and site_id=" + rack.getSite().getId();
        userActionLogger.logUserAction(user, "rack", sqlQuery);
    
        
        
        return this.jdbcTemplate.update("delete from rack where version_name=? and bundle_id=? and combined_network_weight=? "
                + " and product_weight=? and component_name=? and id=? and start_pos=? and site_id=?",
                v.getName(),b.getID(),n.getNetworkWeight(),p.getWeighting(),component.getName(),rack.getId(),component.getStartPosition(),rack.getSite().getId());
        
    }   

    public static final class RackComponentPerProductRelease implements RowMapper<Component>{

        @Override
        public Component mapRow(ResultSet rs, int i) throws SQLException {
            
            Site site = new Site(rs.getInt("r.site_id"));
            APPNumber appNumber = new APPNumber(rs.getInt("r.component_app_number_id"));
            Component c = new Component(rs.getInt("r.id"),rs.getString("r.component_name"),appNumber,rs.getInt("r.start_pos"),site);
            
            return c;
            
        }
        
        
        
    }
    
    @Override
    public List<Component> getRackLayoutForProductRelease(Product p, Version v, Network n, Bundle b) {
        
        List<Component> componentsList = this.jdbcTemplate.query("select * from rack as r where r.product_weight=? and"
                + " r.version_name=? and r.combined_network_weight=? and bundle_id=?",new Object[]{p.getWeighting(),v.getName(),n.getNetworkWeight(),b.getID()},
                new RackComponentPerProductRelease() );
        
        
        
        return componentsList;
    }

    
        public static final class DependencyMapper implements RowMapper<Component>{

        @Override
        public Component mapRow(ResultSet rs, int i) throws SQLException {
            
            
                Component c = new Component();
            
            return c;
        }
        
        
    }
    
    
    
    
    
    protected final static class IntegerMapper implements RowMapper<Integer>{

        @Override
        public Integer mapRow(ResultSet rs, int i) throws SQLException {
            
            Integer id= new Integer(rs.getInt("id"));
            
            
            return id;
            
        }
        
    }
    

    
    protected final static class RackMapper implements RowMapper<Rack>{

        @Override
        public Rack mapRow(ResultSet rs, int i) throws SQLException {
            
            Rack rack = new Rack();
            
            
            return rack;
            
        }
        
    }
    
    
    
    protected final static class RackComponentMapper implements RowMapper<Rack>{

        @Override
        public Rack mapRow(ResultSet rs, int i) throws SQLException {
            
            Rack rack = new Rack(rs.getInt("r.id"));
            
            
            
            
            return rack;
            
        }
        
    }
    
}
