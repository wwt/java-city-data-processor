package com.wwt.locations;

import static java.lang.Math.*;

// Adapted from: https://www.geodatasource.com/developers/java
public class CoordinateDistanceCalculator {
    private static final int NAUTICAL_MILES_PER_DEGREE = 60;
    private static final double MILES_PER_NAUTICAL_MILE = 1.1515;
    private static final double KILOMETERS_PER_MILE = 1.609344;

    public static double kilometersBetween(Coordinate lhs, Coordinate rhs) {
        if (lhs.equals(rhs)) {
            return 0;
        }
        double theta = lhs.getLon() - rhs.getLon();
        double distanceInRadians = acos(sin(toRadians(lhs.getLat())) * sin(toRadians(rhs.getLat())) +
                cos(toRadians(lhs.getLat())) * cos(toRadians(rhs.getLat())) * cos(toRadians(theta)));
        double nauticalMiles = toDegrees(distanceInRadians) * NAUTICAL_MILES_PER_DEGREE;
        double miles = nauticalMiles * MILES_PER_NAUTICAL_MILE;
        return miles * KILOMETERS_PER_MILE;
    }

    private CoordinateDistanceCalculator() {
    }
}
