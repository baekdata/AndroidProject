package com.sgenclub.allnote.overlay;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.sgenclub.allnote.R;

public class PaintAdapter extends BaseAdapter {
   Context mContext;
   ArrayList<Integer> items = new ArrayList<Integer>();
   int width, height;
   
   public void del(int resId){
      items.remove((Integer)resId);
      notifyDataSetChanged();
   }
   
   public PaintAdapter(Context context) {
      mContext = context;
      width = context.getResources().getDimensionPixelSize(R.dimen.image_width);
      height = context.getResources().getDimensionPixelSize(R.dimen.image_height);
   }
   
   public void add(int resId) {
      items.add((Integer)resId);
      notifyDataSetChanged();
   }
   
   @Override
   public int getCount() {
      return items.size();
   }

   @Override
   public Object getItem(int position) {
      return items.get(position % items.size());
   }

   @Override
   public long getItemId(int position) {
      return position;
   }

   @Override
   public View getView(int position, View convertView, ViewGroup parent) {
      ImageView iv;
      if (convertView == null) {
         iv = new ImageView(mContext);
//         Gallery.LayoutParams lp = new Gallery.LayoutParams(300, 300); // gallery사진 크기
         Gallery.LayoutParams lp = new Gallery.LayoutParams(350, 550);
         iv.setLayoutParams(lp);
         iv.setPadding(-10, 0, 0, 0);
//         iv.setScaleType(ScaleType.FIT_XY);
      } else {
         iv = (ImageView)convertView;
      }
      iv.setImageResource(items.get(position % items.size()));
      return iv;
   }
}