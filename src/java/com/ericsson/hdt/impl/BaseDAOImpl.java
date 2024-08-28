/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.impl;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author eadrcle
 */

@Repository
public abstract class BaseDAOImpl {

    protected final Log logger = LogFactory.getLog(getClass());
    private InitialContext ctx;
    public DataSource dataSource;

    public BaseDAOImpl() {
       
            try {
            ctx = new InitialContext();
            // Look up the data source on the server.
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/hdtSchemaDatasource");
        } catch (NamingException ne) {
            logger.error("NamingException: " + ne.getMessage());
            System.out.println("Error occurred....");
        }
       
    }

    
    
        
        
    
    
    
}
