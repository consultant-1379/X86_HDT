/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.impl;

import com.ericsson.hdt.dao.NetworkDAO;
import com.ericsson.hdt.dao.SystemActionLoggerDAO;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
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
public class NetworkDAOImpl extends BaseDAOImpl  implements NetworkDAO{
    
   

     private final JdbcTemplate jdbcTemplate;
     private SystemActionLoggerDAO userActionLogger = new SystemActionLoggerDAOImpl();
     
     public NetworkDAOImpl(){
         
         super();
         jdbcTemplate = new JdbcTemplate(dataSource);
     }

 
    private int getWeight() {
        int weight = this.jdbcTemplate.queryForInt("select MAX(weight) from networks");
        return (weight * 2);
    }

    @Override
    public Network getIndividualNetwork(String name) {
        
        Network n = this.jdbcTemplate.queryForObject("select * from networks where name=?",new Object[]{name},new NetworkMapper());
        
        return n;
        
        
    }

    @Override
    public void addCombinedNetworks(Network n,User user) {
        if(!(isExist(n))){
            String sqlQuery = "insert into combined_network values("+ n.getNetworkWeight() + "," + n.getName() +")";
            userActionLogger.logUserAction(user, "combined_network", sqlQuery);
                this.jdbcTemplate.update("insert into combined_network values(?,?)",n.getNetworkWeight(),n.getName());
        }
    }
    
    private Boolean isExist(Network n){
        int rows = this.jdbcTemplate.queryForInt("select count(*) from combined_network where weight=? and name=? ",n.getNetworkWeight(),n.getName());
        if(rows==1){
            return true;
        }
        
        return false;
        
    }

    @Override
    public int updateName(Network n,User user) {
        int updatedRows = 0;
        String sqlQuery = "update networks set name=" + n.getName() +  " where weight=" + n.getNetworkWeight();
        userActionLogger.logUserAction(user, "networks", sqlQuery);
        updatedRows = this.jdbcTemplate.update("update networks set name=? where weight=?",n.getName(),n.getNetworkWeight());
        return updatedRows;
        
    }

    @Override
    public int setGA(Product p, Version v, Network net,User user) {
        int updatedRows = 0;
        String sqlQuery = "update product_release set pr.GA=" + net.getGA()+ " where pr.product_weight=" +  p.getWeighting() + 
                " and pr.combined_network_weight="  + net.getNetworkWeight() + " and pr.version_name=" + v.getName() ;
        userActionLogger.logUserAction(user, "product_release", sqlQuery);
        updatedRows = this.jdbcTemplate.update("update product_release as pr set pr.GA=? where pr.product_weight=? and pr.combined_network_weight=?"
                + " and pr.version_name=?", net.getGA(),p.getWeighting(),net.getNetworkWeight(),v.getName());
        
        
        return updatedRows;
    }

    @Override
    public List<Network> getProductReleaseNetwork(Product p, Version v) {
        List<Network> networks = null;
        
        networks = this.jdbcTemplate.query("select Distinct cn.name,pr.combined_network_weight from product_release as pr  inner join "
                + " combined_network as cn on pr.combined_network_weight=cn.weight where pr.product_weight=? "
                + " and pr.version_name=?",new Object[]{p.getWeighting(),v.getName()}, new ProductReleaseNetworkMapper());
        return networks;
    }
    
    

    @Override
    public Boolean getProductReleaseNetworkGAStatus(Integer weighting, Version v, Network n) {
        int status = this.jdbcTemplate.queryForInt("select count(*) from product_release as pr where pr.product_weight=? "
                + "and pr.version_name=? and pr.combined_network_weight=? and pr.GA=?", new Object[]{weighting,v.getName(),n.getNetworkWeight(),true});
        
        
        if(status>=1){
            return true;
        }
        
        return false; 
    }

    @Override
    public int deleteProductReleaseNetwork(Product p, Version v, Network n,User user) {
         int updatedRows = 0;
       
        updatedRows += this.jdbcTemplate.update("delete from role_hw_config where product_weight=? and "
                + "combined_network_weight=? and version_name=?;",p.getWeighting(),n.getNetworkWeight(),v.getName());
        
        updatedRows += this.jdbcTemplate.update("delete from role_dependency where product_weight=? and "
                + "combined_network_weight=? and version_name=?;",p.getWeighting(),n.getNetworkWeight(),v.getName());
        
        
        
        updatedRows +=this.jdbcTemplate.update("delete from product_release_role where product_weight=? and "
                + "combined_network_weight=? and version_name=?;",p.getWeighting(),n.getNetworkWeight(),v.getName());
       
       
        
        updatedRows +=this.jdbcTemplate.update("delete from product_release_notes where product_weight=? and "
                + "combined_network_weight=? and version_name=?;",p.getWeighting(),n.getNetworkWeight(),v.getName());
        
        updatedRows +=this.jdbcTemplate.update("delete from network_limits  where product_weight=? and "
                + "combined_network_weight=? and version_name=?;",p.getWeighting(),n.getNetworkWeight(),v.getName());
        
        updatedRows +=this.jdbcTemplate.update("delete from product_url_link where product_weight=? and "
                + "combined_network_weight=? and version_name=?;",p.getWeighting(),n.getNetworkWeight(),v.getName());
        
        
        updatedRows +=this.jdbcTemplate.update("delete from rack  where product_weight=? and "
                + "combined_network_weight=? and version_name=?;",p.getWeighting(),n.getNetworkWeight(),v.getName());
        
        updatedRows +=this.jdbcTemplate.update("delete from user_stored_parameters  where product_weight=? and "
                + "combined_network_weight=? and version_name=?;",p.getWeighting(),n.getNetworkWeight(),v.getName());
        
        
        updatedRows +=this.jdbcTemplate.update("delete from sub_parameter  where product_weight=? and "
                + "combined_network_weight=? and version_name=?;",p.getWeighting(),n.getNetworkWeight(),v.getName());
        
        
        updatedRows +=this.jdbcTemplate.update("delete from product_parameter where product_weight=? and "
                + "combined_network_weight=? and version_name=?;",p.getWeighting(),n.getNetworkWeight(),v.getName());
        
        
        updatedRows +=this.jdbcTemplate.update("delete from rack where product_weight=? and "
                + "combined_network_weight=? and version_name=?;",p.getWeighting(),n.getNetworkWeight(),v.getName());
        
        updatedRows +=this.jdbcTemplate.update("delete from system_details where product_weight=? and "
                + "combined_network_weight=? and version_name=?;",p.getWeighting(),n.getNetworkWeight(),v.getName());
        
        
        // This must be the last table deleted as it hold all the primary keys that are foreign keys in the above tables
        
         updatedRows +=this.jdbcTemplate.update("delete from product_release where product_weight=? and "
                + "combined_network_weight=? and version_name=?;",p.getWeighting(),n.getNetworkWeight(),v.getName());
         
         String sqlQuery = "delete from product_release where product_weight=" +    p.getWeighting() + " and combined_network_weight=" + n.getNetworkWeight() + " and version_name=" + v.getName();
         userActionLogger.logUserAction(user, "networks", sqlQuery);
        
        return updatedRows;
    }
    
     @Override
    public int deleteSystemScriptVariable(Product p, Version v, Network net,User user) {
         
         String sqlQuery = "delete from system_details where product_weight=" +    p.getWeighting() + " and combined_network_weight=" + net.getNetworkWeight() + " and version_name=" + v.getName();
         userActionLogger.logUserAction(user, "networks", sqlQuery);
         
        return this.jdbcTemplate.update("delete from system_details where product_weight=? and "
                + "combined_network_weight=? and version_name=?;",p.getWeighting(),net.getNetworkWeight(),v.getName());
    }


    @Override
    public List<Network> getCombinedNetwork(Network n) {
        List<Network> network;
        
        network = this.jdbcTemplate.query("select * from combined_network as cn where cn.weight=?",new Object[]{n.getNetworkWeight()},new NetworkMapper());
        
        return network;
    }

    @Override
    public List<Network> getProductReleaseNetworkGA(Product p, Version v) {
        List<Network> networks = null;
        
        networks = this.jdbcTemplate.query("select Distinct cn.name,pr.combined_network_weight from product_release as pr  inner join "
                + " combined_network as cn on pr.combined_network_weight=cn.weight where pr.product_weight=? "
                + " and pr.version_name=? and pr.GA=?",new Object[]{p.getWeighting(),v.getName(),true}, new ProductReleaseNetworkMapper());
        return networks;
    }

    @Override
    public List<Network> getUniqueCombinedNetworkWeighting(Product product, Version version) {
        List<Network> networks = this.jdbcTemplate.query("select DISTINCT cn.weight from product_release as pr  inner join "
                + " combined_network as cn on pr.combined_network_weight=cn.weight where pr.product_weight=? "
                + " and pr.version_name=?",new Object[]{product.getWeighting(),version.getName()}, new NetworkWeightMapper());
        
        
        return networks;
    }

   
   
    
    private static final class ProductReleaseNetworkMapper implements RowMapper<Network>{

        @Override
        public Network mapRow(ResultSet rs, int i) throws SQLException {
            Network n = new Network(rs.getString("cn.name"),rs.getInt("pr.combined_network_weight"));
            return n;
        }
        
        
        
    }
    
    
    private static final class NetworkMapper implements RowMapper<Network>{

        @Override
        public Network mapRow(ResultSet rs, int i) throws SQLException {
            Network n = new Network(rs.getString("name"),rs.getInt("weight"));
            
            return n;
            
        }
        
        
        
    }
    private static final class NetworkWeightMapper implements RowMapper<Network>{

        @Override
        public Network mapRow(ResultSet rs, int i) throws SQLException {
            Network n = new Network(rs.getInt("weight"));
            
            return n;
            
        }
        
        
        
    }
    
    @Override
    public List<Network> getNetworks() {
       List<Network> networks = this.jdbcTemplate.query("select * from networks", new NetworkMapper());
       
       return networks;
    }

    @Override
    public void add(Network n,User user)  {
        int rows = this.jdbcTemplate.queryForInt("select count(*) from networks");
        
        if(rows>=1){
            String sqlQuery = "insert into networks values(" + n.getName()+ ", " +getWeight() + ")";
            userActionLogger.logUserAction(user, "networks", sqlQuery);
            this.jdbcTemplate.update("insert into networks values(?,?)",n.getName(),getWeight());
            
        }
        else{
            
            this.jdbcTemplate.update("insert into networks values(?,1)",n.getName());
            
        }
        
        
    }

    @Override
    public void delete(Network n,User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   
}
