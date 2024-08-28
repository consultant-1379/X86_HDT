/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.impl;

import com.ericsson.hdt.dao.SystemActionLoggerDAO;
import com.ericsson.hdt.dao.SystemConfigurationDAO;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Formula;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Note;
import com.ericsson.hdt.model.Parameter;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Role;
import com.ericsson.hdt.model.SystemConfiguration;
import com.ericsson.hdt.model.SystemDetails;
import com.ericsson.hdt.model.UrlLink;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author eadrcle
 */

@Repository
public class SystemConfigurationDAOImpl extends BaseDAOImpl implements SystemConfigurationDAO {

    private final JdbcTemplate jdbcTemplate;
    private SystemActionLoggerDAO userActionLogger = new SystemActionLoggerDAOImpl();
      
    public SystemConfigurationDAOImpl(){
          super();
          this.jdbcTemplate = new JdbcTemplate(dataSource);
          
    }
    
    
        
    @Override
    public int add(Product p, Version v, Network n, Bundle b,User user) {
        String sqlQuery="insert into product_release values(" + v.getName() + "," + b.getID() + "," + n.getNetworkWeight() + "," + p.getWeighting() + "," +  false + "," + null +")"  ;
        userActionLogger.logUserAction(user, "product_release", sqlQuery);
        int updatedRows = this.jdbcTemplate.update("insert into product_release values(?,?,?,?,?,?)",v.getName(),b.getID(),n.getNetworkWeight(),p.getWeighting(),false,null );
        
        return updatedRows;
    }

   
   
    

    @Override
    public int addProductRoles(Product p, Version v, Network n, Bundle b, Role r,Formula f,User user) {
        
        String sqlQuery="insert into product_release_role values(" + r.getId() + "," + r.getSite().getId()+ "," +
                r.getDependency()+ "," + r.getMandatory()+ "," + null + "," + r.getVisible() + "," + r.getName().toUpperCase()+"_RESULT" + "," +f.getName()+ "," + 
                false+ "," +v.getName()+ "," + b.getID() + "," + n.getNetworkWeight()+ "," + p.getWeighting()+ "," + r.getDisplayOrder()+ ")" ;
        userActionLogger.logUserAction(user, "product_release_role", sqlQuery);
        
       int updatedRows = this.jdbcTemplate.update("insert into product_release_role values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",r.getId(),r.getSite().getId(),
                r.getDependency(),r.getMandatory(),null,r.getVisible(),r.getName().toUpperCase()+"_RESULT",f.getName(),false,v.getName(),b.getID(),n.getNetworkWeight(),p.getWeighting(),r.getDisplayOrder());
     return updatedRows;
     
    }

    @Override
    public boolean checkForExistSystem(Product p, Version v, Network n, Bundle b) {
    
        // Must Check for Bundle None as this will be a special case as no other bundle can be created if this exists, as it will allow system to be built on the 
        // network type.
        
        
        
        int rows = this.jdbcTemplate.queryForInt("select count(*) from product_release as pr where pr.product_weight=? and"
                + " pr.version_name=? and pr.combined_network_weight=? and pr.bundle_id=?",p.getWeighting(),v.getName(),n.getNetworkWeight(),b.getID());
        
       
        
        
        if(rows>0){
            return true;
        }
        
        
        return false;
        
    }

    @Override
    public int addSystemFormulaVariables(String name, String description,User user) {
          String sqlQuery="insert into system_detail_parameter values(" + name + "," + description + ")";
        userActionLogger.logUserAction(user, "system_detail_parameter", sqlQuery);
        return this.jdbcTemplate.update("insert into system_detail_parameter values(?,?)",name,description);
        
    }

    @Override
    public Map<String,String> getSystemFormulaVariables() {
        return this.jdbcTemplate.query("select * from system_detail_parameter", new SystemDetailVariableMapper());
    }

    @Override
    public SystemDetails getDefinedProductSystemVariables(Product p, Version v, Network n) {
        
        
        return this.jdbcTemplate.query("select distinct sdp.variable_name,sdp.description, sd.formula_name from system_details as sd inner join"
                                        + " system_detail_parameter as sdp on sd.variable_name=sdp.variable_name where sd.product_weight=? and "
                                        + " sd.combined_network_weight=? and sd.version_name=?",new Object[]{p.getWeighting(),n.getNetworkWeight(),v.getName()}, new SystemDetailsObjectMapper() );
    }

    
    
     @Override
    public List<Note> getSystemWideNote(Boolean status) {
        return this.jdbcTemplate.query("select * from system_messages where visible=? ", new Object[]{status}, new NoteMapper());
    }

    @Override
    public int addSystemWideNote(Note n,User user) {
        String sqlQuery="insert into system_messages value(" + null + "," + n.getNote() + "," + n.isVisible() + ")";
        userActionLogger.logUserAction(user, "system_messages", sqlQuery);
       return this.jdbcTemplate.update("insert into system_messages value(?,?,?)",null,n.getNote(),n.isVisible());
    }

    @Override
    public int updateSystemWideNoteVisible(Note n,User user) {
        String sqlQuery=" update system_messages set visible=" + n.isVisible() +  " where id= " + n.getId();
        userActionLogger.logUserAction(user, "system_messages", sqlQuery);
        return this.jdbcTemplate.update("update system_messages set visible=? where id=?", n.isVisible(),n.getId());
    }

    @Override
    public int deleteSystemNote(Note n,User user) {
        String sqlQuery="delete from system_messages where id=" +  n.getId();
        userActionLogger.logUserAction(user, "system_messages", sqlQuery);
       return this.jdbcTemplate.update("delete from system_messages where id=?", n.getId());
    }
    
     @Override
    public List<Note> getAllSystemWideNote() {
       return this.jdbcTemplate.query("select * from system_messages ",  new NoteMapper());
    }
    
     
     
     private static final class NoteMapper implements RowMapper<Note>{

        @Override
        public Note mapRow(ResultSet rs, int i) throws SQLException {
            Note n = new Note(rs.getInt("id"),rs.getString("content"),rs.getBoolean("visible"));
            
            
            return n;
        }
        
        
        
        
    }
    
    private static class SystemDetailVariableMapper implements ResultSetExtractor<Map<String,String>> {

        
        
        @Override
        public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<String,String> systemDetailVariables = new HashMap<String,String>();
            while(rs.next()){
                systemDetailVariables.put(rs.getString("variable_name"), rs.getString("description"));
                
                
            }
            
            
            
            return systemDetailVariables;
            
            
        }
        
    }
    
    public static class SystemDetailsObjectMapper implements ResultSetExtractor<SystemDetails> {

        @Override
        public SystemDetails extractData(ResultSet rs) throws SQLException, DataAccessException {
        
            SystemDetails systemDetail = null;
            
            if(rs.isBeforeFirst()){
                    systemDetail = new SystemDetails();
                    Map<String,Map<String,Object>> variablesCollection = new HashMap<String,Map<String,Object>>();
                    Map<String,Object> description;
                    Formula formula = new Formula();
                    
                    while(rs.next()){
                        //System.out.println("System Details..");
                            description = new HashMap<String,Object>();
                            String variable = rs.getString("variable_name");
                            String formulaName = rs.getString("formula_name");
                            String descriptionText = rs.getString("description");
                            description.put(descriptionText, null );
                            variablesCollection.put(variable, description);
                            formula.setName(formulaName);
                            
                    }
                    
                    
                    systemDetail.setSystemVariableDetails(variablesCollection);
                    systemDetail.setFormula(formula);
            
            
            
            
          }
            
            
            
            return systemDetail;
        
        
        
        }
        
        
        
        
        
    }
    

    
    
}
