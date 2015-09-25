package dbvtech.com.br.mapaprojeto;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MainActivity extends Activity {

    private Context context;

    private Button botaoNormal;
    private Button botaoSatelite;
    private Button botaoHibrido;
    private Button botaoTerreno;
    private Button botaoRodar;
    private Button botaoTombar;
    private Button botaoMarcador;
    private GoogleMap map;

    private int posicao;
    private float tilt;

    private MarkerOptions opIAI;

    private static final LatLng SANTOS = new LatLng(-23.950584, -46.330341);
    private static final LatLng IAI = new LatLng(-23.581633, -46.683815);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        loadCompoents();
        loadActions();
        loadMap();

        posicao = 0;
        tilt = 0;
    }

    private void loadCompoents(){
        botaoHibrido = (Button) findViewById(R.id.botaoHibrido);
        botaoNormal = (Button) findViewById(R.id.botaoNormal);
        botaoSatelite = (Button) findViewById(R.id.botaoSatelite);
        botaoTerreno = (Button) findViewById(R.id.botaoTerreno);
        botaoRodar = (Button) findViewById(R.id.botaoRodar);
        botaoTombar = (Button) findViewById(R.id.botaoTombar);
        botaoMarcador = (Button) findViewById(R.id.botaoMarcador);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
    }

    private void loadActions(){
        botaoNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });

        botaoSatelite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        botaoHibrido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        });

        botaoTerreno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            }
        });

        botaoRodar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((posicao + 90) >= 360){
                    posicao = 0;
                }
                else{
                    posicao += 90;
                }

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(IAI)
                        .bearing(posicao)
                        .zoom(15)
                        .build();

                CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);
                map.animateCamera(update);
            }
        });

        botaoTombar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((tilt + 5) >= 90){
                    tilt = 0;
                }
                else{
                    tilt += 5;
                }

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(IAI)
                        .tilt(tilt)
                        .zoom(15)
                        .build();

                CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);
                map.animateCamera(update);
            }
        });

        botaoMarcador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        LinearLayout linearLayout = new LinearLayout(context);
                        linearLayout.setOrientation(LinearLayout.VERTICAL);
                        linearLayout.setLayoutParams(
                                new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                ));

                        TextView tvTitulo = new TextView(context);
                        tvTitulo.setText("Este é o conteúdo da view");
                        tvTitulo.setTextColor(Color.BLACK);
                        linearLayout.addView(tvTitulo);

                        return linearLayout;
                    }
                });
            }
        });

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                map.clear();

                map.addMarker(opIAI);

                MarkerOptions opClicado = new MarkerOptions();
                opClicado.title("Eu cliquei aqui");
                opClicado.position(latLng);
                map.addMarker(opClicado);

                Geocoder geocoder = new Geocoder(context);

                try{
                    List<Address> enderecos = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    Address esse = enderecos.get(0);
                    Toast.makeText(context, esse.toString(), Toast.LENGTH_LONG).show();
                }
                catch (Exception ex){
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void loadMap(){
        MapsInitializer.initialize(context);

        if(map != null){
            MarkerOptions opSantos = new MarkerOptions();
            opSantos.position(SANTOS);
            opSantos.title("Minha casa");
            map.addMarker(opSantos);

            opIAI = new MarkerOptions();
            opIAI.position(IAI);
            opIAI.title("IAI?");
            opIAI.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
            map.addMarker(opIAI);

            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(IAI, 15);
            map.moveCamera(update);

            map.setTrafficEnabled(true);
            map.setBuildingsEnabled(true);
            map.setIndoorEnabled(true);
            map.getUiSettings().setCompassEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);
        }
    }
}
