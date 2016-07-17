

package com.example.policeradioscanner;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class mp3playerservice extends Service implements OnPreparedListener {
	String track="s";
	String info[];
	String name;
	MediaPlayer mp;
	Bundle extras;
	String test="a";
	String feedplaying="";
	TelephonyManager mgr;
  private int result = Activity.RESULT_CANCELED;

  public mp3playerservice() {
    super();
  }
  
  private final Binder mBinder= new MediaPlayerBinder();
  public IBinder onBind(Intent in){
	return mBinder;}
  
  public class MediaPlayerBinder extends Binder {
      /**
       * Returns the instance of this service for a client to make method calls on it.
       * @return the instance of this service.
       */
      public mp3playerservice getService() {
          return mp3playerservice.this;
      }
  }
  
  void onStart(){}
  public int onStartCommand(Intent intent, int flags, int startId) {
	  
	  try{
		   mgr= (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
			if(mgr != null) {
			    mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
				}
				}catch(Exception e){Log.d("error is "+e.toString(),"telephone");
			}
    noerror=false;
    test=track;
	//Log.d("in here","okay");
	try{
    extras = intent.getExtras();
    //Log.d("in here","okay");
    info=extras.getStringArray("info");
    //Log.d("in here","okay");
    track=info[1];
    name=info[0];}catch(Exception e){Log.d("error came up "+e.toString(),"okay");}
    //Log.d("in here","okay");
    if(mp!=null&&!test.equals(track)){Log.d("This fucker is working bro","tag");
	mp.release();
	mp=new MediaPlayer();
    }
    feedplaying=track;
    if (mp==null) mp=new MediaPlayer();
    
    //Log.d("in here","okay");
    if(!test.equals(track)){
    start1(track);}
   /* if(test.equals(track)&&stopped==true){
    	//start1(track);
    }*/
    noerror=true;
    return START_STICKY;
    
  }
  Boolean stopped=false;
  
  void stopAudio(){
	  if (mp != null && mp.isPlaying()){
	  try{
	  mp.stop();
	  mp.release();}catch(Exception e){Log.d("error "+e.toString(),"error");}
	  mp=null;
	  
	  stopped=true;
	  }
  }
  
  void startAudio(String a){
	  feedplaying=a;
	  stopped=false;
	  if(mp==null){
		  mp=new MediaPlayer();
		  
	  }
	  
	  start1(a);
  }
  
  public String track(){
	  return track;
  }
  
  public String trackname(){
	  return name;
  }
  
  public Boolean isStopped(){
	  if(stopped==true){
		  return true;
	  }else
		  return false;
  }
  
  // Will be called asynchronously be Android
  //@Override
  protected void onHandleIntent(Intent intent) {
	  test=track;
	  Log.d("in here","okay");try{
    extras = intent.getExtras();}catch(Exception e){Log.d("error came up "+e.toString(),"okay");}
    Log.d("in here","okay");
    info=extras.getStringArray("info");
    Log.d("in here","okay");
    track=info[1];
    name=info[0];
    Log.d("in here","okay");
    if (mp==null) mp=new MediaPlayer();
    if(mp!=null&&!test.equals(track)){Log.d("This fucker is working bro","tag");
		mp.release();
		mp=new MediaPlayer();
	}
    Log.d("in here","okay");
    if(!test.equals(track)){
    	feedplaying=track;
    start1(track);}
    }
  
  boolean running=false;
  boolean noerror=true;
  
  void start1(String a){
		
		try{Log.d("in here1","okay");
		
		mp.setDataSource(a);
		mp.setOnPreparedListener(this);
		feedplaying=a;
		mp.prepareAsync();//Async();
		stopped=false;
		//mp.start();
		running=true;
		}catch(Exception e){
			noerror=false;
			Log.d("in here1","okay");Log.d("error is "+e.toString(),"error came up");
			}
		Log.d("in here1","okay");
  }
  boolean isError(){
	  return noerror;
  }
  
  @Override
  public void onPrepared(MediaPlayer mp1){
	  Log.d("in here2","okay");
		mp.start();
		Log.d("in here2","okay");
	}
  
  Boolean stoppedCozofCall=false;
  PhoneStateListener phoneStateListener = new PhoneStateListener() {
	    @Override
	    public void onCallStateChanged(int state, String incomingNumber) {
	        if (state == TelephonyManager.CALL_STATE_RINGING) {
	            //Incoming call: Pause music
	        	if(isStopped()==false){
	        		stopAudio();
	        		stoppedCozofCall=true;
	        		Log.d("stopcoz1"+Boolean.toString(stoppedCozofCall),"aszx");
	        	}
	        } else if(state == TelephonyManager.CALL_STATE_IDLE) {
	            //Not in call: Play music
	        	if(stoppedCozofCall==true){
	        		startAudio(feedplaying);
	        		Log.d("stopcoz2.0"+Boolean.toString(stoppedCozofCall),"aszx");
	        		Log.d(feedplaying,"aszx");
	        	}
	        	stoppedCozofCall=false;
	        	Log.d("stopcoz2.1"+Boolean.toString(stoppedCozofCall),"aszx");
	        	
	        } else if(state == TelephonyManager.CALL_STATE_OFFHOOK) {
	            //A call is dialing, active or on hold
	        	if(isStopped()==false){
	        		stopAudio();
	        		stoppedCozofCall=true;
	        	}
	        	Log.d("stopcoz3"+Boolean.toString(stoppedCozofCall),"aszx");
	        }
	        super.onCallStateChanged(state, incomingNumber);
	    }
	};
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		try{
			   mgr= (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
				if(mgr != null) {
				    mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
				}
				
			}catch(Exception e){Log.d("error is "+e.toString(),"telephone");
		}
	}
} 