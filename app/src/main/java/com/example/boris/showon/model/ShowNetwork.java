package com.example.boris.showon.model;

/**
 * Created by Boris on 21-Jul-17.
 */

public class ShowNetwork {
    private String name;
    private ShowNetworkCountry country;


    public ShowNetwork(){}
    public ShowNetwork(String name, ShowNetworkCountry country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShowNetworkCountry getCountry() {
        return country;
    }

    public void setCountry(ShowNetworkCountry country) {
        this.country = country;
    }
}
