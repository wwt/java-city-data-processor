package com.wwt.locations;

import java.util.Optional;
import java.util.Set;

public interface LocationService {
    Set<State> getStates();

    Optional<State> findStateByName(String name);

    Set<City> findCitiesByZipCode(String zipCode);

    Set<City> findCitiesIn(State state);

    int calculatePopulation(State state);

    Set<City> findCitiesNearLocation(Coordinate location, double radiusInKilometers);
}
