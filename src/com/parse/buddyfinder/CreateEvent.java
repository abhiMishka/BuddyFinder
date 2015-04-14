package com.parse.buddyfinder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.RestAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract.Events;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.apis.EventServiceApi;
import com.example.model.Event;
import com.example.model.Utils;
import com.parse.integratingfacebooktutorial.R;

@SuppressLint("ValidFragment")
public class CreateEvent extends Fragment {

	EditText title,body;
	Spinner categorySpinner;
	List<Event> eventsList;
	
	private String[] eventcat = {"movie","coffee","outstation","other"};
	
	static EventServiceApi eventService;
	
	private Button createEventButton,getEventsButton;
	
	String myTitle,myEventBody,myEventCategory;
	
	UserInfo info;
	
	@SuppressLint("ValidFragment")
	public CreateEvent(Context applicationContext) {
		// TODO Auto-generated constructor stub
	}
	public CreateEvent() {
		// TODO Auto-generated constructor stub
	}
	private void initialize(View v)
	{
		title =(EditText) v.findViewById(R.id.EditTextTitle);
		body = (EditText) v.findViewById(R.id.EventBody);
		categorySpinner = (Spinner) v.findViewById(R.id.categorySpinner);
		createEventButton = (Button) v.findViewById(R.id.eventCreateButton);
		getEventsButton = (Button) v.findViewById(R.id.getEventsButton);
		eventsList = new ArrayList<Event>();
		eventService = new RestAdapter.Builder()
	    .setEndpoint(Utils.SERVER_URL)
	    .build()
	    .create(EventServiceApi.class);
		
 	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		
		View rootView = inflater.inflate(R.layout.create_event, container, false);

		initialize(rootView);
	
		
		
	
		createEventButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				myTitle =  title.getText().toString();
				myEventBody = body.getText().toString();
				myEventCategory = eventcat[categorySpinner.getSelectedItemPosition()];
				addEvent();
			}
		});
		
		getEventsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), Integer.toString(eventsList.size()), Toast.LENGTH_LONG).show();
			}
		});

		
		return rootView;
	}
	
	public void uploadEventDetail()
	{
		
	}
	
	
	
	public void addEvent() {
     //   List<User> userList = UserTest.userService.getUsers();

		new Thread( new Runnable() {
		    @Override
		    public void run() {
		    	  Event event = new Event("",
		                  myTitle,
		                  myEventBody,
		                  myEventCategory,
		                  Utils.DATE_TIME_FORMAT.format(new Date()), 10.0, 20.0);

		         boolean response =  eventService.addEvent(event);
		         print(String.valueOf(response));
		         Log.d("response",String.valueOf(response));
		    }
		}).start();
		
		new Thread( new Runnable() {
			@Override
			public void run() {	 
				eventsList =  eventService.getEvents();
			}
		}).start();

    }
	
	public void print(String text)
	{
		//Toast.makeText(getActivity(),text, Toast.LENGTH_LONG).show();
	}
	public void getEvents()
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
