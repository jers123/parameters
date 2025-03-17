package org.jers.parameters.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "city_to_city", catalog = "parametersDB", schema = "public")
@NoArgsConstructor
@Getter
@Setter
public class CityToCity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_city_to_city", nullable = false, updatable = false)
    private Integer idCityToCity;

    @JoinColumn(name = "id_origin_city", referencedColumnName = "id_city", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private City idOriginCity;

    @JoinColumn(name = "id_destination_city", referencedColumnName = "id_city", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private City idDestinationCity;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(nullable = false)
    private Double distance;

    @Column(name = "route_numbers", nullable = false)
    private int routeNumbers;
}