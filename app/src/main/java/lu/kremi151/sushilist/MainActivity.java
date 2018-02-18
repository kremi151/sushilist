package lu.kremi151.sushilist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import lu.kremi151.sushilist.util.SushiEntry;
import lu.kremi151.sushilist.util.SushiList;

public class MainActivity extends AppCompatActivity {

    private SushiEntryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new SushiEntryAdapter(getLayoutInflater(), sushiListener);
        ((ListView)findViewById(R.id.mainList)).setAdapter(adapter);
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

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
