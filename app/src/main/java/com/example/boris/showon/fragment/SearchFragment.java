package com.example.boris.showon.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boris.showon.R;
import com.example.boris.showon.adapters.SuggestedShowsAdapter;
import com.example.boris.showon.model.TVShow;
import com.example.boris.showon.model.TVShowTemp;
import com.example.boris.showon.network.RetrofitNetworkManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Boris on 05-Sep-17.
 */

public class SearchFragment extends BaseFragment implements View.OnClickListener {

    private EditText etSearchShows;
    private RecyclerView rvSearchShows;
    private List<TVShow> listSearch;
    private SuggestedShowsAdapter suggestedShowsAdapter;
    private TextView tvSearchShows;

    private View rootView;

    public static SearchFragment newInstance()  { return new SearchFragment();}

    private void initUI()   {
        etSearchShows = (EditText) rootView.findViewById(R.id.etSearchShows);
        rvSearchShows = (RecyclerView) rootView.findViewById(R.id.rvSearchShows);
        listSearch = new ArrayList<>();
        tvSearchShows = (TextView) rootView.findViewById(R.id.tvSearch);
    }
    @Override
    public int getNavigationId() {
        return 2;
    }

    @Override
    public String getTitle() {
        return "showON";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        initUI();

        final Map<String, String> params = new HashMap<>();


        etSearchShows.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = etSearchShows.getText().toString();
                if(string.length() >= 3) {
                    params.put("q", string);
                    RetrofitNetworkManager.getInstance(context).getRetrofitService().searchShows(params).enqueue(new Callback<List<TVShowTemp>>() {
                        @Override
                        public void onResponse(Call<List<TVShowTemp>> call, Response<List<TVShowTemp>> response) {
                            if(response.isSuccessful()) {
                                listSearch.clear();
                                for (int i = 0; i <response.body().size() ; i++) {
                                    listSearch.add(response.body().get(i).getShow());
                                }
                                suggestedShowsAdapter = new SuggestedShowsAdapter(supportFragmentManager,context, listSearch);
                                final RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(context);
                                rvSearchShows.setLayoutManager(mlayoutManager);
                                rvSearchShows.setAdapter(suggestedShowsAdapter);
                                suggestedShowsAdapter.notifyDataSetChanged();

                                tvSearchShows.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<TVShowTemp>> call, Throwable t) {

                        }
                    });
                } else  {

                }
            }
        });


        return rootView;
    }

    @Override
    public void onClick(View v) {

    }
}
