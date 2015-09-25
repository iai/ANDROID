package dbvtech.com.br.cripografia;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Context context;

    private EditText senha;
    private Button botaoProteger;
    private Button botaoDesproteger;
    private EditText textoClaro;
    private EditText textoProtegido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        senha = (EditText) findViewById(R.id.senha);
        textoClaro = (EditText) findViewById(R.id.textoClaro);
        textoProtegido = (EditText) findViewById(R.id.textoProtegido);
        botaoProteger = (Button) findViewById(R.id.botaoProteger);
        botaoDesproteger = (Button) findViewById(R.id.botaoDesproteger);

        botaoProteger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    textoProtegido.setText(CryptoUtil.proteger(textoClaro.getText().toString(), senha.getText().toString()));
                    textoClaro.setText("");
                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        botaoDesproteger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    textoClaro.setText(CryptoUtil.desproteger(textoProtegido.getText().toString(), senha.getText().toString()));
                    textoProtegido.setText("");
                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
