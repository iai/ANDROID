package dbvtech.com.br.notagaintwo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Context context;

    private LoginButton botaoLogin;
    private CallbackManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);

        context = this;
        botaoLogin = (LoginButton) findViewById(R.id.botaoLogin);
        botaoLogin.setReadPermissions("user_friends");

        manager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithPublishPermissions(this, Arrays.asList("publish_actions"));
        botaoLogin.registerCallback(manager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent logado = new Intent(context, LogadoActivity.class);
                logado.putExtra("accessToken", loginResult.getAccessToken());
                startActivity(logado);
                finish();
            }

            @Override
            public void onCancel() {
                Toast.makeText(context, "Cancelado pelo usuário", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(context, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        manager.onActivityResult(requestCode, resultCode, data);
    }
}
