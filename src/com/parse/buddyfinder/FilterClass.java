package com.parse.buddyfinder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.parse.integratingfacebooktutorial.R;

@SuppressLint("ValidFragment")
public class FilterClass extends Fragment implements OnItemClickListener {

	private Button doneButton;
	private CheckBox movie, coffee, outstation,other;
	AutoCompleteTextView autoCompView;
	private static final String LOG_TAG = "Google Places Autocomplete";
	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private static final String OUT_JSON = "/json";

	private static final String API_KEY = "AIzaSyC7HhGk_SoeejaqPWnDkEiuQxJRoYIr1WI";
	
	
	@SuppressLint("ValidFragment")
	public FilterClass(Context applicationContext) {
		// TODO Auto-generated constructor stub
	}
	public FilterClass() {
		// TODO Auto-generated constructor stub
	}
	private void initialize(View v)
	{
		doneButton = (Button) v.findViewById(R.id.okButton);
		movie = (CheckBox) v.findViewById(R.id.moviesCheckbox);
		coffee = (CheckBox) v.findViewById(R.id.coffeCheckbox);
		outstation = (CheckBox) v.findViewById(R.id.outstationCheckbox);
		other = (CheckBox) v.findViewById(R.id.otherCheckbox);
		autoCompView = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteTextView);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		
		View rootView = inflater.inflate(R.layout.filters_page, container, false);

		initialize(rootView);
	
		

		autoCompView.setAdapter(new GooglePlacesAutocompleteAdapter(getActivity().getApplicationContext(), R.layout.autocomplete_list_item));
		autoCompView.setOnItemClickListener(this);
		
		

		doneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(movie.isChecked())
				{
					HomePageActivity.checked.set(0, true);
				}
				else{
					HomePageActivity.checked.set(0, false);
				}
					
				if(coffee.isChecked())
				{
					HomePageActivity.checked.set(1, true);
				}
				else
				{
					HomePageActivity.checked.set(1, false);
				}
				if(outstation.isChecked())
				{
					HomePageActivity.checked.set(2, true);
				}
				else
				{
					HomePageActivity.checked.set(2, false);
				}
				if(other.isChecked())
				{
					HomePageActivity.checked.set(3, true);
				}
				else
				{
					HomePageActivity.checked.set(3, false);
				}
				Fragment fragment = null;
				fragment = new HomePageContent();
				Bundle args = new Bundle();
				fragment.setArguments(args);

				// Insert the fragment by replacing any existing fragment
				FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
				fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment)
				.commit();
			}
		});

		
		return rootView;
	}
	
	public void sendData()
	{
		
	}

	@Override
	public void onResume() {
		super.onResume();

		
	}

		
	

	private void startLoginActivity() {
		Intent intent = new Intent(getActivity(), LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		String str = (String) parent.getItemAtPosition(position);
		Toast.makeText(getActivity().getApplicationContext(), str, Toast.LENGTH_SHORT).show();
		
	}
	
	public static ArrayList autocomplete(String input) {
		ArrayList resultList = null;

		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();
		try {
			StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
			sb.append("?key=" + API_KEY);
			sb.append("&components=country:in");
			sb.append("&input=" + URLEncoder.encode(input, "utf8"));

			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			// Load the results into a StringBuilder
			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
			}
		} catch (MalformedURLException e) {
			Log.e(LOG_TAG, "Error processing Places API URL", e);
			return resultList;
		} catch (IOException e) {
			Log.e(LOG_TAG, "Error connecting to Places API", e);
			return resultList;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		try {			
			// Create a JSON object hierarchy from the results
			JSONObject jsonObj = new JSONObject(jsonResults.toString());
			JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

			// Extract the Place descriptions from the results
			resultList = new ArrayList(predsJsonArray.length());
			for (int i = 0; i < predsJsonArray.length(); i++) {
				System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
				System.out.println("============================================================");
				resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
			}
		} catch (JSONException e) {
			Log.e(LOG_TAG, "Cannot process JSON results", e);
		}

		return resultList;
	}
	
	class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
		private ArrayList resultList;

		public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId);
		}

		@Override
		public int getCount() {
			return resultList.size();
		}

		@Override
		public String getItem(int index) {
			return (String) resultList.get(index);
		}

		@Override
		public Filter getFilter() {
			Filter filter = new Filter() {
				@Override
				protected FilterResults performFiltering(CharSequence constraint) {
					FilterResults filterResults = new FilterResults();
					if (constraint != null) {
						// Retrieve the autocomplete results.
						resultList = autocomplete(constraint.toString());

						// Assign the data to the FilterResults
						filterResults.values = resultList;
						filterResults.count = resultList.size();
					}
					return filterResults;
				}

				@Override
				protected void publishResults(CharSequence constraint, FilterResults results) {
					if (results != null && results.count > 0) {
						notifyDataSetChanged();
					} else {
						notifyDataSetInvalidated();
					}
				}
			};
			return filter;
		}
	}
	
}
