package lu.kremi151.sushilist.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lu.kremi151.sushilist.Callback;
import lu.kremi151.sushilist.R;
import lu.kremi151.sushilist.util.SushiEntry;
import lu.kremi151.sushilist.util.SushiList;

/**
 * Created by michm on 17.02.2018.
 */

public class SushiEntryAdapter extends BaseAdapter{

    private final SushiList list;
    private final LayoutInflater inflater;
    private final Callback<SushiList> listener;

    public SushiEntryAdapter(SushiList list, LayoutInflater inflater, Callback<SushiList> listener){
        this.list = list;
        this.inflater = inflater;
        this.listener = listener;
    }

    public SushiEntryAdapter(LayoutInflater inflater, Callback<SushiList> listener){
        this(new SushiList(), inflater, listener);
    }

    public List<SushiEntry> getImmutableEntryList(){
        return Collections.unmodifiableList(list.getEntries());
    }

    public void addNewEntry(){
        list.getEntries().add(new SushiEntry());
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.getEntries().size();
    }

    @Override
    public Object getItem(int i) {
        return list.getEntries().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        EntryState holder = null;
        if(view == null){
            view = inflater.inflate(R.layout.sushi_entry, viewGroup, false);
            holder = new EntryState(
                    (EditText)view.findViewById(R.id.entryName),
                    (EditText)view.findViewById(R.id.entryPieces),
                    (EditText)view.findViewById(R.id.entryAmount),
                    (EditText)view.findViewById(R.id.entryPrice)
            );
            holder.entryName.addTextChangedListener(new EntryNameWatcher(holder));
            holder.entryPieces.addTextChangedListener(new EntryPiecesWatcher(holder));
            holder.entryAmount.addTextChangedListener(new EntryAmountWatcher(holder));
            holder.entryPrice.addTextChangedListener(new EntryPriceWatcher(holder));
            view.setTag(holder);
        }else{
            holder = (EntryState) view.getTag();
        }
        SushiEntry entry = list.getEntries().get(i);
        holder.referenced = entry;
        holder.entryName.setText(entry.getName());
        holder.entryPieces.setText(""+entry.getPieces());
        holder.entryAmount.setText(""+entry.getAmount());
        holder.entryPrice.setText(""+entry.getPrice());

        return view;
    }

    private static class EntryState {
        private SushiEntry referenced;
        private final EditText entryName;
        private final EditText entryPieces;
        private final EditText entryAmount;
        private final EditText entryPrice;

        private EntryState(EditText entryName, EditText entryPieces, EditText entryAmount, EditText entryPrice){
            this.entryName = entryName;
            this.entryPieces = entryPieces;
            this.entryAmount = entryAmount;
            this.entryPrice = entryPrice;
        }
    }

    private static class ConvenientTextWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void afterTextChanged(Editable editable) {}
    }

    private class EntryNameWatcher extends ConvenientTextWatcher{

        private final EntryState state;

        private EntryNameWatcher(EntryState state){
            this.state = state;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(state.referenced != null){
                state.referenced.setName(editable.toString());
                listener.callback(list);
            }
        }

    }

    private class EntryPiecesWatcher extends ConvenientTextWatcher{

        private final EntryState state;

        private EntryPiecesWatcher(EntryState state){
            this.state = state;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(state.referenced != null){
                try{
                    state.referenced.setPieces(Integer.parseInt(editable.toString()));
                    listener.callback(list);
                }catch(NumberFormatException e){
                    e.printStackTrace();
                }
            }
        }

    }

    private class EntryAmountWatcher extends ConvenientTextWatcher{

        private final EntryState state;

        private EntryAmountWatcher(EntryState state){
            this.state = state;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(state.referenced != null){
                try{
                    state.referenced.setAmount(Integer.parseInt(editable.toString()));
                    listener.callback(list);
                }catch(NumberFormatException e){
                    e.printStackTrace();
                }
            }
        }

    }

    private class EntryPriceWatcher extends ConvenientTextWatcher{

        private final EntryState state;

        private EntryPriceWatcher(EntryState state){
            this.state = state;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(state.referenced != null){
                try{
                    state.referenced.setPrice(Float.parseFloat(editable.toString()));
                    listener.callback(list);
                }catch(NumberFormatException e){
                    e.printStackTrace();
                }
            }
        }

    }
}
