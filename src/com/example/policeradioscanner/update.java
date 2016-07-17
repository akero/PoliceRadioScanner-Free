package com.example.policeradioscanner;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akero.fuzzradiopro.R;
import com.flurry.android.FlurryAgent;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ProgressCallback;


public class update extends Activity{
	int version;
	String currentversion="0";
	boolean downloaded=false;
	Context ba;
	protected boolean mbActive;
    protected ProgressBar mProgressBar;
    protected TextView text;
    ProgressDialog myPd_ring;
    boolean download=false;
    ParseObject object1=null;
	@Override
	protected void onCreate(Bundle savedInstanceState)throws java.lang.NullPointerException,NullPointerException{
		super.onCreate(savedInstanceState);
		Parse.initialize(this, "5AFBQCOWvlNOeNjr8HfgAOEyVPfHJqZ6dE6Cj73M", "6z9xv55e1IfWPg0dGswUUeuLJIdMOUuYCMy2mMVr"); 
		ParseAnalytics.trackAppOpened(getIntent());
		setContentView(R.layout.update);
		
		mProgressBar = (ProgressBar)findViewById(R.id.adprogress_progressBar);
		
		mProgressBar.setVisibility(View.GONE);
		text=(TextView)findViewById(R.id.textview_progressBar);
		text.setVisibility(View.GONE);
		ba=this.getBaseContext();
		if(percent==0){
		try{
			File fileq = null;
        	try{
        	fileq = ba.getFileStreamPath("dbver.txt");}catch(Exception e){Log.d("er1 "+e.toString(),"fubar");}
        	//Log.d("file exists= "+Boolean.toString(fileq.exists()),"fubar");
        	if(fileq.exists()==true){
        		FileInputStream fis = getApplicationContext().openFileInput("dbver.txt");
    			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
    			currentversion=br.readLine();
        	}
			//
        	if(isOnline()==true){
        	text.setText("Checking for new feeds..");
        	text.setVisibility(View.VISIBLE);
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("FeedsAndNames");
			query.orderByDescending("version");
			query.getFirstInBackground(new GetCallback<ParseObject>() {
				 public void done(ParseObject object, ParseException e) {
					    if (object == null&&e==null) {
				        	text.setVisibility(View.GONE);
					    	Toast.makeText(update.this, "Download failed, check your Internet connection",  Toast.LENGTH_SHORT ).show();
					     // Log.d("test", "The object was not found...");
					    }else if(e!=null){
				        	text.setVisibility(View.GONE);
					    	Toast.makeText(update.this, e.toString(),  Toast.LENGTH_SHORT ).show();
						      //Log.d("error is21 " +e.toString(), "fubar");
					    }
					    else if(e==null&&object!=null){
					    	object1=object;
		//ParseObject anotherApplication = new ParseObject("FeedsAndNames");
		//Log.d("1","fubar");
		//Getting Version
					   version=(Integer) object1.getInt("version");
					   //Log.d("version is "+Integer.toString(version),"fubar");
		//Got Version
					   if(currentversion.equals(Integer.toString(version))){
						   Toast.makeText(update.this, "App is already up to date",  Toast.LENGTH_SHORT ).show();
						   onBackPressed();
					   }
					   else{
						  
						   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
								update.this);
					   
						// set title
						alertDialogBuilder.setTitle("Download?");
			 
						// set dialog message
						alertDialogBuilder
							.setMessage("New feeds are available (~1MB), download? (Might take a couple of minutes)")
							.setCancelable(false)
							.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// if this button is clicked, close
									// current activity
									download=true;
									if(download==true){
										   text.setText("Downloading..");
						ParseFile file = (ParseFile)object1.get("appData");
						//Log.d("2","fubar");
						mProgressBar.setMax(100);
						mProgressBar.setProgress(0);
						mProgressBar.setVisibility(View.VISIBLE);
						file.getDataInBackground(new GetDataCallback() {
							
						  public void done(byte[] data, ParseException e) {
							//  Log.d("3","fubar");
						    if (e == null) {//Log.d("4","fubar");
						      // data has the bytes
						    //	Log.d("great success","fubar");
						    	if(downloaded==true){
						    		
						    		
						    		
									FileOutputStream fos = null;
									//Log.d("here ","fubar");
									try{
									fos = getApplicationContext().openFileOutput("feedtitlesandaddresses.txt", Context.MODE_PRIVATE);
									BufferedOutputStream bo=new BufferedOutputStream(fos);
									bo.write(data);
									bo.close();
									fos.close();
									//saving version
									mProgressBar.setVisibility(View.GONE);
									//updating database
									File database=null;
									Context ctx=getApplicationContext();
									try{
									database=getApplicationContext().getDatabasePath("feedsmanager.db");
									}catch(Exception f){Log.d("error is 123 "+e.toString(),"fubar");}
									if (!database.exists()) {
										new FillDbTask().execute(ctx);
										text.setVisibility(View.GONE);
										myPd_ring=ProgressDialog.show(update.this, "Filling database", "This might take a couple of minutes..", true);
				                        myPd_ring.setCancelable(false);
				                        
									    //Log.d("Database not found", "fubar");
									    
									} else {
										new FillDbTask2().execute(ctx);
										text.setVisibility(View.GONE);
										myPd_ring=ProgressDialog.show(update.this, "Filling database", "This might take a couple of minutes..", true);
				                        myPd_ring.setCancelable(false);
										 
								        
									   // Log.d("Database found", "fubar");
									}
									//database updated
									//if(database.exists()==true);{
									//FileOutputStream fo = null;
									//Log.d("here1 ","fubar");
									//fo = getApplicationContext().openFileOutput("dbver.txt", Context.MODE_PRIVATE);
									//fo.write(Integer.toString(version).getBytes());
									//fo.close();
									//text.setText("Update complete");//}
									done=true;
									//Log.d("works bro","fubar");
									}
									catch(Exception f){Log.d("error is "+f.toString(),"fubar");}
								}
						    	else{
									Toast.makeText(update.this, "Download failed, check your Internet connection",  Toast.LENGTH_SHORT ).show();
								}
						    } else {//Log.d("5","fubar");
						      // something went wrong
						    	//Log.d(e.toString(),"fubar1");
						    }
						  }
						}, new ProgressCallback() {
							  public void done(Integer percentDone) {
								 // Log.d(Integer.toString(percentDone),"fubar");
								  
								  mProgressBar.setProgress(percentDone);
								  if(percentDone==100){
									  downloaded=true;
									  percent=percentDone;
								  }
								  
								  
								    // Update your progress spinner here. percentDone will be between 0 and 100.
								  }
							  }
						);
						}
							
						
								}
							  })
							.setNegativeButton("No",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									download=false;
									dialog.cancel();
									onBackPressed();
								}
							});
			 
							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder.create();
			 
							// show it
							alertDialog.show();
						
					
					   }
				 }
				  }
				});
        	}else{
        		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
    					update.this);
    			   
    			// set title
    			//alertDialogBuilder.setTitle("");
        		//Log.d("fubar","asdf");
    			// set dialog message
    			alertDialogBuilder
    				.setMessage("Unable to connect, please check your internet connection")
    				.setCancelable(false)
    					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
    						public void onClick(DialogInterface dialog,int id) {
    							// if this button is clicked, just close
    							// the dialog box and do nothing
    							dialog.cancel();
    							onBackPressed();
    							
    						}
    					});
    	 
    					// create alert dialog
    					AlertDialog alertDialog = alertDialogBuilder.create();
    	 
    					// show it
    					alertDialog.show();
        	}
		}catch(Exception e){Log.d(e.toString(),"fubar");}
		}
	}
	public class FillDbTask2 extends AsyncTask<Context, Void, String[]>{
    	String[] q;
    	@Override
    	
    	protected String[] doInBackground(Context... params) {
    		Context ctx=params[0];
    		try{
    			final DatabaseHandler ourHelper=new DatabaseHandler(ctx);
    			final SQLiteDatabase ourDatabase1;
             	ourDatabase1=ourHelper.getReadableDatabase();
                String sql = "SELECT COUNT(*) FROM " + TABLE_CONTACTS;
                SQLiteStatement statement = ourDatabase1.compileStatement(sql);
                long count1 = statement.simpleQueryForLong();
                int sizeofdb=(int)count1;
               // Log.d("Count is "+Integer.toString(sizeofdb),"Teh end");
                ourDatabase1.close();
			 	String line="";
		        FileInputStream fis = getApplicationContext().openFileInput("feedtitlesandaddresses.txt");
    			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		        //ContentValues values = new ContentValues();
    			Contact obj=new Contact();
		
		final SQLiteDatabase ourDatabase;
		ourDatabase=ourHelper.getWritableDatabase();
		int a=0;
		try{ourDatabase.beginTransaction();
        while((line=br.readLine())!=null) {
        	a+=1;
        	//Log.d(line,"twitch11");
        	//Log.d("fcked here6","twitch1");
        	StringTokenizer stringTokenizer = new StringTokenizer(line, "<");
            //ctid
        	String firstNumber="";
        	String strfinal="";//feedname,address;feedname;address etc
              firstNumber = (String) stringTokenizer.nextElement();
              char c = '<'; // search for this character ...
              int count = 0;
              int size=0;
              for (int i = 0 ; i < line.length() ; i++ ) {
                if ( line.charAt(i) == c ) {
                  count += 1;
                }}size=count/6;
              
              String str="";
              String str1="";
              String st[]= line.split("title>");
              
              int t=1;
              for(int q=1;q<=(size);q++){
              
              if(q!=(size)){
              str=st[t].substring(0,st[t].length()-2)+";";
              t+=1;
              str1=st[t];
              t+=1;
              str=str+str1.substring(10,str1.length()-27)+">";
              strfinal+=str;
             // Log.d(strfinal,"twitch11");
              	}
              else if(q==(size))
              	{
              	str=st[t].substring(0,st[t].length()-2)+";";
              	t+=1;
                  str1=st[t];
                  t+=1;//Log.d("This is sparta","hitlers");
                  str=str+str1.substring(10,str1.length()-20)+">";
                  strfinal+=str;
                  }//Log.d(strfinal,"twitch1");
              
              }
              // Inserting Row
              ContentValues values = new ContentValues();
              values.put(KEY_NAME, firstNumber);
              values.put(KEY_PH_NO, strfinal);
       
              // updating row
              ourDatabase.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                      new String[] { String.valueOf(a) });
             // Log.d("Updating","YOYO");
              }
       // Log.d("a is "+Integer.toString(a),"Teh end");
         	if(sizeofdb>a){
         		for(int i=a+1;i<=sizeofdb;i++){
         			ourDatabase.delete(TABLE_CONTACTS, KEY_ID + " = ?",
         	                new String[] { String.valueOf(i) });
         			
         		}
         	}
        
        ourDatabase.setTransactionSuccessful();
        }finally {
        	    
        	ourDatabase.endTransaction();
        	ourDatabase.close();
        		}
            //Log.d("fcked here4","twitch1");
		dbiscool=true;
		
		
        }catch(Exception g){Log.d("yeah error is"+g,"twitch11");}
			return q;
    		
    	}
    	@Override
        protected void onPostExecute(String[] result) {
    		super.onPostExecute(result);
            // TODO: check this.exception 
    		//Log.d("hi im in here","tagggggg");
    		try{
    			text.setText("Download complete");
    			text.setVisibility(View.VISIBLE);
    			FileOutputStream fo = null;
				//Log.d("here1 ","fubar");
				fo = getApplicationContext().openFileOutput("dbver.txt", Context.MODE_PRIVATE);
				fo.write(Integer.toString(version).getBytes());
				fo.close();
				myPd_ring.dismiss();
    			}catch(Exception e){
        		//Log.d("error is "+e.toString(),"gotcha");
            // TODO: do something with the feed
        }
    		}
	}
	boolean dbfilled=false;
	public class FillDbTask extends AsyncTask<Context, Void, String[]>{
    	String[] q;
    	@Override
    	
    	protected String[] doInBackground(Context... params) {
    		Context ctx=params[0];
    		
			final DatabaseHandler ourHelper=new DatabaseHandler(ctx);
			final SQLiteDatabase ourDatabase;
			ourDatabase=ourHelper.getReadableDatabase();
			return q;
    		
    	}
    	@Override
        protected void onPostExecute(String[] result) {
    		super.onPostExecute(result);
            // TODO: check this.exception 
    		//Log.d("hi im in here","tagggggg");
    		try{
    			text.setText(" complete");
    			text.setVisibility(View.VISIBLE);
    			FileOutputStream fo = null;
			//	Log.d("here1 ","fubar");
				fo = getApplicationContext().openFileOutput("dbver.txt", Context.MODE_PRIVATE);
				fo.write(Integer.toString(version).getBytes());
				fo.close();
				myPd_ring.dismiss();
    			}catch(Exception e){
        		Log.d("error is "+e.toString(),"gotcha");
        	}
            // TODO: do something with the feed
        }
	}
	@Override
	protected void onStart(){
		super.onStart();
		FlurryAgent.onStartSession(this, "4DF4JJB7BHD8XZ47YDGX");
		
	}
	@Override
	protected void onStop(){
		super.onStop();
		FlurryAgent.onEndSession(this); 
	}
	public boolean isOnline() {
        ConnectivityManager cm =
            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
        	//Log.d("true","askq");
            return true;
        }
        //Log.d("false","askq");
        return false;
    }
	
	int h=0;
    // Contacts table name
	boolean dbiscool=false;
	boolean done=false;
    private static final String TABLE_CONTACTS = "table_to_hold_all_values";
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";//ctid
    private static final String KEY_PH_NO = "phone_number";//feedname,address;feedname;address etc
    int percent=0;
}
