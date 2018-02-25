package lu.kremi151.sushilist.util;

import android.support.annotation.NonNull;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

import lu.kremi151.sushilist.Callback;
import lu.kremi151.sushilist.serialization.SushiListParser;
import lu.kremi151.sushilist.task.SushiListLoader;

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

    public SushiList markDirty(boolean value){
        isDirty = value;
        return this;
    }

    @NonNull
    @Override
    public Iterator<SushiEntry> iterator() {
        return entries.iterator();
    }

    public static class Reference{
        private final String title;
        private final Calendar date;
        private final File file;

        public Reference(String title, Calendar date, File file){
            this.title = title;
            this.date = date;
            this.file = file;
        }

        public String getTitle(){
            return title;
        }

        public Calendar getDate(){
            return date;
        }

        public File getFile(){
            return file;
        }

        public SushiList resolve() throws IOException {
            FileInputStream inputStream = null;
            try{
                inputStream = new FileInputStream(file);
                SushiList list = SushiListParser.parse(inputStream);
                list.setFilename(file.getName().substring(0, file.getName().lastIndexOf(".")));
                return list;
            }catch(IOException e){
                SushiListParser.invalidateReferencesCache();
                throw e;
            } catch (SAXException e) {
                SushiListParser.invalidateReferencesCache();
                throw new IOException(e);
            } finally{
                if(inputStream != null){
                    inputStream.close();
                }
            }
        }

        public void resolveAsync(Callback<SushiList> callback){
            new SushiListLoader(callback).execute(this);
        }
    }
}
