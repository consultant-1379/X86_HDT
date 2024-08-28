/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.web;

import com.ericsson.hdt.dao.BundleDAO;
import com.ericsson.hdt.dao.URLLinkDAO;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.UrlLink;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import com.ericsson.hdt.service.CheckUserCreditantials;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author eadrcle
 */

@Controller
@RequestMapping(value="link")
public class URLLinkController {
    
    @Autowired
    private URLLinkDAO urlLinkDAO;
    @Autowired
    private BundleDAO bundleDAO;
    protected final Log logger = LogFactory.getLog(getClass());
    
    
    @RequestMapping(method=RequestMethod.GET)
    public String initRequest(Model model, HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
             return "urllinksEditor";
        }
        
        
    }
    
    
    @RequestMapping(value="ajax",method=RequestMethod.GET)
    public String ajaxRequest(Model model, @ModelAttribute("links")List<UrlLink> links,HttpServletResponse response){
         response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        model.addAttribute("links", links);
        
        
        return "urlLinkListAjax";
    }
    
    
    @RequestMapping(value="save_ajax",method=RequestMethod.POST)
    public @ResponseBody void processAjaxSubmit(@RequestBody Map<String,String> parameters,HttpSession session){
       User user = (User) session.getAttribute("user");
       UrlLink link = new UrlLink(parameters.get("url_link"),parameters.get("desc"));
       if(parameters.get("defaultLink")!=null){
           link.setDefaultLink(true);
       }
       urlLinkDAO.add(link,user);
        
    }
    
    
    @RequestMapping(value="description",method=RequestMethod.GET)
    public String getURLLinkDescription(Model model,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
             return "updateURLLinkDescription";
        }
        
        
        
    }
    
    @RequestMapping(value="getlinks",method=RequestMethod.GET)
    public String getLinks(Model model,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            return "updateURLLink";
        }
    }
    
    
    @RequestMapping(value="delete",method=RequestMethod.GET)
    public String getUnusedHelpMenuLinks(Model model,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
                List<UrlLink> allLinks = urlLinkDAO.getLinkList();
                List<UrlLink> unUsedLinks = new ArrayList<UrlLink>(1);
                Iterator<UrlLink> ilinks = allLinks.iterator();
                Boolean used;
                while(ilinks.hasNext()){
                    UrlLink url = ilinks.next();
                    used = urlLinkDAO.isHelpMenuLinkUsed(url);
                    if(!used){
                        unUsedLinks.add(url);
                    }
            
                }
        
                model.addAttribute("links", unUsedLinks);
        
                return "delete_helpmenulink";
        }
    }
    
    
    @RequestMapping(value="delete",method=RequestMethod.POST)
    public String deleteHelpMenuLink(Model model, HttpServletRequest request,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            List<UrlLink> allLinks = urlLinkDAO.getLinkList();
            Iterator<UrlLink> ilinks = allLinks.iterator();
            User user = (User) session.getAttribute("user");

            while (ilinks.hasNext()) {
                UrlLink url = ilinks.next();
                if (request.getParameter(url.getId().toString()) != null) {

                    urlLinkDAO.delete(url, user);

                }

            }
            return "administrator";
            
        }
        
        
    }
    
    
    
    
    @RequestMapping(value="updateURLDescription",method=RequestMethod.POST)
    public String updateURLDescription(Model model,HttpServletRequest request,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
                int updatedRows = 0;
            List<UrlLink> allLinks = urlLinkDAO.getLinkList();
            Iterator<UrlLink> links = allLinks.iterator();
            User user = (User) session.getAttribute("user");
            while (links.hasNext()) {
                UrlLink link = links.next();
                String description = request.getParameter(link.getId().toString());
                if (!(description.equals(link.getDesc()))) {

                    link.setDesc(description);
                    updatedRows += urlLinkDAO.updateDescription(link, user);

                }

            }

            return "administrator";
            
        }
        
        
        
    }
    
    @RequestMapping(value="updateURLLink",method=RequestMethod.POST)
    public String updateURLLink(Model model, HttpServletRequest request,HttpSession session ){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
                int updatedRows = 0;
            List<UrlLink> allLinks = urlLinkDAO.getLinkList();
            Iterator<UrlLink> links = allLinks.iterator();
            User user = (User) session.getAttribute("user");
            while (links.hasNext()) {
                UrlLink link = links.next();
                String linkUrl = request.getParameter(link.getId().toString());
                if (!(linkUrl.equals(link.getLink()))) {

                    link.setLink(linkUrl);
                    updatedRows += urlLinkDAO.updateLink(link, user);

                }

            }

            return "administrator";
            
        }
        
        
    }
    
    @RequestMapping(value="updateUrlVisibilty",method=RequestMethod.POST)
    public String updateUrlVisibiltyModel (Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam(value="allBundles",required=false) String allBundles,HttpServletRequest request,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
                int updatedRows = 0;

            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            User user = (User) session.getAttribute("user");
            String visible;
            List<UrlLink> productReleaseURLLinks = urlLinkDAO.getProductReleaseURLLinks(p, v, n, b);
            Iterator<UrlLink> links = productReleaseURLLinks.iterator();
            List<Bundle> definedBundles = bundleDAO.getProductReleaseBundle(p, v, n);
            while (links.hasNext()) {
                UrlLink link = links.next();

                if (request.getParameter(link.getId().toString()) == null) {

                    if (link.getVisible()) {
                        link.setVisible(false);
                        if (allBundles != null) {
                            Iterator<Bundle> ibundles = definedBundles.iterator();
                            while (ibundles.hasNext()) {
                                Bundle bundle = ibundles.next();
                                updatedRows = urlLinkDAO.updateProductReleaseURLLink(p, v, n, bundle, link, user);
                            }
                        } else {
                            updatedRows = urlLinkDAO.updateProductReleaseURLLink(p, v, n, b, link, user);

                        }

                    }

                } else {

                    if (!(link.getVisible())) {
                        link.setVisible(true);
                        if (allBundles != null) {
                            Iterator<Bundle> ibundles = definedBundles.iterator();
                            while (ibundles.hasNext()) {
                                Bundle bundle = ibundles.next();
                                updatedRows = urlLinkDAO.updateProductReleaseURLLink(p, v, n, bundle, link, user);
                            }
                        } else {
                            updatedRows = urlLinkDAO.updateProductReleaseURLLink(p, v, n, b, link, user);

                        }
                    }

                }

            }

            return "administrator";

            
        }
        
    }
    
    
    @RequestMapping(value="deleteProductReleaseHelpMenu",method=RequestMethod.POST)
    public String deleteProductReleaseHelpMenuLink(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam(value="allBundles",required=false) String allBundles,HttpServletRequest request,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            int deletedRows = 0;

            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            List<Bundle> definedBundles = bundleDAO.getProductReleaseBundle(p, v, n);
            User user = (User) session.getAttribute("user");

            List<UrlLink> definedLinks = urlLinkDAO.getProductReleaseURLLinks(p, v, n, b);
            Iterator<UrlLink> ilinks = definedLinks.iterator();
            while (ilinks.hasNext()) {
                UrlLink link = ilinks.next();
                if (request.getParameter(link.getId().toString()) != null) {

                    if (allBundles != null) {

                        Iterator<Bundle> ibundles = definedBundles.iterator();
                        while (ibundles.hasNext()) {
                            Bundle bundle = ibundles.next();
                            deletedRows += urlLinkDAO.deleteProductReleaseHelpMenu(p, v, n, bundle, link, user);

                        }
                    } else {

                        deletedRows += urlLinkDAO.deleteProductReleaseHelpMenu(p, v, n, b, link, user);
                    }

                }

            }

            return "administrator";

            
        }
        
    }
    
    
    @RequestMapping(value="productReleaseHelpMenuLink",method=RequestMethod.GET)
    public String getProductReleaseHelpMenuLinks(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,HttpServletResponse response, HttpSession session){
        response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        
        
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            List<UrlLink> links = urlLinkDAO.getDefaultLinks();
            List<UrlLink> allLinks = urlLinkDAO.getVisibleProductReleaseURLLinks(p, v, n, b);
            links.addAll(allLinks);

            model.addAttribute("productLinks", links);

            return "productReleaseHelpMenuLinkAjax";
            
       
        
    }
    
    
    @RequestMapping(value="addProductReleaseHelpLink", method=RequestMethod.POST)
    public String addProductReleaseHelpLink(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam(value="allBundles",required=false) String allBundles,HttpServletRequest request,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            User user = (User) session.getAttribute("user");
            List<Bundle> definedBundles = bundleDAO.getProductReleaseBundle(p, v, n);
            List<UrlLink> allLinks = urlLinkDAO.getLinkList();
            List<UrlLink> currentLinks = urlLinkDAO.getProductReleaseURLLinks(p, v, n, b);
            Iterator<UrlLink> icurrentLinks = currentLinks.iterator();
            while (icurrentLinks.hasNext()) {
                UrlLink url = icurrentLinks.next();

                if (allLinks.contains(url)) {
                    allLinks.remove(url);

                }

            }

            Iterator<UrlLink> iallLinks = allLinks.iterator();
            while (iallLinks.hasNext()) {
                UrlLink url = iallLinks.next();
                if (request.getParameter(url.getId().toString()) != null) {

                    if (allBundles != null) {

                        Iterator<Bundle> ibundles = definedBundles.iterator();

                        while (ibundles.hasNext()) {
                            Bundle bundle = ibundles.next();
                            urlLinkDAO.addProductUrlLinks(p, v, n, bundle, url, user);
                        }

                    } else {
                        urlLinkDAO.addProductUrlLinks(p, v, n, b, url, user);
                    }

                }

            }

            return "administrator";
            
        }
        
        
    }
            
    
    @RequestMapping(method=RequestMethod.POST)
    public String processSubmit(Model model, @RequestParam(value="url_link") String url_link,@RequestParam(value="desc") String desc, @RequestParam(value="defaultLink",required=false) String defaultLink,HttpSession session){
       if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
           UrlLink link = new UrlLink(url_link, desc);
           User user = (User) session.getAttribute("user");

           if (defaultLink != null) {
               link.setDefaultLink(true);
           }
           urlLinkDAO.add(link, user);

           return "administrator";
           
       }
       
    }
    
    
    @ModelAttribute("links")
    public List<UrlLink> populateLinksList(){
        
        
        return urlLinkDAO.getLinkList();
    }
    
    
    
    
}
