package info.androidhive.materialdesign.activity;

/**
 * Created by Ravi on 29/07/15.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.NavDrawerListAdapter;
import info.androidhive.materialdesign.model.NavDrawerItems;

public class FragmentDrawer extends Fragment {

    private ListView recyclerView;
    private static ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavDrawerListAdapter adapter;
    private View containerView;
    private static String[] titles = null;
    private static int[] iconItem = null;
    private FragmentDrawerListener drawerListener;
    static  SharedPreferences user;
    private ArrayList<NavDrawerItems> navDrawerItems;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    public FragmentDrawer() {

    }
    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String member_id = user.getString("member_id", "");
        Log.e("userID",member_id);
        if (member_id.equals("")) {
            iconItem = getActivity().getResources().getIntArray(R.array.nav_drawer_iconsLogin);
            titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
        } else {
            iconItem = getActivity().getResources().getIntArray(R.array.nav_drawer_iconsscce);
            titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labelsLoginSucess);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
//        // drawer labels
//        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
////
////		// nav drawer icons from resources
//        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
//
//        navDrawerItems = new ArrayList<NavDrawerItems>();
//        // adding nav drawer items to array
//        // Home
//        navDrawerItems.add(new NavDrawerItems(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
//        // Find People
//        navDrawerItems.add(new NavDrawerItems(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
//        // Photos
//        navDrawerItems.add(new NavDrawerItems(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
//        // Communities, Will add a counter here
//        navDrawerItems.add(new NavDrawerItems(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
//        // Pages
//        navDrawerItems.add(new NavDrawerItems(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
//
      //  recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView = (ListView)layout.findViewById(R.id.drawerList);
        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
//
//        adapter = new NavDrawerListAdapter(getActivity(), navDrawerItems);
//        recyclerView.setAdapter(adapter);
        //mDrawerLayout =(DrawerLayout)getActivity().findViewById(R.id.drawer_layout)
        return layout;
    }
    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        Log.e("FragmentID",""+containerView);
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                   getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);

    }
}
