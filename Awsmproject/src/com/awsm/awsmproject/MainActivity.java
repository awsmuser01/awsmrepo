package com.awsm.awsmproject;

import java.io.InputStream;

import org.json.JSONArray;

import com.awsm.database.Query;
import com.awsm.database.Query.AsyncResponse;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends Activity implements AsyncResponse{
	EditText PhNumber;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*PhNumber = (EditText) findViewById(R.id.enterphonenumber);
        disableSoftInputFromAppearing(PhNumber);*/
	}




	public static void disableSoftInputFromAppearing(EditText editText) {
		if (Build.VERSION.SDK_INT >= 11) {
			editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
			editText.setTextIsSelectable(true);
		} else {
			editText.setRawInputType(InputType.TYPE_NULL);
			editText.setFocusable(true);
		}
	}

	public void insertNumber(View view){
		String s = view.getTag().toString();
		int start = PhNumber.getSelectionStart();
		PhNumber.getText().insert(start, s);
	}

	public void call(View view) {
		Intent calling = new Intent(Intent.ACTION_CALL);
		String number = PhNumber.getText().toString();
		number=number.replace("#","%23" );
		if (number.length() != 0)                          // Check if phone number is not entered
		{
			calling.setData(Uri.parse("tel:" + number));
			startActivity(calling);
		}
	}

	public void backspace(View view) {
		int start = PhNumber.getSelectionStart();             //Obtain cursor location
		if (start > 0)                                     //Check if cursor location is not at zero because then it cant delete
			PhNumber.getText().delete(start - 1, start);   //delete characters from cursor location -1 to cursor location , that is , delete the character before the cursor location

	}

	public void select(View v)
	{
		String query = "p";
		new Query().getSuggestionsForSearchString(query).delegate= this;
	}


	@Override
	public void processFinish(JSONArray output) {
		Log.i("Result inside processFinish", output.toString());
	}





}

