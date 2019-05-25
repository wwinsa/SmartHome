package com.example.weixi.smarthome;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class MyListAdapter extends ArrayList {

    final ArrayList<Integer> array = new ArrayList<Integer>();

    private final int resourceId;

    public MyListAdapter(Context context, int textViewResourceId, List<Integer> objects){
//        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;

    }


}
