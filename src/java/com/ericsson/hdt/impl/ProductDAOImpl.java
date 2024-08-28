/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.impl;

import com.ericsson.hdt.dao.ProductDAO;
import com.ericsson.hdt.dao.SystemActionLoggerDAO;
import com.ericsson.hdt.model.Formula;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
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
public class ProductDAOImpl extends BaseDAOImpl implements ProductDAO {

    private final JdbcTemplate jdbcTemplate;
    private SystemActionLoggerDAO userActionLogger = new SystemActionLoggerDAOImpl();
    
    public ProductDAOImpl(){
        super();
        jdbcTemplate = new JdbcTemplate(dataSource);
       }

    @Override
    public Product getIndividualProduct(String name) {
        Product p = this.jdbcTemplate.queryForObject("select * from product where name=?",new Object[]{name},new ProductMapper());
               
        return p;
    }

    @Override
    public void addCombinedProduct(Product p,User user) {
        if(!(isExist(p))){
            
            String sqlQuery = "insert into valid_product values(?" + p.getWeighting() + "," + p.getName() + ")";
            userActionLogger.logUserAction(user, "valid_product", sqlQuery);
            this.jdbcTemplate.update("insert into valid_product values(?,?)",p.getWeighting(),p.getName());
        }
        
    }

   
    private Boolean isExist(Product p) {
        
        int rows = this.jdbcTemplate.queryForInt("select count(*) from valid_product where product_weight=?",p.getWeighting());
        if(rows==1){
            return true;
        }
        
        return false;
        
    }

    @Override
    public int deleteProduct(Product p,User user) {
        
         int updatedRows = 0;
       
        updatedRows += this.jdbcTemplate.update("delete from role_hw_config  where product_weight=?;"
                ,p.getWeighting());
        
        updatedRows += this.jdbcTemplate.update("delete from role_dependency  where product_weight=?;"
                ,p.getWeighting());
        
        updatedRows +=this.jdbcTemplate.update("delete from product_release_role where product_weight=?;"
                ,p.getWeighting());
       
       
        
        updatedRows +=this.jdbcTemplate.update("delete from product_release_notes  where product_weight=?; "
                ,p.getWeighting());
        
        updatedRows +=this.jdbcTemplate.update("delete from network_limits where product_weight=?;"
               ,p.getWeighting());
        
        updatedRows +=this.jdbcTemplate.update("delete from product_url_link where product_weight=?;"
                ,p.getWeighting());
        
        
        updatedRows +=this.jdbcTemplate.update("delete from rack where product_weight=?; "
                ,p.getWeighting());
        
        
        updatedRows +=this.jdbcTemplate.update("delete from user_stroed_parameters where product_weight=?; "
                ,p.getWeighting());
        
        
        updatedRows +=this.jdbcTemplate.update("delete from sub_parameter where product_weight=? ;"
                ,p.getWeighting());
        
        
        updatedRows +=this.jdbcTemplate.update("delete from product_parameter where product_weight=? ;"
                ,p.getWeighting());
        
        
        updatedRows +=this.jdbcTemplate.update("delete from rack where product_weight=? ;"
                ,p.getWeighting());
        
         updatedRows +=this.jdbcTemplate.update("delete from system_details where product_weight=? ;"
                ,p.getWeighting());
        
        
        // These must be the last table deleted as it hold all the primary keys that are foreign keys in the above tables
        
         updatedRows +=this.jdbcTemplate.update("delete from product_release where product_weight=?;"
                ,p.getWeighting());
         
          updatedRows +=this.jdbcTemplate.update("delete from valid_product where product_weight=?;"
                ,p.getWeighting());
          
          String sqlQuery = "delete from valid_product where product_weight=" + p.getWeighting();
            userActionLogger.logUserAction(user, "valid_product", sqlQuery);
            sqlQuery = "delete from product_release where product_weight=" + p.getWeighting();
            userActionLogger.logUserAction(user, "valid_product", sqlQuery);
        
        return updatedRows;
        
    }
    
    
    @Override
    public Product getIndividualProduct(Integer weight) {
        Product p = this.jdbcTemplate.queryForObject("select * from product where product_weight=?",new Object[]{weight},new ProductMapper());
               
        return p;
    }

    @Override
    public List<Product> getAllVisibileProducts() {
         return this.jdbcTemplate.query("select * from product where GA=?",new Object[]{true}, new ProductMapper());
    }

    
    
    private static final class ProductMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs, int i) throws SQLException {
            Product p = new Product(rs.getString("name"),rs.getInt("product_weight"),rs.getBoolean("GA"));
            return p;
        }
        
        
        
        
    }
    
    private static final class CombinedProductMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs, int i) throws SQLException {
            Product p = new Product(rs.getString("name"),rs.getInt("product_weight"));
            return p;
        }
        
        
    }
    
    
    
    @Override
    public Product getCombinedProduct(Product p) {
        Product combinedProduct = null;
        combinedProduct = this.jdbcTemplate.queryForObject("select * from valid_product where product_weight=?", new Object[]{p.getWeighting()},new CombinedProductMapper());
        
        return combinedProduct;
    }
    
    @Override
    public void updateName(Product p,User user) {
        this.jdbcTemplate.update("UPDATE  product set name=? WHERE product_weight=?",p.getName(),p.getWeighting());
        
        String sqlQuery = "UPDATE  product set name=" +  p.getName() + " WHERE product_weight=" + p.getWeighting();
        userActionLogger.logUserAction(user, "product", sqlQuery);
        
    }

    @Override
    public void updateWeight(Product p,User user) {
         String sqlQuery = "UPDATE  product set product_weight=" +  p.getWeighting() +  " WHERE  name=" + p.getName();
        userActionLogger.logUserAction(user, "product", sqlQuery);
        this.jdbcTemplate.update("UPDATE  product set product_weight=? WHERE name=?",p.getWeighting(),p.getName());
    }

    @Override
    public void setGA(Product p,User user) {
        this.jdbcTemplate.update("UPDATE  product set GA=? WHERE name=?",p.isGA(),p.getName());
        
        String sqlQuery = "UPDATE  product set GA=" +  p.isGA() + " WHERE  name=" + p.getName();
        userActionLogger.logUserAction(user, "product", sqlQuery);
    }

    @Override
    public void removeGA(Product p,User user) {
        this.jdbcTemplate.update("UPDATE  product set GA=? where name=?",p.isGA(),p.getName());
        
        String sqlQuery = "UPDATE  product set GA=" +  p.isGA() + " WHERE  name=" + p.getName();
        userActionLogger.logUserAction(user, "product", sqlQuery);
    }

    private int getWeight() {
        int weight = this.jdbcTemplate.queryForInt("select MAX(product_weight) from product");
        return (weight * 2);
    }
    
    
    @Override
    public void add(Product p,User user) {
        
        int rows = this.jdbcTemplate.queryForInt("select count(*) from product");
        if(rows>=1){
            this.jdbcTemplate.update("insert into product values(?,?,?)", new Object[]{p.getName(),getWeight(),false});
            
            String sqlQuery = "insert into product values("+ p.getName() + "," + getWeight() + "'" + false;
            userActionLogger.logUserAction(user, "product", sqlQuery);
        }
        else{
            
            this.jdbcTemplate.update("insert into product values(?,?,?)", new Object[]{p.getName(),1,false});
        }
    }

    

    @Override
    public List<Product> getAllProducts() {
        List<Product> p = null;
        p = this.jdbcTemplate.query("select * from product", new ProductMapper());
        
        return p;
    }

    @Override
    public void delete(Product p,User user) {
        String sqlQuery = "DELETE from product where name=" + p.getName() + " AND product_weight="+ p.getWeighting();
        userActionLogger.logUserAction(user, "product", sqlQuery);
        this.jdbcTemplate.update("DELETE from product where name=? AND product_weight=?",p.getName(),p.getWeighting());
    }
    
}
