package com.example.policeradioscanner;


	import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.akero.fuzzradiopro.R;
import com.flurry.android.FlurryAgent;

	public class testinternalstoragewrite extends Activity{
		EditText editTextFileName,editTextData;
		Button saveButton,readButton;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.testinternalstoragewritelayout);
			
			editTextFileName=(EditText)findViewById(R.id.editText1);
			editTextData=(EditText)findViewById(R.id.editText2);
			saveButton=(Button)findViewById(R.id.button1);
			readButton=(Button)findViewById(R.id.button2);
			
			//Performing Action on Read Button
			saveButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					String filename=editTextFileName.getText().toString();
					String data=editTextData.getText().toString();
					
					FileOutputStream fos;
					   try {
					    fos = openFileOutput(filename, Context.MODE_PRIVATE);
					    //default mode is PRIVATE, can be APPEND etc.
					    fos.write((data+"\n"+"new line").getBytes());
					    fos.close();
					   
					    Toast.makeText(getApplicationContext(),filename + " saved",
								Toast.LENGTH_LONG).show();
					    
					   
					   } catch (FileNotFoundException e) {e.printStackTrace();}
					   catch (IOException e) {e.printStackTrace();}
					
				}
				
			});
			
			//Performing Action on Read Button
			readButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					String filename=editTextFileName.getText().toString();
					StringBuffer stringBuffer = new StringBuffer();  
					try {
					    //Attaching BufferedReader to the FileInputStream by the help of InputStreamReader
					    BufferedReader inputReader = new BufferedReader(new InputStreamReader(
					            openFileInput(filename)));
					    String inputString;
					    //Reading data line by line and storing it into the stringbuffer              
					    while ((inputString = inputReader.readLine()) != null) {
					        stringBuffer.append(inputString + "\n");
					    }
					    
					} catch (IOException e) {
					    e.printStackTrace();
					}
					//Displaying data on the toast
					Toast.makeText(getApplicationContext(),stringBuffer.toString(),
							Toast.LENGTH_LONG).show();
				    
				}
				
			});
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.activity_main, menu);
			return true;
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
	}

