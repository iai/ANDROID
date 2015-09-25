package dbvtech.com.br.dbwonder.bean;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by Paulo on 15/07/2015.
 */
public class Aluno extends SugarRecord<Aluno> {
    public String Email;
    public String Nome;
    public String Foto;
    public String DataMatricula;
    public boolean Pago;

    public Aluno() {
    }

}
