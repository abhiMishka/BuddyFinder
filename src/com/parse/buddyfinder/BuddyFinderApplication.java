package com.parse.buddyfinder;

import com.matesnetwork.callverification.Cognalys;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.integratingfacebooktutorial.R;

import android.app.Application;

public class BuddyFinderApplication extends Application {

	static final String TAG = "MyApp";

	@Override
	public void onCreate() {
		super.onCreate();

		Parse.initialize(this, "Vcr6ota8RgV2ONy2jLjbGH6UMhCYfRa86daqg2iL",
				"RIaC45hl6y3VkqIrvqr9j9J7QKZbekOTnEGwoIes");

		ParseFacebookUtils.initialize(getString(R.string.app_id));
		
		
		
		Cognalys.enableAnalytics(getApplicationContext(), true, true);
		
		

	}

}
