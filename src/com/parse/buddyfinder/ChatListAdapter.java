package com.parse.buddyfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Text;

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
import com.parse.FindCallback;
import com.parse.integratingfacebooktutorial.R;

public class ChatListAdapter extends BaseAdapter {
	
	Activity myActivity;
	ArrayList<String> messages;
	private static LayoutInflater inflater=null;
	
	 public ChatListAdapter (Activity activity, ArrayList<String> messages) 
	 {
	        myActivity = activity;
	        this.messages = messages;
	        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 }


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return messages.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return messages.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View vi=convertView;
		if(position%2==0)
		{
			vi = inflater.inflate(R.layout.chat_first_person, null);
			TextView text = (TextView) vi.findViewById(R.id.text);
			text.setText(messages.get(position));
		}
		else
		{
			vi = inflater.inflate(R.layout.chat_second_person, null);
			TextView text = (TextView) vi.findViewById(R.id.text);
			text.setText(messages.get(position));
		}
			
		
		

		
		return vi;
	}

}
