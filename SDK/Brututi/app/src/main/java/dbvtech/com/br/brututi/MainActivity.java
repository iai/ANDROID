package dbvtech.com.br.brututi;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private Button botaoLigar;
    private Button botaoVisivel;
    private Button botaoListar;
    private Button botaoDesligar;
    private ListView listaPareados;

    private BluetoothAdapter adapter;
    private Set<BluetoothDevice> dispositivosPareados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        loadComponents();
        loadActions();
    }

    private void loadComponents(){
        botaoLigar = (Button) findViewById(R.id.botaoLigar);
        botaoVisivel = (Button) findViewById(R.id.botaoVisivel);
        botaoListar = (Button) findViewById(R.id.botaoListar);
        botaoDesligar = (Button) findViewById(R.id.botaoDesligar);
        listaPareados = (ListView) findViewById(R.id.listaPareados);

        adapter = BluetoothAdapter.getDefaultAdapter();
    }

    private void loadActions(){
        botaoLigar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!adapter.isEnabled()){
                    Intent intentLigarBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intentLigarBT, 12345);
                    Toast.makeText(context, "Agora tá ligado", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(context, "Bluetooth já ligado", Toast.LENGTH_LONG).show();
                }
            }
        });

        botaoVisivel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVisivel = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                startActivityForResult(intentVisivel, 56789);
            }
        });

        botaoListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispositivosPareados = adapter.getBondedDevices();

                ArrayList list = new ArrayList();
                for (BluetoothDevice bt : dispositivosPareados){
                    list.add(bt.getName());
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, list);
                listaPareados.setAdapter(arrayAdapter);
                Toast.makeText(context, "Dispositivos encontrados", Toast.LENGTH_LONG).show();
            }
        });

        botaoDesligar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.disable();
                Toast.makeText(context, "BT desligado", Toast.LENGTH_LONG).show();
            }
        });
    }
}
