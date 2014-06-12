package com.dopewarsplus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.dopewarsplus.R;

public class MoveDialog extends DialogFragment implements
    DialogInterface.OnClickListener {
    
    public MoveDialog() {
        
    }
    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setCancelable(true);
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        setStyle(style, theme);
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setNegativeButton("Cancel", this);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogLayout = inflater.inflate(R.layout.move_dialog, null);
        builder.setView(dialogLayout);
    
        final String[] items = {"Red", "Green", "Blue" };
    
        builder.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items), 
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.v("touched: ", items[which].toString());
            }
        });

        return builder.create();
    
    }
    
    @Override
    public void onClick(DialogInterface dialog, int which) {
        // TODO Auto-generated method stub
    }

}