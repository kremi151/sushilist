package lu.kremi151.sushilist.util;

/**
 * Created by michm on 17.02.2018.
 */

public class SushiEntry {
    private String name = "";
    private int pieces = 1, amount = 1;
    private float price = 0.0f;

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
}
