
package com.parse.buddyfinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import retrofit.RestAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.apis.UserServiceApi;
import com.example.model.User;
import com.example.model.Utils;
import com.example.numberverificatoin.NumberVerificationActivity;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.integratingfacebooktutorial.R;



public class LoginActivity extends Activity {

	private Button loginButton,linkedInLoginButton,sendData,sendLinkedInData,numberVerficationButton;
	private Dialog progressDialog;
	public static User myUser;
	 static UserServiceApi userService = new RestAdapter.Builder()
     .setEndpoint(Utils.SERVER_URL)
     .build()
     .create(UserServiceApi.class);
	
	
	public void initialize()
	{
		
		
		loginButton = (Button) findViewById(R.id.loginButton);
		myUser = new User();
		/*
		linkedInLoginButton = (Button) findViewById(R.id.linkedinloginButton);
		sendData = (Button) findViewById(R.id.sendButton);
		sendLinkedInData = (Button) findViewById(R.id.sendLinkedInButton);
		numberVerficationButton = (Button) findViewById(R.id.numberVerficationButton);
		linkedInUser = new LinkedInUserClass();
		*/

	}
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login_page);
		initialize();
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().
	        		permitAll().build(); StrictMode.setThreadPolicy(policy);
	        }

		
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onLoginButtonClicked();
			}
		});
		
		
		/*
		sendData.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendDataToServer();
			}
		});
		
		sendLinkedInData.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendLinkedInDataToServer();
			}
		});
		
		numberVerficationButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, Cognalys_MainActivity.class);
				startActivity(intent);
			}
		});

		
		
		linkedInLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				linkedInLogin();
			}
		});
		
		*/
		
		
		// Check if there is a currently logged in user
				// and they are linked to a Facebook account.
				/*
				ParseUser currentUser = ParseUser.getCurrentUser();
				if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
					// Go to the user info activity
					showUserDetailsActivity();
				}
				*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}

	private void onLoginButtonClicked() {
		LoginActivity.this.progressDialog = ProgressDialog.show(
				LoginActivity.this, "", "Logging in...", true);
		List<String> permissions = Arrays.asList( "public_profile","user_about_me",
				"user_relationships", "user_birthday", "user_location",ParseFacebookUtils.Permissions.User.LIKES,
				ParseFacebookUtils.Permissions.User.STATUS,ParseFacebookUtils.Permissions.User.GROUPS);
		
		ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException err) {
				//LoginActivity.this.progressDialog.dismiss();
				if (user == null) {
					Log.d(BuddyFinderApplication.TAG,
							"Uh oh. The user cancelled the Facebook login.");
					showHomePageActivity();
				} else if (user.isNew()) {
					Log.d(BuddyFinderApplication.TAG,
							"User signed up and logged in through Facebook!");
					showHomePageActivity();
					
				} else {
					Log.d(BuddyFinderApplication.TAG,
							"User logged in through Facebook!");
					getUserInfo();
					
				}
			}
			
		});
	}
	
	public void getUserInfo()
	{
		Request request = Request.newMeRequest(ParseFacebookUtils.getSession(),
				new Request.GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) {
						if (user != null) {
							myUser.setId(user.getId());

							myUser.setFirstName(user.getFirstName());
							myUser.setLastName(user.getLastName());
							ParseUser.getCurrentUser().put("name", user.getName());
							//if (user.getLocation().getProperty("name") != null)
							{
								/*
								myUser.se.put("location", (String) user
										.getLocation().getProperty("name"));
								facebookUser.setLocation((String) user
										.getLocation().getProperty("name"));
							*/
							}
							//if (user.getProperty("gender") != null) 
							{
								/*
								userProfile.put("gender",
										(String) user.getProperty("gender"));
										*/
							
							}
							//if (user.getBirthday() != null)
							{								
								myUser.setDateOfBirth(user.getBirthday());
							}
							
							myUser.setPhoneNumber("8447035110");
							myUser.setMemberOfEvents(new ArrayList<String>());
							
						//	makeServerUserRequest();
							showAfterLoginPageActivity();

						} else if (response.getError() != null) {
							if ((response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_RETRY)
									|| (response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) {
								Log.d(BuddyFinderApplication.TAG,
										"The facebook session was invalidated.");
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
	}
	
	private void makeServerUserRequest()
	{
		
		new Thread( new Runnable() {
			@Override
			public void run() {
				userService.addUser(myUser);
				myUser=userService.findById(myUser.getId());
				showAfterLoginPageActivity();
				LoginActivity.this.progressDialog.dismiss();
			}
		}).start();
		
		
		
	}
	private void showUserDetailsActivity() {
		Intent intent = new Intent(this, UserDetailsActivity.class);
		startActivity(intent);
	}
	
	private void showNumberVerificationActivity() {
		Intent intent = new Intent(this, NumberVerificationActivity.class);
		startActivity(intent);
	}
	
	
	private void showHomePageActivity() {		
		Intent intent = new Intent(this, HomePageActivity.class);
		startActivity(intent);
	}
	
	private void showAfterLoginPageActivity() {		
		Intent intent = new Intent(this, AfterLoginPage.class);
		startActivity(intent);
	}
	
	
	
	private void sendDataToServer()
	{
		  // Create a new HttpClient and Post Header
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

	/*
	private void sendLinkedInDataToServer()
	{
		Toast.makeText(getApplicationContext(), linkedInUser.getFirstName(), Toast.LENGTH_LONG).show();
		  // Create a new HttpClient and Post Header
	    final HttpClient httpclient = new DefaultHttpClient();
	    final HttpPost httppost = new HttpPost("http://192.168.49.250:8080/userInfo");

	    new Thread(new Runnable() {
			public void run() {
				
			
	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("facebook_firstName", linkedInUser.getFirstName()));
	        nameValuePairs.add(new BasicNameValuePair("facebook_lastName", linkedInUser.getLastName()));
	        nameValuePairs.add(new BasicNameValuePair("facebook_dateOfBirth","18/02/1993"));
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
	*/
}
