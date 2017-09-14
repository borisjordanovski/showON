package com.example.boris.showon.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.boris.showon.R;
import com.example.boris.showon.activity.showONActivity;
import com.example.boris.showon.adapters.SuggestedShowsAdapter;
import com.example.boris.showon.model.TVShow;
import com.example.boris.showon.model.User;
import com.example.boris.showon.network.RetrofitNetworkManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Boris on 18-Jul-17.
 */

public class MainFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private List<TVShow> listShows;
    private RecyclerView rvMain;
    private SuggestedShowsAdapter suggestedShowsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private void initUI() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        listShows = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(context);
    }

    @Override
    public int getNavigationId() {
        return 0;
    }

    @Override
    public String getTitle() {
        return "showON";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ((showONActivity) getActivity()).hideActionBar(false);
        initUI();
        ((showONActivity)getActivity()).getNavigationView().getMenu().getItem(0).setChecked(true);
        suggestedShowsGenerator();


        ((showONActivity) getActivity()).updateNavDrawerInfo(firebaseAuth.getCurrentUser().getUid());

        return rootView;
    }

    private void suggestedShowsGenerator() {


        myRef.child("user").child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> params = new HashMap<String, String>();
                User user = dataSnapshot.getValue(User.class);

                for (int i = 0; i < user.getGenres().size(); i++) {
                    if (user.getGenres().get(i).equals("adventure")) {
                        params.put("q", "game of thrones");

                        RetrofitNetworkManager.getInstance(context).getRetrofitService().singleSearch(params).enqueue(new Callback<TVShow>() {
                            @Override
                            public void onResponse(Call<TVShow> call, Response<TVShow> response) {
                                if(response.isSuccessful()) {
                                    listShows.add(response.body());
                                    suggestListDone();
                                }

                            }

                            @Override
                            public void onFailure(Call<TVShow> call, Throwable t) {

                            }
                        });


                    } else if (user.getGenres().get(i).equals("action")) {
                        params.put("q", "arrow");

                        RetrofitNetworkManager.getInstance(context).getRetrofitService().singleSearch(params).enqueue(new Callback<TVShow>() {
                            @Override
                            public void onResponse(Call<TVShow> call, Response<TVShow> response) {
                                if(response.isSuccessful()) {
                                    listShows.add(response.body());
                                    suggestListDone();
                                }

                            }

                            @Override
                            public void onFailure(Call<TVShow> call, Throwable t) {

                            }
                        });
                    } else if (user.getGenres().get(i).equals("animation")) {
                        params.put("q", "Family Guy");

                        RetrofitNetworkManager.getInstance(context).getRetrofitService().singleSearch(params).enqueue(new Callback<TVShow>() {
                            @Override
                            public void onResponse(Call<TVShow> call, Response<TVShow> response) {
                                if(response.isSuccessful()) {
                                    listShows.add(response.body());
                                    suggestListDone();
                                }

                            }

                            @Override
                            public void onFailure(Call<TVShow> call, Throwable t) {

                            }
                        });
                    } else if (user.getGenres().get(i).equals("comedy")) {
                        params.put("q", "friends");

                        RetrofitNetworkManager.getInstance(context).getRetrofitService().singleSearch(params).enqueue(new Callback<TVShow>() {
                            @Override
                            public void onResponse(Call<TVShow> call, Response<TVShow> response) {
                                if(response.isSuccessful()) {
                                    listShows.add(response.body());
                                    suggestListDone();
                                }

                            }

                            @Override
                            public void onFailure(Call<TVShow> call, Throwable t) {

                            }
                        });
                    } else if (user.getGenres().get(i).equals("crime")) {
                        params.put("q", "breaking bad");

                        RetrofitNetworkManager.getInstance(context).getRetrofitService().singleSearch(params).enqueue(new Callback<TVShow>() {
                            @Override
                            public void onResponse(Call<TVShow> call, Response<TVShow> response) {
                                if(response.isSuccessful()) {
                                    listShows.add(response.body());
                                    suggestListDone();
                                }

                            }

                            @Override
                            public void onFailure(Call<TVShow> call, Throwable t) {

                            }
                        });
                    } else if (user.getGenres().get(i).equals("horror")) {
                        params.put("q", "the walking dead");

                        RetrofitNetworkManager.getInstance(context).getRetrofitService().singleSearch(params).enqueue(new Callback<TVShow>() {
                            @Override
                            public void onResponse(Call<TVShow> call, Response<TVShow> response) {
                                if(response.isSuccessful()) {
                                    listShows.add(response.body());
                                    suggestListDone();
                                }

                            }

                            @Override
                            public void onFailure(Call<TVShow> call, Throwable t) {

                            }
                        });
                    } else if (user.getGenres().get(i).equals("documentary")) {
                        params.put("q", "planet earth");

                        RetrofitNetworkManager.getInstance(context).getRetrofitService().singleSearch(params).enqueue(new Callback<TVShow>() {
                            @Override
                            public void onResponse(Call<TVShow> call, Response<TVShow> response) {
                                if(response.isSuccessful()) {
                                    listShows.add(response.body());
                                    suggestListDone();
                                }

                            }

                            @Override
                            public void onFailure(Call<TVShow> call, Throwable t) {

                            }
                        });
                    } else if (user.getGenres().get(i).equals("music")) {
                        params.put("q", "x factor");

                        RetrofitNetworkManager.getInstance(context).getRetrofitService().singleSearch(params).enqueue(new Callback<TVShow>() {
                            @Override
                            public void onResponse(Call<TVShow> call, Response<TVShow> response) {
                                if(response.isSuccessful()) {
                                    listShows.add(response.body());
                                    suggestListDone();
                                }

                            }

                            @Override
                            public void onFailure(Call<TVShow> call, Throwable t) {

                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void suggestListDone() {
        myRef.child("user").child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (listShows.size() == user.getGenres().size()) {
                    rvMain = (RecyclerView) rootView.findViewById(R.id.rvMain);
                    rvMain.setLayoutManager(mLayoutManager);
                    suggestedShowsAdapter = new SuggestedShowsAdapter(supportFragmentManager, context, listShows);
                    rvMain.setAdapter(suggestedShowsAdapter);
                    myRef.removeEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onClick(View v) {

    }
}
