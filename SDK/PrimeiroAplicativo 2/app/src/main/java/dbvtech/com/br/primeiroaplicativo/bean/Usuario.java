package dbvtech.com.br.primeiroaplicativo.bean;

import android.os.Parcelable;
import android.support.annotation.ColorRes;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Paulo on 08/07/2015.
 */
public class Usuario extends SugarRecord<Usuario>{
    public String Email;
    public String Nome;
    public String Senha;
    public String Foto;

    public Usuario() {
    }

    public Usuario(String email, String nome, String senha, String foto) {
        Email = email;
        Nome = nome;
        Senha = senha;
        Foto = foto;
    }

    @Override
    public String toString() {
        return Nome;
    }
}
