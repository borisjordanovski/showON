package com.example.boris.showon.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.boris.showon.R;
import com.example.boris.showon.model.Episode;
import com.example.boris.showon.model.Season;
import com.example.boris.showon.model.TVShow;
import com.example.boris.showon.network.RetrofitNetworkManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Boris on 06-Sep-17.
 */

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.MyViewHolder> {
    public List<Episode> list = new ArrayList<>();
    public Context context;
    public FragmentManager fragmentManager;

    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private int seasonNumber;
    private String tvShowName;
    private List<String> watchedEpisodes;

    public EpisodesAdapter(FragmentManager fragmentManager, Context context, List<Episode> list, int seasonNumber, String tvShowName) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        for (int i = 0; i < list.size(); i++) {
            this.list.add(list.get(i));

        }
        watchedEpisodes = new ArrayList<>();
        this.tvShowName = tvShowName;
        this.seasonNumber = seasonNumber;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().child("user").child(firebaseAuth.getCurrentUser().getUid()).child("watchedEpisodes").child(tvShowName).child(seasonNumber + "");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String episodeName = postSnapshot.getValue(String.class);
                    watchedEpisodes.add(episodeName);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.episodes_row, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.episode = list.get(position);
        String s = new String();
        try {
            Integer.parseInt(holder.episode.getNumber());
            s += holder.episode.getNumber() + ": " + holder.episode.getName();
        } catch (Exception e) {
            s += "Special: " + holder.episode.getName();
        }
        for (int i = 0; i < watchedEpisodes.size() ; i++) {
            if(holder.episode.getName().equals(watchedEpisodes.get(i))) {
                holder.cbEpisodesRow.setChecked(true);
                break;
            }

        }
        holder.tvEpisodesRow.setText(s);
//        holder.btnSeasonsRow.setText("Season " + holder.season.getNumber());
//        if(holder.season.getNumber() == 1)
//            holder.tvSeasonsRowEpisodes.setVisibility(View.VISIBLE);
//        RetrofitNetworkManager.getInstance(context).getRetrofitService().getEpisodes(holder.season.getId()).enqueue(new Callback<List<Episode>>() {
//            @Override
//            public void onResponse(Call<List<Episode>> call, Response<List<Episode>> response) {
//                if(response.isSuccessful()) {
//                    String s = new String();
//                    if (response.body().size() > 0) {
//                        for (int i = 0; i < response.body().size(); i++) {

//
//
//                        }
//                    } else
//                        s = "No episodes yet";
//                    holder.tvSeasonsRowEpisodes.setText(s);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Episode>> call, Throwable t) {
//
//            }
//        });
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
        CheckBox cbEpisodesRow;
        TextView tvEpisodesRow;
        Episode episode;


        public MyViewHolder(View view) {
            super(view);

            cbEpisodesRow = (CheckBox) view.findViewById(R.id.cbEpisodesRow);
            cbEpisodesRow.setOnClickListener(this);
            tvEpisodesRow = (TextView) view.findViewById(R.id.tvEpisodesRow);
            episode = new Episode();

            view.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            switch (v.getId())   {
                case R.id.cbEpisodesRow:
                    if(cbEpisodesRow.isChecked())   {
                        List<String> temp = new ArrayList<>();
                        temp = watchedEpisodes;
                        temp.add(episode.getName());
                        myRef.removeValue();
                        myRef.setValue(temp);
                        cbEpisodesRow.setChecked(true);

                    } else  {

                        List<String> temp = new ArrayList<>();
                        temp = watchedEpisodes;
                        temp.remove(episode.getName());
                        myRef.setValue(temp);
                        cbEpisodesRow.setChecked(false);

                    }
                    break;
            }

        }
    }

}

