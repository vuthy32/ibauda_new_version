package all_action.iblaudas.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import all_action.iblaudas.AppController.AppController;
import all_action.iblaudas.JsonModel.ModelGallery;
import all_action.iblaudas.R;

public class GalleryGridViewAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<ModelGallery> arrayItems;
     ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	public GalleryGridViewAdapter(Activity activity, List<ModelGallery> arrayItems) {
		this.activity = activity;
		this.arrayItems = arrayItems;
	}

	@Override
	public int getCount() {
		SharedPreferences getGallery = activity.getSharedPreferences("GalleryImage", Context.MODE_PRIVATE);
		int var = getGallery.getInt("Arraysize", 0);

		Log.e("DataItemwww", "" + var);
		return var;
	}

	@Override
	public Object getItem(int location) {
		return arrayItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.row_gridview, null);
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail_gridview);
        final ModelGallery m = arrayItems.get(position);
        thumbNail.setImageUrl(m.getThumbImage(), imageLoader);
		//Log.d("Hello",m.getThumbImage());
        return convertView;
    }


}