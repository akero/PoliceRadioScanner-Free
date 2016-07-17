package com.example.policeradioscanner;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.akero.fuzzradiopro.R;
import com.example.policeradioscanner.mp3playerservice.MediaPlayerBinder;
import com.flurry.android.FlurryAgent;
import com.scringo.Scringo;
public class addfeed extends SherlockActivity{
	Scringo scringo;
	EditText et1;
	EditText et2;
	Button bt;
	protected TextView title;
    protected ImageView icon;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scringo = new Scringo(this);
        setContentView(R.layout.personalfeedlayout);
        scringo.init(savedInstanceState);
        //Scringo.setDebugMode(true);
		title = (TextView) findViewById(R.id.title);
        icon  = (ImageView) findViewById(R.id.icon);
        init();
        bt.setOnClickListener(
    	        new View.OnClickListener()
    	        {
    	            public void onClick(View view)
    	            {	String name,feed="";
    	            	name=et1.getText().toString();
    	            	feed=et2.getText().toString();
    	            	name=name.trim();
    	            	feed=feed.trim();
    	                //Log.v("data ", et1.getText().toString()+" "+et2.getText().toString());
    	                store(name,feed);
    	            }
    	        });
	}
	Menu menu1;
	
	 	@Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	       MenuInflater inflater = getSupportMenuInflater();
	       inflater.inflate(R.menu.activity_itemlist, menu);
	       try{
	       	if(isMyServiceRunning()==true){
	       		menu.findItem(R.id.add_item).setVisible(true);
	       	}
	       	else{
	       		menu.findItem(R.id.add_item).setVisible(false);
	       	}
	       	menu1=menu;
	       }catch(Exception e){Log.d("error is "+e.toString(),"tag1");}
	       return true;
	    }
	 	
	    @Override
	    public void onResume(){
	    	super.onResume();
	    	pause=false;
	    	try{
	           	if(isMyServiceRunning()==true){
	           		menu1.findItem(R.id.add_item).setVisible(true);
	           	}
	           else{
	           		menu1.findItem(R.id.add_item).setVisible(false);
	           	}
	           }catch(Exception e){Log.d("error is "+e.toString(),"tag1");}
	    }
	    
	    private boolean isMyServiceRunning() {
	        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	            if (com.example.policeradioscanner.mp3playerservice.class.getName().equals(service.service.getClassName())) {
	                return true;
	            }
	        }
	        return false;
	    }
	    
	    Intent intent;
	    Boolean press=false;
	    mp3playerservice mServer;
	    boolean mBounded=false;;
	    
	    protected ServiceConnection mServerConn = new ServiceConnection() {
			@Override
			public void onServiceConnected(ComponentName arg0, IBinder arg1) {
				// TODO Auto-generated method stub
				MediaPlayerBinder mLocalBinder = (MediaPlayerBinder)arg1;
				//Log.d("asfaf","asfdasfas");
				mBounded = true;
				   mServer = mLocalBinder.getService();
				   if(mServer.isStopped()==false){
					   
					   String[] gh=new String[2];//feedaddresses;
			    		gh[0]=mServer.trackname();
			    		gh[1]=mServer.track();
			    		intent=new Intent(addfeed.this,Webview.class); 
			    		intent.putExtra("feed",gh); 
			    		intent.setFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP);
			    		firstrun=false;
		        	  startActivity(intent);
				   }
			}

			@Override
			public void onServiceDisconnected(ComponentName arg0) {
				// TODO Auto-generated method stub
				mBounded = false;
				mServer=null;
			}
	    };
	    
	    @Override
		public void onDestroy(){
			super.onDestroy();
		}
	    boolean firstrun=true;
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {//for sherlock
	       // Handle item selection
	       switch (item.getItemId()) {
	          case R.id.add_item:
	        	  Intent i=new Intent(this,mp3playerservice.class);
	        	  if(mBounded==true){
        			  String[] gh=new String[2];//feedaddresses;
  		    		gh[0]=mServer.trackname();
  		    		gh[1]=mServer.track();
  		    		//Log.d(gh[0]+gh[1],"This crap");
  		    		intent=new Intent(addfeed.this,Webview.class); 
  		    		intent.putExtra("feed",gh); 
  		    		intent.setFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP);
  		    		firstrun=false;
  	        	  startActivity(intent);
        		  }else{
        			  getApplicationContext().bindService(i, mServerConn, Context.BIND_AUTO_CREATE);
        		  }
	        	  intent=new Intent(this,Webview.class);
	             return true;
	          case R.id.about:
	        	  boolean menuState= scringo.isMenuOpen();
	        	  if(menuState==false){
	        		  scringo.openMenu();
	        	  }else{
	        		  scringo.closeMenu();
	        	  }
	             return true;
	          default:
	             return super.onOptionsItemSelected(item);
	       }
	    }
	Boolean added=false;
	
	void store(String nam, String fee){
		if(nam.equals("")){
			Toast.makeText(addfeed.this, "Enter the feed name please",  Toast.LENGTH_SHORT ).show();
		}if(fee.equals("")){
			Toast.makeText(addfeed.this, "Enter the feed URL please",  Toast.LENGTH_SHORT ).show();	
		}
		if(!nam.equals("")&&!nam.equals("")){
			added=addtofaves(nam,fee);
			if(added==true){
			Toast.makeText(addfeed.this, "Feed added to favourites",  Toast.LENGTH_SHORT ).show();	
			finish();
			}
		}
	}

	boolean addtofaves(String na,String fe){
	String favsname=na;
	String favsfeed=fe;
	InputStream fis=null;
	Boolean alreadyexists=false;
	//count
		boolean empty = true;
		int count = 0;
		try {
			fis = getApplicationContext().openFileInput("favs.txt");
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
			fis = getApplicationContext().openFileInput("favs.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String z;
		for(int i=0;i<count;i++){
        	z=br.readLine();
        	if (z.contains(favsfeed)){
        		alreadyexists=true;
        	}
		}
		}catch(Exception e){Log.d("error issig "+e.toString(),"gotcha123");}
		//adding
		if(alreadyexists==false){
			try{
				FileOutputStream fos = null;
				//Log.d("Empty "+empty,"aaaa");
				fos = getApplicationContext().openFileOutput("favs.txt", Context.MODE_APPEND);
				if(empty==false){//Log.d("I am in here","aaaa");
					fos.write(("\n"+favsname+";"+favsfeed).getBytes());}
				else{
					fos.write((favsname+";"+favsfeed).getBytes());
					}
				fos.close();
				return true;
			}catch(Exception e){
				Log.d("error is "+e.toString(),"gotcha123");
				Toast.makeText(addfeed.this, "Failed, try emptying space in internal storage",  Toast.LENGTH_SHORT ).show();
			return false;
			}
			}
		else{
			Toast.makeText(addfeed.this, "Feed already in favourites",  Toast.LENGTH_SHORT ).show();
			return true;
		}
	}
	Boolean pause=true;
	
	public void init(){
		 et1=(EditText)findViewById(R.id.edt);
		 et2=(EditText)findViewById(R.id.edt1);
		 bt=(Button)findViewById(R.id.but);
	}
	
	@Override
    public void onBackPressed() {
            if (!scringo.onBackPressed()) {
                    super.onBackPressed();
            }else{
            	pause=false;
            }
    }
	
	@Override
    protected void onPause(){
	super.onPause();
	pause=true;
    }
	
    @Override
    protected void onStart() {
            super.onStart();
            scringo.onStart();
            FlurryAgent.onStartSession(this, "4DF4JJB7BHD8XZ47YDGX");
    }

    @Override
    protected void onStop() {
            super.onStop();
            scringo.onStop();
            FlurryAgent.onEndSession(this); 
    }
}

