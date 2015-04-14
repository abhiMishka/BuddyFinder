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
public class UserDetailsActivity extends Fragment {

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
	
	UserInfo info;
	
	@SuppressLint("ValidFragment")
	public UserDetailsActivity(Context applicationContext) {
		// TODO Auto-generated constructor stub
	}
	public UserDetailsActivity() {
		// TODO Auto-generated constructor stub
	}
	private void initialize(View v)
	{
		userProfilePictureView = (ProfilePictureView) v.findViewById(R.id.userProfilePicture);
		userNameView = (TextView) v.findViewById(R.id.userName);
		userLocationView = (TextView) v.findViewById(R.id.userLocation);
		userGenderView = (TextView) v.findViewById(R.id.userGender);
		userDateOfBirthView = (TextView) v.findViewById(R.id.userDateOfBirth);
		userRelationshipView = (TextView) v.findViewById(R.id.userRelationship);
		userLikes = (TextView) v.findViewById(R.id.userLikes);
		//logoutButton = (Button) v.findViewById(R.id.logoutButton);
		sendDataButton = (Button) v.findViewById(R.id.sendDataButton);
		userInfo = new ParseObject("UserInfo");
		info = new UserInfo();
		facebookUser = new FaceBookUserClass();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		
		View rootView = inflater.inflate(R.layout.userdetails, container, false);

		initialize(rootView);
	
		Session session = ParseFacebookUtils.getSession();
		if (session != null && session.isOpened()) {
			try {
				makeMeRequest();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		

		/*
		logoutButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onLogoutButtonClicked();
			}
		});
*/
		sendDataButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendData();
			}
		});

		
		return rootView;
	}
	
	public void sendData()
	{
			final HttpClient httpclient = new DefaultHttpClient();
		    final HttpPost httppost = new HttpPost("http://192.168.49.250:8080/userInfo");

		    new Thread(new Runnable() {
				public void run() {
					
				
		    try {
		        // Add your data
		        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		        nameValuePairs.add(new BasicNameValuePair("facebook_firstName", UserDetailsActivity.facebookUser.getFirstName()));
		        nameValuePairs.add(new BasicNameValuePair("facebook_lastName", UserDetailsActivity.facebookUser.getLastName()));
		        nameValuePairs.add(new BasicNameValuePair("facebook_dateOfBirth", UserDetailsActivity.facebookUser.getBirthdate()));
		        nameValuePairs.add(new BasicNameValuePair("facebook_phoneNo", "8447035110"));
		        nameValuePairs.add(new BasicNameValuePair("facebook_likes", "IIITD"));
		        nameValuePairs.add(new BasicNameValuePair("facebook_facebookId",  UserDetailsActivity.facebookUser.getFacebookId()));
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		        // Execute HTTP Post Request
		        HttpResponse response = httpclient.execute(httppost);
		        
		    } catch (ClientProtocolException e) {
		        // TODO Auto-generated catch block
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		    }
				}
		    }).start();
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
			startLoginActivity();
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
								//Log.d("Response", response.toString());
								// Populate the JSON object
								userProfile.put("facebookId", user.getId());
								facebookUser.setFacebookId(user.getId());
								
								userProfile.put("name", user.getName());
								info.setFbId(user.getId());
								info.setName(user.getName());
								facebookUser.setFirstName(user.getFirstName());
								
								name=user.getName();
								ParseUser.getCurrentUser().put("name", user.getName());
								if (user.getLocation().getProperty("name") != null) {
									userProfile.put("location", (String) user
											.getLocation().getProperty("name"));
									facebookUser.setLocation((String) user
											.getLocation().getProperty("name"));
								//	ParseUser.getCurrentUser().put("location", (String) user
								//			.getLocation().getProperty("name"));
								}
								if (user.getProperty("gender") != null) {
									userProfile.put("gender",
											(String) user.getProperty("gender"));
								//	ParseUser.getCurrentUser().put("gender", (String) user
								//			.getLocation().getProperty("gender"));
								}
								if (user.getBirthday() != null) {
									userProfile.put("birthday",
											user.getBirthday());
									facebookUser.setBirthdate(user.getBirthday());
									//ParseUser.getCurrentUser().put("birthday", user.getBirthday());
								}
								
								

								 currentUser = ParseUser
										.getCurrentUser();
								currentUser.put("profile", userProfile);
								currentUser.saveInBackground();
								Log.d("save","online");

								// Show the user info
								
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
						  //Log.d("for loop",data.get("category").toString()+" : "+data.get("name").toString());
						  
						}
						/*
						 Iterator it = map.entrySet().iterator();
						    while (it.hasNext()) {
						        Map.Entry myDataEntry = (Map.Entry)it.next();
						        Log.d("map : ",myDataEntry.getKey() + " = " + myDataEntry.getValue());
						        it.remove(); // avoids a ConcurrentModificationException
						    }
						    */
						
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
						//Log.d("save","online");
						
						updateViewsWithProfileInfo();
						
						Log.d("my request", response.getRawResponse());
						//response.
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
						//response.
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
					
					/*
					String URL = "https://graph.facebook.com/" + userProfile.get("facebookId") + "?fields=cover&access_token=" +
					ParseFacebookUtils.getSession().getAccessToken();

					String finalCoverPhoto = "";

					try {

					    HttpClient hc = new DefaultHttpClient();
					    HttpGet get = new HttpGet(URL);
					    HttpResponse rp = hc.execute(get);

					    if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					        String result = EntityUtils.toString(rp.getEntity());

					        JSONObject JODetails = new JSONObject(result);

					        if (JODetails.has("cover")) {
					            String getInitialCover = JODetails.getString("cover");

					            if (getInitialCover.equals("null")) {
					                finalCoverPhoto = null;
					        } else {
					            JSONObject JOCover = JODetails.optJSONObject("cover");

					            if (JOCover.has("source"))  {
					                finalCoverPhoto = JOCover.getString("source");
					            } else {
					                finalCoverPhoto = null;
					            }
					        }
					    } else {
					        finalCoverPhoto = null;
					    }
					}} catch (Exception e) {
						Log.d("finalCoverpic : ","exception");
					}
					Log.d("finalCoverpic : ",finalCoverPhoto);
					*/
					
					
					
				} else {
					// Show the default, blank user profile picture
					userProfilePictureView.setProfileId(null);
				}
				if (userProfile.getString("name") != null) {
					userNameView.setText(userProfile.getString("name"));
				} else {
					userNameView.setText("");
				}
				if (userProfile.getString("location") != null) {
					userLocationView.setText(userProfile.getString("location"));
				} else {
					userLocationView.setText("");
				}
				if (userProfile.getString("gender") != null) {
					userGenderView.setText(userProfile.getString("gender"));
				} else {
					userGenderView.setText("");
				}
				if (userProfile.getString("birthday") != null) {
					userDateOfBirthView.setText(userProfile
							.getString("birthday"));
				} else {
					userDateOfBirthView.setText("");
				}
				if(mylikes!="")
				{
					userLikes.setText(mylikes);
					facebookUser.setLikes(mylikes.toString());
				}
				/*
				if (userProfile.getString("relationship_status") != null) {
					userRelationshipView.setText(userProfile
							.getString("relationship_status"));
				} else {
					userRelationshipView.setText("");
				}
				*/
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
		startLoginActivity();
	}

	private void startLoginActivity() {
		Intent intent = new Intent(getActivity(), LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
	
}
