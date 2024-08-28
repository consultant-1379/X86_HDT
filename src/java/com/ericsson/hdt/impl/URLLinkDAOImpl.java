/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.impl;

import com.ericsson.hdt.dao.SystemActionLoggerDAO;
import com.ericsson.hdt.dao.URLLinkDAO;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.UrlLink;
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
public class URLLinkDAOImpl extends BaseDAOImpl implements URLLinkDAO{

    private final JdbcTemplate jdbcTemplate;
    private SystemActionLoggerDAO userActionLogger = new SystemActionLoggerDAOImpl();
    
    public URLLinkDAOImpl(){
        super();
        jdbcTemplate = new JdbcTemplate(dataSource);
        
    }

    @Override
    public List<UrlLink> getLinkList() {
        List<UrlLink> links = null;
        links = this.jdbcTemplate.query("select * from url_link", new URLLinkMapper());
        
        return links;
    }

    @Override
    public UrlLink getIndividualLinks(int id) {
        UrlLink u = this.jdbcTemplate.queryForObject("select * from url_link where id=?",new Object[]{id},new URLLinkMapper());
        
        return u;
    }

    @Override
    public List<UrlLink> getProductReleaseURLLinks(Product p, Version v, Network net, Bundle b) {
        List<UrlLink> productReleaseURLLinks = null;
        
        productReleaseURLLinks = this.jdbcTemplate.query("select * from product_url_link as pul inner join url_link as ul on "
                + "pul.url_link_id=ul.id where pul.product_weight=? and pul.combined_network_weight=? and pul.bundle_id=? "
                + "and pul.version_name=? and ul.default_link!=?", new Object[]{p.getWeighting(),net.getNetworkWeight(),b.getID(),v.getName(),true},new ProductReleaseURLLinkMapper());
        
        return productReleaseURLLinks;
    }

    @Override
    public int updateProductReleaseURLLink(Product p, Version v, Network net, Bundle b, UrlLink url,User user) {
        int updatedRows = 0 ;
        String sqlQuery="update product_url_link as pul set pul.visible=" + url.getVisible() + " where pul.product_weight=" + p.getWeighting() +  " and pul.combined_network_weight=" + net.getNetworkWeight() + 
                " and pul.bundle_id=" + b.getID() + 
                "and pul.version_name=" + v.getName() + " and pul.url_link_id=" + url.getId();
        userActionLogger.logUserAction(user, "product_url_link", sqlQuery);
        
        updatedRows = this.jdbcTemplate.update("update product_url_link as pul set pul.visible=? where pul.product_weight=?"
                + " and pul.combined_network_weight=? and pul.bundle_id=? and pul.version_name=? and pul.url_link_id=?",
                url.getVisible(),p.getWeighting(),net.getNetworkWeight(),b.getID(),v.getName(),url.getId());
        
        return updatedRows;
    }

    @Override
    public int updateDescription(UrlLink url,User user) {
        int updatedRows = 0;
        String sqlQuery=" update product_url_link as ul set ul.help_menu_text=" + url.getDesc() + " where ul.url_link_id=" + url.getId();
        userActionLogger.logUserAction(user, "product_url_link", sqlQuery);
        
        updatedRows = this.jdbcTemplate.update("update url_link as ul set ul.desc=? where ul.id=?",url.getDesc(),url.getId());
        updatedRows = this.jdbcTemplate.update("update product_url_link as ul set ul.help_menu_text=? where ul.url_link_id=?",url.getDesc(),url.getId());
        
        
        
        return updatedRows;
    }

    @Override
    public int updateLink(UrlLink url,User user) {
        int updatedRows = 0;
        String sqlQuery="update url_link as ul set ul.url=" + url.getLink() + " where ul.id=" + url.getId() ;
        userActionLogger.logUserAction(user, "url_link", sqlQuery);
        updatedRows = this.jdbcTemplate.update("update url_link as ul set ul.url=? where ul.id=?",url.getLink(),url.getId());
        
        return updatedRows;
    }

    @Override
    public int addProductUrlLinks(Product p, Version v, Network n, Bundle b, UrlLink url,User user) {
        String sqlQuery="insert into product_url_link values(" + url.getId() + "," + true  + "," + url.getDesc()  + "," + v.getName()  + "," + b.getID()  + "," + n.getNetworkWeight()  + "," + p.getWeighting() + ")";
        userActionLogger.logUserAction(user, "product_url_link", sqlQuery);
        int updatedRows = this.jdbcTemplate.update("insert into product_url_link values(?,?,?,?,?,?,?)",url.getId(),true,url.getDesc(),v.getName(),b.getID(),n.getNetworkWeight(),p.getWeighting());
        
        return updatedRows;
    }

    @Override
    public Boolean isHelpMenuLinkUsed(UrlLink url) {
        int rows = this.jdbcTemplate.queryForInt("select count(*) from product_url_link where url_link_id=?", url.getId());
        
        
        if(rows>0){
            
            return true;
        }
        
        
        return false;
    }

    @Override
    public int deleteProductReleaseHelpMenu(Product p, Version v, Network n, Bundle b, UrlLink url,User user) {
        int deletedRows = 0 ; 
        String sqlQuery="delete from product_url_link where product_weight=" +  p.getWeighting() + " and combined_network_weight=" + n.getNetworkWeight() + " and bundle_id=" + b.getID() + 
                " and version_name=" + v.getName() + " and url_link_id=" + url.getId() ;
        userActionLogger.logUserAction(user, "product_url_link", sqlQuery);
        deletedRows = this.jdbcTemplate.update("delete from product_url_link where product_weight=? and combined_network_weight=? and "
                + " bundle_id=? and version_name=? and url_link_id=?",p.getWeighting(),n.getNetworkWeight(),b.getID(),v.getName(),
                url.getId());
        
        return deletedRows;
    }

    @Override
    public List<UrlLink> getDefaultLinks() {
        List<UrlLink> links;
        links = this.jdbcTemplate.query("select * from url_link where default_link=?",new Object[]{true}, new URLLinkMapper());
        
        return links;
    }

    @Override
    public List<UrlLink> getVisibleProductReleaseURLLinks(Product p, Version v, Network net, Bundle b) {
         List<UrlLink> productReleaseURLLinks = null;
        
        productReleaseURLLinks = this.jdbcTemplate.query("select * from product_url_link as pul inner join url_link as ul on "
                + "pul.url_link_id=ul.id where pul.product_weight=? and pul.combined_network_weight=? and pul.bundle_id=? "
                + "and pul.version_name=? and pul.visible=?", new Object[]{p.getWeighting(),net.getNetworkWeight(),b.getID(),v.getName(),true},new ProductReleaseURLLinkMapper());
        
        return productReleaseURLLinks;
    }

    
    private static final class ProductReleaseURLLinkMapper implements RowMapper<UrlLink>{

        @Override
        public UrlLink mapRow(ResultSet rs, int i) throws SQLException {
            UrlLink links = new UrlLink(rs.getInt("ul.id"),rs.getString("ul.url"),rs.getString("pul.help_menu_text"),rs.getBoolean("pul.visible"));
            
            return links;
            
            
        }
        
        
    }
     
    
    
    private static final class URLLinkMapper implements RowMapper<UrlLink>{

        @Override
        public UrlLink mapRow(ResultSet rs, int i) throws SQLException {
            UrlLink link = new UrlLink(rs.getInt("id"),rs.getString("url"),rs.getString("desc"));
            link.setDefaultLink(rs.getBoolean("default_link"));
            return link;
            
            
        }
        
        
    }
        
    
    @Override
    public void add(UrlLink url,User user) {
        String sqlQuery="insert into url_link values(" + url.getLink() + "," + null + "," + url.getDesc() + "," + url.getDefaultLink() + ")";
        userActionLogger.logUserAction(user, "url_link", sqlQuery);
        //Using null as the key is Auto-Incremented by the Database.
        this.jdbcTemplate.update("insert into url_link values(?,?,?,?)",url.getLink(),null,url.getDesc(),url.getDefaultLink());
    }

    @Override
    public int delete(UrlLink url,User user) {
        String sqlQuery="delete from url_link where id=" + url.getId();
        userActionLogger.logUserAction(user, "url_link", sqlQuery);
        int deletedRows = this.jdbcTemplate.update("delete from url_link where id=?",url.getId());
        
        
        return deletedRows;
    }

   
}
