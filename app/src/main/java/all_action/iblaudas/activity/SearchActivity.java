package all_action.iblaudas.activity;

/**
 * Created by Ravi on 29/07/15.
 */
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.google.android.gcm.GCMRegistrar;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import all_action.iblaudas.CheckInternet.ConnectionDetector;
import all_action.iblaudas.JsonModel.ModelHomeFragment;
import all_action.iblaudas.R;
import all_action.iblaudas.adapter.ImageHomeAdapter;
import all_action.iblaudas.function_api.JSON_buffer;
import all_action.iblaudas.function_api.UserFunctions;
import all_action.iblaudas.json_url.UrlJsonLink;
import all_action.iblaudas.model.getCC;
import all_action.iblaudas.model.m_country;
import all_action.iblaudas.model.m_make;
import all_action.iblaudas.model.m_model;

import static com.google.android.gms.internal.zzhl.runOnUiThread;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnSearch;
    DatabaseHandler mydb;
    private Toolbar mToolbar;
    private TextView headText;

    ArrayList<HashMap<String, String>> arraylistCountry;
    List<String> listist;
    List<String> listmodel;

        //===========var get country=============

    Spinner spinner_make;
    List<String> listNameMake;
    private String chass_notxt;
    private EditText minPrice,nzn_price,nin_year,nax_year,txt_searchchassno;
    private String getCountry,makelist,eModel,eminprice,emaxpric,eninyear,emaxyea,Chashno;
    @SuppressLint({"NewApi", "CutPasteId", "ResourceAsColor"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_search);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        headText = (TextView) findViewById(R.id.toolbar_title);
        headText.setText(R.string.action_search);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        minPrice = (EditText) findViewById(R.id.sp_nin_price);
        nzn_price = (EditText) findViewById(R.id.sp_nan_price);
        nin_year = (EditText) findViewById(R.id.sp_nin_year);
        nax_year = (EditText) findViewById(R.id.sp_nan_year);
        txt_searchchassno = (EditText) findViewById(R.id.offerNo_txt);
        btnSearch = (Button)findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);

//**************get Contructor Sqlite Db*****************888
        mydb = new DatabaseHandler(this);
        new DownloadJSON().execute();
        new getModel().execute();

    }


    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                arraylistCountry = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> listCounty;
                listist = new ArrayList<String>();
                listist.add("Location");

                List<m_country> contactLocation = mydb.getLocation();
                if (contactLocation!=null) {
                    for (m_country cn : contactLocation) {
                        String CarCountry = cn.getCountry();
                        if (CarCountry.equals("null") || CarCountry.equals(" ")) {
                            listist.remove(CarCountry);
                        } else {
                            listist.add(CarCountry);
                        }
                    }
                }
                listNameMake=new ArrayList<String>();
                List<m_make> contactsCars = mydb.getMAke();
                if (contactsCars!=null) {
                     listNameMake.add("Make");
                    for (m_make cn : contactsCars) {
                        listNameMake.add(cn.getMake());
                        Log.e("CountryData", "" + cn.getMake());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute (Void args) {
            createSpinnerDropDown();
        }

    }

    private String selected_country = null;
    private String selected_make = null;
    private String selected_model = null;
    private String selected_nin_price;
    private String selected_nan_price;
    private String selected_nin_year;
    private String selected_nan_year;

    private void createSpinnerDropDown() {
        //get reference to the spinner from the XML layout
        Spinner spinner_country = (Spinner) findViewById(R.id.sp_location);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_country.setAdapter(dataAdapter);
        spinner_make = (Spinner) findViewById(R.id.sp_make);

        ArrayAdapter<String> dataAdapterMake = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listNameMake);
        dataAdapterMake.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_make.setAdapter(dataAdapterMake);

        spinner_country.setOnItemSelectedListener(new MyOnItemSelectedListener());

        spinner_make.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                String result = spinner_make.getSelectedItem().toString();
                try {
                    String makedata = URLEncoder.encode(result, "UTF-8");

                    if (result != "Make") {
                        selected_make = makedata;

                    } else {
                        selected_make = "";
                    }

                   // model_url = ServerURL + "/?c=json&m=getModel&car_make=" + selected_make;
                    new getModel().execute();
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

    public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            String selectedItem = parent.getItemAtPosition(pos).toString();
            //check which spinner triggered the listener
            switch (parent.getId()) {
                //country spinner
                case R.id.sp_location:
                    //make sure the country was already selected during the onCreate
                    if (selectedItem != "Location") {
                        selected_country = selectedItem;
                        // Toast.makeText(getApplicationContext(), selectedItem, 100).show();
                    } else {
                        selected_country = "";
                        // Toast.makeText(getApplicationContext(), selected_country, 100).show();
                    }
                    break;
//

            }
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing.
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_search:
                try {
                    selected_nin_price = minPrice.getText().toString();
                    selected_nan_price = nzn_price.getText().toString();
                    selected_nin_year  = nin_year.getText().toString();
                    selected_nan_year  = nax_year.getText().toString();
                    chass_notxt        = txt_searchchassno.getText().toString();
                    Intent intentActivity = new Intent(this, SearchResultActivity.class);
                    intentActivity.putExtra("COUNTRY",selected_country);
                    intentActivity.putExtra("CAR_MAKE",selected_make);
                    intentActivity.putExtra("CAR_MODEL",selected_model);
                    intentActivity.putExtra("MIN_PRICE",selected_nin_price);
                    intentActivity.putExtra("MAX_PRICE",selected_nan_price);
                    intentActivity.putExtra("MIN_YEAR",selected_nin_year);
                    intentActivity.putExtra("MAX_YEAR",selected_nan_year);
                    intentActivity.putExtra("CAR_CHASNO",chass_notxt);

                    startActivity(intentActivity);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    Log.e("dataSearch", "getCountry=" + getCountry + "makelist=" + makelist + "eModel=" + eModel +
                            "eminprice=" + eminprice + "emaxpric=" + emaxpric + "eninyear=" + eninyear + "emaxyea=" + emaxyea + "Chashno=" + Chashno);
                    //Toast.makeText(getApplicationContext(), "HelloSearch", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Log.e("Erro", ""+e.getMessage());
                }
                break;
        }
    }


    private class getModel extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            try {
                HashMap<String, String> listModel;
                listmodel = new ArrayList<String>();
                listmodel.add("Model");
                List<m_model> contactsCarsModel =    mydb.getModel(selected_make);
                for (m_model cn : contactsCarsModel) {
                    listModel = new HashMap<String, String>();
                    String CarModelKey = cn.getModel();
                    if (CarModelKey.equals("null") || CarModelKey.equals(" ")) {
                        listmodel.remove(CarModelKey);
                    } else {
                        //Log.e("counry=:", ""+listCounty);
                        listmodel.add(CarModelKey);
                    }
                    Log.e("country_Name=:", "" + listmodel);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void args) {
            final Spinner spinner_model = (Spinner) findViewById(R.id.sp_model);

            ArrayAdapter<String> dataAdapterModel = new ArrayAdapter<String>(SearchActivity.this,
                    android.R.layout.simple_spinner_item, listmodel);
            dataAdapterModel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_model.setAdapter(dataAdapterModel);
            spinner_model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                    String modelsp = spinner_model.getSelectedItem().toString();
                    if (modelsp != "Model") {
                        selected_model = modelsp;
                    } else {
                        selected_model = "";
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
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
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }
}

