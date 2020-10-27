package com.example.scansmart.ui.history;

import android.content.Context;
import android.media.Image;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scansmart.R;
import com.example.scansmart.ui.cart.CartAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.zip.Inflater;

public class HistoryList extends BaseAdapter {
    Context context;
    ArrayList<String> dateTime;
    ArrayList<String> store;
    ArrayList<Integer> item;
    ArrayList<Integer> historyId;
    LayoutInflater inflter;
    EventListener listener;

    public interface EventListener {
        void onEvent(int historyId);
    }

    public HistoryList(Context applicationContext, ArrayList<String> dateTime, ArrayList<String> store, ArrayList<Integer> item, ArrayList<Integer> historyId, EventListener listener) {
        this.context = context;
        this.dateTime = dateTime;
        this.store = store;
        this.item = item;
        this.historyId = historyId;
        this.listener = listener;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return dateTime.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View v, ViewGroup vg) {
        final ViewHolder holder;

        if (v == null) {
            v = inflter.inflate(R.layout.historylist, null);
            holder = new ViewHolder();
            holder.dateTime_tv = (TextView) v.findViewById(R.id.dateTime);
            holder.store_tv = (TextView) v.findViewById(R.id.store);
            holder.item_tv  = (TextView) v.findViewById(R.id.items);
            holder.next= (ImageButton) v.findViewById(R.id.next_history);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        Log.v("dateTime1", String.valueOf(dateTime.get(position)));
        holder.dateTime_tv.setText(dateTime.get(position));
        holder.store_tv.setText(store.get(position));
        holder.item_tv.setText(Integer.toString(item.get(position)));
        holder.historyId = historyId.get(position);

        //Handle buttons and add onClickListeners
        holder.next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                Log.wtf("history id", String.valueOf(holder.historyId));
                listener.onEvent(holder.historyId);
            }

        });

        return v;
    }
    static class ViewHolder {
        TextView dateTime_tv;
        TextView store_tv;
        TextView item_tv;
        ImageButton next;
        int historyId;
    }

}







