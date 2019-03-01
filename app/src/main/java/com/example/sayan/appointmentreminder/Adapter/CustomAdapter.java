package com.example.sayan.appointmentreminder.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sayan.appointmentreminder.R;

/**
 * Created by 1605476 and 23-May-18
 **/
public class CustomAdapter extends BaseAdapter {

    int []images;
    String[] names;
    Context context;
    LayoutInflater inflater;

  public CustomAdapter(Context context,int []images, String []names)
  {
      this.context=context;
      this.images=images;
      this.names=names;
      inflater=LayoutInflater.from(context);
  }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return names[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view=inflater.inflate(R.layout.spinner_item,null);
        ImageView imageView=view.findViewById(R.id.imageView);
        TextView txtView=view.findViewById(R.id.textView);
        imageView.setImageResource(images[position]);
        txtView.setText(names[position]);
        return view;
    }
}
