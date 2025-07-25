package com.noBroker.nobroker_application_project.config;

import com.noBroker.nobroker_application_project.model.*;
import com.noBroker.nobroker_application_project.repository.PropertyRepository;
import com.noBroker.nobroker_application_project.repository.UserRepository;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Component
public class PropertyCsvLoader implements CommandLineRunner {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public void run(String... args) throws Exception {


        try (CSVReader reader = new CSVReader(new FileReader("src/main/resources/application_file.csv"))) {
            String[] header = reader.readNext();
            String[] row;

            while ((row = reader.readNext()) != null) {
                try {
                    Property property = new Property();
                    Amenity amenity = new Amenity();
                    Address address = new Address();

                    address.setCity(row[0]);
                    address.setLocality(row[1]);
                    address.setLandmark(row[2]);
                    address.setLatitude(parseDouble(row[3]));
                    address.setLongitude(parseDouble(row[4]));

                    property.setApartmentType(row[5]);
                    property.setApartmentName(row[6]);
                    property.setBhkType(parseLong(row[7]));
                    property.setFloor(parseLong(row[8]));
                    property.setTotalFloors(parseLong(row[9]));
                    property.setPropertyAge(parseLong(row[10]));
                    property.setFacing(row[11]);
                    property.setBuiltUpArea(parseDouble(row[12]));
                    property.setAvailableFor(row[13]);
                    property.setExpectedRent(parseLong(row[14]));
                    property.setExpectedDeposit(parseLong(row[15]));
//                    property.setMonthlyMaintenance(parseLong(row[16]));
                    property.setPreferredTenets(row[17]);
                    property.setNegotiation(parseBoolean(row[18]));
                    property.setAvailableFrom(java.sql.Date.valueOf(row[19]));
                    property.setFurnishing(row[20]);
                    property.setParking(row[21]);
                    property.setPropertyStatus(row[22]);
                    property.setPrice(parseLong(row[23]));
                    property.setIsSale(parseBoolean(row[24]));
                    property.setDescription(row[25]);
                    property.setCreatedAt(LocalDateTime.now());

                    amenity.setBathrooms(parseInt(row[26]));
                    amenity.setBalcony(parseInt(row[27]));
                    amenity.setWaterSupply(row[28]);
                    amenity.setPetAllowed(parseBoolean(row[29]));
                    amenity.setGym(parseBoolean(row[30]));
                    amenity.setNonVeg(parseBoolean(row[31]));
                    amenity.setGatedSecurity(parseBoolean(row[32]));
                    amenity.setShowProperty(row[33]);
                    amenity.setPropertyCondition(row[34]);
                    amenity.setSecondaryNumber(row[35]);
                    amenity.setNearByPlace(row[36]);
                    amenity.setLift(parseBoolean(row[37]));
                    amenity.setGasPipeLine(parseBoolean(row[38]));
                    amenity.setAirConditioner(parseBoolean(row[39]));
                    amenity.setPark(parseBoolean(row[40]));
                    amenity.setHouseKeeping(parseBoolean(row[41]));
                    amenity.setInternetService(parseBoolean(row[42]));
                    amenity.setPowerBackUp(parseBoolean(row[43]));
                    amenity.setServentRoom(parseBoolean(row[44]));
                    amenity.setSwimmingPool(parseBoolean(row[45]));
                    amenity.setFireSafety(parseBoolean(row[46]));

                    Image image = new Image();
                    image.setImageUrl(row[47]);
                    image.setProperty(property);

                    User owner = new User();
                    owner.setName(row[48]);
                    owner.setEmail(row[49]);
                    owner.setMobilePhone(row[50]);
                    owner.setRole(row[51]);
                    owner.setIsSubscribed(parseBoolean(row[52]));
                    userRepository.save(owner);

                    property.setOwner(owner);
                    property.setAmenity(amenity);
                    property.setAddress(address);
                    property.getPhotos().add(image);

                    propertyRepository.save(property);

                } catch (Exception e) {
                    System.err.println("Skipping row due to error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value.trim());
        } catch (Exception e) {
            return 0.0;
        }
    }

    private long parseLong(String value) {
        try {
            return Long.parseLong(value.trim());
        } catch (Exception e) {
            return 0L;
        }
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private boolean parseBoolean(String value) {
        try {
            return value != null && (value.trim().equalsIgnoreCase("true") || value.trim().equalsIgnoreCase("yes"));
        } catch (Exception e) {
            return false;
        }
    }
}
