package com.parse.buddyfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.numberverificatoin.NumberVerificationActivity;
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
public class AfterLoginPage extends Activity {

	public static ProfilePictureView userProfilePictureView;
	private TextView userNameView;
	private TextView userLocationView;
	private TextView userGenderView;
	private TextView userDateOfBirthView;
	private TextView userRelationshipView;
	private TextView userLikes;
	public static Bitmap    bitmap;
	private Button logoutButton,sendDataButton;
	ParseObject userInfo;
	ParseUser currentUser;
	String mylikes="",movieLikes="";
	String mresponse;
	String fbId="",name="";
	Map<String, List<String>> map = new HashMap<String, List<String>>();
	static FaceBookUserClass facebookUser ;
	LinearLayout verifyNumberLinearLayout;
	
	UserInfo info;
	
	@SuppressLint("ValidFragment")
	public AfterLoginPage(Context applicationContext) {
		// TODO Auto-generated constructor stub
	}
	public AfterLoginPage() {
		// TODO Auto-generated constructor stub
	}
	private void initialize()
	{
		userProfilePictureView = (ProfilePictureView) findViewById(R.id.userProfilePicture);
		userNameView = (TextView) findViewById(R.id.userName);	
		verifyNumberLinearLayout = (LinearLayout) findViewById(R.id.verifyNumberLinerarLayout);
		userInfo = new ParseObject("UserInfo");
		info = new UserInfo();
		facebookUser = new FaceBookUserClass();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.after_login_page);

		initialize();
		
		verifyNumberLinearLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), NumberVerificationActivity.class);
				startActivity(intent);
			}
		});
	
		Session session = ParseFacebookUtils.getSession();
		if (session != null && session.isOpened()) {
			try {
				makeMeRequest();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	@Override
	public void onResume() {
		super.onResume();

		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			// Check if the user is currently logged
			// and show any cached content
			updateViewsWithProfileInfo();
		} else {
			// If the user is not logged in, go to the
			// activity showing the login view.
			//startLoginActivity();
		}
	}

	private void makeMeRequest() throws JSONException {
		
		Request request = Request.newMeRequest(ParseFacebookUtils.getSession(),
				new Request.GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) {
						if (user != null) {
							fbId=user.getId();

							JSONObject userProfile = new JSONObject();
							try {
								
								userProfile.put("facebookId", user.getId());
								facebookUser.setFacebookId(user.getId());
								
								userProfile.put("name", user.getName());
								info.setFbId(user.getId());
								info.setName(user.getName());
								facebookUser.setFirstName(user.getFirstName());
								
								name=user.getName();
								ParseUser.getCurrentUser().put("name", user.getName());
								/*
								if (user.getLocation().getProperty("name") != null) {
									userProfile.put("location", (String) user
											.getLocation().getProperty("name"));
									facebookUser.setLocation((String) user
											.getLocation().getProperty("name"));
								}
								if (user.getProperty("gender") != null) {
									userProfile.put("gender",
											(String) user.getProperty("gender"));
								
								}
								if (user.getBirthday() != null) {
									userProfile.put("birthday",
											user.getBirthday());
									facebookUser.setBirthdate(user.getBirthday());
								}
								*/
								

								 currentUser = ParseUser
										.getCurrentUser();
								currentUser.put("profile", userProfile);
								currentUser.saveInBackground();
								Log.d("save","online");

								
							} catch (JSONException e) {
								Log.d(BuddyFinderApplication.TAG,
										"Error parsing returned user data.");
							}

						} else if (response.getError() != null) {
							if ((response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_RETRY)
									|| (response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) {
								Log.d(BuddyFinderApplication.TAG,
										"The facebook session was invalidated.");
								onLogoutButtonClicked();
							} else {
								Log.d(BuddyFinderApplication.TAG,
										"Some other error: "
												+ response.getError()
														.getErrorMessage());
							}
						}
					}
				});
		request.executeAsync();
		
		
		
		Request myRequest = Request.newGraphPathRequest(ParseFacebookUtils.getSession(), "me/likes",
				new Request.Callback() {
					
					@Override
					public void onCompleted(Response response) {
						// TODO Auto-generated method stub
						try
						{
						JSONObject json = new JSONObject(response.getRawResponse());
						JSONArray jarray = json.getJSONArray("data");
						
						for(int i = 0; i < jarray.length(); i++){
						  JSONObject data = jarray.getJSONObject(i);
						  mylikes +=data.get("name").toString()+"\n";
						  if(map.containsKey(data.get("category").toString()))
						  {
							  map.get(data.get("category").toString()).add(data.get("name").toString());
						  }
						  else{
							  List l= new ArrayList();
							  l.add(data.get("name").toString());
							  map.put(data.get("category").toString(),l);
						  }
						  
						}
						
						
						}
						catch (JSONException e)
						{
							Log.d("Json exception ", "exception thrown");
						}
						
						userInfo.put("fbId", fbId);
						Log.d("fbId",fbId);
						userInfo.put("name", name);
						
						userInfo.put("likes", mylikes);
						userInfo.saveInBackground();
						
						updateViewsWithProfileInfo();
						
						Log.d("my request", response.getRawResponse());
					}
				});
		myRequest.executeAsync();
		
		Request movieRequest = Request.newGraphPathRequest(ParseFacebookUtils.getSession(), "me/movies",
				new Request.Callback() {
					
					@Override
					public void onCompleted(Response response) {
						// TODO Auto-generated method stub
						try
						{
						JSONObject json = new JSONObject(response.getRawResponse());
						JSONArray jarray = json.getJSONArray("data");
						
						for(int i = 0; i < jarray.length(); i++){
						  JSONObject data = jarray.getJSONObject(i);
						  movieLikes +=data.get("name").toString()+"\n";				  
						 
						  
						}
						
						}
						catch (JSONException e)
						{
							Log.d("Json exception ", "exception thrown");
						}
						
							Log.d("movie request",movieLikes);
							ParseObject trial=new ParseObject("information");
							trial.put("fbId", fbId);
							trial.put("name", name);
							trial.saveInBackground();
					}
				});
		movieRequest.executeAsync();
		
	

	}

	private void updateViewsWithProfileInfo() {
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser.get("profile") != null) {
			JSONObject userProfile = currentUser.getJSONObject("profile");
			try {
				if (userProfile.getString("facebookId") != null) {
					String facebookId = userProfile.get("facebookId")
							.toString();
					userProfilePictureView.setProfileId(facebookId);
					ImageView fbImage = ((ImageView)userProfilePictureView.getChildAt(0));
					bitmap  = ((BitmapDrawable) fbImage.getDrawable()).getBitmap();
					
				
					
				} else {
					// Show the default, blank user profile picture
					userProfilePictureView.setProfileId(null);
				}
				if (userProfile.getString("name") != null) {
					userNameView.setText(userProfile.getString("name"));
				} else {
					userNameView.setText("");
				}
				
				
			} catch (JSONException e) {
				Log.d(BuddyFinderApplication.TAG,
						"Error parsing saved user data.");
				
			}
			

		}
	}

	private void onLogoutButtonClicked() {
		// Log the user out
		ParseUser.logOut();

		// Go to the login view
		//startLoginActivity();
	}

	
	
	
}
