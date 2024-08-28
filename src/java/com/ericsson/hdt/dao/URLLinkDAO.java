/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.dao;

import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.UrlLink;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public interface URLLinkDAO extends Serializable{
    
    public List<UrlLink> getLinkList();
    public void add(UrlLink url,User user);
    public int delete(UrlLink url,User user);
    public List<UrlLink> getDefaultLinks();
    public int updateDescription(UrlLink url,User user);
    public int updateLink(UrlLink url,User user);
    public UrlLink getIndividualLinks(int id);
    public List<UrlLink> getProductReleaseURLLinks(Product p, Version v, Network net,Bundle b);
    public List<UrlLink> getVisibleProductReleaseURLLinks(Product p, Version v, Network net,Bundle b);
    public int updateProductReleaseURLLink(Product p, Version v, Network net,Bundle b,UrlLink url,User user);
    public int addProductUrlLinks(Product p, Version v, Network n, Bundle b, UrlLink url,User user);
    public Boolean isHelpMenuLinkUsed(UrlLink url);
    public int deleteProductReleaseHelpMenu(Product p, Version v, Network n, Bundle b, UrlLink url,User user);
    
}
