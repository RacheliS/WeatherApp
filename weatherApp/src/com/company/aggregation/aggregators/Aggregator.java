package com.company.aggregation.aggregators;



import com.company.aggregation.services.DailyTemp;

import java.util.List;

public interface Aggregator {

    public double aggregate(List<DailyTemp> temps);
}
