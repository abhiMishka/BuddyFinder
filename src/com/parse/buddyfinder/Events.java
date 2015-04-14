package com.parse.buddyfinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.userInfoClasses.FaceBookUserClass;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.integratingfacebooktutorial.R;

@SuppressLint("ValidFragment")
public class Events extends Fragment {

	public static ProfilePictureView userProfilePictureView;
	private TextView userNameView;
	private TextView userLocationView;
	private TextView userGenderView;
	private TextView userDateOfBirthView;
	private TextView userRelationshipView;
	private TextView userLikes;
	public static Bitmap    bitmap;
	private Button createEventButton;
	ParseObject userInfo;
	ParseUser currentUser;
	String mylikes="",movieLikes="";
	String mresponse;
	String fbId="",name="";
	Map<String, List<String>> map = new HashMap<String, List<String>>();
	static FaceBookUserClass facebookUser ;
	
	UserInfo info;
	
	@SuppressLint("ValidFragment")
	public Events(Context applicationContext) {
		// TODO Auto-generated constructor stub
	}
	public Events() {
		// TODO Auto-generated constructor stub
	}
	private void initialize(View v)
	{
		createEventButton = (Button) v.findViewById(R.id.createEventButton);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		
		View rootView = inflater.inflate(R.layout.events_page, container, false);

		initialize(rootView);
	
		
		
		

		createEventButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Fragment fragment = null;
				fragment = new CreateEvent();
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
