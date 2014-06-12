package com.dopewarsplus;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dopewarsplus.R;

public class MoveFragment extends Fragment {
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.move_fragment, container, false);
    }
    
    public void initiateMenu(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
 
        // set title
        //alertDialogBuilder.setTitle("Your Title");
 
            // set dialog message
        alertDialogBuilder
            .setMessage("Return to Main Menu?")
            .setCancelable(false)
            .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    // if this button is clicked, close
                    // current activity
                    getActivity().finish();
                }
              })
            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    // if this button is clicked, just close
                    // the dialog box and do nothing
                        dialog.cancel();
                    }
                });
 
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
 
            // show it
            alertDialog.show();
    }

}
