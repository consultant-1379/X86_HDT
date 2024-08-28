/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.impl;

import com.ericsson.hdt.dao.SystemActionLoggerDAO;
import com.ericsson.hdt.dao.UserDAO;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Parameter;
import com.ericsson.hdt.model.ParameterType;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import com.ericsson.hdt.utils.SHA1HashingPassword;
import com.ericsson.hdt.utils.StaticInputs;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


/**
 *
 * @author eadrcle
 */
@Repository
public class UserDAOImpl extends BaseDAOImpl implements UserDAO {

    private final JdbcTemplate jdbcTemplate;
    String hashedPassword;
    SHA1HashingPassword hash = SHA1HashingPassword.getInstance();
    private SystemActionLoggerDAO userActionLogger = new SystemActionLoggerDAOImpl();
      
    User user;
    
    public UserDAOImpl(){
        super();
        jdbcTemplate = new JdbcTemplate(dataSource);
        
        try {
            hashedPassword = hash.getShaHash(StaticInputs.defaultPassword);
            
            // User - (Fname, Surname,email,Username,password,role)
            user = new User("Adrian","Cleere", "adrian.cleere@ericsson.com","Administartor",hashedPassword,StaticInputs.defaultRole);
            if(!checkUser(user)){
                register(user);
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        
    }

    @Override
    public Boolean validEmail(String email) {
        int row;
        row = jdbcTemplate.queryForInt("select COUNT(*) from user where email=?", new Object[]{email});
        
        if(row==1){
            
            return true;
        }
         
        return false;
        
    }

    @Override
    public Boolean loggedOn(String email) {
        int row ;
        // This should return 0 if user is NOT logged in to the system, else return 1 indicating user is logged in already!
        row = jdbcTemplate.queryForInt("select count(*) from user_login where email=? and logged_in_now=1", email);
        if(row==1){
            return true;
        }
        
        return false;
    }

    @Override
    public int saveParameters(User user,Product p, Version v, Network n, Bundle b, Parameter parameter) {
        java.util.Date now = new java.util.Date();
        java.sql.Timestamp timeStamp = new java.sql.Timestamp(now.getTime());
        int rows = this.jdbcTemplate.update("insert into user_stored_parameters values(?,?,?,?,?,?,?,?)",user.getEmail(),
                parameter.getId(),v.getName(),b.getID(),n.getNetworkWeight(),p.getWeighting(),timeStamp,parameter.getValue());
        
        return rows;
    }

   

    
    @Override
    public List<Parameter> getUserSelectedParameterSet(User user, Product p, Version v, Network n, Bundle b) {
        List<Parameter> parameterSet = null;
         
         parameterSet = this.jdbcTemplate.query("select DISTINCT b.description,cn.name,vp.name,usp.product_weight, usp.version_name, usp.combined_network_weight,"
                 + "usp.bundle_id,usp.time_saved from user_stored_parameters as usp inner join valid_product as vp on usp.product_weight=vp.product_weight "
                 + "inner join combined_network as cn on usp.combined_network_weight = cn.weight "
                 + "inner join bundle as b on usp.bundle_id=b.id where user_email=?",new Object[]{user.getEmail()}, new UserSavedParameterSet());
         
        
        return parameterSet;
    }

    @Override
    public List<Parameter> getUniqueSavedParameter(User user, Product p, Version v, Network n, Bundle b, String timeStamp) {
        List<Parameter> parameterSet = null;
         
         parameterSet = this.jdbcTemplate.query("select pt.id,pt.type,p.name,p.id,p.desc,b.description,cn.name,vp.name,usp.product_weight, usp.version_name, usp.combined_network_weight,"
                 + "usp.bundle_id,usp.time_saved,usp.value from user_stored_parameters as usp inner join valid_product as vp on usp.product_weight=vp.product_weight "
                 + "inner join combined_network as cn on usp.combined_network_weight = cn.weight inner join bundle as b on usp.bundle_id=b.id "
                 + " inner join parameter as p on p.id=usp.parameter_id inner join parameter_type as pt on pt.id=p.parameter_type_id"
                 + " where user_email=? and time_saved=?",new Object[]{user.getEmail(),timeStamp}, new UserUniqueSavedParameter());
         
        
        return parameterSet;
    }

    @Override
    public Boolean updateUserRole(User u) {
        int row = this.jdbcTemplate.update("update user set role=? where email=?",u.getRole(),u.getEmail());
        if(row>0){
            return true;
        }
        
        
        return false;
        
    }

    @Override
    public List<Parameter> getUserSavedParameterSet(User user) {
       List<Parameter> parameterSet = null;
         
         parameterSet = this.jdbcTemplate.query("select DISTINCT b.description,cn.name,vp.name,usp.product_weight, usp.version_name, usp.combined_network_weight,"
                 + "usp.bundle_id,usp.time_saved from user_stored_parameters as usp inner join valid_product as vp on usp.product_weight=vp.product_weight "
                 + "inner join combined_network as cn on usp.combined_network_weight = cn.weight "
                 + "inner join bundle as b on usp.bundle_id=b.id where user_email=?",new Object[]{user.getEmail()}, new UserSavedParameterSet());
         
        
        return parameterSet;
    }

    
    
    private static final class UserUniqueSavedParameter implements RowMapper<Parameter>{

        @Override
        public Parameter mapRow(ResultSet rs, int i) throws SQLException {
            ParameterType parType = new ParameterType(rs.getInt("pt.id"),rs.getString("pt.type").toUpperCase());
            
            Parameter p = new Parameter(rs.getInt("p.id"),rs.getString("p.name"),parType,rs.getString("p.desc"));
            p.setParType(parType);
            p.setVersion(rs.getString("version_name"));
            p.setProduct(rs.getString("vp.name"));
            p.setBundle(rs.getString("b.description"));
            p.setNetwork(rs.getString("cn.name"));
            p.setSavedTime(rs.getTimestamp("time_saved").toString());
            p.setValue(rs.getDouble("usp.value"));
            
            return p;
        }
        
        
        
    }
    
    private static final class UserSavedParameterSet implements RowMapper<Parameter>{

        @Override
        public Parameter mapRow(ResultSet rs, int i) throws SQLException {
            Parameter p = new Parameter();
            p.setVersion(rs.getString("version_name"));
            p.setProduct(rs.getString("vp.name"));
            p.setBundle(rs.getString("b.description"));
            p.setNetwork(rs.getString("cn.name"));
            p.setSavedTime(rs.getTimestamp("time_saved").toString());
            
            
            return p;
        }
        
        
        
    }
    
    private static final class TimeStampUserParameter implements RowMapper<Parameter>{

        @Override
        public Parameter mapRow(ResultSet rs, int i) throws SQLException {
             Parameter p = new Parameter();
             
             
             return p;
        }
        
        
    }
    
    private static final class UserMapper implements RowMapper<User> {

        

        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User(rs.getString("u.firstname"),rs.getString("u.lastname"),rs.getString("u.email"),rs.getString("u.username"),rs.getString("p.passwd"),rs.getInt("u.role"));
            
            return user;
        }
    }
    
     
    
    
    @Override
    public User login(User u) {
        User user = null;
        
        try {
            user = jdbcTemplate.queryForObject("select u.username,u.email,u.firstname,u.lastname,p.passwd,u.role from user u inner join passwd p on u.email=p.email where p.email=?  and p.passwd=?",new Object[]{ u.getEmail(),u.getPassword()}, new UserMapper());
            
            
            userLoginTime(user);
            
        }catch(EmptyResultDataAccessException e){
            user = null;
            
            
        }
        
        
        
        return user;
    }

    private void cleanupUserTables(User u){
        
        this.jdbcTemplate.update("delete from user_stored_parameters where user_email=?",u.getEmail());
        this.jdbcTemplate.update("delete from user_login where email=?",u.getEmail());
        this.jdbcTemplate.update("delete from passwd where email=?",u.getEmail());
        this.jdbcTemplate.update("delete from sys_log where email=?",u.getEmail());
        
    }
    
    @Override
    public void delete(User u) {
        cleanupUserTables(u);
        int rows = this.jdbcTemplate.update("delete from user where email=?",u.getEmail());
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<User> registeredUsers() {
       List<User> users = this.jdbcTemplate.query("select * from user as u inner join passwd as p on u.email=p.email;", new UserMapper());
       
       return users;
    }

    @Override
    public Boolean changePassword(User u) {
        
        int rows = this.jdbcTemplate.update("UPDATE passwd set passwd=? where email=?",u.getPassword(),u.getEmail());
        
        if(rows>0){
            return true;
        }
            
        return false;
    }

   
    @Override
    public Boolean logout(User u) {
        
       return  userLogOutTime(u);
        
    }

    
    @Override
    public Boolean register(User u) {
        
        // Setting 1 as Default User Priv.
        
        if(!checkUser(u)){
            this.jdbcTemplate.update("insert into user values(?,?,?,?,?)", u.getUsername(),u.getEmail(),u.getName(),u.getSurname(),u.getRole());
            this.jdbcTemplate.update("insert into passwd values(?,?)",u.getPassword(),u.getEmail());
       
            return true;
            
        }
        
       
        
        return false;
        
        
    }
    
    
    private Boolean checkUser(User user){
        // Email is Unique
        int rows = this.jdbcTemplate.queryForInt("select count(*) from user as u where u.email=?", user.getEmail());
        
        if(rows>0){
            return true;
        }
        
        return false;
        
    }
    
    
    private Boolean userLoginTime(User user) {
        
        java.util.Date now = new java.util.Date();
        java.sql.Timestamp timeStamp = new java.sql.Timestamp(now.getTime());
        int result=0;
        
        // Check if User logged in already, if he has then need to update the table with the new timestamp.
        int rowCount =jdbcTemplate.queryForInt("SELECT count(*) FROM user_login where email=?", new Object[]{ user.getEmail()});
        if(rowCount==1){
            
            result = jdbcTemplate.update("UPDATE user_login SET login_time=?,logged_in_now=? where email=?",new Object[]{timeStamp,1,user.getEmail()});
            
        }
        else{
            // For First login must set both login and logout time.
          result = jdbcTemplate.update("INSERT INTO user_login (email,login_time,logout_time,logged_in_now)  VALUES(?,?,?,?)", new Object[]{user.getEmail(),timeStamp,timeStamp,1});
          
            
        }
        
            
        if(result==1){
            return true;
        }
        
        return false;
        
        
            
    }
    
    private Boolean userLogOutTime(User user) {
        
        
        java.util.Date now = new java.util.Date();
        java.sql.Timestamp timeStamp = new java.sql.Timestamp(now.getTime());
        int result=0;
        
        
        //This is need because when the user logs out we need to keep the record of login time because it gets overriden when we set only 
        // the logout_time field this maybe any error in mysql. 
        //
        //UPDATE user_login SET logout_time=? where email=?" this updates the login time as well for what reason i don;t know?
        String loginTime = (String)jdbcTemplate.queryForObject("select login_time from user_login where email=?",new Object[]{user.getEmail()}, String.class);
        
        
        
        result = jdbcTemplate.update("UPDATE user_login SET logout_time=?,login_time=?,logged_in_now=? where email=?",new Object[]{timeStamp,loginTime,0,user.getEmail()});
            
        
        
            
        if(result==1){
            return true;
        }
        
        return false;
        
        
            
    }

    @Override
    public Boolean deletedUserSavedParameters(User user, String timeStamp) {
            
        this.jdbcTemplate.update("delete from user_stored_parameters where user_email=? and time_saved=?", new Object[]{user.getEmail(),timeStamp});
        
        return true;
    }

    
    
}
