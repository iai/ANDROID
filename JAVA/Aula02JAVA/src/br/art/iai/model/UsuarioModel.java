/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.art.iai.model;

import br.art.iai.bean.Usuario;
import com.mysql.jdbc.MySQLConnection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class UsuarioModel {
    public static boolean VerificaEmail(String email){
        boolean retorno = false;
        
        MySQLConnection con = null;
        PreparedStatement stmt = null;
        ResultSet res = null;
        
        try{
            con = DBUtil.GetConnection();
            stmt = (PreparedStatement) 
                    con.prepareStatement
        ("select count(*) as quantidade from usuarios where email = ?;");
            stmt.setString(1, email);
            res = stmt.executeQuery();
            
            while(res.next()){
                int quantidade = res.getInt("quantidade");
                if(quantidade > 0){
                    retorno = true;
                }
                else{
                    retorno = false;
                }
            }
            
            stmt.close();
            res.close();
        }
        catch(Exception ex){
            
        }
        finally{
            DBUtil.CloseConnection(con);
        }
        
        return retorno;
    }
    
    public static boolean Login(String email, String senha){
        boolean retorno = false;
        
        MySQLConnection con = null;
        PreparedStatement stmt = null;
        ResultSet res = null;
        
        try{
            con = DBUtil.GetConnection();
            stmt = (PreparedStatement) 
                    con.prepareStatement
        ("select count(*) as quantidade from usuarios where email = ? and senha = ?;");
            stmt.setString(1, email);
            stmt.setString(2, senha);
            res = stmt.executeQuery();
            
            while(res.next()){
                int quantidade = res.getInt("quantidade");
                if(quantidade > 0){
                    retorno = true;
                }
                else{
                    retorno = false;
                }
            }
            
            stmt.close();
            res.close();
        }
        catch(Exception ex){
            
        }
        finally{
            DBUtil.CloseConnection(con);
        }
        
        return retorno;
    }
    
    public static boolean Incluir(Usuario usuario){
        boolean retorno = false;
        
        MySQLConnection con = null;
        PreparedStatement stmt = null;
        int resultado = 0;
        
        try{
            con = DBUtil.GetConnection();
            stmt = (PreparedStatement) 
                    con.prepareStatement("insert into usuarios(email, nome, "
                            + "senha, datanascimento) values(?, ?, ?, ?);");
            stmt.setString(1, usuario.email);
            stmt.setString(2, usuario.nome);
            stmt.setString(3, usuario.senha);
            java.sql.Date d = new Date(usuario.dataNascimento.getTime());
            stmt.setDate(4, d);
            
            resultado = stmt.executeUpdate();
            if(resultado > 0){
                retorno = true;
            }
            else{
                retorno = false;
            }
            
            stmt.close();
            
        }
        catch(Exception ex){
            String e = ex.getMessage();
        }
        finally{
            DBUtil.CloseConnection(con);
        }
        
        return retorno;
    }
    
    public static List<Usuario> GetAll(){
        List<Usuario> usuarios = new ArrayList<>();
        
        MySQLConnection con = null;
        PreparedStatement stmt = null;
        ResultSet res = null;
        
        try{
            con = DBUtil.GetConnection();
            stmt = (PreparedStatement) 
                    con.prepareStatement("select * from usuarios order by nome;");
            res = stmt.executeQuery();
            
            while(res.next()){
                Usuario u = new Usuario();
                
                Calendar c = Calendar.getInstance();
                int dia = 0;
                int mes = 0;
                int ano = 0;
                
                ano = Integer.parseInt(res.getDate("datanascimento")
                        .toString().substring(0, 4));
                mes = Integer.parseInt(res.getDate("datanascimento")
                        .toString().substring(4, 7));
                dia = Integer.parseInt(res.getDate("datanascimento")
                        .toString().substring(7, 10));
                
                c.set(Calendar.DAY_OF_MONTH, dia);
                c.set(Calendar.MONTH, mes + 1);
                c.set(Calendar.YEAR, ano);
                
                u.dataNascimento = c.getTime();
                u.email = res.getString("email");
                u.nome = res.getString("nome");
                u.senha = res.getString("senha");
                
                usuarios.add(u);
            }
            
            res.close();
            stmt.close();
        }
        catch(Exception ex){
            String e = ex.getMessage();
        }
        finally{
            DBUtil.CloseConnection(con);
        }
        
        return usuarios;
    }
    
    public static boolean Excluir(String email){
        boolean retorno = false;
        
        MySQLConnection con = null;
        PreparedStatement stmt = null;
        int resultado = 0;
        
        try{
            con = DBUtil.GetConnection();
            stmt = (PreparedStatement) 
                    con.prepareStatement("delete from usuarios where email = ?;");
            stmt.setString(1, email);
            
            resultado = stmt.executeUpdate();
            if(resultado > 0){
                retorno = true;
            }
            else{
                retorno = false;
            }
            
            stmt.close();
        }
        catch(Exception ex){
            
        }
        finally{
            DBUtil.CloseConnection(con);
        }
        
        return retorno;
    }
    
    public static boolean Atualizar(Usuario usuario){
        boolean retorno = false;
        
        MySQLConnection con = null;
        PreparedStatement stmt = null;
        int resultado = 0;
        
        try{
            con = DBUtil.GetConnection();
            stmt = (PreparedStatement) 
                    con.prepareStatement("update usuarios set nome = ?, "
                            + "senha = ?, datanascimento = ? where "
                            + "email = ?;");
            
            stmt.setString(1, usuario.nome);
            stmt.setString(2, usuario.senha);
            stmt.setDate(3, (Date) usuario.dataNascimento);
            stmt.setString(4, usuario.email);
            
            resultado = stmt.executeUpdate();
            if(resultado > 0){
                retorno = true;
            }
            else{
                retorno = false;
            }
            
            stmt.close();
        }
        catch(Exception ex){
            
        }
        finally{
            DBUtil.CloseConnection(con);
        }
        
        return retorno;
    }
}
