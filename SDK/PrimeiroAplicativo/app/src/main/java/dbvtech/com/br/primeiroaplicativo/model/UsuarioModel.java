package dbvtech.com.br.primeiroaplicativo.model;

import android.util.Log;

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
}
