package info.androidhive.materialdesign.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.parse.codec.Encoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.JsonModel.ModelHomeFragment;
import info.androidhive.materialdesign.R;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();
    private  static SharedPreferences user;
    private  String member_id;

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    RecyclerView mMyListView;
    DatabaseHandler mydb;
    public static String PACKAGE_NAME;
    String title;
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
        mMyListView=(RecyclerView) findViewById(R.id.drawerList);
        // display the first navigation drawer view on app launch
        displayView(0);

        DatabaseHandler db = new DatabaseHandler(this);
       db.Myqery();

    }



    ArrayList<ModelHomeFragment> mainList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager)getApplication().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Intent intentSearch = new Intent(MainActivity.this,SearchResultActivity.class);
                    intentSearch.putExtra("search_data",query);
                    startActivity(intentSearch);
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
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
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
                }else{
                    intentActivity = new Intent(this,LoginActivity.class);
                    startActivity(intentActivity);
                    this.overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
                }
               break;
            case 3:
                 title = getString(R.string.title_Login);
                //fragment = new ContactUsFragment();
               // title = ContactUsFragment.TAGFragment;
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

    int backButtonCount = 0;
    @Override
    public void onBackPressed() {
        if(backButtonCount++ >= 2) {super.onBackPressed();}
        else {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
}