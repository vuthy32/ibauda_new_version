package info.androidhive.materialdesign.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.function_api.UserFunctions;


public class Register extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView headText;
    String member_id, member_pwd, cmember_pwd, email, member_first_name, member_last_name, mobilbo1, member_country, mobile_no1, mobile_no2, mobile_no3;
    EditText txtID, txtPassword, txtConfirmpassword, txtEmail, txtFirstname, txtLastname, txtMobile, txtmobile01, txtmobile02;

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

        txtID = (EditText) findViewById(R.id.txt_id);
        txtPassword = (EditText) findViewById(R.id.txt_register_pwd);
        txtConfirmpassword = (EditText) findViewById(R.id.txt_pwdComfirm);
        txtEmail = (EditText) findViewById(R.id.txt_register_emails);
        txtFirstname = (EditText) findViewById(R.id.txt_fName);
        txtLastname = (EditText) findViewById(R.id.txt_lName);
        txtMobile = (EditText) findViewById(R.id.txt_mobile1);
        txtmobile01 = (EditText) findViewById(R.id.txt_mobile2);
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
                    URL url = new URL("http://www.google.com");
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
            /**
             * Checks for success message.
             **/
            try {

                String res = json.getString("success");
                Bundle extras = getIntent().getExtras();
                String value_back = extras.getString("MAIN");
                String value_back1 = extras.getString("ListCarBack");
                String value_back2 = extras.getString("cardetail");

                Log.d("key suc: ", "> " + res);

                if(res.equals("1")){
                    pDialog.setTitle("Getting Data");
                    pDialog.setMessage("Loading Info");
//                    Intent registered = new Intent(getApplicationContext(), Login.class);
//
//                    /**
//                     * Close all views before launching Registered screen
//                     **/
//                    //registered.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    pDialog.dismiss();
//                    Toast.makeText(Register.this, "Successfully Registered ! Check your inbox for active" , Toast.LENGTH_SHORT).show();
//                    registered.putExtra("MAIN",value_back);
//                    registered.putExtra("ListCarBack",value_back1);
//                    registered.putExtra("cardetail",value_back2);
//                    startActivity(registered);
//
//                    finish();
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




}

