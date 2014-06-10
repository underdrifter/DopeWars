package com.dopewarsplus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dopewarsplus.neighborhoods.Ghetto;
import com.example.dopewarsplus.R;

/**
 * Start page, displays main menu
 */
public class StartPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        
        setContentView(R.layout.activity_start_page);
    }
    
    /**
     * Starts a new game
     */
    public void startNewGame(View view) {
        Intent intent = new Intent(this, Ghetto.class);
        Game.startNewGame(30);
        startActivity(intent);
    }
}
