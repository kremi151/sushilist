package lu.kremi151.sushilist.data;

import lu.kremi151.sushilist.util.SushiEntry;

/**
 * Created by michm on 25.02.2018.
 */

public class SushiCardEntry {
    private String name = "";
    private int pieces = 1;
    private float price = 0.0f;

    public SushiCardEntry(String name, int pieces, float price){
        this.name = name;
        this.pieces = pieces;
        this.price = price;
    }

    public SushiCardEntry(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public SushiEntry createChoice(int amount){
        return new SushiEntry(name, pieces, price, amount);
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }else if(obj instanceof SushiCardEntry){
            SushiCardEntry other = (SushiCardEntry) obj;
            return name.equals(other.name) && pieces == other.pieces && price == other.price;
        }else{
            return false;
        }
    }

    @Override
    public int hashCode(){
        return (name.hashCode() * 31 + pieces) * 31 + (int)price;
    }
}
