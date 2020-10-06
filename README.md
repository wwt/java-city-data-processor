# city-data-processor
Hello, thanks for spending your time and taking a look at this problem today! 

This project has already been started. The end goal is to provide an implementation of LocationService based on the city
data included in `src/main/resources/uscities.csv`
 
### What's Included
There are a few things in place for you already:
 - `CsvCityDataSource`: This is the class responsible for transforming CSV rows into `City` objects. Your time is valuable, so we knocked out most of the CSV parsing infrastructure for you.
 - `CoordinateDistanceCalculator`: This class calculates the distance between two coordinates in kilometers. It will be important for your implementation of the LocationService and finding nearby cities given a lat/lng. 

### Requirements
- JDK 11 (or higher)
- Optional: Intellij, Eclipse, or your favorite IDE

### Instructions
There are just a few things left before we can deliver value to our hypothetical users!

1. We need zip codes to be mapped into the city object, so we can find cities by zip code in the LocationService. Tests have been provided for this in `CsvCityDataSourceTest`. 
1. We need an implementation of LocationService, so we can answer questions about the data set. Since it will be backed by city data, we called it `CityDataLocationService`. There are tests that use the real data in uscities.csv, see `CityDataLocationServiceTest`. If you prefer mocks, feel free to use them; we just wanted to keep things simple for people that are unfamiliar with mocking frameworks.

**Have fun!** Feel free to add dependencies if you need them. If you need to look something up, that's absolutely allowed, but don't copy another solution, or collaborate on this problem with others. Normally we encourage teamwork, but today we want to see what you can do!

We value clean, readable code that passes the requirements. If you can make it efficient and easy to read, even better!

### Verifying Results
- Run `./gradlew check` (or `gradlew check` on Windows) to see if you've completed the requirements.

### Submitting Results
Please fork this project and implement your solution. Send us a link to your repo, or send us a zip file of your implementation. 

### Attributions

| Item | Source |
| ------ | ------ |
| City Data | https://simplemaps.com/data/us-cities |
| Coorindate Distance Calculation | https://www.geodatasource.com/developers/java |
