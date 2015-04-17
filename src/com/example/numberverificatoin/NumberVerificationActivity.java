package com.example.numberverificatoin;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.matesnetwork.callverification.Cognalys;
import com.matesnetwork.interfaces.VerificationListner;
import com.parse.buddyfinder.HomePageActivity;
import com.parse.buddyfinder.UserDetailsActivity;
import com.parse.integratingfacebooktutorial.R;


public class NumberVerificationActivity  extends Activity {
	private TextView timertv = null;
	private EditText phoneNumbTv = null;
	EditText countryCode = null;
	private CountDownTimer countDownTimer;
	private RelativeLayout timerLayout;
	private TextView verifyNumbertext;
	LinearLayout checkNowLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.number_verification_activity_main);
		phoneNumbTv = (EditText) findViewById(R.id.ph_et);
		timertv = (TextView) findViewById(R.id.timer_tv);
		verifyNumbertext = (TextView) findViewById(R.id.enter_phno_tv);
		TextView country_code_tv = (TextView) findViewById(R.id.country_code_tv);
		country_code_tv.setText(Cognalys.getCountryCode(getApplicationContext()));
		timerLayout = (RelativeLayout) findViewById(R.id.timer_rl);
		countryCode = (EditText) findViewById(R.id.country_code_tv);
		checkNowLayout = (LinearLayout) findViewById(R.id.verifyNumberLinerarLayout);
		
		findViewById(R.id.verifyNumberLinerarLayout).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (!TextUtils.isEmpty(phoneNumbTv.getText().toString())) {
							verify();
						}else{
							Toast.makeText(getApplicationContext(), "Please enter your phone number to verify", 3500).show();
						}
						
					}
				});
	}

	private void verify() {
		timerLayout.setVisibility(View.VISIBLE);
		countryCode.setVisibility(View.INVISIBLE);
		phoneNumbTv.setVisibility(View.INVISIBLE);
		checkNowLayout.setVisibility(View.INVISIBLE);
		verifyNumbertext.setText("Verifying Number");
		countDownTimer = new CountDownTimer(60000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				timertv.setText("" + millisUntilFinished / 1000);
			}

			@Override
			public void onFinish() {
				timerLayout.setVisibility(View.GONE);
				countryCode.setVisibility(View.VISIBLE);
				phoneNumbTv.setVisibility(View.VISIBLE);
				checkNowLayout.setVisibility(View.VISIBLE);
				showHomeActivity();
			}

		};
		countDownTimer.start();
		
		Cognalys.verifyMobileNumber(NumberVerificationActivity.this,
				"0951e2c0a7494045b59e8bba7ee7fa69fbe02bb4",
				"5945914e80774d9ca12a733", phoneNumbTv.getText().toString(), new VerificationListner() {
					
					@Override
					public void onVerificationStarted() {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onVerificationSuccess() {
						countDownTimer.cancel();
						timerLayout.setVisibility(View.GONE);
						verifyNumbertext.setText("Number verified");
						showAlert("Your number has been verified\n\nThanks!!",true);
						showHomeActivity();
						
					}

					@Override
					public void onVerificationFailed(ArrayList<String> errorList) {
						countDownTimer.cancel();
						timerLayout.setVisibility(View.GONE);
						for (String error : errorList) {
							Log.d("abx", "error:"+error);
						}
						showAlert("Something went wrong.\n please try again",false);
					}
				});
		

	}
	
	private void showHomeActivity() {
		Intent intent = new Intent(this, HomePageActivity.class);
		startActivity(intent);
	}
	
private void showAlert(String message,boolean status){
	final Dialog dialog = new Dialog(NumberVerificationActivity.this);
	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	dialog.setContentView(R.layout.dialog);


	ImageView mImageView = (ImageView) dialog.findViewById(R.id.verify_im);
	TextView messageTv=(TextView) dialog.findViewById(R.id.messagetv);
	if (status) {
		mImageView.setImageResource(R.drawable.blue_tick);
	}else{
		mImageView.setImageResource(R.drawable.wrong);
	}
	
	messageTv.setText(message);
	dialog.show();
}
}