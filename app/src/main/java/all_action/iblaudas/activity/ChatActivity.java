package all_action.iblaudas.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import all_action.iblaudas.CheckInternet.ConnectionDetector;
import all_action.iblaudas.JsonModel.Message;

import all_action.iblaudas.R;
import all_action.iblaudas.adapter.ChatListAdapter;
import all_action.iblaudas.adapter.Task;
import all_action.iblaudas.adapter.TaskAdapter;
import all_action.iblaudas.function_api.UserFunctions;

public class ChatActivity extends AppCompatActivity implements OnItemClickListener {
    private Toolbar mToolbar;
    private TextView headText;

    private Button submit_button;
    MediaPlayer mp = null;
    private EditText mTaskInput;
    private ListView mListView;
    private TaskAdapter mAdapter;
    String receiver_no,memeber_no,remember_token,cm_id,msg;
    String userName;
    String lastCM = "";
    ArrayList<Message> listMessage;
    ArrayList<Message> CM;
    private ChatListAdapter adapter;
    ListView lvChat;
    public static final String DEFAULT ="";
    private Handler handler = new Handler();
    String userID1,UserID2;
    String cmID,last_user,user2,users;

    int result;
    ConnectionDetector cd;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);
        Intent test = getIntent();
        receiver_no = test.getStringExtra("receiver_no");
        userName = test.getStringExtra("user");

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        headText = (TextView) findViewById(R.id.toolbar_title);
        headText.setText(userName);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdapter = new TaskAdapter(ChatActivity.this, new ArrayList<Task>());

        //handler.postDelayed(runnable, 100);
        new getMessage().execute();
        // Run the runnable object defined every 100ms


        submit_button = (Button)findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mTaskInput = (EditText)findViewById(R.id.task_input);
                msg = mTaskInput.getText().toString();
                if(msg.length()>0) {
                    cd = new ConnectionDetector(getApplicationContext());
                    // Check if Internet present
                    if (!cd.isConnectingToInternet()) {
                        // Internet Connection is not present
                        Toast.makeText(getApplicationContext(),"Please check the Internet...",Toast.LENGTH_LONG).show();
                    } else {
                        new setupMessagePosting().execute();
                        mTaskInput.setText("");
                    }
                }else{

                    // mp.stop();
                    //Toast.makeText(getApplicationContext(),"Write something..",Toast.LENGTH_LONG).show();
                }
            }
        });
        handler.postDelayed(runnable, 1000);
    }



    // Defines a runnable which is run every 100ms
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

                refreshMessages();
                handler.postDelayed(this, 1000);

        }
    };

    private void refreshMessages() {
        new getLastCM().execute();
       // new getNewMsgs().execute();
        if(!lastCM.equals(cm_id)){
            new getMessage().execute();

        }
        //Toast.makeText(ChatActivity.this, lastCM, Toast.LENGTH_SHORT).show();
    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Task task = mAdapter.getItem(position);
        TextView taskDescription = (TextView) view.findViewById(R.id.task_description);

        task.setCompleted(!task.isCompleted());

        if(task.isCompleted()){
            taskDescription.setPaintFlags(taskDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            taskDescription.setPaintFlags(taskDescription.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        task.saveEventually();

    }

    private class getMessage extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            UserFunctions userFunction = new UserFunctions();
            SharedPreferences chatSetting = getSharedPreferences("chatSetting", Context.MODE_PRIVATE);
            memeber_no = chatSetting.getString("memeber_no",DEFAULT);
            remember_token = chatSetting.getString("remember_token",DEFAULT);
            receiver_no = chatSetting.getString("receiver_no",DEFAULT);
            Log.e("memeber_no",memeber_no);
            Log.e("remember_token",remember_token);
            Log.e("receiver_no",receiver_no);

            JSONObject json = userFunction.ReceiveMessage(memeber_no, remember_token, receiver_no);
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

                JSONArray jsonarray = json.getJSONArray("conversation_msgs");
                listMessage = new ArrayList<Message>();
                Log.d("leaght" , String.valueOf(jsonarray.length()));
                for (int i = 0; i < jsonarray.length(); i++){
                    JSONObject jsonobject  = jsonarray.getJSONObject(i);

                    Message mMessage = new Message();
                    mMessage.setText(jsonobject.getString("conversation_text"));
                    mMessage.setMessageId(jsonobject.getString("member_name"));
                    mMessage.setSenderId(jsonobject.getString("author"));
                    mMessage.setRecipientId(jsonobject.getString("user2"));
                    users = jsonobject.getString("author");
                    mMessage.setCm_id(jsonobject.getString("cm_id"));
                    String createDate = (jsonobject.getString("created_dt"));
                    mMessage.setCreated_dt(createDate.substring(10,createDate.length()));
                    listMessage.add(mMessage);
                    lvChat = (ListView) findViewById(R.id.lvChat);
                    adapter = new ChatListAdapter(ChatActivity.this, memeber_no, listMessage);
                    adapter.notifyDataSetChanged();
                    lvChat.setAdapter(adapter);
                    lvChat.invalidateViews();
                    lastCM = listMessage.get(listMessage.size()-1).getCm_id();

                }
              /*  mp = MediaPlayer.create(getApplicationContext(), R.raw.mysound);
                Log.e("users=",users);
                Log.e("remsdf=",receiver_no);
                if(!users.equals(memeber_no)) {;
                    mp.start();

                }*/
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    //========================Check internet post msg========================

    private class setupMessagePosting extends AsyncTask<String, String, JSONObject> {

        /**
         * Defining Process dialog
         */
        private ProgressDialog pDialog;
        @Override
        protected JSONObject doInBackground(String... args) {
            UserFunctions userFunction = new UserFunctions();
                SharedPreferences chatSetting = getSharedPreferences("chatSetting", Context.MODE_PRIVATE);
                memeber_no = chatSetting.getString("memeber_no", DEFAULT);
                remember_token = chatSetting.getString("remember_token", DEFAULT);
                receiver_no = chatSetting.getString("receiver_no", DEFAULT);
                JSONObject json = userFunction.setupMessagePosting(memeber_no, remember_token, receiver_no, msg);
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
                Message mMessage = new Message();
                mMessage.setText(msg);
                mMessage.setSenderId(memeber_no);
                mMessage.setRecipientId(receiver_no);
                mMessage.setCm_id(lastCM);
                /*listMessage.add(mMessage);
                lvChat = (ListView) findViewById(R.id.lvChat);
                adapter = new ChatListAdapter(ChatActivity.this, memeber_no, listMessage);
                adapter.notifyDataSetChanged();
                lvChat.setAdapter(adapter);
                lvChat.invalidateViews();*/
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class getLastCM extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            UserFunctions userFunction = new UserFunctions();
            SharedPreferences chatSetting = getSharedPreferences("chatSetting", Context.MODE_PRIVATE);
            memeber_no = chatSetting.getString("memeber_no",DEFAULT);
            remember_token = chatSetting.getString("remember_token",DEFAULT);
            receiver_no = chatSetting.getString("receiver_no",DEFAULT);
            JSONObject json = userFunction.ReceiveMessage(memeber_no, remember_token,receiver_no);
            return json;

        }

        @Override
        protected void onPostExecute(JSONObject json) {
            SharedPreferences getmewMsg = getSharedPreferences("getNewMsg", Context.MODE_PRIVATE);
            cmID = getmewMsg.getString("last_cmd", "");
            last_user = getmewMsg.getString("last_user", "");
             user2 = getmewMsg.getString("last_user2","");
            /**
             * Checks for success message.
             **/
            try {
                String res = json.getString("success");
                Log.d("key suc: ", "> " + res);
                Log.d("json: ", "> " + json.toString());

                JSONArray jsonarray = json.getJSONArray("conversation_msgs");
                CM = new ArrayList<Message>();
                Log.d("leaght" , String.valueOf(jsonarray.length()));
                for (int i = 0; i < jsonarray.length(); i++){
                    JSONObject jsonobject  = jsonarray.getJSONObject(i);

                    Message mMessage = new Message();
                    mMessage.setCm_id(jsonobject.getString("cm_id"));
                    mMessage.setUserID(jsonobject.getString("user2"));
                    CM.add(mMessage);
                    cm_id = CM.get(CM.size()-1).getCm_id();

                }


                //Log.e("LasCmID=",cm_id);
               // Log.e("LasstUerID=",userID);
            } catch (JSONException e) {
                e.printStackTrace();
            }

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
        handler.postDelayed(runnable, 1000);
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
        handler.postDelayed(runnable, 0);
       handler.removeCallbacks(runnable);
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
        ChatActivity.this.finish();
        this.overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
    }
}
