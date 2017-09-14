package com.example.boris.showon.network;

import com.example.boris.showon.model.Episode;
import com.example.boris.showon.model.Season;
import com.example.boris.showon.model.TVShow;
import com.example.boris.showon.model.TVShowTemp;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Boris on 18-Jul-17.
 */

public interface RetrofitService {

    @GET("/singlesearch/shows")
    Call<TVShow> singleSearch(@QueryMap Map<String, String> params);

    @GET("/search/shows")
    Call<List<TVShowTemp>> searchShows(@QueryMap Map<String, String> params);

    @GET("/shows/{id}/seasons")
    Call<List<Season>> getSeasons(@Path("id") Long id);

    @GET("/seasons/{id}/episodes")
    Call<List<Episode>> getEpisodes(@Path("id") Long id);






}
