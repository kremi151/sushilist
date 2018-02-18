package lu.kremi151.sushilist.task;

import android.os.AsyncTask;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import lu.kremi151.sushilist.Callback;
import lu.kremi151.sushilist.serialization.SushiListParser;
import lu.kremi151.sushilist.util.SushiList;

/**
 * Created by michm on 18.02.2018.
 */

public class SushiListLoader extends AsyncTask<File, Void, SushiList> {

    private final Callback<SushiList> callback;

    public SushiListLoader(Callback<SushiList> callback){
        this.callback = callback;
    }

    @Override
    protected SushiList doInBackground(File... files) {
        if(files.length > 0){
            final File file = files[0];
            FileInputStream inputStream = null;
            try{
                inputStream = new FileInputStream(file);
                SushiList list = SushiListParser.parse(inputStream);
                list.setFilename(file.getName().substring(0, file.getName().lastIndexOf(".")));
                return list;
            } catch(IOException | SAXException e){
                throw new RuntimeException(e);
            } finally{
                if(inputStream != null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            throw new RuntimeException("No file provided to load from");
        }
    }

    @Override
    protected void onPostExecute(SushiList result){
        callback.callback(result);
    }

}
