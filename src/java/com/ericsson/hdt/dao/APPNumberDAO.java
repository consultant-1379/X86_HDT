/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.dao;

import com.ericsson.hdt.model.APPNumber;
import com.ericsson.hdt.model.HWBundle;
import com.ericsson.hdt.model.User;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public interface APPNumberDAO {
    
    
    public void add(APPNumber app,User user);
    public int delete(APPNumber app,User user);
    public int updateAppNumber(APPNumber app,User user);
    public int updateDescription(APPNumber app,User user);
    public void addType(String type);
    public List<String> getListAPPTypes();
    public List<APPNumber> get();
    public APPNumber getAPPNumber(int id);
    public List<APPNumber> getListHWAPPNumber(HWBundle hw);
    public APPNumber findAPPNumberByName(String name);
    public List<APPNumber> searchForAPPNumberByDescription(String text);
    public List<APPNumber> searchForAPPNumberByName(String name);
    public int updateAPPNumberExposerToEngine(APPNumber app,User user);
    
    
}
