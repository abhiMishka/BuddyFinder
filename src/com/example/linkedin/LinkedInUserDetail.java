package com.example.linkedin;

import com.parse.buddyfinder.LoginActivity;
import com.parse.buddyfinder.SettingsPage;
import com.parse.integratingfacebooktutorial.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class LinkedInUserDetail extends Fragment {

	TextView firstName,lastName;
	
	Context mContext;
	
	public LinkedInUserDetail(Context ctx) {
		mContext = ctx; 
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.linkedin_user_info, container, false);
		
		getActivity().setTitle("Linkedin Profile");
		
		firstName = (TextView) rootView.findViewById(R.id.firstName);
		lastName = (TextView) rootView.findViewById(R.id.lastName);
		
		firstName.setText(SettingsPage.linkedInUser.getFirstName());
		lastName.setText(SettingsPage.linkedInUser.getLastName());
		
		return rootView;
		
		
	}

	
}
