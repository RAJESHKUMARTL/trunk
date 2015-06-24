package com.exenta.timesheet;

import java.util.ArrayList;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.model.LatLng;

public class GeoConstants {
	 /**
     * Geofence Coordinates
     */
    ArrayList<LatLng> mGeofenceCoordinates;
    ArrayList<Integer> mGeofenceRadius;
    ArrayList<Geofence> mGeofences;
   /* 
	public GeoConstants(ArrayList<LatLng> mGeofenceCoordinates,
			ArrayList<Integer> mGeofenceRadius, ArrayList<Geofence> mGeofences) {
		super();
		this.mGeofenceCoordinates = mGeofenceCoordinates;
		this.mGeofenceRadius = mGeofenceRadius;
		this.mGeofences = mGeofences;
	}
    */
    
	
    public  ArrayList<Geofence> Geofence(){
    
    mGeofenceCoordinates = new ArrayList<LatLng>();
    mGeofenceRadius = new ArrayList<Integer>();  
    mGeofences = new ArrayList<Geofence>();	
    
    mGeofenceCoordinates.add(new LatLng(8.5563532, 76.8821253));
    mGeofenceRadius.add(10);
    
   mGeofences.add(new Geofence.Builder()
    .setRequestId("Exenta")
    // The coordinates of the center of the geofence and the radius in meters.
    .setCircularRegion(mGeofenceCoordinates.get(0).latitude, mGeofenceCoordinates.get(0).longitude, mGeofenceRadius.get(0).intValue()) 
    .setExpirationDuration(Geofence.NEVER_EXPIRE)
    // Required when we use the transition type of GEOFENCE_TRANSITION_DWELL
    .setLoiteringDelay(30000) 
    .setTransitionTypes(
            Geofence.GEOFENCE_TRANSITION_ENTER
                | Geofence.GEOFENCE_TRANSITION_DWELL
                | Geofence.GEOFENCE_TRANSITION_EXIT).build());
	
return	mGeofences;
}
}