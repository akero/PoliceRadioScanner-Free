package com.example.policeradioscanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;
 
    // Database Name
    private static final String DATABASE_NAME = "feedsmanager";
 
    // Contacts table name
    private static final String TABLE_CONTACTS = "table_to_hold_all_values";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";//ctid
    private static final String KEY_PH_NO = "phone_number";//feedname,address;feedname;address etc
    Context ctx;
    
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx=context;
    }
    
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	
        //Log.d("in onCreate","twitch1");
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_PH_NO + " TEXT);";
        db.execSQL(CREATE_CONTACTS_TABLE);
        String line="";
        InputStream is=null;
        
        try{
        	
        File fileq = ctx.getFileStreamPath("feedtitlesandaddresses.txt");
        if(!fileq.exists()){
        	is = ctx.getAssets().open("feedtitlesandaddresses.txt");
        	//Log.d("indatabasefileexists false","fubar");
        }else{
        	is = ctx.openFileInput("feedtitlesandaddresses.txt");
        	//Log.d("indatabasefileexists true","fubar");
        }
        
        InputStreamReader iz=new InputStreamReader(is);
        BufferedReader br = new BufferedReader(iz);//Log.d("fcked here8","twitch1");
        ContentValues values = new ContentValues();
        try{db.beginTransaction();
        while((line=br.readLine())!=null) {
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
              //Log.d(strfinal,"twitch11");
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
              //Log.d("cked here1","twitch1");
              values.put(KEY_NAME, firstNumber); // Contact Name
              values.put(KEY_PH_NO,strfinal); // Contact Phone
              //Log.d("cked here2","twitch1");
              // Inserting Row
              //Log.d("Inserting","YOYO");
              db.insert(TABLE_CONTACTS, null, values);}
        db.setTransactionSuccessful();
        }finally {
        	    
        		      db.endTransaction();
        		   
        		}
            //Log.d("cked here4","twitch1");
        }catch(Exception e){Log.d("yeah error is"+e,"twitch11");}
        
    }
 //populating database on create
    void populate(SQLiteDatabase dbs){
        try{
        InputStream is = ctx.getAssets().open("feedtitlesandaddresses.txt");
        InputStreamReader iz=new InputStreamReader(is);
        BufferedReader br = new BufferedReader(iz);//Log.d("fcked here8","twitch1");
        ContentValues values = new ContentValues();
        
              values.put(KEY_NAME, "NAME"); // Contact Name
              values.put(KEY_PH_NO, "BLA"); // Contact Phone
              dbs.insert(TABLE_CONTACTS, null, values);
        dbs.close();}catch(Exception e){Log.d("yeah error is"+e,"twitch12");}
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed

       // Log.d("in onupgrade","twitch");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new contact
    void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone
 
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }
 
    // Getting single contact
    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }
 
    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return contactList;
    }
 
    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());
 
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }
 
    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }
 
    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
 
}
