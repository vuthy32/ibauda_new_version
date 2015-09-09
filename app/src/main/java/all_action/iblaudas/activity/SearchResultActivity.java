package all_action.iblaudas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
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
import java.util.IllegalFormatCodePointException;
import java.util.List;

import all_action.iblaudas.JsonModel.ModelHomeFragment;
import all_action.iblaudas.R;
import all_action.iblaudas.adapter.ImageHomeAdapter;
import all_action.iblaudas.adapter.ImageHomeAdapterSqlite;
import all_action.iblaudas.adapter.MyOrderAdapter;
import all_action.iblaudas.json_url.UrlJsonLink;

/**
 * Created by Ravi on 29/07/15.
 */
public class SearchResultActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView headText;
    private  ListView gridviewMostView;
    private   RequestQueue mRequestQueue;
    ImageHomeAdapterSqlite mAdapterSqlite;
    DatabaseHandler mydb;
    public static final String REQUEST_TAG = "SearchResultActivity";
    private List<ModelHomeFragment> ArarryTestMake = new ArrayList<ModelHomeFragment>();
    //***************************************************************

   private String KeyCountry,KeyMake,KeyModel,KeyMinPrice,KeyMaxPrice,KeyMinYear,KeyMaxYear,KeyChashno;
    FrameLayout notresult;
    //**************Var String*************************
    private   String carFob;

    public SearchResultActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        mydb = new DatabaseHandler(this);
        mRequestQueue = Volley.newRequestQueue((this));
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        headText = (TextView) findViewById(R.id.toolbar_title);
        headText.setText(R.string.button_search);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       notresult = (FrameLayout)findViewById(R.id.frmsearch);
//        getLayoutInflater().inflate(R.layout.listview_layout, notresult);
        if (savedInstanceState==null)


            Log.d("savedInstanceState", "Null");
        Intent getIntentSearch = this.getIntent();
        KeyCountry = getIntentSearch.getStringExtra("COUNTRY");
        KeyMake = getIntentSearch.getStringExtra("CAR_MAKE");
        KeyModel = getIntentSearch.getStringExtra("CAR_MODEL");
        KeyMinPrice = getIntentSearch.getStringExtra("MIN_PRICE");
        KeyMaxPrice = getIntentSearch.getStringExtra("MAX_PRICE");
        KeyMinYear = getIntentSearch.getStringExtra("MIN_YEAR");
        KeyMaxYear = getIntentSearch.getStringExtra("MAX_YEAR");
        KeyChashno = getIntentSearch.getStringExtra("CAR_CHASNO");

        Log.e("dataSearch", "getCountry=" + KeyCountry + "makelist=" + KeyMake + "eModel=" + KeyModel +
                "eminprice=" + KeyMinPrice + "emaxpric=" + KeyMaxPrice + "eninyear=" + KeyMinYear + "emaxyea=" + KeyMaxYear + "Chashno=" + KeyChashno);
        List<ModelHomeFragment> contactsCars = mydb.get_tag_Data(KeyCountry,KeyMake,KeyModel,KeyMinPrice,KeyMaxPrice,KeyMinYear,KeyMaxYear,KeyChashno);
        for (ModelHomeFragment cn : contactsCars){
            ModelHomeFragment PhotoCar = new ModelHomeFragment();
            PhotoCar.setImageUrl(cn.getImageUrl());

            PhotoCar.setCarNo("NO: " + cn.getCarNo());

            PhotoCar.setTitle(cn.getTitle());

            PhotoCar.setIdexID(cn.getIdexID());

            PhotoCar.setCityCar(cn.getCityCar());

            if (cn.getCarFob().equals("0") ||cn.getCarFob().equals("")){
                carFob = "FOB: Ask For Price";
            }else{
                int numberPrice = Integer.parseInt(cn.getCarFob().toString());
                DecimalFormat dfmal = new DecimalFormat("#,###");
                String resultPrice = dfmal.format(numberPrice);
                carFob = "FOB: "+resultPrice+""+cn.getCarFobCurrency();
            }
            PhotoCar.setCarFob(carFob);

            PhotoCar.setStatusNew(cn.getStatusNew());
            PhotoCar.setStatusReserved(cn.getStatusReserved());
            ArarryTestMake.add(PhotoCar);
        }
        if(ArarryTestMake.size()==0){
            getLayoutInflater().inflate(R.layout.no_resault, notresult);
            Log.e("Six",""+ArarryTestMake.size());
        }else {
            Log.e("Six", "" + ArarryTestMake.size());
            getLayoutInflater().inflate(R.layout.listview_search, notresult);
            mAdapterSqlite = new ImageHomeAdapterSqlite(SearchResultActivity.this, ArarryTestMake);
//        //  Log.e("Adapter", ""+records.size());
            gridviewMostView = (ListView) findViewById(R.id.listViewHome);
            // set adapter grideview
            gridviewMostView.setAdapter(mAdapterSqlite);
        }





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
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        this.finish();
    }
//    private List<ModelHomeFragment> parse(JSONArray json) throws JSONException {
//        records = new ArrayList<ModelHomeFragment>();
//        String carFob;
//        //JSONArray jsonImages = json.getJSONArray("car_list");
//        for(int i =0; i < json.length(); i++) {
//            JSONObject jsonImage = json.getJSONObject(i);
//            String title = jsonImage.getString("car_make")+" "+jsonImage.getString("car_model")+" "+jsonImage.getString("car_year_start");
//            String car_year = jsonImage.getString("car_year");
//            String ImageUrl = jsonImage.getString("image_name");
//            String car_stock_no = "No: "+jsonImage.getString("car_stock_no");
//            if (jsonImage.getString("car_fob_cost").equals("0")){
//                carFob = "FOB: Ask For Price";
//            }else{
//                int numberPrice = Integer.parseInt(jsonImage.getString("car_fob_cost").toString());
//                DecimalFormat dfmal = new DecimalFormat("#,###");
//                String resultPrice = dfmal.format(numberPrice);
//                carFob = "FOB: "+resultPrice+" "+jsonImage.getString("car_fob_currency");
//            }
//            String IndexID = jsonImage.getString("idx");
//            String CtityCarCountry = jsonImage.getString("country") + " " + jsonImage.getString("car_city");
//            Log.e("JsonImage",ImageUrl);
//            String StatusNew = jsonImage.getString("created_status");
//            String StatusReserved = jsonImage.getString("icon_status");
//
//            Log.e("JsonImage",ImageUrl);
//            ModelHomeFragment record = new ModelHomeFragment(
//                    title,car_year,ImageUrl,car_stock_no,
//                    carFob,IndexID,CtityCarCountry,StatusNew,StatusReserved);
//            records.add(record);
//        }
//        return records;
//    }
//
//    public void HomJson() {
//        JsonArrayRequest mJsonArrayDetailgallery = new JsonArrayRequest(UrlJsonLink.URL_SEARCH+KeyWordSearch,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Log.d("JsonAray Respone", response.toString());
//                            try {
//                                List<ModelHomeFragment> imageRecords = parse(response);
//                             //   mAdapter.swapImageRecords(imageRecords);
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d("Json can not respone", "Error: " + error.getMessage());
//            }
//        });
//
//// add it to the RequestQueue
//        mRequestQueue.add(mJsonArrayDetailgallery);
//
//    }


}
