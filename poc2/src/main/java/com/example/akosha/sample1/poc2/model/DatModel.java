package com.example.akosha.sample1.poc2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kushagarlall on 02/02/16.
 */
public class DatModel {
    @SerializedName("countries")
    public List<Country> countries;

    public class Country {
        @SerializedName("countryName")
        public String countryName;

    }
}
