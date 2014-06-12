package com.dopewarsplus.neighborhoods;

import java.util.ArrayList;

import android.os.Bundle;

import com.dopewarsplus.Drugs;
import com.dopewarsplus.Neighborhood;

public class ChinaTown extends Neighborhood {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    protected void populateFields() {
        ArrayList<String> drugs = new ArrayList<String>();
        drugs.add(Drugs.WEED);
        drugs.add(Drugs.SPEED);
        drugs.add(Drugs.METH);
        if (Drugs.r.nextInt(10) > 4)
            drugs.add(Drugs.MOLLY);
        if (Drugs.r.nextInt(10) > 5)
            drugs.add(Drugs.LSD);
        if (Drugs.r.nextInt(10) > 7)
            drugs.add(Drugs.COKE);
        this.drugs = drugs.toArray(new String[drugs.size()]);
        priceFactor = 3;
        crimeFactor = 7;
        name = "China Town";
    }

}
