package all_action.iblaudas.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import all_action.iblaudas.R;


public class MenuAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<String> items;
    private ArrayList<Integer> iconImage;
	private LayoutInflater inflater;
	SharedPreferences user;
	String full_name;

	public MenuAdapter(Context context, ArrayList<String> items, ArrayList<Integer> iconImage, Activity act) {
		this.context = context;
		this.items = items;
        this.iconImage = iconImage;
		this.inflater = LayoutInflater.from(context);		
	}
			

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueCondensedBold.otf");
		convertView = inflater.inflate(R.layout.drawer_list_item, null);

		final String dataleftmenu = getItem(position);
		TextView title = (TextView) convertView.findViewById(R.id.title);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.icon);


		title.setText(getItem(position));
        imageView.setImageResource(iconImage.get(position));
		title.setTypeface(custom_font);
		return convertView;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public String getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


}
