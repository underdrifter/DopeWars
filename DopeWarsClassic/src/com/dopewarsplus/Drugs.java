package com.dopewarsplus;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Collection of drugs, each has a name and an average price
 */

public abstract class Drugs {
    
    private static Random r = new Random();

    public static final String WEED = "Weed";
    public static final String COKE = "Cocaine";
    public static final String HEROIN = "Heroin";
    public static final String SPEED = "Speed";
    public static final String METH = "Meth";
    public static final String MOLLY = "Molly";
    public static final String PCP = "PCP";
    public static final String LSD = "LSD";
    public static final String SHROOMS = "Shrooms";
    public static final String OXY = "Oxycottin";
    public static final String ADDERALL = "Adderall";
    
    public static final Map<String, Integer> drugs = getDrugs();
    public static final Set<String> drugNames = drugs.keySet();
    
    private static Map<String, Integer> getDrugs() {
        HashMap<String, Integer> drugs = new HashMap<String, Integer>();
        drugs.put(COKE, 15000);
        drugs.put(OXY, 9000);
        drugs.put(HEROIN, 7000);
        drugs.put(LSD, 3000);
        drugs.put(MOLLY, 2000);
        drugs.put(PCP, 1400);
        drugs.put(ADDERALL, 800);
        drugs.put(SHROOMS, 250);
        drugs.put(SPEED, 200);
        drugs.put(METH, 100);
        drugs.put(WEED, 50);
        return drugs;
    }
    
    /**
     * Gets a price for the given drug, modified by the given factor.
     * Prices are randomized +- 50% their normal price, then modified by given factor
     */
    public static int getPrice(String drugName, int factor) {
        int price = drugs.get(drugName);
        price = ( price * ( 25 + r.nextInt(50) ) ) / 50; // randomize
        price = ( price * factor ) / 5; // factorize
        return price;
    }

}
