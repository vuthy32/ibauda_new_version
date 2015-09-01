package all_action.iblaudas.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

import all_action.iblaudas.R;
import all_action.iblaudas.function_api.JSON_buffer;
import all_action.iblaudas.function_api.UserFunctions;
import all_action.iblaudas.model.getCC;


public class Register extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView headText;
    String member_id, member_pwd, cmember_pwd, email, member_first_name, member_last_name, mobilbo1, member_country, mobile_no1, mobile_no2, mobile_no3;
    EditText txtID, txtPassword, txtConfirmpassword, txtEmail, txtFirstname, txtLastname, txtMobile, txtmobile01, txtmobile02;


    // URL to get contacts JSON
    private static String url = "http://iblauda.com/?c=json&m=getCountry";
    ArrayList<String> listCC;
    ArrayList<String> worldlist;
    ArrayList<String> defaulCountry;
    private String member_no,CodeCountry, countryCodeName,mycountry,mycountrycode,countryPhoneCode;
    private Spinner spCountry;
    TelephonyManager tm;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        headText = (TextView) findViewById(R.id.toolbar_title);
        headText.setText(R.string.register);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tm = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);

        txtID = (EditText) findViewById(R.id.txt_id);
        txtPassword = (EditText) findViewById(R.id.txt_register_pwd);
        txtConfirmpassword = (EditText) findViewById(R.id.txt_pwdComfirm);
        txtEmail = (EditText) findViewById(R.id.txt_register_emails);
        txtFirstname = (EditText) findViewById(R.id.txt_fName);
        txtLastname = (EditText) findViewById(R.id.txt_lName);
        txtMobile = (EditText) findViewById(R.id.txt_mobile1);
        txtmobile01 = (EditText) findViewById(R.id.txt_mobile2);
        //get country
        new getCountry().execute();

        Button btnRegister = (Button)findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                member_id = txtID.getText().toString();
                member_pwd = txtPassword.getText().toString();
                cmember_pwd = txtConfirmpassword.getText().toString();
                email = txtEmail.getText().toString();
                member_first_name = txtFirstname.getText().toString();
                member_last_name = txtLastname.getText().toString();
                mobile_no1 = txtMobile.getText().toString();
                mobile_no2 =  txtmobile01.getText().toString();

                //check empty field
                txtID.setError(null);
                txtPassword.setError(null);
                txtConfirmpassword.setError(null);
                txtFirstname.setError(null);
                txtLastname.setError(null);
                txtEmail.setError(null);
                txtmobile01.setError(null);
                if(member_id.equals("") ) {
                    txtID.setError("User ID is empty");
                }
                else if(member_pwd.equals("") ) {
                    txtPassword.setError("Password is empty");
                }
                else if(cmember_pwd.equals("") ) {
                    txtConfirmpassword.setError("Confirm password is empty");
                }
                else if(email.equals("") ) {
                    txtEmail.setError("Email is empty");
                }
                else if(member_first_name.equals("") ) {
                    txtFirstname.setError("First name is empty");
                }
                else if(member_last_name.equals("") ) {
                    txtLastname.setError("Last name is empty");
                }
                //check password and confirm password
                else if (!member_pwd.equals(cmember_pwd)){
                    txtPassword.setError("not match");
                    txtConfirmpassword.setError("not match");
                    Toast.makeText(Register.this, "Password and Confirm password not match !", Toast.LENGTH_SHORT).show();
                }
                else if (member_id.length() < 4){
                    txtID.setError("Username should be minimum 4 characters");
                }
                else if (member_pwd.length() < 5){
                    txtPassword.setError("Password should be minimum 5 characters");
                }else if(mobile_no2.equals("")){
                    txtmobile01.setError("Mobile2 is empty");
                }else if(mobile_no2.length()>20){
                    txtmobile01.setError("Mobile2 maximum 20 characters");
                }
                else {
                    NetAsync(v);
                }
            }
        });
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
        Register.this.finish();
        this.overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
    }

    /**
     * Async Task to check whether internet connection is working
     **/

    private class NetCheck extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(Register.this);
            nDialog.setMessage("Loading..");
            nDialog.setTitle("Checking Network");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... args){


/**
 * Gets current device state and checks for working internet connection by trying Google.
 **/
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL("http://iblauda.com");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(3000);
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {
                        return true;
                    }
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return false;

        }
        @Override
        protected void onPostExecute(Boolean th){

            if(th == true){
                nDialog.dismiss();
                new ProcessRegister().execute();
            }
            else{
                nDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Error network connection", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class ProcessRegister extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            member_id = txtID.getText().toString();
            member_pwd = txtPassword.getText().toString();
            email = txtEmail.getText().toString();
            member_first_name = txtFirstname.getText().toString();
            member_last_name = txtLastname.getText().toString();
            mobile_no1 = txtMobile.getText().toString();
            mobile_no2 =  txtmobile01.getText().toString();
            mobilbo1 = mobile_no1.substring(1,mobile_no1.length());
            pDialog = new ProgressDialog(Register.this);
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Registering ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected JSONObject doInBackground(String... args) {
            UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.registerUser(member_id, member_pwd, email, member_first_name, member_last_name, member_country, mobilbo1, mobile_no2 );
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            try {

                String res = json.getString("success");
                Log.d("key suc: ", "> " + res);
                if(res.equals("1")){
                    pDialog.setTitle("Getting Data");
                    pDialog.setMessage("Loading Info");
                    Intent registered = new Intent(getApplicationContext(), LoginActivity.class);
                    registered.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    pDialog.dismiss();
                    Toast.makeText(Register.this, "Successfully Registered ! Check your inbox for active" , Toast.LENGTH_SHORT).show();
                    startActivity(registered);
                    finish();
                }
                else if(json.getString("error_msg").equals("Member ID already existed")){
                    pDialog.dismiss();
                    txtID.setError("Member ID already existed");
                }
                else if(json.getString("error_msg").equals("Member ID must be alphanumeric and dot characters, from 4 to 20 characters long")){
                    pDialog.dismiss();
                    txtID.setError("Member ID must be alphanumeric and dot characters, from 4 to 20 characters long");
                }
                else if(json.getString("error_code").equals("4")){
                    pDialog.dismiss();
                    txtEmail.setError("Invalid Email");
                }
                else if(json.getString("error_code").equals("5")){
                    pDialog.dismiss();
                    txtFirstname.setError("First Name must be alphanumeric");
                }
                else if(json.getString("error_code").equals("6")){
                    pDialog.dismiss();
                    txtLastname.setError("Last Name must be alphanumeric");
                }else if(json.getString("error_mobile_no").equals("Mobile Number must be only numbers")){
                    pDialog.dismiss();
                    Toast.makeText(Register.this, "Mobile Number must be only numbers" , Toast.LENGTH_SHORT).show();
                }else if(json.getString("error_code").equals("42")){
                    pDialog.dismiss();
                    txtEmail.setError("Email already existed");
                }
                else{
                    pDialog.dismiss();
                    Toast.makeText(Register.this, "Error occured in registration" , Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void NetAsync(View view){
        new NetCheck().execute();
    }



    // get Country AsyncTask
    private class getCountry extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            countryCodeName = tm.getSimCountryIso();
           Log.e("dfasd",""+countryCodeName);
            final SharedPreferences mylocation = getSharedPreferences("locaction", Context.MODE_PRIVATE);
            mycountry = mylocation.getString("countryname", "");
            mycountrycode = mylocation.getString("countrycode", "");
            countryPhoneCode = mylocation.getString("countrycodeNumber","");
            worldlist = new ArrayList<String>();
            defaulCountry = new ArrayList<String>();
            listCC = new ArrayList<String>();
            try {
//                JSON_buffer data_countryDefault = new JSON_buffer();
//
//                JSONArray jsonarray = new JSONArray(data_countryDefault.getJSONUrl("http://ip-api.com/json"));
//                countryCodeName = tm.getNetworkCountryIso();
//                for (int i = 0; i < jsonarray.length(); i++) {
//                    JSONObject jsonobject = jsonarray.getJSONObject(i);
//                        Log.e("Fullname=", jsonobject.optString("country"));
//                        SharedPreferences.Editor editor = mylocation.edit();
//                        editor.putString("countryname", jsonobject.getString("country"));
//                        editor.putString("countrycode", jsonobject.getString("countryCode"));
//                        //editor.putString("countrycodeNumber", jsonobject.optString("phonecode"));
//                        editor.commit();
//
//                }




                JSON_buffer data_country = new JSON_buffer();
                getCC listAllCC;
                JSONArray jsonarraysa = new JSONArray(data_country.getJSONUrl(url));
               // worldlist.add(mycountry);
               // listCC.add(mycountrycode);
                //defaulCountry.add(countryPhoneCode);
                for (int i = 0; i < jsonarraysa.length(); i++) {
                    JSONObject jsonobject  = jsonarraysa.getJSONObject(i);
                    listAllCC = new getCC();
                    defaulCountry.add(jsonobject.optString("phonecode"));
                    worldlist.add(jsonobject.optString("country_name"));
                    listCC.add(jsonobject.optString("cc"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            spCountry = (Spinner) findViewById(R.id.sp_country);

            // Spinner adapter
            spCountry.setAdapter(new ArrayAdapter<String>(Register.this,
                    android.R.layout.simple_spinner_dropdown_item,worldlist));
            // Spinner on item click listener
            spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0,
                                           View arg1, int position, long arg3) {
                    countryCodeName = listCC.get(position);
                    member_country = listCC.get(position);

                    txtMobile = (EditText)findViewById(R.id.txt_mobile1);
                    txtMobile.setText("+"+defaulCountry.get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> position) {

                }
            });

        }

    }



}

