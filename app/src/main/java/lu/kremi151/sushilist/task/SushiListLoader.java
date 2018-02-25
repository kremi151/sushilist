package lu.kremi151.sushilist.task;

import android.os.AsyncTask;

import java.io.IOException;

import lu.kremi151.sushilist.Callback;
import lu.kremi151.sushilist.serialization.SushiListParser;
import lu.kremi151.sushilist.util.SushiList;
import lu.kremi151.sushilist.util.SushiListReference;

/**
 * Created by michm on 18.02.2018.
 */

public class SushiListLoader extends AsyncTask<SushiListReference, Void, SushiList> {

    private final Callback<SushiList> callback;

    public SushiListLoader(Callback<SushiList> callback){
        this.callback = callback;
    }

    @Override
    protected SushiList doInBackground(SushiListReference... references) {
        if(references.length > 0){
            try{
                return references[0].resolve();
            } catch(IOException e){
                throw new RuntimeException(e);
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
