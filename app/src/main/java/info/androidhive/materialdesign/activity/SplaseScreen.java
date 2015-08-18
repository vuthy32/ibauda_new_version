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
    Button btnShowProgress;
    // Progress Dialog
    private ProgressDialog pDialog;
    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;
    // File url to download
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
        btnShowProgress = (Button) findViewById(R.id.btnProgressBar);
        // Image view to show image after downloading
      //  my_image = (ImageView) findViewById(R.id.my_image);

        /**
         * Show Progress bar click event
         * */
        new DownloadFileFromURL().execute(file_url);

    }

    /**
     * Showing Dialog
     * */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type:
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }

    /**
     * Background Async Task to download file
     * */
    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();
                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                // Output stream to write file
                OutputStream output = new FileOutputStream("/sdcard/downloadedfile.sqlite");
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress(""+(int)((total*100)/lenghtOfFile));
                    // writing data to file
                    output.write(data, 0, count);
                }
                // flushing output
                output.flush();
                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            dismissDialog(progress_bar_type);
             Intent i = new Intent(SplaseScreen.this,MainActivity.class);
            startActivity(i);
            finish();
            // Displaying downloaded image into image view
            // Reading image path from sdcard
            //String imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg";
            // setting downloaded into image view
          //  my_image.setImageDrawable(Drawable.createFromPath(imagePath));
        }

    }


}
//    void downloadAndOpenPDF() {
//        new Thread(new Runnable() {
//            public void run() {
//                Uri path = Uri.fromFile(downloadFile(download_file_url));
//                try {
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setDataAndType(path, "application/sqlite");
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                    finish();
//                } catch (ActivityNotFoundException e) {
//                    //tv_loading.setError("PDF Reader application is not installed in your device");
//                }
//            }
//        }).start();
//
//    }
//
//    File downloadFile(String dwnload_file_path) {
//        File file = null;
//        try {
//
//            URL url = new URL(dwnload_file_path);
//            HttpURLConnection urlConnection = (HttpURLConnection) url
//                    .openConnection();
//            urlConnection.setRequestMethod("GET");
//            urlConnection.setDoOutput(true);
//            // connect
//            urlConnection.connect();
//            // set the path where we want to save the file
//            File SDCardRoot = Environment.getExternalStorageDirectory();
//            // create a new file, to save the downloaded file
//            file = new File(SDCardRoot, dest_file_path);
//            FileOutputStream fileOutput = new FileOutputStream(file);
//
//            // Stream used for reading the data from the internet
//            InputStream inputStream = urlConnection.getInputStream();
//
//            // this is the total size of the file which we are
//            // downloading
//            totalsize = urlConnection.getContentLength();
//            setText("Starting PDF download...");
//
//            // create a buffer...
//            byte[] buffer = new byte[1024 * 1024];
//            int bufferLength = 0;
//
//            while ((bufferLength = inputStream.read(buffer)) > 0) {
//                fileOutput.write(buffer, 0, bufferLength);
//                downloadedSize += bufferLength;
//                per = ((float) downloadedSize / totalsize) * 100;
//                setText("Total PDF File size  : "
//                        + (totalsize / 1024)
//                        + " KB\n\nDownloading PDF " + (int) per
//                        + "% complete");
//            }
//            // close the output stream when complete //
//            fileOutput.close();
//            setText("Download Complete. Open PDF Application installed in the device.");
//
//        } catch (final MalformedURLException e) {
//            setTextError("Some error occured. Press back and try again.",
//                    Color.RED);
//        } catch (final IOException e) {
//            setTextError("Some error occured. Press back and try again.",
//                    Color.RED);
//        } catch (final Exception e) {
//            setTextError(
//                    "Failed to download image. Please check your internet connection.",
//                    Color.RED);
//        }
//        return file;
//    }
//
//    void setTextError(final String message, final int color) {
//        runOnUiThread(new Runnable() {
//            public void run() {
//                // tv_loading.setTextColor(color);
//                // tv_loading.setText(message);
//            }
//        });
//
//    }
//
//    void setText(final String txt) {
//        runOnUiThread(new Runnable() {
//            public void run() {
//                //  tv_loading.setText(txt);
//            }
//        });
//
//    }

//}


//        JSONParserGet jsonParserGet = new JSONParserGet();
//        JSONArray jsonObj = jsonParserGet.getJSONFromUrl(UrlJsonLink.URL_SEARCH);
//        for (int i=0; i<jsonObj.length();i++){
//            Log.e("data",""+jsonObj.length());
//        }
//    }
//    //*******************Set Time Splas Screen ********************
//    class SplashHandler implements Runnable {
//
//        @Override
//        public void run() {
//            // TODO Auto-generated method stub
//            startActivity(new Intent(getApplication(), MainActivity.class));
//            SplaseScreen.this.finish();
//        }
//
//    }
//
//
//
//}
