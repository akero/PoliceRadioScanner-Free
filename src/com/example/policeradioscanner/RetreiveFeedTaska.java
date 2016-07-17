package com.example.policeradioscanner;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

public class RetreiveFeedTaska extends AsyncTask<Context, Void, String[]>{

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
		        try{
		        File file = base.getFileStreamPath("TOPFEEDS.txt");
		        boolean numerous=false;
		        Context context=app;
		        while ((line = reader.readLine()) != null) {
		        	
		        	if(z==0){
		        		InputStream is;
		        		
		        		if(file.exists()==true){
		        			is = app.openFileInput("TOPFEEDS.txt");
		        		}
		        		else{
		        			File file1 = new File(context.getFilesDir(), "TOPFEEDS.txt");
		        			is = app.openFileInput("TOPFEEDS.txt");
		        		}
		        		
		        		InputStreamReader iz=new InputStreamReader(is);
		        	BufferedReader br = new BufferedReader(iz);     
		        	if (br.readLine() == null ) {
		        		empty=true;
		        	    System.out.println("No errors, and file empty");
		        	}
		        		z=1;
		        	}
		        	
		        	if(empty==true){
		        		Context ctx=app;
		        		AssetManager am = ctx.getAssets();
		        		FileOutputStream fos;
		        		fos = app.openFileOutput("TOPFEEDS.txt", Context.MODE_APPEND);
		        	//FileWriter outFile = new FileWriter("TOPFEEDS.txt",true);
	                //PrintWriter out = new PrintWriter(outFile);
		        	if(line.contains("<a href=\"/listen/ctid/")&&one!=1){
		        		
		        		int start = line.indexOf("<a href=\"/listen/ctid/");
		        		
		                int end = line.indexOf("</a><br />");
		                
		                String a="";
		                String c="";
		                a=(line.substring(start + "<a href=\"/listen/ctid/".length(), end));
		                String[] b=a.split("\"");
		                c=b[0];
		               // System.out.println(c);
		  
		                //out.print(c+";");
		                fos.write((c+";").getBytes());
		                one=1;
		                fos.close();
		                
		                
		        	}else if(line.contains("Numerous")&&one!=2){
		        		String c="";
		                c="Numerous";
		                //System.out.println(c);
		                //out.print(c+";");
		                fos.write((c+";").getBytes());
		                one=2;
		                numerous=true;
		                fos.close();
		                //outFile.flush();
		        	}else if(line.contains("<a href=\"/listen/feed/")&&(!line.contains("class=\"button-info\""))&&(!line.contains("<span class="))&&(!line.contains("<!DOCTYPE html>"))){ 
		        		
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
		                //out.println(c);
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
	                	 //out.println(c+"#"+feed);
	                	 fos.write((c+"#"+feed+"\n").getBytes());
			             fos.close();
	                	 }
		                one=3;
		                numerous=false;
		        }
		        	//out.close();
		        }else{
		        	FileOutputStream fos;
	        		fos = app.openFileOutput("TOPFEEDSTEMP.txt", Context.MODE_APPEND);
		        	//FileWriter outFile = new FileWriter("TOPFEEDSTEMP.txt",true);
	                //PrintWriter out = new PrintWriter(outFile);
		        	if(line.contains("<a href=\"/listen/ctid/")&&one!=1){
		        		
		        		int start = line.indexOf("<a href=\"/listen/ctid/");
		        		
		                int end = line.indexOf("</a><br />");
		                
		                String a="";
		                String c="";
		                a=(line.substring(start + "<a href=\"/listen/ctid/".length(), end));
		                String[] b=a.split("\"");
		                c=b[0];
		               // System.out.println(c);
		                //out.print(c+";");
		                fos.write((c+";").getBytes());
			            fos.close();
		                one=1;
		                //outFile.flush();
		                
		        	}else if(line.contains("Numerous")&&one!=2){
		        		String c="";
		                c="Numerous";
		                //System.out.println(c);
		                //out.print(c+";");
		                fos.write((c+";").getBytes());
			            fos.close();
		                one=2;
		                numerous=true;
		                //outFile.flush();
		        	}else if(line.contains("<a href=\"/listen/feed/")&&(!line.contains("class=\"button-info\""))&&(!line.contains("<span class="))&&(!line.contains("<!DOCTYPE html>"))){ 
		        		
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
			                //out.println(c);
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
			                	 //out.println(c+"#"+feed);
			                	fos.write((c+"#"+feed+"\n").getBytes());
					            fos.close();
			                	}
		                numerous=false;
		                one=3;
		        }
		        	//out.close();
		        	hasbeeninthere=true;
		        }
		        }
		        if(empty==false&&hasbeeninthere==true){
		        	PrintWriter writer = new PrintWriter(base.getFileStreamPath("TOPFEEDS.txt"));
		        	writer.print("");
		        	writer.close();
		        	String j;
		        	j=readTextFile("TOPFEEDSTEMP.txt");
		        	writeTextFile("TOPFEEDS.txt",j);
		        	PrintWriter writer1 = new PrintWriter("TOPFEEDSTEMP.txt");
		        	writer1.print("");
		        	writer1.close();
		        }
		        
		        
		    }catch(Exception f){Log.d("error1 "+ f.toString()+line,"tagyo");
		    PrintWriter writer1 = new PrintWriter("TOPFEEDSTEMP.txt");
        	writer1.print("");
        	writer1.close();
        }
			
			
			
		}catch(Exception e){Log.d("error2 "+ e.toString(),"tagyo");}
		
		int g=0;
		int count = 0;
		try{
		//g=count("TOPFEEDS.txt");
			InputStream is = app.openFileInput("TOPFEEDS.txt");
			BufferedInputStream bis1 = new BufferedInputStream(is);
		    //InputStream is = new BufferedInputStream(new FileInputStream(filename));
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
				}
				
		}catch(Exception e){Log.d("Error is "+e.toString(),"TAGER" );
		}
		
		}catch(Exception e){Log.d("error is "+e.toString(),"taggg");}
		String[] a=new String[g];
		
		return h;
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
    protected void onPostExecute() {
        // TODO: check this.exception 
    	
        // TODO: do something with the feed
    }
	

	
 }