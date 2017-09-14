package com.example.boris.showon.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boris.showon.R;
import com.example.boris.showon.model.Season;
import com.example.boris.showon.model.TVShow;
import com.example.boris.showon.util.UIHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boris on 21-Jul-17.
 */

public class SingleShowFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    private ImageView ivSingleShowLogo;
    private TextView tvSingleShowName;
    private TextView tvSingleShowStatus;
    private TextView tvSingleShowLanguage;
    private TextView tvSingleShowGenres;
    private TextView tvSingleShowPremiered;
    private TextView tvSingleShowNetwork;
    private WebView wvSingleShowSummary;
    private Button btnSingleShowSeasons;
    private ImageButton btnSingleShowStar;
    private List<TVShow> showsList;
    private Boolean flagShowWatched;

    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;

    private static TVShow tvShow;

    public static SingleShowFragment newInstance(TVShow tvShow1)  {
        tvShow = tvShow1;
        return new SingleShowFragment();
    }

    private void initUI() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().child("user").child(firebaseAuth.getCurrentUser().getUid()).child("watchedShows");
        ivSingleShowLogo = (ImageView) rootView.findViewById(R.id.ivSingleShowLogo);
        tvSingleShowName = (TextView) rootView.findViewById(R.id.tvSingleShowName);
        tvSingleShowStatus = (TextView) rootView.findViewById(R.id.tvSingleShowStatus);
        tvSingleShowLanguage = (TextView) rootView.findViewById(R.id.tvSingleShowLanguage);
        tvSingleShowGenres = (TextView) rootView.findViewById(R.id.tvSingleShowGenres);
        tvSingleShowPremiered = (TextView) rootView.findViewById(R.id.tvSingleShowPremiered);
        tvSingleShowNetwork = (TextView) rootView.findViewById(R.id.tvSingleShowNetwork);
        wvSingleShowSummary = (WebView) rootView.findViewById(R.id.wvSingleShowSummary);
        btnSingleShowSeasons = (Button) rootView.findViewById(R.id.btnSingleShowSeasons);
        btnSingleShowSeasons.setOnClickListener(this);
        btnSingleShowStar = (ImageButton) rootView.findViewById(R.id.btnSingleShowStar);
        btnSingleShowStar.setOnClickListener(this);
        flagShowWatched = false;

        showsList = initShowList();
    }

    @Override
    public int getNavigationId() {
        return 0;
    }

    @Override
    public String getTitle() {
        return tvShow.getName();
    }

    private List<TVShow> initShowList() {
        final List<TVShow> list = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    TVShow tvShow1 = postSnapshot.getValue(TVShow.class);
                    if(tvShow1.getName().equals(tvShow.getName()))   {
                       if(getActivity()!=null && isAdded()) {
                           flagShowWatched = true;
                           btnSingleShowStar.setImageDrawable(getResources().getDrawable(R.drawable.ic_star));
                       }
                    }
                    list.add(tvShow1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return list;
    }


    private void addShowToFavorites() {
        showsList.add(tvShow);
        myRef.setValue(showsList);
        flagShowWatched = true;
        btnSingleShowStar.setImageDrawable(getResources().getDrawable(R.drawable.ic_star));
    }

    private void removeShowFromFavorites(List<TVShow> showsList)  {
        List<TVShow> temp = new ArrayList<>();
        for (int i = 0; i < showsList.size() ; i++) {
            if(!showsList.get(i).getId().equals(tvShow.getId())) {
               temp.add(showsList.get(i));
            }
        }
        myRef.setValue(temp);
        this.showsList = temp;
        flagShowWatched = false;
        btnSingleShowStar.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_border));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_single_show, container, false);
        initUI();
        if(tvShow.getImage() != null) {
            Picasso.with(context).load(tvShow.getImage().getMedium()).into(ivSingleShowLogo);
        }
        tvSingleShowName.setText(tvShow.getName());
        if(tvShow.getStatus() != null)  {
            tvSingleShowStatus.setText("Status: " + tvShow.getStatus());
        }
        if(tvShow.getLanguage() != null)    {
            tvSingleShowLanguage.setText(tvShow.getLanguage());
        }
        List<String> s = tvShow.getGenres();
        String temp = new String();
        for (int i = 0; i <s.size() ; i++) {
            temp += s.get(i) + ", ";
        }
        if(temp.length() > 0) {
            tvSingleShowGenres.setText("Genres:\t" + temp.substring(0, temp.length() - 2));
        }
        if(tvShow.getPremiered() != null) {
            tvSingleShowPremiered.setText("Premired: " + tvShow.getPremiered());
        }
        if(tvShow.getNetwork() != null) {
            tvSingleShowNetwork.setText(tvShow.getNetwork().getName());
        }
        if(tvShow.getSummary() != null) {
            wvSingleShowSummary.loadData(tvShow.getSummary(), "text/html; charset=utf-8", "utf-8");
        }


        return rootView;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId())  {
            case R.id.btnSingleShowSeasons:
                UIHelper.addFragment(supportFragmentManager, R.id.fragmentContainer, SeasonsFragment.newInstance(tvShow), true, 0, 0);
                break;
            case R.id.btnSingleShowStar:
                if(flagShowWatched == true)
                    removeShowFromFavorites(showsList);
                else    {
                    addShowToFavorites();
                }
                break;
        }

    }


}
