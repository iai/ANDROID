package dbvtech.com.br.consultacep;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends Activity {

    private Context context;

    private EditText cep;
    private Button botaoOK;
    private TextView rua;
    private TextView bairro;
    private TextView cidade;
    private TextView uf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        loadComponents();
        loadAction();
    }

    private void loadComponents(){
        cep = (EditText) findViewById(R.id.cep);
        rua = (TextView) findViewById(R.id.rua);
        bairro = (TextView) findViewById(R.id.bairro);
        cidade = (TextView) findViewById(R.id.cidade);
        uf = (TextView) findViewById(R.id.uf);
        botaoOK = (Button) findViewById(R.id.botaoOK);
    }

    private void loadAction(){
        botaoOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cep.getText().toString().isEmpty()){
                    Toast.makeText(context, "Digite o CEP. Somente números", Toast.LENGTH_LONG).show();
                }
                else{
                    try{

                        Integer.parseInt(cep.getText().toString());

                        if(cep.getText().toString().length() != 8){
                            throw new NumberFormatException("sdfsdf");
                        }

                        final ProgressDialog progressDialog = ProgressDialog.show(context, "ATENÇÃO", "Consultado. Aguarde ...");
                        final String dadosCep = cep.getText().toString();

                        AsyncTask<Void, Void, JSONObject> tarefa = new AsyncTask<Void, Void, JSONObject>() {
                            @Override
                            protected JSONObject doInBackground(Void... voids) {
                                JSONObject json = null;

                                try{
                                    DefaultHttpClient client = new DefaultHttpClient();
                                    HttpGet get = new HttpGet("http://cep.correiocontrol.com.br/" + dadosCep + ".json");
                                    HttpResponse response = client.execute(get);

                                    HttpEntity entity = response.getEntity();
                                    InputStream dadoBinario = entity.getContent();

                                    InputStreamReader leitorBinario = new InputStreamReader(dadoBinario);
                                    BufferedReader leitorTexto = new BufferedReader(leitorBinario);

                                    String linha = "";
                                    String texto = "";

                                    while((linha = leitorTexto.readLine()) != null){
                                        texto += linha;
                                    }

                                    json = new JSONObject(texto);
                                }
                                catch (Exception ex){
                                    Log.e("MainActivity", ex.getMessage());
                                }

                                return json;
                            }

                            @Override
                            protected void onPostExecute(JSONObject jsonObject) {
                                super.onPostExecute(jsonObject);

                                if(progressDialog.isShowing())
                                    progressDialog.cancel();

                                if(jsonObject != null){
                                    try{
                                        rua.setText(jsonObject.getString("logradouro"));
                                        bairro.setText(jsonObject.getString("bairro"));
                                        cidade.setText(jsonObject.getString("localidade"));
                                        uf.setText(jsonObject.getString("uf"));
                                    }
                                    catch (Exception ex){
                                        Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        };

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
                            tarefa.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        }
                        else{
                            tarefa.execute();
                        }

                    }
                    catch (NumberFormatException nfe){
                        Toast.makeText(context, "Somente números seu mané!", Toast.LENGTH_LONG).show();
                    }
                    catch (Exception ex){
                        Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

}
