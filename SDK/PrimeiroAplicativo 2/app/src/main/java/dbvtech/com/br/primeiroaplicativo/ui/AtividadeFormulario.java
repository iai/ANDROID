package dbvtech.com.br.primeiroaplicativo.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import dbvtech.com.br.primeiroaplicativo.R;
import dbvtech.com.br.primeiroaplicativo.bean.Usuario;
import dbvtech.com.br.primeiroaplicativo.model.UsuarioModel;

public class AtividadeFormulario extends Activity {

    public Context context;

    private ImageButton botaoVoltar;
    private ImageButton botaoSalvar;
    private ImageButton botaoExcluir;
    private EditText email;
    private EditText nome;
    private EditText senha;
    private ImageView foto;
    private Button botaoAddFoto;
    private Button botaoDelFoto;

    private boolean isUpdate;
    private String emailUsuario;
    private Usuario usuarioGlobal;
    private Bitmap fotinho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_formulario);

        context = this;

        loadComponents();
        loadActions();
        loadData();
    }

    private void loadComponents(){
        botaoExcluir = (ImageButton) findViewById(R.id.botaoExcluir);
        botaoSalvar = (ImageButton) findViewById(R.id.botaoSalvar);
        botaoVoltar = (ImageButton) findViewById(R.id.botaoVoltar);
        email = (EditText) findViewById(R.id.email);
        nome = (EditText) findViewById(R.id.nome);
        senha = (EditText) findViewById(R.id.senha);
        foto = (ImageView) findViewById(R.id.foto);
        botaoAddFoto = (Button) findViewById(R.id.botaoAddFoto);
        botaoDelFoto = (Button) findViewById(R.id.botaoDelFoto);
    }

    private void loadActions(){
        botaoExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UsuarioModel.Excluir(usuarioGlobal) == true){
                    finish();
                }
                else{
                    Toast.makeText(context, "Erro ao excluir usuário", Toast.LENGTH_LONG).show();
                }
            }
        });

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().equals("")){
                    Toast.makeText(context, "Digite seu e-mail", Toast.LENGTH_LONG).show();
                }
                else if(nome.getText().toString().equals("")){
                    Toast.makeText(context, "Digite seu nome", Toast.LENGTH_LONG).show();
                }
                else if(senha.getText().toString().equals("")){
                    Toast.makeText(context, "Digite sua senha", Toast.LENGTH_LONG).show();
                }
                else{
                    if(isUpdate == false){
                        Usuario usuario = new Usuario();
                        usuario.Email = email.getText().toString();
                        usuario.Nome = nome.getText().toString();
                        usuario.Senha = senha.getText().toString();
                        usuario.Foto = SalvarFoto();

                        if(UsuarioModel.Insert(usuario) == true){
                            Toast.makeText(getBaseContext(), "Usuário incluído com sucesso!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else{
                            Toast.makeText(context, "Erro ao incluir usuário", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        //é uma atualizacao
                        usuarioGlobal.Email = email.getText().toString();
                        usuarioGlobal.Nome = nome.getText().toString();
                        usuarioGlobal.Senha = senha.getText().toString();
                        usuarioGlobal.Foto = SalvarFoto();

                        if(UsuarioModel.Insert(usuarioGlobal) == true){
                            Toast.makeText(getBaseContext(), "Usuário atualizado com sucesso!", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(context, "Erro ao atualizar usuário", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        botaoAddFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent telaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(telaCamera, 1234);
            }
        });

        botaoDelFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foto.setImageBitmap(null);
                fotinho = null;
            }
        });
    }

    private void loadData(){
        if(getIntent().hasExtra("isUpdate")){
            isUpdate = getIntent().getExtras().getBoolean("isUpdate");
            if(isUpdate == false){
                //incluir novo usuario
                botaoExcluir.setVisibility(View.INVISIBLE);
                email.setText(getIntent().getExtras().getString("email"));
                senha.setText(getIntent().getExtras().getString("senha"));
            }
            else{
                //atualizar um usuario
                emailUsuario = getIntent().getExtras().getString("email");
                usuarioGlobal = UsuarioModel.GetUsuario(emailUsuario);
                email.setText(usuarioGlobal.Email);
                nome.setText(usuarioGlobal.Nome);
                senha.setText(usuarioGlobal.Senha);

                fotinho = BitmapFactory.decodeFile(usuarioGlobal.Foto);
                foto.setImageBitmap(fotinho);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1234 && resultCode == RESULT_OK){
            fotinho = (Bitmap) data.getExtras().get("data");
            foto.setImageBitmap(fotinho);
        }
    }

    private String SalvarFoto(){

        String retorno = "";
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File nomeArquivo = new File(dir.getPath(), String.valueOf((new Date()).getTime()) + ".jpg");
        retorno = nomeArquivo.getPath();

        try{
            FileOutputStream fos = new FileOutputStream(nomeArquivo);

            fotinho.compress(Bitmap.CompressFormat.JPEG, 85, fos);

            fos.flush();
            fos.close();
        }
        catch (Exception ex){
            Log.e("Form", ex.getMessage());
            retorno = "";
        }

        return retorno;
    }
}
