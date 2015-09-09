package all_action.iblaudas.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import all_action.iblaudas.AppController.AppController;
import all_action.iblaudas.FragmentDetial.FragmentDetailCar;
import all_action.iblaudas.JsonModel.ModelHomeFragment;
import all_action.iblaudas.R;


public class ImageHomeAdapterSqlite extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelHomeFragment> arrayItems;
    private String home_title,
            count_item;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public ImageHomeAdapterSqlite(Activity activity, List<ModelHomeFragment> arrayItems) {
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
      final  ModelHomeFragment mRecords = arrayItems.get(position);

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.cutome_list_item, null);

            NetworkImageView imageView = (NetworkImageView) convertView.findViewById(R.id.imageViewData);

        Typeface custom_font = Typeface.createFromAsset(activity.getAssets(), "fonts/HelveticaNeueCondensedBold.otf");
        imageView.setImageUrl(mRecords.getImageUrl(), imageLoader);

        TextView carNo = (TextView)convertView.findViewById(R.id.textViewNo);
        carNo.setText(mRecords.getCarNo());
        carNo.setTypeface(custom_font);

        TextView titleCar = (TextView)convertView.findViewById(R.id.textViewTitle);
        titleCar.setText(mRecords.getTitle());
        titleCar.setTypeface(custom_font);
        Log.e("InexCar", mRecords.getIdexID());
        Log.e("getCarFob",mRecords.getCarFob());
        // Log.e("InexCar",mRecords.ge());

        TextView CountryCity = (TextView)convertView.findViewById(R.id.textViewContryCity);
        CountryCity.setText(mRecords.getCityCar());
        CountryCity.setTypeface(custom_font);

        TextView carPrice = (TextView)convertView.findViewById(R.id.textViewPrice);
        carPrice.setText(mRecords.getCarFob());
        carPrice.setTypeface(custom_font);
        TextView txt_newstatus = (TextView)convertView.findViewById(R.id.txtNewStatus);
        Log.e("Records",mRecords.getStatusNew()+","+(mRecords.getStatusReserved()));
        if(mRecords.getStatusNew().equals("new")){
            txt_newstatus.setVisibility(View.VISIBLE);
         }else{
            txt_newstatus.setVisibility(View.INVISIBLE);
           }
        TextView txtSaleOrReversed = (TextView)convertView.findViewById(R.id.txtReserved);
        if(mRecords.getStatusReserved().equals("sale") || mRecords.getStatusReserved().equals("shipok")){
            txtSaleOrReversed.setVisibility(View.INVISIBLE);
        }else{
            txtSaleOrReversed.setVisibility(View.VISIBLE);
            txtSaleOrReversed.setBackgroundResource(R.color.reversed_color_text);
            txtSaleOrReversed.setText(mRecords.getStatusReserved());
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDetailActivity = new Intent(activity, FragmentDetailCar.class);
                SharedPreferences CheckCarID = activity.getSharedPreferences("CheckCarID", 0);
                SharedPreferences.Editor userEditer=CheckCarID.edit();
                userEditer.putString("indexID",mRecords.getIdexID());
                userEditer.commit();
                activity.startActivity(toDetailActivity);
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        return convertView;
    }


}