package all_action.iblaudas.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;

import all_action.iblaudas.CheckInternet.ConnectionDetector;
import all_action.iblaudas.R;
import all_action.iblaudas.function_api.JSONParserGet;
import all_action.iblaudas.json_url.UrlJsonLink;

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
    ConnectionDetector connectionDetectorInternet;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splas_layout);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        connectionDetectorInternet = new  ConnectionDetector(this);
        PACKAG_PAHT = this.getPackageName();
//        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
//        mboolean = settings.getBoolean("FIRST_RUN", false);
//        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
//        if (!mboolean) {
//            settings = getSharedPreferences("PREFS_NAME", 0);
//            SharedPreferences.Editor editor = settings.edit();
//            editor.putBoolean("FIRST_RUN", true);
//            Log.e("First Runing", "First Runing getJson");
//            editor.commit();
//             new DownloadFileFromURL().execute(file_url);
//
//        }else{
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Intent i = new Intent(SplaseScreen.this, MainActivity.class);
//                    startActivity(i);
//                    finish();
//                }
//            }, SPLASH_TIME_OUT);
//            Log.e("First Runing", " Runing AnyTime");
//            // Log.e("Firs", );
//        }
        if (!connectionDetectorInternet.isConnectingToInternet()) {
                Intent i = new Intent(SplaseScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            //Toast.makeText(this,"dfdf",Toast.LENGTH_SHORT).show();
        }else {
            new DownloadFileFromURL().execute(file_url);
            //Toast.makeText(this,"Intner",Toast.LENGTH_SHORT).show();
        }

    }



    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Toast.makeText(this,"Please remove some app in your phone",Toast.LENGTH_SHORT).show();
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
