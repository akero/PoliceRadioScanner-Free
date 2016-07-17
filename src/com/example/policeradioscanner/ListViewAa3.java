package com.example.policeradioscanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

public class ListViewAa3{
	public String[] process(String cti,Context context)throws IOException{
		String ctid=cti;
		//Reading file into a sparsearray
		//note br.readLine returns lines for only n-1, last line is empty for whatever reason, so don't save the Last line
		try{
			InputStream is = context.getAssets().open("feedtitlesandaddresses.txt");
        InputStreamReader iz=new InputStreamReader(is);
        BufferedReader br = new BufferedReader(iz);
        String line = null;
        
        //This is where I left off
        while((line=br.readLine())!=null) {
        	if(line.trim().isEmpty()){continue;}
        	//System.out.println("line: "+line);
        	readLaine(line);
        	}
        }
		catch(Exception e){e.printStackTrace();}
		int b=Integer.parseInt(ctid);
        SparseArray<String> subSparseArray = sparseArray.get(b); //You first need a 1D sparseArray
        int key = 0;
        String[] array1=new String[subSparseArray.size()];
        for(int i = 0; i < subSparseArray.size(); i++) {
           key = subSparseArray.keyAt(i);
           String feed = subSparseArray.get(key); //feed is the String in place (0,key)
           array1[i]=feed;
        }
        //printing array for test
        Log.d((String)array1[1],"millionfinal");
        return array1;
		
	}
	//sparsearray processor
	private String firstNumber = "";
    private StringTokenizer stringTokenizer = null;
    private SparseArray<SparseArray<String>> sparseArray = new SparseArray<SparseArray<String>>();
    private SparseArray<String> temporarySparseArray = null;
        
	private void readLaine(String line) {
		String[] array1;//saves name,address. separated by a comma
		//saving ctid
        stringTokenizer = new StringTokenizer(line, "<");
      //ctid
        firstNumber = (String) stringTokenizer.nextElement();
        //finding size of arrays
        char c = '<'; // search for this character ...
        int count = 0;
        int size=0;
        for (int i = 0 ; i < line.length() ; i++ ) {
          if ( line.charAt(i) == c ) {
            count += 1;
          }size=count/6;}
          array1=new String[size];
          //moving on
        //StringTokenizer st= new StringTokenizer(line, "title>");
        //a=(String)st.nextElement();
        String str="";
        String str1="";
        String st[]= line.split("title>");
        int t=1;
        for(int q=1;q<=(size);q++){
        
        if(q!=(size)){
        str=st[t].substring(0,st[t].length()-2)+";";
        t+=1;
        System.out.println("line1 is "+str);
        str1=st[t];
        t+=1;
        System.out.println("line2 is "+str1);
        str=str+str1.substring(10,str1.length()-27);
        System.out.println("line is "+str);
        array1[q-1]=str;
        	}
        else if(q==(size))
        	{
        	str=st[t].substring(0,st[t].length()-2)+";";
        	t+=1;
            str1=st[t];
            t+=1;
            str=str+str1.substring(10,str1.length()-28);
            array1[q-1]=str;
            }
        
        }
        //testing array1
        for(int as=0;as<array1.length;as++){
        	System.out.println("line "+array1[as]);
        }
        //secondNumber = (String) stringTokenizer.nextElement();
        //countyName = ((String) stringTokenizer.nextElement());
        //countyName = countyName.substring(0, countyName.length());
        int num1 = Integer.parseInt(firstNumber);//ctid
        
        //int num2 = Integer.parseInt(secondNumber);
        if(sparseArray.get(num1) == null) {
        	
            sparseArray.put(num1, new SparseArray<String>());
            
        }
        temporarySparseArray = sparseArray.get(num1);
        
        for(int w=0;w<size;w++){
        temporarySparseArray.put(w, array1[w]);
        		}
        sparseArray.put(num1, temporarySparseArray);
        
        temporarySparseArray = null;
        
        
    }
}
