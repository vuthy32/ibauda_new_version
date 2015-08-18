package info.androidhive.materialdesign.activity;

/**
 * Created by Ravi on 29/07/15.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import info.androidhive.materialdesign.FragmentDetial.FragmentDetailCar;
import info.androidhive.materialdesign.JsonModel.ModelHomeFragment;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.ImageHomeAdapter;
import info.androidhive.materialdesign.json_url.UrlJsonLink;

import static com.google.android.gms.internal.zzhl.runOnUiThread;

public class HomeFragment extends Fragment{
    private  ListView gridviewMostView;
    private   RequestQueue mRequestQueue;
    ImageHomeAdapter mAdapter;
    public static final String REQUEST_TAG = "HomeFragmentRequestActivity";
    ArrayList<ModelHomeFragment> records;
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new ImageHomeAdapter(getActivity());


        //  Log.e("Adapter", ""+records.size());
        gridviewMostView = (ListView) getActivity().findViewById(R.id.listViewHome);
        // set adapter grideview
        gridviewMostView.setAdapter(mAdapter);
        if (savedInstanceState==null){
        Log.d("savedInstanceState","Null");
        }else{
            Log.d("savedInstanceState","Not Null");
        }

    }


    public void HomJson(){
        // prepare the Request as JSON
        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, UrlJsonLink.URL_HOME_MOST_VIEW, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                     // display JSON response
                        try {
                                List<ModelHomeFragment> imageRecords = parse(response);
                               if (imageRecords.size()>0){
                                   mAdapter.swapImageRecords(imageRecords);
                               }

                            }catch(JSONException e) {
                                Toast.makeText(getActivity(), "Unable to parse data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        Log.d("Response", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", error.getMessage());
            }
        });
        mJsonObjectRequest.setTag(REQUEST_TAG);
// add it to the RequestQueue
        mRequestQueue.add(mJsonObjectRequest);
    }
    private List<ModelHomeFragment> parse(JSONObject json) throws JSONException {
             records = new ArrayList<ModelHomeFragment>();
        String carFob;
            JSONArray jsonImages = json.getJSONArray("car_list");
            for(int i =0; i < jsonImages.length(); i++) {
                JSONObject jsonImage = jsonImages.getJSONObject(i);
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

    @Override
    public void onStart() {
        super.onStart();
        startParsingTask();
     //  Log.e("Adapter", "" + records.size());
    }

    @Override
    public void onStop() {
        super.onStop();
//        if (mRequestQueue != null) {
//            mRequestQueue.cancelAll(REQUEST_TAG);
//        }
    }
    //========================

    public void startParsingTask() {
        Thread threadA = new Thread() {
            public void run() {
                ThreadB threadB = new ThreadB(getActivity());
                JSONObject jsonObject = null;
                try {
                    jsonObject = threadB.execute().get(10, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
                final JSONObject receivedJSONObject = jsonObject;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Response is:",""+receivedJSONObject);
                       // mTextView.setText("Response is: " + receivedJSONObject);
                        if (receivedJSONObject != null) {
                            try {
                              // linearLayout.setVisibility(View.GONE);
                              //  getActivity().getLayoutInflater().inflate(R.layout.gride_view_layout, ContentFrameLayout);
                                receivedJSONObject.getString("car_list");
                                List<ModelHomeFragment> imageRecords = parse(receivedJSONObject);
                                mAdapter.swapImageRecords(imageRecords);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                          //  getActivity().getLayoutInflater().inflate(R.layout.loading_layout, ContentFrameLayout);

                        }
                    }
                });
            }
        };
        threadA.start();
    }

    private class ThreadB extends AsyncTask<Void, Void, JSONObject> {
        private Context mContext;
        public ThreadB(Context ctx) {
            mContext = ctx;
        }
        @Override
        protected JSONObject doInBackground(Void... params) {
            final RequestFuture<JSONObject> futureRequest = RequestFuture.newFuture();
            mRequestQueue =Volley.newRequestQueue((mContext.getApplicationContext()));
            //String url = "http://api.openweathermap.org/data/2.5/weather?q=London,uk";
            final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method
                    .GET, UrlJsonLink.URL_HOME_MOST_VIEW,
                    new JSONObject(), futureRequest, futureRequest);
            jsonRequest.setTag(REQUEST_TAG);
            mRequestQueue.add(jsonRequest);
            try {
                return futureRequest.get(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        getActivity().getMenuInflater().inflate(R.menu.menu_register, menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_register, menu);
      //  super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("HomeActivity", "Resualt");
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
