package com.example.boris.showon.model;

import java.util.List;

/**
 * Created by Boris on 18-Jul-17.
 */

public class TVShow {
    private Long id;
    private String url;
    private String name;
    private String type;
    private String language;
    private List<String> genres;
    private String status;
    private String premiered;
    private TVShowImage image;
    private String summary;
    private ShowNetwork network;

    public TVShow() {}

    public TVShow(Long id, String url, String name, String type, String language, List<String> genres, String status, String premiered, TVShowImage image, String summary, ShowNetwork network) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.type = type;
        this.language = language;
        this.genres = genres;
        this.status = status;
        this.premiered = premiered;
        this.image = image;
        this.summary = summary;
        this.network = network;
    }

    public ShowNetwork getNetwork() {
        return network;
    }

    public void setNetwork(ShowNetwork network) {
        this.network = network;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPremiered() {
        return premiered;
    }

    public void setPremiered(String premiered) {
        this.premiered = premiered;
    }

    public TVShowImage getImage() {
        return image;
    }

    public void setImage(TVShowImage image) {
        this.image = image;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
