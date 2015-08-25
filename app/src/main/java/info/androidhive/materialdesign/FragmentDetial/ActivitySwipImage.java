package info.androidhive.materialdesign.FragmentDetial;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import info.androidhive.materialdesign.JsonModel.ModelHomeFragment;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.DatabaseHandler;
import info.androidhive.materialdesign.adapter.ViewPagerAdapter;

/**
 * Created by softbloom on 6/5/2015.
 */
public class ActivitySwipImage extends AppCompatActivity {
    DatabaseHandler mydb;
    ViewPager mViewPager;
    ArrayList<ModelHomeFragment> myArrayList = new ArrayList<ModelHomeFragment>();
    private ArrayList<HashMap<String, String>> arrayItems = new ArrayList<HashMap<String, String>>();
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle s){
        super.onCreate(s);
        mydb = new DatabaseHandler(this);
        setContentView(R.layout.layout_swipe);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences CheckCarID = this.getSharedPreferences("CheckCarID", Context.MODE_PRIVATE);
        String IndexID = CheckCarID.getString("indexID", null);
        Log.e("IndexID", "" + IndexID);
        HashMap<String,String> listImage;
        List<ModelHomeFragment> contactsCarPhoto = mydb.getGalleryPhoto(IndexID);
        for (ModelHomeFragment cnP : contactsCarPhoto){
            listImage = new HashMap<String, String>();
            listImage.put("photo_url",cnP.getPhotoUrl());
            Log.e("Photo232",cnP.getPhotoUrl());
            arrayItems.add(listImage);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.ImageswipePager);
        // Pass results to ViewPagerAdapter Class
        ViewPagerAdapter adaptera = new ViewPagerAdapter(ActivitySwipImage.this,arrayItems);
        viewPager.setAdapter(adaptera);


    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
                onBackPressed();
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
