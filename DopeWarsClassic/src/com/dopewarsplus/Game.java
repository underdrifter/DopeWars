package com.dopewarsplus;

/**
 * A game stores a player and has a given duration.
 */

public class Game {
    
    public static Game currentGame;
    
    public final Player player;
    public final int duration;
    private int day;
    private int debt;
    private int bank;
    
    /**
     * Creates a new game
     */
    private Game(int duration) {
        this.player = new Player();
        this.duration = duration;
        day = 0;
        debt = 1000;
        bank = 0;
    }
    
    public int getBankCash() {
        return bank;
    }
    
    /**
     * Adds a day to the game, increments debt and bank value
     */
    public void addDay() {
        day++;
        debt = (int) ( debt * 1.2 );
        bank = (int) ( bank * 1.1 );
    }
    
    /**
     * Removes given money from bank and adds it to player
     */
    public void removeBankMoney(int money) {
        bank -= money;
        player.addMoney(money);
    }
    
    /**
     * Adds given money to bank, subtracts it from player's cash
     */
    public void addBankCash(int money) {
        player.removeMoney(money);
        bank += money;
    }
    
    public static void startNewGame(int duration) {
        currentGame = new Game(duration);
    }
    
    public static void resumeGame(Game g) {
        currentGame = g;
    }
    
    public int getDay() {
        return day;
    }

    public int getDebt() {
        return debt;
    }
}
