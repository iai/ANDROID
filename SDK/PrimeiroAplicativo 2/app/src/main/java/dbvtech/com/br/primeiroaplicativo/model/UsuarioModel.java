package dbvtech.com.br.primeiroaplicativo.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dbvtech.com.br.primeiroaplicativo.bean.Usuario;

/**
 * Created by Paulo on 08/07/2015.
 */
public class UsuarioModel {
    public static boolean Login(String email, String senha){
        boolean retorno = false;

        try{
            if(Usuario.count(Usuario.class, "email = ? and senha = ?",
                    new String[]{email, senha}) > 0)
                retorno = true;
            else
                retorno = false;
        }
        catch (Exception ex){
            Log.e("UsuarioModel", ex.getMessage());
        }

        return retorno;
    }

    public static boolean VerificarEmail(String email){
        boolean retorno = false;

        try{
            if(Usuario.count(Usuario.class, "email = ?",
                    new String[]{email}) > 0)
                retorno = true;
            else
                retorno = false;
        }
        catch (Exception ex){
            Log.e("UsuarioModel", ex.getMessage());
        }

        return retorno;
    }

    public static boolean Insert(Usuario usuario){
        boolean retorno = false;

        try{

            usuario.save();
            retorno = true;

        }
        catch (Exception ex){
            Log.e("UsuarioModel", ex.getMessage());
        }

        return retorno;
    }

    public static Usuario GetUsuario(String email){
        Usuario usuario = new Usuario();

        try{

            usuario = Usuario.find(Usuario.class, "email = ?", new String[]{ email }).get(0);

        }
        catch (Exception ex){
            Log.e("UsuarioModel", ex.getMessage());
        }

        return usuario;
    }

    public static List<Usuario> GetUsuario(){
        List<Usuario> usuarios = new ArrayList<>();

        try{

            usuarios = Usuario.find(Usuario.class, "", new String[]{});

        }
        catch (Exception ex){
            Log.e("UsuarioModel", ex.getMessage());
        }

        return usuarios;
    }

    public static boolean Excluir(Usuario usuario){
        boolean retorno = false;

        try{
            usuario.delete();
            retorno = true;
        }
        catch (Exception ex){
            Log.e("UsuarioModel", ex.getMessage());
        }

        return retorno;
    }
}
