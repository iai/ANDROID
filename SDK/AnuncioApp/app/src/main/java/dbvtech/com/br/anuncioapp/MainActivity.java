package dbvtech.com.br.anuncioapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {

    private InterstitialAd interstitial;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId("ca-app-pub-6334626013777566/8314096933");

        adView = (AdView) findViewById(R.id.adView);

        AdRequest request = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("BCCC48957BDD6EBF06ECABE13A0A9BF9")
                .build();

        adView.loadAd(request);
        interstitial.loadAd(request);

        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mostrarAnuncio();
            }
        });
    }

    private void mostrarAnuncio(){
        if(interstitial.isLoaded()){
            interstitial.show();
        }
    }
}
