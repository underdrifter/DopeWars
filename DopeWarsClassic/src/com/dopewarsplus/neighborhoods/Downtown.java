package com.dopewarsplus.neighborhoods;

import java.util.ArrayList;

import android.os.Bundle;

import com.dopewarsplus.Drugs;
import com.dopewarsplus.Neighborhood;

public class Downtown extends Neighborhood {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected void populateFields() {
        ArrayList<String> drugs = new ArrayList<String>();
        drugs.add(Drugs.WEED);
        drugs.add(Drugs.SPEED);
        drugs.add(Drugs.METH);
        drugs.add(Drugs.MOLLY);
        drugs.add(Drugs.SPEED);
        if (Drugs.r.nextInt(10) > 2)
            drugs.add(Drugs.HEROIN);
        if (Drugs.r.nextInt(10) > 3)
            drugs.add(Drugs.PCP);
        if (Drugs.r.nextInt(10) > 4)
            drugs.add(Drugs.ADDERALL);
        if (Drugs.r.nextInt(10) > 5)
            drugs.add(Drugs.OXY);
        if (Drugs.r.nextInt(10) > 6)
            drugs.add(Drugs.COKE);
        if (Drugs.r.nextInt(10) > 7)
            drugs.add(Drugs.SHROOMS);
        this.drugs = drugs.toArray(new String[drugs.size()]);
        factor = 5;
        name = "Downtown";
        selection = "Excellent";
        crime = "Average";
    }

}
