package info.androidhive.materialdesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.JsonModel.ModelHomeFragment;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.ImageHomeAdapter;
import info.androidhive.materialdesign.json_url.UrlJsonLink;

/**
 * Created by Ravi on 29/07/15.
 */
public class SearchResultActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView headText;
    private  ListView gridviewMostView;
    private   RequestQueue mRequestQueue;
    ImageHomeAdapter mAdapter;
    public static final String REQUEST_TAG = "SearchResultActivity";
    ArrayList<ModelHomeFragment> records;
    private String KeyWordSearch;
    public SearchResultActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        mRequestQueue = Volley.newRequestQueue((this));
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        headText = (TextView) findViewById(R.id.toolbar_title);
        headText.setText(R.string.button_search);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState==null)
            Log.d("savedInstanceState","Null");
        Intent getIntentSearch = this.getIntent();
        KeyWordSearch = getIntentSearch.getStringExtra("search_data");
        HomJson();

        mAdapter = new ImageHomeAdapter(this);
        //  Log.e("Adapter", ""+records.size());
        gridviewMostView = (ListView)findViewById(R.id.listViewHome);
        // set adapter grideview
        gridviewMostView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    private List<ModelHomeFragment> parse(JSONArray json) throws JSONException {
        records = new ArrayList<ModelHomeFragment>();
        String carFob;
        //JSONArray jsonImages = json.getJSONArray("car_list");
        for(int i =0; i < json.length(); i++) {
            JSONObject jsonImage = json.getJSONObject(i);
            String title = jsonImage.getString("car_make")+" "+jsonImage.getString("car_model")+" "+jsonImage.getString("car_year_start");
            String car_year = jsonImage.getString("car_year");
            String ImageUrl = jsonImage.getString("image_name");
            String car_stock_no = "No: "+jsonImage.getString("car_stock_no");
            if (jsonImage.getString("car_fob_cost").equals("0")){
                carFob = "FOB: Ask For Price";
            }else{
                int numberPrice = Integer.parseInt(jsonImage.getString("car_fob_cost").toString());
                DecimalFormat dfmal = new DecimalFormat("#,###");
                String resultPrice = dfmal.format(numberPrice);
                carFob = "FOB: "+resultPrice+" "+jsonImage.getString("car_fob_currency");
            }
            String IndexID = jsonImage.getString("idx");
            String CtityCarCountry = jsonImage.getString("country") + " " + jsonImage.getString("car_city");
            Log.e("JsonImage",ImageUrl);
            String StatusNew = jsonImage.getString("created_status");
            String StatusReserved = jsonImage.getString("icon_status");

            Log.e("JsonImage",ImageUrl);
            ModelHomeFragment record = new ModelHomeFragment(
                    title,car_year,ImageUrl,car_stock_no,
                    carFob,IndexID,CtityCarCountry,StatusNew,StatusReserved);
            records.add(record);
        }
        return records;
    }

    public void HomJson() {
        JsonArrayRequest mJsonArrayDetailgallery = new JsonArrayRequest(UrlJsonLink.URL_SEARCH+KeyWordSearch,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("JsonAray Respone", response.toString());
                            try {
                                List<ModelHomeFragment> imageRecords = parse(response);
                                mAdapter.swapImageRecords(imageRecords);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Json can not respone", "Error: " + error.getMessage());
            }
        });

// add it to the RequestQueue
        mRequestQueue.add(mJsonArrayDetailgallery);

    }


}
