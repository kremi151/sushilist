package lu.kremi151.sushilist.util;

/**
 * Created by michm on 17.02.2018.
 */

public class SushiEntry {
    private String name = "";
    private int pieces = 1, amount = 1;
    private float price = 0.0f;

    public SushiEntry(){}

    public SushiEntry(String name, int pieces, float price, int amount){
        this.name = name;
        this.pieces = pieces;
        this.price = price;
        this.amount = amount;
    }

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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }else if(obj instanceof SushiEntry){
            SushiEntry other = (SushiEntry) obj;
            return name.equals(other.name) && pieces == other.pieces && price == other.price && amount == other.amount;
        }else{
            return false;
        }
    }

    @Override
    public int hashCode(){
        return ((name.hashCode() * 31 + pieces) * 31 + (int)price) * 31 + amount;
    }
}
