package info.androidhive.materialdesign.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import info.androidhive.materialdesign.JsonModel.User;
import info.androidhive.materialdesign.R;

/**
 * Created by DON on 3/30/2015.
 */
public class ListUserAdapter extends BaseAdapter {

    /*********** Declare Used Variables *********/
    private Activity activity;
    private LayoutInflater inflater;
    private List<User> listUser;
    String getfname,getlname,getflname;
    public ListUserAdapter(Activity activity, List<User> listUser) {
        this.activity = activity;
        this.listUser = listUser;
    }


    @Override
    public int getCount() {
        return listUser.size();
    }

    @Override
    public Object getItem(int position) {
        return listUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View vi = view;
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (vi == null)
            vi = inflater.inflate(R.layout.list_user_item, null);

        TextView userName = (TextView)vi.findViewById(R.id.tvUserName);
        TextView msg = (TextView)vi.findViewById(R.id.tvMsg);

        User m = listUser.get(position);
         getfname = m.getUserName();
         getlname = m.getlastname();
        if(getlname.equals("null")){
            getlname = "";
        }
         getflname = getfname+" "+getlname;

        userName.setText(getflname);
        String newcounter = msg.toString();
        if(!m.getMsg().equals("0")) {
            msg.setText(m.getMsg());
            msg.setBackgroundResource(R.drawable.badge_circle);
            //msg.setVisibility(vi.GONE);
        }else{
            msg.setBackgroundResource(R.color.textColorPrimary);
            //
            }


        return vi;
    }
}
