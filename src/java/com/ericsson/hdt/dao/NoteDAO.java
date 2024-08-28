/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.dao;

import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.HWBundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Note;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Role;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public interface NoteDAO extends Serializable{
    
    public int add(Note n,User user);
    public int addProductReleaseNote(Product p, Bundle b, Network net, Version v, Note n,User user);
    public int addProductRelaseNotePerRole(Product p, Bundle b, Network net, Version v, Note n,Role r,User user);
    public Note getProductRelaseNotePerRole(Product p, Bundle b, Network net, Version v,Role r);
    public Note productHardwareNote(Product p, Bundle b, Network net, Version v,Role r,HWBundle hw);
    
    public Note getProductRoleHardwareNote(Product p,Bundle b, Network net, Version v, Role r, HWBundle hardware);
    public List<Note> getProductReleaseNotes(Product p, Bundle b, Network net, Version v);
    public List<Note> getVisibileProductReleaseNotes(Product p, Bundle b, Network net, Version v);
    public int update(Note n,User user);
    public int delete(Note n,User user);
    public Boolean isNoteUsed(Note n);
    public List<Note> getAllNotes();
    public Note getNote(int id);
    public int deleteProductReleaseNote(Product p, Bundle b, Network net, Version v, Note note,User user);
    
}
