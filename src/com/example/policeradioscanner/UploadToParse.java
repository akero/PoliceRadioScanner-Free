package com.example.policeradioscanner;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.akero.fuzzradiopro.R;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseFile;
import com.parse.ParseObject;

public class UploadToParse extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Parse.initialize(this, "5AFBQCOWvlNOeNjr8HfgAOEyVPfHJqZ6dE6Cj73M", "6z9xv55e1IfWPg0dGswUUeuLJIdMOUuYCMy2mMVr"); 
		ParseAnalytics.trackAppOpened(getIntent());
		setContentView(R.layout.parseupload);
		try{int a=1;
			String line;
			String stuff="";
			String finalstring="";
		InputStream fis=null;
		int v=count("feedtitlesandaddresses.txt");
		fis = getApplicationContext().openFileInput("feedtitlesandaddresses.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		for(int i=0;i<v-1;i++){
			line=br.readLine();
			a+=1;
			Log.d(Integer.toString(a),"fubar");
			stuff=stuff+line+"\n";
			if(i!=0&&(i%200==0||i==v-2)){
				finalstring=finalstring+stuff;
				stuff="";
			}
		}
		br.close();
		fis.close();
		byte[] data = finalstring.getBytes();
		ParseFile file = new ParseFile("feeds.txt", data);
		file.saveInBackground();
		int version=2;//*************************************************************************************************
		//increase version by 1 when updating
		//sending
		ParseObject po = new ParseObject("FeedsAndNames");
		po.put("appData", file);
		po.put("version", version);
		po.saveInBackground();
		}catch(Exception e){Log.d(e.toString(),"fubar");}
		//ParseObject testObject = new ParseObject("TestObject");
		//testObject.put("foo", "bar");
		//testObject.saveInBackground();	
	}
	public int count(String filename) throws IOException {
		InputStream is = getApplicationContext().openFileInput(filename);
		BufferedInputStream bis = new BufferedInputStream(is);
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = bis.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }Log.d("count is "+count,"HERERE");
	        return (count == 0 && !empty) ? 1 : count+1;
	    } finally {
	        bis.close();
	        is.close();
	    }
	}
}
