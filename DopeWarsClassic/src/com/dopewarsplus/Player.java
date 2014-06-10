package com.dopewarsplus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    
    private Map<String, Integer> drugs;
    private List<Item> items;
    private int money;
    private int numPockets;
    

    
    /**
     * New game constructor
     */
    public Player() {
        drugs = new HashMap<String, Integer>();
        items = new ArrayList<Item>();
        money = 200;
        numPockets = 50;
    }
    
    /**
     * Calculates the number of pockets currently occupied by drugs and pockets
     */
    public int getOccupiedPockets() {
        int result = 0;
        for (String drug : drugs.keySet())
            result += drugs.get(drug);
        for (Item i : items)
            result += i.size;
        return result;
    }
    
    /**
     * Tries to add given drug, returns false if player doesn't have enough room.
     * true if successful
     */
    public boolean addDrugs(String drug, int qty) {
        if (getOccupiedPockets() + qty > numPockets) {
            return false;
        } else {
            drugs.put(drug, qty);
            return true;
        }
    }
    
    /**
     * Attempts to remove given qty of drug from player. Returns true if successful,
     * false if given qty is not present in player
     */
    public boolean removeDrugs(String drug, int qty) {
        if (drugs.containsKey(drug)) {
            int currQty = drugs.get(drug);
            if (currQty == qty) {
                drugs.remove(drug);
            } else if (currQty > qty) {
                drugs.put(drug, qty);
            }
        }
        return false;
    }
    
    public int getNumDrug(String drug) {
        if (drugs.containsKey(drug))
            return drugs.get(drug);
        else
            return 0;
    }
    
    public int getMoney() {
        return money;
    }
    
    public int getNumPockets() {
        return numPockets;
    }
    
    public void addMoney(int money) {
        this.money += money;
    }
    
    public void removeMoney(int money) {
        this.money -= money;
    }
    
    public void addPockets(int pockets) {
        numPockets += pockets;
    }
    
}
