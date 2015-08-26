package all_action.iblaudas.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import all_action.iblaudas.JsonModel.ModelHomeFragment;
import all_action.iblaudas.R;
import all_action.iblaudas.adapter.MenuAdapter;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    ListView recyclerView;

    private MenuAdapter adapter;

    private static String TAG = MainActivity.class.getSimpleName();
    private static SharedPreferences user;
    private String member_id;

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    ListView mMyListView;
    DatabaseHandler mydb;
    public static String PACKAGE_NAME;
    String title;

    MenuItem searchItem;



    private final String[] menuTitleslogin = {
            "Home",
            "My Order Cars",
            "My Account",
            "Contact Us",
            "Log in"};
    private final int[] imgslidelogin = {R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car};

    //===lgoin out ===
    private final String[] menuTitles = {"My Favorite Cars",
            "Featured Cars",
            "View My Cars",
            "Edit Profile",
            "Logout"};
    private final int[] imgslide = {R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car};


    ArrayList<String> items ;
    ArrayList<Integer> Iconitems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PACKAGE_NAME = getApplicationContext().getPackageName();
        user = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        member_id = user.getString("member_id", "");
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        items = new ArrayList<String>();
       Iconitems = new ArrayList<Integer>();
        if (member_id.equals("")) {
        for (int i = 0; i < menuTitleslogin.length; i++) {
            items.add(menuTitleslogin[i]);
            Iconitems.add(imgslidelogin[i]);
                }
             }else{
              for (int i = 0; i < menuTitles.length; i++) {
               items.add(menuTitles[i]);
               Iconitems.add(imgslide[i]);
              }

    }
        recyclerView = (ListView) findViewById(R.id.drawerList);
        adapter = new MenuAdapter(getApplicationContext(), items,Iconitems,this);
        recyclerView.setAdapter(adapter);
        displayView(0);

        // DatabaseHandler db = new DatabaseHandler(this);
        // db.Myqery();

    }
    @Override
    public void onDrawerItemSelected(View view, int position) {
        Toast.makeText(this,""+items.get(position),Toast.LENGTH_SHORT).show();

        displayView(position);

    }
    ArrayList<ModelHomeFragment> mainList;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager)getApplication().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Intent intentSearch = new Intent(MainActivity.this,SearchResultActivity.class);
                    intentSearch.putExtra("search_data",query);
                    startActivityForResult(intentSearch, 2);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    Log.e("sumbite",""+query);
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        if (searchView != null) {
            Log.e("sumbite","sdsd");
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerFragment.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void displayView(final int position) {
        Intent intentActivity;
        Fragment fragment = null;
        title = getString(R.string.app_name);

        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                if (!member_id.equals("")) {
                    intentActivity = new Intent(this, OrderCarListUser.class);
                    startActivity(intentActivity);
                }else{
                    intentActivity = new Intent(this,LoginActivity.class);
                    startActivity(intentActivity);
                    this.overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
                }
                break;
            case 2:

                if (!member_id.equals("")) {
                    intentActivity = new Intent(this, ListChatUser.class);
                    startActivity(intentActivity);
                    Log.d("ListChatUser", "ListChatUser");
                }else{
                    Log.d("LoginActivity", "LoginActivity");
                    intentActivity = new Intent(this,LoginActivity.class);
                    startActivity(intentActivity);
                    this.overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
                }
               break;

            case 3:
                if (!member_id.equals("")) {
                    Log.d("Login","Log in True");
                }else{
                    Log.d("ContactUsFragment","ContactUsFragment");
                    intentActivity = new Intent(this, ContactUsFragment.class);
                    startActivityForResult(intentActivity,1);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }

                break;
            case 4:
                    Log.d("Clear user","Log Out True");
                    SharedPreferences.Editor editorUser = user.edit();
                    editorUser.clear();
                    editorUser.commit();

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //./fragmentTransaction.addToBackStack(title);
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("MainActivityResult", "Resualt" + resultCode);



    }

    private void hideKeyboard(View view) {
        // Check if no view has focus:
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }
    //*****************************Back Close App Fuction*************************
    int backButtonCount = 0;
    @Override
    public void onBackPressed() {
        if(backButtonCount++ >= 2) {super.onBackPressed();}
        else {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }//****************End******************************************



}