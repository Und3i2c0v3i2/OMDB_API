package com.example.omdbapi.retrofit;

import static com.example.omdbapi.App.API_KEY;

public interface APIConstants {

    String BASE_URL = "https://www.omdbapi.com";


    String QUERYMAP_KEY_SEARCH = "s";
    String QUERYMAP_KEY_ID = "i";

    String ENDPOINT_ALL = "/";

    String HEADER_KEY = "apikey";
    String HEADER_KEY_VALUE = API_KEY;

}
