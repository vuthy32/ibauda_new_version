package all_action.iblaudas.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import all_action.iblaudas.R;

/**
 * Created by sunry on 8/31/2015.
 */
public class MyAccount extends AppCompatActivity {
    SharedPreferences user;
    String member_no;
    private Toolbar mToolbar;
    private TextView headText;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_account);

        //************Set ToolBar****************************
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        headText = (TextView) findViewById(R.id.toolbar_title);
        headText.setText("My Account");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //************* get ShareReference Data User******************
        user = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        member_no = user.getString("member_id", "");
        String fname = user.getString("member_first_name", "");
        String lanme = user.getString("member_last_name", "");
        String email = user.getString("email", "");
        String country = user.getString("member_country", "");

        //**********************Set Text User Info*****************
        TextView membID = (TextView)findViewById(R.id.txtId);
        membID.setText(member_no);
        TextView fullanme = (TextView)findViewById(R.id.txtcar_fullanme);
        fullanme.setText(fname+" "+lanme);
        TextView myemail = (TextView)findViewById(R.id.txtemail);
        myemail.setText(email);
        TextView mycpountry = (TextView)findViewById(R.id.coutyy);
        mycpountry.setText(country);
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
