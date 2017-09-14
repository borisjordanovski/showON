package com.example.boris.showon.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.boris.showon.R;
import com.example.boris.showon.activity.showONActivity;
import com.example.boris.showon.adapters.SuggestedShowsAdapter;
import com.example.boris.showon.model.TVShow;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boris on 30-Aug-17.
 */

public class MyShowsFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    private RecyclerView rvMyShows;
    private List<TVShow> listMyShows;
    private SuggestedShowsAdapter suggestedShowsAdapter;

    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;

    public static MyShowsFragment newInstance() {
        return new MyShowsFragment();
    }

    private void initUI()   {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().child("user").child(firebaseAuth.getCurrentUser().getUid()).child("watchedShows");
        rvMyShows = (RecyclerView) rootView.findViewById(R.id.rvMyShows);
        listMyShows = new ArrayList<>();
    }

    @Override
    public int getNavigationId() {
        return 1;
    }

    @Override
    public String getTitle() {
        return "My Shows";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_my_shows, container, false);
        ((showONActivity) getActivity()).hideActionBar(false);
        initUI();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listMyShows = new ArrayList<TVShow>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    listMyShows.add( postSnapshot.getValue(TVShow.class));
                }
                suggestedShowsAdapter = new SuggestedShowsAdapter(supportFragmentManager,context, listMyShows);
                final RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(context);
                rvMyShows.setLayoutManager(mlayoutManager);
                rvMyShows.setAdapter(suggestedShowsAdapter);
                suggestedShowsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return rootView;
    }

    @Override
    public void onClick(View v) {

    }
}
