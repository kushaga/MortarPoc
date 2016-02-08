package com.example.akosha.sample1.poc2.apiclass;

import com.example.akosha.sample1.poc2.model.DatModel;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by kushagarlall on 02/02/16.
 */
public interface CountriesApiService {
    //http://www.mocky.io/v2/56b208cf130000e81a894547
    public static final String ENDPOINT = "http://www.mocky.io/v2";

    @GET("/56b208cf130000e81a894547")
    Observable<DatModel> getCountriesList();

    @GET("/56b252ee1300009a2f89459d")
    Observable<DatModel> getCountriesdDesc();
}
