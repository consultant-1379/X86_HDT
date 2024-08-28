/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.impl;

import com.ericsson.hdt.dao.NoteDAO;
import com.ericsson.hdt.dao.SystemActionLoggerDAO;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.HWBundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Note;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Role;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author eadrcle
 */
@Repository
public class NoteDAOImpl extends BaseDAOImpl implements NoteDAO {

    private SystemActionLoggerDAO userActionLogger = new SystemActionLoggerDAOImpl();
   private JdbcTemplate jdbcTemplate;
   
    
    public NoteDAOImpl(){
        super();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        
    }

    @Override
    public int addProductReleaseNote(Product p, Bundle b, Network net, Version v, Note n,User user) {
        String sqlQuery = "insert into product_release_notes values(" + n.getId()+"," + true + "," + v.getName() + "," + b.getID() +"," +net.getNetworkWeight()+"," +p.getWeighting() + ")";
        userActionLogger.logUserAction(user, "product_release_notes", sqlQuery);
        int rows = this.jdbcTemplate.update("insert into product_release_notes values(?,?,?,?,?,?)",n.getId(),true,v.getName(),b.getID(),net.getNetworkWeight(),p.getWeighting());

        return rows;
    }

    @Override
    public int addProductRelaseNotePerRole(Product p, Bundle b, Network net, Version v, Note n, Role r,User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Note getProductRelaseNotePerRole(Product p, Bundle b, Network net, Version v, Role r) {
        Note n = null;
        
        n = this.jdbcTemplate.queryForObject("select * from info_note as n inner join product_release_role as pr on pr.info_note_id=n.id where "
                + " pr.product_weight=? and pr.combined_network_weight=? and pr.bundle_id=? and pr.version_name=? and pr.roles_id = ?"
                + " and pr.site_id=?",new Object[]{p.getWeighting(),net.getNetworkWeight(),b.getID(),v.getName(),r.getId(),r.getSite().getId()},new HardwareRoleNoteMapper());
        
        return n;
    }

   

    @Override
    public List<Note> getProductReleaseNotes(Product p, Bundle b, Network net, Version v) {
        List<Note> notes = null;
        
        notes = this.jdbcTemplate.query("select * from product_release_notes as prn inner join info_note as n on prn.info_note_id=n.id  "
                + " where prn.product_weight=? and prn.combined_network_weight=? and prn.bundle_id=? and prn.version_name=?",
                new Object[] {p.getWeighting(),net.getNetworkWeight(),b.getID(),v.getName()} ,new ProductReleaseNoteMapper());
        
        
        return notes;
    
    }

    @Override
    public Boolean isNoteUsed(Note n) {
        int rows =  0 ;
        
         rows += this.jdbcTemplate.queryForInt("select count(*) from product_release_notes where info_note_id=? and visible=?", n.getId(),true);
         rows  += this.jdbcTemplate.queryForInt("select count(*) from product_release_role where info_note_id=? and note_visible=?", n.getId(),true);
        
         rows += this.jdbcTemplate.queryForInt("select count(*) from role_hw_config where info_note_id=? and note_visible=?", n.getId(),true);
        
        if(rows>0){
            
            return true;
            
        }
        
        return false;
        
    }

    @Override
    public int deleteProductReleaseNote(Product p, Bundle b, Network net, Version v, Note note, User user) {
        int deletedRows = 0;
        
       String sqlQuery = "delete from product_release_notes  where product_weight=" + p.getWeighting() + " and combined_network_weight=" +net.getNetworkWeight()  + "  and bundle_id=" + b.getID() + 
               " and version_name=" + v.getName() + " and info_note_id=" +  note.getId();
       userActionLogger.logUserAction(user, "product_release_notes", sqlQuery);
       deletedRows = this.jdbcTemplate.update("delete from product_release_notes  where product_weight=? and combined_network_weight=? "
                + " and bundle_id=? and version_name=? and info_note_id=? ",p.getWeighting(),net.getNetworkWeight(),b.getID(),v.getName(),
                note.getId());
        
        return deletedRows;
        
    }

    @Override
    public Note productHardwareNote(Product p, Bundle b, Network net, Version v, Role r, HWBundle hw) {
        Note n = null;
        
        n = this.jdbcTemplate.queryForObject("select * from info_note as n inner join role_hw_config as pr on pr.info_note_id=n.id where "
                + " pr.product_weight=? and pr.combined_network_weight=? and pr.bundle_id=? and pr.version_name=? and pr.roles_id = ?"
                + " and pr.site_id=? and pr.hw_config_id = ? and pr.result_variable=? and pr.hw_ver=? and pr.note_visible=?",
                new Object[]{p.getWeighting(),net.getNetworkWeight(),b.getID(),v.getName(),r.getId(),r.getSite().getId(),
                    hw.getId(),hw.getExpectedResult(),hw.getRevision(),true},new HardwareRoleNoteMapper());    
        
       
        
        
        return n;
    }

    @Override
    public List<Note> getVisibileProductReleaseNotes(Product p, Bundle b, Network net, Version v) {
        return this.jdbcTemplate.query("select * from product_release_notes as prn inner join info_note as n on prn.info_note_id=n.id  "
                + " where prn.product_weight=? and prn.combined_network_weight=? and prn.bundle_id=? and prn.version_name=? and visible=?",
                new Object[] {p.getWeighting(),net.getNetworkWeight(),b.getID(),v.getName(),true} ,new ProductReleaseNoteMapper());
    }

    @Override
    public Note getProductRoleHardwareNote(Product p, Bundle b, Network net, Version v, Role r, HWBundle hardware) {
        Note n = null;
        
        
        n = this.jdbcTemplate.queryForObject("select * from info_note as n inner join role_hw_config as pr on pr.info_note_id=n.id where "
                + " pr.product_weight=? and pr.combined_network_weight=? and pr.bundle_id=? and pr.version_name=? and pr.roles_id = ?"
                + " and pr.site_id=? and pr.hw_config_id = ? and pr.result_variable=? and pr.hw_ver=?",
                new Object[]{p.getWeighting(),net.getNetworkWeight(),b.getID(),v.getName(),r.getId(),r.getSite().getId(),
                    hardware.getId(),hardware.getExpectedResult(),hardware.getRevision()},new HardwareRoleNoteMapper());    
        
       
        
        
        return n;
    }

   

   

  

    private static final class NoteMapper implements RowMapper<Note>{

        @Override
        public Note mapRow(ResultSet rs, int i) throws SQLException {
            Note n = new Note(rs.getInt("id"),rs.getString("content"));
            
            
            return n;
        }
        
        
        
        
    }
    
     private static final class HardwareRoleNoteMapper implements RowMapper<Note>{

        @Override
        public Note mapRow(ResultSet rs, int i) throws SQLException {
            Note n = new Note(rs.getInt("id"),rs.getString("content"),rs.getBoolean("note_visible"));
            
            
            return n;
        }
        
        
        
        
    }
      private static final class ProductReleaseNoteMapper implements RowMapper<Note>{

        @Override
        public Note mapRow(ResultSet rs, int i) throws SQLException {
            Note n = new Note(rs.getInt("id"),rs.getString("content"),rs.getBoolean("visible"));
            
            
            return n;
        }
        
        
        
        
    }
    
      @Override
    public int add(Note n,User user) {
          
         String sqlQuery = "insert into info_note values("  + n.getNote()+ ")";
        userActionLogger.logUserAction(user, "info_note", sqlQuery);
        int status = this.jdbcTemplate.update("insert into info_note values(?,?)",n.getNote(),null);
        
        return status;
    }
    
    @Override
    public int update(Note n,User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int delete(Note n,User user) {
        String sqlQuery = "delete from info_note where id=" + n.getId();
        userActionLogger.logUserAction(user, "info_note", sqlQuery);
        
        this.jdbcTemplate.update("update product_release_notes set info_note_id=? where info_note_id=?",null,n.getId());
        this.jdbcTemplate.update("update product_release_role set info_note_id=? where info_note_id=?",null,n.getId());
        this.jdbcTemplate.update("update role_hw_config set info_note_id=? where info_note_id=?",null,n.getId());
        int deletedRows = this.jdbcTemplate.update("delete from info_note where id=?",n.getId());
        
        
        return deletedRows;
    }

    @Override
    public List<Note> getAllNotes() {
        List<Note> notes = this.jdbcTemplate.query("select * from info_note", new NoteMapper());
        
        return notes;
    }

    @Override
    public Note getNote(int id) {
        
        Note n = this.jdbcTemplate.queryForObject("select * from info_note where id=" + id, new NoteMapper());
        
        return n;
    }
    
}
