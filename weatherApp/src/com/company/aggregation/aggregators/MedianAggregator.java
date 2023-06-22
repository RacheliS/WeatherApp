package com.company.aggregation.aggregators;



import com.company.aggregation.services.DailyTemp;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MedianAggregator implements Aggregator {

    @Override
    public double aggregate(List<DailyTemp> temps) {
        Collections.sort(temps, new Comparator<DailyTemp>() {
            public int compare(DailyTemp t1, DailyTemp t2) {
                return Double.compare(t1.getTemperature(), t2.getTemperature());
            }
        });
        int size = temps.size();
        if (size % 2 == 0) {
            return (temps.get(size / 2 - 1).getTemperature() + temps.get(size / 2).getTemperature()) / 2;
        } else {
            return temps.get(size / 2).getTemperature();
        }
    }
}
