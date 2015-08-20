package info.androidhive.materialdesign.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.function_api.JSONParserGet;
import info.androidhive.materialdesign.json_url.UrlJsonLink;

/**
 * Created by sunry on 8/17/2015.
 */
public class SplaseScreen extends AppCompatActivity {
   private String PACKAG_PAHT;
    private static String file_url = "http://angkorauto.com/carfinder_ios/rest/data-sqlite/data.sqlite";
    /**
     * Called when the activity is first created.
     */
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
        new DownloadFileFromURL().execute(file_url);
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory= Runtime.getRuntime().totalMemory();
        long FreeMemory = Runtime.getRuntime().freeMemory();
        Log.d("MemoryLow", "maxMemory=" + maxMemory + ",totalMemory=" + totalMemory + ";freeMemory=" + FreeMemory);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory= Runtime.getRuntime().totalMemory();
        long FreeMemory = Runtime.getRuntime().freeMemory();
        Log.d("MemoryLow","maxMemory="+maxMemory+",totalMemory="+totalMemory+";freeMemory="+FreeMemory);
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                File wallpaperDirectory = new File("/sdcard/Android//data/"+PACKAG_PAHT);
// have the object build the directory structure, if needed.
                wallpaperDirectory.mkdirs();
// create a File object for the output file
                File outputFile = new File(wallpaperDirectory,UrlJsonLink.DATABASE_NAME);
                int lenghtOfFile = conection.getContentLength();
                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                // Output stream to write file
                OutputStream output = new FileOutputStream(outputFile);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    // writing data to file
                    Log.e("Totoal",count+","+total*1024);
                    output.write(data, 0, count);
                }
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

}
