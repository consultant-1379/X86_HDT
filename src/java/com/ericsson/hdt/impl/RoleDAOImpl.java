/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.impl;

import com.ericsson.hdt.dao.RoleDAO;
import com.ericsson.hdt.dao.SystemActionLoggerDAO;
import com.ericsson.hdt.model.APPNumber;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.HWBundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Note;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Role;
import com.ericsson.hdt.model.Site;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author eadrcle
 */
@Repository
public class RoleDAOImpl extends BaseDAOImpl implements RoleDAO {

    private final JdbcTemplate jdbcTemplate;
    private SystemActionLoggerDAO userActionLogger = new SystemActionLoggerDAOImpl();
    
    public RoleDAOImpl(){
        super();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        
        
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> roles = this.jdbcTemplate.query("select * from roles",new RoleMapper());
        
        return roles;
    }

    @Override
    public List<Role> gatherPerProductRoles(Product p) {
        List<Role> roles = null;
        roles=this.jdbcTemplate.query("select * from product_release_roles where product_name=? and bundle_id=? and version_name=? and combined_network_weight=?",new RoleMapper());
        return roles;
    }

    @Override
    public Role getIndividualRole(int id) {
        
        Role r = this.jdbcTemplate.queryForObject("select * from roles where id=?",new Object[]{id},new RoleMapper());
        
        
        return r;
    }

    @Override
    public void setHardwareConfig(Product p, Version v, Network n, Bundle b, Role r,User user) {
        
        String sqlQuery =null;
        Note note = null;
        try {
                note  = r.getHardwareBundle().get(0).getNote();
        }catch (NullPointerException np){
            
            
        }
       
        if(note==null){

            this.jdbcTemplate.update("insert into role_hw_config values(?,?,?,?,?,?"
                                + ",?,?,?,?,?)",r.getExpectedResult(),r.getRevision(),r.getHardwareBundle().get(0).getId(),
                                null,false,r.getId(),r.getSite().getId(),v.getName(),b.getID(),n.getNetworkWeight(),p.getWeighting());
            
            
            sqlQuery = "insert into role_hw_config values(" + r.getExpectedResult() + "," + r.getRevision()+ "," + r.getHardwareBundle().get(0).getId()+ "," + null + "," + false
                + "," + r.getId() + "," + r.getSite().getId() + "," + v.getName() + "," + b.getID() + "," + n.getNetworkWeight() + "," + p.getWeighting() + ")" ;
            
        }
        else{
            
            this.jdbcTemplate.update("insert into role_hw_config values(?,?,?,?,?,?"
                                + ",?,?,?,?,?)",r.getExpectedResult(),r.getRevision(),r.getHardwareBundle().get(0).getId(),
                                r.getHardwareBundle().get(0).getNote().getId(),true,r.getId(),r.getSite().getId(),v.getName(),b.getID(),n.getNetworkWeight(),p.getWeighting());
            
            sqlQuery = "insert into role_hw_config values(" + r.getExpectedResult() + "," + r.getRevision()+ "," + r.getHardwareBundle().get(0).getId()+ "," + r.getHardwareBundle().get(0).getNote().getId() + "," + true
                + "," + r.getId() + "," + r.getSite().getId() + "," + v.getName() + "," + b.getID() + "," + n.getNetworkWeight() + "," + p.getWeighting() + ")" ;
        }
        
       
       
         userActionLogger.logUserAction(user, "role_hw_config", sqlQuery);
        
        
    }

    @Override
    public List<Role> getProductReleaseRole(Product p, Version v, Network n, Bundle b) {
        List<Role> roles = null;
        
        roles = this.jdbcTemplate.query("select r.id,r.name,r.desc,pr.visible,pr.site_id,pr.result_variable,pr.mandatory,pr.dependency,pr.display_order from product_release_role as pr inner join roles as r "
                            + "on pr.roles_id=r.id where pr.product_weight=?"
                            + " and pr.version_name=? and pr.combined_network_weight=? "
                            + " and pr.bundle_id=? order by pr.display_order", new Object[]{p.getWeighting(),v.getName(),n.getNetworkWeight(),b.getID()}, new RoleSiteMapper() );
           
        
        return roles;
        
        
    }

   

    @Override
    public Boolean isExist(Role r) {
       
        int rows = this.jdbcTemplate.queryForInt("select count(*) from roles where name=?" , r.getName());
        
        if(rows>0){
            return true;
        }
        
        return false;
        
        
    }

    @Override
    public List<Site> findNumberOfSites(Product p, Version v, Network n, Bundle b) {
        List<Site> sites = this.jdbcTemplate.query("select distinct site_id from product_release_role where product_weight=? and "
                + "version_name=? and combined_network_weight=? and bundle_id=? order by site_id ASC",new Object[]{p.getWeighting(),v.getName(),n.getNetworkWeight(),b.getID()}, new SiteMapper());
        
        
        return sites;
    }

    @Override
    public int updateRoleHardwareConfigRevision(Product p, Version v, Network n, Bundle b, Role r,User user) {
        
        
        
        // Need to loop through all hwBundles for a particular role as it may have more than one.
        int updatedRows=0;
        List<HWBundle> listHardwareBundle = r.getHardwareBundle();
        Iterator<HWBundle> hardwareBundles = listHardwareBundle.iterator();
        while(hardwareBundles.hasNext()){
            HWBundle hwBundle = hardwareBundles.next();
            
            updatedRows = this.jdbcTemplate.update("update role_hw_config as rc set rc.hw_ver=? where rc.product_weight=? "
                + "and rc.combined_network_weight=? and rc.bundle_id=? and rc.version_name=? " 
                + "and rc.hw_config_id=? and rc.site_id=? and rc.roles_id=? and rc.hw_ver=?",hwBundle.getRevision(),p.getWeighting(),n.getNetworkWeight()
                ,b.getID(),v.getName(),hwBundle.getId(),r.getSite().getId(),r.getId(),hwBundle.getOldRevision());
            
            
            String sqlQuery = "update role_hw_config as rc set rc.hw_ver=" + hwBundle.getRevision()+ " where rc.product_weight= " + p.getWeighting()+ " and rc.combined_network_weight=" + n.getNetworkWeight()+ 
                " and rc.bundle_id=" + b.getID() + " and rc.version_name=" + v.getName() + "and rc.hw_config_id=" + hwBundle.getId() + " and rc.site_id="+ r.getSite().getId() + 
                " and rc.roles_id=" + r.getId() + " and rc.hw_ver=" + hwBundle.getOldRevision() ;
            userActionLogger.logUserAction(user, "role_hw_config", sqlQuery);
            
            
            
            
            
        }
        
        return updatedRows;
        
        
    }

    @Override
    public int updateRoleHardwareConfigSiteID(Product p, Version v, Network n, Bundle b, Role r,User user) {
        
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int updateRoleHardwareConfigFormula(Product p, Version v, Network n, Bundle b, Role r,User user) {
        
        String sqlQuery = "update product_release_role set formula_name=" + r.getFormula().getName() + " where product_weight= "
                + p.getWeighting() + "and combined_network_weight="  + n.getNetworkWeight() + " and bundle_id=" + b.getID() + " and version_name= " + v.getName() + " and site_id=" + r.getSite().getId() + " and roles_id=" + r.getId() ;
        
        
        userActionLogger.logUserAction(user, "product_release_role", sqlQuery);
        int updatedRows = this.jdbcTemplate.update("update product_release_role set formula_name=? where product_weight=? "
                + "and combined_network_weight=? and bundle_id=? and version_name=? " 
                + "and site_id=? and roles_id=?;",r.getFormula().getName(),p.getWeighting(),n.getNetworkWeight()
                ,b.getID(),v.getName(),r.getSite().getId(),r.getId());
        
        
        return updatedRows;
    }

    @Override
    public int updateRoleHardwareConfigResultValue(Product p, Version v, Network n, Bundle b, Role r,User user) {
         
        int updatedRows =0;
        
        
        List<HWBundle> listHardwareBundle = r.getHardwareBundle();
        Iterator<HWBundle> hardwareBundles = listHardwareBundle.iterator();
        while(hardwareBundles.hasNext()){
            HWBundle hwBundle = hardwareBundles.next();
            
           updatedRows = this.jdbcTemplate.update("update role_hw_config set result_variable=? where product_weight=? "
                + "and combined_network_weight=? and bundle_id=? and version_name=? " 
                + "and hw_config_id=? and hw_ver=? and site_id=? and roles_id=? and result_variable=?;",hwBundle.getExpectedResult(),p.getWeighting(),n.getNetworkWeight()
                ,b.getID(),v.getName(),hwBundle.getId(),hwBundle.getRevision(),r.getSite().getId(),r.getId(),hwBundle.getOldResult());
           
           
           String sqlQuery = "update role_hw_config set result_variable=" +  hwBundle.getExpectedResult() + " where product_weight= " + p.getWeighting() + 
                    " and combined_network_weight=" + n.getNetworkWeight() + " and bundle_id=" + b.getID() + " and version_name= " + v.getName() + "and hw_config_id=" 
                   + hwBundle.getId() + " and hw_ver=" + hwBundle.getRevision() + " and site_id=" + r.getSite().getId() + " and roles_id=" + r.getId()+ " and result_variable= " + hwBundle.getOldResult() ;
          userActionLogger.logUserAction(user, "role_hw_config", sqlQuery);
           
            
        }
        
         return updatedRows;
            
    }

    @Override
    public int updateRoleHardwareConfigNote(Product p, Version v, Network n, Bundle b, Role r,HWBundle hwBundle,User user) {
        int updatedRows=0;
         String sqlQuery = "update role_hw_config set info_note_id=" + hwBundle.getNote().getId() + "  where product_weight= " + p.getWeighting() + "and combined_network_weight=" + n.getNetworkWeight() 
                 + " and bundle_id=" + b.getID() + " and version_name= " +    v.getName()  + "and hw_config_id=" + hwBundle.getId() + " and site_id=" + r.getSite().getId() + " and roles_id="  + r.getId();
         
        userActionLogger.logUserAction(user, "role_hw_config", sqlQuery);
            
            updatedRows =this.jdbcTemplate.update("update role_hw_config set info_note_id=? where product_weight=? "
                + "and combined_network_weight=? and bundle_id=? and version_name=? " 
                + "and hw_config_id=? and site_id=? and roles_id=?;",hwBundle.getNote().getId(),p.getWeighting(),n.getNetworkWeight()
                ,b.getID(),v.getName(),hwBundle.getId(),r.getSite().getId(),r.getId());
            
        
        return updatedRows;
        
    }

    @Override
    public List<Role> getProductReleaseRolePerSite(Product p, Version v, Network n, Bundle b, Site s) {
        List<Role> roles = null;
        
        roles = this.jdbcTemplate.query("select r.id,r.name,r.desc,pr.visible,pr.site_id,pr.result_variable,pr.mandatory,pr.dependency,pr.display_order from product_release_role as pr inner join roles as r "
                            + "on pr.roles_id=r.id where pr.product_weight=?"
                            + " and pr.version_name=? and pr.combined_network_weight=? "
                            + " and pr.bundle_id=? and pr.site_id=? order by pr.display_order ASC", new Object[]{p.getWeighting(),v.getName(),n.getNetworkWeight(),b.getID(),s.getId()}, new RoleSiteMapper() );
           
        
        return roles;
    }

    @Override
    public int updateRoleHardwareConfigNoteVisibility(Product p, Version v, Network n, Bundle b, Role r,HWBundle hwBundle,User user) {
        
        int updatedRows=0;
        String sqlQuery = "update role_hw_config set note_visible=" + hwBundle.getNote().isVisible() + " where product_weight= " + p.getWeighting()
                + "and combined_network_weight=" + n.getNetworkWeight() + "  and bundle_id=" + b.getID() + " and version_name= " +v.getName()
                + "and hw_config_id="  + hwBundle.getId() + " and site_id=" + r.getSite().getId() + " and roles_id=" + r.getId() + " and info_note_id=" + hwBundle.getNote().getId();
        userActionLogger.logUserAction(user, "role_hw_config", sqlQuery);
        
        updatedRows =this.jdbcTemplate.update("update role_hw_config set note_visible=? where product_weight=? "
                + "and combined_network_weight=? and bundle_id=? and version_name=? " 
                + "and hw_config_id=? and site_id=? and roles_id=? and info_note_id=?",hwBundle.getNote().isVisible(),p.getWeighting(),n.getNetworkWeight()
                ,b.getID(),v.getName(),hwBundle.getId(),r.getSite().getId(),r.getId(),hwBundle.getNote().getId());
        
       
        
        
        return updatedRows;
    }

    @Override
    public int updateRoleVisibilty(Product p, Version v, Network n, Bundle b, Role r,User user) {
        int updatedRows = 0;
        
         String sqlQuery = "update product_release_role as pr set pr.visible=" + r.getVisible() + " where pr.product_weight= " + p.getWeighting()
                            + " and pr.version_name=" + v.getName() + " and pr.combined_network_weight= " + n.getNetworkWeight()
                            + " and pr.bundle_id=" + b.getID() + "  and pr.site_id=" + r.getSite().getId() + " and pr.roles_id=" + r.getId();
        userActionLogger.logUserAction(user, "product_release_role", sqlQuery);
        updatedRows = this.jdbcTemplate.update("update product_release_role as pr set pr.visible=? where pr.product_weight=? "
                            + " and pr.version_name=? and pr.combined_network_weight=? "
                            + " and pr.bundle_id=? and pr.site_id=? and pr.roles_id=?" ,
                            new Object[]{r.getVisible(),p.getWeighting(),v.getName(),n.getNetworkWeight(),b.getID(),r.getSite().getId(),r.getId()});
      
        return updatedRows;
                
     }

    @Override
    public int updateRoleNote(Product p, Version v, Network n, Bundle b, Role r,User user) {
        int updatedRows = 0;
        String sqlQuery = "update product_release_role as pr set info_note_id=" +r.getNote().getId() + " where pr.product_weight= " + p.getWeighting()
                            + " and pr.version_name=" + v.getName() + " and pr.combined_network_weight= " + n.getNetworkWeight()
                            + " and pr.bundle_id=" + b.getID() + "  and pr.site_id=" + r.getSite().getId() + " and pr.roles_id=" + r.getId();
        userActionLogger.logUserAction(user, "product_release_role", sqlQuery);
        updatedRows = this.jdbcTemplate.update("update product_release_role as pr set info_note_id=? where pr.product_weight=? "
                            + " and pr.version_name=? and pr.combined_network_weight=? "
                            + " and pr.bundle_id=? and pr.site_id=? and pr.roles_id=?" ,
                            new Object[]{r.getNote().getId(),p.getWeighting(),v.getName(),n.getNetworkWeight(),b.getID(),r.getSite().getId(),r.getId()});
        
        return updatedRows;
    }

    @Override
    public int updateRoleNoteVisibilty(Product p, Version v, Network n, Bundle b, Role r,User user) {
        
        int updatedRows = 0;
        String sqlQuery = "update product_release_role as pr set note_visible=" + r.getNote().isVisible() + " where pr.product_weight= "+ p.getWeighting()
                            + " and pr.version_name=" + v.getName()+ " and pr.combined_network_weight= " + n.getNetworkWeight()
                            + " and pr.bundle_id=" + b.getID() + " and pr.site_id=" + r.getSite().getId() + " and pr.roles_id=" + r.getId() ;

        userActionLogger.logUserAction(user, "product_release_role", sqlQuery);
        updatedRows = this.jdbcTemplate.update("update product_release_role as pr set note_visible=? where pr.product_weight=? "
                            + " and pr.version_name=? and pr.combined_network_weight=? "
                            + " and pr.bundle_id=? and pr.site_id=? and pr.roles_id=?" ,
                            new Object[]{r.getNote().isVisible(),p.getWeighting(),v.getName(),n.getNetworkWeight(),b.getID(),r.getSite().getId(),r.getId()});
        
        return updatedRows;
        
    }

   
    @Override
    public int updateRoleHardwareConfigID(Product p, Version v, Network n, Bundle b, Role r, HWBundle oldHW, HWBundle newHW,User user) {
        
        int updatedRows=0;
        String sqlQuery = "update role_hw_config as rc set rc.hw_config_id =" + newHW.getId() + " where rc.product_weight=" + p.getWeighting()
                            + "and rc.combined_network_weight=" + n.getNetworkWeight()+ " and rc.bundle_id=" + b.getID() + " and rc.version_name= " + v.getName()
                            + "and rc.hw_config_id=" + oldHW.getId() + " and rc.site_id="  + r.getSite().getId() +" and rc.roles_id=" + r.getId() +  
                " and rc.result_variable=" + oldHW.getExpectedResult() + " and rc.hw_ver=" + oldHW.getRevision() ;
        userActionLogger.logUserAction(user, "role_hw_config", sqlQuery);
       
        
        updatedRows = this.jdbcTemplate.update("update role_hw_config as rc set rc.hw_config_id =? where rc.product_weight=? "
                            + "and rc.combined_network_weight=? and rc.bundle_id=? and rc.version_name=? " 
                            + "and rc.hw_config_id=? and rc.site_id=? and rc.roles_id=? and rc.result_variable=? and"
                + " rc.hw_ver=?",
                            newHW.getId(),p.getWeighting(),n.getNetworkWeight(),
                            b.getID(),v.getName(),oldHW.getId(),r.getSite().getId(),r.getId(),oldHW.getExpectedResult(),oldHW.getRevision());
        
        
        return updatedRows;
    }

    private Boolean checkExistProductReleaseRoleDependant(Product p, Version v, Network n, Bundle b, Role parent, Role dependant){
        
        int row = this.jdbcTemplate.queryForInt("select count(*) from role_dependency as rd where rd.product_weight=? and rd.combined_network_weight=?"
                + " and rd.bundle_id=? and rd.version_name=? and rd.site_id=? and rd.roles_id=? and rd.dep_roles=?",p.getWeighting(),n.getNetworkWeight(),
                b.getID(),v.getName(),parent.getSite().getId(),parent.getId(),dependant.getId());
        
        if(row>0)
            return true;
        
        
        return false;
    }
    private Boolean checkExistProductReleaseRole(Product p, Version v, Network n, Bundle b, Role r){
        
        int row = this.jdbcTemplate.queryForInt("select count(*) from product_release_role as prr where prr.product_weight=? and prr.combined_network_weight=?"
                + " and prr.bundle_id=? and prr.version_name=? and prr.site_id=? and prr.roles_id=?",p.getWeighting(),n.getNetworkWeight(),
                b.getID(),v.getName(),r.getSite().getId(),r.getId());
        
        if(row>0)
            return true;
        
        
        return false;
    }
    
    @Override
    public int addProductReleaseRole(Product p, Version v, Network n, Bundle b, Role r,User user) {
        int updatedRows = 0;
        String sqlQuery="insert into product_release_role values(" + r.getId() +","+ r.getSite().getId()+","+ r.getDependency()+","+ r.getMandatory()+","+ null +","+ r.getVisible() 
                +","+ r.getName().toUpperCase()+ "_RESULT" +","+ r.getFormula().getName()+","+false+","+ v.getName()+","+b.getID()+","+n.getNetworkWeight()+","+p.getWeighting()+",1)";
         userActionLogger.logUserAction(user, "product_release_role", sqlQuery);
        
        if(!checkExistProductReleaseRole(p,v,n,b,r)){
            updatedRows = this.jdbcTemplate.update("insert into product_release_role values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",r.getId(),r.getSite().getId(),
                r.getDependency(),r.getMandatory(),null,r.getVisible(),r.getName().toUpperCase()+"_RESULT",r.getFormula().getName(),false,
                v.getName(),b.getID(),n.getNetworkWeight(),p.getWeighting(),1);
        }
              return updatedRows;
    }

    @Override
    public int addProductReleaseRoleDependant(Product p, Version v, Network n, Bundle b, Role parent, Role dependant,User user) throws DuplicateKeyException {
       int updatedRows = 0;
        if(!checkExistProductReleaseRoleDependant(p,v,n,b,parent,dependant)){
            String sqlQuery="insert into role_dependency values(" + dependant.getId() + "," + parent.getId() + "," + parent.getSite().getId() + "," + v.getName() 
                    + "," + b.getID() + "," + n.getNetworkWeight() + "," + p.getWeighting() + ")";
         userActionLogger.logUserAction(user, "role_dependency", sqlQuery);
            
            updatedRows = this.jdbcTemplate.update("insert into role_dependency values(?,?,?,?,?,?,?)", new Object[]{dependant.getId(),parent.getId(),
            parent.getSite().getId(),v.getName(),b.getID(),n.getNetworkWeight(),p.getWeighting()});
        
            this.jdbcTemplate.update("update product_release_role as prr set prr.dependency=? where prr.product_weight=? and prr.combined_network_weight=?"
                + " and prr.bundle_id=? and prr.version_name=? and prr.site_id=? and prr.roles_id=?", new Object[]{parent.getDependency(),p.getWeighting(),
                n.getNetworkWeight(),b.getID(),v.getName(),parent.getSite().getId(),parent.getId()});
        }
        return updatedRows;
    }

    @Override
    public int deleteRoleHardwareConfig(Product p, Version v, Network n, Bundle b, Role r, HWBundle hardware,User user) {
        
        int updatedRows;
        String sqlQuery="delete from role_hw_config where product_weight=" + p.getWeighting() + " and combined_network_weight=" + n.getNetworkWeight() + " and bundle_id=" + b.getID() + 
                " and version_name=" + v.getName() + " and site_id=" + r.getSite().getId() + " and roles_id="+ r.getId() + 
                " and hw_config_id=" + hardware.getId()+ "  and hw_ver="+ hardware.getRevision()+ "and result_variable=" + hardware.getExpectedResult();
         userActionLogger.logUserAction(user, "role_hw_config", sqlQuery);
        
        
        updatedRows = this.jdbcTemplate.update("delete from role_hw_config where product_weight=? and combined_network_weight=?"
                + " and bundle_id=? and version_name=? and site_id=? and roles_id=? and hw_config_id=? and hw_ver=?"
                + " and result_variable=?",p.getWeighting(),n.getNetworkWeight(),b.getID(),v.getName(),r.getSite().getId(),r.getId(),
                 hardware.getId(),hardware.getRevision(),hardware.getExpectedResult());
        
        return updatedRows;
        
    }

    @Override
    public int updateRoleMadatoryStatus(Product p, Version v, Network n, Bundle b, Role r,User user) {
        int updatedRows = 0;
         String sqlQuery="update product_release_role set mandatory=" + r.getMandatory() + " where product_weight=" + p.getWeighting() + " and combined_network_weight=" + n.getNetworkWeight()
                + " and bundle_id=" + b.getID() + " and version_name=" + v.getName() + " and site_id=" + r.getSite().getId()+ " and roles_id=" + r.getId();
         userActionLogger.logUserAction(user, "product_release_role", sqlQuery);
        
        updatedRows = this.jdbcTemplate.update("update product_release_role set mandatory=? where product_weight=? and combined_network_weight=?"
                + " and bundle_id=? and version_name=? and site_id=? and roles_id=?",r.getMandatory(),p.getWeighting(),n.getNetworkWeight(),b.getID(),v.getName()
                ,r.getSite().getId(),r.getId());
        
        
        return updatedRows;
    }

    @Override
    public int deleteProductRole(Product p, Version v, Network n, Bundle b, Role r,User user) {
        int deletedRows;
        
        String sqlQuery=" delete from product_release_role " + " where product_weight=" + p.getWeighting() + " and combined_network_weight=" + n.getNetworkWeight()
                + " and bundle_id=" + b.getID() + " and version_name=" + v.getName() + " and site_id=" + r.getSite().getId()+ " and roles_id=" + r.getId();
         userActionLogger.logUserAction(user, "role_hw_config", sqlQuery);
         
         
        deletedRows = this.jdbcTemplate.update("delete from role_hw_config where product_weight=? and version_name=? and combined_network_weight=?"
                + " and bundle_id=? and site_id=? and roles_id=?",p.getWeighting(),v.getName(),n.getNetworkWeight(),b.getID(),r.getSite().getId()
                ,r.getId());
        
        this.jdbcTemplate.update("delete from role_dependency where product_weight=? and version_name=? and combined_network_weight=?"
                + " and bundle_id=? and site_id=? and roles_id=?",p.getWeighting(),v.getName(),n.getNetworkWeight(),b.getID(),r.getSite().getId()
                ,r.getId());
        
        this.jdbcTemplate.update("delete from product_release_role where product_weight=? and version_name=? and combined_network_weight=?"
                + " and bundle_id=? and site_id=? and roles_id=?",p.getWeighting(),v.getName(),n.getNetworkWeight(),b.getID(),r.getSite().getId()
                ,r.getId());
        
        
        
        return deletedRows;
    }

    @Override
    public int cleanupProductRole(Product p, Version v, Network n, Bundle b) {
     int deletedRows;
     
     
     this.jdbcTemplate.update("delete from product_url_link where product_weight=? and version_name=? and combined_network_weight=?"
                + " and bundle_id=?",p.getWeighting(),v.getName(),n.getNetworkWeight(),b.getID());
     
     this.jdbcTemplate.update("delete from sub_parameter where product_weight=? and version_name=? and combined_network_weight=?"
                + " and bundle_id=?",p.getWeighting(),v.getName(),n.getNetworkWeight(),b.getID());
     
     this.jdbcTemplate.update("delete from product_parameter where product_weight=? and version_name=? and combined_network_weight=?"
                + " and bundle_id=?",p.getWeighting(),v.getName(),n.getNetworkWeight(),b.getID());
     
     this.jdbcTemplate.update("delete from product_release_notes where product_weight=? and version_name=? and combined_network_weight=?"
                + " and bundle_id=?",p.getWeighting(),v.getName(),n.getNetworkWeight(),b.getID());
     
     this.jdbcTemplate.update("delete from network_limits where product_weight=? and version_name=? and combined_network_weight=?"
                + " and bundle_id=?",p.getWeighting(),v.getName(),n.getNetworkWeight(),b.getID());
     
     
     this.jdbcTemplate.update("delete from rack where product_weight=? and version_name=? and combined_network_weight=?"
                + " and bundle_id=?",p.getWeighting(),v.getName(),n.getNetworkWeight(),b.getID());
     
     deletedRows = this.jdbcTemplate.update("delete from product_release where product_weight=? and version_name=? and combined_network_weight=?"
                + " and bundle_id=?",p.getWeighting(),v.getName(),n.getNetworkWeight(),b.getID());
     
     return deletedRows;
    }

    @Override
    public int updateProductReleaseRoleDependant(Product p, Version v, Network n, Bundle b, Role parent, Role dependant,User user) {
       
        int updatedRows;
        String sqlQuery="insert into role_dependency values(" + dependant.getId() + "," + parent.getId() + "," +  parent.getSite().getId()+ "," + v.getName()+ "," + b.getID()+ "," 
                + n.getNetworkWeight()+ "," + p.getWeighting()  + ")";
         userActionLogger.logUserAction(user, "role_dependency", sqlQuery);
       
        
        updatedRows = this.jdbcTemplate.update("insert into role_dependency values(?,?,?,?,?,?,?)",dependant.getId(),parent.getId()
                ,parent.getSite().getId(),v.getName(),b.getID(),n.getNetworkWeight(),p.getWeighting());
        
        
        return updatedRows;
    }

    @Override
    public List<Role> getRoleDependants(Product p, Version v, Network n, Bundle b, Role parent) {
        List<Role> roleDependant = this.jdbcTemplate.query("select * from role_dependency as rd inner join roles as r on r.id=rd.dep_roles_id "
                + "where rd.product_weight=? and rd.combined_network_weight=? and rd.bundle_id=? and rd.version_name=? and rd.site_id=? "
                + " and rd.role_id=?",new Object[]{p.getWeighting(),n.getNetworkWeight(),b.getID(),v.getName(),
                parent.getSite().getId(),parent.getId()}, new RoleMapper());
        
        return roleDependant;
    }

    @Override
    public int deleteProductReleaseRoleDependant(Product p, Version v, Network n, Bundle b, Role parent,User user) {
        int updatedRows;
         String sqlQuery="delete from role_dependency where product_weight=" + p.getWeighting() + " and combined_network_weight="  + n.getNetworkWeight() + " and bundle_id="  
                 + b.getID() + " and version_name="  + v.getName() + " and site_id="  + parent.getSite().getId() + " and roles_id=" + parent.getId();
         userActionLogger.logUserAction(user, "role_dependency", sqlQuery);
        
        updatedRows = this.jdbcTemplate.update("delete from role_dependency where product_weight=? and combined_network_weight=? and "
                + " bundle_id=? and version_name=? and site_id=? and roles_id=?",p.getWeighting(),n.getNetworkWeight(),b.getID(),v.getName(),
                parent.getSite().getId(),parent.getId());
        
       
        
        return updatedRows;
    }

    @Override
    public int updateProductReleaseRoleDisplayOrder(Product p, Version v, Network n, Bundle b,Site s, Role role,User user) {
         String sqlQuery="update product_release_role set display_order=" + role.getDisplayOrder() + " where product_weight=" + p.getWeighting()+ " and combined_network_weight=" 
                 + n.getNetworkWeight() + "and bundle_id=" + b.getID() + " and version_name=" + v.getName() + " and site_id=" +s.getId() + " and roles_id=" + role.getId();
         userActionLogger.logUserAction(user, "product_release_role", sqlQuery);
        return this.jdbcTemplate.update("update product_release_role set display_order=? where product_weight=? and combined_network_weight=? and "
                + " bundle_id=? and version_name=? and site_id=? and roles_id=?",role.getDisplayOrder(),p.getWeighting(),n.getNetworkWeight(),b.getID(),v.getName(),
                s.getId(),role.getId());
    }

   
   
    
    private static final class SiteMapper implements RowMapper<Site>{

        @Override
        public Site mapRow(ResultSet rs, int i) throws SQLException {
        
            Site site = new Site(rs.getInt("site_id"));
            
            
            
            return site;
        }
        
        
        
        
    }
    
    private static final class RoleSiteMapper implements RowMapper<Role>{

        @Override
        public Role mapRow(ResultSet rs, int i) throws SQLException {
            
            Site site = new Site(rs.getInt("site_id"));
            Role r = new Role(rs.getInt("id"),rs.getString("name"),rs.getString("desc"),site);
            r.setVisible(rs.getBoolean("visible"));
            r.setVariableName(rs.getString("result_variable"));
            r.setMandatory(rs.getBoolean("mandatory"));
            r.setDependency(rs.getBoolean("dependency"));
            r.setDisplayOrder(rs.getInt("display_order"));
            
            
            
            
            
            return r;
        }
        
        
    }
    
    
    private static final class RoleMapper implements RowMapper<Role>{

        @Override
        public Role mapRow(ResultSet rs, int i) throws SQLException {
            Role r = new Role(rs.getInt("id"),rs.getString("name"),rs.getString("desc"));
            return r;
        }
        
    }
    
    
    @Override
    public void add(Role r,User user) {
        
         String sqlQuery="insert into roles values(" + r.getName()+ "," + r.getDescription() + " )";
         userActionLogger.logUserAction(user, "roles", sqlQuery);
        
        this.jdbcTemplate.update("insert into roles values(?,?,?)", null ,r.getName(),r.getDescription());
        
        
    }

    @Override
    public int updateDescription(Role r,User user) {
       int updatedRows = 0 ;
         String sqlQuery="update roles as r set r.desc=" + r.getDescription() +  " where r.id=" + r.getId();
         userActionLogger.logUserAction(user, "roles", sqlQuery);
        updatedRows = this.jdbcTemplate.update("update roles as r set r.desc=? where r.id=?;",r.getDescription(),r.getId()); 
       
        return updatedRows;
        
    }

    @Override
    public void delete(Role r,User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
