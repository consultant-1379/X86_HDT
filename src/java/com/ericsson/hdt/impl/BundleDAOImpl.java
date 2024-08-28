/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.impl;

import com.ericsson.hdt.dao.BundleDAO;
import com.ericsson.hdt.dao.SystemActionLoggerDAO;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Network;
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
public class BundleDAOImpl extends BaseDAOImpl implements BundleDAO{
    
    private final JdbcTemplate jdbcTemplate;
    private SystemActionLoggerDAO userActionLogger = new SystemActionLoggerDAOImpl();
    
    public BundleDAOImpl(){
        super();
        jdbcTemplate = new JdbcTemplate(dataSource);    
        setupDefaultBundle(StaticInputs.bundle_none,StaticInputs.bundle_none);
        setupDefaultBundle(StaticInputs.bundle_default,StaticInputs.bundle_default);
        
    }

    @Override
    public List<Bundle> getProductReleaseBundle(Product p, Version v, Network n) {
        List<Bundle> bundles = this.jdbcTemplate.query("select b.id,b.name,b.description from product_release as pr inner join bundle as b on "
                + " pr.bundle_id=b.id where pr.product_weight=? and pr.version_name=? and pr.combined_network_weight=? order by b.id DESC", 
                new Object[]{p.getWeighting(),v.getName(),n.getNetworkWeight()},new BundleMapper());
    
    
    return bundles;
    }

    @Override
    public int setGA(Product p, Version v, Network net, Bundle b,User user) {
        int updatedRows = 0;
        String sql = "update product_release as pr set pr.GA=" + b.getGA() + "  where pr.product_weight=" + p.getWeighting()+ 
                " and pr.combined_network_weight=" + net.getNetworkWeight() + " and pr.version_name= " +v.getName() + " and pr.bundle_id=" + b.getID();
        userActionLogger.logUserAction(user, "product_release", sql);
        updatedRows = this.jdbcTemplate.update("update product_release as pr set pr.GA=? where pr.product_weight=? and pr.combined_network_weight=?"
                + " and pr.version_name=? and pr.bundle_id=?",b.getGA(),p.getWeighting(),net.getNetworkWeight(),v.getName(),b.getID());
       
        return updatedRows;
    }

    @Override
    public List<Bundle> getProductReleaseBundleGA(Product p, Version v, Network n) {
        List<Bundle> bundles = this.jdbcTemplate.query("select b.id,b.name,b.description,pr.GA from product_release as pr inner join bundle as b"
                + " on pr.bundle_id=b.id where pr.product_weight=? and pr.version_name=? and pr.combined_network_weight=? order by b.id DESC"
                ,new Object[]{p.getWeighting(),v.getName(),n.getNetworkWeight()}, new ProductReleaseGABundleMapper());
        
        
        return bundles;
    }

    @Override
    public int deleteProductReleaseBundle(Product p, Version v, Network n,Bundle b,User user) {
       int updatedRows = 0;
       
        updatedRows += this.jdbcTemplate.update("DELETE from role_hw_config where product_weight=? and "
                + "combined_network_weight=? and version_name=? and bundle_id=?;",new Object[]{p.getWeighting(),n.getNetworkWeight(),v.getName(),b.getID()});
        
        
        updatedRows += this.jdbcTemplate.update("DELETE from role_dependency where product_weight=? and "
                + "combined_network_weight=? and version_name=? and bundle_id=?;",new Object[]{p.getWeighting(),n.getNetworkWeight(),v.getName(),b.getID()});
        
        
        updatedRows +=this.jdbcTemplate.update("DELETE from product_release_role where product_weight=? and "
                + "combined_network_weight=? and version_name=? and bundle_id=?;",new Object[]{p.getWeighting(),n.getNetworkWeight(),v.getName(),b.getID()});
       
       
        
        updatedRows +=this.jdbcTemplate.update("DELETE from product_release_notes where product_weight=? and "
                + "combined_network_weight=? and version_name=? and bundle_id=?;",new Object[]{p.getWeighting(),n.getNetworkWeight(),v.getName(),b.getID()});
        
        updatedRows +=this.jdbcTemplate.update("DELETE from network_limits   where product_weight=? and "
                + "combined_network_weight=? and version_name=? and bundle_id=?;",new Object[]{p.getWeighting(),n.getNetworkWeight(),v.getName(),b.getID()});
        
        updatedRows +=this.jdbcTemplate.update("DELETE from product_url_link where product_weight=? and "
                + "combined_network_weight=? and version_name=? and bundle_id=?;",new Object[]{p.getWeighting(),n.getNetworkWeight(),v.getName(),b.getID()});
        
        
        updatedRows +=this.jdbcTemplate.update("DELETE from rack  where product_weight=? and "
                + "combined_network_weight=? and version_name=? and bundle_id=?;",new Object[]{p.getWeighting(),n.getNetworkWeight(),v.getName(),b.getID()});
        
        updatedRows +=this.jdbcTemplate.update("DELETE from user_stored_parameters  where product_weight=? and "
                + "combined_network_weight=? and version_name=? and bundle_id=?;",new Object[]{p.getWeighting(),n.getNetworkWeight(),v.getName(),b.getID()});
        
        
        updatedRows +=this.jdbcTemplate.update("DELETE from sub_parameter where product_weight=? and "
                + "combined_network_weight=? and version_name=? and bundle_id=?;",new Object[]{p.getWeighting(),n.getNetworkWeight(),v.getName(),b.getID()});
        
        
        updatedRows +=this.jdbcTemplate.update("DELETE from product_parameter where product_weight=? and "
                + "combined_network_weight=? and version_name=? and bundle_id=?;",new Object[]{p.getWeighting(),n.getNetworkWeight(),v.getName(),b.getID()});
        
         updatedRows +=this.jdbcTemplate.update("DELETE from system_details where product_weight=? and "
                + "combined_network_weight=? and version_name=? and bundle_id=?;",new Object[]{p.getWeighting(),n.getNetworkWeight(),v.getName(),b.getID()});
        
        
        updatedRows +=this.jdbcTemplate.update("DELETE from rack where product_weight=? and "
                + "combined_network_weight=? and version_name=? and bundle_id=?;",new Object[]{p.getWeighting(),n.getNetworkWeight(),v.getName(),b.getID()});
        
        
        
        // This must be the last table deleted as it hold all the primary keys that are foreign keys in the above tables
        
         updatedRows +=this.jdbcTemplate.update("DELETE from product_release  where product_weight=? and "
                + "combined_network_weight=? and version_name=? and bundle_id=?;",new Object[]{p.getWeighting(),n.getNetworkWeight(),v.getName(),b.getID()});
        
         String sql = "delete from product_release where pr.product_weight=" + p.getWeighting()+ 
                " and pr.combined_network_weight=" + n.getNetworkWeight() + " and pr.version_name= " +v.getName() + " and pr.bundle_id=" + b.getID();
         userActionLogger.logUserAction(user, "product_release", sql);
         
        return updatedRows;
    }

    @Override
    public Boolean isBundleUsedForProductRelease(Bundle b) {
        int rows = this.jdbcTemplate.queryForInt("select count(*) from product_release where bundle_id=?",b.getID());
        if(rows>0){
            
            return true;
        }
        
        
        return false;
        
    }

    
    private Boolean checkExistingBundle(String name){
        
        int rows = this.jdbcTemplate.queryForInt("select count(*) from bundle where name=?",name);
        
        if(rows>0){
            
            return true;
        }
        
        return false;
        
    }
    private void setupDefaultBundle(String name, String description) {
        
        Bundle b = new Bundle(name,description);
        
        if(!(checkExistingBundle(name))){
            
            //add(b);
            this.jdbcTemplate.update("insert into bundle values(?,?,?)",new Object[]{null,name,description});
        }
        
        
        
        
    }

    @Override
    public List<Bundle> getProductReleaseBundleGASet(Product p, Version v, Network n) {
        return this.jdbcTemplate.query("select b.id,b.name,b.description,pr.GA from product_release as pr "
               + "inner join bundle as b on pr.bundle_id=b.id where pr.product_weight=?"
                + " and pr.version_name=? and pr.combined_network_weight=? and pr.GA=? order by b.id DESC",
               new Object[]{p.getWeighting(),v.getName(),n.getNetworkWeight(),true}, new ProductReleaseGABundleMapper());
        
        
        
    }

    
    
    
    private static final class BundleMapper implements RowMapper<Bundle>{

        @Override
        public Bundle mapRow(ResultSet rs, int i) throws SQLException {
            Bundle b = new Bundle(rs.getInt("id"),rs.getString("name"),rs.getString("description"));
            
            return b;
        }
        
        
    }
    
    private static final class ProductReleaseGABundleMapper implements RowMapper<Bundle>{

        @Override
        public Bundle mapRow(ResultSet rs, int i) throws SQLException {
            Bundle b = new Bundle(rs.getInt("b.id"),rs.getString("b.name"),rs.getString("b.description"),rs.getBoolean("pr.GA"));
            
            return b;
        }
        
        
    }
    
    
    @Override
    public Bundle getBundle(int id) {
        
        Bundle b = this.jdbcTemplate.queryForObject("select * from bundle where id=?",new Object [] { id }, new BundleMapper());
        
        return b;
     }
    
    @Override
    public Bundle getBundle(String name) {
        
        Bundle b = this.jdbcTemplate.queryForObject("select * from bundle where name=?",new Object [] { name }, new BundleMapper());
        
        return b;
     }
    
    
    @Override
    public void add(Bundle b,User user) {
        
        
        String sql = "insert into bundle values(" + b.getName() + "," + b.getDescription() + ")";
        userActionLogger.logUserAction(user, "bundle", sql);
        this.jdbcTemplate.update("INSERT into bundle VALUES(?,?,?)", null,b.getName(),b.getDescription());
    }

    @Override
    public List<Bundle> getListBundle() {
        
        // May have to initialize with bundle = new ArrayList<Bundle>()
        List<Bundle> bundles;
        
        bundles = this.jdbcTemplate.query("SELECT * from bundle", new BundleMapper());
        
        return bundles;
        
        
    }

    @Override
    public void delete(Bundle b,User user) {
        
        String sql = "delete from bundle where id=" + b.getID();
        userActionLogger.logUserAction(user, "bundle", sql);
        
        this.jdbcTemplate.update("DELETE FROM bundle where id=?", new Object[]{b.getID()});
    }

    @Override
    public int updateDescription(Bundle b,User user) {
        int updatedRows = 0;
        String sql = "update bundle as b set b.description=" + b.getDescription() + " where b.id=" + b.getID();
        userActionLogger.logUserAction(user, "bundle", sql);
        updatedRows = this.jdbcTemplate.update("update bundle as b set b.description=? where b.id=?",b.getDescription(),b.getID());
        
        return updatedRows;
    }
    
}
