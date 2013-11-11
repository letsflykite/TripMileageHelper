package com.androidcamp.tripmileage.utils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;


public class Utils {
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	
	public static Uri getCameraOutputMediaFileUri(int type, String fileDescr, String appName){
	    return Uri.fromFile(getCameraOutputMediaFile(type, fileDescr, appName));
	}
	
	public static File getCameraOutputMediaFile(int type, String fileDescr, String appName){
	    String fileName = getCameraOutputMediaFileFullPath(type, fileDescr,
				appName);
	    
	    File mediaFile = new File(fileName);
	    return mediaFile;
	}

	public static String getCameraOutputMediaFileFullPath(int type,
			String fileDescr, String appName) {
		// check the SDCard is mounted ?
	    // using Environment.getExternalStorageState()

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), appName);
	    
	    // This location is to share the created images 
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()&& !mediaStorageDir.mkdirs()){
	        Log.d(appName, "failed to create directory");
	        return null;
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String fileName = null;

	    if (type == MEDIA_TYPE_IMAGE){
	    	fileName = mediaStorageDir.getPath() + File.separator +
	    	        "IMG_"+ timeStamp + fileDescr+ ".jpg";
	    	
	    } else if(type == MEDIA_TYPE_VIDEO) {
	    	fileName = mediaStorageDir.getPath() + File.separator +
	        "VID_"+ timeStamp + fileDescr+".mp4";
	    } else {
	        return null;
	    }

	    if (fileName == null) return null;
		return fileName;
	}
	public static boolean hasNetworkConnection(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null)
	            return false;
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null
				&& activeNetwork.isConnectedOrConnecting();

		return isConnected;
	}

	public static Uri getFileUriByName(String fileFullName) {
		File file = new File(fileFullName);
		return Uri.fromFile (file);
	}
	
//	public static Uri getFileNameByUri(Uri fileUri) {
//		File file = new File(fileUri);
//		return file.getAbsolutePath();
//	}

	/*
	public static ArrayList<> removeDuplicates(ArrayList<> l) {
	    // ... the list is already populated
	    Set<> s = new TreeSet<>(new Comparator<>() {

	        @Override
	        public int compare(Tweet o1, Tweet o2) {
	            // ... compare the two object according to your requirements
//	        	return (int)(o1.getTweetId()-o2.getTweetId());
	        }
	    });
	    s.addAll(l);
	    ArrayList<> res = new ArrayList<>(s);
	    return res;
	}
	*/
}
