package com.example.linkedin;

public class Config {

	public static String LINKEDIN_CONSUMER_KEY = "75bcty683381wv";
	public static String LINKEDIN_CONSUMER_SECRET = "dreD2BpkqhM5BvWY";
	public static String scopeParams = "rw_nus+r_basicprofile+r_contactinfo";
	
	public static String OAUTH_CALLBACK_SCHEME = "x-oauthflow-linkedin";
	public static String OAUTH_CALLBACK_HOST = "callback";
	public static String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;
}
