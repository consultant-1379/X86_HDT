/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.impl;

import com.ericsson.hdt.dao.HardwareConfigDAO;
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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author eadrcle
 */
@Repository
public class HardwareConfigDAOImpl  extends BaseDAOImpl implements HardwareConfigDAO{
    
    private final JdbcTemplate jdbcTemplate;
    private SystemActionLoggerDAO userActionLogger = new SystemActionLoggerDAOImpl();
    
    public HardwareConfigDAOImpl(){
           super();
          this.jdbcTemplate = new JdbcTemplate(dataSource);
           
    }

    @Override
    public HWBundle getIndividualHWBundle(int id) {
        
        HWBundle hardwareBundle = this.jdbcTemplate.queryForObject("select * from hw_config where id=?",new Object[]{id}, new HardwareConfigMapper());
        return hardwareBundle;
    }

    @Override
    public List<HWBundle> findRoleHardwareBundle(Product p, Version v, Network n, Bundle b, Role r) {
        
        List<HWBundle> hwBundles = this.jdbcTemplate.query("select * from role_hw_config as rh inner join hw_config as hc on rh.hw_config_id=hc.id where "
                + " rh.product_weight=? and rh.version_name=? and rh.combined_network_weight=? and rh.bundle_id=? and rh.roles_id=?"
                + " and rh.site_id=?", new Object[]{p.getWeighting(),v.getName(),n.getNetworkWeight(),b.getID(),r.getId(),r.getSite().getId()}, new HardwareConfigBundle());
        
        return hwBundles;
    }

    @Override
    public Integer deleteHWBundleAPP(HWBundle hw, APPNumber app,User user) {
        int updatedRows;
        String sqlQuery="delete from hw_config_bundle where hw_config_id= " + hw.getId()+ " and app_number_id=" + app.getId();
        
        userActionLogger.logUserAction(user, "hw_config_bundle", sqlQuery);
        updatedRows = this.jdbcTemplate.update("delete from hw_config_bundle where hw_config_id=? and app_number_id=?",
                                                hw.getId(),app.getId());
        
        
        return updatedRows;
        
        
    }

    @Override
    public int updateHWConfigAPPQty(HWBundle hw,APPNumber app,User user) {
        int updatedRows;
        String sqlQuery="update hw_config_bundle set qty=" + app.getQty()+ " where app_number_id=" + app.getId() +  "and hw_config_id=" +hw.getId() ;
        
        userActionLogger.logUserAction(user, "hw_config_bundle", sqlQuery);
        updatedRows= this.jdbcTemplate.update("update hw_config_bundle set qty=? where app_number_id=? and hw_config_id=?;",app.getQty(),app.getId(),hw.getId());
    
    return updatedRows;
    }

    @Override
    public int updateHWConfigAPPList(HWBundle hw, APPNumber app,User user) {
        int updatedRows;
        String sqlQuery="insert into hw_config_bundle values(" + app.getQty() + "," + app.getId()  + "," + hw.getId()+ ")" ;
        
        userActionLogger.logUserAction(user, "hw_config_bundle", sqlQuery);
        updatedRows = this.jdbcTemplate.update("insert into hw_config_bundle values(?,?,?);",app.getQty(),app.getId(),hw.getId());
        return updatedRows;
    }

    @Override
    public int updateHWConfigDescription(HWBundle hw,User user) {
        int updatedRows ;
        String sqlQuery="update hw_config as hc set hc.desc=" +hw.getDesc()+ "where hc.id=" + hw.getId() ;
        
        userActionLogger.logUserAction(user, "hw_config", sqlQuery);
        updatedRows = this.jdbcTemplate.update("update hw_config as hc set hc.desc=? where hc.id=?;",hw.getDesc(),hw.getId());
        return updatedRows;
    }

    @Override
    public List<HWBundle> findHardwareBundleWithAPPNumber(APPNumber app) {
        
        List<HWBundle> hw = this.jdbcTemplate.query("select * from hw_config_bundle as hcb inner join "
                + " hw_config as hc on hc.id=hcb.hw_config_id where hcb.app_number_id=?",new Object[]{app.getId()}, new HardwareConfigMapper());
        
        return hw;
    }

    @Override
    public List<HWBundle> findRoleHardwareBundleWithResult(Product p, Version v, Network n, Bundle b, Role r) {
        List<HWBundle> hwBundles = this.jdbcTemplate.query("select * from role_hw_config as rh inner join hw_config as hc on rh.hw_config_id=hc.id where "
                + " rh.product_weight=? and rh.version_name=? and rh.combined_network_weight=? and rh.bundle_id=? and rh.roles_id=?"
                + " and rh.site_id=? and rh.result_variable=? ORDER BY rh.hw_ver DESC", new Object[]{p.getWeighting(),v.getName(),n.getNetworkWeight(),
                    b.getID(),r.getId(),r.getSite().getId(),r.getExpectedResult()}, new HardwareConfigBundle());
        
        return hwBundles;
    }

    @Override
    public List<HWBundle> findRoleHardwareWithResult(Product p, Version v, Network n, Role r) {
       List<HWBundle> hwBundles = this.jdbcTemplate.query("select * from role_hw_config as rh inner join hw_config as hc on rh.hw_config_id=hc.id where "
                + " rh.product_weight=? and rh.version_name=? and rh.combined_network_weight=? and rh.roles_id=?"
                + " and rh.site_id=? and rh.result_variable=? ORDER BY rh.hw_ver DESC", new Object[]{p.getWeighting(),v.getName(),
                    n.getNetworkWeight(),r.getId(),r.getSite().getId(),r.getExpectedResult()}, new HardwareConfigBundle());
        
        return hwBundles;
    }

    @Override
    public int checkRoleHardwareBundleExistance(Product p, Version v, Network n, Role r, HWBundle hardware) {
        
        
      return this.jdbcTemplate.queryForInt("select count(*) from role_hw_config as rh where "
                + " rh.product_weight=? and rh.version_name=? and rh.combined_network_weight=? and rh.roles_id=?"
                + " and rh.site_id=? and rh.result_variable=? and rh.hw_config_id=?", new Object[]{p.getWeighting(),v.getName(),
                    n.getNetworkWeight(),r.getId(),r.getSite().getId(),r.getExpectedResult(),hardware.getId()} 
                );
        
         
    }

    @Override
    public List<HWBundle> searchForHWBundleByDescription(String text) {
        text = "%" + text + "%";
       return this.jdbcTemplate.query("select * from hw_config as hc where hc.desc like ? order by hc.id ASC", new Object[]{text}, new HardwareConfigMapper());
    }

     
       

    @Override
    public List<Role> findRolesWithHWBundle(HWBundle hw) {
        List<Role> roles;
       
        roles = this.jdbcTemplate.query("select * from role_hw_config as rc inner join roles as r on rc.roles_id=r.id "
                + "inner join valid_product vp on rc.product_weight=vp.product_weight inner join bundle as b on b.id=rc.bundle_id "
                + " inner join combined_network as cn on cn.weight=rc.combined_network_weight "
                + " inner join version as v on v.name=rc.version_name where hw_config_id=?",new Object[] {hw.getId()},new RoleHardwareMapper());
    
    
        return roles;
    }

    @Override
    public List<HWBundle> searchForHWBundleByAPPDescription(String text) {
        text = "%" + text + "%";
        List<HWBundle> hwBundles = this.jdbcTemplate.query("select * from hw_config as hc inner join hw_config_bundle as hcb "
                + "on hc.id=hcb.hw_config_id inner join app_number as apn on apn.id = hcb.app_number_id where apn.description like ? order by hc.id ASC",new Object[]{text}, new  HardwareConfigMapper() );
        
        return hwBundles;
    }

    @Override
    public int updateHardwareEOSLDate(HWBundle hw,User user) {
        String sqlQuery="update hw_config set eol_date=" +hw.getEol()+ " where id=" + hw.getId();
        userActionLogger.logUserAction(user, "hw_config", sqlQuery);
        return this.jdbcTemplate.update("update hw_config set eol_date=? where id=?",hw.getEol(),hw.getId());
    }

    @Override
    public int updateHardwareEOSLStatus(HWBundle hw,User user) {
        String sqlQuery="update hw_config set eol=" + hw.getEolStatus()+ " where id=" + hw.getId();
        userActionLogger.logUserAction(user, "hw_config", sqlQuery);
        return this.jdbcTemplate.update("update hw_config set eol=? where id=?",hw.getEolStatus(),hw.getId());
    }

    @Override
    public List<HWBundle> searchForHWBundleWithAPPNumber(String text) {
        text = "%" + text + "%";
        List<HWBundle> hwBundles = this.jdbcTemplate.query("select * from hw_config as hc inner join hw_config_bundle as hcb "
                + "on hc.id=hcb.hw_config_id inner join app_number as apn on apn.id = hcb.app_number_id where apn.name like ? order by hc.id ASC",new Object[]{text}, new  HardwareConfigMapper() );
        
        return hwBundles;
    }

    @Override
    public int updateHardwareBundleGenerationType(HWBundle hw,User user) {
        String sqlQuery="update hw_config set app_type_type="+ hw.getGenerationType()+ " where id=" + hw.getId();
        userActionLogger.logUserAction(user, "hw_config", sqlQuery);
        return this.jdbcTemplate.update("update hw_config set app_type_type=? where id=?",hw.getGenerationType(),hw.getId());
    }

    @Override
    public List<String> getListHardwareGeneration() {
        List<String> types = this.jdbcTemplate.query("select * from app_type", new HardwareGenerationMapper());
        return types;
    }
    
    private final static class HardwareGenerationMapper implements RowMapper<String>{

        @Override
        public String mapRow(ResultSet rs, int i) throws SQLException {
            return rs.getString("type");
        }
        
        
    }
      
    private final static class RoleHardwareMapper implements RowMapper<Role> {

        @Override
        public Role mapRow(ResultSet rs, int i) throws SQLException {
            Role r = new Role(rs.getInt("rc.roles_id"),rs.getString("r.name"),rs.getString("r.desc"));
            Product p = new Product(rs.getString("vp.name"),rs.getInt("vp.product_weight"));
            Bundle b = new Bundle(rs.getInt("b.id"),rs.getString("b.name"),rs.getString("b.description"));
            Network n = new Network(rs.getString("cn.name"),rs.getInt("cn.weight"));
            Version v = new Version(rs.getString("v.name"),rs.getString("v.desc"));
            Site site = new Site(rs.getInt("rc.site_id"));
            r.setSite(site);
            r.setProduct(p);
            r.setNetwork(n);
            r.setVersion(v);
            r.setBundle(b);
            
            
            return r;
        }
           
           
    }
    
    
    
    
    private final static class HardwareConfigBundle implements RowMapper<HWBundle>{

        @Override
        public HWBundle mapRow(ResultSet rs, int i) throws SQLException {
            HWBundle hw = new HWBundle();
            
            hw.setId(rs.getInt("id"));
            hw.setDesc(rs.getString("desc"));
            hw.setEol(rs.getString("eol_date"));
            hw.setRevision(rs.getInt("hw_ver"));
            hw.setEolStatus(rs.getBoolean("eol"));
            hw.setExpectedResult(rs.getObject("result_variable"));
            hw.setGenerationType(rs.getString("app_type_type"));
           
            
            
           
            return hw;
        }
        
    }

   
   
    private final static class HardwareConfigMapper implements RowMapper<HWBundle> {

        @Override
        public HWBundle mapRow(ResultSet rs, int i) throws SQLException {
            
            HWBundle hw = new HWBundle();
            hw.setId(rs.getInt("id"));
            hw.setDesc(rs.getString("desc"));
            hw.setEol(rs.getString("eol_date"));
            hw.setEolStatus(rs.getBoolean("eol"));
            hw.setGenerationType(rs.getString("app_type_type"));
            
            
           
            return hw;
        }
        
    }
    
    
     @Override
    public void addAPPNumbers(HWBundle hw,User user) {
        
         int rows = this.jdbcTemplate.queryForInt("select count(*) from hw_config where id=?",hw.getId());
         
         // Check that the HW Bundle exists if not create it!
         if(rows!=1){
             add(hw,user);
         }
         List<APPNumber> app = hw.getAppList();
        Iterator<APPNumber> appList = app.iterator();
        while(appList.hasNext()){
            
            APPNumber a = appList.next();
            String sqlQuery="insert into hw_config_bundle values(" + a.getQty() + "," + a.getId() + "," + hw.getId() + ")";
            userActionLogger.logUserAction(user, "hw_config_bundle", sqlQuery);
            this.jdbcTemplate.update("insert into hw_config_bundle values(?,?,?)",a.getQty(),a.getId(),hw.getId());
        }
    }
    
     
     private Boolean exist(HWBundle hw){
         int rows = 0;
         try{
            rows = this.jdbcTemplate.queryForInt("select count(*) from hw_config as hc where hc.desc=?", hw.getDesc());
         } catch(EmptyResultDataAccessException er){
             
         }
         if(rows>0){
             return true;
         }
         
         return false;
     }
    
    @Override
    public int add(HWBundle hw,User user) {
        int hwID ; //= this.jdbcTemplate.queryForInt("select count(*) from hw_config;");
        
        
        if(!exist(hw)){        
            String sqlQuery="insert into hw_config values(" + hw.getDesc()+ "," + ",0," + hw.getGenerationType()+ ")";
            this.jdbcTemplate.update("insert into hw_config values(?,?,?,?,?)", null,hw.getDesc(),"",0,hw.getGenerationType());
            hwID = this.jdbcTemplate.queryForInt("select id from hw_config as hc where hc.desc=?", hw.getDesc());
            userActionLogger.logUserAction(user, "hw_config", sqlQuery);
            
        }
        else{
            hwID = this.jdbcTemplate.queryForInt("select id from hw_config as hc where hc.desc=?", hw.getDesc());
            
        }
        return hwID;
    }

    @Override
    public int delete(HWBundle hw,User user) {
        int deleteRows ;
        
        this.jdbcTemplate.update("delete from hw_config_bundle where hw_config_id=?", hw.getId());
        String sqlQuery="delete from hw_config_bundle where hw_config_id=" +hw.getId();
        userActionLogger.logUserAction(user, "hw_config_bundle", sqlQuery);
        
        deleteRows = this.jdbcTemplate.update("delete from hw_config where id=?" , hw.getId());
        sqlQuery="delete from hw_config where hw_config_id=" +hw.getId();
        userActionLogger.logUserAction(user, "hw_config", sqlQuery);
        return deleteRows;
    }

    @Override
    public void update(HWBundle hw,User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<HWBundle> getAllHWBundles() {
        
        List<HWBundle> hwBundle = this.jdbcTemplate.query("select * from hw_config order by id DESC ", new HardwareConfigMapper());
        
        return hwBundle;
    }
    
}
