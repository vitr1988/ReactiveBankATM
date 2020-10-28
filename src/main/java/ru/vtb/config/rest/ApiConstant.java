package ru.vtb.config.rest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiConstant {
    public static final String ATM_SEARCH_URL = "/api/atms";
    public static final String LONGITUDE_REQUEST_PARAM = "longitude";
    public static final String LATITUDE_REQUEST_PARAM = "latitude";
    public static final String DISTANCE_REQUEST_PARAM = "distance";

    public static final String REFRESH_URL = "/refresh";
    public static final String STATUS_URL = "/status";
}
