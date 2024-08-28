/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.impl;

import com.ericsson.hdt.dao.ComponentDAO;
import com.ericsson.hdt.dao.SystemActionLoggerDAO;
import com.ericsson.hdt.model.APPNumber;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Component;
import com.ericsson.hdt.model.ComponentType;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Rack;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import com.ericsson.hdt.utils.StaticInputs;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author eadrcle
 */
@Repository
public class ComponentDAOImpl extends BaseDAOImpl implements ComponentDAO{
    
    private final JdbcTemplate jdbcTemplate;
    private SystemActionLoggerDAO userActionLogger = new SystemActionLoggerDAOImpl();
    
    public ComponentDAOImpl(){
        super();
        jdbcTemplate = new JdbcTemplate(dataSource);
        setupDefaultComponentType(StaticInputs.compTypeRackMount);
        setupDefaultComponentType(StaticInputs.compTypeFullHeight);
        setupDefaultComponentType(StaticInputs.compTypeHalfHeight);
    }
    

    @Override
    public int add(Component c,User user) {
        int rows = 0;
        String sqlQuery="insert into component values(" + c.getName() + "," + c.getUnits()+ "," +c.getHasDependant()+ "," +c.getType().getId()+ "," +c.getAppNumber().getId() + ")";
        userActionLogger.logUserAction(user, "component", sqlQuery);
        rows = this.jdbcTemplate.update("insert into component values(?,?,?,?,?)",c.getName(),c.getUnits(),c.getHasDependant(),c.getType().getId(),c.getAppNumber().getId());
        
        return rows;
    }

    @Override
    public int delete(Component c,User user) {
        int deletedRows = 0;
        
        deletedRows = this.jdbcTemplate.update("delete from dependency where component_app_number_id=?",c.getAppNumber().getId());
         
        deletedRows = this.jdbcTemplate.update("delete from rack where component_name=? and component_app_number_id=?",c.getName(),c.getAppNumber().getId());
        
        
        String sqlQuery="delete from component where name=" + c.getName()+ " and app_number_id=" + c.getAppNumber().getId() ;
        userActionLogger.logUserAction(user, "component", sqlQuery);
        deletedRows = this.jdbcTemplate.update("delete from component where name=? and app_number_id=?",c.getName(),c.getAppNumber().getId());
        
        return deletedRows;
    }

    

    @Override
    public int addComponentType(ComponentType c,User user) {
        int rows = 0;
        String sqlQuery="insert into component_type values(" + c.getType() + ")";
        userActionLogger.logUserAction(user, "component_type", sqlQuery);
        rows = this.jdbcTemplate.update("insert into component_type values(?,?)",c.getType(),null);
        
        return rows;
    }

    @Override
    public List<ComponentType> getComponentTypes() {
        List<ComponentType> comTypes = this.jdbcTemplate.query("select * from component_type", new ComponentTypeMapper());
        
        
        return comTypes;
    }

    @Override
    public List<Component> getRackComponent(Product p, Version v, Network n, Bundle b, Rack r) {
        List<Component> components = this.jdbcTemplate.query("select * from rack as r inner join component as c on "
                + " c.name=r.component_name and c.app_number_id=r.component_app_number_id inner join component_type as ct on ct.id=c.component_type_id inner join app_number as ap"
                + " on ap.id=r.component_app_number_id where r.id=? and r.site_id=? and r.product_weight=? and r.combined_network_weight=? and "
                + " r.bundle_id=? and r.version_name=?  order by r.id DESC,r.start_pos DESC ",new Object[]{r.getId(),
                r.getSite().getId(),p.getWeighting(),n.getNetworkWeight(),b.getID(),v.getName()}, new RackComponentMapper() );
        
        
        return components;
    }

    @Override
    public int updateUnits(Component c,User user) {
       int updatedRows;
       String sqlQuery="update component set rack_unit=" + c.getUnits()+ " where name=" + c.getName();
        userActionLogger.logUserAction(user, "component", sqlQuery);
       updatedRows = this.jdbcTemplate.update("update component set rack_unit=? where name=?",c.getUnits(),c.getName());
       
       return updatedRows;
    }
    
    
    
    

    @Override
    public int updateName(Component oldComponent, Component newComponent,User user) {
        int updatedRows;
       
       String sqlQuery="update rack set component_name= "+ newComponent.getName() + " where component_name=" + oldComponent.getName();
        userActionLogger.logUserAction(user, "rack", sqlQuery);
        
       updatedRows = this.jdbcTemplate.update("update rack as r set component_name=? where component_app_number_id=? and r.product_weight!=?"
               + " and r.combined_network_weight!=? and  r.bundle_id!=? and r.version_name!=?",
               newComponent.getName(),oldComponent.getAppNumber().getId(),0,0,0," ");
       
        
       
       
       
       return updatedRows;
    }

    @Override
    public int updateType(Component c,User user) {
        int updatedRows;
       String sqlQuery="update component set component_type_id=" +c.getType().getId()  + " where name=" + c.getName();
        userActionLogger.logUserAction(user, "component", sqlQuery);
       updatedRows = this.jdbcTemplate.update("update component set component_type_id=? where name=?",c.getType().getId(),c.getName());
       
       return updatedRows;
    }

    
    
    private Boolean checkDoesComponentTypeExist(String type){
        
        int rows = this.jdbcTemplate.queryForInt("select count(*) from component_type as ct where ct.type=?",type);
        
        if(rows>0){
            return true;
        }
        
        return false;
        
    }
    private void setupDefaultComponentType(String type) {
        
        if(!(checkDoesComponentTypeExist(type))){
            ComponentType ct = new ComponentType(type);
            //addComponentType(ct);
           this.jdbcTemplate.update("insert into component_type values(?,?)",ct.getType(),null);
            
        }
        
        
        
    }

    @Override
    public int addComponentDependant(Component component, Component dependant,User user) {
        
        String sqlQuery="insert into dependency values(" + dependant.getName() +"," +dependant.getAppNumber().getName()+"," +component.getName()+"," +component.getAppNumber().getId() + ")";
        userActionLogger.logUserAction(user, "dependency", sqlQuery);
        
        return this.jdbcTemplate.update("insert into dependency values(?,?,?,?,?)", dependant.getName(),dependant.getAppNumber().getName(),component.getName(),component.getAppNumber().getId(),null);
    }

    @Override
    public Boolean isAppNumberUsed(APPNumber app) {
        int rows = this.jdbcTemplate.queryForInt("select count(*) from component where app_number_id=?",app.getId());
        
        if(rows>0){
            
            return true;
        }
                
                return false;
    }

    @Override
    public Component findComponentByAPPNumber(APPNumber app) {
        
        return this.jdbcTemplate.queryForObject("select * from component as c inner join component_type as ct on ct.id=c.component_type_id "
                + "inner join app_number as ap on ap.id=c.app_number_id where c.app_number_id=?",new Object[]{app.getId()}, new ComponentMapper());
        
       
        
    }

    @Override
    public List<Component> getComponentDependants(Component component,Product p, Version v, Network n, Bundle b) {
        return this.jdbcTemplate.query("select * from dependency as d inner join app_number as ap on ap.name=d.dep_app inner join component as c"
                + " on c.app_number_id=ap.id inner join component_type as ct on ct.id=c.component_type_id inner join rack as r "
                + "on r.component_name=c.name where d.component_app_number_id=? and d.component_name=? and r.product_weight=? and r.combined_network_weight=? and "
                + " r.bundle_id=? and r.version_name=?",
                new Object[]{component.getAppNumber().getId(),component.getName(),p.getWeighting(),n.getNetworkWeight(),b.getID(),v.getName()}, new RackComponentMapper());
    }

    @Override
    public int updateComponentDependantStatus(Component component,User user) {
        
        String sqlQuery="update component as c set dependency=" +component.getHasDependant() + " where c.name=" + component.getName() + " and c.app_number_id=" +component.getAppNumber().getId();
        userActionLogger.logUserAction(user, "dependency", sqlQuery);
       return this.jdbcTemplate.update("update component as c set dependency=? where c.name=? and c.app_number_id=? ",component.getHasDependant(),component.getName(),
               component.getAppNumber().getId());
    }

       

    
     private static final class RackComponentMapper implements RowMapper<Component>{

        @Override
        public Component mapRow(ResultSet rs, int i) throws SQLException {
            
            ComponentType comType = new ComponentType(rs.getInt("ct.id"),rs.getString("ct.type"));
            APPNumber app = new APPNumber(rs.getInt("ap.id"),rs.getString("ap.name"));
            
            Component component = new Component(rs.getString("c.name"),rs.getInt("rack_unit"),rs.getBoolean("dependency"));
            component.setAppNumber(app);
            component.setType(comType);
            component.setStartPosition(rs.getInt("r.start_pos"));
            component.setRack_id(rs.getInt("r.id"));
            
            
            
            return component;
        }
        
    }
    
    private static final class ComponentTypeMapper implements RowMapper<ComponentType>{

        @Override
        public ComponentType mapRow(ResultSet rs, int i) throws SQLException {
            ComponentType comType  = new ComponentType(rs.getInt("id"),rs.getString("type"));
            
            
            return comType;
        }
        
    }
    
    private static final class ComponentMapper implements RowMapper<Component>{

        @Override
        public Component mapRow(ResultSet rs, int i) throws SQLException {
            
            ComponentType comType = new ComponentType(rs.getInt("ct.id"),rs.getString("ct.type"));
            APPNumber app = new APPNumber(rs.getInt("ap.id"),rs.getString("ap.name"),rs.getString("description"));
            Component component = new Component(rs.getString("name"),rs.getInt("rack_unit"),rs.getBoolean("dependency"));
            component.setAppNumber(app);
            component.setType(comType);
            
            
            
            return component;
        }
        
    }
    
    
    @Override
    public List<Component> getRackMountableComponents() {
        List<Component> components = this.jdbcTemplate.query("select * from component as c inner join component_type as ct on ct.id=c.component_type_id inner join app_number as ap on ap.id=c.app_number_id where ct.type=?",new Object[]{"rack_mount"}, new ComponentMapper());
        
        
        return components;
    }

    
    
    @Override
    public List<Component> getComponents() {
        
        List<Component> components = this.jdbcTemplate.query("select * from component as c inner join component_type as ct on ct.id=c.component_type_id inner join app_number as ap on ap.id=c.app_number_id", new ComponentMapper());
        
        
        return components;
    }

    @Override
    public Component getComponent(Component c) {
        
        Component component = this.jdbcTemplate.queryForObject("select * from component where name=? and app_number_id=?", new ComponentMapper());
        
        
        return component;
    }
    
}
