package lu.kremi151.sushilist.util;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by michm on 18.02.2018.
 */

public class SushiList implements Iterable<SushiEntry>{
    private final List<SushiEntry> entries = new ArrayList<>();
    private String title = "unnamed";
    private String filename = null;
    private Calendar date;

    private boolean isDirty = false;

    public SushiList(){
        this.date = Calendar.getInstance();
    }

    public List<SushiEntry> getEntries(){
        return entries;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public boolean hasFilename(){
        return filename != null;
    }

    public String getFilename(){
        return filename;
    }

    public void setFilename(String filename){
        this.filename = filename;
    }

    public boolean isDirty(){
        return isDirty;
    }

    public void markDirty(boolean value){
        isDirty = value;
    }

    @NonNull
    @Override
    public Iterator<SushiEntry> iterator() {
        return entries.iterator();
    }
}
