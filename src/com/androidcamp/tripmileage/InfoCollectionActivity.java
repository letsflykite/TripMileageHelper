package com.androidcamp.tripmileage;

import java.util.ArrayList;

import com.androidcamp.tripmileage.R;
import com.androidcamp.tripmileage.adapters.PhotosAdapter;
import com.androidcamp.tripmileage.fragments.LogFragment;
import com.androidcamp.tripmileage.fragments.PhotosFragment;
import com.androidcamp.tripmileage.models.Photo;
import com.androidcamp.tripmileage.utils.Utils;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class InfoCollectionActivity extends FragmentActivity implements TabListener{

	
	PhotosFragment fragmentPhotos = null;
	com.google.android.gms.maps.SupportMapFragment fragmentMap = null;
	private LogFragment fragmentLog = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_collection);
        setupNavigationTabs();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.info_collection, menu);
        return true;
    }
 
	private void setupNavigationTabs() {

		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab tabLog = actionBar.newTab().setText("Log").setTag("fragmentLog")
				.setIcon(R.drawable.ic_log).setTabListener(this);
		Tab tabPhotos = actionBar.newTab().setText("Photos").setTag("fragmentPhotos")
				.setIcon(R.drawable.ic_photo)
				.setTabListener(this);
		Tab tabMap = actionBar.newTab().setText("Map").setTag("fragmentMap")
				.setIcon(R.drawable.ic_map).setTabListener(this);

		actionBar.addTab(tabLog);
		actionBar.addTab(tabPhotos);
		actionBar.addTab(tabMap);
		actionBar.selectTab(tabMap);
	}
	
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = getSupportFragmentManager()
				.beginTransaction();
		
		if (tab.getTag() == "fragmentLog") {
			fragmentLog  = new LogFragment();
			fts.replace(R.id.frmContent, fragmentLog);
		}
		else if (tab.getTag() == "fragmentPhotos") {
			// set the fragment in framelayout to home timeline
			fragmentPhotos = new PhotosFragment();
			fts.replace(R.id.frmContent, fragmentPhotos);
		} else if (tab.getTag() == "fragmentMap") {
			// set the fragment in the frame layout to the mentions timeline
			fragmentMap = new SupportMapFragment();
			fts.replace(R.id.frmContent, fragmentMap);
			
			GoogleMap map = fragmentMap.getMap();
			if (map!=null)
				map.setMyLocationEnabled(true);

		}
		fts.commit();
		//invalidateOptionsMenu();	
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}
	
//	  public void onStartTrip() {
//			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////	        photoUri = Utils.getCameraOutputMediaFileUri(Utils.MEDIA_TYPE_IMAGE, "JoanneTest", "TubmlrSnap");
//	        
//			photoFileFullName = Utils.getCameraOutputMediaFileFullPath(Utils.MEDIA_TYPE_IMAGE, fileDescr, APP_NAME);
//			takenPhotoUri = Utils.getFileUriByName(photoFileFullName);
//	        intent.putExtra(MediaStore.EXTRA_OUTPUT, takenPhotoUri);
//	        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//
//	  }
}
