package com.example.boris.showon.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boris.showon.R;
import com.example.boris.showon.model.Episode;
import com.example.boris.showon.model.Season;
import com.example.boris.showon.model.TVShow;
import com.example.boris.showon.network.RetrofitNetworkManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Boris on 22-Jul-17.
 */

public class SeasonsAdapter extends RecyclerView.Adapter<SeasonsAdapter.MyViewHolder> {
    public List<Season> list = new ArrayList<>();
    public Context context;
    public FragmentManager fragmentManager;
    public String tvShowName;

    public SeasonsAdapter(FragmentManager fragmentManager, Context context, List<Season> list, String tvShowName) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        for (int i = 0; i < list.size(); i++) {
            this.list.add(list.get(i));

        }
        this.tvShowName = tvShowName;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.seasons_row, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.season = list.get(position);
        holder.btnSeasonsRow.setText("Season " + holder.season.getNumber());
        if (holder.season.getNumber() == 1)
            holder.rvSeasonsRowEpisodes.setVisibility(View.VISIBLE);
        RetrofitNetworkManager.getInstance(context).getRetrofitService().getEpisodes(holder.season.getId()).enqueue(new Callback<List<Episode>>() {
            @Override
            public void onResponse(Call<List<Episode>> call, Response<List<Episode>> response) {
                if (response.isSuccessful()) {
                    String s = new String();
                    if (response.body().size() > 0) {
                        holder.listEpisode = response.body();
                        holder.rvSeasonsRowEpisodes.setLayoutManager(holder.mLayoutManager);
                        holder.episodesAdapter = new EpisodesAdapter(fragmentManager, context, holder.listEpisode, holder.season.getNumber(), tvShowName);
                        holder.rvSeasonsRowEpisodes.setAdapter(holder.episodesAdapter);


                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Episode>> call, Throwable t) {

            }
        });
//        holder.tvShow = list.get(position);
//        Picasso.with(context).load(holder.tvShow.getImage().getMedium()).into(holder.ivRowShowImage);
//        holder.tvRowShowName.setText(holder.tvShow.getName());
//        holder.tvRowShowLanguage.setText(holder.tvShow.getLanguage());
//        String[] s = holder.tvShow.getGenres();
//        String temp = new String();
//        for (int i = 0; i <s.length ; i++) {
//            temp += s[i] + ", ";
//        }
//


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button btnSeasonsRow;
        RecyclerView rvSeasonsRowEpisodes;
        Season season;
        List<Episode> listEpisode;
        EpisodesAdapter episodesAdapter;
        RecyclerView.LayoutManager mLayoutManager;
        TextView tvSeasonsRowEpisodes;


        public MyViewHolder(View view) {
            super(view);

            btnSeasonsRow = (Button) view.findViewById(R.id.btnSeasonsRow);
            btnSeasonsRow.setOnClickListener(this);
            rvSeasonsRowEpisodes = (RecyclerView) view.findViewById(R.id.rvSeasonsRowEpisodes);
            rvSeasonsRowEpisodes.setVisibility(View.GONE);
            mLayoutManager = new LinearLayoutManager(context);
            listEpisode = new ArrayList<>();
            tvSeasonsRowEpisodes = (TextView) view.findViewById(R.id.tvSeasonsRowEpisodes);

            season = new Season();

            view.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnSeasonsRow:
                    if (rvSeasonsRowEpisodes.getVisibility() == View.GONE)  {
                        if(listEpisode.size() == 0) {
                            tvSeasonsRowEpisodes.setText("No episodes yet");
                            tvSeasonsRowEpisodes.setVisibility(View.VISIBLE);
                            rvSeasonsRowEpisodes.setVisibility(View.VISIBLE);
                        }
                        else {
                            rvSeasonsRowEpisodes.setVisibility(View.VISIBLE);
                            tvSeasonsRowEpisodes.setVisibility(View.GONE);
                        }
                    }

                    else {
                        rvSeasonsRowEpisodes.setVisibility(View.GONE);
                        tvSeasonsRowEpisodes.setVisibility(View.GONE);
                    }
            }

        }
    }

}
