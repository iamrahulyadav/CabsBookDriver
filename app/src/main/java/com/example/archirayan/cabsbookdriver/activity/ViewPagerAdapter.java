package com.example.archirayan.cabsbookdriver.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.activity.DriverShareMytrripcontactDetail;

/**
 * Created by archirayan on 7/12/17.
 */

public class ViewPagerAdapter extends PagerAdapter {
    public String[] str_txt_title = {"peace of mind", "You're in control"};
    public String[] str_txt_content = {"Your friends and family can easily see trip status and where you are on a map", "Start and stop sharing at any time. Share only with the people you choose."};
    public String[] str_txt_buttonstatus = {"Next", "Setup"};
    public ImageView imageView;
    public TextView txt_title;
    public TextView txt_nextview;
    public ViewPager vp;
    private TextView txt_content;
    private Context context;
    private LayoutInflater layoutInflater;
    private Integer[] images = {R.drawable.images, R.drawable.images};

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position)
    {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        txt_title = view.findViewById(R.id.txt_title);
        txt_content = view.findViewById(R.id.txt_content);
        txt_nextview = view.findViewById(R.id.txt_nextview);
        imageView.setImageResource(images[position]);
        txt_title.setText(str_txt_title[position]);
        txt_content.setText(str_txt_content[position]);
        txt_nextview.setText(str_txt_buttonstatus[position]);
        vp = (ViewPager) container;
        vp.addView(view, 0);

        txt_nextview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (str_txt_buttonstatus[position].equalsIgnoreCase("Next"))
                {
                    Toast.makeText(context, "Next", Toast.LENGTH_SHORT).show();
                } else if (str_txt_buttonstatus[position].equalsIgnoreCase("Setup"))
                {
                    ((Activity) context).finish();
                    context.startActivity(new Intent(context, DriverShareMytrripcontactDetail.class));
                }
            }
        });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}