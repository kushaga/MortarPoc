package com.example.akosha.sample1.poc2.apiclass;

import retrofit.http.GET;

/**
 * Created by kushagarlall on 01/02/16.
 */
public interface ApiService {
    class Data {
        public String dataString;

        public Data(String dataString) {
            this.dataString = dataString;
        }
    }

    @GET("/")
    Data getData();
}
