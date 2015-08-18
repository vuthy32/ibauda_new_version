package info.androidhive.materialdesign.FragmentDetial;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.JsonModel.ModelGallery;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.GalleryGridViewAdapter;

public class FragmentChildDetailGallery extends Fragment {
GalleryGridViewAdapter galleryGridViewAdapter;
	public FragmentChildDetailGallery(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.gridview_gallery, container, false);
         
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences gallerData =getActivity().getSharedPreferences("GalleryImage", 0);
        int ArraySize = gallerData.getInt("Arraysize", 0);
        GridView gridViewGallery = (GridView)getActivity().findViewById(R.id.gridViewGaller);
        List<ModelGallery> arrayListItems = new ArrayList<ModelGallery>();
        for (int g=0; g<ArraySize;g++){
            ModelGallery photoData = new ModelGallery();
            photoData.setThumbImage(gallerData.getString("ImageThumb" + g, null));
            arrayListItems.add(photoData);
            Log.e("DADD", "" + gallerData.getString("ImageThumb" + g, null));
        }
        galleryGridViewAdapter = new GalleryGridViewAdapter(getActivity(),arrayListItems);
        gridViewGallery.setAdapter(galleryGridViewAdapter);
        Log.d("ArraySize",""+ArraySize);
    }
}
