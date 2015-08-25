package info.androidhive.materialdesign.FragmentDetial;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.JsonModel.ModelGallery;
import info.androidhive.materialdesign.JsonModel.ModelHomeFragment;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.DatabaseHandler;
import info.androidhive.materialdesign.adapter.GalleryGridViewAdapter;

public class FragmentChildDetailGallery extends Fragment {
GalleryGridViewAdapter galleryGridViewAdapter;
    DatabaseHandler mydb;
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
        mydb=new DatabaseHandler(getActivity());
        SharedPreferences gallerData =getActivity().getSharedPreferences("GalleryImage", 0);
        int ArraySize = gallerData.getInt("Arraysize", 0);
        GridView gridViewGallery = (GridView)getActivity().findViewById(R.id.gridViewGaller);
        List<ModelGallery> arrayListItems = new ArrayList<ModelGallery>();
//        for (int g=0; g<ArraySize;g++){
//            ModelGallery photoData = new ModelGallery();
//            photoData.setThumbImage(gallerData.getString("ImageThumb", null));
//            arrayListItems.add(photoData);
//            Log.e("DADD", "" + gallerData.getString("ImageThumb", null));
//        }
        SharedPreferences CheckCarID = getActivity().getSharedPreferences("CheckCarID", Context.MODE_PRIVATE);
        String IndexID = CheckCarID.getString("indexID", null);
        Log.e("IndexID",""+IndexID);
       SharedPreferences.Editor editorImage = gallerData.edit();
        List<ModelHomeFragment> contactsCarPhoto = mydb.getGalleryPhoto(IndexID);
        for (ModelHomeFragment cnP : contactsCarPhoto){
            ModelGallery photoData = new ModelGallery();
            photoData.setThumbImage(cnP.getImageUrl());
            arrayListItems.add(photoData);
        }
        galleryGridViewAdapter = new GalleryGridViewAdapter(getActivity(),arrayListItems);
        gridViewGallery.setAdapter(galleryGridViewAdapter);
        gridViewGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent gridGallery =new Intent(getActivity(),ActivitySwipImage.class);
                startActivityForResult(gridGallery,12);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        Log.d("ArraySize",""+ArraySize);
    }
}
