package info.androidhive.materialdesign.FragmentDetial;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.AppController.AppController;
import info.androidhive.materialdesign.JsonModel.ModelCarGet;
import info.androidhive.materialdesign.JsonModel.ModelHomeFragment;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.DatabaseHandler;
import info.androidhive.materialdesign.activity.SearchResultActivity;
import info.androidhive.materialdesign.adapter.ImageHomeAdapter;
import info.androidhive.materialdesign.json_url.SchemaFieldTable;
import info.androidhive.materialdesign.json_url.UrlJsonLink;

public class FragmentDetailCar extends AppCompatActivity {
    private TextView headText;
    private Toolbar mToolbar;
    static String TAB_A = "Details";
    static String TAB_B = "Galleries";
    TabHost mTabHost;
    FragmentChildDetail fragmentDetailTab;
    FragmentChildDetailGallery fragmentGalleryTab;
    private ImageLoader mImageLoader;

    private RequestQueue mRequestQueue;
    ProgressDialog dialogprocess;
    String filename = "ShareDataDetail";
    private  SharedPreferences detailDataShare;
    SharedPreferences.Editor editorImage;
    String url;
    DatabaseHandler mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        mydb=new DatabaseHandler(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        headText=(TextView)findViewById(R.id.toolbar_title);
        headText.setText(R.string.header_detail);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //=====Set Tab ===
        fragmentDetailTab = new FragmentChildDetail();
        fragmentGalleryTab = new FragmentChildDetailGallery();
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setOnTabChangedListener(listener);
        mTabHost.setup();
        initializeTab();
        mRequestQueue = Volley.newRequestQueue(this);

        mImageLoader = AppController.getInstance().getImageLoader();
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.frameLayout2);
        getLayoutInflater().inflate(R.layout.banner_detail, frameLayout);
        detailDataShare = this.getSharedPreferences(filename, 0);


        if (savedInstanceState==null) {
            SharedPreferences CheckCarID = this.getSharedPreferences("CheckCarID", Context.MODE_PRIVATE);
            String IndexID = CheckCarID.getString("indexID", null);
                Log.e("IndexID",""+IndexID);
            //*******array thumb image for gallery***************
            ArrayList<String> myArrayList = new ArrayList<String>();
            //************ShareReference Image Data*************
            SharedPreferences gallerData = getSharedPreferences("GalleryImage", Context.MODE_PRIVATE);
            editorImage = gallerData.edit();
            List<ModelHomeFragment> contactsCarPhoto = mydb.getGalleryPhoto(IndexID);
            int i=0;
            for (ModelHomeFragment cnP : contactsCarPhoto){
                i++;
                myArrayList.add(cnP.getImageUrl());
                 if (cnP.getSortPhoto().equals("1")){
                     url=cnP.getPhotoUrl();
                     Log.e("HeaderUrl",url);
                 }else{
                     if (cnP.getSortPhoto().equals("2"))
                         url=cnP.getPhotoUrl();
                 }
                Log.e("i=", "" + i);
            }
            NetworkImageView thumbNail = (NetworkImageView)findViewById(R.id.detail_headerImg);
            Resources res = getApplication().getResources();
            thumbNail.getLayoutParams().width=res.getDimensionPixelSize(R.dimen.bannerSqizeImage);
            thumbNail.getLayoutParams().height=res.getDimensionPixelSize(R.dimen.bannerSqizeImageH);
            thumbNail.setImageUrl(url, mImageLoader);

            List<ModelCarGet> contactsCars = mydb.getAllCarDataDatil(IndexID);
            for (ModelCarGet cn : contactsCars){
                String carMaks                  = cn.getCarMake();
                String carmodel                 = cn.getCarModel();
                String carYears                 = cn.getCarYear();
                String carStatYears             = cn.getCarStatYear();
                String carTransmissionss        = cn.getCarTransmission();
                String carCountryzz             = cn.getCarCountry();
                String carCityssd               = cn.getCarCity();
                String carStockNosd             = cn.getCarStockNo();
                String carChasissNo             = cn.getCarChassNo();
                String carStatussd              = cn.getCarStatus();
                String carGrsdade               = cn.getCarGrade();
                String carCC                    = cn.getCarCCStr();
                String firstRegister            = cn.getFirstRegisterMonth();
                String carMileagesd             = cn.getCarMileage();
                String carFuelsd                = cn.getCarFuel();
                String carColorsd               = cn.getCarColor();
                String carSeatsd                = cn.getCarSeat();
                String carBodyTypesd            = cn.getCarBodyType();
                String carDriverTypesd          = cn.getCarDriverType();
                String carFobCostsd             = cn.getCarFobCost();
                String carFobCursdrent          = cn.getCarFobCurrent();

                int numberPrice = Integer.parseInt(carFobCostsd.toString());
                DecimalFormat dfmal = new DecimalFormat("#,###");
                String resultPrice = dfmal.format(numberPrice);
                //******************* set data to to text ************
                TextView txtTitlePrice = (TextView)findViewById(R.id.txt_price_car);
                TextView txtTitlePrices = (TextView)findViewById(R.id.txt_titleName);
                if (carFobCostsd.equals("0")){
                    txtTitlePrices.setText("FOB: " + "Ask For Price");
                    txtTitlePrice.setText("FOB: " + "Ask For Price");
                }else {
                    txtTitlePrices.setText("FOB: " + resultPrice + "($)");
                    txtTitlePrice.setText("FOB: " + resultPrice + "($)");
                }
                //*******************set data to shareReference************
                SharedPreferences.Editor editerShare = detailDataShare.edit();
                editerShare.putString(UrlJsonLink.ShareCarMake, carMaks);
                editerShare.putString(UrlJsonLink.ShareCarModel, carmodel);
                editerShare.putString(UrlJsonLink.ShareCarYear, carYears);
                editerShare.putString(UrlJsonLink.ShareCarYearStart, carStatYears);
                editerShare.putString(UrlJsonLink.ShareCarYearTransmission, carTransmissionss);
                editerShare.putString(UrlJsonLink.ShareCarFirstRag, firstRegister);

                editerShare.putString(UrlJsonLink.ShareCountry, carCountryzz);
                editerShare.putString(UrlJsonLink.ShareCarCity, carCityssd);
                editerShare.putString(UrlJsonLink.ShareCaStockNo, carStockNosd);
                editerShare.putString(UrlJsonLink.ShareChasnisNo, carChasissNo);
                editerShare.putString(UrlJsonLink.ShareIconStatus, carStatussd);
                editerShare.putString(UrlJsonLink.ShareCarGrand, carGrsdade);
                editerShare.putString(UrlJsonLink.ShareCarCC, carCC);
                editerShare.putString(UrlJsonLink.ShareCarMilleag, carMileagesd);
                editerShare.putString(UrlJsonLink.ShareCarFuel, carFuelsd);
                editerShare.putString(UrlJsonLink.ShareCarColor, carColorsd);
                editerShare.putString(UrlJsonLink.ShareCarSeat, carSeatsd);
                editerShare.putString(UrlJsonLink.ShareCarBodyType, carBodyTypesd);
                editerShare.putString(UrlJsonLink.ShareCarDriveType, carDriverTypesd);

                editerShare.putString(UrlJsonLink.ShareCarCost, carFobCostsd);
                editerShare.putString(UrlJsonLink.ShareCarFOB, carFobCursdrent);
                Log.d("obje",carFobCursdrent);
                editerShare.commit();
            }



        }




    }
    /*
    * Initialize the tabs and set views and identifiers for the tabs
    */
    public void initializeTab() {
        TabHost.TabSpec spec    =   mTabHost.newTabSpec(TAB_A);
        mTabHost.setCurrentTab(-3);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(android.R.id.tabcontent);
            }
        });
        spec.setIndicator(createTabView(getApplication(), R.drawable.tab_indicator_gen_text,R.drawable.tab_indicator_gen_detail, TAB_A));
        mTabHost.addTab(spec);
        spec  =   mTabHost.newTabSpec(TAB_B);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(android.R.id.tabcontent);
            }
        });
        spec.setIndicator(createTabView(getApplication(), R.drawable.tab_indicator_gen_text,R.drawable.tab_indicator_gen_detail, TAB_B));
        mTabHost.addTab(spec);
    }
    /*
     * TabChangeListener for changing the tab when one of the tabs is pressed
     */
    TabHost.OnTabChangeListener listener    =   new TabHost.OnTabChangeListener() {
        public void onTabChanged(String tabId) {
            /*Set current tab..*/
            if(tabId.equals(TAB_A)){
                pushFragments(tabId, fragmentDetailTab);
            }else if(tabId.equals(TAB_B)){
                pushFragments(tabId, fragmentGalleryTab);
            }
        }
    };
    /*
     * adds the fragment to the FrameLayout
     */
    public void pushFragments(String tag, Fragment fragment){
        FragmentManager manager         =   getSupportFragmentManager();
        FragmentTransaction ft            =   manager.beginTransaction();

        ft.replace(android.R.id.tabcontent, fragment);
        ft.commit();
    }

    /*
     * returns the tab view i.e. the tab icon and text
     */
    private static View createTabView(final Context context,int colorid, int bgimg, final String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_item_detail, null);
        view.setBackgroundResource(bgimg);
        TextView tv = (TextView) view.findViewById(R.id.txt_tabtxts);
        tv.setText(text);
        tv.setTextColor(colorid);
        return view;
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
            editorImage.clear();
    }
    //***************Sqlite*********************
    public void getDetail(String IndIDCar){
        dialogprocess = new ProgressDialog(FragmentDetailCar.this);
        dialogprocess.setMessage("Please wait");
        dialogprocess.setCancelable(false);
        dialogprocess.show();
        // prepare the Request as JSON Detail data array
        final JsonArrayRequest[] mJsonArrayDetail = {new JsonArrayRequest(UrlJsonLink.URL_DETAIL_LINKE + IndIDCar,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("JsonAray Respone", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                int numberPrice = Integer.parseInt(obj.getString("car_fob_cost").toString());
                                DecimalFormat dfmal = new DecimalFormat("#,###");
                                String resultPrice = dfmal.format(numberPrice);
                                //******************* set data to to text ************
                                TextView txtTitlePrice = (TextView)findViewById(R.id.txt_price_car);
                                TextView txtTitlePrices = (TextView)findViewById(R.id.txt_titleName);
                                if (obj.getString("car_fob_cost").equals("0")){
                                    txtTitlePrices.setText("FOB: " + "Ask For Price");
                                    txtTitlePrice.setText("FOB: " + "Ask For Price");
                                }else {
                                    txtTitlePrices.setText("FOB: " + resultPrice + "($)");
                                    txtTitlePrice.setText("FOB: " + resultPrice + "($)");
                                }
                                TextView textViewTitle = (TextView) findViewById(R.id.txt_title_header);
                                textViewTitle.setText(obj.getString("car_make") + " " + obj.getString("car_model") + " " + obj.getString("car_year_start"));

                                TextView textViewLocation = (TextView) findViewById(R.id.txt_Location);
                                textViewLocation.setText(obj.getString("country"));

                                TextView textViewCity = (TextView) findViewById(R.id.txt_City);
                                textViewCity.setText(obj.getString("car_city"));

                                TextView textViewOfferNo = (TextView) findViewById(R.id.txt_offerNo);
                                textViewOfferNo.setText("NO." + obj.getString("car_stock_no"));

                                TextView textViewChasNo = (TextView) findViewById(R.id.txt_chassNo);
                                textViewChasNo.setText(obj.getString("car_chassis_no"));

                                TextView textViewCarStatus = (TextView) findViewById(R.id.txt_carStatus);
                                textViewCarStatus.setText(obj.getString("icon_status"));

                                TextView textViewMake = (TextView) findViewById(R.id.txt_Make);
                                textViewMake.setText(obj.getString("car_make"));

                                TextView textViewModel = (TextView) findViewById(R.id.txt_Model);
                                textViewModel.setText(obj.getString("car_model"));

                                TextView textViewGrand = (TextView) findViewById(R.id.txt_Grand);
                                textViewGrand.setText(obj.getString("car_grade"));

                                TextView textViewModelYear = (TextView) findViewById(R.id.txt_ModelYear);
                                textViewModelYear.setText(obj.getString("car_year_start")+" YEAR");

                                TextView textViewanuFacture = (TextView) findViewById(R.id.txt_ManuFacture);
                                //textViewanuFacture.setText(obj.getString("country"));

                                TextView textViewirstRag = (TextView) findViewById(R.id.txt_FirstRag);
                                if (!obj.getString("first_registration_month").equals("0")) {
                                    textViewirstRag.setText(obj.getString("first_registration_month") + "(MONTH)/" + obj.getString("car_year_start") + "(YEAR)");
                                }else{
                                    textViewirstRag.setText("");
                                }
                                TextView textViewTransmission = (TextView) findViewById(R.id.TranSmisstionCar);
                                textViewTransmission.setText(obj.getString("car_transmission"));
                                Log.d("data", "" + obj.getString("car_cc"));

                                TextView textViewEnginSize = (TextView) findViewById(R.id.EnGinSize);
                                if (!obj.getString("car_cc").equals("0")) {
                                    textViewEnginSize.setText(obj.getString("car_cc"));
                                }else{
                                    textViewEnginSize.setText("");
                                }

                                TextView textViewMileage = (TextView) findViewById(R.id.txt_Mileage);
                                if (!obj.getString("car_mileage").equals("0")){
                                    int numberMoney = Integer.parseInt(obj.getString("car_mileage").toString());
                                    DecimalFormat df = new DecimalFormat("##,###");
                                    String result = df.format(numberMoney);
                                 textViewMileage.setText(result + "KM");
                                    }else{
                                    textViewMileage.setText("");
                                    }
                                TextView textViewFuel = (TextView) findViewById(R.id.txt_Fuel);
                                textViewFuel.setText(obj.getString("car_fuel"));

                                TextView textViewColor = (TextView) findViewById(R.id.txt_Color);
                                textViewColor.setText(obj.getString("car_color"));

                                TextView textViewCarSeat = (TextView) findViewById(R.id.txt_CarSeat);
                                textViewCarSeat.setText(obj.getString("car_seat"));

                                TextView textViewodyType = (TextView) findViewById(R.id.txt_BodyType);
                                textViewodyType.setText(obj.getString("car_body_type"));

                                TextView textViewDriverType = (TextView) findViewById(R.id.txt_DriverType);
                                textViewDriverType.setText(obj.getString("car_drive_type"));

                                TextView textViewFOBLocation = (TextView) findViewById(R.id.txt_FOB);
                                if (obj.getString("car_fob_cost").equals("0")){
                                    textViewFOBLocation.setText("Ask For Price");
                                }else {
                                    textViewFOBLocation.setText(resultPrice + obj.getString("car_fob_currency") + "($)");
                                }
                                //*******************set data to shareReference************
                                SharedPreferences.Editor editerShare = detailDataShare.edit();
                                editerShare.putString(UrlJsonLink.ShareCarModel, obj.getString("car_model"));
                                editerShare.putString(UrlJsonLink.ShareCarMake, obj.getString("car_make"));
                                editerShare.putString(UrlJsonLink.ShareCarFirstRag, obj.getString("first_registration_month"));
                                editerShare.putString(UrlJsonLink.ShareCarYear, obj.getString("car_year"));
                                editerShare.putString(UrlJsonLink.ShareCarYearStart, obj.getString("car_year_start"));
                                editerShare.putString(UrlJsonLink.ShareCarYearTransmission, obj.getString("car_transmission"));
                                editerShare.putString(UrlJsonLink.ShareCountry, obj.getString("country"));
                                editerShare.putString(UrlJsonLink.ShareCarCity, obj.getString("car_city"));
                                editerShare.putString(UrlJsonLink.ShareCaStockNo, obj.getString("car_stock_no"));
                                editerShare.putString(UrlJsonLink.ShareChasnisNo, obj.getString("car_chassis_no"));
                                editerShare.putString(UrlJsonLink.ShareIconStatus, obj.getString("icon_status"));
                                editerShare.putString(UrlJsonLink.ShareCarGrand, obj.getString("car_grade"));
                                editerShare.putString(UrlJsonLink.ShareCarCC, obj.getString("car_cc"));
                                editerShare.putString(UrlJsonLink.ShareCarMilleag, obj.getString("car_mileage"));
                                editerShare.putString(UrlJsonLink.ShareCarFuel, obj.getString("car_fuel"));
                                editerShare.putString(UrlJsonLink.ShareCarColor, obj.getString("car_color"));
                                editerShare.putString(UrlJsonLink.ShareCarSeat, obj.getString("car_seat"));
                                editerShare.putString(UrlJsonLink.ShareCarBodyType, obj.getString("car_body_type"));
                                editerShare.putString(UrlJsonLink.ShareCarDriveType, obj.getString("car_drive_type"));
                                editerShare.putString(UrlJsonLink.ShareCarCost, obj.getString("car_fob_cost"));
                                editerShare.putString(UrlJsonLink.ShareCarFOB, obj.getString("car_fob_currency"));
                                editerShare.putString(UrlJsonLink.ShareCarBuyingPrin, obj.getString("car_buying_price"));
                                editerShare.putString(UrlJsonLink.ShareCarBuyingCurr, obj.getString("car_buying_currency"));
                                    Log.d("obje",obj.getString("car_fob_cost"));
                                editerShare.commit();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Json can not respone", "Error: " + error.getMessage());
            }
        })};
        JsonArrayRequest mJsonArrayDetailgallery = new JsonArrayRequest(UrlJsonLink.URL_PHOTO_GALLERY+IndIDCar,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("JsonAray Respone", response.toString());
                        //*******array photo image for swip***************
                        ArrayList<String> myArrayListSwip = new ArrayList<String>();

                        //*******array thumb image for gallery***************
                        ArrayList<String> myArrayList = new ArrayList<String>();

                        //************ShareReference Image Data*************
                        SharedPreferences gallerData = getSharedPreferences("GalleryImage",0);
                        int myNum = 0;            // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                SharedPreferences.Editor editorImage = gallerData.edit();
                                JSONObject obj = response.getJSONObject(i);
                                myArrayList.add(obj.getString("image_name"));
                                myArrayListSwip.add(obj.getString("full_image_url"));
                                editorImage.putInt("Arraysize", myArrayList.size());
                                editorImage.putString("ImageThumb" + i, myArrayList.get(i));
                                editorImage.putString("ImagePhoto" + i, myArrayListSwip.get(i));
                                editorImage.commit();
                                if (i==0) {
                                    url = obj.getString("full_image_url");
                                   }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            NetworkImageView thumbNail = (NetworkImageView)findViewById(R.id.detail_headerImg);

                            Resources res = getApplication().getResources();
                            thumbNail.getLayoutParams().width=res.getDimensionPixelSize(R.dimen.bannerSqizeImage);
                            thumbNail.getLayoutParams().height=res.getDimensionPixelSize(R.dimen.bannerSqizeImageH);
                            thumbNail.setImageUrl(url, mImageLoader);
                        }
                        dialogprocess.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Json can not respone", "Error: " + error.getMessage());
            }
        });

// add it to the RequestQueue
        mRequestQueue.add(mJsonArrayDetail[0]);
        mRequestQueue.add(mJsonArrayDetailgallery);
    }


}
