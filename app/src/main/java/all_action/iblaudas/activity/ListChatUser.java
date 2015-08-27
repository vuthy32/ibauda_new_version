package all_action.iblaudas.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import all_action.iblaudas.JsonModel.User;
import all_action.iblaudas.R;
import all_action.iblaudas.activity.ChatActivity;
import all_action.iblaudas.adapter.ListUserAdapter;
import all_action.iblaudas.function_api.UserFunctions;


public class ListChatUser extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Toolbar mToolbar;
    private TextView headText;

    private EditText mTaskInput;
    private ListView mListView;
   // private TaskAdapter mAdapter;

    private List<User> listUser;
    private ListView listView;
    private ListUserAdapter adapter;
    //ArrayList<String> list;
    //private static String url = "http://192.168.0.210/mobile/getUserList.php";
    SharedPreferences user;
    private String memeber_no,cmID, last_user;

    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_user_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        headText = (TextView) findViewById(R.id.toolbar_title);
        headText.setText(R.string.title_chat);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String member_id = user.getString("member_id", "");
        memeber_no = user.getString("memeber_no", "");
if (!member_id.equals("")){
    new getUserList().execute();
}else{
    Intent upanel = new Intent(ListChatUser.this, LoginActivity.class);
    upanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(upanel);
    this.finish();
}

        handler.postDelayed(runnable, 20000);
        SharedPreferences getmewMsg = getSharedPreferences("getNewMsg", Context.MODE_PRIVATE);
        cmID = getmewMsg.getString("last_cmd", "");
        last_user = getmewMsg.getString("last_user", "");
        String user2 = getmewMsg.getString("last_user2", "");
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
        ListChatUser.this.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        SharedPreferences users = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String memeber_no = users.getString("member_no", "");
        String remember_token = users.getString("remember_token", "");
        String receiver_no = listUser.get(position).getReceiver().toString();
        String user = listUser.get(position).getUserName().toString();
        Intent chat = new Intent(ListChatUser.this, ChatActivity.class);

        SharedPreferences chatSetting = getSharedPreferences("chatSetting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = chatSetting.edit();
        editor.putString("receiver_no", receiver_no);
        editor.putString("memeber_no", memeber_no);
        editor.putString("remember_token", remember_token);

        editor.commit();
        chat.putExtra("receiver_no", receiver_no);
        chat.putExtra("user", user);
        chat.putExtra("listchat",4);
        startActivity(chat);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }



    // Defines a runnable which is run every 100ms
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            new getUserList().execute();
            // new getNewMsglistUser().execute();
            handler.postDelayed(this, 20000);

        }
    };
        private class getUserList extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... args) {
            UserFunctions userFunction = new UserFunctions();
            SharedPreferences user = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
            String memeber_no = user.getString("member_no", "");
            String remember_token = user.getString("remember_token", "");
            Log.e("member_no", "" + memeber_no);
            Log.e("remember_token", remember_token);
            JSONObject json = userFunction.UserChat(memeber_no, remember_token);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            /**
             * Checks for success message.
             **/
            try {

                String res = json.getString("success");
                Log.d("key suc: ", "> " + res);

                if (res.equals("1")) {
                    JSONArray jsonarray = json.getJSONArray("user_info");
                    listUser = new ArrayList<User>();
                    //list = new ArrayList<String>();
                    Log.d("leaght", String.valueOf(jsonarray.length()));
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        //list.add(jsonobject.getString("member_first_name"));

                        User mUser = new User();
                        String revice = jsonobject.getString("member_no");
                        mUser.setReceiver(jsonobject.getString("member_no"));
                        mUser.setUserName(jsonobject.getString("member_first_name"));
                        mUser.setLastname(jsonobject.getString("member_last_name"));
                        String newmsgcount = jsonobject.getString("new_count");
                        mUser.setMsg(newmsgcount);
                        listUser.add(mUser);
                        adapter = new ListUserAdapter(ListChatUser.this, listUser);
                        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListChatUser.this, android.R.layout.simple_list_item_1, list );
                        listView = (ListView) findViewById(R.id.listViewUserChat);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(ListChatUser.this);
                        Log.e("HellNew=",""+jsonobject.getString("new_count"));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            handler.postDelayed(runnable, 40000);
            }
        }
    private String tag="Chat Activity";
    public void onStart()
    {
        super.onStart();
        Log.d(tag, "In the onStart() event");
    }
    public void onRestart()
    {
        super.onRestart();
        handler.postDelayed(runnable, 20000);
        Log.d(tag, "In the onRestart() event");
    }
    public void onResume()
    {
        super.onResume();
        Log.d(tag, "In the onResume() event");
    }
    public void onPause()
    {
        super.onPause();
        //this.finish();
        Log.d(tag, "In the onPause() event");
    }
    public void onStop()
    {
        super.onStop();
        handler.removeCallbacks(runnable);
        Log.d(tag, "In the onStop() event");
    }
    @Override
    protected void onDestroy() {

        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
    }

