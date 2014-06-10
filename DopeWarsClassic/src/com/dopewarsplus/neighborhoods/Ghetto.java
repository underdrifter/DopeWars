package com.dopewarsplus.neighborhoods;

import android.os.Bundle;

import com.dopewarsplus.Drugs;
import com.dopewarsplus.Neighborhood;

public class Ghetto extends Neighborhood {
    
     // drugs for sale here
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.name = "Ghetto";
        this.factor = 3;
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        populateDrugs(drugs);
    }
    
    @Override
    protected String[] getDrugNames() {
        String[] drugs = {Drugs.HEROIN, Drugs.LSD, Drugs.METH, Drugs.MOLLY, Drugs.PCP,
                Drugs.SHROOMS, Drugs.SPEED, Drugs.WEED};
        return drugs;
    }

}
