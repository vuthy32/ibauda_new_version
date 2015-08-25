package info.androidhive.materialdesign.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.toolbox.ImageLoader;

import org.apache.http.HttpStatus;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import info.androidhive.materialdesign.AppController.AppController;
import info.androidhive.materialdesign.JsonModel.ModelHomeFragment;
import info.androidhive.materialdesign.R;

public class ViewPagerAdapter extends PagerAdapter {
	Context context;
	ArrayList<HashMap<String, String>> viewImageData;
	LayoutInflater inflater;
	TouchImageView thumbNail_slide;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	HashMap<String, String> resultp_slide = new HashMap<String, String>();
	public ViewPagerAdapter(Context context,
							ArrayList<HashMap<String, String>> arraylistImage) {
		this.context = context;
		viewImageData = arraylistImage;
	}

	@Override
	public int getCount() {
		return viewImageData.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((RelativeLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View convertView = inflater.inflate(R.layout.viewpager_item, container, false);
		resultp_slide = viewImageData.get(position);
		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		thumbNail_slide = (TouchImageView) convertView.findViewById(R.id.image_slide);
		new ImageDownloaderTask(thumbNail_slide).execute(resultp_slide.get("photo_url"));
		//thumbNail_slide.setImageUrl(resultp_slide.get("photo_url"), imageLoader);
		((ViewPager) container).addView(convertView);
		return convertView;

	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// Remove viewpager_item.xml from ViewPager
		((ViewPager) container).removeView((RelativeLayout) object);

	}
	class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap>{
		private final WeakReference<TouchImageView>ImageViewReference;
		public ImageDownloaderTask(TouchImageView touchImageView){
			ImageViewReference = new WeakReference<TouchImageView>(touchImageView);
		}
		@Override
		protected Bitmap doInBackground(String... params) {
			return downloadBitmap(params[0]);
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (isCancelled()){
				bitmap = null;
			}
			if (ImageViewReference!=null){
				thumbNail_slide = ImageViewReference.get();
				if (thumbNail_slide!=null){
					thumbNail_slide.setImageBitmap(bitmap);
				}else{
					thumbNail_slide.setImageResource(R.drawable.thumb_bg);
				}
			}
			super.onPostExecute(bitmap);
		}
	}

	private Bitmap downloadBitmap(String url){
		HttpURLConnection urlConnection=null;
		try{
			URL uri = new URL(url);
			urlConnection = (HttpURLConnection)uri.openConnection();
			int statusCode = urlConnection.getResponseCode();
			if (statusCode!= HttpStatus.SC_OK){
				return null;
			}
			InputStream inputStream = urlConnection.getInputStream();
			if (inputStream!=null){
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
				return bitmap;
			}
		}catch (Exception e){
			urlConnection.disconnect();
		}finally {
			if (urlConnection!=null){
				urlConnection.disconnect();
			}
		}
		return null;
	}

}