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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.matesnetwork.callverification.Cognalys;
import com.matesnetwork.interfaces.VerificationListner;
import com.parse.buddyfinder.UserDetailsActivity;
import com.parse.integratingfacebooktutorial.R;


public class NumberVerificationActivity  extends Activity {
	private TextView timertv = null;
	private EditText phoneNumbTv = null;
	private CountDownTimer countDownTimer;
	private RelativeLayout timerLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.number_verification_activity_main);
		phoneNumbTv = (EditText) findViewById(R.id.ph_et);
		timertv = (TextView) findViewById(R.id.timer_tv);
		TextView country_code_tv = (TextView) findViewById(R.id.country_code_tv);
		country_code_tv.setText(Cognalys.getCountryCode(getApplicationContext()));
		timerLayout = (RelativeLayout) findViewById(R.id.timer_rl);
		
		findViewById(R.id.verifybutton).setOnClickListener(
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
		countDownTimer = new CountDownTimer(60000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				timertv.setText("" + millisUntilFinished / 1000);
			}

			@Override
			public void onFinish() {
				timerLayout.setVisibility(View.GONE);
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
						showAlert("Your number has been verified\n\nThanks!!",true);
						
						
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
	
	private void showUserDetailsActivity() {
		Intent intent = new Intent(this, UserDetailsActivity.class);
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