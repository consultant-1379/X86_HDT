/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.impl;

import com.ericsson.hdt.dao.SystemActionLoggerDAO;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.UserAction;
import java.sql.Date;
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
public class SystemActionLoggerDAOImpl extends BaseDAOImpl implements SystemActionLoggerDAO {
    
    private final JdbcTemplate jdbcTemplate;
    
    public SystemActionLoggerDAOImpl(){
        super();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        
        
    }
    
    

    @Override
    public void logUserAction(User user, String table, String sql) {
        java.util.Date now = new java.util.Date();
        java.sql.Timestamp timeStamp = new java.sql.Timestamp(now.getTime());
     
        this.jdbcTemplate.update("insert into sys_log values(?,?,?,?,?)",table,sql,timeStamp,user.getEmail(),null);
        
        
    }

    @Override
    public List<UserAction> viewInsertActions() {
         return this.jdbcTemplate.query("select * from sys_log where action like '%insert%' order by id DESC",new UserActionMapper());
    }

    
    private static final class UserActionMapper implements RowMapper<UserAction> {

        

        @Override
        public UserAction mapRow(ResultSet rs, int i) throws SQLException {
            UserAction userAction = new UserAction(rs.getString("user_email"),rs.getString("table_name"),rs.getString("action"),rs.getDate("change_time"));
            
            
            
            return userAction;
        }
    
    }
    @Override
    public List<UserAction> viewUpdateActions() {
        return this.jdbcTemplate.query("select * from sys_log where action like '%update%' order by id DESC",new UserActionMapper());
        
    }

    @Override
    public List<UserAction> viewDeleteActions() {
       return this.jdbcTemplate.query("select * from sys_log where action like '%delete%' order by id DESC",new UserActionMapper());
    }

    @Override
    public List<UserAction> viewAllActions() {
        return this.jdbcTemplate.query("select * from sys_log order by id DESC",new UserActionMapper());
    }

    @Override
    public List<UserAction> viewUserUpdateActions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserAction> viewUserDeleteActions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserAction> viewAllUserActions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
