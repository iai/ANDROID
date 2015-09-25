package dbvtech.com.br.menudrawer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Paulo on 22/07/2015.
 */
public class MenuAdapter extends BaseAdapter {

    private Context context;
    private List<ItemMenu> itens;

    public MenuAdapter(Context context, List<ItemMenu> itens){
        this.context = context;
        this.itens = itens;
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Object getItem(int i) {
        return itens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            view = View.inflate(context, R.layout.list_item, null);
        }

        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        TextView title = (TextView) view.findViewById(R.id.title);

        ItemMenu item = itens.get(i);

        icon.setImageResource(item.icon);
        title.setText(item.title);

        return view;
    }
}
