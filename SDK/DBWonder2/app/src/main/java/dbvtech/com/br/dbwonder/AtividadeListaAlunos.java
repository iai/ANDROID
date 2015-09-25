package dbvtech.com.br.dbwonder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import dbvtech.com.br.dbwonder.adapter.AlunoAdapter;
import dbvtech.com.br.dbwonder.bean.Aluno;

public class AtividadeListaAlunos extends Activity {

    private Context context;
    private ListView listaAlunos;
    private List<Aluno> alunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_lista_alunos);

        context = this;

        listaAlunos = (ListView) findViewById(R.id.listaAlunos);

        alunos = Aluno.find(Aluno.class, "", new String[]{});
        AlunoAdapter adapter = new AlunoAdapter(context, alunos);
        listaAlunos.setAdapter(adapter);

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }


}
