package all_action.iblaudas.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

import all_action.iblaudas.AppController.AppController;
import all_action.iblaudas.FragmentDetial.FragmentDetailCar;
import all_action.iblaudas.JsonModel.ModelHomeFragment;
import all_action.iblaudas.R;
import all_action.iblaudas.json_url.UrlJsonLink;

public class ImageHomeAdapter extends ArrayAdapter<ModelHomeFragment> {
    private ImageLoader mImageLoader;
    Context mcontext;
    private RequestQueue mRequestQueue;
    public ImageHomeAdapter(Context context) {
        super(context, R.layout.custom_gride_items);
        mcontext =context;
        mImageLoader = AppController.getInstance().getImageLoader();
        mRequestQueue = Volley.newRequestQueue(mcontext);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public ModelHomeFragment getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cutome_list_item, parent, false);
        }

        // NOTE: You would normally use the ViewHolder pattern here
        NetworkImageView imageView = (NetworkImageView) convertView.findViewById(R.id.imageViewData);

        Typeface custom_font = Typeface.createFromAsset(mcontext.getAssets(), "fonts/HelveticaNeueCondensedBold.otf");

        final ModelHomeFragment m = getItem(position);
        imageView.setImageUrl(m.getImageUrl(), mImageLoader);

        TextView titleCar = (TextView)convertView.findViewById(R.id.textViewTitle);
        titleCar.setText(m.getTitle());
        titleCar.setTypeface(custom_font);

        TextView carNo = (TextView)convertView.findViewById(R.id.textViewNo);
        carNo.setText(m.getCarNo());
        carNo.setTypeface(custom_font);

        TextView CountryCity = (TextView)convertView.findViewById(R.id.textViewContryCity);
        CountryCity.setText(m.getCityCar());
        CountryCity.setTypeface(custom_font);

        TextView carPrice = (TextView)convertView.findViewById(R.id.textViewPrice);
        carPrice.setText(m.getCarFob());
        carPrice.setTypeface(custom_font);
        TextView txt_newstatus = (TextView)convertView.findViewById(R.id.txtNewStatus);
        if(m.getStatusNew().equals("new")){
            txt_newstatus.setVisibility(View.VISIBLE);
        }else{
            txt_newstatus.setVisibility(View.INVISIBLE);
        }
        TextView txtSaleOrReversed = (TextView)convertView.findViewById(R.id.txtReserved);
        if(m.getStatusReserved().equals("sale")){
            txtSaleOrReversed.setVisibility(View.INVISIBLE);
        }else{
            txtSaleOrReversed.setVisibility(View.VISIBLE);
            txtSaleOrReversed.setBackgroundResource(R.color.reversed_color_text);
            txtSaleOrReversed.setText(m.getStatusReserved());
        }


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDetailActivity = new Intent(mcontext, FragmentDetailCar.class);
                SharedPreferences CheckCarID = mcontext.getSharedPreferences("CheckCarID", 0);
                SharedPreferences.Editor userEditer=CheckCarID.edit();
                userEditer.putString("indexID",m.getIdexID());
                userEditer.commit();
                mcontext.startActivity(toDetailActivity);
            }
        });
        return convertView;
    }
    public void swapImageRecords(List<ModelHomeFragment> objects) {
        clear();
        for(ModelHomeFragment object : objects) {
            add(object);
        }
        notifyDataSetChanged();
    }

}
