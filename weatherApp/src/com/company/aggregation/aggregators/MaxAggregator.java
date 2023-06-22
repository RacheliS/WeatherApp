package com.company.aggregation.aggregators;


import com.company.aggregation.services.DailyTemp;

import java.util.List;

public class MaxAggregator implements Aggregator {

    @Override
    public double aggregate(List<DailyTemp> temps) {
        double max = Double.MIN_VALUE;
        for (DailyTemp temp : temps) {
            max = Math.max(max, temp.getTemperature());
        }
        return max;
    }
}
