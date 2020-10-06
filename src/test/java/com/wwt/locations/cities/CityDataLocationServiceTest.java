package com.wwt.locations.cities;

import com.wwt.locations.*;
import com.wwt.locations.data.CityDataSource;
import com.wwt.locations.data.CsvCityDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CityDataLocationServiceTest {
    public static final String[] ALL_STATE_NAMES = {"North Carolina",
            "Indiana", "Wyoming", "Utah", "Arizona", "Montana",
            "Kentucky", "Kansas", "California",
            "Delaware", "Florida", "Pennsylvania",
            "Mississippi", "Iowa", "Illinois",
            "Texas", "Connecticut", "Georgia",
            "Maryland", "Virginia", "Idaho",
            "Vermont", "Oregon", "Puerto Rico",
            "Oklahoma", "Maine", "Tennessee",
            "Alabama", "Arkansas", "South Carolina",
            "Washington", "Nebraska", "West Virginia",
            "Colorado", "Massachusetts", "Missouri",
            "Alaska", "North Dakota", "Wisconsin",
            "Nevada", "New York", "Rhode Island",
            "Hawaii", "South Dakota", "Minnesota",
            "New Jersey", "Michigan", "New Mexico",
            "New Hampshire", "Louisiana", "Ohio"};
    private LocationService locationService;

    @BeforeEach
    void setUp() throws Exception {
        InputStream usCities = getClass().getClassLoader().getResourceAsStream("uscities.csv");
        CityDataSource csvCityDataSource = CsvCityDataSource.from(usCities);
        locationService = new CityDataLocationService(csvCityDataSource);
    }

    @Test
    void shouldBeAbleToGetAllStatesFromCityData() {

        Set<State> states = locationService.getStates();

        assertThat(states.size()).isEqualTo(51); // Puerto Rico is in the data set.
        assertThat(states.stream().map(State::getName)).contains(ALL_STATE_NAMES);
    }

    @Test
    void shouldBeAbleToFindExistingStateByName() {
        Optional<State> states = locationService.findStateByName("Missouri");

        assertThat(states).isPresent().contains(new State("Missouri", "MO"));
    }

    @Test
    void shouldReturnAbsentWhenNoSuchState() {
        Optional<State> states = locationService.findStateByName("Arkasota");

        assertThat(states).isEmpty();
    }

    @Test
    void findCitiesInState() {
        State missouri = new State("Missouri", "MO");
        State illinois = new State("Illinois", "IL");

        // Spot check counts to verify correct city filtering.
        assertThat(locationService.findCitiesIn(missouri).size()).isEqualTo(1034);
        assertThat(locationService.findCitiesIn(illinois).size()).isEqualTo(1367);
    }

    @Test
    void findCityInStateThatDoesNotExistReturnsEmptySet() {
        State state = new State("Fake", "Town");

        assertThat(locationService.findCitiesIn(state)).isEmpty();
    }

    @Test
    void calculatePopulationOfStateAsSumOfCityPopulation() {
        State missouri = new State("Missouri", "MO");
        State illinois = new State("Illinois", "IL");

        assertThat(locationService.calculatePopulation(missouri)).isEqualTo(7451406);
        assertThat(locationService.calculatePopulation(illinois)).isEqualTo(9496917);
    }

    @Test
    void calculatePopulationOfFakeStateReturnsZero() {
        State state = new State("Fake", "FK");

        assertThat(locationService.calculatePopulation(state)).isZero();
    }

    @Test
    void findCityByZipReturnsCityWhenSingleMatchFound() {
        Set<City> citiesByZipCode = locationService.findCitiesByZipCode("63139");

        assertThat(citiesByZipCode).hasSize(1)
                .first()
                .hasFieldOrPropertyWithValue("name", "St. Louis")
                .hasFieldOrPropertyWithValue("state.abbreviation", "MO");
    }

    @Test
    void findCityByZipReturnsEmptyOptionalWhenNoMatch() {
        assertThat(locationService.findCitiesByZipCode("fake-stuff")).isEmpty();
    }

    @Test
    void findCityByZipReturnsAllCitiesWhenMultipleShareZipCode() {
        Set<City> citiesByZipCode = locationService.findCitiesByZipCode("78516");

        assertThat(citiesByZipCode).hasSize(3);
        assertThat(citiesByZipCode.stream().map(City::getState).map(State::getName)).containsOnly("Texas");
        assertThat(citiesByZipCode.stream().map(City::getName)).containsExactlyInAnyOrder("Alamo", "North Alamo", "South Alamo");
    }

    /**
     * Please use {@link com.wwt.locations.CoordinateDistanceCalculator}
     * You do not have to implement the math for this!
     */
    @Test
    void findCitiesNearCoordinateShouldReturnCitiesWithinRadiusOfProvidedLocation() {
        String brooklynIlId = "1840010780";
        String stLouisId = "1840001651";
        String saugetIlId = "1840012873";
        String eastStLouisId = "1840008629";
        String veniceIlId = "1840010710";
        Coordinate stLouisCoordinate = Coordinate.of(38.6270, -90.1994);

        Set<City> citiesNearLocation = locationService.findCitiesNearLocation(stLouisCoordinate, 7);

        assertThat(citiesNearLocation.stream().map(City::getId))
                .containsExactlyInAnyOrder(brooklynIlId, stLouisId, saugetIlId, eastStLouisId, veniceIlId);
    }

    @Test
    void findNearbyCitiesReturnsEmptySetWhenNothingMatchesCriteria() {
        Set<City> citiesNearLocation = locationService.findCitiesNearLocation(Coordinate.of(0, 0), 1000);

        assertThat(citiesNearLocation).isEmpty();
    }

    @Test
    void findCitiesNearCoordinateDoesNotAcceptNegativeSearchRadius() {
        Coordinate anyCoordinate = Coordinate.of(0, 0);

        assertThatThrownBy(() -> locationService.findCitiesNearLocation(anyCoordinate, -1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
