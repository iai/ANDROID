/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.art.iai.model;

import com.mysql.jdbc.MySQLConnection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Paulo
 */
public class DBUtil {
    public static MySQLConnection GetConnection(){
        
        MySQLConnection con = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = (MySQLConnection) DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/bancojava", "root", "gilgourt");
        } catch (Exception ex) {
            System.out.println("Deu um erro");
        }
        
        return con;
    }
    
    public static void CloseConnection(MySQLConnection con){
        try{
            con.close();
        }
        catch(Exception ex){
            System.out.println("Deu um erro");
        }
    }
}
