package info.androidhive.materialdesign.FragmentDetial;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.DatabaseHandler;

/**
 * Created by softbloom on 6/5/2015.
 */
public class ActivitySwipImage extends AppCompatActivity {
    DatabaseHandler db;
    ViewPager mViewPager;
    private ArrayList<HashMap<String, String>> arrayItems = new ArrayList<HashMap<String, String>>();
    JSONArray GalerTCarsPhoto=null;
    String CarID;
    TextView txtDone;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle s){
        super.onCreate(s);
        db = new DatabaseHandler(this);
        setContentView(R.layout.layout_swipe);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
//		headText=(TextView)findViewById(R.id.toolbar_title);
//		headText.setText("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onBackPressed() {
        this.finish();
        return;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActivitySwipImage.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
		{
            onBackPressed();
		}
		return super.onKeyDown(keyCode, event);
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.e("ActivituyAzp","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("ActivituyAzp", "onDestroy");
    }
}
