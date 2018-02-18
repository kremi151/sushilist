package lu.kremi151.sushilist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import lu.kremi151.sushilist.adapter.SushiEntryAdapter;
import lu.kremi151.sushilist.serialization.SushiListParser;
import lu.kremi151.sushilist.util.DialogHelper;
import lu.kremi151.sushilist.util.SushiEntry;
import lu.kremi151.sushilist.util.SushiList;
import lu.kremi151.sushilist.util.SushiListReference;
import lu.kremi151.sushilist.util.SwipeToDeleteCallback;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTypes, textViewPieces, textViewPrice;
    private RecyclerView recyclerView;
    private SushiEntryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Locale.setDefault(getResources().getConfiguration().locale);

        textViewTypes = findViewById(R.id.globalAmountTypes);
        textViewPieces = findViewById(R.id.globalTotalPieces);
        textViewPrice = findViewById(R.id.globalTotalPrice);

        recyclerView = findViewById(R.id.mainList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(getResources()) {

            @Override
            protected void onRemove(int position) {
                adapter.removeEntry(position);
            }

        });

        itemTouchHelper.attachToRecyclerView(recyclerView);

        switchList(new SushiList());
    }

    private void switchList(SushiList list){
        adapter = new SushiEntryAdapter(list, getLayoutInflater(), sushiListener);
        recyclerView.setAdapter(adapter);
        updateOrderInformation(list);
    }

    private void updateTitle(){
        if(adapter.getList().isDirty()){
            setTitle("* " + adapter.getList().getTitle());
        }else{
            setTitle(adapter.getList().getTitle());
        }
    }

    @SuppressLint("DefaultLocale")
    private void updateOrderInformation(SushiList list){
        int pieces = 0;
        float price = 0.0f;
        for(SushiEntry entry : list){
            pieces += entry.getPieces() * entry.getAmount();
            price += entry.getPrice() * entry.getAmount();
        }
        textViewTypes.setText(String.valueOf(list.getEntries().size()));
        textViewPieces.setText(String.valueOf(pieces));
        textViewPrice.setText(String.format("%.2f€", price));
        updateTitle();
    }

    private final Callback<SushiList> sushiListener = new Callback<SushiList>() {
        @Override
        public void callback(SushiList obj) {//TODO: Track value changements for saving
            updateOrderInformation(obj);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
        return true;
    }

    private void saveList(boolean newFile) throws IOException{
        //TODO: Check for unsaved changes
        if(newFile || !adapter.getList().hasFilename()){
            DialogHelper.buildInputDialog(this, R.string.dialogTitleSaveAs, new Callback<String>() {
                @Override
                public void callback(String obj) {
                    if(obj != null && obj.length() > 0){
                        adapter.getList().setTitle(obj);
                        //TODO: Update displayed title
                        adapter.getList().setFilename(String.valueOf(System.currentTimeMillis()));
                        try {
                            saveList(false);
                        } catch (IOException e) {
                            e.printStackTrace();
                            DialogHelper.buildErrorDialog(MainActivity.this, e).show();
                        }
                    }
                }
            }).show();
            return;
        }
        adapter.getList().setDate(Calendar.getInstance());
        File outputFile = new File(getFilesDir(), adapter.getList().getFilename() + ".xml");
        FileOutputStream outputStream = null;
        adapter.getList().markDirty(false);
        updateTitle();
        try{
            outputStream = new FileOutputStream(outputFile);
            SushiListParser.write(outputStream, adapter.getList());
        } catch (IOException e) {
            throw e;
        } catch (SAXException e) {
            throw new IOException(e);
        } finally{
            if(outputStream != null){
                outputStream.close();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuItemAdd:
                adapter.addNewEntry();
                updateTitle();
                return true;
            case R.id.menuItemSaveAs:
            case R.id.menuItemSaveList:
                try {
                    saveList(item.getItemId() == R.id.menuItemSaveAs);
                } catch (IOException e) {
                    e.printStackTrace();
                    DialogHelper.buildErrorDialog(this, e).show();
                }
                return true;
            case R.id.menuItemLoadList:
                //TODO: Check for unsaved changes
                try {
                    DialogHelper.buildLoadDialog(
                            this,
                            SushiListParser.getSavedReferences(this),
                            new Callback<SushiListReference>() {
                                @Override
                                public void callback(SushiListReference obj) {
                                    final AlertDialog waitDialog = DialogHelper.buildNonDismissableWaitDialog(MainActivity.this);
                                    waitDialog.show();
                                    obj.resolveAsync(new Callback<SushiList>() {
                                        @Override
                                        public void callback(SushiList obj) {
                                            switchList(obj);
                                            waitDialog.dismiss();
                                        }
                                    });
                                }
                            }).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    DialogHelper.buildErrorDialog(this, e).show();
                }
                return true;
            case R.id.menuItemNewList:
                //TODO: Check for unsaved changes
                switchList(new SushiList().markDirty(true));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
