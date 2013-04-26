package com.marakana.android.tricoder;

import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LocationFragment extends Fragment {
	private static final String PROVIDER = LocationManager.GPS_PROVIDER;
	private static final int MIN_TIME = 30000; // 30 seconds
	private static final int MIN_DISTANCE = 10; // 10 meters
	private static TextView textLat, textLong, textAlt;
	private LocationManager locationManager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_location, null);
		textLat = (TextView) view.findViewById(R.id.text_lat);
		textLong = (TextView) view.findViewById(R.id.text_long);
		textAlt = (TextView) view.findViewById(R.id.text_alt);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		locationManager = (LocationManager) getActivity().getSystemService(
				Context.LOCATION_SERVICE);
		Location location = locationManager.getLastKnownLocation(PROVIDER);
		updateView(location);
	}

	@Override
	public void onStart() {
		super.onStart();
		locationManager.requestLocationUpdates(PROVIDER, MIN_TIME, MIN_DISTANCE, LISTENER);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		locationManager.removeUpdates(LISTENER);
	}
	
	private static final LocationListener LISTENER = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			updateView(location);
		}

		@Override
		public void onProviderDisabled(String provider) {			
		}
		@Override
		public void onProviderEnabled(String provider) {			
		}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};
	
	private static void updateView(Location location) {
		if (location == null) {
			textLat.setText("Location unknown");
			textLong.setText("");
			textAlt.setText("");
		} else {
			textLat.setText(String.format("%.3f", location.getLatitude()));
			textLong.setText(String.format("%.3f", location.getLongitude()));
			textAlt.setText(String.format("%.0f", location.getAltitude()));
		}
	}
}
