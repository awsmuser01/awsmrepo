package com.awsm.awsmproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;




public class MainActivity extends Activity {
    EditText PhNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PhNumber = (EditText) findViewById(R.id.enterphonenumber);
        disableSoftInputFromAppearing(PhNumber);
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

    public void one(View view) {
        int start = PhNumber.getSelectionStart();

        PhNumber.getText().insert(start, "1");
    }

    public void two(View view) {
        int start = PhNumber.getSelectionStart();

        PhNumber.getText().insert(start, "2");
    }

    public void three(View view) {
        int start = PhNumber.getSelectionStart();

        PhNumber.getText().insert(start, "3");
    }

    public void four(View view) {
        int start = PhNumber.getSelectionStart();

        PhNumber.getText().insert(start, "4");
    }

    public void five(View view) {
        int start = PhNumber.getSelectionStart();

        PhNumber.getText().insert(start, "5");
    }

    public void six(View view) {
        int start = PhNumber.getSelectionStart();

        PhNumber.getText().insert(start, "6");
    }

    public void seven(View view) {
        int start = PhNumber.getSelectionStart();

        PhNumber.getText().insert(start, "7");
    }

    public void eight(View view) {
        int start = PhNumber.getSelectionStart();

        PhNumber.getText().insert(start, "8");
    }

    public void nine(View view) {
        int start = PhNumber.getSelectionStart();

        PhNumber.getText().insert(start, "9");
    }

    public void zero(View view) {
        int start = PhNumber.getSelectionStart();

        PhNumber.getText().insert(start, "0");
    }

    public void star(View view) {
        int start = PhNumber.getSelectionStart();

        PhNumber.getText().insert(start, "*");
    }

    public void hash(View view) {
        int start = PhNumber.getSelectionStart();

        PhNumber.getText().insert(start, "#");
    }

    public void call(View view) {
        Intent calling = new Intent(Intent.ACTION_CALL);
        String number = PhNumber.getText().toString();
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


}

