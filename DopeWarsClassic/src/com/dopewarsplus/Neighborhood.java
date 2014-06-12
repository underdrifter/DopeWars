package com.dopewarsplus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.dopewarsplus.neighborhoods.BusinessDistrict;
import com.dopewarsplus.neighborhoods.ChinaTown;
import com.dopewarsplus.neighborhoods.Downtown;
import com.dopewarsplus.neighborhoods.Ghetto;
import com.dopewarsplus.neighborhoods.Heights;
import com.dopewarsplus.neighborhoods.Suburbs;
import com.example.dopewarsplus.R;

/**
 * This is what the user sees as they play the game.
 */
public abstract class Neighborhood extends Activity {
    
    public String[] NEIGHBORHOOD_NAMES = {"Business District", "China Town", "Downtown", "Ghetto", "Heights", "Suburbs"};
    
    protected Game game;
    protected String name;
    // lower priceFactor is lower price, should be between 3 and 8, 5 is avg price
    // i.e. drugs are cheap in the ghetto but expensive in the heights
    protected int priceFactor;
    // lower crimeFactor is less crime, higher is more, avg 5
    protected int crimeFactor;
    // both of these, the index in the listview corresponds to the drugs name and price
    protected String[] drugs;
    protected int[] prices;
    protected String currDrug;
    protected int currPrice;
    protected boolean buying; // we are buying if true, selling if false
    protected TextView cashView;
    protected TextView debtView;
    protected TextView bankView;
    protected LinearLayout transactionBar;
    protected Button switchButton;
    protected TextView transactionText;
    protected Button transactionCompleteButton;
    protected SeekBar transactionSeekBar;
    
    // methods
    protected abstract void populateFields();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_neighborhood);
        
        // populate fields
        populateFields();
        game = Game.currentGame;
        prices = new int[drugs.length];
        cashView = (TextView) findViewById(R.id.cash_text_view);
        debtView = (TextView) findViewById(R.id.debt_text_view);
        bankView = (TextView) findViewById(R.id.bank_text_view);
        transactionBar = (LinearLayout) findViewById(R.id.transaction_bar);
        switchButton = (Button) findViewById(R.id.switch_transaction_mode_button);
        transactionText = (TextView) findViewById(R.id.transaction_text_view);
        transactionCompleteButton = (Button) findViewById(R.id.finish_transaction_button);
        transactionSeekBar = (SeekBar) findViewById(R.id.transaction_seek_bar);
        
        game.addDay();
        TextView townNameView = (TextView) findViewById(R.id.town_name_text_view);
        townNameView.setText(this.name);
        TextView daysLeftView = (TextView) findViewById(R.id.days_left_text_view);
        daysLeftView.setText("Day " + game.getDay() + "/" + game.duration);
        updateMoney();
        populateDrugs(drugs);
        transactionSeekBar.setOnSeekBarChangeListener(new TransactionSeekBarListener());
    }
    
    public void initiateMove(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Destination")
               .setItems(NEIGHBORHOOD_NAMES, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                       String dest = "";
                       Intent intent = new Intent(Neighborhood.this, Ghetto.class);
                       if (dest.equals("Business District")) {
                           intent = new Intent(Neighborhood.this, BusinessDistrict.class);
                       } else if (dest.equals("China Town")) {
                           intent = new Intent(Neighborhood.this, ChinaTown.class);
                       } else if (dest.equals("Downtown")) {
                           intent = new Intent(Neighborhood.this, Downtown.class);
                       } else if (dest.equals("Heights")) {
                           intent = new Intent(Neighborhood.this, Heights.class);
                       } else if (dest.equals("Suburbs")) {
                           intent = new Intent(Neighborhood.this, Suburbs.class);
                       }
                       startActivity(intent);
                       Neighborhood.this.finish();
                   }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    
    public void initiateMenu(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Neighborhood.this);
 
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
                    Neighborhood.this.finish();
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
    

    
    protected void initiateBuy() {
        buying = true;
        int numCanBuy = Math.min(game.player.getMoney() / currPrice, game.player.getNumEmptyPockets());
        switchButton.setText("Sell");
        transactionCompleteButton.setText("Buy");
        transactionSeekBar.setMax(numCanBuy);
        transactionSeekBar.setProgress(numCanBuy);
        transactionBar.setVisibility(View.VISIBLE);
    }
    
    protected void initiateSell() {
        buying = false;
        int numOwned = game.player.getNumDrug(currDrug);
        switchButton.setText("Buy");
        transactionCompleteButton.setText("Sell");
        transactionSeekBar.setMax(numOwned);
        transactionSeekBar.setProgress(numOwned);
        transactionBar.setVisibility(View.VISIBLE);
    }
    
    public void transactionComplete(View view) {
        int numDrug = transactionSeekBar.getProgress();
        int sum = numDrug * currPrice;
        if (numDrug > 0) {
            if (buying) {
                game.player.removeMoney(sum);
                game.player.addDrugs(currDrug, numDrug);
                showToast("Purchased " + numDrug + " " + currDrug + " for " + formatMoney(sum));
            } else { // selling..
                game.player.removeDrugs(currDrug, numDrug);
                game.player.addMoney(sum);
                showToast("Sold " + numDrug + " " + currDrug + " for " + formatMoney(sum));
            }
            transactionBar.setVisibility(View.GONE);
            updateMoney();
        }
    }
    
    public void initiateSwitch(View view) {
        if (buying) { // currently buying, attempting to switch to selling
            int numOwned = game.player.getNumDrug(currDrug);
            if (numOwned == 0) {
                showToast("You don't have any");
            } else {
                initiateSell();
            }
        } else { // we are currently selling, attempting to switch to buying
            if (game.player.getNumEmptyPockets() == 0) {
                showToast("Your pockets are full");
            } else if (game.player.getMoney() < currPrice) {
                showToast("Too expensive");
            } else { // initiate buy
                initiateBuy();
            }
        }
    }
    
    /**
     * Populates listview with drugs and sets click listener
     */
    protected void populateDrugs(final String[] drugs) {
        // Get the reference of ListView
        ListView drugListView = (ListView)findViewById(R.id.drugs_list_view);
        // Create The Adapter with passing Array as 3rd parameter
        ArrayAdapter<String> arrayAdapter =      
               new DrugAdapter(this, android.R.layout.simple_list_item_1, drugs);
        // Set The Adapter
        drugListView.setAdapter(arrayAdapter);
        drugListView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View v, int pos, long arg3) {
                currDrug = drugs[pos];
                currPrice = prices[pos];
                int numOwned = game.player.getNumDrug(currDrug);
                if (numOwned > 0) { // do we have some? initiate sell menu
                    initiateSell();
                } else if (game.player.getNumEmptyPockets() == 0) {
                    transactionBar.setVisibility(View.GONE);
                    showToast("Your pockets are full");
                } else if (game.player.getMoney() < currPrice) {
                    transactionBar.setVisibility(View.GONE);
                    showToast("Too expensive");
                } else { // initiate buy
                    initiateBuy();
                }

                
            }
        });
    }
    
    /**
     * Updates money display
     */
    protected void updateMoney() {
        cashView.setText("Cash: " + formatMoney(game.player.getMoney()));
        debtView.setText("Debt: " + formatMoney(game.getDebt()));
        bankView.setText("Bank: " + formatMoney(game.getBankCash()));
    }
    
    protected void showToast(String str) {
        Toast toast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
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
    
    /**
     * Listener for transaction seek bar
     */
    protected class TransactionSeekBarListener implements OnSeekBarChangeListener {
        @Override       
        public void onStopTrackingTouch(SeekBar seekBar) {
            
        }       
    
        @Override       
        public void onStartTrackingTouch(SeekBar seekBar) {
            
        }       
    
        @Override       
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {     
            transactionText.setText("" + progress);
        }
    }
    
    /**
     * Adapter for populating listview
     */
    protected class DrugAdapter extends ArrayAdapter<String> {

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
                price = Drugs.getPrice(drug, priceFactor);
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
