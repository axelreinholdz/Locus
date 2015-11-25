package com.locus.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Melker on 2015-11-07.
 */
public class StringArrayAdapter extends BaseAdapter {

    String[] names;
    Context ctxt;
    LayoutInflater myInflater;

    public  StringArrayAdapter(String[] arr, Context c){
        names = arr;
        ctxt = c;
        myInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return names.length;
    }

    @Override
    public Object getItem(int arg0){
        return names;
    }

    @Override
    public  long getItemId(int arg0){
        return 0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2){
        if (arg1 == null)
            arg1 = myInflater.inflate(android.R.layout.activity_list_item, arg2, false);
        TextView name = (TextView)arg1.findViewById(android.R.id.text1);
        name.setText(names[arg0]);
        return arg1;
    }
}
