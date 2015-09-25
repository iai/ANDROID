package dbvtech.com.br.menudrawer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context;

    private String[] menutitles;
    private TypedArray menuicons;
    private CharSequence tituloDrawer;
    private CharSequence titulo;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private List<ItemMenu> itensMenu;
    private MenuAdapter menuAdapter;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        titulo = tituloDrawer = getTitle();
        menutitles = getResources().getStringArray(R.array.titles);
        menuicons = getResources().obtainTypedArray(R.array.icons);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.slider_list);

        itensMenu = new ArrayList<>();
        for(int i=0; i < menutitles.length; i++){
            ItemMenu item = new ItemMenu();
            item.title = menutitles[i];
            item.icon = menuicons.getResourceId(i, -1);
            itensMenu.add(item);
        }

        menuicons.recycle();

        menuAdapter = new MenuAdapter(context, itensMenu);
        drawerList.setAdapter(menuAdapter);

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                updateDisplay(i);
            }
        });

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.mipmap.ic_launcher, R.string.app_name, R.string.app_name){
            @Override
            public void onDrawerOpened(View drawerView) {
                //getActionBar().setTitle(titulo);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                //getActionBar().setTitle(tituloDrawer);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        if(savedInstanceState == null){
            updateDisplay(0);
        }
    }

    private void updateDisplay(int posicao){
        Fragment fragment = null;
        switch (posicao){
            case 0:
                fragment = new FBFragment();
                break;
            case 1:
                fragment = new GPFragment();
                break;
            case 2:
                fragment = new TTFragment();
                break;
            default:
                break;
        }

        if(fragment != null){
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.frame_container, fragment).commit();
            setTitle(menutitles[posicao]);
            drawerLayout.closeDrawer(drawerList);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        titulo = title;
        //getActionBar().setTitle(titulo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try{
            if(drawerToggle.onOptionsItemSelected(item)){
                return true;
            }

            switch (item.getItemId()) {
                case R.id.action_settings:
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
        catch (Exception ex){

        }

        return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isDrawerOpened = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_settings).setVisible(!isDrawerOpened);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}

