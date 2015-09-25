package dbvtech.com.br.notagaintwo;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;

import org.json.JSONObject;

public class LogadoActivity extends AppCompatActivity {

    private Context context;

    private AccessToken accessToken;
    private CallbackManager manager;

    private TextView id;
    private TextView nome;
    private EditText titulo;
    private EditText texto;
    private Button botaoPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logado);

        context = this;

        id = (TextView) findViewById(R.id.id);
        nome = (TextView) findViewById(R.id.nome);
        titulo = (EditText) findViewById(R.id.titulo);
        texto = (EditText) findViewById(R.id.texto);
        botaoPost = (Button) findViewById(R.id.botaoPostar);

        accessToken = getIntent().getParcelableExtra("accessToken");
        manager = CallbackManager.Factory.create();

        final ProgressDialog dialog = ProgressDialog.show(context, "ATENÇÃO", "Carregando ...");

        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                try{
                    if(dialog.isShowing())
                        dialog.cancel();

                    id.setText(jsonObject.getString("id"));
                    nome.setText(jsonObject.getString("name"));
                }
                catch (Exception ex){

                }
            }
        });

        request.executeAsync();

        botaoPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareLinkContent.Builder contentBuilder = new ShareLinkContent.Builder()
                        .setContentTitle(titulo.getText().toString())
                        .setContentDescription(texto.getText().toString())
                        .setImageUrl(Uri.parse("http://biosanctuary.com/wp-content/uploads/2012/05/crazy-cartoon.gif"));

                ShareApi.share(contentBuilder.build(), new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(context, "Enviado com sucesso", Toast.LENGTH_LONG).show();
                        titulo.setText("");
                        texto.setText("");
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(context, "Cancelado pelo usuário", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException e) {
                        Toast.makeText(context, "Erro ao enviar post", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }


}
