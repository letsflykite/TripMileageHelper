package com.androidcamp.tripmileage.fragments;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androidcamp.tripmileage.R;
import com.androidcamp.tripmileage.adapters.PhotosAdapter;
import com.androidcamp.tripmileage.models.Photo;
import com.androidcamp.tripmileage.utils.Utils;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class PhotosFragment extends Fragment {
	private static final int TAKE_PHOTO_CODE = 1;
	private static final int PICK_PHOTO_CODE = 2;
	private static final int CROP_PHOTO_CODE = 3;
	private static final int POST_PHOTO_CODE = 4;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
	private static final String APP_NAME = "TripMileageHelper";
	
	private Uri takenPhotoUri;
	private Bitmap photoBitmap;
	private String fileDescr = "JoanneTest";
//	TumblrClient client;
	ArrayList<Photo> photos;
	PhotosAdapter photosAdapter;
	ListView lvPhotos;
	private String photoFileFullName;
	
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_photos, container, false);
		setHasOptionsMenu(true);
		return view;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
//		client = ((TumblrClient) TumblrClient.getInstance(
//				TumblrClient.class, getActivity()));
		photos = new ArrayList<Photo>();
		photosAdapter = new PhotosAdapter(getActivity(), photos);
		lvPhotos = (ListView) getView().findViewById(R.id.lvPhotos);
		lvPhotos.setAdapter(photosAdapter);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		reloadPhotos();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.photos, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.action_take_photo:
			{
				// Take the user to the camera app
				// create Intent to take a picture and return control to the calling application
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		        photoUri = Utils.getCameraOutputMediaFileUri(Utils.MEDIA_TYPE_IMAGE, "JoanneTest", "TubmlrSnap");
		        
				photoFileFullName = Utils.getCameraOutputMediaFileFullPath(Utils.MEDIA_TYPE_IMAGE, fileDescr, APP_NAME);
				takenPhotoUri = Utils.getFileUriByName(photoFileFullName);
		        intent.putExtra(MediaStore.EXTRA_OUTPUT, takenPhotoUri);
		        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}
			break;
			case R.id.action_use_existing:
			{
				// Take the user to the gallery app
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == TAKE_PHOTO_CODE) {
				// Extract the photo that was just taken by the camera
//				Uri takenPhotoUri = Utils.getFileUriByName(photoFileFullName);
				
				// show the photo
				Photo photo = new Photo();
				photosAdapter.add(photo);
				// Call the method below to trigger the cropping
				cropPhoto(takenPhotoUri);
			} else if (requestCode == PICK_PHOTO_CODE) {
				// Extract the photo that was just picked from the gallery
				
				// Call the method below to trigger the cropping
				// cropPhoto(photoUri)
			} else if (requestCode == CROP_PHOTO_CODE) {
				photoBitmap = data.getParcelableExtra("data");
				startPreviewPhotoActivity();
			} else if (requestCode == POST_PHOTO_CODE) {
				reloadPhotos();
			}
		}
	}
	
	private void reloadPhotos() {
//		client.getTaggedPhotos(new JsonHttpResponseHandler() {
//			@Override
//			public void onSuccess(int code, JSONObject response) {
//				try {
//					JSONArray photosJson = response.getJSONArray("response");
//					photosAdapter.clear();
//					photosAdapter.addAll(Photo.fromJson(photosJson));
//					mergeUserPhotos(); // bring in user photos
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//
//			@Override
//			public void onFailure(Throwable arg0) {
//				Log.d("DEBUG", arg0.toString());
//			}
//		});
	}
	
	private void cropPhoto(Uri photoUri) {
		//call the standard crop action intent (the user device may not support it)
		Intent cropIntent = new Intent("com.android.camera.action.CROP");
		//indicate image type and Uri
		cropIntent.setDataAndType(photoUri, "image/*");
		//set crop properties
		cropIntent.putExtra("crop", "true");
		//indicate aspect of desired crop
		cropIntent.putExtra("aspectX", 1);
		cropIntent.putExtra("aspectY", 1);
		//indicate output X and Y
		cropIntent.putExtra("outputX", 300);
		cropIntent.putExtra("outputY", 300);
		//retrieve data on return
		cropIntent.putExtra("return-data", true);
		//start the activity - we handle returning in onActivityResult
		startActivityForResult(cropIntent, CROP_PHOTO_CODE);
	}
	
	private String getFileUri(Uri mediaStoreUri) {
		String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(mediaStoreUri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String fileUri = cursor.getString(columnIndex);
        cursor.close();
        
        return fileUri;
	}
	
	private void startPreviewPhotoActivity() {
//		Intent i = new Intent(getActivity(), PreviewPhotoActivity.class);
//        i.putExtra("photo_bitmap", photoBitmap);
//        startActivityForResult(i, POST_PHOTO_CODE);
	}
	
	
	// Loads feed of users photos and merges them with the tagged photos
	// Used to avoid an API limitation where user photos arent returned in tagged
//	private void mergeUserPhotos() {
//		client.getUserPhotos(new JsonHttpResponseHandler() {
//			@Override
//			public void onSuccess(int code, JSONObject response) {
//				try {
//					JSONArray photosJson = response.getJSONObject("response").getJSONArray("posts");
//					for (Photo p : Photo.fromJson(photosJson)) {
//						if (p.isSnap()) { photosAdapter.add(p); }
//					}
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				photosAdapter.sort(new Comparator<Photo>() {
//					@Override
//					public int compare(Photo a, Photo b) {
//						return Long.valueOf(b.getTimestamp()).compareTo(a.getTimestamp());
//					}
//				});
//			}
//
//			@Override
//			public void onFailure(Throwable arg0) {
//				Log.d("DEBUG", arg0.toString());
//			}
//		});
//	}
}
