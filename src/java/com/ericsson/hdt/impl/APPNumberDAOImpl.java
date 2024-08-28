/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.impl;

import com.ericsson.hdt.dao.APPNumberDAO;
import com.ericsson.hdt.dao.SystemActionLoggerDAO;
import com.ericsson.hdt.model.APPNumber;
import com.ericsson.hdt.model.HWBundle;
import com.ericsson.hdt.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
public class APPNumberDAOImpl extends BaseDAOImpl implements APPNumberDAO{

      private SystemActionLoggerDAO userActionLogger = new SystemActionLoggerDAOImpl();
      private final JdbcTemplate jdbcTemplate;
      
      
      public APPNumberDAOImpl(){
          super();
          this.jdbcTemplate = new JdbcTemplate(dataSource);
      }

    @Override
    public void addType(String type) {
        
        this.jdbcTemplate.update("insert into app_type values(?)",type);
        
    }


    

    @Override
    public List<APPNumber> getListHWAPPNumber(HWBundle hw) {
        List<APPNumber> appList;
        appList = this.jdbcTemplate.query("select apn.id,apn.name,apn.description,apn.expose,hcb.qty from hw_config as hc inner join "
                + "hw_config_bundle as hcb on hc.id = hcb.hw_config_id inner join app_number as apn on apn.id=hcb.app_number_id "
                + "where hc.id =?",new Object[]{hw.getId()}, new HWAPPNumberMapper());
        
        return appList;
    }

    @Override
    public APPNumber getAPPNumber(int id) {
        APPNumber app = this.jdbcTemplate.queryForObject("select * from app_number where id=? ORDER by name",
                    new Object[]{id},new APPNumberMapper());
        
        return app;
    }

    @Override
    public APPNumber findAPPNumberByName(String name) {
        return this.jdbcTemplate.queryForObject("select * from app_number where name=?",new Object[]{name}, new APPNumberMapper());
    }

    @Override
    public List<APPNumber> searchForAPPNumberByDescription(String text) {
        return this.jdbcTemplate.query("select * from app_number as ap where ap.description like '%" + text + "%';" , new APPNumberMapper());
    }

    @Override
    public int updateAPPNumberExposerToEngine(APPNumber app,User user) {
        
        String sqlQuery = "update app_number set expose=" + app.getExposeToEngine() + " where id=" + app.getId() ;
        userActionLogger.logUserAction(user, "app_number", sqlQuery);
       
        return this.jdbcTemplate.update("update app_number set expose=? where id=?",app.getExposeToEngine(),app.getId());
    }

    @Override
    public List<APPNumber> searchForAPPNumberByName(String name) {
         return this.jdbcTemplate.query("select * from app_number as ap where ap.name like '%" + name + "%';" , new APPNumberMapper());
    }

   

        
    private static final class TypeMapper implements RowMapper<String> {

        @Override
        public String mapRow(ResultSet rs, int i) throws SQLException {
            
            return(rs.getString("type"));
            
        }
        
    }

    @Override
    public List<String> getListAPPTypes() {
        
        List<String> types = this.jdbcTemplate.query("select * from app_type",new TypeMapper());
        return types;
    } 
    
    final static class APPNumberMapper implements RowMapper<APPNumber> {

        @Override
        public APPNumber mapRow(ResultSet rs, int i) throws SQLException {
            APPNumber app = new APPNumber(rs.getInt("id"),rs.getString("name"),rs.getString("description"),rs.getBoolean("expose"));
            
            return app;
        }
        
    }
    
    
    final static class HWAPPNumberMapper implements RowMapper<APPNumber>{

        @Override
        public APPNumber mapRow(ResultSet rs, int i) throws SQLException {
            APPNumber app = new APPNumber(rs.getInt("id"),rs.getString("name"),rs.getString("description"),rs.getBoolean("expose"),rs.getInt("qty"));
            
            return app;
        }
        
        
    }
    
    @Override
    public void add(APPNumber app, User user) {
        //int numRows = this.jdbcTemplate.queryForInt("select count(*) from app_number");
        // Using null value as the key is auto-incremented by the database
        String sqlQuery = "insert into app_number values('" + app.getName() + "','" +app.getDescription()+ "','" + app.getExposeToEngine() + "')" ;
        userActionLogger.logUserAction(user, "app_number", sqlQuery);
        
        this.jdbcTemplate.update("insert into app_number values(?,?,?,?)",null,app.getName(),app.getDescription(),app.getExposeToEngine());
    }

    @Override
    public int delete(APPNumber app,User user) {
        int deletedRows;
        String sqlQuery = "delete from app_number where name=" + app.getName();
        userActionLogger.logUserAction(user, "app_number", sqlQuery);
       deletedRows = this.jdbcTemplate.update("delete from app_number where name=?",app.getName());
    
    return deletedRows;
    
    }
    
     @Override
    public int updateAppNumber(APPNumber app,User user) {
        int updatedRows;
        String sqlQuery = "update app_number set apn.name=" + app.getName() + " where apn.id=" + app.getId();
        userActionLogger.logUserAction(user, "app_number", sqlQuery);
        updatedRows = this.jdbcTemplate.update("update app_number as apn set apn.name=? where"
                + " apn.id=?",app.getName(),app.getId());
        
        return updatedRows;
    }

    @Override
    public int updateDescription(APPNumber app,User user) {
        int updatedRows = 0;
         String sqlQuery = "update app_number set apn.description=" + app.getDescription() + " where apn.id=" + app.getId();
        userActionLogger.logUserAction(user, "app_number", sqlQuery);
        updatedRows = this.jdbcTemplate.update("update app_number as apn set apn.description=? where"
                + " apn.id=?",app.getDescription(),app.getId());
        
        return updatedRows;
    }

    @Override
    public List<APPNumber> get() {
        List<APPNumber> apps = this.jdbcTemplate.query("select * from app_number ORDER BY name", new APPNumberMapper());
        
        return apps;
    }
    
}
