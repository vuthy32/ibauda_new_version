package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.FragmentDetial.FragmentDetailCar;
import info.androidhive.materialdesign.JsonModel.OrderModel;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.MyOrderAdapter;
import info.androidhive.materialdesign.function_api.UserFunctions;

public class OrderCarListUser extends AppCompatActivity {
	private static final String DEFAULT ="";
	private SharedPreferences user;
    private String member_id,member_no,remember_token,IndeXID;
    private int getPositionID;

	private Toolbar mToolbar;
	private TextView headText;

    List<OrderModel> listOrder;

    private ListView orderlistview;
    private MyOrderAdapter myOrderAdapter;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylistcarorder_layout);
		mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
		headText = (TextView) findViewById(R.id.toolbar_title);
		headText.setText(R.string.headerMycar);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FrameLayout notresult = (FrameLayout)findViewById(R.id.order_frame);
        getLayoutInflater().inflate(R.layout.listview_layout, notresult);

		new MyCarOrder().execute();
	
		
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

    private class MyCarOrder extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog nDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
			nDialog = new ProgressDialog(OrderCarListUser.this);
			nDialog.setMessage("Loading..");
			//nDialog.setTitle("Please wait...");
			nDialog.setIndeterminate(false);
			nDialog.setCancelable(false);
			nDialog.show();
            SharedPreferences user = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
    		member_no = user.getString("member_no", DEFAULT);
    		remember_token = user.getString("remember_token", DEFAULT);
 
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.ReservedCar(member_no, remember_token);
            return json;
        }
        protected void onPostExecute(JSONObject json) {
            Log.e("image_name", "" + json.toString());
            try {
                if (json.getString("success").equals("1")){}

            try {

                listOrder = new ArrayList<OrderModel>();
				JSONArray response = json.getJSONArray("cars");
				for (int i = 0; i < response.length(); i++) {
					try {
                       OrderModel OrderList = new OrderModel();
						JSONObject obj = response.getJSONObject(i);

                        OrderList.setInDexID(obj.getString("idx"));
                        OrderList.setThumbImage(obj.getString("image_name"));
                        OrderList.setCountCity(obj.getString("country") + "," + obj.getString("car_city"));

                        OrderList.setCarNoStr("NO:" + obj.getString("car_stock_no"));
                        if (obj.getString("car_fob_cost").equals("0")){
                            OrderList.setCarPrice("FOB: ASK");
                        }else {
                            int numberPrice = Integer.parseInt(obj.getString("car_fob_cost").toString());
                            DecimalFormat dfmal = new DecimalFormat("#,###");
                            String resultPrice = dfmal.format(numberPrice);
                            OrderList.setCarPrice("FOB: "+resultPrice + " " + obj.getString("car_fob_currency"));
                        }
                        OrderList.setTitleCar(obj.getString("car_make") + " " + obj.getString("car_model") + " " + obj.getString("car_year_start"));
						Log.e("image_name", "" + obj.getString("image_name"));
                        listOrder.add(OrderList);

					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
                nDialog.dismiss();

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        	if(listOrder.size()==0){
				FrameLayout notresult = (FrameLayout)findViewById(R.id.order_frame);
				getLayoutInflater().inflate(R.layout.no_resault, notresult);
                nDialog.dismiss();
			}
        	orderlistview = (ListView) findViewById(R.id.listView);
            myOrderAdapter = new MyOrderAdapter(OrderCarListUser.this, listOrder);
			orderlistview.setAdapter(myOrderAdapter);
            registerForContextMenu(orderlistview);
            myOrderAdapter.notifyDataSetChanged();
			orderlistview.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						final int position, long id) {
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderCarListUser.this);

		            alertDialog.setCancelable(false);
		            // Setting Dialog Message
		            alertDialog.setMessage("Are you sure you want delete or see details this?");
//***********************************Delele Order Dailog Massage***********************************
		            alertDialog.setNegativeButton("Delete",
		                    new DialogInterface.OnClickListener() {
		                        public void onClick(DialogInterface dialog, int which) {
                                    getPositionID = position;
                                    IndeXID = listOrder.get(position).getInDexID();
                                              new ProcessCancelOrder().execute();
		                                       dialog.cancel();
		                        }
		                    });
//***********************************Link to Detail Dailog Massage*********************************
		            	alertDialog.setNeutralButton("View Detail", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences CheckCarID = getSharedPreferences("CheckCarID", 0);
                                SharedPreferences.Editor userEditer=CheckCarID.edit();
                                userEditer.putString("indexID",listOrder.get(position).getInDexID());
                                userEditer.commit();
                                Intent todetail = new Intent(OrderCarListUser.this, FragmentDetailCar.class);
                                todetail.putExtra("IDX", IndeXID);
                                startActivity(todetail);
                                dialog.cancel();


                            }
                        });
//***********************************Cancel Dailog Massage******************************************
			            alertDialog.setPositiveButton("Cancel",
			                    new DialogInterface.OnClickListener() {
			                        public void onClick(DialogInterface dialog,int which) {
			                            // Write your code here to execute after dialog
			                        	dialog.cancel();
			                        }
			                    });


		            // Showing Alert Message
			        alertDialog.show();

				}
			});

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //check json respon !null

        }
//==================Cancel Order=================================

        private class ProcessCancelOrder extends AsyncTask<String, String, JSONObject> {
            private ProgressDialog nDialog;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                nDialog = new ProgressDialog(OrderCarListUser.this);
                nDialog.setMessage("Processing..");
               // nDialog.setTitle("Checking Network");
                nDialog.setIndeterminate(false);
                nDialog.setCancelable(true);
                nDialog.show();
            }
            @Override
            protected JSONObject doInBackground(String... args) {

              UserFunctions userFunction = new UserFunctions();
                JSONObject json_cancel = userFunction.CancelOrder(member_no, remember_token, IndeXID);
                return json_cancel;

            }
            
            @Override
            protected void onPostExecute(JSONObject json_cancel) {
                String result;
            	String sucessMsg;
    			try {
    				result = json_cancel.getString("success");
    				sucessMsg = json_cancel.getString("msg");
    				if(result.equals("1")){
    					Toast.makeText(getApplicationContext(), sucessMsg, Toast.LENGTH_LONG).show();
                        listOrder.remove(getPositionID);
                        myOrderAdapter.notifyDataSetChanged();
    					//Toast.makeText(getApplicationContext(), ""+getPositionID, 100).show();
    				}else if(result.equals("0")){
    					Log.e("Json_CancelOrder_sucss0=", "" + result);
    					Log.e("Json_CancelOrder_msg0=", "" + sucessMsg);
    					//pDialog.dismiss();
    					Toast.makeText(getApplicationContext(), sucessMsg, Toast.LENGTH_LONG).show();
    				}
    				
    			} catch (JSONException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}

                nDialog.dismiss();
    			
            }
            
        }

    }



}
