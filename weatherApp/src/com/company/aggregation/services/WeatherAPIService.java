package com.company.aggregation.services;


import java.util.List;
import java.util.Set;

public class WeatherAPIService implements WeatherAPI {
    @Override
    public Set<City> getAllCitiesByIds(Set<String> cityIds) {
        return null;
    }

    @Override
    public List<DailyTemp> getLastYearTemprature(String cityId) {
        return null;
    }
}
