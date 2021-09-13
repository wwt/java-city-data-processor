package com.wwt.locations.data;

import com.wwt.locations.City;
import com.wwt.locations.Coordinate;
import com.wwt.locations.State;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import static java.lang.Double.parseDouble;
import static java.util.stream.Collectors.toUnmodifiableSet;

public class CsvCityDataSource implements CityDataSource {
    private final InputStream csvInputStream;
    private Set<City> cities;

    public static CityDataSource from(InputStream inputStream) throws IOException {
        return new CsvCityDataSource(inputStream).load();
    }

    private CsvCityDataSource(InputStream csvInputStream) {
        this.csvInputStream = Objects.requireNonNull(csvInputStream, "InputStream is required.");
    }

    @Override
    public Set<City> getAllCities() {
        return cities;
    }

    private CsvCityDataSource load() throws IOException {
        try (InputStreamReader inputStreamReader = new InputStreamReader(csvInputStream);
             BufferedReader reader = new BufferedReader(inputStreamReader)) {
            this.cities = readCities(CSVFormat.DEFAULT.builder()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .build().parse(reader));
        }
        return this;
    }

    private Set<City> readCities(CSVParser csvParser) throws IOException {
        return csvParser.getRecords().stream()
                .map(rowToCity)
                .collect(toUnmodifiableSet());
    }

    private final Function<CSVRecord, City> rowToCity = row -> {
        City city = new City();
        city.setId(row.get("id"));
        city.setName(row.get("city_ascii"));
        city.setLocation(new Coordinate(parseDouble(row.get("lat")), parseDouble(row.get("lng"))));
        city.setTimeZoneId(ZoneId.of(row.get("timezone")));
        city.setPopulation((int) Double.parseDouble(row.get("population")));
        city.setState(new State(row.get("state_name"), row.get("state_id")));
        return city;
    };
}
