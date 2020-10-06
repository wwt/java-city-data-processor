package com.wwt.locations.cities;

import com.wwt.locations.City;
import com.wwt.locations.Coordinate;
import com.wwt.locations.State;
import com.wwt.locations.data.CityDataSource;
import com.wwt.locations.data.CsvCityDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CsvCityDataSourceTest {
    private static final String SEDONA_AZ_ID = "1840021584";
    private static final String RIVERSIDE_CA_ID = "1840020551";
    private CityDataSource cityDataSource;

    @BeforeEach
    void setUp() throws IOException {
        InputStream testCities = getClass().getClassLoader().getResourceAsStream("test-cities.csv");
        cityDataSource = CsvCityDataSource.from(testCities);
    }

    @Test
    void shouldReturnAllCities() {
        Set<City> cities = cityDataSource.getAllCities();

        assertThat(cities.size()).isEqualTo(20);
    }

    @Test
    void allCityPropertiesShouldBePopulated() {
        City sedona = findCity(SEDONA_AZ_ID);

        assertThat(sedona)
                .hasFieldOrPropertyWithValue("id", SEDONA_AZ_ID)
                .hasFieldOrPropertyWithValue("name", "Sedona")
                .hasFieldOrPropertyWithValue("location", Coordinate.of(34.8574, -111.7951))
                .hasFieldOrPropertyWithValue("timeZoneId", ZoneId.of("America/Phoenix"))
                .hasFieldOrPropertyWithValue("state", new State("Arizona", "AZ"))
                .hasFieldOrPropertyWithValue("population", 6969);
    }

    @Test
    // FIXME: #1 Please implement the code that passes this test.
    // It expects a java.util.Set property on cities called "zipCodes", see other passing test for reference.
    void zipCodesShouldBeMappedIntoSetOfStrings() {
        Set<String> riversideZipCodes = Set.of(
                "92508", "92503", "92501",
                "92505", "92504", "92507",
                "92506", "92502", "92513",
                "92514", "92516", "92517",
                "92521", "92522");

        assertThat(findCity(RIVERSIDE_CA_ID))
                .hasFieldOrPropertyWithValue("zipCodes", riversideZipCodes);

        assertThat(findCity(SEDONA_AZ_ID))
                .hasFieldOrPropertyWithValue("zipCodes", Set.of("86336", "86339"));
    }

    private City findCity(String cityId) {
        return cityDataSource.getAllCities()
                .stream()
                .filter(city -> city.getId().equals(cityId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Expected city with id " + cityId + " to be present, but was not."));
    }
}
