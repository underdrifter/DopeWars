package com.dopewarsplus;

/**
 * Items are purchasable things a player can buy, such as guns, bullet proof jackets etc
 */

public abstract class Item {
    
    public final String name;
    public final int value;
    public final int size; // number of pockets the item occupies, may be 0
    
    public Item(String name, int value, int size) {
        this.name = name;
        this.value = value;
        this.size = size;
    }
    
}
