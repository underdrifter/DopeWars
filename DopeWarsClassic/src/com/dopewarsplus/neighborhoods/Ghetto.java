package com.dopewarsplus.neighborhoods;

import android.os.Bundle;

import com.dopewarsplus.Neighborhood;

public class Ghetto extends Neighborhood {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.name = "Ghetto";
        this.factor = 0.5;
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        
    }

}
