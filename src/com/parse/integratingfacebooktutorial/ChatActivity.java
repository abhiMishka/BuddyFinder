package com.parse.integratingfacebooktutorial;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.parse.buddyfinder.ChatListAdapter;

public class ChatActivity extends Activity {
	
	ListView chatList;
	ChatListAdapter chatListAdapter;
	ArrayList<String> messages;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_forum);
		
		chatList = (ListView) findViewById(R.id.myList);
		messages = new ArrayList<String>();
		messages.add("Hi");messages.add("Hello");messages.add("How you doing");messages.add("Cool");
		messages.add("jada mat ban");messages.add("bana banaya hu mae to");messages.add("joke h ye?");
		
		chatListAdapter = new ChatListAdapter(this, messages);
		chatList.setAdapter(chatListAdapter);
		
	}
}
