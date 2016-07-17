package com.example.policeradioscanner;
//using mp3 streamer instead of webview
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Window;
import com.akero.fuzzradiopro.R;
import com.example.policeradioscanner.LocationValet.ILocationValetListener;
import com.example.policeradioscanner.mp3playerservice.MediaPlayerBinder;
import com.flurry.android.FlurryAgent;
import com.millennialmedia.android.MMAdView;
import com.millennialmedia.android.MMRequest;
import com.millennialmedia.android.MMSDK;
import com.scringo.Scringo;
public class Webview extends Activity  implements  OnClickListener, MediaPlayer.OnPreparedListener{
	String track;
	String info[];
	String name;
	MediaPlayer mp;
	private SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null; 
	boolean mBounded;
	Scringo scringo;
	mp3playerservice mServer;
	private Button btn_play,btn_stop,butfavs;
	private TextView ten,eleven,fourteen,other;
	private TextView one;
	private TextView two;
	Context ctr;
	Context ctz;
	String favsname,favsfeed;
	private Button frag1,frag2,frag3,frag4;
	ImageButton frag5;
	private LinearLayout a;
	private ListView b;
	private LinearLayout at,codes;
	private ListView bt;
	private ScrollView sv1;
	String hour,h1,minutes,seconds,ampm;
	int h11;
	Context ba,app;
	android.support.v4.app.FragmentTransaction fragmentTransaction;
	Boolean a1=false;
	GradientTextView wha;
	LinearLayout wha1;
	ImageView wha2;
	 LocationValet locationValet;
	 RefreshHandler handler;
	 
	ServiceConnection mConnection = new ServiceConnection() {
		  public void onServiceDisconnected(ComponentName name) {
		   mBounded = false;
		   mServer = null;
		  }
		  
		  public void onServiceConnected(ComponentName name, IBinder service) {
		   //Toast.makeText(Webview.this, "Service is connected", 1000).show();
		   mBounded = true;
		   //Log.d("hea1 ","hyena");
		   MediaPlayerBinder mLocalBinder = (MediaPlayerBinder)service;
		   mServer = mLocalBinder.getService();
		   favsname=mServer.trackname();
		   favsfeed=mServer.track;
		   //Log.d("mserver.track "+mServer.track,"fuuu");
		  // Log.d("track "+track,"fuuu");
		  // Log.d("mserver.isStopped "+mServer.isStopped(),"fuuu");
		   setVolumeControlStream(AudioManager.STREAM_MUSIC);
		   //if(mServer.isError()==true){
		   if(track.equals(mServer.track())&&mServer.isStopped()==true){
       		btn_play.setVisibility(View.VISIBLE);
       		btn_play.setEnabled(true);
       		btn_stop.setEnabled(false);
       		btn_stop.setVisibility(View.GONE);
       	}else{
       		btn_play.setEnabled(false);
       		btn_play.setVisibility(View.GONE);
       		btn_stop.setVisibility(View.VISIBLE);
       		btn_stop.setEnabled(true);
       	}/*}else{
       		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					Webview.this);
			   
			// set title
			//alertDialogBuilder.setTitle("");

			// set dialog message
			alertDialogBuilder
				.setMessage("Unable to connect. Server could be down, or check your Internet connection.")
				.setCancelable(false)
					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, just close
							// the dialog box and do nothing
							onBackPressed();
							dialog.cancel();
						}
					});
	 
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
	 
					// show it
					alertDialog.show();
       	}*/
		}
	};
	//MMRequest object
	 MMRequest request = new MMRequest();
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature((int) Window.FEATURE_NO_TITLE);
		
		scringo = new Scringo(this);
		
		 MMSDK.initialize(this);
		
		setContentView(R.layout.activity_webview);//Log.d("random","random");
		
		scringo.init(savedInstanceState);
		
		//MMSDK.setLogLevel(2);//remove on release****************************************************************
		
		ba=this.getBaseContext();
		
		app=this.getApplicationContext();
		
		ImageView iv=(ImageView) findViewById(R.id.imageView9);
		
		iv.setImageResource(R.drawable.frag11);
		
		Time now = new Time();
		now.setToNow();
		String ti=now.toString();
		String tim[]=ti.split("GMT");
		tim=tim[0].split("T");
		ti=tim[1];
		wha= (GradientTextView)findViewById(R.id.time);
		Thread myThread = null;
	    Runnable myRunnableThread = new CountDownRunner();
	    myThread= new Thread(myRunnableThread);   
	    myThread.start();
		Bundle extras;
		int a=0;
		a+=1;
		//Log.d(Integer.toString(a),"kibo");
        try{
            extras = getIntent().getExtras();
            info=extras.getStringArray("feed");
            track=info[1];
            name=info[0];
            init();
            one.setText(name);
          
            System.out.println();
            if(isOnline()==true){
            start1(track);
            }else{
            	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
    					Webview.this);
    			   
    			// set title
    			//alertDialogBuilder.setTitle("");

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
            }
        catch(Exception e){
        	Log.d("err "+e.toString(),"random1");
        	}
       locationValet = new LocationValet(this, new ILocationValetListener()
       {

           public void onBetterLocationFound(Location userLocation)
           {
              request.setUserLocation(userLocation);
          }
       });
      

        adViewFromXml.setMMRequest(request);

        adViewFromXml.getAd();
        
       adViewFromXml.setVisibility(View.VISIBLE);
       hidead.setVisibility(View.VISIBLE);
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
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	     if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) 
	    { 
	           int index = volumeSeekbar.getProgress(); 
	           volumeSeekbar.setProgress(index + 1); 
	           return true; 
	    } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
	     {
	           int index = volumeSeekbar.getProgress(); 
	           volumeSeekbar.setProgress(index - 1); 
	           return true; 
	    }
	     return super.onKeyDown(keyCode, event); 
	    }
	 
	 @Override
	  protected void onStart() {
	  super.onStart();
	  scringo.onStart();
	  FlurryAgent.onStartSession(this, "4DF4JJB7BHD8XZ47YDGX");
	  Intent mIntent = new Intent(this, mp3playerservice.class);
	        bindService(mIntent, mConnection, BIND_AUTO_CREATE);
	 }
	 
	@Override 
	protected void onNewIntent(Intent intent) 
	{ 
		super.onNewIntent(intent); 
		setIntent(intent);
		//Log.d("Webview", "onNewIntent"); 
		// cancel the playing code 
		//Log.d("Webview", "onNewIntent"); 
		Bundle extras;
		extras = getIntent().getExtras();
		mp.stop();
		mp.release();
		mp=new MediaPlayer();
        info=extras.getStringArray("feed");
        track=info[1];
        name=info[0];
        a1=true;
        if(isOnline()==true){
    	start1(track);
    	
    	 adViewFromXml.setMMRequest(request);
    	
    	 adViewFromXml.setVisibility(View.VISIBLE);
    	 hidead.setVisibility(View.VISIBLE);
		
    	 adViewFromXml.getAd();
        }else{
        	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					Webview.this);
			   
			// set title
			//alertDialogBuilder.setTitle("");

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
	// get the new feed 
	// play the new feed onResume 
	}
	@Override
	protected void onResume(){
		if(isOnline()==true){
			try{
			 if(!locationValet.startAquire(true))
			  {
	            //Log.d("Unable to start acquiring location, do you have the permissions declared?", "Tag123");

	            // Manifest.permission.ACCESS_FINE_LOCATION
	            // Manifest.permission.ACCESS_COARSE_LOCATION
			   }
			 }catch(Exception e){Log.d("error is"+e.toString(),"tag2");}
		 adViewFromXml.setVisibility(View.VISIBLE);
			 hidead.setVisibility(View.VISIBLE);
		super.onResume();
		
		}else{
			super.onResume();
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					Webview.this);
			   
			// set title
			//alertDialogBuilder.setTitle("");

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
		 if(handler != null)
		     handler.onResume();
		
	}
	
	@Override
	protected void onPause(){
		 locationValet.stopAquire();
		super.onPause();
		 if(handler != null)
		     handler.onPause();
	}
	TextView hidead;
	MMAdView adViewFromXml;
	private void init() {
		adViewFromXml = (MMAdView) findViewById(R.id.adView);
		hidead=(TextView)findViewById(R.id.ButtonAd);
		hidead.setOnClickListener(new View.OnClickListener() {
		@Override
        public void onClick(View v) {
			adViewFromXml.setVisibility(View.GONE);
			hidead.setVisibility(View.GONE);
        }
    });
		hidead.setVisibility(View.GONE);
		 handler = new RefreshHandler(adViewFromXml);
		btn_play = (Button)findViewById(R.id.btn_play);
		btn_play.setOnClickListener(this);
		btn_stop = (Button)findViewById(R.id.btn_stop);
		btn_stop.setOnClickListener(this);
		btn_stop.setEnabled(false);
		one=(TextView)findViewById(R.id.tv);
		two=(TextView)findViewById(R.id.tv1);
		two.setVisibility(View.GONE);
		frag1=(Button)findViewById(R.id.button2);
		frag1.setOnClickListener(this);
		frag2=(Button)findViewById(R.id.button3);
		frag2.setOnClickListener(this);
		frag3=(Button)findViewById(R.id.button4);//codes
		frag3.setOnClickListener(this);
		frag4=(Button)findViewById(R.id.button5);//top
		frag4.setOnClickListener(this);
		frag5=(ImageButton)findViewById(R.id.button6);//scringo
		frag5.setOnClickListener(this);
		frag1.setTextColor(Color.WHITE);
		frag2.setTextColor(0xFFCCCCCC);
		frag3.setTextColor(0xFFCCCCCC);
		frag4.setTextColor(0xFFCCCCCC);
		//Find the ad view for reference
		
		//Scringo.setDebugMode(true);//**********************************************************************************************
		//Log.d("p","he");
		butfavs=(Button)findViewById(R.id.buttonfavs);
		butfavs.setOnClickListener(this);
		wha2=(ImageView)findViewById(R.id.timefiller);
		wha1=(LinearLayout)findViewById(R.id.timell);
		wha1.setVisibility(View.VISIBLE);
		wha.setVisibility(View.VISIBLE);
		wha2.setVisibility(View.VISIBLE);
        butfavs.setVisibility(View.GONE);
		ten=(TextView)findViewById(R.id.onezero);
		ten.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	//changing colors
            	ten.setTextColor(Color.WHITE);
            	eleven.setTextColor(0xFFCCCCCC);
        		fourteen.setTextColor(0xFFCCCCCC);
        		other.setTextColor(0xFFCCCCCC);
            	//till here
        		String l="";
        		try{
        		InputStream is =getAssets().open("tencodes.txt");
        	    InputStreamReader iz=new InputStreamReader(is);
        	    BufferedReader bis = new BufferedReader(iz);
        	    String text="";
        	    while((l=bis.readLine())!=null){
        	    	text=text+l+"\n";
        	    }
            	System.out.println(text+"whogivesafuck");
        	    two.setText(text);}catch(Exception e){Log.d("error is "+e.toString(),"newguy");}
            }
        });
		eleven=(TextView)findViewById(R.id.oneone);
		eleven.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	eleven.setTextColor(Color.WHITE);
            	ten.setTextColor(0xFFCCCCCC);
        		fourteen.setTextColor(0xFFCCCCCC);
        		other.setTextColor(0xFFCCCCCC);
            	//till here
        		String l="";
        		try{
        		InputStream is =getAssets().open("elevencodes.txt");
        	    InputStreamReader iz=new InputStreamReader(is);
        	    BufferedReader bis = new BufferedReader(iz);
        	    String text="";
        	    while((l=bis.readLine())!=null){
        	    	text=text+l+"\n";
        	    }
            	System.out.println(text+"whogivesafuck");
        	    two.setText(text);}catch(Exception e){Log.d("error is "+e.toString(),"newguy");}
            }
        });
		
		fourteen=(TextView)findViewById(R.id.onefour);
		fourteen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	fourteen.setTextColor(Color.WHITE);
            	eleven.setTextColor(0xFFCCCCCC);
        		ten.setTextColor(0xFFCCCCCC);
        		other.setTextColor(0xFFCCCCCC);
            	//till here
        		String l="";
        		try{
        		InputStream is =getAssets().open("fourteencodes.txt");
        	    InputStreamReader iz=new InputStreamReader(is);
        	    BufferedReader bis = new BufferedReader(iz);
        	    String text="";
        	    while((l=bis.readLine())!=null){
        	    	text=text+l+"\n";
        	    }
            	System.out.println(text+"whogivesafuck");
        	    two.setText(text);}catch(Exception e){Log.d("error is "+e.toString(),"newguy");}
            }
        });
		
		other=(TextView)findViewById(R.id.other);
		other.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	other.setTextColor(Color.WHITE);
            	eleven.setTextColor(0xFFCCCCCC);
        		fourteen.setTextColor(0xFFCCCCCC);
        		ten.setTextColor(0xFFCCCCCC);
            	//till here
        		String l="";
        		try{
        		InputStream is =getAssets().open("othercodes.txt");
        	    InputStreamReader iz=new InputStreamReader(is);
        	    BufferedReader bis = new BufferedReader(iz);
        	    String text="";
        	    while((l=bis.readLine())!=null){
        	    	text=text+l+"\n";
        	    }
            	System.out.println(text+"whogivesafuck");
        	    two.setText(text);}catch(Exception e){Log.d("error is "+e.toString(),"newguy");}
            }
        });;
		sv1=(ScrollView)findViewById(R.id.scrollview1);
		sv1.setVisibility(View.GONE);
		a=(LinearLayout)findViewById(R.id.layout1);
		a.setVisibility(View.GONE);
		b=(ListView)findViewById(R.id.listview1);
		b.setVisibility(View.GONE);
		at=(LinearLayout)findViewById(R.id.layout11);
		codes=(LinearLayout)findViewById(R.id.layout111);
		codes.setVisibility(View.GONE);
		bt=(ListView)findViewById(R.id.listview11);
		bt.setVisibility(View.GONE);at.setVisibility(View.GONE);
		ten.setVisibility(View.GONE);
	    eleven.setVisibility(View.GONE);
		fourteen.setVisibility(View.GONE);
		other.setVisibility(View.GONE);
		try
        {
            volumeSeekbar = (SeekBar)findViewById(R.id.seekBar1);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() 
            {
            	
                @Override
                public void onStopTrackingTouch(SeekBar arg0) 
                {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) 
                {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) 
                {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
	}
	public void doWork() {
	    runOnUiThread(new Runnable() {
	        public void run() {
	            try{
	                	String hou, min, sec;
	                    Date dt = new Date();
	                    int hours = dt.getHours();
	                    int minutes = dt.getMinutes();
	                    int seconds = dt.getSeconds();
	                    hou=Integer.toString(hours);
	                    min=Integer.toString(minutes);
	                    sec=Integer.toString(seconds);
	                    if(hou.length()==1){
	                    	hou="0"+hou;
	                    }
	                    if(min.length()==1){
	                    	min="0"+minutes;
	                    }
	                    if(sec.length()==1){
	                    	sec="0"+seconds;
	                    }
	                    String curTime = hou + ":" + min + ":" + sec;
	                    wha.setText(curTime);
	            }catch (Exception e) {Log.d("yeah "+e.toString(),"yeah4");}
	        }
	    });
	}
	
	class CountDownRunner implements Runnable{
		
	    public void run() {
	            while(!Thread.currentThread().isInterrupted()){
	                try {
	                doWork();
	                    Thread.sleep(1000); // Pause of 1 Second
	                } catch (InterruptedException e) {
	                        Thread.currentThread().interrupt();
	                }catch(Exception e){
	                }
	            }
	    }
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_play:
			if(mBounded==true){
				startAudio(track);
				break;
			}
			else{
				start1(track);
				break;
			}
		case R.id.btn_stop:
			
				stopAudio();
				break;
		case R.id.button2:
			fragment1();
			if (frag1.isSelected()){
				frag1.setSelected(true);
	        } else {
	        	frag1.setSelected(false);
	        }
			wha.setVisibility(View.VISIBLE);
			wha1.setVisibility(View.VISIBLE);
			wha2.setVisibility(View.VISIBLE);
			butfavs.setVisibility(View.GONE);
			frag1.setTextColor(Color.WHITE);
			frag2.setTextColor(0xFFCCCCCC);
			frag3.setTextColor(0xFFCCCCCC);
			frag4.setTextColor(0xFFCCCCCC);
			codes.setVisibility(View.GONE);
			ten.setVisibility(View.GONE);
		    eleven.setVisibility(View.GONE);
			fourteen.setVisibility(View.GONE);
			other.setVisibility(View.GONE);
			break;
		case R.id.button3:
			fragment2();
			wha.setVisibility(View.GONE);
			wha1.setVisibility(View.GONE);
			wha2.setVisibility(View.GONE);
			frag1.setTextColor(0xFFCCCCCC);
			frag2.setTextColor(Color.WHITE);
			frag3.setTextColor(0xFFCCCCCC);
			frag4.setTextColor(0xFFCCCCCC);
			codes.setVisibility(View.GONE);
			ten.setVisibility(View.GONE);
		    eleven.setVisibility(View.GONE);
			fourteen.setVisibility(View.GONE);
			other.setVisibility(View.GONE);
			butfavs.setVisibility(View.VISIBLE);
			break;
		case R.id.button4:
			fragment3();
			wha.setVisibility(View.GONE);
			wha1.setVisibility(View.GONE);
			wha2.setVisibility(View.GONE);
			frag1.setTextColor(0xFFCCCCCC);
			frag2.setTextColor(0xFFCCCCCC);
			frag3.setTextColor(Color.WHITE);
			frag4.setTextColor(0xFFCCCCCC);
			butfavs.setVisibility(View.GONE);
			break;
		case R.id.button5:
			topfeed();
			wha.setVisibility(View.GONE);
			wha1.setVisibility(View.GONE);
			wha2.setVisibility(View.GONE);
			frag1.setTextColor(0xFFCCCCCC);
			frag2.setTextColor(0xFFCCCCCC);
			frag3.setTextColor(0xFFCCCCCC);
			frag4.setTextColor(Color.WHITE);
			codes.setVisibility(View.GONE);
			ten.setVisibility(View.GONE);
		    eleven.setVisibility(View.GONE);
			fourteen.setVisibility(View.GONE);
			butfavs.setVisibility(View.GONE);
			other.setVisibility(View.GONE);
			break;
		case R.id.button6:
			scringo.openMenu() ;
		break;
		case R.id.buttonfavs:
			addtofaves();//............................................................................................
			break;
		default:
			break;
		}
	}
	
	//******************************************************************************************************************************************
    @Override
	public void onDestroy(){
		super.onDestroy();
		if(mBounded==true){
			unbindService(mConnection);
		}
	}
    
	void addtofaves(){
		favsname=mServer.trackname();
		favsfeed=mServer.track;
		InputStream fis=null;
		Boolean alreadyexists=false;
	//count
	boolean empty = true;
	int count = 0;
    try {
    	fis = app.openFileInput("favs.txt");
    	BufferedInputStream bis = new BufferedInputStream(fis);
        byte[] c = new byte[1024];
        int readChars = 0;
        while ((readChars = bis.read(c)) != -1) {
        	//Log.d("as "+readChars,"asdf");
            empty = false;
            for (int i = 0; i < readChars; ++i) {
                if (c[i] == '\n') {
                    ++count;
                }
            }
        }
        //Log.d("count is "+count,"HERERE");
        count=count+1;
    }catch(Exception e){
    	Log.d("Error is3 "+e.toString(),"gotcha123");
    }
    //count end
    try{
    fis.close();
	fis = app.openFileInput("favs.txt");
	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	String z;
			for(int i=0;i<count;i++){
	        	z=br.readLine();
	        	if (z.contains(favsname)){
	        		alreadyexists=true;
	        	}
	}
			}catch(Exception e){
				Log.d("error issig "+e.toString(),"gotcha123");
				}
        //adding
    if(alreadyexists==false){
    try{
		FileOutputStream fos = null;
		fos = app.openFileOutput("favs.txt", Context.MODE_APPEND);
		if(empty==false){
		fos.write(("\n"+favsname+";"+favsfeed).getBytes());
		}
		else{
			fos.write((favsname+";"+favsfeed).getBytes());
			}
		fos.close();
		Toast.makeText(Webview.this, "Feed added to favorites",  Toast.LENGTH_SHORT ).show();
	}catch(Exception e){
		Log.d("error is "+e.toString(),"gotcha123");
		}
    }
    else{
    	Toast.makeText(Webview.this, "Feed already in favorites",  Toast.LENGTH_SHORT ).show();
    }
	 fragment2();
	}
	
	void fragment3(){try{
		//showing TextViews
		ten.setTextColor(Color.WHITE);
		eleven.setTextColor(0xFFCCCCCC);
		fourteen.setTextColor(0xFFCCCCCC);
		other.setTextColor(0xFFCCCCCC);
		String l="";
		InputStream is =getAssets().open("tencodes.txt");
	    InputStreamReader iz=new InputStreamReader(is);
	    BufferedReader bis = new BufferedReader(iz);
	    try{
	    	String text="";
	    while((l=bis.readLine())!=null){
	    	text=text+l+"\n";
	    }
    	System.out.println(text+"whogivesafuck");
    	codes.setVisibility(View.VISIBLE);
    	ten.setVisibility(View.VISIBLE);
	    eleven.setVisibility(View.VISIBLE);
		fourteen.setVisibility(View.VISIBLE);
		other.setVisibility(View.VISIBLE);
	    two.setText(text);
	}catch(Exception a){Log.d("error is "+a.toString(),"errorqq");}
	    ImageView iv=(ImageView) findViewById(R.id.imageView9);
		iv.setImageResource(R.drawable.fragfavs);  
	    two.setBackgroundResource(R.drawable.fragfavs);
	    a.setVisibility(View.GONE);
		b.setVisibility(View.GONE);
	    two.setVisibility(View.VISIBLE);//
	    sv1.setVisibility(View.VISIBLE);//
	    bt.setVisibility(View.GONE);at.setVisibility(View.GONE);
	    iv.setVisibility(View.VISIBLE);
	}catch(Exception e){Log.d("error is "+e.toString(),"errorbro");}
	}
	ImageView iv;
	
	void fragment1(){
		iv=(ImageView) findViewById(R.id.imageView9);
		a.setVisibility(View.GONE);
		b.setVisibility(View.GONE);
		two.setVisibility(View.GONE);
		sv1.setVisibility(View.GONE);
		iv.setImageResource(R.drawable.frag11);  
		bt.setVisibility(View.GONE);at.setVisibility(View.GONE);
	}
	//contextmenu
	
	@Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
      }
	
	public boolean onContextItemSelected(MenuItem item) {
		  InputStream fis=null;
		  boolean empty=true;
		  int count=0;
	      AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	      //stupid crap
	      try {
	      	fis = app.openFileInput("favs.txt");
	      	BufferedInputStream bis = new BufferedInputStream(fis);
	          byte[] c = new byte[1024];
	          
	          int readChars = 0;
	          
	          while ((readChars = bis.read(c)) != -1) {
	          	//Log.d("as "+readChars,"asdf");
	              empty = false;
	              for (int i = 0; i < readChars; ++i) {
	                  if (c[i] == '\n') {
	                      ++count;
	                  }
	              }
	          }
	         // Log.d("count is "+count,"HEREREhe");
	          count=count+1;
	      }catch(Exception e){
	      	Log.d("Error is3 "+e.toString(),"gotcha123");
	      }//end of stupid crap
	      BufferedReader br=null;
	      try{
	      fis = app.openFileInput("favs.txt");
	  	  br = new BufferedReader(new InputStreamReader(fis));
	  	  
	      }catch(Exception e){Log.d("idgaf","idgaf");}
	      String[] favsarray=new String[count];
	      try{
	      for(int i=0;i<count;i++){
	    	  favsarray[i]=br.readLine();
	      }
	      }catch(Exception e){Log.d("idgaf","idgaf");}
	      String listItemName = favsarray[info.position];
	      switch(item.getItemId()) {
	      case R.id.delete:
	    	  	//deleting
	    	  FileInputStream fis1=null;
	    	  BufferedReader br1=null;
	    	  FileOutputStream fos1=null;
	    	  Boolean lastoneisbad=false;
	    	  try{
	    	  fis1 = app.openFileInput("favs.txt");
		  	  br1 = new BufferedReader(new InputStreamReader(fis1));
		  	  fos1 = app.openFileOutput("favstemp.txt", Context.MODE_PRIVATE);
	    	  Boolean printed=false;
	    	  String h=null;
	    	  for(int i=0;i<count;i++){
	    		  if(printed==true&&i!=count-1){
	    			  fos1.write(("\n").getBytes());
	    		  }
	    		  h=br1.readLine();
	    		  //Log.d(h,"whagwan");
	    		 // Log.d(listItemName,"whagwan");
	    		  if(h.contains(listItemName)){
	    			  printed=false;
	    			  continue;
	    		  }
	    		  else{
	    			  if((i==count-1)&&printed!=false){
		    			  fos1.write(("\n").getBytes());
		    		  }
	    			  fos1.write((h).getBytes());
	    			  printed=true;
	    		  }
	    	  }
	    	  br1.close();
	    	  fis1.close();
	    	  fos1.close();
	    	  //renaming
	    	  String j=null;
	    	  try{
		        		boolean exists1=false;
	    		        File fav = null;
	    		        boolean deleted =false;
	    		        try{
	    		        	fav =ba.getFileStreamPath("favs.txt");
  		        		if(fav.exists()){
  		        			exists1=true;
  		        			deleted = fav.delete(); 
  		        		}
  		        		}
	    		        catch(Exception e){
  		        			Log.d("yo "+e.toString(),"gotcha");
  		        		}if(deleted==true){
  		        			File from      = new File(ba.getFilesDir(), "favstemp.txt");
  		        	        File to        = new File(ba.getFilesDir(), "favs.txt");
  		        	        from.renameTo(to);
  		        		}
		        	}catch(Exception f){Log.d("error1j54 "+ f.toString(),"gotcha");}
	    	  //renamed
	    	  }catch(Exception e){Log.d("error is "+e.toString(),"wookey");}
	    	//deleted feed
	    	    fragment2();
	            return true;
	      default:
	            return super.onContextItemSelected(item);
	      }
	}
	//favourites list stuff
	String info1[]=new String[2];
	Boolean h=false;
	String[] quack;
	String[] add;
	
	void fragment2(){
	sv1.setVisibility(View.GONE);
		int v=0;
		try{
			ListView lv;
			lv= (ListView)findViewById(R.id.listview1);
			registerForContextMenu(lv);//
			List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
			//*******************
			FileOutputStream fos = null;
			FileInputStream fis=null;
        	File fileq = null;
        	try{
        	fileq = ba.getFileStreamPath("favs.txt");}catch(Exception e){Log.d("er1 "+e.toString(),"gotcha");}
        	//Log.d(Boolean.toString(fileq.exists()),"gotcha123");
        	if(fileq.exists()==true){
        		try{
        		fis = app.openFileInput("favs.txt");
        	}catch(Exception f){Log.d("error is1 "+ f.toString(),"gotcha123");}
    		}
    		else{
            try{
            	fos = app.openFileOutput("favs.txt", Context.MODE_PRIVATE);
        		fos.close();
    			fis = app.openFileInput("favs.txt");
            }catch(Exception f){Log.d("error is2 "+ f.toString(),"gotcha123");}
    		}
        	boolean empty = true;
		    try {
		    	BufferedInputStream bis = new BufferedInputStream(fis);
		        byte[] c = new byte[1024];
		        int count = 0;
		        int readChars = 0;
		        while ((readChars = bis.read(c)) != -1) {
		        	//Log.d("as "+readChars,"asdf");
		            empty = false;
		            for (int i = 0; i < readChars; ++i) {
		                if (c[i] == '\n') {
		                    ++count;
		                }
		            }
		        }
		        //Log.d("count is "+count,"HERERE");
		        v=count+1;
		    }catch(Exception e){
		    	Log.d("Error is3 "+e.toString(),"gotcha123");
		    }
		    fis.close();
    		fis = app.openFileInput("favs.txt");
    		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
    		if(empty==true){
    		}
    		else{
    		quack=new String[v];
    		add=new String[v];
    		String[] quack1;
    		String z;
    				for(int i=0;i<v;i++){
    		        	z=br.readLine();
    		        	//Log.d(z,"readline isss");
    		            quack1=z.split(";");
    		            //Log.d(quack1[0],"readline issss");
    		        	quack[i]=quack1[0];
    		        	add[i]=quack1[1];
    		        }
    		}
		String[] from = new String[] {"col_1"};
		int[] to = new int[] {R.id.itemq};
		iv=(ImageView) findViewById(R.id.imageView9);
		iv.setImageResource(R.drawable.fragfavs);  
		a.setVisibility(View.VISIBLE);
		b.setVisibility(View.VISIBLE);
		two.setVisibility(View.GONE);
		bt.setVisibility(View.GONE);at.setVisibility(View.GONE);
		if(empty==false){
		for(int q = 0; q <v; q++){
        	HashMap<String, String> map = new HashMap<String, String>();
        	map.put("col_1", quack[q]);
        	fillMaps.add(map);
        	lv.setOnItemClickListener(onListClick11);
        }
		SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.grid_itemq, from, to);
        lv.setAdapter(adapter);
		}else{
			a.setVisibility(View.VISIBLE);
			b.setVisibility(View.GONE);
		}
		}catch(Exception e){Log.d("error is "+e.toString(),"error came up bro");}
		}
	
	void startAudio(String a){
		btn_play.setEnabled(false);
		btn_play.setVisibility(View.GONE);
		btn_stop.setVisibility(View.VISIBLE);
		btn_stop.setEnabled(true);
		mServer.startAudio(a);
	}
	
	void start1(String a){
		btn_play.setEnabled(false);
		btn_play.setVisibility(View.GONE);
		btn_stop.setVisibility(View.VISIBLE);
		btn_stop.setEnabled(true);
		try{
		Intent intent = new Intent(this, mp3playerservice.class);
		intent.putExtra("info",info);
		//Log.d("info 0"+info[0],"okay");Log.d("info 1"+info[1],"okay");
		//Log.d("sending intent to service","okay");
		startService(intent);
		//Log.d("sent intent to service","okay");
		}
		catch(Exception e)
		{AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				Webview.this);
		   
		// set title
		//alertDialogBuilder.setTitle("");

		// set dialog message
		alertDialogBuilder
			.setMessage("Unable to connect. Server could be down, or check your Internet connection.")
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
			
		
		   
			
			//Log.d("Error came up man "+e.toString(),", check the internet connection and stuff..");
	}
	}
	
	public void onPrepared(MediaPlayer mp){
		mp.start();
	}
	
	private void stopAudio() {try{
		if(mBounded==true){
		//mp.stop();
			mServer.stopAudio();
		btn_play.setVisibility(View.VISIBLE);
		btn_play.setEnabled(true);
		btn_stop.setEnabled(false);
		btn_stop.setVisibility(View.GONE);
		}
	}catch(Exception e){
		Log.d("error is "+e.toString(),"error");
		}
	}
	
		 @Override
		 protected void onStop() {
		  super.onStop();
		  scringo.onStop();
		  FlurryAgent.onEndSession(this); 
		  if(mBounded) {
		   unbindService(mConnection);
		   mBounded = false;
		  }
		 }
		 
		 private AdapterView.OnItemClickListener onListClick11=new AdapterView.OnItemClickListener(){
			    public void onItemClick(AdapterView<?> parent,View view, int position, long id)
			    {
			    	//Log.d("onitemclick invoked man","value of v");
			    	try{
			    		InputStream fi=null;
			    		Boolean empty=true;
			    		int count =0;
			    		String[] addresses;
			    		String[] names;
			    		//**********************************************************************************************************************************************
			    		try {
			    	      	fi = app.openFileInput("favs.txt");
			    	      	BufferedInputStream bis = new BufferedInputStream(fi);
			    	          byte[] c = new byte[1024];
			    	          int readChars = 0;
			    	          while ((readChars = bis.read(c)) != -1) {
			    	          	//Log.d("as "+readChars,"asdf");
			    	              empty = false;
			    	              for (int i = 0; i < readChars; ++i) {
			    	                  if (c[i] == '\n') {
			    	                      ++count;
			    	                  }
			    	              }
			    	          }
			    	          //Log.d("count is "+count,"HEREREhe");
			    	          count=count+1;
			    	      }catch(Exception e){
			    	      	//Log.d("Error is3 "+e.toString(),"gotcha123");
			    	      }//end of stupid crap
			    		fi.close();
			    		addresses=new String[count];
			    		names=new String[count];
			    	      BufferedReader br=null;
			    	      try{
			    	      fi = app.openFileInput("favs.txt");
			    	  	  br = new BufferedReader(new InputStreamReader(fi));
			    	  	  String hj;
			    	  	  String[] hk;
			    	  	  for(int i=0;i<count;i++){
			    	  		  hj=br.readLine();
			    	  		  hk=hj.split(";");
			    	  		  addresses[i]=hk[1];
			    	  		  names[i]=hk[0];
			    	  	  }
			    	      }catch(Exception e){Log.d("yo","yo");}
			    		info1[0]=names[position];
			    		info1[1]=addresses[position];
			    		btn_play.setEnabled(false);
			    		btn_play.setVisibility(View.GONE);
			    		btn_stop.setVisibility(View.VISIBLE);
			    		btn_stop.setEnabled(true);
					Intent intent1 = new Intent(Webview.this, mp3playerservice.class);
					intent1.putExtra("info",info1);
					//Log.d("info1 0"+info[0],"okay");Log.d("info1 1"+info[1],"okay");
					//Log.d("sending intent to service","okay");
					one.setText(info1[0]);
					startService(intent1);
					//Log.d("sent intent to service","okay");
					}
					catch(Exception e)
					{Log.d("Error came up man "+e.toString(),", check the internet connection and stuff..");
				}
			    }
			};
			
		 private AdapterView.OnItemClickListener onListClick=new AdapterView.OnItemClickListener(){
			    public void onItemClick(AdapterView<?> parent,View view, int position, long id)
			    {
			    	//Log.d("onitemclick invoked man","value of v");
			    	try{
			    		info1[0]=quack[position];
			    		info1[1]=add[position];
			    		btn_play.setEnabled(false);
			    		btn_play.setVisibility(View.GONE);
			    		btn_stop.setVisibility(View.VISIBLE);
			    		btn_stop.setEnabled(true);
					Intent intent1 = new Intent(Webview.this, mp3playerservice.class);
					intent1.putExtra("info",info1);
					//Log.d("info1 0"+info[0],"okay");Log.d("info1 1"+info[1],"okay");
					//Log.d("sending intent to service","okay");
					one.setText(info1[0]);
					startService(intent1);
					//Log.d("sent intent to service","okay");
					}
					catch(Exception e)
					{Log.d("Error came up man "+e.toString(),", check the internet connection and stuff..");
				}
			    }
			};
			
			public int count(String filename) throws IOException {
				InputStream is = getAssets().open(filename);
				BufferedInputStream bis = new BufferedInputStream(is);
			    try {
			        byte[] c = new byte[1024];
			        int count = 0;
			        int readChars = 0;
			        boolean empty = true;
			        while ((readChars = bis.read(c)) != -1) {
			        	//Log.d("as "+readChars,"asdf");
			            empty = false;
			            for (int i = 0; i < readChars; ++i) {
			                if (c[i] == '\n') {
			                    ++count;
			                }
			            }
			        }//Log.d("count is "+count,"HERERE");
			        return (count == 0 && !empty) ? 1 : count+1;
			    } finally {
			        bis.close();
			    }
			}
			
			public void topfeed(){
				//cache
				File file3=null;;
		    	Boolean shouldupdate=true;
		    	Calendar c = Calendar.getInstance(); 
		    	try{
		    		file3 =getBaseContext().getFileStreamPath("timeOfUpdate.txt");}catch(Exception e){Log.d("er1 "+e.toString(),"qwaszx");
		    		}
		    	if(file3.exists()){
		    		String line="";
		    		String arr[];
		    		int hour=0;
		    		int minute=0;
		    		int day=0;
		    		int hour1=c.get(Calendar.HOUR_OF_DAY);;
		    		int minute1=c.get(Calendar.MINUTE);;
		    		int day1=c.get(Calendar.DAY_OF_MONTH);;
		    		BufferedReader br=null;
		    		try{
		    		InputStream pis = getApplicationContext().openFileInput("timeOfUpdate.txt");
					br = new BufferedReader(new InputStreamReader(pis));
					line=br.readLine();
		    		arr=line.split(";");
		    		hour=Integer.parseInt(arr[0]);
		    		minute=Integer.parseInt(arr[1]);
		    		day=Integer.parseInt(arr[2]);
		    		}catch(Exception e)
					{
						Log.d("error is  "+e.toString(),"qwaszx");
					}
		    		if(day1==day&&hour1==hour&&(minute1<=minute+15)){
		    			shouldupdate=false;
		    		}else if(day1==day&&(hour1==hour+1)&&(minute1+60)<=minute+15){
		    			shouldupdate=false;
		    		}
		    		
		    	}else{
		    		shouldupdate=true;
		    	}
				//cache
		    	
				ctz=this.getApplicationContext();
				ctr=this.getBaseContext();
				if(shouldupdate==true){
				new RetreiveFeedTask().execute(ctr,ctz);
				}else{
			    		int count = 0;
			    		String h[]=null;
			    		try{
			    			
			    			InputStream is = getApplicationContext().openFileInput("TOPFEEDS.txt");
			    			BufferedInputStream bis1 = new BufferedInputStream(is);
			    		    try {
			    		        byte[] c1 = new byte[1024];
			    		        int readChars = 0;
			    		        while ((readChars = bis1.read(c1)) != -1) {
			    		            for (int i = 0; i < readChars; ++i) {
			    		                if (c1[i] == '\n') {
			    		                    ++count;
			    		                }
			    		            }
			    		        }//Log.d("count isa "+count,"HEREREre");
			    		    } finally {
			    		        bis1.close();
			    		        is.close();
			    		    }
			    		h=new String[count];
			    		try{
			    				String a;
			    				int q=0;
			    				InputStream pis = getApplicationContext().openFileInput("TOPFEEDS.txt");
			    				BufferedReader br = new BufferedReader(new InputStreamReader(pis));
			    				while((a=br.readLine())!=null){
			    					h[q]=a;
			    					q=q+1;
			    				}
			    				br.close();
			    				pis.close();
			    		}catch(Exception e){Log.d("Error is "+e.toString(),"TAGER" );
			    		}
			    		}catch(Exception e){Log.d("error is "+e.toString(),"t1");}
			    		gff=true;
			        	asynchelp(h);
			    	}
				
				iv=(ImageView) findViewById(R.id.imageView9);
				iv.setImageResource(R.drawable.fragfavs); 
				a.setVisibility(View.GONE);
				b.setVisibility(View.GONE);
				two.setVisibility(View.GONE);
				sv1.setVisibility(View.GONE);
				bt.setVisibility(View.VISIBLE);
				at.setVisibility(View.VISIBLE);
			}
			
			public class RetreiveFeedTask extends AsyncTask<Context, Void, String[]>{
		    	String[] q;
		    	@Override
		    	protected String[] doInBackground(Context... params) {
		    		String address="http://www.broadcastify.com/listen/top";
		        	String h[]=null;
		        	Context base,app;
		        	base=params[0];
		        	app=params[1];
		    		try{
		    			URL url = new URL(address);
		    		    BufferedReader reader = null;
		    		    boolean empty=false;
		    		    boolean hasbeeninthere=false;
		    		        reader = new BufferedReader(new InputStreamReader(url.openStream()));
		    		        int z=0;
		    		        String y="garbage";
		    		        String line = null;
		    		        int one=0;
		    		        boolean exists=false;
		    		        File file3 = null;
		    		        try{
	    		        		file3 =base.getFileStreamPath("TOPFEEDS.txt");
	    		        		if(file3.exists()){
	    		        			exists=true;
	    		        			}
	    		        		file3=null;
	    		        		}
		    		        catch(Exception e){
	    		        			Log.d("1 "+e.toString(),"gotcha");
	    		        		}
		    		        try{
		    		        	//Log.d("exists="+ Boolean.toString(exists),"finals");
		    		        boolean numerous=false;
		    		        Context context=app;
		    		        while ((line = reader.readLine()) != null) {
		    		        	
		    		        	if(z==0){
		    		        		InputStream is=null;
		    		        		try{
		    		        		if(exists==true){
		    		        			is = app.openFileInput("TOPFEEDS.txt");
		    		        		}
		    		        		else{
		    		        			//Log.d("here 1","finals");
		    		        			String filepath=context.getFilesDir().getPath().toString() + "/TOPFEEDS.txt";
		    		        			File file1 = new File(filepath);
		    		        			FileOutputStream fOut = app.openFileOutput("TOPFEEDS.txt", MODE_PRIVATE);
		    		        			fOut.close();
		    		        			is = app.openFileInput("TOPFEEDS.txt");
		    		        			//Log.d("here 2","finals");
		    		        		}
		    		        		}catch(Exception f){
		    		        			Log.d("error11 "+ f.toString()+line,"gotcha");
		    		        			}
		    		        		InputStreamReader iz=new InputStreamReader(is);
		    		        	BufferedReader br = new BufferedReader(iz);     
		    		        	if (br.readLine() == null ) {
		    		        		empty=true;
		    		        	    System.out.println("No errors, and file empty");
		    		        	}
		    		        		br.close();
		    		        		iz.close();
		    		        		is.close();
		    		        		z=1;
		    		        	}
		    		        	if(empty==true){
		    		        		boolean nevergotin=true;
		    		        		Context ctx=app;
		    		        		AssetManager am = ctx.getAssets();
		    		        		FileOutputStream fos = null;try{
		    		        		fos = app.openFileOutput("TOPFEEDS.txt", Context.MODE_APPEND);
		    		        		}catch(Exception f){
		    		        			Log.d("error12 "+ f.toString()+line,"gotcha");
		    		        			}
		    		        	if(line.contains("<a href=\"/listen/ctid/")&&one!=1){
		    		        		nevergotin=false;
		    		        		int start = line.indexOf("<a href=\"/listen/ctid/");
		    		                int end = line.indexOf("</a><br />");
		    		                String a="";
		    		                String c="";
		    		                a=(line.substring(start + "<a href=\"/listen/ctid/".length(), end));
		    		                String[] b=a.split("\"");
		    		                c=b[0];
		    		                fos.write((c+";").getBytes());
		    		                one=1;
		    		                fos.close();
		    		        	}else if(line.contains("Numerous")&&one!=2){
		    		        		nevergotin=false;
		    		        		String c="";
		    		                c="Numerous";
		    		                fos.write((c+";").getBytes());
		    		                one=2;
		    		                numerous=true;
		    		                fos.close();
		    		        	}else if(line.contains("<a href=\"/listen/feed/")&&(!line.contains("class=\"button-info\""))&&(!line.contains("<span class="))&&(!line.contains("<!DOCTYPE html>")))
		    		        	{ 
		    		        		nevergotin=false;
		    		        		int end=0;
		    		        		Boolean endisnot=false;
		    		        		if(line.endsWith("</a></td>")){end = line.indexOf("</a></td>");}
		    		        		else if(line.endsWith("</a><br /><br />")){end = line.indexOf("</a><br /><br />");}
		    		        		else{endisnot=true;}
		    		        		int start = line.indexOf("<a href=\"/listen/feed/");
		    		                String a="";
		    		                String c="";
		    		                if(endisnot==false){
		    		                a=(line.substring(start + "<a href=\"/listen/feed/".length(), end));
		    		                }else{a=(line.substring(start + "<a href=\"/listen/feed/".length()));}
		    		                String[] b=a.split("\"");
		    		                c=b[1];	
		    		                c=c.substring(1,c.length());
		    		                if(numerous==false){
		    		                fos.write((c+"\n").getBytes());
		    		                fos.close();
		    		                }
		    		                else {String website="http://www.broadcastify.com/scripts/playlists/ep.php?feedId="+b[0];
		    	                	URL web = new URL(website);
		    	                	BufferedReader reader1 = new BufferedReader(new InputStreamReader(web.openStream()));
		    	                	String line1="";
		    	                	String feed="";
		    	                	while ((line1 = reader1.readLine()) != null) {
		    	                		if(line1.contains("<location>")){
		    	                			System.out.println("line1 "+line1);
		    	                			feed=line1.substring(11,line1.length()-11);
		    	                			break;
		    	                		}
		    	                	}
		    	                	 fos.write((c+"#"+feed+"\n").getBytes());
		    			             fos.close();
		    	                	 }
		    		                FileOutputStream fos1 = app.openFileOutput("timeOfUpdate.txt", Context.MODE_PRIVATE);
		    		    	        Calendar c1 = Calendar.getInstance(); 
		    		    	        int minute = c1.get(Calendar.MINUTE);
		    		    	        int hour = c1.get(Calendar.HOUR_OF_DAY);
		    		    	        int day = c1.get(Calendar.DAY_OF_MONTH);
		    		    	        fos1.write((Integer.toString(hour)+";"+Integer.toString(minute)+";"+Integer.toString(day)).getBytes());
		    		                one=3;
		    		                numerous=false;
		    		                fos1.close();
		    		        }
		    		        	if(nevergotin==true){
		    		        		fos.close();
		    		        	}
		    		        }
		    		        	else{
		    		        		boolean nevergotin=true;
		    		        	FileOutputStream fos = null;
		    		        	File fileq = null;
		    		        	
		    		        	try{
		    		        	fileq = base.getFileStreamPath("TOPFEEDSTEMP1.txt");}catch(Exception e){Log.d("er1 "+e.toString(),"gotcha");}
		    		        	if(fileq.exists()==true){
		    		        		try{
		    		        		fos = app.openFileOutput("TOPFEEDSTEMP1.txt", Context.MODE_APPEND);
		    		        		
		    		        	}catch(Exception f)
		    		        	{Log.d("error13 "+ f.toString()+line,"gotcha");}
				        		}
				        		else{
				        			try{
				        			//String filepath=context.getFilesDir().getPath().toString() + "/TOPFEEDSTEMP1.txt";
				        			//File file1 = new File(filepath);
				        			fos = app.openFileOutput("TOPFEEDSTEMP1.txt", Context.MODE_APPEND);
				        			
				        			}catch(Exception f){
				        				Log.d("error14 "+ f.toString()+line,"gotcha");
				        				}
				        		}
		    		        	if(line.contains("<a href=\"/listen/ctid/")&&one!=1){
		    		        		nevergotin=false;
		    		        		int start = line.indexOf("<a href=\"/listen/ctid/");
		    		                int end = line.indexOf("</a><br />");
		    		                String a="";
		    		                String c="";
		    		                a=(line.substring(start + "<a href=\"/listen/ctid/".length(), end));
		    		                String[] b=a.split("\"");
		    		                c=b[0];
		    		                fos.write((c+";").getBytes());
		    			            fos.close();
		    		                one=1;
		    		        	}else if(line.contains("Numerous")&&one!=2){
		    		        		nevergotin=false;
		    		        		String c="";
		    		                c="Numerous";
		    		                fos.write((c+";").getBytes());
		    			            fos.close();
		    		                one=2;
		    		                numerous=true;
		    		        	}else if(line.contains("<a href=\"/listen/feed/")&&(!line.contains("class=\"button-info\""))&&(!line.contains("<span class="))&&(!line.contains("<!DOCTYPE html>")))
		    		        	{ 	nevergotin=false;
		    		        		int end=0;
		    		        		Boolean endisnot=false;
		    		        		StringBuffer sb=new StringBuffer(line);
		    		        		if(line.endsWith("</a></td>")){end = line.indexOf("</a></td>");}
		    		        		else if(line.endsWith("</a><br /><br />")){end = line.indexOf("</a><br /><br />");}
		    		        		else{endisnot=true;}
		    		        		int start = line.indexOf("<a href=\"/listen/feed/");
		    		                String a="";
		    		                String c="";
		    		                if(endisnot==false){
		    		                a=(line.substring(start + "<a href=\"/listen/feed/".length(), end));
		    		                }else{a=(line.substring(start + "<a href=\"/listen/feed/".length()));}
		    		                String[] b=a.split("\"");
		    		                c=b[1];	
		    		                c=c.substring(1,c.length());
		    		                if(numerous==false){
		    		                	fos.write((c+"\n").getBytes());
		    				            fos.close();
		    			                }
		    			                else {
		    			                	String website="http://www.broadcastify.com/scripts/playlists/ep.php?feedId="+b[0];
		    			                	URL web = new URL(website);
		    			                	BufferedReader reader1 = new BufferedReader(new InputStreamReader(web.openStream()));
		    			                	String line1="";
		    			                	String feed="";
		    			                	while ((line1 = reader1.readLine()) != null) {
		    			                		if(line1.contains("<location>")){
		    			                			System.out.println("line1 "+line1);
		    			                			feed=line1.substring(11,line1.length()-11);
		    			                			break;
		    			                		}
		    			                	}
		    			                	fos.write((c+"#"+feed+"\n").getBytes());
		    					            fos.close();
		    			                	}
		    		                numerous=false;
		    		                one=3;
		    		        }
		    		        	if(nevergotin==true){
		    		        		fos.close();
		    		        	}
		    		        	hasbeeninthere=true;
		    		        	FileOutputStream fos1 = app.openFileOutput("timeOfUpdate.txt", Context.MODE_PRIVATE);
		    	    	        Calendar c1 = Calendar.getInstance(); 
		    	    	        int minute = c1.get(Calendar.MINUTE);
		    	    	        int hour = c1.get(Calendar.HOUR_OF_DAY);
		    	    	        int day = c1.get(Calendar.DAY_OF_MONTH);
		    	    	        fos1.write((Integer.toString(hour)+";"+Integer.toString(minute)+";"+Integer.toString(day)).getBytes());
		    	    	        fos1.close();
		    		        }
		    		        }
		    		        if(empty==false&&hasbeeninthere==true){
		    		        	String j=null;
		    		        	try{
		    		        		boolean exists1=false;
				    		        File TOPFEEDS = null;
				    		        boolean deleted =false;
				    		        try{
				    		        	TOPFEEDS =base.getFileStreamPath("TOPFEEDS.txt");
			    		        		if(TOPFEEDS.exists()){
			    		        			exists1=true;
			    		        			deleted = TOPFEEDS.delete(); 
			    		        		}
			    		        		TOPFEEDS=null;
			    		        		}
				    		        catch(Exception e){
			    		        			Log.d("yo "+e.toString(),"gotcha");
			    		        		}if(deleted==true){
			    		        			File from= new File(base.getFilesDir(), "TOPFEEDSTEMP1.txt");
			    		        	        File to = new File(base.getFilesDir(), "TOPFEEDS.txt");
			    		        	        from.renameTo(to);
			    		        	        from=null;
			    		        	        to=null;
			    		        		}
		    		        	}catch(Exception f){
		    		        		Log.d("error1j54 "+ f.toString()+line,"gotcha");
		    		        		}
		    		        }
		    		    }catch(Exception f){
		    		    	Log.d("error1 "+ f.toString()+line,"gotchaaaa");
		            }
		    		}catch(Exception e){
		    			Log.d("error2 "+ e.toString(),"gotcha");
		    			}
		    		int g=0;
		    		int count = 0;
		    		BufferedInputStream bis1=null; 
		    		InputStream is =null;
		    		try{
		    			File from=base.getFileStreamPath("TOPFEEDS.txt");
		    			Boolean a=false;
		    			Boolean q=from.exists();
		    			//Log.d("q "+q.toString(),"gotcha");
		    			a=from.canRead();
		    			//Log.d("a before "+q.toString(),"gotcha");
		    			if(a==false){
		    				from.setReadable(true);
		    			}
		    			//Log.d("a after "+q.toString(),"gotcha");
		    			is = app.openFileInput("TOPFEEDS.txt");}catch(Exception e){System.out.println("error3 4is "+e.toString()+"gotcha");}
		    			bis1 = new BufferedInputStream(is);
		    		    try {
		    		        byte[] c = new byte[1024];
		    		        int readChars = 0;
		    		        boolean empty = true;
		    		        while ((readChars = bis1.read(c)) != -1) {
		    		            empty = false;
		    		            for (int i = 0; i < readChars; ++i) {
		    		                if (c[i] == '\n') {
		    		                    ++count;
		    		                }
		    		            }
		    		        }
		    		      //  Log.d("count isa "+count,"HEREREre");
		    		    }
		    		    catch(Exception e){
		    		    	Log.d("idgaf"+e.toString(),"gotcha");
		    		    	}
		    		    finally {
		    		    	try{
		    		        bis1.close();
		    		        is.close();
		    		    }
		    		    catch(Exception e){
		    		    	Log.d("idgaf1"+e.toString(),"gotcha");
		    		    	}
		    		    }
		    		h=new String[count];
		    		try{
		    				String a;
		    				int q=0;
		    				File from1= new File(base.getFileStreamPath("TOPFEEDS.txt"), "TOPFEEDS.txt");
			    			Boolean y=false;
			    			y=from1.canRead();
			    			if(y==false){
			    				from1.setReadable(true);
			    			}
		    				InputStream pis = app.openFileInput("TOPFEEDS.txt");
		    				BufferedReader br = new BufferedReader(new InputStreamReader(pis));
		    				while((a=br.readLine())!=null){
		    					h[q]=a;
		    					q=q+1;
		    				}
		    				br.close();
		    				pis.close();
		    		}catch(Exception e){
		    			System.out.println("Error3 is "+e.toString()+"gotcha");
		    		}
		    		String[] a=new String[g];
		    		q=h;
		    		return h;
		        }
		    	
		    	public String readTextFilea(String fileName) {
		    	    String returnValue = "";
		    	    FileReader file;
		    	    String line = "";
		    	    try {
		    	        file = new FileReader(fileName);
		    	        BufferedReader reader = new BufferedReader(file);
		    	                    try {
		    	            while ((line = reader.readLine()) != null) {
		    	            returnValue += line + "\n";
		    	            }
		    	                    } finally {
		    	                        reader.close();
		    	                    }
		    	    } catch (FileNotFoundException e) {
		    	        throw new RuntimeException("File not found");
		    	    } catch (IOException e) {
		    	        throw new RuntimeException("IO Error occured");
		    	    }
		    	    return returnValue;
		    	}

		    	public  void writeTextFilea(String fileName, String s) {
		    	    FileWriter output;
		    	    try {
		    	        output = new FileWriter(fileName);
		    	        BufferedWriter writer = new BufferedWriter(output);
		    	        writer.write(s);
		    	        writer.close();
		    	    } catch (IOException e) {
		    	    Log.d("error16 "+ e.toString(),"gotcha");
		    	    }
		    	}
		    	
		    	@Override
		        protected void onPostExecute(String[] result) {
		    		super.onPostExecute(result);
		            // TODO: check this.exception 
		    		//Log.d("hi im in here","tagggggg");
		    		
		    		String[] er=null;
		    		try{
		        	er=result;
		        	gff=true;
		        	asynchelp(er);}catch(Exception e){
		        		Log.d("error is "+e.toString(),"gotcha");
		        	}
		            // TODO: do something with the feed
		        }
           }
			List<HashMap<String, String>> fillMaps;
			String[] from;
		    int[] to;
		    String[] fromtopfeeds;
			public void asynchelp(String[] er){try{
		    	fromtopfeeds=er;
		    	fillMaps= new ArrayList<HashMap<String, String>>();
		    	final ListView lv1= (ListView)findViewById(R.id.listview11);
		    	from  = new String[] {"col_1"};
		        to = new int[] {R.id.itemq1};
		        String iamoutofnames[]=null;
		    	for(int q = 0; q <er.length; q++){
		        	HashMap<String, String> map = new HashMap<String, String>();
		        	iamoutofnames=null;
		        	int len;
		        	if(er[q].contains("Numerous")){
		        		iamoutofnames=er[q].split("#");
		        		iamoutofnames=iamoutofnames[0].split(";");
		        		len=iamoutofnames.length-1;
		        		map.put("col_1", iamoutofnames[len]);
		        	}else{
		        		iamoutofnames=er[q].split(";");
		        	map.put("col_1", iamoutofnames[1]);}
		        	//Log.d(er[q].toString(),"ayeoo ");
		        	fillMaps.add(map);
		        	lv1.setOnItemClickListener(onListClick1);
		        	 adViewFromXml.setVisibility(View.VISIBLE);
		        	 hidead.setVisibility(View.VISIBLE);
		        }
		    	final SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.grid_itemq1, from, to);
		    	runOnUiThread(new Runnable() {
		    	     public void run() {

		    	//stuff that updates ui

		    	   
		    	try{
		        lv1.setAdapter(adapter);Log.d("done","tag");
		    	}catch(Exception e){Log.d("tag",e.toString());}
		    	     }
		    	});
		        }catch(Exception e){Log.d("e.toString()","gotcha");}
		    }
			
			private AdapterView.OnItemClickListener onListClick1=new AdapterView.OnItemClickListener(){
			    public void onItemClick(AdapterView<?> parent,View view, int position, long id)
			    {
			    	{
		    			//Log.d("im here","peecee");
		    			String p = null;
		    			try{
		    			p=fromtopfeeds[position];
		    			}catch(Exception e){System.out.println("peecee1 "+e.toString());
		    			}
		    			System.out.println("peecee "+p);
		    			if(p.contains("Numerous")){// for when ctid is numerous
		    				String temp1,nam,fee;
			    			String[] temp,tempq;
			    			String[] gh=new String[2];
			    			tempq=p.split("#");
		    				temp=tempq[0].split(";");
		    				temp1=temp[(temp.length-1)];
		    				nam=temp1;
		    				fee=tempq[1];
		    				gh[0]=nam;
		    				gh[1]=fee;
		    				System.out.println("gh 0"+gh[0]);
		    				System.out.println("gh 1"+gh[1]);
		    				try{
					    		String info11[]=new String[2];
					    		info11[0]=gh[0];
					    		info11[1]=gh[1];
					    		btn_play.setEnabled(false);
					    		btn_play.setVisibility(View.GONE);
					    		btn_stop.setVisibility(View.VISIBLE);
					    		btn_stop.setEnabled(true);
								//mService.startm(info);
							Intent intent11 = new Intent(Webview.this, mp3playerservice.class);
							intent11.putExtra("info",info11);
							//Log.d("info111 0"+info11[0],"okay");Log.d("info111 1"+info11[1],"okay");
							//Log.d("sending intent to service","okay");
							String[] whatever=info11[0].split(";");
	    					one.setText(info11[0]);							
							startService(intent11);
							//Log.d("sent intent to service","okay");
							}
							catch(Exception e)
							{Log.d("Error came up man "+e.toString(),", check the internet connection and stuff..");
						}
		    			}else{
		    				
		    				String[] gh1=new String[2];
		    				String na,ct,add="";
		    				String qwyu[]=p.split(";");
		    				gh1[0]=qwyu[1];
		    				ct=qwyu[0];
		    				na=qwyu[1];
		    				String dboutput=null;
		    				dboutput=getName(Long.parseLong(ct));
		    				if(dboutput!=null){
		    				System.out.println("The output is "+dboutput.toString());
		    				String[] splitz=dboutput.split(">");
		    				for(int t=0;t<splitz.length;t++){
		    					if(splitz[t].contains(na)){
		    						String[] po=splitz[t].split(";");
		    						add=po[1];
		    						break;
		    					}
		    				}
		    				gh1[0]=na;
		    				gh1[1]=add;
		    				if(!add.equals("")){
		    					try{
		    			    		String info11[]=new String[2];
		    			    		info11[0]=gh1[0];
		    			    		info11[1]=gh1[1];
		    			    		//Log.d("name is "+gh1[0]+" address is "+gh1[1],"civil war");
		    			    		btn_play.setEnabled(false);
		    			    		btn_play.setVisibility(View.GONE);
		    			    		btn_stop.setVisibility(View.VISIBLE);
		    			    		btn_stop.setEnabled(true);
		    					Intent intent11 = new Intent(Webview.this, mp3playerservice.class);
		    					intent11.putExtra("info",info11);
		    					//Log.d("info11 0"+info11[0],"okay");Log.d("info11 1"+info11[1],"okay");
		    					//Log.d("sending intent to service","okay");
		    					String[] whatever=info11[0].split(";");
		    					one.setText(info11[0]);
		    					startService(intent11);
		    					//Log.d("sent intent to service","okay");
		    					}
		    					catch(Exception e)
		    					{Log.d("Error came up man "+e.toString(),", check the internet connection and stuff..");
		    				}
		    			}else{
		    				Toast.makeText(Webview.this, "Check for new feeds from the main menu",  Toast.LENGTH_SHORT ).show();
		    			}}else{Toast.makeText(Webview.this, "Check for new feeds from the main menu",  Toast.LENGTH_SHORT ).show();}
		    				}
		    		}
			    	//Log.d("onitemclick invoked man","value of v");
			    }
			};
			
			public String getName(long l/*ctid*/){
				String feed="";
				Context ctx=getApplicationContext();
				DatabaseHandler ourHelper=new DatabaseHandler(ctx);
				SQLiteDatabase ourDatabase;
				ourDatabase=ourHelper.getReadableDatabase();
				String KEY_ID="id";
				String KEY_NAME="name";
				String KEY_PH_NO="phone_number";
				String lo=Long.toString(l);
				final String TABLE_CONTACTS = "table_to_hold_all_values";
				String[] columns=new String[]{KEY_ID,KEY_NAME, KEY_PH_NO};
				//Log.d("above calculations3"+l,"hitler");
				Cursor c=ourDatabase.query(TABLE_CONTACTS,columns,KEY_NAME + "="+lo,null,null,null,null);
				//Log.d("below calculations","hitler");
				c.moveToFirst();
				try{
				while(c!=null){
					if(feed==""){
					feed= c.getString(2);
					//Log.d("feed is"+feed,"hitler");
				}else{
					feed=feed+"$"+c.getString(2);
					//Log.d("2feed is"+feed,"hitler");
				}
					c.moveToNext();
				}}catch(Exception e){Log.d("while loop gave error man: "+e, "hitler");}
				if(feed==""){
					//Log.d("its null man","hitler");
					ourHelper.close();
					return null;
				}
				else {
					ourHelper.close();
					return feed;
				}
			}
			
			@Override
			public void onBackPressed() {
			        if (!scringo.onBackPressed()) {
			                super.onBackPressed();
			        }
			}boolean gff;
			//Millenial Media crap DO NOT TOUCH
			
}