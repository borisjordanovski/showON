package com.example.boris.showon.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.boris.showon.R;
import com.example.boris.showon.fragment.SingleShowFragment;
import com.example.boris.showon.model.TVShow;
import com.example.boris.showon.util.UIHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boris on 20-Jul-17.
 */

public class SuggestedShowsAdapter extends RecyclerView.Adapter<SuggestedShowsAdapter.MyViewHolder> {
    public List<TVShow> list = new ArrayList<>();
    public Context context;
    public FragmentManager fragmentManager;

    public SuggestedShowsAdapter(FragmentManager fragmentManager, Context context, List<TVShow> list) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        for (int i = 0; i < list.size(); i++) {
            this.list.add(list.get(i));

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.suggested_show_row, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvShow = list.get(position);
        if(holder.tvShow.getImage() != null)    {
            Picasso.with(context).load(holder.tvShow.getImage().getMedium()).into(holder.ivRowShowImage);
        }
        holder.tvRowShowName.setText(holder.tvShow.getName());
        if(holder.tvShow.getLanguage() != null) {
            holder.tvRowShowLanguage.setText(holder.tvShow.getLanguage());
        } else holder.tvRowShowLanguage.setText("No information");
        List<String> s = holder.tvShow.getGenres();
        String temp = new String();
        for (int i = 0; i <s.size() ; i++) {
            temp += s.get(i) + ", ";
        }
        if(temp.length() > 0) {
            holder.tvRowShowGenre.setText("Genres:\t" + temp.substring(0,temp.length() - 2));
        } else holder.tvRowShowGenre.setText("No information");
        if(holder.tvShow.getPremiered() != null)    {
            holder.tvRowShowPremiered.setText("Premiered: " + holder.tvShow.getPremiered());
        } else holder.tvRowShowPremiered.setText("No information");

        if(holder.tvShow.getNetwork() != null)  {
            holder.tvRowShowCountry.setText(holder.tvShow.getNetwork().getCountry().getName());
            holder.tvRowShowNetwork.setText(holder.tvShow.getNetwork().getName());
        } else {
            holder.tvRowShowCountry.setText("No information");
            holder.tvRowShowNetwork.setText("No information");
        }





    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivRowShowImage;
        TextView tvRowShowName;
        TextView tvRowShowLanguage;
        TextView tvRowShowGenre;
        TextView tvRowShowPremiered;
        TextView tvRowShowNetwork;
        TextView tvRowShowCountry;

        TVShow tvShow;


        public MyViewHolder(View view) {
            super(view);

            ivRowShowImage = (ImageView) view.findViewById(R.id.ivRowShowImage);
            tvRowShowName = (TextView) view.findViewById(R.id.tvRowShowName);
            tvRowShowLanguage = (TextView) view.findViewById(R.id.tvRowShowLanguage);
            tvRowShowGenre = (TextView) view.findViewById(R.id.tvRowShowGenre);
            tvRowShowPremiered = (TextView) view.findViewById(R.id.tvRowShowPremiered);
            tvRowShowNetwork = (TextView) view.findViewById(R.id.tvRowShowNetwork);
            tvRowShowCountry = (TextView) view.findViewById(R.id.tvRowShowCountry);
            tvShow = new TVShow();
            view.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            UIHelper.addFragment(fragmentManager, R.id.fragmentContainer, SingleShowFragment.newInstance(tvShow), true, 0, 0);

        }
    }

}