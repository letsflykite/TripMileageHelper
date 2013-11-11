package com.androidcamp.tripmileage.fragments;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.androidcamp.tripmileage.R;
import com.androidcamp.tripmileage.adapters.PhotosAdapter;
import com.androidcamp.tripmileage.models.Photo;
import com.androidcamp.tripmileage.utils.Utils;

public class LogFragment extends Fragment implements LocationListener{
	private Uri takenPhotoUri;
	private Bitmap photoBitmap;
	private String fileDescr = "JoanneTest";
//	TumblrClient client;
	ArrayList<Photo> photos;
	PhotosAdapter photosAdapter;
	ListView lvPhotos;
	private String photoFileFullName;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
	private static final String APP_NAME = "TripMileageHelper";
	
	
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_log, container, false);
//		setHasOptionsMenu(true);
		return view;
	}
	
	  private TextView latituteField;
	  private TextView longitudeField;
	  private LocationManager locationManager;
	  private String provider;

	  
	/** Called when the  is first created. */

	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
//	    setContentView(R.layout.main);

	  }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Button startTripButton = (Button) getView().findViewById(R.id.btnStartTrip);
		Button endTripButton = (Button) getView().findViewById(R.id.btnEndTrip);
		startTripButton.setOnClickListener(new View.OnClickListener() {
			@Override
	        public void onClick(View v) {onStartTrip();};
		});
		
	    latituteField = (TextView) getView().findViewById(R.id.TextView02);
	    longitudeField = (TextView) getView().findViewById(R.id.TextView04);

	    // Get the location manager
	    locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
	    
	    boolean enabled = locationManager
	    		  .isProviderEnabled(LocationManager.GPS_PROVIDER);

		// check if enabled and if not send user to the GSP settings
		// Better solution would be to display a dialog and suggesting to 
		// go to the settings
		if (!enabled) {
		  Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		  startActivity(intent);
		} 
	    		
		    // Define the criteria how to select the locatioin provider -> use
		    // default
		    Criteria criteria = new Criteria();
		    provider = locationManager.getBestProvider(criteria, false);
		    Location location = locationManager.getLastKnownLocation(provider);

		    // Initialize the location fields
		    if (location != null) {
		      System.out.println("Provider " + provider + " has been selected.");
		      onLocationChanged(location);
		    } else {
		      latituteField.setText("Location not available");
		      longitudeField.setText("Location not available");
		    }

	  }
	  /* Request updates at startup */
//	  @Override
//	  protected void onResume() {
//	    super.onResume();
//	    locationManager.requestLocationUpdates(provider, 400, 1, this);
//	  }

	  /* Remove the locationlistener updates when Activity is paused */
//	  @Override
//	  protected void onPause() {
//	    super.onPause();
//	    locationManager.removeUpdates(this);
//	  }

	  @Override
	  public void onLocationChanged(Location location) {
	    int lat = (int) (location.getLatitude());
	    int lng = (int) (location.getLongitude());
	    latituteField.setText(String.valueOf(lat));
	    longitudeField.setText(String.valueOf(lng));
	  }

	  @Override
	  public void onStatusChanged(String provider, int status, Bundle extras) {
	    // TODO Auto-generated method stub

	  }

	  @Override
	  public void onProviderEnabled(String provider) {
//	    Toast.makeText(this, "Enabled new provider " + provider,
//	        Toast.LENGTH_SHORT).show();

	  }

	  @Override
	  public void onProviderDisabled(String provider) {
//	    Toast.makeText(this, "Disabled provider " + provider,
//	        Toast.LENGTH_SHORT).show();
	  }
	  
	  public void onStartTrip() {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//	        photoUri = Utils.getCameraOutputMediaFileUri(Utils.MEDIA_TYPE_IMAGE, "JoanneTest", "TubmlrSnap");
	        
			photoFileFullName = Utils.getCameraOutputMediaFileFullPath(Utils.MEDIA_TYPE_IMAGE, fileDescr, APP_NAME);
			takenPhotoUri = Utils.getFileUriByName(photoFileFullName);
	        intent.putExtra(MediaStore.EXTRA_OUTPUT, takenPhotoUri);
	        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

	  }
	  public void onEndTrip() {
		  
	  }
}
