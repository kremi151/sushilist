package lu.kremi151.sushilist.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by michm on 25.02.2018.
 */

public class Restaurant {
    private final String name;
    private float rating = 0.0f;
    private final List<SushiCardEntry> sushis = new ArrayList<>();

    public Restaurant(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Restaurant addToCard(SushiCardEntry entry){
        sushis.add(entry);
        return this;
    }

    public List<SushiCardEntry> getSushis(){
        return Collections.unmodifiableList(sushis);
    }
}
