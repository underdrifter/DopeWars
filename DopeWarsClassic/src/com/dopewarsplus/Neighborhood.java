package com.dopewarsplus;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dopewarsplus.R;

/**
 * This is what the user sees as they play the game.
 */
public abstract class Neighborhood extends Activity {

    protected Game game;
    protected String name;
    // lower factor is lower price, should be between 1 and 10, 5 is avg price
    // i.e. drugs are cheap in the ghetto but expensive in the business district
    protected int factor;
    // both of these, the index in the listview corresponds to the drugs name and price
    protected String[] drugs = getDrugNames();
    protected int[] prices;
    
    protected abstract String[] getDrugNames();

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
        prices = new int[drugs.length];
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
    
    protected void populateDrugs(final String[] drugs) {
        // Get the reference of ListView
        ListView drugListView=(ListView)findViewById(R.id.drugs_list_view);
        // Create The Adapter with passing Array as 3rd parameter
        ArrayAdapter<String> arrayAdapter =      
               new DrugAdapter(this, android.R.layout.simple_list_item_1, drugs);
        // Set The Adapter
        drugListView.setAdapter(arrayAdapter);
        drugListView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View v, int pos, long arg3) {
                String drugName = drugs[pos];
                int drugPrice = prices[pos];
                Toast.makeText(getApplicationContext(), drugName + " " + drugPrice, Toast.LENGTH_SHORT).show();
            }
        });
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
        StringBuilder result = new StringBuilder();
        int comaCounter = 0;
        do {
            if (comaCounter == 3) {
                comaCounter = 0;
                result.insert(0,  ",");
            }
            result.insert(0, money % 10);
            money /= 10;
            comaCounter++;
        } while (money > 0);
        result.insert(0, "$");
        return result.toString();
    }
    
    private class DrugAdapter extends ArrayAdapter<String> {

        public DrugAdapter(Context context, int textViewResourceId, String[] drugs) {
                super(context, textViewResourceId, drugs);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.drug_item, null);
            }
            String drug = drugs[position];
            int price = prices[position];
            if (price == 0) {
                price = Drugs.getPrice(drug, factor);
                prices[position] = price;
            }
            TextView qtyTextView = (TextView) v.findViewById(R.id.qty_owned_text_view);
            TextView nameTextView = (TextView) v.findViewById(R.id.drug_name_text_view);
            TextView drugPriceTextView = (TextView) v.findViewById(R.id.drug_price_text_view);
            qtyTextView.setText("" + game.player.getNumDrug(drug));
            nameTextView.setText(drug);
            drugPriceTextView.setText("" + price);
            return v;
        }
    }

}
