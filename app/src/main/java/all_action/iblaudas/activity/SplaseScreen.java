package all_action.iblaudas.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import  all_action.iblaudas.CheckInternet.ConnectionDetector;

import all_action.iblaudas.Dialog.DialogManager;
import all_action.iblaudas.Pushnotification.ServerUtilities;
import all_action.iblaudas.R;
import all_action.iblaudas.json_url.UrlJsonLink;
import static all_action.iblaudas.Pushnotification.CommonUtilities.SENDER_ID;
/**
 * Created by sunry on 8/17/2015.
 */
public class SplaseScreen extends AppCompatActivity {
   private String PACKAG_PAHT;
    private static String file_url = "http://iblauda.com/android/iblauda.sqlite";
    /**
     * Called when the activity is first created.
     */
    boolean mboolean = false;
    private static int SPLASH_TIME_OUT = 1000;

    ProgressBar progressBar;
    TextView tv;
    int prg = 0;
    AsyncTask<Void, Void, Void> mRegisterTask;

    ConnectionDetector connectionDetectorInternet;
    DialogManager dialogManager;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splas_layout);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        tv = (TextView) findViewById(R.id.tvId);

        dialogManager = new DialogManager();
        connectionDetectorInternet = new  ConnectionDetector(this);
        PACKAG_PAHT = this.getPackageName();
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
        mboolean = settings.getBoolean("FIRST_RUN", false);
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (!mboolean) {
            settings = getSharedPreferences("PREFS_NAME", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("FIRST_RUN", true);
            Log.e("First Runing", "First Runing getJson");
            editor.commit();
            tv.setText("Please wait downloading");
            //*******************************************************************************8
            GCMRegistrar.checkDevice(this);
            GCMRegistrar.checkManifest(this);
            final String regId = GCMRegistrar.getRegistrationId(this);
            // Check if regid already presents
            if (regId.equals("")) {
                // Registration is not present, register now with GCM
                GCMRegistrar.register(this, SENDER_ID);
            } else {
                // Device is already registered on GCM
                if (GCMRegistrar.isRegisteredOnServer(this)) {
                } else {
                    final Context context = this;
                    mRegisterTask = new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            // Register on our server
                            // On server creates a new user
                            ServerUtilities.register(context, regId);
                            return null;
                        }
                        @Override
                        protected void onPostExecute(Void result) {
                            mRegisterTask = null;
                        }
                    };
                    mRegisterTask.execute(null, null, null);
                }
            }
            Log.d("NumberPhoneID", "" + regId);
            //*******************************************************************************8
        }else{
            tv.setText("Please wait updating");
            Log.e("First Runing", " Runing AnyTime");
            // Log.e("Firs", );
        }
        if (!connectionDetectorInternet.isConnectingToInternet()) {
                Intent i = new Intent(SplaseScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            //Toast.makeText(this,"dfdf",Toast.LENGTH_SHORT).show();
        }else {
            new DownloadFileFromURL().execute(file_url);
       }
        //==========notification========




    }



    @Override
    public void onLowMemory() {
        super.onLowMemory();
        dialogManager.showAlertDialog(this, "IBLUDA MESSAGE", "Please remove other app menory full", false);
        this.finish();
    }

    class DownloadFileFromURL extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.pbId);
            tv = (TextView) findViewById(R.id.tvId);
            progressBar.setProgress(0);
            super.onPreExecute();

        }
        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
//            progressBar.setVisibility(View.VISIBLE);
//            // updating progress bar value
//            progressBar.setProgress(progress[0]);
//            // updating percentage value
//            tv.setText(String.valueOf(progress[0]) + "%");
        }
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                File wallpaperDirectory = new File("/sdcard/Android/data/"+PACKAG_PAHT);
// have the object build the directory structure, if needed.
                wallpaperDirectory.mkdirs();
// create a File object for the output file
                File outputFile = new File(wallpaperDirectory,UrlJsonLink.DATABASE_NAME);
               // Log.d("ANDRO_ASYNC", "Length of file: " + lenghtOfFile);
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                // Output stream to write file
                OutputStream output = new FileOutputStream(outputFile);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                  //  publishProgress((int) ((total * 100)/total-count));
                    output.write(data, 0, count);
                }
                output.flush();
                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
                SplaseScreen.this.finish();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
             Intent i = new Intent(SplaseScreen.this,MainActivity.class);
            startActivity(i);
            finish();
        }

    }


    //**************Progress Bar ********************88
    private Runnable myThread = new Runnable()
    {
        @Override
        public void run()
        {
            while (prg < 100)
            {
                try
                {
                    hnd.sendMessage(hnd.obtainMessage());
                    Thread.sleep(100);
                }
                catch(InterruptedException e)
                {
                    Log.e("ERROR", "Thread was Interrupted");
                }
            }

            runOnUiThread(new Runnable() {
                public void run() {
                    tv.setText("Finished");
                }
            });
        }

        Handler hnd = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                prg++;
               // pb.setProgress(prg);
//
                String perc = String.valueOf(prg).toString();
                tv.setText(perc+"% completed");
            }
        };
    };
    //***The end********************



}
