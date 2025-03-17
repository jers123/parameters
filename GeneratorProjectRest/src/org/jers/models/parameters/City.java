package org.jers.models.parameters;

import jakarta.persistence.*;

@Entity
public class City extends BaseEntity {
    @Id
    private Integer idCity;

    @Column(nullable = false, length = 100)
    private String cityName;

    private State idState;

    private CityType idCityType;

    @Column(length = 5)
    private String landlinePhoneIdentifier;

    @Column(nullable = false)
    private Double area;

    @Column(name = "mi")
    private Double minimumTemperature;

    @Column(name = "m")
    private Double maximumTemperature;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(name = "h")
    private Double heightAboveSeaLevel;
}