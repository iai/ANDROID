package dbvtech.com.br.dbwonder.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import dbvtech.com.br.dbwonder.R;
import dbvtech.com.br.dbwonder.bean.Aluno;

/**
 * Created by Paulo on 16/07/2015.
 */
public class AlunoAdapter extends BaseAdapter {

    private Context context;
    private List<Aluno> alunos;

    public AlunoAdapter(Context context, List<Aluno> alunos){
        this.context = context;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int i) {
        return alunos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return alunos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            view = View.inflate(context, R.layout.item_aluno, null);
        }

        final ImageView foto = (ImageView) view.findViewById(R.id.foto);
        TextView nome = (TextView) view.findViewById(R.id.nome);
        TextView email = (TextView) view.findViewById(R.id.email);

        final Aluno a = alunos.get(i);

        nome.setText(a.Nome);
        email.setText(a.Email);

        AsyncTask<Void, Void, Bitmap> tarefa = new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... voids) {

                Bitmap bitmap = null;

                try{
                    URL url = new URL(a.Foto);
                    InputStream dadoBinario = url.openStream();
                    bitmap = BitmapFactory.decodeStream(dadoBinario);
                }
                catch (Exception ex){
                    Log.e("AlunoAdapter", ex.getMessage());
                }

                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                foto.setImageBitmap(bitmap);
            }
        };

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            tarefa.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else{
            tarefa.execute();
        }

        return view;
    }
}
