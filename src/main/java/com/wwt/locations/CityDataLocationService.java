package com.wwt.locations;

import com.wwt.locations.data.CityDataSource;

import java.util.Optional;
import java.util.Set;

// FIXME: #2 Please implement this! See CityDataLocationServiceTest for some examples of how this should work.
public class CityDataLocationService implements LocationService {
    private final CityDataSource cityDataSource;

    public CityDataLocationService(CityDataSource cityDataSource) {
        this.cityDataSource = cityDataSource;
    }

    @Override
    public Set<State> getStates() {
        return Set.of();
    }

    @Override
    public Optional<State> findStateByName(String stateName) {
        return Optional.empty();
    }

    @Override
    public Set<City> findCitiesByZipCode(String zipCode) {
        return Set.of();
    }

    @Override
    public Set<City> findCitiesIn(State state) {
        return Set.of();
    }

    @Override
    public int calculatePopulation(State state) {
        return 0;
    }

    @Override
    public Set<City> findCitiesNearLocation(Coordinate location, double radiusInKilometers) {
        return Set.of();
    }
}
