package com.dopewarsplus;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.dopewarsplus.R;

/**
 * This is what the user sees as they play the game.
 */
public abstract class Neighborhood extends Activity {

    protected Game game;
    protected String name;
    // lower factor is lower price, should be between 0.5 and 1.5
    // i.e. drugs are cheap in the ghetto but expensive in the business district
    protected double factor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_neighborhood);
        game = Game.currentGame;
        game.addDay();
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        TextView townNameView = (TextView) findViewById(R.id.town_name_text_view);
        townNameView.setText(this.name);
        TextView daysLeftView = (TextView) findViewById(R.id.days_left_text_view);
        daysLeftView.setText("Day " + game.getDay() + "/" + game.duration);
        updateMoney();
    }
    
    protected void updateMoney() {
        TextView cashView = (TextView) findViewById(R.id.cash_text_view);
        cashView.setText("Cash: " + formatMoney(game.player.getMoney()));
        TextView debtView = (TextView) findViewById(R.id.debt_text_view);
        debtView.setText("Debt: " + formatMoney(game.getDebt()));
        TextView bankView = (TextView) findViewById(R.id.bank_text_view);
        bankView.setText("Bank: " + formatMoney(game.getBankCash()));
    }
    
    /**
     * Returns the given int in money format ie $123,123,123
     */
    public static String formatMoney(int money) {
        if (money == 0)
            return "$0";
        StringBuilder result = new StringBuilder("" + money % 1000);
        money /= 1000;
        while (money > 99) {
            result.insert(0, ",");
            result.insert(0, money % 10);
            money /= 10;
            result.insert(0, money % 10);
            money /= 10;
            result.insert(0, money % 10);
            money /= 10;
        }
        while (money > 0) {
            result.insert(0, ",");
            result.insert(0, money % 10);
            money /= 10;
        }
        result.insert(0, "$");
        return result.toString();
    }

}
