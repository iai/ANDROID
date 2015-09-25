package dbvtech.com.br.primeiroaplicativo.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dbvtech.com.br.primeiroaplicativo.R;
import dbvtech.com.br.primeiroaplicativo.model.UsuarioModel;

public class MainActivity extends Activity {

    private Context context;

    private EditText email;
    private EditText senha;
    private Button botaoEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        loadCompoents();

        loadActions();
    }

    //Este metodo carrega todos os componentes de tela
    private void loadCompoents(){
        email = (EditText) findViewById(R.id.email);
        senha = (EditText) findViewById(R.id.senha);
        botaoEntrar = (Button) findViewById(R.id.botaoEntrar);
    }

    private void loadActions(){
        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().isEmpty()){
                    Toast.makeText(context, "Digite o seu e-mail", Toast.LENGTH_LONG).show();
                }
                else if(senha.getText().toString().isEmpty()){
                    Toast.makeText(context, "Digite a sua senha", Toast.LENGTH_LONG).show();
                }
                else{
                    //Toast.makeText(context, "Deu certo!", Toast.LENGTH_LONG).show();
                    if(UsuarioModel.VerificarEmail(email.getText().toString())){
                        if(UsuarioModel.Login(email.getText().toString(), senha.getText().toString())){
                            //entra no app
                        }
                        else{
                            //mostra toast
                            Toast.makeText(context, "Usuário inválido", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);

                        builder.setMessage("Este e-mail não existe no banco. Deseja criá-lo?");
                        builder.setCancelable(false);
                        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent telaFormulario = new Intent(context, AtividadeFormulario.class);
                                startActivity(telaFormulario);
                            }
                        });
                        builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                }
            }
        });
    }

}
