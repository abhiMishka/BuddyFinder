package com.parse.buddyfinder;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.parse.integratingfacebooktutorial.R;

@SuppressLint("ValidFragment")
public class FilterClass extends Fragment {

	private Button doneButton;
	private CheckBox movie, coffee, outstation,other;
	
	
	@SuppressLint("ValidFragment")
	public FilterClass(Context applicationContext) {
		// TODO Auto-generated constructor stub
	}
	public FilterClass() {
		// TODO Auto-generated constructor stub
	}
	private void initialize(View v)
	{
		doneButton = (Button) v.findViewById(R.id.filterButton);
		movie = (CheckBox) v.findViewById(R.id.moviesCheckbox);
		coffee = (CheckBox) v.findViewById(R.id.coffeCheckbox);
		outstation = (CheckBox) v.findViewById(R.id.outstationCheckbox);
		other = (CheckBox) v.findViewById(R.id.otherCheckbox);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		
		View rootView = inflater.inflate(R.layout.filters_page, container, false);

		initialize(rootView);
	
		
		
		

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
	
}
