/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author eadrcle
 */
public class SHA1HashingPassword {
    
    
    private static SHA1HashingPassword sha=null ;
    
    private SHA1HashingPassword(){
               
    }
    
    public static SHA1HashingPassword getInstance(){
        
        
        if(sha==null){
            sha = new SHA1HashingPassword();
        }
        
        return sha;
        
    }
    
    public String getShaHash(String password) throws NoSuchAlgorithmException{
        
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte byteData[] = md.digest();
        
        StringBuffer hexString = new StringBuffer();
        for(int i=0;i<byteData.length;i++){
            
            String h = Integer.toHexString(0xff & byteData[i]);
                if(h.length()==1) {
                hexString.append("0");
            }
            hexString.append(h);
            
        }
        
        
        return hexString.toString();
        
    }
    
    
}
