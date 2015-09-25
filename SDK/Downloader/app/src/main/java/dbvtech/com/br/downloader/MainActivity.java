package dbvtech.com.br.downloader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends Activity {

    private Context context;
    private Button botaoDownload;
    private ImageView foto;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        botaoDownload = (Button) findViewById(R.id.botaoDownload);
        foto = (ImageView) findViewById(R.id.foto);

        botaoDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(progress == null){
                    progress = ProgressDialog.show(context, "ATENÇÃO", "Baixando imagem. Aguarde ...");
                }

                AsyncTask<String, Void, Bitmap> tarefa = new AsyncTask<String, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(String... strings) {
                        Bitmap bitmap = null;

                        try{
                            URL url = new URL(strings[0]);
                            InputStream dadoBinario = url.openStream();
                            bitmap = BitmapFactory.decodeStream(dadoBinario);
                        }
                        catch (Exception ex){
                            Log.e("Download", ex.getMessage());
                        }

                        return bitmap;
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);

                        if(progress.isShowing()){
                            progress.cancel();
                        }

                        if(bitmap != null){
                            foto.setImageBitmap(bitmap);
                        }
                        else{
                            Toast.makeText(context, "Não foi possível baixar a foto", Toast.LENGTH_LONG).show();
                        }
                    }
                };

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
                    tarefa.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://tinyurl.com/iaiiai001");
                }
                else{
                    tarefa.execute("http://tinyurl.com/iaiiai001");
                }
            }
        });
    }


}
