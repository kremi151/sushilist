package lu.kremi151.sushilist.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Collections;
import java.util.List;

import lu.kremi151.sushilist.Callback;
import lu.kremi151.sushilist.R;
import lu.kremi151.sushilist.util.SushiEntry;
import lu.kremi151.sushilist.util.SushiList;

/**
 * Created by michm on 17.02.2018.
 */

public class SushiEntryAdapter extends RecyclerView.Adapter<SushiEntryAdapter.SushiEntryViewHolder>{

    private final SushiList list;
    private final LayoutInflater inflater;
    private final Callback<SushiList> listener;

    public SushiEntryAdapter(SushiList list, LayoutInflater inflater, Callback<SushiList> listener){
        this.list = list;
        this.inflater = inflater;
        this.listener = listener;
    }

    public SushiEntryAdapter(LayoutInflater inflater, Callback<SushiList> listener){
        this(new SushiList().markDirty(true), inflater, listener);
    }

    public SushiList getList(){
        return list;
    }

    public List<SushiEntry> getImmutableEntryList(){
        return Collections.unmodifiableList(list.getEntries());
    }

    public void addNewEntry(){
        list.getEntries().add(new SushiEntry());
        list.markDirty(true);
        notifyDataSetChanged();
    }

    @Override
    public SushiEntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.sushi_entry, parent, false);
        SushiEntryViewHolder holder = new SushiEntryViewHolder(view);
        holder.entryName.addTextChangedListener(new EntryNameWatcher(holder));
        holder.entryPieces.addTextChangedListener(new EntryPiecesWatcher(holder));
        holder.entryAmount.addTextChangedListener(new EntryAmountWatcher(holder));
        holder.entryPrice.addTextChangedListener(new EntryPriceWatcher(holder));
        return holder;
    }

    @Override
    public void onBindViewHolder(SushiEntryViewHolder holder, int position) {
        SushiEntry entry = list.getEntries().get(position);
        holder.referenced = entry;
        holder.entryName.setText(entry.getName());
        holder.entryPieces.setText(String.valueOf(entry.getPieces()));
        holder.entryAmount.setText(String.valueOf(entry.getAmount()));
        holder.entryPrice.setText(String.valueOf(entry.getPrice()));
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return list.getEntries().size();
    }

    public static class SushiEntryViewHolder extends RecyclerView.ViewHolder {
        private SushiEntry referenced;
        private final EditText entryName;
        private final EditText entryPieces;
        private final EditText entryAmount;
        private final EditText entryPrice;

        private SushiEntryViewHolder(View view){
            super(view);
            this.entryName = view.findViewById(R.id.entryName);
            this.entryPieces = view.findViewById(R.id.entryPieces);
            this.entryAmount = view.findViewById(R.id.entryAmount);
            this.entryPrice = view.findViewById(R.id.entryPrice);
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

        private final SushiEntryViewHolder state;

        private EntryNameWatcher(SushiEntryViewHolder state){
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

        private final SushiEntryViewHolder state;

        private EntryPiecesWatcher(SushiEntryViewHolder state){
            this.state = state;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(state.referenced != null){
                final String input = editable.toString();
                if(input.length() > 0){
                    try{
                        state.referenced.setPieces(Integer.parseInt(input));
                        listener.callback(list);
                    }catch(NumberFormatException e){
                        e.printStackTrace();
                    }
                }else{
                    state.referenced.setPieces(0);
                    listener.callback(list);
                }
            }
        }

    }

    private class EntryAmountWatcher extends ConvenientTextWatcher{

        private final SushiEntryViewHolder state;

        private EntryAmountWatcher(SushiEntryViewHolder state){
            this.state = state;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(state.referenced != null){
                final String input = editable.toString();
                if(input.length() > 0){
                    try{
                        state.referenced.setAmount(Integer.parseInt(input));
                        listener.callback(list);
                    }catch(NumberFormatException e){
                        e.printStackTrace();
                    }
                }else{
                    state.referenced.setAmount(0);
                    listener.callback(list);
                }
            }
        }

    }

    private class EntryPriceWatcher extends ConvenientTextWatcher{

        private final SushiEntryViewHolder state;

        private EntryPriceWatcher(SushiEntryViewHolder state){
            this.state = state;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(state.referenced != null){
                final String input = editable.toString();
                if(input.length() > 0){
                    try{
                        state.referenced.setPrice(Float.parseFloat(input));
                        listener.callback(list);
                    }catch(NumberFormatException e){
                        e.printStackTrace();
                    }
                }else{
                    state.referenced.setPrice(0.0f);
                    listener.callback(list);
                }
            }
        }

    }
}
