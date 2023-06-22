package com.company.aggregation.aggregators;


import com.company.aggregation.services.DailyTemp;

import java.util.List;



public class AvgAggregator implements Aggregator {

    @Override
    public double aggregate(List<DailyTemp> temps) {
        double sum = 0;
        for (DailyTemp temp : temps) {
            sum += temp.getTemperature();
        }
        return sum / temps.size();
    }
}
