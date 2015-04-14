package com.parse.buddyfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.RestAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.apis.EventServiceApi;
import com.example.model.Event;
import com.example.model.Utils;
import com.parse.integratingfacebooktutorial.R;


@SuppressLint("ValidFragment")
public class HomePageContent extends Fragment {
	View rootView;
	Context mContext;
	ListView eventListView;
	static final String EVENT_TYPE = "type"; // parent node
	static final String EVENT_ID = "id";
	static final String EVENT_TITLE = "title";
	static final String EVENT_CREATOR = "creator";
	static final String EVENT_LOCATION = "location";
	static final String EVENT_TIME = "time";
	
	List<Event> eventList;
	static List<Event> finalEvents;
	
	private Button getEventsButton,inflateButton;
	
	
	EventsListAdapter myAdapter;
	
	
	public HomePageContent(Context ctx) {
		mContext = ctx; 
	}
	
	public HomePageContent()
	{
		
	}
	
	public void initialize(View rootView)
	{
		eventList = new ArrayList<Event>();
		finalEvents = new ArrayList<Event>();
		getEventsButton = (Button) rootView.findViewById(R.id.getButton);
		inflateButton = (Button) rootView.findViewById(R.id.inflateButton);
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.events_list, container, false);
		
		initialize(rootView);
		
		getEventsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getEvents();
			//	Toast.makeText(getActivity().getApplicationContext(), 
			//			Integer.toString(finalEvents.size()), Toast.LENGTH_LONG).show();
			}
		});
		
		inflateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				inflatePage();
				/*
				Toast.makeText(getActivity().getApplicationContext(), 
						Integer.toString(finalEvents.size()), Toast.LENGTH_LONG).show();
						*/
			}
		});
		
	
		
		
		//Toast.makeText(getActivity().getApplicationContext(), Integer.toString(eventList.size()), Toast.LENGTH_LONG).show();
		
		return rootView;
	}
	
	
	
	public void inflatePage()
	{
		
		
		
		ArrayList<HashMap<String, String>> eventsList = new ArrayList<HashMap<String, String>>();
		
		HashMap<String, String> map = new HashMap<String, String>();
		/*map.put(EVENT_ID, "1");
		map.put(EVENT_CREATOR,"Abhishek");
		map.put(EVENT_LOCATION, "Delhi");
		map.put(EVENT_TIME, "11");
		map.put(EVENT_TYPE, "movie");
		map.put(EVENT_TITLE,"awesome");

		eventsList.add(map);
*/
		for (int i = 0; i<finalEvents.size(); i++) {
			 map = new HashMap<String, String>();
			map.put(EVENT_ID, "1");
			map.put(EVENT_CREATOR,finalEvents.get(i).getCategory());
			map.put(EVENT_LOCATION, "Delhi");
			map.put(EVENT_TIME, finalEvents.get(i).getTime());
			map.put(EVENT_TYPE, finalEvents.get(i).getCategory());
			map.put(EVENT_TITLE,finalEvents.get(i).getTitle());

			eventsList.add(map);
		}

		eventListView = (ListView) rootView.findViewById(R.id.list);

		myAdapter=new EventsListAdapter(getActivity(), eventsList);        
		eventListView.setAdapter(myAdapter);


		eventListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				
				Toast.makeText(getActivity().getApplicationContext(), finalEvents.get(position).getCategory(),
						Toast.LENGTH_LONG).show();
				new Thread( new Runnable() {
					@Override
					public void run() {
						RestAdapter restAdapter = new RestAdapter.Builder()
					    .setEndpoint(Utils.SERVER_URL)
					    .build();
						
						EventServiceApi service = restAdapter.create(EventServiceApi.class);
						//service.
						service.addMember(LoginActivity.myUser.getId(),finalEvents.get(position).getId());
					}
				}).start();

			}
		});		
	}
	
	
	public void getEvents()
	{
		RestAdapter restAdapter = new RestAdapter.Builder()
	    .setEndpoint(Utils.SERVER_URL)
	    .build();

		final EventServiceApi service = restAdapter.create(EventServiceApi.class);
		
		new Thread( new Runnable() {
		    @Override
		    public void run() {
				{
					eventList = service.getEvents();
					Log.d("eventList",eventList.get(0).getId());
					//inflatePage();
				}
		    }
		}).start();
		
		
		for(int i=0;i<eventList.size();i++)
		{
			if(HomePageActivity.checked.get(0).equals(true))
			{
				if(eventList.get(i).getCategory().equals("movie"))
				{
					finalEvents.add(eventList.get(i));
				}
			}
			if(HomePageActivity.checked.get(1).equals(true))
			{
				if(eventList.get(i).getCategory().equals("coffee"))
				{
					finalEvents.add(eventList.get(i));
				}
				
			}
			if(HomePageActivity.checked.get(2).equals(true))
			{
				if(eventList.get(i).getCategory().equals("outstation"))
				{
					finalEvents.add(eventList.get(i));
				}
			}
			if(HomePageActivity.checked.get(3).equals(true))
			{
				if(eventList.get(i).getCategory().equals("other"))
				{
					finalEvents.add(eventList.get(i));
				}
			}
		}
		
		
	//	Toast.makeText(getApplicationContext(), eventList, Toast.LENGTH_LONG).show();
	}
	
	
	

	
	
	

	
}