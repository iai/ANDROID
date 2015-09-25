package dbvtech.com.br.meuscontatos;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Paulo on 20/07/2015.
 */
public class ContatosAdapter extends BaseAdapter{

    private Context context;
    private List<Contato> contatos;

    public ContatosAdapter(Context context, List<Contato> contatos){
        this.context = context;
        this.contatos = contatos;
    }

    @Override
    public int getCount() {
        return contatos.size();
    }

    @Override
    public Object getItem(int i) {
        return contatos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            view = View.inflate(context, R.layout.item_contato, null);
        }

        TextView id = (TextView) view.findViewById(R.id.id);
        TextView nome = (TextView) view.findViewById(R.id.nome);
        TextView email = (TextView) view.findViewById(R.id.email);
        TextView telefone = (TextView) view.findViewById(R.id.telefone);

        Contato contato = contatos.get(i);

        id.setText(contato.Id);
        nome.setText(contato.Nome);
        email.setText(contato.Email);
        telefone.setText(contato.Telefone);

        return view;
    }
}
