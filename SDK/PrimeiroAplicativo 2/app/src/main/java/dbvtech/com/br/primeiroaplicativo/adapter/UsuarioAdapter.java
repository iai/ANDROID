package dbvtech.com.br.primeiroaplicativo.adapter;

import android.content.Context;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import dbvtech.com.br.primeiroaplicativo.R;
import dbvtech.com.br.primeiroaplicativo.bean.Usuario;

/**
 * Created by Paulo on 13/07/2015.
 */
public class UsuarioAdapter extends BaseAdapter {

    private Context context;
    private List<Usuario> usuarios;

    public UsuarioAdapter(Context context, List<Usuario> usuarios){
        this.context = context;
        this.usuarios = usuarios;
    }

    @Override
    public int getCount() {
        return usuarios.size();
    }

    @Override
    public Object getItem(int i) {
        return usuarios.get(i);
    }

    @Override
    public long getItemId(int i) {
        return usuarios.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            view = View.inflate(context, R.layout.item_usuario, null);
        }

        ImageView foto = (ImageView) view.findViewById(R.id.foto);
        TextView email = (TextView) view.findViewById(R.id.email);
        TextView nome = (TextView) view.findViewById(R.id.nome);

        Usuario u = usuarios.get(i);
        email.setText(u.Email);
        nome.setText(u.Nome);

        return view;
    }
}
