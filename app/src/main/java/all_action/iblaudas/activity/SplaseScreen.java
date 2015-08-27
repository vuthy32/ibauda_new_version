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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

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

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splas_layout);
        // Handler x = new Handler();
        //  x.postDelayed(new SplashHandler(), 2000);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
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
            new DownloadFileFromURL().execute(file_url);

        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplaseScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }, SPLASH_TIME_OUT);
            Log.e("First Runing", " Runing AnyTime");
            // Log.e("Firs", );
        }
        //doDownload(file_url,"sqlite");
       // new DownloadFileFromURL().execute(file_url);

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory= Runtime.getRuntime().totalMemory();
        long FreeMemory = Runtime.getRuntime().freeMemory();
        Log.d("MemoryLow","maxMemory="+maxMemory+",totalMemory="+totalMemory+";freeMemory="+FreeMemory);
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
            progressBar.setVisibility(View.VISIBLE);
            // updating progress bar value
            progressBar.setProgress(progress[0]);
            // updating percentage value
            tv.setText(String.valueOf(progress[0]) + "%");
        }
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                int lenghtOfFile = conection.getContentLength();

                File wallpaperDirectory = new File("/sdcard/Android/data/"+PACKAG_PAHT);
// have the object build the directory structure, if needed.
                wallpaperDirectory.mkdirs();
                long startTime = System.currentTimeMillis();
// create a File object for the output file
                File outputFile = new File(wallpaperDirectory,UrlJsonLink.DATABASE_NAME);

                long bytes = outputFile.length();

                Log.d("ANDRO_ASYNC", "Length of file: " + bytes);


                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                // Output stream to write file
                OutputStream output = new FileOutputStream(outputFile);
                byte data[] = new byte[1024];
                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress((int) ((total * 100) / count));
                    output.write(data, 0, count);
                }

//                Log.d("ImageManager", "download ready in"
//                        + ((System.currentTimeMillis() - startTime) / 1000)
//                        + " sec,"+((System.currentTimeMillis() - startTime) / 1000)/600);
                // flushing output
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

    protected void doDownload(final String urlLink, final String fileName) {
        Thread dx = new Thread() {

            public void run() {

                File root = android.os.Environment.getExternalStorageDirectory();
                File dir = new File (root.getAbsolutePath() + "/Content2/");
                if(dir.exists()==false) {
                    dir.mkdirs();
                }
                //Save the path as a string value

                try
                {
                    URL url = new URL(urlLink);
                    Log.i("FILE_NAME", "File name is "+fileName);
                    Log.i("FILE_URLLINK", "File URL is "+url);
                    URLConnection connection = url.openConnection();
                    connection.connect();
                    // this will be useful so that you can show a typical 0-100% progress bar
                    int fileLength = connection.getContentLength();
                    Log.i("FILE_URLLINK232", "File URL is "+fileLength);
                    // download the file
                    InputStream input = new BufferedInputStream(url.openStream());
                    OutputStream output = new FileOutputStream(dir+"/"+fileName);

                    byte data[] = new byte[1024];
                    long total = 0;
                    int count;
                    while ((count = input.read(data)) != -1) {
                        total += count;

                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Log.i("ERROR ON DOWNLOADING FILES", "ERROR IS" +e);
                }
            }
        };

        dx.start();
    }



}
