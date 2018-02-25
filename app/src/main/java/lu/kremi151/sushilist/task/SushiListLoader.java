package lu.kremi151.sushilist.task;

import android.os.AsyncTask;

import java.io.IOException;

import lu.kremi151.sushilist.Callback;
import lu.kremi151.sushilist.util.SushiList;

/**
 * Created by michm on 18.02.2018.
 */

public class SushiListLoader extends AsyncTask<SushiList.Reference, Void, SushiList> {

    private final Callback<SushiList> callback;

    public SushiListLoader(Callback<SushiList> callback){
        this.callback = callback;
    }

    @Override
    protected SushiList doInBackground(SushiList.Reference... references) {
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
