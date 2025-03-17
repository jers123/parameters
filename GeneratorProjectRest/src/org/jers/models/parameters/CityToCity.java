package org.jers.models.parameters;

import jakarta.persistence.*;

@Entity
public class CityToCity extends BaseEntity {
    @Id
    private Integer idCityToCity;

    private City idOriginCity;

    private City idDestinationCity;

    @Column(nullable = false)
    private Double distance;

    @Column(nullable = false)
    private Integer routeNumbers;
}