package lu.kremi151.sushilist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import lu.kremi151.sushilist.R;
import lu.kremi151.sushilist.util.SushiList;

/**
 * Created by michm on 18.02.2018.
 */

public class SushiReferenceAdapter extends BaseAdapter {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyy");;

    private final LayoutInflater inflater;
    private final List<SushiList.Reference> refs;

    public SushiReferenceAdapter(LayoutInflater inflater, List<SushiList.Reference> refs){
        this.inflater = inflater;
        this.refs = refs;
    }

    @Override
    public int getCount() {
        return refs.size();
    }

    @Override
    public Object getItem(int i) {
        return refs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = inflater.inflate(R.layout.listitem_sushi_list_reference, viewGroup, false);
            holder = new ViewHolder(
                    (TextView)view.findViewById(R.id.titleView),
                    (TextView)view.findViewById(R.id.dateView)
            );
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        SushiList.Reference ref = refs.get(i);
        holder.title.setText(ref.getTitle());
        holder.date.setText(DATE_FORMAT.format(ref.getDate().getTime()));
        return view;
    }

    private static class ViewHolder{
        private final TextView title, date;

        private ViewHolder(TextView title, TextView date){
            this.title = title;
            this.date = date;
        }
    }
}
