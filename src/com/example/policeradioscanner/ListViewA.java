package com.example.policeradioscanner;
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
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
public class ListViewA extends SherlockActivity{
	List<HashMap<String, String>> fillMaps;//
	int a=-1;//how many lists further is it
	String[] arrayctid;//contains key(ctid) when a==2
	String[] feedaddresses;//for when a==3
	String[] feedaddresses1;//for storing passed feedaddresses
	String[] j;//to save coid;
	String[] stid12;//to save stid for rest of the world except murica;
	boolean murica;String[] feednames;
	boolean murica1;static Intent intentbro;
	boolean playa=false;
	static boolean active = false;
	Context ctr=this.getBaseContext();
	Context ctz;
	String[] from;
    int[] to;
	String stidd="";//stid for murica=false
	//for a==2
	String[] ctid2;//to pass ctid to a==3
	ListView lv;
	//till here
	Menu menu1;
	Scringo scringo;
	protected TextView title;
    protected ImageView icon;
    Boolean addfeed=false;
    //boolean appOpen=true;
    //static Context appContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {try{
        super.onCreate(savedInstanceState);
        lv=null;
        ctz=null;
        fillMaps=null;
        fillMaps = new ArrayList<HashMap<String, String>>();
        
        scringo = new Scringo(this);
        setContentView(R.layout.main);
       // appContext=getApplicationContext();
        scringo.init(savedInstanceState);
        //Scringo.setDebugMode(true);//*******************************************************************************************************
        //FlurryAgent.onError(errorId, String message, "ListViewA");
        
        try{
        lv= (ListView)findViewById(R.id.listview);}catch(Exception e){Log.d("e.toString()","uiop");}
        title = (TextView) findViewById(R.id.title);
        icon  = (ImageView) findViewById(R.id.icon);
        lv.setBackgroundColor(Color.TRANSPARENT);
        lv.setCacheColorHint(Color.TRANSPARENT);
        Context context=getApplicationContext();
        ctz=this.getApplicationContext();
        //Log.d("Listvewa main for a==3 invoked","million");
        int instanceposition;
        int position=0;//position of the click
        String ctid="";//when a==3, store value of ctid
        String coid="";//when a==1, store value of coid
        Bundle extras;
        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            if(extras == null) {
                //newString= null;
                instanceposition=-1;
                a=instanceposition;
                position=0;
                if(a==1){
                coid=null;
                }
                if(a==1){
                	stidd=null;
                }
                if(a==3){
                ctid=null;
                }
                if(a==4){
                feedaddresses1=null;
                }
            } else {
                //newString= extras.getString("Key");
                instanceposition=extras.getInt("instpos");
                a=instanceposition;
                position=extras.getInt("position");
                if(a==1){
                	coid=extras.getString("coid");
                }
                if(a==2){
                    murica=extras.getBoolean("murica");
                    //Log.d(Boolean.toString(murica),"murica");
                    stidd=extras.getString("stid");
                }
                if(a==3){
                ctid=extras.getString("ctid");
                }
                if(a==4){
                    feedaddresses1=extras.getStringArray("feedaddresses");
                    }
                }
        }
        // create the grid item mapping
        from  = new String[] {"col_1"};
        to = new int[] {R.id.item2};
        String[] array;
        //Log.d(Integer.toString(a),"value of a");
        if(a==-1){
        	boolean firstEverRun=false;
        	File file3=null;
        	try{
          		file3 =getBaseContext().getFileStreamPath("isfirstrun.txt");
          		}catch(Exception e){Log.d("er1 "+e.toString(),"whaeva");
          	}
          	//boolean empty=true;
              if(!file3.exists()){
            	  firstEverRun=true;
            	  FileOutputStream fOut = getApplicationContext().openFileOutput("isfirstrun.txt",MODE_PRIVATE);
      				fOut.close();
      				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
      	    				ListViewA.this);
      	    			// set title
      	    			alertDialogBuilder.setTitle("Disclaimer")
      	    			.setMessage("The audio feeds in this app are provided by volunteers across the world. If your area isn't listed, that means no one in your area volunteered to provide audio.\n\nThis app's creators have no control over which areas' feeds are available, or the quality and reliability of the audio.")
      	    			.setCancelable(false)
      					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
      						public void onClick(DialogInterface dialog,int id) {
      							// if this button is clicked, just close
      							// the dialog box and do nothing
      							
      							dialog.cancel();
      							
      						}
      					});
      	 
      					// create alert dialog
      					AlertDialog alertDialog = alertDialogBuilder.create();
      	 
      					// show it
      					alertDialog.show();
      				
              }
              if(firstEverRun==false){
        	//interstitial ad
            //Log.d(Integer.toString(a),"qora");
            
            	//final MMInterstitial interstitial = new MMInterstitial(this);
            

          //Set your metadata in the MMRequest object
         // MMRequest request = new MMRequest();

          //Add metadata here.

          //Add the MMRequest object to your MMInterstitial.
          //interstitial.setMMRequest(request);
          
          //interstitial.setApid("127326");
          
          //interstitial.fetch();

         // interstitial.setListener(new RequestListenerImpl() {
         // @Override
        //  public void requestCompleted(MMAd mmAd) {
         //    interstitial.display();
         // }
         // });
            
          //ad ends
          
              }
        	String z="";
        	setTitle("Index");
        	try{
        		InputStream is = getAssets().open("CATALOG.txt");
        		InputStreamReader iz=new InputStreamReader(is);
        		BufferedReader bis = new BufferedReader(iz);
            int v=0;
            v=count("CATALOG.txt");
            //Log.d(Integer.toString(v),"value of v");
            array=new String[v];
         for(int i=0;i<v;i++){
        	z=bis.readLine();
        	//Log.d(z,"readline is");
        	array[i]=z;
        	}
         // prepare the list of all records
        for(int q = 0; q <v; q++){
        	HashMap<String, String> map = new HashMap<String, String>();
        	map.put("col_1", array[q]);
        	fillMaps.add(map);
        	lv.setOnItemClickListener(onListClick);
        	}
        /*File file3 = null;
    	try{
    		file3 =getBaseContext().getFileStreamPath("favs.txt");
    		}catch(Exception e){Log.d("er1 "+e.toString(),"whaeva");
    	}
    	boolean empty=true;
        if(file3.exists()){
        	InputStream isr = getApplicationContext().openFileInput("favs.txt");
        	InputStreamReader isr1=new InputStreamReader(isr);
        	BufferedReader br=new BufferedReader(isr1);
        	String line;
        	if(!(line=br.readLine()).equals(null)){
        		empty=false;
        	}
        }
        if(empty==false){
        	HashMap<String, String> map = new HashMap<String, String>();
        	map.put("col_1", "Favorites");
        	fillMaps.add(map);
        	lv.setOnItemClickListener(onListClick);
        }*/
        }catch(Exception E){E.printStackTrace();
        }    
       }
        
        else if(a==0){
        	if(position==0){
        		setTitle("World");
        	String z="";
        	try{
        		InputStream is = getAssets().open("COUNTRIES.txt");
        		InputStreamReader iz=new InputStreamReader(is);
        		BufferedReader bis = new BufferedReader(iz);
            int v=0;
            v=count("COUNTRIES.txt");
            //Log.d(Integer.toString(v),"value of v");
            array=new String[v];
            String[] h;//to split coid and country names
            j=new String[v];
        for(int i=0;i<v;i++){
        	z=bis.readLine();
        	h=z.split(">");
        	j[i]=h[0];
        	array[i]=h[1];
        }
        // prepare the list of all records
        for(int q = 0; q <v; q++){
        	HashMap<String, String> map = new HashMap<String, String>();
        	map.put("col_1", array[q]);
        	fillMaps.add(map);
        	lv.setOnItemClickListener(onListClick);
        }
        }catch(Exception E){E.printStackTrace();
        }
        	}else if(position==1){
        		setTitle("Top Feeds");
        		playa=false;
        		topfeeds();
        	}
        	else if(position==4){
        		setTitle("Favorites");
        		playa=false;
        		favourites();
        	}
        }
//list of states
        if(a==1)
        {	
        	if(position==0){
        	setTitle("United States");
        	murica1=true;
        	//Log.d(Boolean.toString(murica1),"murica2(murica1)");
        	//Log.d(Boolean.toString(murica),"murica2");
        	//Log.d(Integer.toString(position),"position");
        	String z="";
        	try{
        		InputStream is = getAssets().open("USSTATES.txt");
        		InputStreamReader iz=new InputStreamReader(is);
        		BufferedReader bis = new BufferedReader(iz);
            int v=0;
            v=count("USSTATES.txt");
            //Log.d(Integer.toString(v),"value of v");
            array=new String[v];
        for(int i=0;i<v;i++){
        	z=bis.readLine();
        	array[i]=z;
        }
        // prepare the list of all records
        for(int q = 0; q <v; q++){
        	HashMap<String, String> map = new HashMap<String, String>();
        	map.put("col_1", array[q]);
        	fillMaps.add(map);
        	lv.setOnItemClickListener(onListClick);
        }
        }catch(Exception E){E.printStackTrace();
        }
        }else{
        	try{
        		String coid1=coid;
        		//finding country
            	int v=count("COUNTRIES.txt");
            	//Log.d("v"+Integer.toString(v),"asdfqwer");
            	String line="";
            	String[] line1=new String[2];;
            	String[] countryid=new String[v];
            	String[] countryname=new String[v];
        		InputStream is1 = getAssets().open("COUNTRIES.txt");
        		InputStreamReader iz1=new InputStreamReader(is1);
        		BufferedReader bis1 = new BufferedReader(iz1);
        		//Log.d("coid"+coid,"asdfqwer");
        		for(int i=0;i<v;i++){
        			line=bis1.readLine();
        			line1=line.split(">");
        			countryid[i]=line1[0];
        			countryname[i]=line1[1];
        		}
        		int counter=0;
        		for(int i=0;i<countryid.length;i++){
        			if(countryid[i].equals(coid1)){
        				
        				counter=i;
        				//Log.d("counter"+Integer.toString(counter),"asdfqwer");
        				break;
        			}
        		}
        		
        	setTitle(countryname[counter]);
        	murica1=false;
        	
        	
        		InputStream is = getAssets().open("WORLDSTATES.txt");
        		InputStreamReader iz=new InputStreamReader(is);
        		BufferedReader bis = new BufferedReader(iz);
        		String p="";
        		String r="";
        		String[] o;
        		while((p=bis.readLine())!=null){
        			o=p.split(":");
        			if(o[0].equals(coid1)){
        				r=r+o[1];
        			}
        		}
        		String[] e;
        		e=r.split(";");
        		array=new String[e.length];
        		stid12=new String[e.length];
        		String[] q;
        		for(int i=0;(i<e.length);i++){
        			q=e[i].split(">");
        			array[i]=q[1];
        			stid12[i]=q[0];
        		}
        		// prepare the list of all records
    			for(int t = 0; t <array.length; t++){
    			HashMap<String, String> map = new HashMap<String, String>();
    			map.put("col_1", array[t]);
    			fillMaps.add(map);
    			lv.setOnItemClickListener(onListClick);
        		}
        }catch(Exception e){Log.d("Error is: "+e,"error came up");}
        }
        }
        else if(a==2){//displaying counties
        	setTitle("Counties");
        	if(murica==true){
        	//Log.d("Listvewa outside try invoked","million");
        	try{
        		//Log.d("Listvewa inside try invoked","million");
        			String[] array1;//contains counties
        			Object[] obj=readFromIS(position);
        			array1=(String[])obj[0];
        			arrayctid=(String[])obj[1];
        			// prepare the list of all records
        			for(int q = 0; q <array1.length; q++){
        			HashMap<String, String> map = new HashMap<String, String>();
        			map.put("col_1", array1[q]);
        			fillMaps.add(map);
        			lv.setOnItemClickListener(onListClick);
        			}	
        		}catch(Exception e){e.printStackTrace();}//Log.d("Listvewa outside below try invoked","million");
        		}
        	else{
        		try{
        		String e=stidd;//stid of state clicked on
        		String f="";
        		String line1="";
        		String[] w;
        		String[] q;
        		String[] d;
        		String[] s;
        		InputStream is = getAssets().open(("WORLDCOUNTIES.txt"));
        	    InputStreamReader iz=new InputStreamReader(is);
        	    BufferedReader bis = new BufferedReader(iz);
        		try{
        		while((line1=bis.readLine())!=null){
        			w=line1.split(",");
        			if(w[0].equals(e)){
        				f=f+w[1];
        			}
        		}
        		}catch(Exception as){Log.d("Error is "+as,"error came up bro");}
        		q=f.split(";");
        		s=new String[q.length];
        		ctid2=new String[q.length];
        		for(int i=0;i<q.length;i++){
        			d=q[i].split(">");
        			s[i]=d[1];
        			ctid2[i]=d[0];}
        			// prepare the list of all records
        			for(int k = 0; k <s.length; k++){
        			HashMap<String, String> map = new HashMap<String, String>();
        			map.put("col_1", s[k]);
        			fillMaps.add(map);
        			lv.setOnItemClickListener(onListClick);
        			}
        	}catch(Exception e){Log.d("Error is "+e,"error came up dude");
        	}
        	}
        	}
        else if(a==3){
        	setTitle("Feeds");
        	//Log.d("in a==3","twitch");
        	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
    				context);
    			// set title
    			alertDialogBuilder.setTitle("");
    			// set dialog message
    			alertDialogBuilder.setMessage("No feeds for this county.");
    			String asdf="";
        	try{
        	asdf=getName(Long.parseLong(ctid));
        	asdf=asdf.substring(0,(asdf.length()-1));
        	}catch(Exception e){Log.d("asdf is fcked,e is "+e,"hitler");}
        	try{
        	System.out.println("asdf is "+asdf);
        	if(asdf==null){
        		// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
				// show it
				alertDialog.show();
        	}
        	else{
        		//Log.d("asdf is "+asdf,"fuck");
        	String[] strarray=asdf.split(">");
        	//Log.d("strarray length "+strarray.length,"fuck");
        	//Log.d("strarray 0 is "+strarray[0],"fuck");
        	feednames=new String[strarray.length];
        	feedaddresses=new String[strarray.length];
        	try{for(int i=0;i<strarray.length;i++){
        		String[] qwerty=strarray[i].split(";");
        		//Log.d("qwerty length "+qwerty.length,"fuck");
        		//Log.d("qwerty 1 is "+qwerty[1],"fuck");
        		//Log.d("qwerty 0 is "+qwerty[0],"fuck");
        		feednames[i]=qwerty[0];
        		feedaddresses[i]=qwerty[1];
        	}}catch(Exception e){Log.d("This is what went wrong "+e,"fuck");}
        	// prepare the list of all records
			for(int q = 0; q <feednames.length; q++){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("col_1", feednames[q]);
			fillMaps.add(map);
			lv.setOnItemClickListener(onListClick);
        }
        	}
        	}catch(Exception e){Log.d("It doesnt exist probably","fuck");}
        }
        //Log.d(Integer.toString(a)," a!! before ");
    	a=a+1;
    	//Log.d(Integer.toString(a)," a!! after ");
        // fill in the grid_item layout
    	if(playa==false){
        SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.grid_item, from, to);
        lv.setAdapter(adapter);
    	}
    }catch(Exception e){Log.d("Error "+e.toString(),"gfdsa");}}
    String[] fromtopfeeds;
    public void asynchelp(String[] er){
    	fromtopfeeds=er;
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
        	lv.setOnItemClickListener(onListClick);
        }
    	final SimpleAdapter adapterquesha = new SimpleAdapter(this, fillMaps, R.layout.grid_item, from, to);
    	playa=true;
    	runOnUiThread(new Runnable() {
   	     public void run() {

   	//stuff that updates ui

   	   
   	try{
       lv.setAdapter(adapterquesha);Log.d("done","tag");
   	}catch(Exception e){Log.d("tag",e.toString());}
   	     }
   	});
        
    }
    private boolean isMyServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (com.example.policeradioscanner.mp3playerservice.class.getName().equals(service.service.getClassName())) {
            	//Log.d("YEAH","uyi");
                return true;
            }
        }
       // Log.d("NAY","uyi");
        return false;
    }
	
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
    boolean favsexists=false;
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
    	//
    	if(a==0){
    	if(favsexists==false){
    File file3 = null;
	try{
		file3 =getBaseContext().getFileStreamPath("favs.txt");
		}catch(Exception e){Log.d("er1 "+e.toString(),"whaeva");
	}
	try{
	int w=0;
	boolean empty=true;
    if(file3.exists()){
    	//Log.d(Integer.toString(w+=1),"waka waka");
    	InputStream isr = getApplicationContext().openFileInput("favs.txt");//Log.d(Integer.toString(w+=1),"waka waka");
    	InputStreamReader isr1=new InputStreamReader(isr);//Log.d(Integer.toString(w+=1),"waka waka");
    	BufferedReader br=new BufferedReader(isr1);//Log.d(Integer.toString(w+=1),"waka waka");
    	//Log.d(Integer.toString(w+=1),"waka waka");
    	String line;
    	if(!(line=br.readLine()).equals(null)){//Log.d(Integer.toString(w+=1),"waka waka");
    		empty=false;//Log.d(Integer.toString(w+=1),"waka waka");
    	}//Log.d(Integer.toString(w+=1),"waka waka");
    }//Log.d(Integer.toString(w+=1),"waka waka");
    if(empty==false){//Log.d(Integer.toString(w+=1),"waka waka");
    	HashMap<String, String> map = new HashMap<String, String>();//Log.d(Integer.toString(w+=1),"waka waka");
    	map.put("col_1", "Favorites");//Log.d(Integer.toString(w+=1),"waka waka");
    	fillMaps.add(map);//Log.d(Integer.toString(w+=1),"waka waka");
    	lv.setOnItemClickListener(onListClick);//Log.d(Integer.toString(w+=1),"waka waka");
    	SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.grid_item, from, to);
    	lv.setAdapter(adapter);
    	favsexists=true;
    }//Log.d(Integer.toString(w+=1),"waka waka");
    	}catch(Exception e){Log.d("er2 "+e.toString(),"whaeva");
    }       
    }}
    }
    Boolean press=false;
    mp3playerservice mServer;
    boolean mBounded=false;
    
    protected ServiceConnection mServerConn = new ServiceConnection() {
    	
		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			// TODO Auto-generated method stub
			MediaPlayerBinder mLocalBinder = (MediaPlayerBinder)arg1;
			//Log.d("asfaf","asfdasfas");
			mBounded = true;
			   mServer = mLocalBinder.getService();
			   //if(mServer.isStopped()==false){
				   String[] gh=new String[2];//feedaddresses;
		    		gh[0]=mServer.trackname();
		    		gh[1]=mServer.track();
		    		//Log.d(gh[0]+gh[1],"This crap");
		    		intent=new Intent(ListViewA.this,Webview.class); 
		    		intent.putExtra("feed",gh); 
		    		//playa=true; 
		    		//active=true; 
		    		intent.setFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP);
		    		firstrun=false;
	        	  startActivity(intent);
			   //}
		}

		
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
			mBounded = false;
			mServer=null;
		}
    };
    boolean firstrun=true;
    Intent intent;
    //******************************************************************************************************************************************
    @Override
	public void onDestroy(){
		super.onDestroy();
		//if(mBounded==true){
		//	unbindService(mServerConn);
		//}
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//for sherlock
       // Handle item selection
       switch (item.getItemId()) {
          case R.id.add_item:
             // do s.th.
        	 // Log.d(Boolean.toString(mBounded)+"code is set to comments","This crap");
        	 Intent i=new Intent(this,mp3playerservice.class);
        	  ///if(firstrun==true){
        	 // getApplicationContext().bindService(i, mServerConn, Context.BIND_AUTO_CREATE);
        	 // }else{
        		  if(mBounded==true){
        			  String[] gh=new String[2];//feedaddresses;
  		    		gh[0]=mServer.trackname();
  		    		gh[1]=mServer.track();
  		    		//Log.d(gh[0]+gh[1],"This crap");
  		    		intent=new Intent(ListViewA.this,Webview.class); 
  		    		intent.putExtra("feed",gh); 
  		    		//playa=true; 
  		    		//active=true; 
  		    		intent.setFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP);
  		    		firstrun=false;
  	        	  startActivity(intent);
        		  }else{
        			  getApplicationContext().bindService(i, mServerConn, Context.BIND_AUTO_CREATE);
        		  }
        	//  }
        	  intent=new Intent(this,Webview.class);
             return true;
          case R.id.about:
        	  boolean menuState= scringo.isMenuOpen();
        	  if(menuState==false){
        		  scringo.openMenu();
        	  }else{
        		  scringo.closeMenu();
        	  }
          /*  if(pause==false){
        	  scringo.openMenu();
        	  pause=true;}
        	else{
        	  scringo.closeMenu();
        	  pause=false;
        	  }*/
             return true;
          default:
             return super.onOptionsItemSelected(item);
       }
    }
    //for scringo
    boolean pause=false;
    
    @Override
    protected void onPause(){
	super.onPause();
	pause=true;
	//Log.d(Boolean.toString(pause),"gftr");
    }
    //***************************************************************************************************************
    public boolean isOnline() {
        ConnectivityManager cm =
            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
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
            //if(mBounded==true){
    		//	unbindService(mServerConn);
    		//}
    }
    
    @Override
    public void onBackPressed() {
            if (!scringo.onBackPressed()) {
                super.onBackPressed();
                   
            }else{
            	pause=false;
            }
    }
    //till here
    private AdapterView.OnItemClickListener onListClick=new AdapterView.OnItemClickListener(){
    public void onItemClick(AdapterView<?> parent,View view, int position, long id)
    {//Log.d(Integer.toString(position),"value of vee");
    	String x="";
    	String l="";//coid passed to display corresponding states
    	Intent i=new Intent(ListViewA.this,ListViewA.class);
    	if(a==0){
    		if(position==2){
    			playa=true;
    				//Log.d("yeah i come here","aaaa");
            		Intent int1=new Intent(ListViewA.this,addfeed.class);
            		startActivity(int1);
    		}else if(position==0||position==1){
    			playa=false;
    		}else if(position==3){
    			playa=true;
    			//Log.d("yeah i come here","fubar");
        		Intent int1=new Intent(ListViewA.this,update.class);
        		startActivity(int1);
    		}else if(position==4){
    			playa=false;
    		}
    	}
    	if(a==1){
    		if(playa==false){
    		//Log.d("YEP BRO U ARE RIGHT","supersaiyan");
    		l=j[position];
    		i.putExtra("coid",l);
    		}
    		else{
    			//Log.d("im here","peecee");
    			String p = null,temp1,nam,fee;
    			String[] temp,tempq;
    			String[] gh=new String[2];
    			try{
    			p=fromtopfeeds[position];
    			}catch(Exception e){System.out.println("peecee1 "+e.toString());
    			}
    			System.out.println("peecee "+p);
    			if(p.contains("Numerous")){// for when ctid is numerous
    				tempq=p.split("#");
    				temp=tempq[0].split(";");
    				temp1=temp[(temp.length-1)];
    				nam=temp1;
    				fee=tempq[1];
    				gh[0]=nam;
    				gh[1]=fee;
    				System.out.println("gh 0"+gh[0]);
    				System.out.println("gh 1"+gh[1]);
    				intentbro=new Intent(ListViewA.this,Webview.class); 
    	    		intentbro.putExtra("feed",gh); 
    	    		playa=true; 
    	    		active=true; 
    	    		intentbro.setFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP); 
    	    		startActivity(intentbro); 
    			}else{
    				String[] gh1=new String[2];
    				String na,ct,add="";
    				String qwyu[]=p.split(";");
    				gh1[0]=qwyu[1];
    				ct=qwyu[0];
    				na=qwyu[1];
    				String dboutput=null;//output from db
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
    				intentbro=new Intent(ListViewA.this,Webview.class); 
    	    		intentbro.putExtra("feed",gh1); 
    	    		playa=true; 
    	    		active=true; 
    	    		intentbro.setFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP); 
    	    		startActivity(intentbro); 
    			}else{
    				Toast.makeText(ListViewA.this, "Check for new feeds from the main menu",  Toast.LENGTH_SHORT ).show();
    			}
    			}else
    				{Toast.makeText(ListViewA.this, "Check for new feeds from the main menu",  Toast.LENGTH_SHORT ).show();}
				}
    			}//else ends
    		}
    	if(a==2){String g="";
    		i.putExtra("murica", murica1);
    		//Log.d(Boolean.toString(murica),"murica1");
    		if(murica1==false){
    			g=stid12[position];
    			i.putExtra("stid", g);
    		}
    	}
    	try{
    	if(a==3){if(murica==true){
    		x=arrayctid[position];
    		i.putExtra("ctid", x);}
    	else{
    		x=ctid2[position];
    		i.putExtra("ctid", x);
    	}
    		//Log.d("im invoked","a!!");
    	}}catch(Exception e){e.printStackTrace();}
    	
    	if(a==4){
    		String[] gh=new String[2];//feedaddresses;
    		gh[0]=feednames[position];
    		gh[1]=feedaddresses[position];
    		intentbro=new Intent(ListViewA.this,Webview.class); 
    		intentbro.putExtra("feed",gh); 
    		playa=true; 
    		active=true; 
    		intentbro.setFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP); 
    		startActivity(intentbro); 
    		System.out.println();
    		}
    		//}
    	 if(playa==false){
    		 i.putExtra("Key",fillMaps.get(position));
    		 i.putExtra("instpos", a);
    		 i.putExtra("position",position);
    		 startActivity(i);
    	}
    }
    };
    
//getting the name and address according to ctid
//my function
    DatabaseHandler ourHelper;
	SQLiteDatabase ourDatabase;
    int hdsafasfsa;
    public String getName(long l/*ctid*/){
    	//Log.d("in getname","tag12345");
	String feed="";
	Context ctx=getApplicationContext();
	boolean dbExists=false;
	File file3 = null;
	try{
		
		file3 =getBaseContext().getFileStreamPath("dbExistance.txt");
		
		}catch(Exception e){Log.d("er1234 "+e.toString(),"tag12345");}
	if(!file3.exists()){
		try{
		FileOutputStream fOut = getApplicationContext().openFileOutput("dbExistance.txt",MODE_PRIVATE);
		fOut.close();
		DbCopier db = new DbCopier(getApplicationContext());
		SQLiteDatabase q=db.getReadableDatabase();
		q.close();
		db.close();
		db=null;
		FileOutputStream fo = null;
		fo = getApplicationContext().openFileOutput("dbver.txt", Context.MODE_PRIVATE);
		fo.write("1".getBytes());
		fo.close();
		}catch(Exception e){Log.d("error is "+e.toString(),"tag12345");}
	}
	//new RetreiveFeedTask1().execute(ctx);
	//myPd_ring1=ProgressDialog.show(ListViewA.this, "Creating Database", "Creating Database, please wait..", true);
    //myPd_ring1.setCancelable(true);
    
	//remove this code to async*********************************************************************************************
	ourHelper=new DatabaseHandler(ctx);
	ourDatabase=ourHelper.getReadableDatabase();
	//myPd_ring.dismiss();
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
		return null;
	}
	else return feed;
	//remove this code to async*********************************************************************************************
}
public int count(String filename) throws IOException {
	InputStream is = getAssets().open(filename);
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
        }//Log.d("count is "+count,"HERERE");
        return (count == 0 && !empty) ? 1 : count+1;
    } finally {
        bis.close();
    }
}
//for (a==2)
    private String firstNumber = "";
    private String secondNumber = "";
    private String countyName = "";
    private StringTokenizer stringTokenizer = null;
    private SparseArray<SparseArray<String>> sparseArray = new SparseArray<SparseArray<String>>();
    private SparseArray<String> temporarySparseArray = null;

    public Object[] readFromIS(int pos) throws IOException {
        InputStream is = getAssets().open("USCOUNTIES1.txt");
        InputStreamReader iz=new InputStreamReader(is);
        BufferedReader bis = new BufferedReader(iz);
        String line = null;
        while((line = bis.readLine()) != null) {
            readLaine(line);
        }
        int b=pos;
        SparseArray<String> subSparseArray = sparseArray.get(b); //You first need a 1D sparseArray
        int key = 0;
        String[] array=new String[subSparseArray.size()];
        String[] array1=new String[subSparseArray.size()];//to pass key(ctid)
        for(int i = 0; i < subSparseArray.size(); i++) {
           key = subSparseArray.keyAt(i);
           array1[i]=Integer.toString(key);
           String county = subSparseArray.get(key); //county is the String in place (0,key)
           array[i]=county;
        }
        return new Object[]{array,array1};
    }
    String[] er;
    
    private void readLaine(String line) {
        stringTokenizer = new StringTokenizer(line, ",");
        firstNumber = (String) stringTokenizer.nextElement();
        stringTokenizer = new StringTokenizer((String)stringTokenizer.nextElement(), ">");
        secondNumber = (String) stringTokenizer.nextElement();
        countyName = ((String) stringTokenizer.nextElement());
        countyName = countyName.substring(0, countyName.length());
        int num1 = Integer.parseInt(firstNumber);
        int num2 = Integer.parseInt(secondNumber);
        if (sparseArray.get(num1) == null) {
            sparseArray.put(num1, new SparseArray<String>());
        }
        temporarySparseArray = sparseArray.get(num1);
        temporarySparseArray.put(num2, countyName);
        sparseArray.put(num1, temporarySparseArray);
        temporarySparseArray = null;
        
    }
    public class RetreiveFeedTask1 extends AsyncTask<Context, Void, Boolean>{
    	String[] q;
    	@Override
    	protected Boolean doInBackground(Context... params) {
    		boolean done=false;
    		Context ctx;
    		ctx=params[0];
    		ourHelper=new DatabaseHandler(ctx);
    		ourDatabase=ourHelper.getReadableDatabase();
    		return done;
    	}
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
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
    		        String line = null;
    		        int one=0;
    		        boolean exists=true;
    		        try{
    		        	File file3 = null;
    		        	try{
    		        		file3 =base.getFileStreamPath("TOPFEEDS.txt");}catch(Exception e){Log.d("er1 "+e.toString(),"whaeva");
    		        		exists=false;
    		        		}
    		        boolean numerous=false;
    		        while ((line = reader.readLine()) != null) {
    		        	if(z==0){
    		        		InputStream is;
    		        		if(exists==true){
    		        			is = app.openFileInput("TOPFEEDS.txt");
    		        		}
    		        		else{
    		        			FileOutputStream fOut = app.openFileOutput("TOPFEEDS.txt",MODE_PRIVATE);
    		        			fOut.close();
    		        			is = app.openFileInput("TOPFEEDS.txt");
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
    		        		FileOutputStream fos = null;
    		        		fos = app.openFileOutput("TOPFEEDS.txt", Context.MODE_APPEND);
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
    		        	}else if(line.contains("<a href=\"/listen/feed/")&&(!line.contains("class=\"button-info\""))&&(!line.contains("<span class="))&&(!line.contains("<!DOCTYPE html>"))){ 
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
    		        }else{
    		        	boolean nevergotin=true;
    		        	FileOutputStream fos = null;
    		        	File fileq = null;
    		        	try{
    		        	fileq = base.getFileStreamPath("TOPFEEDSTEMP.txt");}catch(Exception e){Log.d("er1 "+e.toString(),"whaeva");}
    		        	if(fileq.exists()==true){
    		        		fos = app.openFileOutput("TOPFEEDSTEMP.txt", Context.MODE_APPEND);
    		        		
		        		}
		        		else{
		        			fos = app.openFileOutput("TOPFEEDSTEMP.txt", Context.MODE_APPEND);
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
    		        	}else if(line.contains("<a href=\"/listen/feed/")&&(!line.contains("class=\"button-info\""))&&(!line.contains("<span class="))&&(!line.contains("<!DOCTYPE html>"))){ 
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
    		        	PrintWriter writer = new PrintWriter(base.getFileStreamPath("TOPFEEDS.txt"));
    		        	writer.print("");
    		        	writer.close();
    		        	String j;
    		        	j=readTextFilea("TOPFEEDSTEMP.txt");
    		        	writeTextFilea("TOPFEEDS.txt",j,app);
    		        	PrintWriter writer1 = new PrintWriter("TOPFEEDSTEMP.txt");
    		        	writer1.print("");
    		        	writer1.close();
    		        }
    		    }catch(Exception f){Log.d("error1 "+ f.toString()+line,"tagyo");
    		    	String filepath=app.getFilesDir().getPath().toString() + "/TOPFEEDSTEMP.txt";
    		    	PrintWriter writer1 = new PrintWriter(filepath);
    		    	writer1.print("");
    		    	writer1.close();
            }
    		}catch(Exception e){Log.d("error2 "+ e.toString(),"tagyo");}
    		int count = 0;
    		try{
    			InputStream is = app.openFileInput("TOPFEEDS.txt");
    			BufferedInputStream bis1 = new BufferedInputStream(is);
    		    try {
    		        byte[] c = new byte[1024];
    		        int readChars = 0;
    		        while ((readChars = bis1.read(c)) != -1) {
    		            for (int i = 0; i < readChars; ++i) {
    		                if (c[i] == '\n') {
    		                    ++count;
    		                }
    		            }
    		        }//Log.d("count isa "+count,"HEREREre");
    		    } finally {
    		        bis1.close();
    		    }
    		h=new String[count];
    		try{
    				String a;
    				int q=0;
    				InputStream pis = app.openFileInput("TOPFEEDS.txt");
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

    	public  void writeTextFilea(String fileName, String s,Context app) {
    	    FileWriter output;
    	    try {
    	        output = new FileWriter(fileName);
    	        BufferedWriter writer = new BufferedWriter(output);
    	        writer.write(s);
    	        writer.close();
    	        
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
    	}
    	
    	@Override
        protected void onPostExecute(String[] result) {
    		super.onPostExecute(result);
            // TODO: check this.exception 
    		//Log.d("hi im in here","tagggggg");
        	er=result;
        	//Log.d("er[0]"+er[0],"tagggggg");Log.d("er.length"+er.length,"tagggggg");
        	gff=true;
        	asynchelp(er);
        	myPd_ring.dismiss();
            // TODO: do something with the feed
        }
    }
    
    //cotext menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	
        super.onCreateContextMenu(menu, v, menuInfo);
        //Log.d("test2","qora");
        android.view.MenuInflater inflater =(android.view.MenuInflater) getMenuInflater();
        inflater.inflate(R.menu.context_menu, (android.view.Menu) menu);
      }
	public boolean onContextItemSelected(android.view.MenuItem item) {
			//Log.d("test1","qora");
		  InputStream fis=null;
		  boolean empty=true;
		  int count=0;
	      AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	      //stupid crap
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
	          //Log.d("count is "+count,"HEREREhe");
	          count=count+1;
	      }catch(Exception e){
	      	Log.d("Error is3 "+e.toString(),"gotcha123");
	      }//end of stupid crap
	      BufferedReader br=null;
	      try{
	      fis = getApplicationContext().openFileInput("favs.txt");
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
	    	  fis1 = getApplicationContext().openFileInput("favs.txt");
		  	  br1 = new BufferedReader(new InputStreamReader(fis1));
		  	  fos1 = getApplicationContext().openFileOutput("favstemp.txt", Context.MODE_PRIVATE);
	    	  Boolean printed=false;
	    	  String h=null;
	    	  for(int i=0;i<count;i++){
	    		  if(printed==true&&i!=count-1){
	    			  fos1.write(("\n").getBytes());
	    		  }
	    		  h=br1.readLine();
	    		 // Log.d(h,"whagwan");
	    		  //Log.d(listItemName,"whagwan");
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
	    		        	fav =getBaseContext().getFileStreamPath("favs.txt");
  		        		if(fav.exists()){
  		        			exists1=true;
  		        			deleted = fav.delete(); 
  		        		}
  		        		}
	    		        catch(Exception e){
  		        			Log.d("yo "+e.toString(),"gotcha");
  		        		}if(deleted==true){
  		        			File from      = new File(getBaseContext().getFilesDir(), "favstemp.txt");
  		        	        File to        = new File(getBaseContext().getFilesDir(), "favs.txt");
  		        	        from.renameTo(to);
  		        		}
		        	}catch(Exception f){Log.d("error1j54 "+ f.toString(),"gotcha");}
	    	  //renamed
	    	  }catch(Exception e){Log.d("error is "+e.toString(),"wookey");}
	    	//deleted feed
	    	  Toast.makeText(ListViewA.this, "Feed deleted",  Toast.LENGTH_SHORT ).show();
	    	  //Log.d("iy","qora");
	    	    favourites();
	            return true;
	      default:
	            return super.onContextItemSelected((android.view.MenuItem) item);
	      }
	}
    private void favourites(){
    	File file3=null;
    	registerForContextMenu(lv);//
    	try{
    		file3 =getBaseContext().getFileStreamPath("favs.txt");}catch(Exception e){Log.d("er1 "+e.toString(),"aszx");
    		}
    	String arr[]=null;
    	if(file3.exists()){
    		try{//
    			boolean nofeeds=false;
    			InputStream is1=getApplicationContext().openFileInput("favs.txt");
        		InputStreamReader isr1=new InputStreamReader(is1);
        		BufferedReader br1=new BufferedReader(isr1);
    			if(br1.readLine()==null){
    				nofeeds=true;
    			}
    			br1.close();
    			isr1.close();
    			is1.close();
    			//
    			if(!nofeeds==true){
    		InputStream is=getApplicationContext().openFileInput("favs.txt");
    		InputStreamReader isr=new InputStreamReader(is);
    		BufferedReader br=new BufferedReader(isr);
    		
    		String line="";
    		int count=count("favs.txt");
    		arr=new String[count];
    		//Log.d("count is "+Integer.toString(count),"aszx");
    		for(int i=0;i<count;i++){
    			line=br.readLine();
    			//Log.d(line,"aszx");
    			arr[i]=line;
    			
    		}
    	}
    	}
    	catch(Exception e){Log.d("error is2 "+e.toString(),"aszx");}
    		favshelp(arr);
    	}
    }
    
    String favsAdds[];
    String favsnames[];
    public void favshelp(String[] arr){
    	try{
    		lv=null;
    		lv= (ListView)findViewById(R.id.listview);
        title = (TextView) findViewById(R.id.title);
        icon  = (ImageView) findViewById(R.id.icon);
        lv.setBackgroundColor(Color.TRANSPARENT);
        lv.setCacheColorHint(Color.TRANSPARENT);
        	String iamoutofnames[]=null;
        	//Log.d("arrlen is "+Integer.toString(arr.length),"aszx");
        	favsAdds=new String[arr.length];
        	favsnames=new String[arr.length];
        	fillMaps=null;
        	fillMaps = new ArrayList<HashMap<String, String>>();
        	try{
        	for(int q = 0; q <arr.length; q++){
            	HashMap<String, String> map = new HashMap<String, String>();
            	iamoutofnames=null;
            	int len;
            	
            		iamoutofnames=arr[q].split(";");
            		map.put("col_1", iamoutofnames[0]);
            		favsAdds[q]=iamoutofnames[1];
            		//Log.d("arrlen is "+iamoutofnames[0],"aszx");
            		favsnames[q]=iamoutofnames[0];
            		//Log.d("arrlen is "+iamoutofnames[1],"aszx");
            		
            	fillMaps.add(map);//Log.d("arrlen is1","qora");
            	lv.setOnItemClickListener(onListClickfavs);
            }
        	}
        	catch(Exception e){Log.d("error is4 "+e.toString(),"aszx");}
                
        	//Log.d("arrlen is2","qora");
        	
        	SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.grid_item, from, to);
        	playa=true;
        	//Log.d("arrlen is3","qora");
            lv.setAdapter(adapter);//Log.d("arrlen is4","qora");
    }
	catch(Exception e){Log.d("error is1 "+e.toString(),"aszx");}
        }
    private AdapterView.OnItemClickListener onListClickfavs=new AdapterView.OnItemClickListener(){
        public void onItemClick(AdapterView<?> parent,View view, int position, long id)
        {
        	String[] gh=new String[2];//feedaddresses;
    		gh[0]=favsnames[position];
    		gh[1]=favsAdds[position];
    		Intent intentbro1=new Intent(ListViewA.this,Webview.class); 
    		intentbro1.putExtra("feed",gh); 
    		playa=true;
    		active=true; 
    		intentbro1.setFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP); 
    		startActivity(intentbro1); 
        } 
    };
    
    boolean gff=false;
    ProgressDialog myPd_ring;
    ProgressDialog myPd_ring1;
    private void topfeeds(){
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
    	if(shouldupdate==true){
    	if(isOnline()==true){
    	new RetreiveFeedTask().execute(ctr,ctz);
    	myPd_ring=ProgressDialog.show(ListViewA.this, "Loading feeds", "Loading, please wait..", true);
        myPd_ring.setCancelable(true);
    	}else{
    		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					ListViewA.this);
			   
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
    	else{
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
    		er= h;
    		gff=true;
        	asynchelp(er);
    	}
    }
    
    public static String readTextFile(String fileName) {
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

	public static void writeTextFile(String fileName, String s) {
	    FileWriter output;
	    try {
	        output = new FileWriter(fileName);
	        BufferedWriter writer = new BufferedWriter(output);
	        writer.write(s);
	        writer.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
}

