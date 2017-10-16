package com.example.chungwei.placetogo.helpers;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AllRounderClass extends Activity{

    final private int REQUEST_CALL_CONTACTS = 456;

    //Calling Function/Method
    public void callcaller(Context context, String phonenumber, String contactname) {

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CALL_CONTACTS);
            Snackbar.make(((Activity) context).findViewById(android.R.id.content), "Phone permission required.", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", null)
                    .show();
        }
        else{
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CALL_CONTACTS);
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+phonenumber));
            context.startActivity(intent);
            Toast.makeText(context, "Calling " + contactname , Toast.LENGTH_LONG).show();
        }
    }

    //Snapbar Method
    public void SnackbarMessage(View v, String mainText, String actionText){
        Snackbar.make(v, mainText, Snackbar.LENGTH_LONG).setAction(actionText,null).show();
    }

}
