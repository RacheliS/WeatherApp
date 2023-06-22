package com.company.aggregation.services;

import com.company.aggregation.aggregators.Aggregator;
import com.company.aggregation.enums.AggregatorType;


import java.util.*;
import java.util.concurrent.*;


public class WeatherService {

    private final WeatherAPI weatherAPI = new WeatherAPIService();
    private static final int POPULATION = 50000;

    public Collection<City> getCitiesAggregation(Set<String> cityIds, AggregatorType aggregatorType, Integer topNCities) {
        Set<City> allCitiesByIds = weatherAPI.getAllCitiesByIds(cityIds);
        Map<String, List<DailyTemp>> results = getApiCitiesTempById(cityIds);
        Map<City, Double> cityTemps = calculateAggregated(aggregatorType, allCitiesByIds, results);
        PriorityQueue<City> topCities = getTopCities(topNCities, cityTemps);

        List<City> topCitiesList = new ArrayList<>();
        while (!topCities.isEmpty()) {
            topCitiesList.add(0, topCities.poll());
        }
        return topCitiesList;
    }

    // Calculate aggregated temperature for each city
    private Map<City, Double> calculateAggregated(AggregatorType aggregatorType, Set<City> allCitiesByIds, Map<String, List<DailyTemp>> results) {
        Map<City, Double> cityTemps = new HashMap<>();
        for (String citiId : results.keySet()) {
            List<DailyTemp> dailyTemps = results.get(citiId);
            if (!dailyTemps.isEmpty()) {
                Aggregator aggregator = AggregatorFactory.createAggregator(aggregatorType);
                double temp = aggregator.aggregate(dailyTemps);
                final City city = allCitiesByIds.stream().filter(x -> x.getId().equals(citiId)).findFirst().orElse(null);
                cityTemps.put(city, temp);
            }
        }
        return cityTemps;
    }

    // Use PriorityQueue to find top N cities by temperature
    private PriorityQueue<City> getTopCities(Integer topNCities, Map<City, Double> cityTemps) {
        PriorityQueue<City> topCities = new PriorityQueue<>(Comparator.comparingDouble(cityTemps::get));
        for (City city : cityTemps.keySet()) {
            if (city.getPopulation() > POPULATION) {
                topCities.offer(city);
                if (topCities.size() > topNCities) {
                    topCities.poll();
                }
            }
        }
        return topCities;
    }

    private Map<String, List<DailyTemp>> getApiCitiesTempById(Set<String> cityIds) {
        ExecutorService executor = Executors.newFixedThreadPool(cityIds.size());
        Map<String, Future<List<DailyTemp>>> futures = new HashMap<>();
        for (String cityId : cityIds) {
            Callable<List<DailyTemp>> callable = () -> weatherAPI.getLastYearTemprature(cityId);
            Future<List<DailyTemp>> future = executor.submit(callable);
            futures.put(cityId, future);
        }

        Map<String, List<DailyTemp>> results = new HashMap<>();
        for (String cityId : futures.keySet()) {
            try {
                List<DailyTemp> temps = futures.get(cityId).get();
                results.put(cityId, temps);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
