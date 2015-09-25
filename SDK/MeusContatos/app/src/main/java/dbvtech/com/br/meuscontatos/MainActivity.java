package dbvtech.com.br.meuscontatos;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private Context context;

    private ListView listaContatos;
    private List<Contato> contatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        listaContatos = (ListView) findViewById(R.id.listaContatos);

        loadContacts();

        ContatosAdapter adapter = new ContatosAdapter(context, contatos);
        listaContatos.setAdapter(adapter);
    }

    private void loadContacts(){

        contatos = new ArrayList<>();

        try {
            Uri uriContatos = ContactsContract.Contacts.CONTENT_URI;
            String _ID = ContactsContract.Contacts._ID;
            String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
            String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

            Uri uriTelefones = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String PHONE_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
            String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

            Uri uriEmails = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
            String EMAIL_CONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
            String DATA = ContactsContract.CommonDataKinds.Email.DATA;

            ContentResolver resolver = getContentResolver();
            Cursor cursorContato = resolver.query(uriContatos, null, null, null, null);

            if (cursorContato.getCount() > 0) {

                while(cursorContato.moveToNext()){

                    Contato contato = new Contato();
                    contato.Id = cursorContato.getString(cursorContato.getColumnIndex(_ID));
                    contato.Nome = cursorContato.getString(cursorContato.getColumnIndex(DISPLAY_NAME));

                    int temTelefone = Integer.parseInt(cursorContato.getString(cursorContato.getColumnIndex(HAS_PHONE_NUMBER)));
                    if(temTelefone > 0){

                        Cursor cursorTelefone = resolver.query(uriTelefones, null, PHONE_CONTACT_ID + " = ?", new String[]{contato.Id}, null);
                        if(cursorTelefone.getCount() > 0){

                            while(cursorTelefone.moveToNext()){

                                contato.Telefone += cursorTelefone.getString(cursorTelefone.getColumnIndex(NUMBER)) + "   ";

                            }

                        }

                        cursorTelefone.close();
                    }

                    Cursor cursorEmail = resolver.query(uriEmails, null, EMAIL_CONTACT_ID + " = ?", new String[]{contato.Id}, null);
                    if(cursorEmail.getCount() > 0){

                        while(cursorEmail.moveToNext()){

                            contato.Email += cursorEmail.getString(cursorEmail.getColumnIndex(DATA)) + "   ";

                        }

                    }

                    cursorEmail.close();

                    contatos.add(contato);
                }

            } else {
                Toast.makeText(context, "Nenhum contato encontrado", Toast.LENGTH_LONG).show();
            }

            cursorContato.close();
        }
        catch (Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
