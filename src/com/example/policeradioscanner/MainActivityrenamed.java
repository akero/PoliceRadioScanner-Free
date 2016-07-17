package com.example.policeradioscanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.akero.fuzzradiopro.R;
import com.flurry.android.FlurryAgent;
public class MainActivityrenamed extends Activity {
	 protected TextView title;
	    protected ImageView icon;
	 
public final static String EXTRA_MESSAGE="com.example.PoliceRadioScanner.MESSAGE";

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        Log.d("fked", "fked-2");
        setContentView(R.layout.activity_maintest);
        Intent intent2=new Intent(this,ListViewA.class);
    	startActivity(intent2);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);
        
        //title = (TextView) findViewById(R.id.title);
        //icon  = (ImageView) findViewById(R.id.icon);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void nextPage(View view1)
    {
    	//Intent intent1=new Intent(this,NextPage.class);
    	//startActivity(intent1);
    }
    public void nextList(View view2)
    {
    	Intent intent2=new Intent(this,ListViewA.class);
    	startActivity(intent2);
    }
    
public void nextPage2(View view3)
{
	Intent intent3=new Intent(this,update.class);
	startActivity(intent3);
	//Intent intent3=new Intent(this,testinternalstoragewrite.class);
   // startActivity(intent3);
}

public void webview1(View arg0) {
    Intent intent5 = new Intent(this, Webview.class);
    startActivity(intent5);
  }
public void MainActivityap(View view4){
	Log.d("fked", "fked0");
	//Intent intent9=new Intent(this,MainActivityap.class);
	Log.d("fked", "fked-1");
	
	//startActivity(intent9);
}
}
