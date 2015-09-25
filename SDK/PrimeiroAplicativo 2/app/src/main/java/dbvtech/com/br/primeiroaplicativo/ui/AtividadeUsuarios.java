package dbvtech.com.br.primeiroaplicativo.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import dbvtech.com.br.primeiroaplicativo.R;
import dbvtech.com.br.primeiroaplicativo.adapter.UsuarioAdapter;
import dbvtech.com.br.primeiroaplicativo.bean.Usuario;
import dbvtech.com.br.primeiroaplicativo.model.UsuarioModel;

public class AtividadeUsuarios extends Activity {

    private Context context;

    private ListView listaUsuarios;
    private List<Usuario> dadosUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_usuarios);

        context = this;

        loadComponents();
        loadActions();
        loadData();
    }

    private void loadComponents(){
        listaUsuarios = (ListView) findViewById(R.id.listaUsuarios);
    }

    private void loadActions(){
        listaUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Usuario usuarioSelecionado = dadosUsuarios.get(i);
                Intent telaAtualizacao = new Intent(context, AtividadeFormulario.class);
                telaAtualizacao.putExtra("isUpdate", true);
                telaAtualizacao.putExtra("email", usuarioSelecionado.Email);
                startActivity(telaAtualizacao);
            }
        });
    }

    private void loadData(){
        dadosUsuarios = UsuarioModel.GetUsuario();
        if(dadosUsuarios.size() > 0){
            UsuarioAdapter adapter = new UsuarioAdapter(context, dadosUsuarios);
            listaUsuarios.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        loadData();
        super.onResume();
    }
}
