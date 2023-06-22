package com.company;

import com.company.aggregation.enums.AggregatorType;
import com.company.aggregation.services.City;
import com.company.aggregation.services.WeatherService;


import java.util.*;

public class Main {


    public static void main(String[] args) {
        WeatherService weatherApp = new WeatherService();
        Set<String> cityIds = Set.of("TLV", "NY");

        Collection<City> topCities = weatherApp.getCitiesAggregation(cityIds, AggregatorType.AVG,3);
        System.out.println("Top 3 cities:");
        for (City city : topCities) {
            System.out.println(city.getName());
        }
    }


}