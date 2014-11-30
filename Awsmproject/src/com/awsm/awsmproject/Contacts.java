package com.awsm.awsmproject;

import java.io.File;
import java.io.FileOutputStream;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class Contacts extends ActionBarActivity {
	
	SimpleCursorAdapter mAdapter;  
	
	
	/* SimpleCursor Adapter is an easy adapter to map columns from a cursor to TextViews or
	ImageViews defined in an XML file. 
	You can specify which columns you want, which views you want to display the columns, 
	and the XML file that defines the appearance of these views.
	
	In this case, we want to get the values which are in the columns(name and photo) from the MatrixCursor
	and then put them in the textview and imageview of our single listview item.
	*/
	
	
	MatrixCursor mMatrixCursor;    
	
	
	
	/* This is the cursor that will store the contacts from the contacts content provider.
	 We can specify the column names like "name" and "photo" and store the names and photos to these columns.
	Then in the SimpleCursorAdapter, we specify that we want the details from the columns("name" and "photo")
	 and pass it to the textview and imageview.
	 */
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
		
		
		//If we do need details as well , we can add it this way.
        //mMatrixCursor = new MatrixCursor(new String[] { "_id","name","photo","details"} ); 
		
		
		/*Specifying the column names to which data will be stored. 
		We can use these column names to pass data to our listview items */
		
		mMatrixCursor = new MatrixCursor(new String[] { "_id","name","photo"} );  
		
		
		/* Parameters are context, layout of the listviewitem, Cursor, Strings from(here name and photo), int[] to(here our textview and imageview), flags  */
       
		
		mAdapter = new SimpleCursorAdapter(getBaseContext(), 
                R.layout.contacts_single_item, 
                null,
                new String[] { "name","photo"}, // strings from name and photo to textview and imageview
                new int[] { R.id.contact_name,R.id.contact_photo}, 0);

        // Getting reference to listview
        
		ListView lstContacts = (ListView) findViewById(R.id.list_view_for_contacts);
 
        // Setting the adapter to listview . Tells the listview that each row in the listview should be populated from that adapter.
       
		lstContacts.setAdapter(mAdapter);
 
        // Creating an AsyncTask object to retrieve and load listview with contacts
        
		ListViewContactsLoader listViewContactsLoader = new ListViewContactsLoader();
 
        // Starting the AsyncTask process to retrieve and load listview with contacts. Need to call the execute method to carry out the AsyncTask. 
        //The execute is performed in 4 steps : onPreExecute, doInBackground, onProgressUpdate , onPostExecute
        
		listViewContactsLoader.execute();
    }
 
    /* An AsyncTask class to retrieve and load listview with contacts */
    private class ListViewContactsLoader extends AsyncTask<Void, Void, Cursor>{
 
        @Override
        protected Cursor doInBackground(Void... params) {
            Uri contactsUri = ContactsContract.Contacts.CONTENT_URI;
 
            
            //Querying the table ContactsContract.Contacts and sorting the results by DISPLAY_NAME. Not sure why ASC is there.
            
            Cursor contactsCursor = getContentResolver().query(contactsUri, null, null, null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC "); // FIXME Works even without ASC. Not sure why.
            
            
            //contactsCursor now contains all the sorted contacts as a cursor.
            
            
            if(contactsCursor.moveToFirst())  //Returns false if cursor is empty , else moves the cursor to the first row
            { 
                do{
                    long contactId = contactsCursor.getLong(contactsCursor.getColumnIndex("_ID")); // Get the id of each contact.
 
                    Uri dataUri = ContactsContract.Data.CONTENT_URI; //Reference to the Contact Data.
 
                    // Querying the table ContactsContract.Data to retrieve items  like
                    // home phone, mobile phone, work email etc corresponding to each contact referenced by contactId.
                    
                    Cursor dataCursor = getContentResolver().query(dataUri, null,
                                        ContactsContract.Data.CONTACT_ID + "=" + contactId,
                                        null, null);
 
                    String displayName="";
                    String photoPath="" + R.drawable.ic_launcher;
                    byte[] photoByte=null;
                    
                    
                    /* Include to get other details of the person.
                    String nickName="";
                    String homePhone="";
                    String mobilePhone="";
                    String workPhone="";
                    String homeEmail="";
                    String workEmail="";
                    String companyName="";
                    String title="";
 					*/
                    
                    
                    if(dataCursor.moveToFirst()){
                        // Getting Display Name
                        displayName = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME ));
                        do{
                        	/*
                            // Getting NickName
                            if(dataCursor.getString(dataCursor.getColumnIndex("mimetype")).equals(ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE))
                                nickName = dataCursor.getString(dataCursor.getColumnIndex("data1"));
 
                            // Getting Phone numbers
                            if(dataCursor.getString(dataCursor.getColumnIndex("mimetype")).equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)){
                                switch(dataCursor.getInt(dataCursor.getColumnIndex("data2"))){
                                    case ContactsContract.CommonDataKinds.Phone.TYPE_HOME :
                                        homePhone = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                        break;
                                    case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE :
                                        mobilePhone = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                        break;
                                    case ContactsContract.CommonDataKinds.Phone.TYPE_WORK :
                                        workPhone = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                        break;
                                }
                            }
 
                            // Getting EMails
                            if(dataCursor.getString(dataCursor.getColumnIndex("mimetype")).equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE ) ) {
                                switch(dataCursor.getInt(dataCursor.getColumnIndex("data2"))){
                                    case ContactsContract.CommonDataKinds.Email.TYPE_HOME :
                                        homeEmail = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                        break;
                                    case ContactsContract.CommonDataKinds.Email.TYPE_WORK :
                                        workEmail = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                        break;
                                }
                            }
 
                            // Getting Organization details
                            if(dataCursor.getString(dataCursor.getColumnIndex("mimetype")).equals(ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)){
                                companyName = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                title = dataCursor.getString(dataCursor.getColumnIndex("data4"));
                            }
 							*/
                        	
                        	
                        	
                            // Getting Photo
                            if(dataCursor.getString(dataCursor.getColumnIndex("mimetype")).equals(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)){
                                photoByte = dataCursor.getBlob(dataCursor.getColumnIndex("data15"));
 
                                if(photoByte != null) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length);
 
                                    // Getting Caching directory
                                    File cacheDirectory = getBaseContext().getCacheDir();
 
                                    // Temporary file to store the contact image
                                    File tmpFile = new File(cacheDirectory.getPath() + "/wpta_"+contactId+".png");
 
                                    // The FileOutputStream to the temporary file
                                    try {
                                        FileOutputStream fOutStream = new FileOutputStream(tmpFile);
 
                                        // Writing the bitmap to the temporary file as png file
                                        bitmap.compress(Bitmap.CompressFormat.PNG,100, fOutStream);
 
                                        // Flush the FileOutputStream
                                        fOutStream.flush();
 
                                        //Close the FileOutputStream
                                        fOutStream.close();
 
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    photoPath = tmpFile.getPath();
                                }
                            }
                        }while(dataCursor.moveToNext());
                        
                        
                        
                        /*
                        
                        String details = "";
 
                        // Concatenating various information to single string
                        if(homePhone != null && !homePhone.equals("") )
                            details = "HomePhone : " + homePhone + "\n";
                        if(mobilePhone != null && !mobilePhone.equals("") )
                            details += "MobilePhone : " + mobilePhone + "\n";
                        if(workPhone != null && !workPhone.equals("") )
                            details += "WorkPhone : " + workPhone + "\n";
                        if(nickName != null && !nickName.equals("") )
                            details += "NickName : " + nickName + "\n";
                        if(homeEmail != null && !homeEmail.equals("") )
                            details += "HomeEmail : " + homeEmail + "\n";
                        if(workEmail != null && !workEmail.equals("") )
                            details += "WorkEmail : " + workEmail + "\n";
                        if(companyName != null && !companyName.equals("") )
                            details += "CompanyName : " + companyName + "\n";
                        if(title != null && !title.equals("") )
                            details += "Title : " + title + "\n";
                            
                            
                        */
 
                        // Adding id, display name and path to photo  to cursor
                        mMatrixCursor.addRow(new Object[]{ Long.toString(contactId),displayName,photoPath});
                        
                        /**
                          If we need to add other details to cursor ,
                           uncomment the details and other lines
                            and add  mMatrixCursor.addRow(new Object[]{ Long.toString(contactId),displayName,photoPath,details });
                         Also , there will have to be a column in the mMatrixCursor for details.
                         And we can add it to the list view by creating a textview in listitem and passing the details column to that textview
                         */
                    }
                }while(contactsCursor.moveToNext());
            }
            
            return mMatrixCursor;
            // Returns to the Function that called doInBackground which is part of AsyncTask
        }
 
        @Override
        protected void onPostExecute(Cursor result) {
        	
        	
            // Setting the cursor containing contacts to listview
        	//Property of SimpleCursorAdapter
            
        	mAdapter.swapCursor(result); 
        	
        }
    }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contacts, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
