package com.parse.buddyfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.RestAdapter;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.apis.EventServiceApi;
import com.example.model.Utils;
import com.parse.integratingfacebooktutorial.R;

public class EventsListAdapter extends BaseAdapter {
	
	Activity myActivity;
	ArrayList<HashMap<String, String>> myEvents;
	private static LayoutInflater inflater=null;
	
	 public EventsListAdapter (Activity activity, ArrayList<HashMap<String, String>> eventsList) 
	 {
	        myActivity = activity;
	        myEvents = eventsList;
	        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 }


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myEvents.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return myEvents.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return Long.parseLong(myEvents.get(position).get(HomePageContent.EVENT_ID));
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View vi=convertView;
		if(convertView==null)
			vi = inflater.inflate(R.layout.events_row, null);

		TextView title = (TextView)vi.findViewById(R.id.title); // title
		TextView artist = (TextView)vi.findViewById(R.id.artist); // artist name
		TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
		ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
		final ToggleButton joinEvent = (ToggleButton) vi.findViewById(R.id.joinButton);
		
		List<String> eventsMember = LoginActivity.myUser.getMemberOfEvents();
		
		for(int i=0;i<eventsMember.size();i++)
		{
			if(HomePageContent.finalEvents.get(position).getId().equals(eventsMember.get(i)))
			{
				joinEvent.setChecked(true);
				break;
			}
		}
		
		if(HomePageContent.finalEvents.get(position).getCategory().equals("movie"))
		{
			thumb_image.setImageResource(R.drawable.movie_icon);
		}
		else if(HomePageContent.finalEvents.get(position).getCategory().equals("coffee"))
		{
			thumb_image.setImageResource(R.drawable.coffee_icon);
		}
		else if(HomePageContent.finalEvents.get(position).getCategory().equals("outstation"))
		{
			thumb_image.setImageResource(R.drawable.outstation_icon);
		}
		else if(HomePageContent.finalEvents.get(position).getCategory().equals("other"))
		{
			thumb_image.setImageResource(R.drawable.other_icon);
		}
		
		
		
		joinEvent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(joinEvent.isChecked()){
					new Thread( new Runnable() {
						@Override
						public void run() {
							RestAdapter restAdapter = new RestAdapter.Builder()
						    .setEndpoint(Utils.SERVER_URL)
						    .build();
							
							EventServiceApi service = restAdapter.create(EventServiceApi.class);
							//service.
							service.addMember(LoginActivity.myUser.getId(),HomePageContent.finalEvents.get(position).getId());
						}
					}).start();
				}
				else
				{
					Log.d("clicked","off");
					new Thread( new Runnable() {
						@Override
						public void run() {
							RestAdapter restAdapter = new RestAdapter.Builder()
						    .setEndpoint(Utils.SERVER_URL)
						    .build();
							
							EventServiceApi service = restAdapter.create(EventServiceApi.class);
							//service.
							service.deleteMember(LoginActivity.myUser.getId(),HomePageContent.finalEvents.get(position).getId());
							Log.d("id",HomePageContent.finalEvents.get(position).getId());
						}
					}).start();
				}
			}
		});
		
		
		HashMap<String, String> event = new HashMap<String, String>();
		event = myEvents.get(position);

		// Setting all values in listview
		title.setText(event.get(HomePageContent.EVENT_TITLE));
		artist.setText(event.get(HomePageContent.EVENT_CREATOR));
		duration.setText(event.get(HomePageContent.EVENT_TIME));
		//imageLoader.DisplayImage(event.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
		return vi;
	}

}
