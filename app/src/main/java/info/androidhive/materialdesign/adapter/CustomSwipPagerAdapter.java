package info.androidhive.materialdesign.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import info.androidhive.materialdesign.AppController.AppController;
import info.androidhive.materialdesign.JsonModel.ModelHomeFragment;
import info.androidhive.materialdesign.R;

public class CustomSwipPagerAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<ModelHomeFragment> arrayItems;
    private String home_title,
           count_item;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	public CustomSwipPagerAdapter(Activity activity, List<ModelHomeFragment> arrayItems) {
		this.activity = activity;
		this.arrayItems = arrayItems;
	}

	@Override
	public int getCount() {
		return arrayItems.size();
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
		ModelHomeFragment m = arrayItems.get(position);
        thumbNail.setImageUrl(m.getPhotoUrl(), imageLoader);

        return convertView;
    }


}