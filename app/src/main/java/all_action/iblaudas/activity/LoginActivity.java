package all_action.iblaudas.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import all_action.iblaudas.R;
import all_action.iblaudas.activity.MainActivity;
import all_action.iblaudas.activity.Register;
import all_action.iblaudas.function_api.UserFunctions;

/**
 * Created by Ravi on 29/07/15.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView RegisterTxt,headText;
    private EditText txt_userID, txt_pwd;
    private Button ButtonLogin;
    private static String KEY_SUCCESS = "success";
    private Toolbar mToolbar;
    public LoginActivity() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        headText = (TextView) findViewById(R.id.toolbar_title);
        headText.setText(R.string.nav_item_login);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txt_userID=(EditText)findViewById(R.id.txt_userID);
        txt_pwd=(EditText)findViewById(R.id.txt_pwd);

        RegisterTxt = (TextView)findViewById(R.id.register_now);
        RegisterTxt.setOnClickListener(this);
        // defin Button Login
        ButtonLogin =(Button)findViewById(R.id.btn_login);
        ButtonLogin.setOnClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LoginActivity.this.finish();
        this.overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                txt_userID.setError(null);
                txt_pwd.setError(null);
                if (  ( !txt_userID.getText().toString().equals("")) && ( !txt_pwd.getText().toString().equals("")) )
                {
                    new ProcessLoginIblauda().execute();
                    Log.d("Login","Do Acion Login");
                }
                else if ( ( !txt_userID.getText().toString().equals("")) )
                {
                    txt_pwd.setError("User ID is empty");

                }
                else if ( ( !txt_pwd.getText().toString().equals("")) )
                {
                    txt_userID.setError("Password is empty");
                }
                else
                {
                    txt_userID.setError("User ID is empty");
                    txt_pwd.setError("Password is empty");
                }
                break;
            case R.id.register_now:
                Intent RegisterActivity = new Intent(this,Register.class);
                startActivity(RegisterActivity);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                break;
        }

    }//end View Click Action

    //== Do Action Login AsyncTask
    private class ProcessLoginIblauda extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        private String email,password;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            email = txt_userID.getText().toString();
            password = txt_pwd.getText().toString();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Logging in ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.loginUser(email, password);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {

            try {
                if (json.getString(KEY_SUCCESS) != null) {

                    String res = json.getString(KEY_SUCCESS);
                    Log.d("key: ", "> " + res);

                    if(Integer.parseInt(res) == 1){
                        pDialog.setMessage("Login success ...");
                        pDialog.setTitle("Welcome");
                        //get user info
                        JSONObject json_user = json.getJSONObject("user");
                        //put in SharedPreferences
                        SharedPreferences user = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = user.edit();
                        editor.putString("member_id", json_user.getString("member_id"));
                        editor.putString("member_first_name", json_user.getString("member_first_name"));
                        editor.putString("member_last_name", json_user.getString("member_last_name"));
                        editor.putString("email", json_user.getString("email"));
                        editor.putString("member_country", json_user.getString("member_country"));
                        editor.putString("member_no", json_user.getString("member_no"));
                        editor.putString("remember_token", json_user.getString("remember_token"));
                        editor.commit();
                        Intent upanel = new Intent(LoginActivity.this, MainActivity.class);
                        upanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pDialog.dismiss();
                        startActivity(upanel);
                        finish();
                    }
                    else{
                        pDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Incorrect username/password", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
