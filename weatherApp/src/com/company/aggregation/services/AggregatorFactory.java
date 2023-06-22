package com.company.aggregation.services;

import com.company.aggregation.aggregators.Aggregator;
import com.company.aggregation.aggregators.AvgAggregator;
import com.company.aggregation.aggregators.MaxAggregator;
import com.company.aggregation.aggregators.MedianAggregator;
import com.company.aggregation.enums.AggregatorType;

public  class AggregatorFactory {

    public static Aggregator createAggregator(AggregatorType aggregatorType) {
        return switch (aggregatorType) {
            case AVG -> new AvgAggregator();
            case MAX -> new MaxAggregator();
            case MEDIAN -> new MedianAggregator();
        };
    }
}
