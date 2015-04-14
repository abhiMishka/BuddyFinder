package com.parse.buddyfinder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.content.Context;
import android.util.Log;

public class SaveFile {

	private final static String UserInfoFilename = "userInfo.dat";
	private static final String TAG = "SaveFile";
	
	public static UserInfo retrieveUserInfoFromDatabase(Context context) {
		try {
			ObjectInputStream inStream = new ObjectInputStream(context.openFileInput(UserInfoFilename));
			
			UserInfo userInfo = (UserInfo) inStream.readObject();
			
			inStream.close();
			
			return userInfo;
			
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.i(TAG, "No previous data found");
		return null;
	}

	public static boolean writeUserInfoToDatabase(Context context, UserInfo userInfo) {
		try {
			ObjectOutputStream outStream = new ObjectOutputStream(context.openFileOutput(UserInfoFilename, Context.MODE_PRIVATE));
			
			outStream.writeObject(userInfo);
			
			outStream.close();
			
			return true;
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
