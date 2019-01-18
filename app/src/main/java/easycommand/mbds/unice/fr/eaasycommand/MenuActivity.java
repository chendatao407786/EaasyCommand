package easycommand.mbds.unice.fr.eaasycommand;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import easycommand.mbds.unice.fr.eaasycommand.api.RetrofitInstance;
import easycommand.mbds.unice.fr.eaasycommand.api.model.MenuApi;
import easycommand.mbds.unice.fr.eaasycommand.fragment.MenuFragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private MenuApi menuApi = RetrofitInstance.getRetrofitInstance().create(MenuApi.class);
    private ArrayList<String> mCategoryList = new ArrayList<>();
    private JSONArray mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        //********************************* ==> i comment this code for testing my nfc code
        /*Intent intent = getIntent();
        Bundle data = intent.getExtras();
        View headerLayout = navigationView.getHeaderView(0);
        TextView username = headerLayout.findViewById(R.id.username);
        TextView email = headerLayout.findViewById(R.id.email);
        username.setText(data.getString("username"));
        email.setText(data.getString("email"));*/


        //***************************** ==> to get idResto and idTable

        String idResto = NfcReadActivity.copyIdResto;
        String idTable = NfcReadActivity.copyIdTable;

        Toast.makeText(getApplicationContext(), "idResto = "+idResto+", idTable = "+idTable, Toast.LENGTH_LONG).show();

        Menu menu = navigationView.getMenu();
        SubMenu subMenu = menu.addSubMenu(0,1,0,"Menu");

        loadMenu(subMenu,idResto);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String itemName = (String) item.getTitle();
        Toast.makeText(MenuActivity.this,itemName,Toast.LENGTH_LONG).show();
        FragmentManager fragmentManager =getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        MenuFragment menuFragment = new MenuFragment();
        transaction.replace(R.id.content_menu_activity,menuFragment);
        Bundle bundle = new Bundle();
        bundle.putString("title",itemName);
        bundle.putString("courses",prepareDataForFragement(itemName).toString());
        menuFragment.setArguments(bundle);
        transaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void loadMenu(final SubMenu menu,String idResto) {

        Call<ResponseBody> call = menuApi.getMenu(idResto);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    try {
                        JSONObject res = new JSONObject(response.body().string());
                        String restoName = res.getString("restoName");
                        mMenu = new JSONArray(res.getJSONArray("menu").toString());
                        for (int i = 0; i < mMenu.length(); i++) {
                            mCategoryList.add(mMenu.getJSONObject(i).getString("_id"));
                        }
                        for (int i = 0; i < mCategoryList.size(); i++) {
                            addMenu(menu,mCategoryList.get(i));
                        }

                    } catch (Exception e) {
                        Toast.makeText(MenuActivity.this, "get menu error :/\n" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MenuActivity.this, "get menu error :/\n" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }
    public JSONArray prepareDataForFragement(String itemName){
        JSONArray courses = new JSONArray();
        for(int i = 0;i <= mMenu.length();i++){
            try{
                if(mMenu.getJSONObject(i).getString("_id") == itemName){
                    courses = mMenu.getJSONObject(i).getJSONArray("courses");
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return courses;
    }

    private void addMenu(Menu menu,String category){
        switch (category){
            case "POISSONS":
                menu.add(R.id.category, menu.NONE, 0, category).setIcon(R.drawable.ic_seafood);
                break;
            case "ENTRÉES":
                menu.add(R.id.category, menu.NONE, 0, category).setIcon(R.drawable.ic_baguette);
                break;
            default:
                menu.add(R.id.category, menu.NONE, 0, category).setIcon(R.drawable.ic_tableware);

        }
    }
}
