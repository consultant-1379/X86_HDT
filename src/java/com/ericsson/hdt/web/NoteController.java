/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.web;

import com.ericsson.hdt.dao.BundleDAO;
import com.ericsson.hdt.dao.NoteDAO;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Note;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import com.ericsson.hdt.service.CheckUserCreditantials;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
@RequestMapping(value="note")
public class NoteController {
    @Autowired
    private NoteDAO noteDAO;
    @Autowired
    private BundleDAO bundleDAO;
    protected final Log logger = LogFactory.getLog(getClass());
    
    @RequestMapping(method=RequestMethod.GET)
    public String initRequest(HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else{
            
            return "noteEditor";
        }
        
        
        
    }
    
     @RequestMapping(value="ajax",method=RequestMethod.GET)
    public String ajaxRequest(Model model, @ModelAttribute("notes") List<Note> notes,HttpServletResponse response){
          response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        
         model.addAttribute("notes", notes);
        
        return "noteListAjax";
    }
    
     @RequestMapping(value="save_ajax",method=RequestMethod.POST)
    public @ResponseBody void processAjaxSubmit(@RequestBody Map<String,String> parameters, HttpSession session){
        
        User user = (User)session.getAttribute("user");
       
        Note n = new Note(parameters.get("content"));
        if(n.getNote().length()>1){
            noteDAO.add(n,user);
        }
        
        
        
    }
     
     @RequestMapping(value="delete",method=RequestMethod.GET)
     public String getUnUsedNotes(Model model,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else{
             List<Note> notes = noteDAO.getAllNotes();
            List<Note> unUsedNotes = new ArrayList<Note>(1);
            Iterator<Note> inotes = notes.iterator();
            Boolean used;
            while(inotes.hasNext()){
                Note n = inotes.next();
                used = noteDAO.isNoteUsed(n);
                if(!used){
                    unUsedNotes.add(n);
                }
            }
         
            model.addAttribute("notes",unUsedNotes);
            return "delete_notes";
             
         }
         
     }
     
     @RequestMapping(value="delete",method=RequestMethod.POST)
     public String deleteUnusedNotes(Model model,HttpServletRequest request,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            List<Note> notes = noteDAO.getAllNotes();
            User user = (User) session.getAttribute("user");
            Iterator<Note> inotes = notes.iterator();
            while(inotes.hasNext()){
                Note n = inotes.next();
                if(request.getParameter(n.getId().toString())!=null){
                    noteDAO.delete(n,user);
                }
            }
         
            return "administrator";
         }
         
     }
     
     @RequestMapping(value="deleteReleaseNote",method=RequestMethod.POST)
     public String deleteProductReleaseNote(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam(value="allBundles", required=false) String allBundles,HttpSession session,HttpServletResponse response,HttpServletRequest request){
         
         if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            int deletedRows=0;
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            List<Bundle> bundles = bundleDAO.getProductReleaseBundle(p, v, n);
            User user = (User)session.getAttribute("user");
            List<Note> definedNotes = noteDAO.getProductReleaseNotes(p, b, n, v);
            Iterator<Note> inote = definedNotes.iterator();
            while(inote.hasNext()){
                Note note = inote.next();
                if(request.getParameter(note.getId().toString())!=null){
                    if(allBundles!=null){
                        Iterator<Bundle> ibundles = bundles.iterator();
                        while(ibundles.hasNext()){
                            Bundle bundle = ibundles.next();
                            deletedRows += noteDAO.deleteProductReleaseNote(p, bundle, n, v, note,user);
                        }
                    }else{
                        deletedRows += noteDAO.deleteProductReleaseNote(p, b, n, v, note,user);
                    }
                }
            
            }
            return "administrator";
         }
         
     }
     
     
    @RequestMapping(method=RequestMethod.POST)
    public String processSubmit(Model model,@RequestParam(value="content",required=true) String content,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            User user = (User) session.getAttribute("user");
            Note n = new Note(content);
            if(n.getNote().length()>1){
                noteDAO.add(n,user);
            }
            return "administrator";
        }
    }
    
    
    @ModelAttribute("notes")
    public List<Note> populateNoteList(){
        
        
        return noteDAO.getAllNotes();
    }
}
