package dbvtech.com.br.dbwonder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import dbvtech.com.br.dbwonder.bean.Aluno;
import dbvtech.com.br.dbwonder.bean.Materia;
import dbvtech.com.br.dbwonder.bean.Matricula;

public class MainActivity extends Activity {

    private Context context;

    private List<Aluno> alunos;
    private List<Materia> materias;
    private List<Matricula> matriculas;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        Button botaoCarga = (Button) findViewById(R.id.botaoCarga);

        botaoCarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(progressDialog == null)
                    progressDialog = ProgressDialog.show(context, "ATENÇÃO", "Carregando dados. Aguarde ...");

                AsyncTask<Void, Void, Void> tarefa = new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {

                        try{

                            DownloadJson(0);
                            DownloadJson(1);
                            DownloadJson(2);

                        }
                        catch (Exception ex){
                            Log.e("MainActivity", ex.getMessage());
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        if(progressDialog.isShowing())
                            progressDialog.cancel();

                        Intent intent = new Intent(context, AtividadeListaAlunos.class);
                        startActivity(intent);
                    }
                };

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
                    tarefa.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
                else{
                    tarefa.execute();
                }
            }
        });
    }

    private void DownloadJson(int entidade){
        //0 para aluno
        //1 para materia
        //2 para matricula

        try {

            Aluno.deleteAll(Aluno.class);
            Matricula.deleteAll(Matricula.class);
            Materia.deleteAll(Materia.class);

            DefaultHttpClient client = new DefaultHttpClient();

            HttpGet get = null;

            switch (entidade) {
                case 0:
                    get = new HttpGet("http://www.aprendermobile.com.br/escola/Alunos.json");
                    break;
                case 1:
                    get = new HttpGet("http://www.aprendermobile.com.br/escola/Materias.json");
                    break;
                case 2:
                    get = new HttpGet("http://www.aprendermobile.com.br/escola/Matriculas.json");
                    break;
            }

            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            InputStream dadoBinario = entity.getContent();

            InputStreamReader leitorBinario = new InputStreamReader(dadoBinario);
            BufferedReader leitorTexto = new BufferedReader(leitorBinario);

            String linha = "";
            String texto = "";

            while((linha = leitorTexto.readLine()) != null ){
                texto += linha;
            }

            JSONObject jsonObject = new JSONObject(texto);
            JSONArray array = null;

            switch (entidade){
                case 0:
                    array = jsonObject.getJSONArray("alunos");
                    break;
                case 1:
                    array = jsonObject.getJSONArray("materias");
                    break;
                case 2:
                    array = jsonObject.getJSONArray("matriculas");
                    break;
            }

            for(int i=0; i < array.length(); i++){
                JSONObject obj = array.getJSONObject(i);

                if(entidade == 0){
                    Aluno aluno = new Aluno();
                    aluno.Nome = obj.getString("nome");
                    aluno.DataMatricula = obj.getString("dataMatricula");
                    aluno.Email = obj.getString("email");
                    aluno.Foto = obj.getString("foto");
                    aluno.Pago = obj.getBoolean("pago");
                    aluno.save();
                }
                else if(entidade == 1){
                    Materia materia = new Materia();
                    materia.Nome = obj.getString("nome");
                    materia.save();
                }
                else if(entidade == 2){
                    Matricula matricula = new Matricula();

                    JSONArray arrayMaterias = obj.getJSONArray("materias");
                    String materias = "";
                    for(int x=0; x < arrayMaterias.length(); x++){
                        materias += arrayMaterias.getString(x) + ",";
                    }

                    materias = materias.substring(0, materias.length() - 1);
                    matricula.Materias = materias;
                    matricula.save();
                }
            }
        }
        catch (Exception ex){
            Log.e("MainActivity", ex.getMessage());
        }
    }
}
