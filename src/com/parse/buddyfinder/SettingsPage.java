package com.parse.buddyfinder;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.linkedin.Config;
import com.example.linkedin.LinkedInUserDetail;
import com.example.linkedin.LinkedinDialog;
import com.example.linkedin.LinkedinDialog.OnVerifyListener;
import com.example.numberverificatoin.NumberVerificationActivity;
import com.example.userInfoClasses.LinkedInUserClass;
import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthService;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthServiceFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInRequestToken;
import com.google.code.linkedinapi.schema.Person;
import com.parse.integratingfacebooktutorial.R;


@SuppressLint("ValidFragment")
public class SettingsPage extends Fragment {

	Context mContext;
	LinearLayout mListOfSellers;
	private Button linkedInButton,numberVerificationButton,filterButton;
	
	public SettingsPage(Context ctx) {
		mContext = ctx; 
	}
	
	
	public final static  String OAUTH_CALLBACK_HOST = "litestcalback";
	public final static LinkedInOAuthService oAuthService = LinkedInOAuthServiceFactory
            .getInstance().createLinkedInOAuthService(
                    Config.LINKEDIN_CONSUMER_KEY,Config.LINKEDIN_CONSUMER_SECRET, Config.scopeParams);;
	public final static LinkedInApiClientFactory factory = LinkedInApiClientFactory
			.newInstance(Config.LINKEDIN_CONSUMER_KEY,
					Config.LINKEDIN_CONSUMER_SECRET);;
	LinkedInRequestToken liToken;
	LinkedInApiClient client;
	LinkedInAccessToken accessToken = null;	
	public static LinkedInUserClass linkedInUser;
	
	
	
	public SettingsPage()
	{
		
	}
	
	public void initialize(View rootView)
	{
		linkedInButton = (Button) rootView.findViewById(R.id.linkedinLoginButton);
		numberVerificationButton = (Button) rootView.findViewById(R.id.numberVerificationButton);
		filterButton = (Button) rootView.findViewById(R.id.filterButton);
		linkedInUser = new LinkedInUserClass();
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.settings_page, container, false);
		
		initialize(rootView);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().
	        		permitAll().build(); StrictMode.setThreadPolicy(policy);
	        }
		
		linkedInButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				linkedInLogin();
			}
		});
		
		
		numberVerificationButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showNumberVerificationActivity();
			}
		});
		
		filterButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Fragment fragment2 = new FilterClass(getActivity().getApplicationContext());
				Bundle args2 = new Bundle();
				fragment2.setArguments(args2);

				// Insert the fragment by replacing any existing fragment
				FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
				fragmentManager2.beginTransaction()
				.replace(R.id.content_frame, fragment2)
				.commit();
			}
		});
		
		
		return rootView;
	}
	
	
	
	public void inflateProductPage()
	{
		
			
	}
	
	private void showNumberVerificationActivity() {
		Intent intent = new Intent(getActivity(), NumberVerificationActivity.class);
		startActivity(intent);
	}
	
	
	private void linkedInLogin() {
		ProgressDialog progressDialog = new ProgressDialog(
				getActivity());

		LinkedinDialog d = new LinkedinDialog(getActivity(),
				progressDialog);
		d.show();

		// set call back listener to get oauth_verifier value
		d.setVerifierListener(new OnVerifyListener() {
			@Override
			public void onVerify(String verifier) {
				try {
					Log.i("LinkedinSample", "verifier: " + verifier);

					accessToken = LinkedinDialog.oAuthService
							.getOAuthAccessToken(LinkedinDialog.liToken,
									verifier);
					LinkedinDialog.factory.createLinkedInApiClient(accessToken);
					client = factory.createLinkedInApiClient(accessToken);
					Log.i("LinkedinSample",
							"ln_access_token: " + accessToken.getToken());
					Log.i("LinkedinSample",
							"ln_access_token: " + accessToken.getTokenSecret());
					Person p = client.getProfileForCurrentUser();
					
					Log.d("first name",p.getFirstName());
					Log.d("last name",p.getLastName());
					linkedInUser.setFirstName(p.getFirstName());
					linkedInUser.setLastName(p.getLastName());
				//	Log.d("phone",p.getPhoneNumbers().getPhoneNumberList().toString());
					

				} catch (Exception e) {
					Log.i("LinkedinSample", "error to get verifier");
					e.printStackTrace();
				}
				finally
				{
					showLinkedInUserDetailsActivity();
				}
			}
		});

		// set progress dialog
		progressDialog.setMessage("Loading...");
		progressDialog.setCancelable(true);
		progressDialog.show();
	}
	
	private void showLinkedInUserDetailsActivity() {
		Fragment fragment2 = new LinkedInUserDetail(getActivity().getApplicationContext());
		Bundle args2 = new Bundle();
		fragment2.setArguments(args2);

		// Insert the fragment by replacing any existing fragment
		FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
		fragmentManager2.beginTransaction()
		.replace(R.id.content_frame, fragment2)
		.commit();
		
	}

	
	
	

	
}