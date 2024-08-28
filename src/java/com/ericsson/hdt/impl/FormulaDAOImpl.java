/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.impl;

import com.ericsson.hdt.dao.FormulaDAO;
import com.ericsson.hdt.dao.SystemActionLoggerDAO;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Formula;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Role;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import java.io.DataInputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
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
public class FormulaDAOImpl extends BaseDAOImpl  implements FormulaDAO{
    
private SystemActionLoggerDAO userActionLogger = new SystemActionLoggerDAOImpl();
      private final JdbcTemplate jdbcTemplate;
      
      
      public FormulaDAOImpl(){
          super();
          jdbcTemplate = new JdbcTemplate(dataSource); 
      }

    @Override
    public Formula getIndividualFormula(String name) {
        Formula f = this.jdbcTemplate.queryForObject("select * from formula where name=?", new Object[]{name}, new FormulaMapper());
        
        return f;
    }

    @Override
    public Boolean isExist(Formula f) {
        int rows = this.jdbcTemplate.queryForInt("select count(*) from formula where name=?", f.getName());
      
        
        if(rows==1){
            return true;
        }
         
        return false;
        
    }

    @Override
    public Formula getFormulaPerRole(Product p, Version v, Network n, Bundle b,Role r) {
        Formula formula =  this.jdbcTemplate.queryForObject("select name,fml_code from formula as f inner join product_release_role as pr  "
                                    + "on f.name=pr.formula_name where pr.product_weight=? and pr.combined_network_weight=? and pr.bundle_id=? and pr.version_name=?"
                                    + " and pr.roles_id=? and pr.site_id=? ",
                                    new Object[]{p.getWeighting(),n.getNetworkWeight(),b.getID(),v.getName(),r.getId(),r.getSite().getId()},new FormulaMapper());
        
        
        return formula;
    }

    @Override
    public Formula getProductReleaseTestScript(Product p, Version v, Network n, Bundle b) {
       Formula formula = this.jdbcTemplate.queryForObject("select * from product_release as pr inner join formula as f "
               + "on pr.formula_name=f.name where pr.product_weight=? and pr.combined_network_weight=? and pr.bundle_id=?"
               + " and pr.version_name=?",new Object[]{p.getWeighting(),n.getNetworkWeight(),b.getID(),v.getName()}, new FormulaMapper());
       
       return formula;
    }

    
    /*****
    @Override
    public int updateProductReleaseFormulaName(Formula oldFormula, Formula newFormula,User user) {
        int updatedRows = 0;
        String sqlQuery = "update product_release as pr set pr.formula_name=" + newFormula.getName() +  " where pr.formula_name=" + oldFormula.getName();
        userActionLogger.logUserAction(user, "product_release", sqlQuery);
        
    
        updatedRows += this.jdbcTemplate.update("update product_release as pr set pr.formula_name=? where pr.formula_name=?",newFormula.getName(),oldFormula.getName());
        updatedRows += this.jdbcTemplate.update("update system_details as pr set pr.formula_name=? where pr.formula_name=?",newFormula.getName(),oldFormula.getName());
        updatedRows += this.jdbcTemplate.update("update product_release_role as prr set prr.formula_name=? where prr.formula_name=?",newFormula.getName(),oldFormula.getName());
        return updatedRows;
    }

    @Override
    public int updateProductReleaseRoleFormulaName(Formula oldFormula, Formula newFormula,User user) {
         int updatedRows = 0;
         String sqlQuery = "update product_release_role set formula_name=" + newFormula.getName() +  " where formula_name=" + oldFormula.getName();
        userActionLogger.logUserAction(user, "product_release_role", sqlQuery);
        updatedRows = this.jdbcTemplate.update("update product_release_role as prr set prr.formula_name=? where prr.formula_name=?",newFormula.getName(),oldFormula.getName());
    
        return updatedRows;
    }

* 
* ****/
    
    
    @Override
    public List<Formula> getAssignedFormulas() {
        List<Formula> formulas = this.jdbcTemplate.query("select DISTINCT f.name,f.fml_code from product_release_role as prr inner join "
                + " formula as f on f.name=prr.formula_name;", new FormulaMapper() );
    
        
        // Check for Test Scripts defined in the product_release and add any available formula to the formula list.
        List<Formula> testFormula = this.jdbcTemplate.query("select DISTINCT f.name,f.fml_code from product_release as prr inner join "
                + " formula as f on f.name=prr.formula_name;", new FormulaMapper() );
        
        // Check for Test Scripts defined in the product_release and add any available formula to the formula list.
        List<Formula> systemScriptFormula = this.jdbcTemplate.query("select DISTINCT f.name,f.fml_code from system_details as sd inner join "
                + " formula as f on f.name=sd.formula_name;", new FormulaMapper() );
        
        
        
        Iterator<Formula> iformula = testFormula.iterator();
        while(iformula.hasNext()){
            Formula f = iformula.next();
            if(!(formulas.contains(f))){
                    formulas.add(f);
            }
        }
        
        iformula = systemScriptFormula.iterator();
        while(iformula.hasNext()){
            Formula f = iformula.next();
            if(!(formulas.contains(f))){
                    formulas.add(f);
            }
        }
        
    
    return formulas;
    }

    @Override
    public Formula getProductSystemDetailsScript(Product p, Version v, Network n) {
        
        return this.jdbcTemplate.queryForObject("select distinct f.name,f.fml_code from system_details as pr inner join formula as f "
               + "on pr.formula_name=f.name where pr.product_weight=? and pr.combined_network_weight=? "
               + " and pr.version_name=?",new Object[]{p.getWeighting(),n.getNetworkWeight(),v.getName()}, new FormulaMapper());
        
    }

    @Override
    public int renameFomulaName(Formula oldFormula, Formula newFormula, User user) {
        int updatedRows = 0;
        String sqlQuery = "update product_release as f set formula_name=" + newFormula.getName() +  " where formula_name=" + oldFormula.getName();
        
    
        updatedRows += this.jdbcTemplate.update("update product_release as pr set pr.formula_name=? where pr.formula_name=?",newFormula.getName(),oldFormula.getName());
        updatedRows += this.jdbcTemplate.update("update system_details as pr set pr.formula_name=? where pr.formula_name=?",newFormula.getName(),oldFormula.getName());
        updatedRows += this.jdbcTemplate.update("update product_release_role as prr set prr.formula_name=? where prr.formula_name=?",newFormula.getName(),oldFormula.getName());
        userActionLogger.logUserAction(user, "product_release", sqlQuery);
        sqlQuery = "update product_release as f set formula_name=" + newFormula.getName() +  " where formula_name=" + oldFormula.getName();
        
        userActionLogger.logUserAction(user, "system_details", sqlQuery);
        sqlQuery = "update system_details as f set formula_name=" + newFormula.getName() +  " where formula_name=" + oldFormula.getName();
        
        userActionLogger.logUserAction(user, "product_release_role", sqlQuery);
        sqlQuery = "update product_release_role as f set formula_name=" + newFormula.getName() +  " where formula_name=" + oldFormula.getName();
        
        return updatedRows;
    }

    
    
      
      private static final class FormulaMapper implements RowMapper<Formula>{

        @Override
        public Formula mapRow(ResultSet rs, int i) throws SQLException {
            
            Formula f = new Formula(rs.getString("name"),rs.getString("fml_code"));
            
            return f;
        }
    


    }
      
    @Override
    public List<Formula> getAllFormula() {
        List<Formula> formula = null;
        
       formula = this.jdbcTemplate.query("select * from formula", new FormulaMapper());
       
       return formula;
    }
    
      
      @Override
    public void add(Formula f,User user) {
      
        int row = this.jdbcTemplate.queryForInt("select count(*) from formula where name =?",f.getName());
        
        if(row==1){
            updateFormulaCode(f,user);
             
        }
        else{
            this.jdbcTemplate.update("insert into formula values(?,?)",f.getName(),f.getFormula());
            String sqlQuery = "insert into formula values(" + f.getName() +  ", ****Some Code *****)";
        userActionLogger.logUserAction(user, "formula", sqlQuery);
        }
        
        
    }

    @Override
    public List<Formula> getFormula(Formula f) {
        return this.jdbcTemplate.query("select * from formala where name=?",new Object[] {f.getName()},new FormulaMapper());
    }

    @Override
    public int delete(Formula f, User user) {
         String sqlQuery = "delete from formula where name=" + f.getName();
        userActionLogger.logUserAction(user, "formula", sqlQuery);
        int deleteRows = this.jdbcTemplate.update("delete from formula where name=?",f.getName());
        
        return deleteRows;
        
    }

    @Override
    public int  updateFormulaCode(Formula f,User user) {
        int updatedRows = 0;
         String sqlQuery = "update formula set fml_code= ***Some Code  **** where name=" + f.getName();
        userActionLogger.logUserAction(user, "formula", sqlQuery);
        updatedRows = this.jdbcTemplate.update("update formula set fml_code=? where name=?",f.getFormula(),f.getName());
    
        return updatedRows;
    }
    
    
}
