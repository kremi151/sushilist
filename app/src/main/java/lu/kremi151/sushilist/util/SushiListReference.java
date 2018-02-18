package lu.kremi151.sushilist.util;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;

import lu.kremi151.sushilist.Callback;
import lu.kremi151.sushilist.serialization.SushiListParser;
import lu.kremi151.sushilist.task.SushiListLoader;

/**
 * Created by michm on 18.02.2018.
 */

public class SushiListReference {
    private final String title;
    private final Calendar date;
    private final File file;

    public SushiListReference(String title, Calendar date, File file){
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

    public SushiList resolve() throws IOException{
        FileInputStream inputStream = null;
        try{
            inputStream = new FileInputStream(file);
            SushiList list = SushiListParser.parse(inputStream);
            list.setFilename(file.getName().substring(0, file.getName().lastIndexOf(".")));
            return list;
        }catch(IOException e){
            throw e;
        } catch (SAXException e) {
            throw new IOException(e);
        } finally{
            if(inputStream != null){
                inputStream.close();
            }
        }
    }

    public void resolveAsync(Callback<SushiList> callback){
        new SushiListLoader(callback).execute(file);
    }
}
