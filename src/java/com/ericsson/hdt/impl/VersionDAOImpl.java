/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.impl;

import com.ericsson.hdt.dao.SystemActionLoggerDAO;
import com.ericsson.hdt.dao.VersionDAO;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Formula;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Note;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import java.io.Serializable;
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
public class VersionDAOImpl extends BaseDAOImpl implements VersionDAO,Serializable {

    private final JdbcTemplate jdbcTemplate;
    private SystemActionLoggerDAO userActionLogger = new SystemActionLoggerDAOImpl();
    
    public VersionDAOImpl(){
        super();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        
    }
    
    
    @Override
    public void add(Version v,User user) {
        int rows = this.jdbcTemplate.queryForInt("select count(*) from version where name=?",v.getName());
        if(rows!=1){
        String sqlQuery = "insert into version values(" + v.getName() + "," + v.getDesc() + ")" ;
        userActionLogger.logUserAction(user, "version", sqlQuery);
            
            
            
            this.jdbcTemplate.update("insert into version values(?,?)",v.getName(),v.getDesc());
            
        }
        
        
     }

    @Override
    public void delete(Version v,User user) {
        String sqlQuery = "delete from version where name=?" + v.getName();
        userActionLogger.logUserAction(user, "version", sqlQuery);
        this.jdbcTemplate.update("delete from version where name=?",v.getName());
    }

    @Override
    public void update(Version v,User user) {
        String sqlQuery = "";
        userActionLogger.logUserAction(user, "version", sqlQuery);
        
    }

    @Override
    public List<Version> get() {
        
        List<Version> versions =  this.jdbcTemplate.query("select * from version",new VersionMapper());
        return versions;
    }

    @Override
    public Version getIndividualVersion(String name) {
        Version v = this.jdbcTemplate.queryForObject("select * from version where name=?", new Object[]{name}, new VersionMapper());
        
        
        return v;
    }

    @Override
    public Boolean isExist(Version v) {
        
        int rows = this.jdbcTemplate.queryForInt("select count(*) from version where name=?",v.getName());
        if(rows>0){
            
            return true;
            
        }
        
        return false;
        
        
    }

    @Override
    public List<Version> getProductReleaseVersion(Integer weighting) {
        
        
        List<Version> versions = this.jdbcTemplate.query("select DISTINCT v.name,v.desc from product_release as pr inner join version as v on pr.version_name=v.name where product_weight=? order by v.name DESC",new Object[]{weighting},new VersionMapper());
        
        return versions;
        
    }

    @Override
    public int updateDescription(Version v,User user) {
        int updatedRows = 0;
        String sqlQuery = "update version as v set v.desc=" + v.getDesc() + " where v.name=" + v.getName();
        userActionLogger.logUserAction(user, "version", sqlQuery);
        updatedRows = this.jdbcTemplate.update("update version as v set v.desc=? where v.name=?;",v.getDesc(),v.getName());
        return updatedRows;
    }

    @Override
    public int updateNotesVisibilty(Product p, Version v, Network n, Bundle b, Note note,User user) {
        int updatedRows = 0;
        String sqlQuery = "update product_release_notes set visible=" + note.isVisible()+  " where product_weight="  + p.getWeighting() +  " and combined_network_weight=" + n.getNetworkWeight() 
                +  " and bundle_id=" + b.getID() + " and version_name=" + v.getName() +  " and info_note_id=" + note.getId();
                
                
        userActionLogger.logUserAction(user, "product_release_notes", sqlQuery);
        updatedRows = this.jdbcTemplate.update("update product_release_notes set visible=? where product_weight=?"
                + " and combined_network_weight=? and bundle_id=? and version_name=? and info_note_id=?",note.isVisible(),p.getWeighting()
                ,n.getNetworkWeight(),b.getID(),v.getName(),note.getId());
                
        return updatedRows;
                
    }

    @Override
    public int updateProductReleaseTestScript(Product p, Version v, Network net, Bundle b,Formula f,User user) {
        int updatedRows = 0;
        String sqlQuery = "update product_release as pr set pr.formula_name="  + f.getName()+  " where product_weight="  + p.getWeighting() +  " and combined_network_weight=" + net.getNetworkWeight() 
                +  " and bundle_id=" + b.getID() + " and version_name=" + v.getName();
        userActionLogger.logUserAction(user, "product_release", sqlQuery);
        updatedRows = this.jdbcTemplate.update("update product_release as pr set pr.formula_name=? where pr.product_weight=?"
                + " and pr.combined_network_weight=? and pr.bundle_id=? and pr.version_name=?",f.getName(),p.getWeighting(),net.getNetworkWeight()
                ,b.getID(),v.getName());
        
        return updatedRows;
    }
    
     @Override
    public int updateSystemDetailScript(Product p, Version v, Network net, Bundle b,Formula f,User user) {
        int updatedRows = 0;
        String sqlQuery = " update system_details as sd set sd.formula_name=" + f.getName()+  " where product_weight="  + p.getWeighting() +  " and combined_network_weight=" + net.getNetworkWeight() 
                +  " and bundle_id=" + b.getID() + " and version_name=" + v.getName();
        userActionLogger.logUserAction(user, "system_details", sqlQuery);
        updatedRows = this.jdbcTemplate.update("update system_details as sd set sd.formula_name=? where sd.product_weight=?"
                + " and sd.combined_network_weight=? and sd.version_name=? and sd.bundle_id=?",f.getName(),p.getWeighting(),net.getNetworkWeight()
                ,v.getName(),b.getID());
        
        return updatedRows;
    }
    

    @Override
    public int setGA(Product p, Version v,User user) {
        int updatedRows = 0;
        String sqlQuery = "update product_release as pr set pr.GA=" + v.getGA() +  " where product_weight="  + p.getWeighting() + " and pr.version_name=" + v.getName();;
        userActionLogger.logUserAction(user, "product_release", sqlQuery);
        updatedRows = this.jdbcTemplate.update("update product_release as pr set pr.GA=? where pr.product_weight=? and pr.version_name=?",v.getGA(),p.getWeighting(),v.getName());
        
        
        return updatedRows;
    }

    @Override
    public Boolean getProductReleaseVersionGAStatus(Integer weighting,Version v) {
       
        int status = this.jdbcTemplate.queryForInt("select count(*) from product_release as pr where pr.product_weight=? "
                + "and pr.version_name=? and pr.GA=?", new Object[]{weighting,v.getName(),true});
        
        if(status>=1){
            return true;
        }
        
        return false; 
    }

    @Override
    public int deleteProductRelease(Product p, Version v,User user) {
         int updatedRows = 0;
         
       
        updatedRows += this.jdbcTemplate.update("delete from role_hw_config  where product_weight=? and "
                + "version_name=?;",p.getWeighting(),v.getName());
        
        updatedRows += this.jdbcTemplate.update("delete from role_dependency where product_weight=? and "
                + "version_name=?;",p.getWeighting(),v.getName());
        
        updatedRows += this.jdbcTemplate.update("delete from role_hw_change where product_weight=? and "
                + "version_name=?;",p.getWeighting(),v.getName());
        
        
        updatedRows +=this.jdbcTemplate.update("delete from product_release_role where product_weight=? and "
                + "version_name=?;",p.getWeighting(),v.getName());
       
       
        
        updatedRows +=this.jdbcTemplate.update("delete from product_release_notes where product_weight=? and "
                + "version_name=?;",p.getWeighting(),v.getName());
        
        updatedRows +=this.jdbcTemplate.update("delete from network_limits where product_weight=? and "
                + "version_name=?;",p.getWeighting(),v.getName());
        
        updatedRows +=this.jdbcTemplate.update("delete from product_url_link where product_weight=? and "
                + "version_name=?;",p.getWeighting(),v.getName());
        
        
        updatedRows +=this.jdbcTemplate.update("delete from rack where product_weight=? and "
                + "version_name=?;",p.getWeighting(),v.getName());
        
        updatedRows +=this.jdbcTemplate.update("delete from user_stored_parameters where product_weight=? and "
                + "version_name=?;",p.getWeighting(),v.getName());
        
        
        
        updatedRows +=this.jdbcTemplate.update("delete from sub_parameter where product_weight=? and "
                + "version_name=?;",p.getWeighting(),v.getName());
        
        
        updatedRows +=this.jdbcTemplate.update("delete from product_parameter where product_weight=? and "
                + "version_name=?;",p.getWeighting(),v.getName());
        
        
        
        updatedRows +=this.jdbcTemplate.update("delete from rack where product_weight=? and "
                + "version_name=?;",p.getWeighting(),v.getName());
        
        updatedRows +=this.jdbcTemplate.update("delete from system_details where product_weight=? and "
                + "version_name=?;",p.getWeighting(),v.getName());
        
        
        // This must be the last table deleted as it hold all the primary keys that are foreign keys in the above tables
        
         updatedRows +=this.jdbcTemplate.update("delete from product_release where product_weight=? and "
                + "version_name=?;",p.getWeighting(),v.getName());
         
         String sqlQuery = "delete from product_release where product_weight=" + p.getWeighting() + " and version_name="  +v.getName();
        userActionLogger.logUserAction(user, "product_release", sqlQuery);
        
        return updatedRows;
    }

    @Override
    public List<Version> getProductReleaseVersionGA(Integer weighting) {
        List<Version> versions = this.jdbcTemplate.query("select DISTINCT v.name,v.desc from product_release as pr "
                + "inner join version as v on pr.version_name=v.name where product_weight=? and pr.GA=? order by v.name DESC",new Object[]{weighting,true},new VersionMapper());
        
        return versions;
    }

   

   
    
    private static final class VersionMapper implements RowMapper<Version>{

        @Override
        public Version mapRow(ResultSet rs, int i) throws SQLException {
            Version v = new Version(rs.getString("name"),rs.getString("desc"));
            return v;
        }
        
        
    }
    
    private static final class ProductVersionMapper implements RowMapper<Version>{

        @Override
        public Version mapRow(ResultSet rs, int i) throws SQLException {
            Version v = new Version(rs.getString("name"),rs.getString("desc"),rs.getBoolean("GA"));
            return v;
        }
        
        
    }
    
}
 