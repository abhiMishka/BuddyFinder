package com.parse.buddyfinder;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.parse.integratingfacebooktutorial.ChatActivity;
import com.parse.integratingfacebooktutorial.R;


public class MainActivity extends Activity implements OnClickListener {

    private TextView textView;
	private Button checkUserProfileButton,signIn;
	

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textView = (TextView) this.findViewById(R.id.textView);
        checkUserProfileButton = (Button) this.findViewById(R.id.CheckUserProfileButton);
        signIn = (Button) this.findViewById(R.id.signUp);
        signIn.setVisibility(View.INVISIBLE);
        
        signIn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, ChatActivity.class);
	    		startActivity(intent);
			}
		});
        
        checkUserProfileButton.setOnClickListener(this);                        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


	@Override
	public void onClick(View v) {
		if(v.getId() == checkUserProfileButton.getId()) {
			// Get user info from database
	        UserInfo userInfo = SaveFile.retrieveUserInfoFromDatabase(this.getApplicationContext());
	        
	        if(userInfo == null) {
	        	textView.setText("No user info found, Please sign In");
	        	signIn.setVisibility(View.VISIBLE);
	        	
	        	
	        }
	        else {
	        	textView.setText("User info found");
	        	signIn.setVisibility(View.VISIBLE);
	        }
	        
	        SaveFile.writeUserInfoToDatabase(this.getApplicationContext(), new UserInfo());
		}
	}
}
