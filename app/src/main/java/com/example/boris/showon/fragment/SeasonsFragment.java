package com.example.boris.showon.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.boris.showon.R;
import com.example.boris.showon.activity.showONActivity;
import com.example.boris.showon.adapters.SeasonsAdapter;
import com.example.boris.showon.model.Season;
import com.example.boris.showon.model.TVShow;
import com.example.boris.showon.network.RetrofitNetworkManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Boris on 22-Jul-17.
 */

public class SeasonsFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    private static TVShow tvShow;
    private RecyclerView rvSeasons;
    private List<Season> listSeasons;
    private SeasonsAdapter seasonsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public static SeasonsFragment newInstance(TVShow tvShow1) {
        tvShow = tvShow1;
        return new SeasonsFragment();
    }


    private void initUI() {
        rvSeasons = (RecyclerView) rootView.findViewById(R.id.rvSeasons);
        listSeasons = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(context);
    }

    @Override
    public int getNavigationId() {
        return 0;
    }

    @Override
    public String getTitle() {
        return tvShow.getName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_seasons, container, false);
        ((showONActivity) getActivity()).hideActionBar(false);
        initUI();

        RetrofitNetworkManager.getInstance(context).getRetrofitService().getSeasons(tvShow.getId()).enqueue(new Callback<List<Season>>() {
            @Override
            public void onResponse(Call<List<Season>> call, Response<List<Season>> response) {
                if(response.isSuccessful()) {
                    listSeasons = response.body();
                    rvSeasons.setLayoutManager(mLayoutManager);
                    seasonsAdapter = new SeasonsAdapter(supportFragmentManager,context, listSeasons, tvShow.getName());
                    rvSeasons.setAdapter(seasonsAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Season>> call, Throwable t) {

            }
        });

        return rootView;
    }


    @Override
    public void onClick(View v) {

    }
}
