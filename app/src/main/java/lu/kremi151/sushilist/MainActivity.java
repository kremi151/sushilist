package lu.kremi151.sushilist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import lu.kremi151.sushilist.adapter.SushiEntryAdapter;
import lu.kremi151.sushilist.serialization.SushiEntryParser;
import lu.kremi151.sushilist.util.DialogHelper;
import lu.kremi151.sushilist.util.SushiEntry;
import lu.kremi151.sushilist.util.SushiList;
import lu.kremi151.sushilist.util.SushiListReference;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private SushiEntryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.mainList);
        switchList(new SushiList());
    }

    private void switchList(SushiList list){
        adapter = new SushiEntryAdapter(list, getLayoutInflater(), sushiListener);
        listView.setAdapter(adapter);
        //TODO: Title
    }

    private final Callback<SushiList> sushiListener = new Callback<SushiList>() {
        @Override
        public void callback(SushiList obj) {
            int pieces = 0;
            float price = 0.0f;
            List<SushiEntry> list = obj.getEntries();
            for(SushiEntry entry : list){
                pieces += entry.getPieces() * entry.getAmount();
                price += entry.getPrice() * entry.getAmount();
            }
            ((TextView)findViewById(R.id.globalAmountTypes)).setText("" + list.size());
            ((TextView)findViewById(R.id.globalTotalPieces)).setText("" + pieces);
            ((TextView)findViewById(R.id.globalTotalPrice)).setText("" + price + "€");
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuItemAdd:
                adapter.addNewEntry();
                return true;
            case R.id.menuItemSaveList:

                return true;
            case R.id.menuItemLoadList:
                //TODO: Check for unsaved changes
                try {
                    DialogHelper.buildLoadDialog(
                            this,
                            SushiEntryParser.getSavedReferences(this),
                            new Callback<SushiListReference>() {
                                @Override
                                public void callback(SushiListReference obj) {
                                    try {
                                        switchList(obj.resolve());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        DialogHelper.buildErrorDialog(MainActivity.this, e).show();
                                    }
                                }
                            }).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    DialogHelper.buildErrorDialog(this, e).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
