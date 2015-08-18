package info.androidhive.materialdesign.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.text.DecimalFormat;
import java.util.List;

import info.androidhive.materialdesign.AppController.AppController;
import info.androidhive.materialdesign.JsonModel.ModelGallery;
import info.androidhive.materialdesign.JsonModel.OrderModel;
import info.androidhive.materialdesign.R;

public class MyOrderAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<OrderModel> arrayItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public MyOrderAdapter(Activity activity, List<OrderModel> arrayItems) {
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
            convertView = inflater.inflate(R.layout.cutome_list_item, null);
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.imageViewData);
        Typeface custom_font = Typeface.createFromAsset(activity.getAssets(), "fonts/HelveticaNeueCondensedBold.otf");

        final OrderModel  m = arrayItems.get(position);
        thumbNail.setImageUrl(m.getThumbImage(), imageLoader);
        Log.d("Hello", m.getThumbImage());
        TextView titleCar = (TextView)convertView.findViewById(R.id.textViewTitle);
        titleCar.setText(m.getTitleCar());
        titleCar.setTypeface(custom_font);

        TextView carNo = (TextView)convertView.findViewById(R.id.textViewNo);
        carNo.setText(m.getCarNoStr());
        carNo.setTypeface(custom_font);

        TextView CountryCity = (TextView)convertView.findViewById(R.id.textViewContryCity);
        CountryCity.setText(m.getCountCity());
        CountryCity.setTypeface(custom_font);

        TextView carPrice = (TextView)convertView.findViewById(R.id.textViewPrice);
        carPrice.setText(m.getCarPrice());
        carPrice.setTypeface(custom_font);

        return convertView;
    }


}