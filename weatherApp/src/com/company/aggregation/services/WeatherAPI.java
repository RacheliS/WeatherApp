package com.company.aggregation.services;


import java.util.List;
import java.util.Set;

public interface WeatherAPI {
    Set<City> getAllCitiesByIds(Set<String> cityIds);
    List<DailyTemp> getLastYearTemprature(String cityId);
}
