package lu.kremi151.sushilist.util;

/**
 * Created by michm on 20.02.2018.
 */

public class Tuple<A, B> {

    public final A first;
    public final B second;

    public Tuple(A first, B second){
        this.first = first;
        this.second = second;
    }
}
