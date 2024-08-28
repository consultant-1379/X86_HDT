/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.impl;

import com.ericsson.hdt.dao.ParameterDAO;
import com.ericsson.hdt.dao.SystemActionLoggerDAO;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Formula;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Parameter;
import com.ericsson.hdt.model.ParameterType;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import com.ericsson.hdt.utils.StaticInputs;
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
public class ParameterDAOImpl extends BaseDAOImpl  implements ParameterDAO{

    
    private final JdbcTemplate jdbcTemplate;
    private SystemActionLoggerDAO userActionLogger = new SystemActionLoggerDAOImpl();
    
    public ParameterDAOImpl(){
        super();
        jdbcTemplate= new JdbcTemplate(dataSource);
        setupDefaultParameterType(StaticInputs.ParameterBoolean);
        setupDefaultParameterType(StaticInputs.ParameterInteger);
        setupDefaultParameterType(StaticInputs.ParameterDouble);
       
        setupDefaultParameter(StaticInputs.GEO_PARAMETER,"Do You Require Geo Redundancy?","boolean",true);  
       
        
    }

    @Override
    public List<ParameterType> getParameterTypes() {
        List<ParameterType> parTypes;
        parTypes = this.jdbcTemplate.query("select * from parameter_type", new ParameterTypeMapper());
        
        return parTypes;
    }

    @Override
    public Parameter getParameter(int id) {
        Parameter par;
        par = this.jdbcTemplate.queryForObject("select * from parameter as p inner join parameter_type as pt on p.parameter_type_id=pt.id where p.id=?",new Object[]{id},new ParameterMapper());
        return par;
    }
    
    @Override
    public Parameter getParameter(String name) {
        Parameter par;
        par = this.jdbcTemplate.queryForObject("select * from parameter as p inner join parameter_type as pt on p.parameter_type_id=pt.id where p.name=?",new Object[]{name},new ParameterMapper());
        return par;
    }

    @Override
    public Boolean parameterExist(String name) {
        
        int result = this.jdbcTemplate.queryForInt("select count(*) from parameter where name=?",name);
        
        if(result>0){
            return true;
        }
        
        return false;
    }
    
    
    private Boolean productReleaseParameterExist(Product product, Version version, Network network, Bundle bundle,Parameter parameter){
        int result = this.jdbcTemplate.queryForInt("select count(*) from product_parameter as pp where pp.parameter_id=? and "
                + "pp.version_name=? and pp.bundle_id=? and pp.combined_network_weight=? and pp.product_weight=?",parameter.getId(),version.getName()
                ,bundle.getID(),network.getNetworkWeight(),product.getWeighting());
        
        if(result>0){
            return true;
        }
        
        
        return false;
        
        
    }

    @Override
    public List<Parameter> getProductReleaseParameterList(Product p, Version v, Network net, Bundle b) {
        List<Parameter> parameters = this.jdbcTemplate.query("select * from product_parameter as pp inner join parameter as p "
                + "on pp.parameter_id=p.id inner join parameter_type as pt on p.parameter_type_id = pt.id where pp.product_weight=? "
                + "and pp.combined_network_weight=? and pp.bundle_id=? and pp.version_name=? order by pp.display_order ASC",
                new Object[]{p.getWeighting(),net.getNetworkWeight(),b.getID(),v.getName()}, new ProductReleaseParameterMapper());
        
        
        return parameters;
    }

    @Override
    public int updateProductReleaseParameterValue(Product p, Version v, Network net, Bundle b, Parameter par,User user) {
        int updatedRows = 0 ;
        String sqlQuery = "update product_parameter as pp set pp.value=" + par.getValue() + " where pp.product_weight=" + p.getWeighting() + 
                " and pp.combined_network_weight=" + net.getNetworkWeight()+ " and pp.bundle_id=" +b.getID() + " and pp.version_name=" + v.getName()+ " and pp.parameter_id=" + par.getId();
        
        userActionLogger.logUserAction(user, "product_parameter", sqlQuery);
        
        updatedRows = this.jdbcTemplate.update("update product_parameter as pp set pp.value=? where pp.product_weight=?"
                + " and pp.combined_network_weight=? and pp.bundle_id=? and pp.version_name=? and pp.parameter_id=?"
                ,par.getValue(),p.getWeighting(),net.getNetworkWeight(),b.getID(),v.getName(),par.getId());
        
        return updatedRows;
    }

    @Override
    public int updateParameterDescription(Parameter p,User user) {
        int updatedRows = 0;
        String sqlQuery = "update parameter as p set p.desc=" + p.getDesc() + " where p.id=" + p.getId();
        
        userActionLogger.logUserAction(user, "parameter", sqlQuery);
        updatedRows = this.jdbcTemplate.update("update parameter as p set p.desc=? where p.id=?",p.getDesc(),p.getId());
        return updatedRows;
    }

    @Override
    public int updateProductReleaseParameterVisibilty(Product p, Version v, Network net, Bundle b, Parameter par,User user) {
        int updatedRows = 0;
        String sqlQuery = "update product_parameter as pp set pp.visible_state=" + par.getVisible()+ " where pp.product_weight=" + p.getWeighting()+ 
                " and pp.combined_network_weight=" + net.getNetworkWeight()+ " and pp.bundle_id=" + b.getID() + " and pp.version_name=" + v.getName()+ " and pp.parameter_id=" + par.getId();
        
        userActionLogger.logUserAction(user, "product_parameter", sqlQuery);
        updatedRows = this.jdbcTemplate.update("update product_parameter as pp set pp.visible_state=? where pp.product_weight=?"
                + " and pp.combined_network_weight=? and pp.bundle_id=? and pp.version_name=? and pp.parameter_id=?",par.getVisible(),p.getWeighting(),
                net.getNetworkWeight(),b.getID(),v.getName(),par.getId());
        return updatedRows;
    }

    @Override
    public int updateProductReleaseParameterEditability(Product p, Version v, Network net, Bundle b, Parameter par,User user) {
        int updatedRows = 0;
        String sqlQuery = "update product_parameter as pp set pp.enabled=" + par.getEnabled()+ " where pp.product_weight=" + p.getWeighting()+ 
                " and pp.combined_network_weight=" + net.getNetworkWeight()+ " and pp.bundle_id=" + b.getID() + " and pp.version_name=" + v.getName()+ " and pp.parameter_id=" + par.getId();
        
        userActionLogger.logUserAction(user, "product_parameter", sqlQuery);
        
        updatedRows = this.jdbcTemplate.update("update product_parameter as pp set pp.enabled=? where pp.product_weight=?"
                + " and pp.combined_network_weight=? and pp.bundle_id=? and pp.version_name=? and pp.parameter_id=?",par.getEnabled(),p.getWeighting(),
                net.getNetworkWeight(),b.getID(),v.getName(),par.getId());
        return updatedRows;
    }

    @Override
    public List<Parameter> getProductReleaseSubParameter(Product p, Version v, Network net, Bundle b, Parameter par) {
        List<Parameter> subParameter = null;
        
        subParameter = this.jdbcTemplate.query("select * from parameter as p inner join sub_parameter as sp on p.id=sp.sub_parameter_id "
                        + " inner join parameter_type as pt on p.parameter_type_id=pt.id where sp.main_parameter_id=? and sp.product_weight=?"
                + "  and sp.combined_network_weight=? and sp.bundle_id=? and sp.version_name=?",new Object[]{par.getId(),p.getWeighting(),
                 net.getNetworkWeight(),b.getID(),v.getName()},new ProductReleaseSubParameterMapper());
        
        return subParameter;
        
        
    }

    @Override
    public int updateParameterType(Parameter p,User user) {
        int updatedRows = 0;
        String sqlQuery = "update parameter as p set p.parameter_type_id="+ p.getParType().getId() + " where p.id=" + p.getId();
        
        userActionLogger.logUserAction(user, "parameter", sqlQuery);
        
        updatedRows = this.jdbcTemplate.update("update parameter as p set p.parameter_type_id=? where p.id=?",p.getParType().getId(),p.getId());
        
        return updatedRows;
    }

    @Override
    public int updateProductReleaseParameterSubParameterValue(Product p, Version v, Network net, Bundle b, Parameter mainParameter, Parameter subParameter, User user) {
        int updatedRows = 0;
        String sqlQuery = "update sub_parameter as sp set sp.value=" + subParameter.getValue()+ " where sp.product_weight=" +p.getWeighting()
                + " and sp.combined_network_weight=" +net.getNetworkWeight() +" and sp.bundle_id=" + b.getID() + " and sp.version_name=" + v.getName() 
                + " and sp.main_parameter_id="+ mainParameter.getId() + "and sp.sub_parameter_id=" + subParameter.getId();
        
        userActionLogger.logUserAction(user, "sub_parameter", sqlQuery);
        
        
        updatedRows = this.jdbcTemplate.update("update sub_parameter as sp set sp.value=? where sp.product_weight=?"
                + " and sp.combined_network_weight=? and sp.bundle_id=? and sp.version_name=? and sp.main_parameter_id=? and sp.sub_parameter_id=?"
                ,subParameter.getValue(),p.getWeighting(),net.getNetworkWeight(),b.getID(),v.getName(),mainParameter.getId(),subParameter.getId());
        
        return updatedRows;
    }

    @Override
    public int updateProductReleaseParameterSubParameterVisibilty(Product p, Version v, Network net, Bundle b, Parameter mainParameter, Parameter subParameter, User user) {
        int updatedRows = 0;
        
        String sqlQuery = "update sub_parameter as sp set sp.visible_state=" + subParameter.getValue()+ " where sp.product_weight=" +p.getWeighting()
                + " and sp.combined_network_weight=" +net.getNetworkWeight() +" and sp.bundle_id=" + b.getID() + " and sp.version_name=" + v.getName() 
                + " and sp.main_parameter_id="+ mainParameter.getId() + "and sp.sub_parameter_id=" + subParameter.getId();
        
        userActionLogger.logUserAction(user, "sub_parameter", sqlQuery);
        
        
       updatedRows = this.jdbcTemplate.update("update sub_parameter as sp set sp.visible_state=? where sp.product_weight=?"
                + " and sp.combined_network_weight=? and sp.bundle_id=? and sp.version_name=? and sp.main_parameter_id=? and sp.sub_parameter_id=?"
                ,subParameter.getVisible(),p.getWeighting(),net.getNetworkWeight(),b.getID(),v.getName(),mainParameter.getId(),subParameter.getId());
        
        
        return updatedRows;
    }

    @Override
    public int updateProductReleaseParameterSubParameterEditability(Product p, Version v, Network net, Bundle b, Parameter mainParameter, Parameter subParameter, User user) {
         int updatedRows = 0;
         
         String sqlQuery = "update sub_parameter as sp set sp.enabled=" + subParameter.getValue()+ " where sp.product_weight=" +p.getWeighting()
                + " and sp.combined_network_weight=" +net.getNetworkWeight() +" and sp.bundle_id=" + b.getID() + " and sp.version_name=" + v.getName() 
                + " and sp.main_parameter_id="+ mainParameter.getId() + "and sp.sub_parameter_id=" + subParameter.getId();
        
        userActionLogger.logUserAction(user, "sub_parameter", sqlQuery);
        
        updatedRows = this.jdbcTemplate.update("update sub_parameter as sp set sp.enabled=? where sp.product_weight=?"
                + " and sp.combined_network_weight=? and sp.bundle_id=? and sp.version_name=? and sp.main_parameter_id=? and sp.sub_parameter_id=?"
                ,subParameter.getEnabled(),p.getWeighting(),net.getNetworkWeight(),b.getID(),v.getName(),mainParameter.getId(),subParameter.getId());
        
        
        return updatedRows;
    }

    @Override
    public int updateParameterSubParameterStatus(Product p, Version v, Network net, Bundle b, Parameter mainParameter,User user) {
        
        int updatedRows = 0;
         String sqlQuery = "update product_parameter as pp set pp.sub_par=" + mainParameter.getHasSubParameters()+ " where pp.product_weight=" +p.getWeighting()
                + " and pp.combined_network_weight=" +net.getNetworkWeight() +" and pp.bundle_id=" + b.getID() + " and pp.version_name=" + v.getName() 
                + " and pp.main_parameter_id="+ mainParameter.getId() + "and pp.sub_parameter_id=" + mainParameter.getId();
        
        userActionLogger.logUserAction(user, "product_parameter", sqlQuery);
        
        updatedRows = this.jdbcTemplate.update("update product_parameter as pp set pp.sub_par=? where  pp.product_weight=?"
                + " and pp.combined_network_weight=? and pp.bundle_id=? and pp.version_name=? and pp.parameter_id=?"
                ,mainParameter.getHasSubParameters(),p.getWeighting(),net.getNetworkWeight(),b.getID(),v.getName(),mainParameter.getId());
        
        
        return updatedRows;
        
    }

    @Override
    public int addProductParameters(Product p, Version v, Network n, Bundle b, Parameter par,User user) {
        
        if(!productReleaseParameterExist(p,v,n,b,par)){
            String sqlQuery = "insert into product_parameter values(" + par.getValue() + "," + par.getId()+ "," + par.getHasSubParameters()
                                        + "," + true + "," + par.getVisible()+ "," + v.getName()+ "," + b.getID()+ "," +n.getNetworkWeight()+ "," + p.getWeighting()+ "," + par.getDisplayOrder() + ")";
            userActionLogger.logUserAction(user, "product_parameter", sqlQuery);
            
            return  this.jdbcTemplate.update("insert into product_parameter values(?,?,?,?,?,?,?,?,?,?)",par.getValue(),par.getId(),par.getHasSubParameters(),true,par.getVisible(),v.getName(),b.getID(),n.getNetworkWeight(),p.getWeighting(),par.getDisplayOrder());
        }
        return 0;
    }

    @Override
    public int addProductSubParameter(Product p, Version v, Network n, Bundle b, Parameter mainParameter, Parameter subParameter,User user) {
        
        String sqlQuery = "insert into sub_parameter values(" + subParameter.getValue() + "," + subParameter.getId()+ "," 
                                        + "," + true + "," + subParameter.getVisible()+ "," + mainParameter.getId()+ "," + v.getName()+ "," + b.getID()+ "," +n.getNetworkWeight()+ "," + p.getWeighting()+  ")";
        userActionLogger.logUserAction(user, "sub_parameter", sqlQuery);
        
        int updatedRows = this.jdbcTemplate.update("insert into sub_parameter values(?,?,?,?,?,?,?,?,?)",subParameter.getValue(),subParameter.getId(),true,
                    subParameter.getVisible(),mainParameter.getId(),v.getName(),b.getID(),n.getNetworkWeight(),p.getWeighting());
        
        return updatedRows;
    }

    @Override
    public Boolean isParameterUsed(Parameter p) {
        
        int rows = this.jdbcTemplate.queryForInt("select count(*) from product_parameter where parameter_id=?",p.getId());
        if(rows<1){
            
            rows = this.jdbcTemplate.queryForInt("select count(*) from sub_parameter where sub_parameter_id=?",p.getId());
            
            if(rows>0){
                return true;
            }
            
        }
        else{
            
            return true;
        }
        
        
        return false;
    }

    @Override
    public int deleteProductReleaseParameter(Product p, Version v, Network net, Bundle b, Parameter par,User user) {
        int deletedRows = 0;
        String sqlQuery = "delete from product_parameter where product_weight=" +  p.getWeighting()+ " and combined_network_weight=" + net.getNetworkWeight() + " and bundle_id=" +b.getID() +  " and version_name=" + v.getName()+ " and parameter_id=" + par.getId();
        userActionLogger.logUserAction(user, "product_parameter", sqlQuery);
        
        deletedRows = this.jdbcTemplate.update("delete from product_parameter where product_weight=? and combined_network_weight=? and bundle_id=? and "
                + " version_name=? and parameter_id=?",p.getWeighting(),net.getNetworkWeight(),b.getID(),v.getName(),par.getId());
        
        
        return deletedRows;
    }

    @Override
    public int deleteProductReleaseSubParameter(Product p, Version v, Network net, Bundle b, Parameter mainParameter,Parameter subParameter,User user) {
        int deletedRows = 0;
        String sqlQuery = "delete from sub_parameter where product_weight=" +  p.getWeighting()+ " and combined_network_weight=" + net.getNetworkWeight() + " and bundle_id=" +b.getID() 
                +  " and version_name=" + v.getName()+ " and main_parameter_id=" + mainParameter.getId() + " and sub_parameter_id= " + subParameter.getId();
        userActionLogger.logUserAction(user, "sub_parameter", sqlQuery);
        
        
        deletedRows = this.jdbcTemplate.update("delete from sub_parameter where product_weight=? and combined_network_weight=? and bundle_id=? and "
                + " version_name=? and main_parameter_id=?  and sub_parameter_id=?",p.getWeighting(),net.getNetworkWeight(),b.getID(),v.getName(),
                mainParameter.getId(),subParameter.getId());
        
        
        return deletedRows;
    }

    private boolean checkExistParameterType(String name) {
        int rows = this.jdbcTemplate.queryForInt("select count(*) from parameter_type as pt where pt.type=?",name);
        
        if(rows>0){
            return true;
        }
        
        return false;
    }
    
    
    private boolean checkExistParameter(String name){
        int rows = this.jdbcTemplate.queryForInt("select count(*) from parameter as pt where pt.name=?",name);
        
        if(rows>0){
            return true;
        }
        
        return false;
        
    }
    
    
    
    
    private void setupDefaultParameterType(String name) {
        ParameterType pt = new ParameterType(name);
        if(!(checkExistParameterType(name))){
           this.jdbcTemplate.update("insert into parameter_type values(?,?)",null,pt.getType());
            
        }
        
    }

    
    private ParameterType getParameterTypeID(String type) {
        return this.jdbcTemplate.queryForObject("select * from parameter_type as pt where pt.type=?",new Object[]{type}, new ParameterTypeMapper());
    }

    @Override
    public Boolean isProductReleaseParameterDefined(Product p, Version v, Network n, Bundle b, Parameter par) {
        int row = this.jdbcTemplate.queryForInt("select count(*) from product_parameter  where product_weight=? and combined_network_weight=? and bundle_id=? and "
                + " version_name=? and parameter_id=?",p.getWeighting(),n.getNetworkWeight(),b.getID(),v.getName(),par.getId());
        
        
        if(row>0){
            return true;
        }
        
        
        return false;
    }

    @Override
    public int updateProductReleaseParameterDisplayOrder(Product p, Version v, Network net, Bundle b, Parameter parameter,User user) {
       
        String sqlQuery = "update product_parameter set display_order=" + parameter.getDisplayOrder() + " where product_weight=" +p.getWeighting() + 
                " and combined_network_weight="+ net.getNetworkWeight()+ "  and bundle_id=" + b.getID()+ " and version_name=" + v.getName()+ " and parameter_id=" + parameter.getId();
        userActionLogger.logUserAction(user, "product_parameter", sqlQuery);
        
        
        return this.jdbcTemplate.update("update product_parameter set display_order=? where product_weight=? and combined_network_weight=? and bundle_id=? and "
                + " version_name=? and parameter_id=?",parameter.getDisplayOrder(), p.getWeighting(),net.getNetworkWeight(),b.getID(),v.getName(),parameter.getId());
       
    }

    @Override
    public int addProductDetailedVariable(Product p, Version v, Network n,Bundle b, Formula f, String variable,User user) {
        
         String sqlQuery = "insert into system_details values(" + f.getName() + "," + variable + "," + v.getName()+ "," + b.getID()+ "," + n.getNetworkWeight()+ "," + p.getWeighting() + ")";
        userActionLogger.logUserAction(user, "system_details", sqlQuery);
        
        return this.jdbcTemplate.update("insert into system_details values(?,?,?,?,?,?)",f.getName(),variable,v.getName(),b.getID(),n.getNetworkWeight(),p.getWeighting());
    }

    
    
    

   
        
    private static final class ParameterTypeMapper implements RowMapper<ParameterType>{

        @Override
        public ParameterType mapRow(ResultSet rs, int i) throws SQLException {
            
            ParameterType parType = new ParameterType(rs.getInt("id"),rs.getString("type"));
            
            return parType;        
        
        }
        
        
    }
    
    
    
    private static final class ParameterMapper implements RowMapper<Parameter>{

        @Override
        public Parameter mapRow(ResultSet rs, int i) throws SQLException {
            
            // Converting the Parameter Type String to Uppercase, this help when comparing the String values.
                      
            ParameterType parType = new ParameterType(rs.getInt("pt.id"),rs.getString("pt.type").toUpperCase());
            Parameter p = new Parameter(rs.getInt("p.id"),rs.getString("p.name"),parType,rs.getString("p.desc"));
            p.setSystem(rs.getBoolean("p.system"));
            
            return p;
        }
        
    }
    
     private static final class ProductReleaseParameterMapper implements RowMapper<Parameter>{

        @Override
        public Parameter mapRow(ResultSet rs, int i) throws SQLException {
             // Converting the type return to upper case for when I'm testing in the OUTPUT as I only need to check once.
            ParameterType parType = new ParameterType(rs.getInt("pt.id"),rs.getString("pt.type").toUpperCase());
            Parameter p = new Parameter(rs.getInt("p.id"),rs.getString("p.name"),parType,rs.getString("p.desc"));
            p.setHasSubParameters(rs.getBoolean("pp.sub_par"));
            p.setEnabled(rs.getBoolean("pp.enabled"));
            p.setVisible(rs.getInt("pp.visible_state"));
            p.setValue(rs.getDouble("pp.value"));
            p.setSystem(rs.getBoolean("p.system"));
            p.setDisplayOrder(rs.getInt("display_order"));
            
            return p;
        }
        
    }
     
     private static final class ProductReleaseSubParameterMapper implements RowMapper<Parameter> {

        @Override
        public Parameter mapRow(ResultSet rs, int i) throws SQLException {
                        
            ParameterType parType = new ParameterType(rs.getInt("pt.id"),rs.getString("pt.type").toUpperCase());
            Parameter p = new Parameter(rs.getInt("p.id"),rs.getString("p.name"),parType,rs.getString("p.desc"));
            p.setEnabled(rs.getBoolean("sp.enabled"));
            p.setVisible(rs.getInt("sp.visible_state"));
            p.setValue(rs.getDouble("sp.value"));
            p.setSystem(rs.getBoolean("p.system"));
            
            return p;
        }
         
         
         
     }
    
    @Override
    public void add(Parameter p,User user) {
        
       
        String sqlQuery = "insert into parameter value(" + p.getName()+ "," +  p.getParType().getId()  + "," +  p.getDesc()+ "," +  p.getSystem() + ")";
        userActionLogger.logUserAction(user, "parameter", sqlQuery);
         //Using null value as the key is auto-incremented by the database.
        this.jdbcTemplate.update("insert into parameter value(?,?,?,?,?)",null,p.getName(),p.getParType().getId(),p.getDesc(),p.getSystem());
    }
    
    
    private void setupDefaultParameter(String name,String desc,String type,boolean system){
         ParameterType parType = getParameterTypeID(type);
        Parameter p = new Parameter(name,parType,desc);
        p.setSystem(system);
        
        if(!parameterExist(name)){
            this.jdbcTemplate.update("insert into parameter value(?,?,?,?,?)",null,p.getName(),p.getParType().getId(),p.getDesc(),p.getSystem());
            
        }
        
        
        
    }

    @Override
    public List<Parameter> getListParameter() {
        List<Parameter> parList = null;
        parList=this.jdbcTemplate.query("select * from parameter as p inner join parameter_type as pt on p.parameter_type_id=pt.id", new ParameterMapper());
        return parList;
    }

    @Override
    public void update(Parameter p,User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Parameter p,User user) {
        String sqlQuery = "delete from parameter where id= " +p.getId();
        userActionLogger.logUserAction(user, "parameter", sqlQuery);
        
        this.jdbcTemplate.update("delete from parameter where id=?",p.getId());
    }

    @Override
    public void addParameterType(String type,User user) {
        String sqlQuery = "insert into parameter_type values(" + type + ")";
        userActionLogger.logUserAction(user, "parameter_type", sqlQuery);
        
        this.jdbcTemplate.update("insert into parameter_type values(?,?)",null,type);
    }
    
}
