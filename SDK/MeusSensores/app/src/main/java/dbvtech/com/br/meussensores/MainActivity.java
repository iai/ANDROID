package dbvtech.com.br.meussensores;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context;

    private Spinner spinnerSensores;
    private TextView valores;

    private SensorManager sensorManager;
    private List<Sensor> sensores;
    private int sensorSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        loadComponents();
        loadActions();
        loadData();
    }

    private void loadComponents(){
        spinnerSensores = (Spinner) findViewById(R.id.spinnerSensores);
        valores = (TextView) findViewById(R.id.valores);
    }

    private void loadActions(){
        spinnerSensores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sensorSelecionado = i;
                valores.setText("");
                sensorManager.unregisterListener(listener);
                sensorManager.registerListener(listener, sensores.get(sensorSelecionado), SensorManager.SENSOR_DELAY_NORMAL);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void loadData(){
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensores = sensorManager.getSensorList(Sensor.TYPE_ALL);

        List<String> nomesSensores = new ArrayList<>();
        for(Sensor sensor : sensores){
            nomesSensores.add(sensor.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, nomesSensores);
        spinnerSensores.setAdapter(adapter);
    }

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            for(int i=0; i < sensorEvent.values.length; i++){
                valores.append("valores[" + i + "]" + sensorEvent.values[i] + "\r\n");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(listener, sensores.get(sensorSelecionado), SensorManager.SENSOR_DELAY_NORMAL);
    }
}
